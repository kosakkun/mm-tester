import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.imageio.ImageWriter;
import javax.imageio.ImageIO;
import javax.imageio.IIOImage;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.util.Iterator;

public class Visualizer extends JFrame
{
    private View view;

    public Visualizer (
        final InputData id,
        final OutputData od)
    {
        view = new View(id, od);
        view.initState();
        this.getContentPane().add(view);
        this.getContentPane().setPreferredSize(view.getDimension());
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Sliding Puzzle");
    }

    public void startAnimation (final long delay)
    {
        try {
            while (true) {
                Thread.sleep(2000);
                view.initState();
                while (view.nextState()) {
                    this.repaint();
                    Thread.sleep(delay);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAnimation (
        final String fileName,
        final long delay)
    {
        try {
            Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("gif");
            ImageWriter writer = it.next();
            File file = new File(fileName + ".gif");
            ImageOutputStream stream = ImageIO.createImageOutputStream(file);
            writer.setOutput(stream);
            writer.prepareWriteSequence(null);
            view.initState();

            do {
                BufferedImage image = view.drawImage();
                IIOMetadata metadata = getMetadata(view.isFinish() ? 2000 : delay, writer, image);
                writer.writeToSequence(new IIOImage(image, null, metadata), null);
            } while (view.nextState());

            writer.endWriteSequence();
            stream.close();
        }
        catch (Exception e) {
            System.err.println("Visualizer failed to save the gif animation.");
            e.printStackTrace();
        }
    }

    private IIOMetadata getMetadata (
        final long delay,
        final ImageWriter writer,
        final BufferedImage image)
        throws Exception
    {
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), iwp);
        String metaFormat = metadata.getNativeMetadataFormatName();
        IIOMetadataNode root = (IIOMetadataNode)metadata.getAsTree(metaFormat);
        
        IIOMetadataNode gctrl = new IIOMetadataNode("GraphicControlExtension");
        gctrl.setAttribute("delayTime", "" + (delay / 10));
        gctrl.setAttribute("disposalMethod", "none");
        gctrl.setAttribute("userInputFlag", "FALSE");
        gctrl.setAttribute("transparentColorFlag", "FALSE");
        gctrl.setAttribute("transparentColorIndex","0");
        root.appendChild(gctrl);
        
        IIOMetadataNode appext = new IIOMetadataNode("ApplicationExtensions");
        IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
        child.setAttribute("applicationID", "NETSCAPE");
        child.setAttribute("authenticationCode", "2.0");
        byte[] uo = {
            //last two bytes is an unsigned short (little endian) that
            //indicates the the number of times to loop.
            //0 means loop forever.
            0x1, 0x0, 0x0
        };
        child.setUserObject(uo);
        appext.appendChild(child);
        root.appendChild(appext);

        metadata.setFromTree(metaFormat, root);
        return metadata;
    }
}

class View extends JPanel
{
    private final int PANNEL_SIZE = 40;
    private final int PADDING     = 10;
    private final int FIELD_SIZE_X;
    private final int FIELD_SIZE_Y;
    private final int VIS_SIZE_X;
    private final int VIS_SIZE_Y;
    private final InputData id;
    private final OutputData od;

    private int curTurn;
    private int[][] curB;
    private int bposR;
    private int bposC;

    public View (
        final InputData _id,
        final OutputData _od)
    {
        this.id = _id;
        this.od = _od;
        FIELD_SIZE_X = PANNEL_SIZE * id.N;
        FIELD_SIZE_Y = PANNEL_SIZE * id.N;
        VIS_SIZE_X = FIELD_SIZE_X + PADDING * 2;
        VIS_SIZE_Y = FIELD_SIZE_Y + PADDING * 2;

        curB = new int[id.N][id.N];
    }

    public Dimension getDimension ()
    {
        return new Dimension(VIS_SIZE_X, VIS_SIZE_Y);
    }

    @Override
    public void paint (Graphics g)
    {
        try {
            BufferedImage bi = drawImage();
            g.drawImage(bi, 0, 0, VIS_SIZE_X, VIS_SIZE_Y, null);
        }
        catch (Exception e) {
            System.err.println("Visualizer failed to draw.");
            e.printStackTrace();
        }
    }

    public void initState ()
    {
        curTurn = 0;
        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N; c++) {
                curB[r][c] = id.B[r][c];
                if (curB[r][c] < 0) {
                    bposR = r;
                    bposC = c;
                }
            }
        }
    }

    public boolean isFinish ()
    {
        return curTurn >= od.M;
    }

    public boolean nextState ()
    {
        if (curTurn >= od.M) {
            return false;
        }

        if (Checker.movePannel(id.N, od.r[curTurn], od.c[curTurn], curB)) {
            bposR = od.r[curTurn];
            bposC = od.c[curTurn];
        }

        curTurn++;
        return true;
    }

    /**
     * int     id.N          Board size.
     * int     od.M          Number of moves.
     * int[]   od.r          Row coordinate to slide.
     * int[]   od.c          Column coordinate to slide.
     * int     bposR         Row coordinate of the blank.
     * int     bposC         Column coordinate of the blank.
     * int     curTurn       Number of moves from the initial board.
     * int[][] curB          Current board.
     * 
     * @see InputData
     * @see OutputData
     */
    public BufferedImage drawImage ()
    {
        BufferedImage bi = new BufferedImage(VIS_SIZE_X, VIS_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        /* Draw background */
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIS_SIZE_X, VIS_SIZE_Y);
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(PADDING, PADDING, FIELD_SIZE_X, FIELD_SIZE_Y);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system. */
        g2.translate(PADDING, PADDING);

        /* Draw pannels */
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N; c++) {
                if (curB[r][c] < 0) continue;
                int pos_x = r * PANNEL_SIZE;
                int pos_y = c * PANNEL_SIZE;
                int num = curB[r][c];
                g2.setColor(new Color(0xFFFFFF));
                g2.fillRect(pos_y, pos_x, PANNEL_SIZE, PANNEL_SIZE);
                g2.setColor(Color.getHSBColor((1.0f / (float)(id.N * id.N)) * (float)num, 1.0f, 0.95f));
                g2.fillRect(pos_y + 1, pos_x + 1, PANNEL_SIZE - 2, PANNEL_SIZE - 2);
                
                String text = "" + num;
                FontMetrics fm = g2.getFontMetrics();
                Rectangle rectText = fm.getStringBounds(text, g2).getBounds();
                int tw = pos_y - rectText.width / 2 + PANNEL_SIZE / 2;
                int th = pos_x - rectText.height / 2 + fm.getMaxAscent() + PANNEL_SIZE / 2;
                g2.setColor(new Color(0x000000));
                g2.drawString(text, tw, th);
            }
        }

        /* Mark panels to move. */
        if (curTurn < od.M) {
            int dx = bposR - od.r[curTurn];
            int dy = bposC - od.c[curTurn];
            if (!(dx == 0 || dy == 0) || (dx == 0 && dy == 0)) {
                int sx = od.r[curTurn];
                int sy = od.c[curTurn];
                g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
                g2.fillRect(PANNEL_SIZE * sy, PANNEL_SIZE * sx,
                            PANNEL_SIZE, PANNEL_SIZE);
                g2.setColor(new Color(0x000000));
                g2.setStroke(new BasicStroke(2.0f));
                g2.drawLine(PANNEL_SIZE * sy, PANNEL_SIZE * sx,
                            PANNEL_SIZE * (sy + 1), PANNEL_SIZE * (sx + 1));
                g2.drawLine(PANNEL_SIZE * (sy + 1), PANNEL_SIZE * sx,
                            PANNEL_SIZE * sy, PANNEL_SIZE * (sx + 1));
                g2.drawRect(PANNEL_SIZE * sy, PANNEL_SIZE * sx,
                            PANNEL_SIZE, PANNEL_SIZE);
            }
            else {
                int rx = od.r[curTurn] + dx + 1;
                int ry = od.c[curTurn] + dy + 1;
                int sx = (dx == 0) ? 1 : Math.abs(dx);
                int sy = (dy == 0) ? 1 : Math.abs(dy);
                g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.3f));
                g2.fillRect(Math.min(ry, od.c[curTurn]) * PANNEL_SIZE,
                            Math.min(rx, od.r[curTurn]) * PANNEL_SIZE, 
                            sy * PANNEL_SIZE, sx * PANNEL_SIZE);
                g2.setColor(new Color(0x000000));
                g2.setStroke(new BasicStroke(2.0f));
                g2.drawRect(Math.min(ry, od.c[curTurn]) * PANNEL_SIZE,
                            Math.min(rx, od.r[curTurn]) * PANNEL_SIZE, 
                            sy * PANNEL_SIZE, sx * PANNEL_SIZE);
                g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.3f));
                g2.fillOval(od.c[curTurn] * PANNEL_SIZE + PANNEL_SIZE / 8,
                            od.r[curTurn] * PANNEL_SIZE + PANNEL_SIZE / 8,
                            PANNEL_SIZE / 8 * 6, PANNEL_SIZE / 8 * 6);

                /* Draw an arrow */
                try {
                    int tx = bposR * PANNEL_SIZE + PANNEL_SIZE / 2;
                    int ty = bposC * PANNEL_SIZE + PANNEL_SIZE / 2;
                    double rad = 0.0;
                    if (dx < 0) rad = 0.0;
                    if (dx > 0) rad = Math.PI;
                    if (dy < 0) rad = Math.PI * 1.5;
                    if (dy > 0) rad = Math.PI * 0.5;

                    g2.translate(ty, tx);
                    g2.rotate(rad);
                    ClassLoader cl = this.getClass().getClassLoader(); 
                    ImageIcon icon = new ImageIcon(cl.getResource("img/arrow.png"));
                    Image rsImg = icon.getImage().getScaledInstance(PANNEL_SIZE, PANNEL_SIZE, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(rsImg);
                    g2.drawImage(icon.getImage(), - PANNEL_SIZE / 2, - PANNEL_SIZE / 2, this);
                    g2.translate(-ty, -tx);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return bi;
    }
}
