import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
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
        this.getContentPane().add(view);
        this.getContentPane().setPreferredSize(view.getDimension());
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Hiroimono");
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

    public void saveImage (final String fileName)
    {
        try {
            view.initState();
            while (view.nextState());
            BufferedImage bi = view.drawImage();
            ImageIO.write(bi, "png", new File(fileName +".png"));
        }
        catch (Exception e) {
            System.err.println("Visualizer failed to save the image.");
            e.printStackTrace();
        }
    }
}

class View extends JPanel
{
    final private int FIELD_SIZE_X = 1000;
    final private int FIELD_SIZE_Y = 1000;
    final private int PADDING1     = 10;
    final private int PADDING2     = 25;
    final private int VIS_SIZE_X   = FIELD_SIZE_X + (PADDING1 + PADDING2) * 2;
    final private int VIS_SIZE_Y   = FIELD_SIZE_Y + (PADDING1 + PADDING2) * 2;
    final InputData id;
    final OutputData od;

    public View (
        final InputData _id,
        final OutputData _od)
    {
        this.id = _id;
        this.od = _od;
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

    private int curTurn;

    public void initState ()
    {
        curTurn = 0;
    }

    public boolean nextState ()
    {
        if (curTurn >= od.M) {
            return false;
        } else {
            curTurn++;
            return true;
        }
    }

    public boolean isFinish ()
    {
        return curTurn >= od.M;
    }

    /**
     * int   id.N    Number of vertices.
     * int[] id.x    The x coordinate of the vertex.
     * int[] id.y    The y coordinate of the vertex.
     * int   od.M    Number of visiting vertices.
     * int[] od.v    Order of visiting vertices.
     *
     * @see InputData
     * @see OutputData
     */
    public BufferedImage drawImage () throws Exception
    {
        BufferedImage bi = new BufferedImage(VIS_SIZE_X, VIS_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        /* Draw background */
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIS_SIZE_X, VIS_SIZE_Y);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system. */
        g2.translate(PADDING1, PADDING1);

        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(0, 0, FIELD_SIZE_X + PADDING2 * 2, FIELD_SIZE_Y + PADDING2 * 2);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system. */
        g2.translate(PADDING2, PADDING2);

        final int SCALE_X = FIELD_SIZE_X / InputData.MAX_X;
        final int SCALE_Y = FIELD_SIZE_Y / InputData.MAX_Y;

        /* Draw path */
        final int strokeW = 6;
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.25f));
        g2.setStroke(new BasicStroke((float)strokeW));
        for (int i = 0; i < curTurn - 1; i++) {
            int a = od.v[i];
            int b = od.v[i + 1];
            g2.drawLine(id.x[a] * SCALE_X, id.y[a] * SCALE_Y,
                        id.x[b] * SCALE_X, id.y[b] * SCALE_Y);
        }

        /* Draw vertex */
        boolean[] used = new boolean[id.N];
        for (int i = 0; i < curTurn; i++) {
            used[od.v[i]] = true;
        }

        final int R = 8;
        g2.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < id.N; i++) {
            g2.setColor(new Color(used[i] ? 0xDC143C : 0xFFFFFF));
            g2.fillOval(id.x[i] * SCALE_X - R / 2, id.y[i] * SCALE_Y - R / 2, R, R);
            g2.setColor(new Color(0x000000));
            g2.drawOval(id.x[i] * SCALE_X - R / 2, id.y[i] * SCALE_Y - R / 2, R, R);
        }

        /* Draw the index */
        g2.setColor(new Color(0x000000));
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        for (int i = 0; i < curTurn; i++) {
            String text = "" + i;
            int px = id.x[od.v[i]] * SCALE_X + (int)((double)R / 1.4);
            int py = id.y[od.v[i]] * SCALE_Y - (int)((double)R / 1.4);
            g2.setColor(new Color(0x000000));
            g2.drawString(text, px, py);
        }

        return bi;
    }
}
