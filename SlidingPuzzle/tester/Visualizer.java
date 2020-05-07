import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.imageio.ImageWriter;
import javax.imageio.ImageIO;
import javax.imageio.IIOImage;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import org.w3c.dom.Node;
import java.util.Iterator;

public class Visualizer extends JPanel
{
    final int PANNEL_SIZE = 40;
    final int PADDING     = 10;
    final int FIELD_SIZE_X;
    final int FIELD_SIZE_Y;
    final int VIS_SIZE_X;
    final int VIS_SIZE_Y;
    final Tester tester;

    public Visualizer (final Tester _tester) {
        this.tester = _tester;
        FIELD_SIZE_X = PANNEL_SIZE * _tester.N;
        FIELD_SIZE_Y = PANNEL_SIZE * _tester.N;
        VIS_SIZE_X = FIELD_SIZE_X + PADDING * 2;
        VIS_SIZE_Y = FIELD_SIZE_Y + PADDING * 2;
    }

    public Dimension getDimension () {
        return new Dimension(VIS_SIZE_X, VIS_SIZE_Y);
    }

    public void startAnimation (final long delay) {
        try {
            tester.initState();
            Thread.sleep(2000);
            while (tester.nextState()) {
                this.repaint();
                Thread.sleep(delay);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // output gif animation
    public void saveAnimation (final String filename, final long delay) {
        try {
            Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("gif");
            ImageWriter writer = it.next();
            File file = new File(filename + ".gif");
            ImageOutputStream stream = ImageIO.createImageOutputStream(file);
            writer.setOutput(stream);
            writer.prepareWriteSequence(null);

            tester.initState();
            do {
                BufferedImage image = drawImage();
                ImageWriteParam iwp = writer.getDefaultWriteParam();
                IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), iwp);
                String metaFormat = metadata.getNativeMetadataFormatName();
                IIOMetadataNode root = (IIOMetadataNode)metadata.getAsTree(metaFormat);
                
                IIOMetadataNode gctrl = new IIOMetadataNode("GraphicControlExtension");
                gctrl.setAttribute("delayTime", (tester.turn == tester.M) ? "200" : String.valueOf(delay / 10));
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
                writer.writeToSequence(new IIOImage(image, null, metadata), null);
            } while (tester.nextState());

            writer.endWriteSequence();
            stream.close();
        }
        catch (Exception e) {
            System.err.println("Visualizer failed to save the gif animation.");
            e.printStackTrace();
        }
    }

    @Override
    public void paint (Graphics g) {
        try {
            BufferedImage bi = drawImage();
            g.drawImage(bi, 0, 0, VIS_SIZE_X, VIS_SIZE_Y, null);
        }
        catch (Exception e) {
            System.err.println("Visualizer failed to draw.");
            e.printStackTrace();
        }
    }

    /**
     * int     tester.N          Board size.
     * int     tester.M          Number of moves.
     * int[]   tester.posX       Row coordinate to slide.
     * int[]   tester.posY       Column coordinate to slide.
     * int     tester.bposX      Row coordinate of the blank.
     * int     tester.bposY      Column coordinate of the blank.
     * int     tester.turn       Number of moves from the initial board.
     * int[][] tester.curBoard   Current board condition.
     * 
     * @see Tester
     */
    private BufferedImage drawImage () {

        BufferedImage bi = new BufferedImage(VIS_SIZE_X, VIS_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        /* Draw background */
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIS_SIZE_X, VIS_SIZE_Y);
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(PADDING, PADDING, FIELD_SIZE_X, FIELD_SIZE_Y);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(PADDING, PADDING);

        /* Draw pannels */
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        for (int x = 0; x < tester.N; x++) {
            for (int y = 0; y < tester.N; y++) {
                if (tester.curBoard[x][y] < 0) continue;
                int pos_x = x * PANNEL_SIZE;
                int pos_y = y * PANNEL_SIZE;
                int num = tester.curBoard[x][y];
                g2.setColor(new Color(0xFFFFFF));
                g2.fillRect(pos_y, pos_x, PANNEL_SIZE, PANNEL_SIZE);
                g2.setColor(Color.getHSBColor((1.0f / (float)(tester.N * tester.N)) * (float)num, 1.0f, 0.95f));
                g2.fillRect(pos_y + 1, pos_x + 1, PANNEL_SIZE - 2, PANNEL_SIZE - 2);
                char[] ch = ("" + num).toCharArray();
                g2.setColor(new Color(0x000000));
                g2.drawChars(ch, 0, ch.length, pos_y + 20 - ch.length * 4, pos_x + 25);
            }
        }

        /* Mark panels to move. */
        if (tester.turn < tester.M) {
            int dx = tester.bposX - tester.posX[tester.turn];
            int dy = tester.bposY - tester.posY[tester.turn];
            if (!(dx == 0 || dy == 0) || (dx == 0 && dy == 0)) {
                int sx = tester.posX[tester.turn];
                int sy = tester.posY[tester.turn];
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
                int rx = tester.posX[tester.turn] + dx + 1;
                int ry = tester.posY[tester.turn] + dy + 1;
                int sx = (dx == 0) ? 1 : Math.abs(dx);
                int sy = (dy == 0) ? 1 : Math.abs(dy);
                g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.3f));
                g2.fillRect(Math.min(ry, tester.posY[tester.turn]) * PANNEL_SIZE,
                            Math.min(rx, tester.posX[tester.turn]) * PANNEL_SIZE, 
                            sy * PANNEL_SIZE, sx * PANNEL_SIZE);
                g2.setColor(new Color(0x000000));
                g2.setStroke(new BasicStroke(2.0f));
                g2.drawRect(Math.min(ry, tester.posY[tester.turn]) * PANNEL_SIZE,
                            Math.min(rx, tester.posX[tester.turn]) * PANNEL_SIZE, 
                            sy * PANNEL_SIZE, sx * PANNEL_SIZE);
                g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.3f));
                g2.fillOval(tester.posY[tester.turn] * PANNEL_SIZE + PANNEL_SIZE / 8,
                            tester.posX[tester.turn] * PANNEL_SIZE + PANNEL_SIZE / 8,
                            PANNEL_SIZE / 8 * 6, PANNEL_SIZE / 8 * 6);

                /* Draw an arrow */
                try {
                    int tx = tester.bposX * PANNEL_SIZE + PANNEL_SIZE / 2;
                    int ty = tester.bposY * PANNEL_SIZE + PANNEL_SIZE / 2;
                    double rad = 0.0;
                    if (dx < 0) rad = 0.0;
                    if (dx > 0) rad = Math.PI;
                    if (dy < 0) rad = Math.PI * 1.5;
                    if (dy > 0) rad = Math.PI * 0.5;

                    g2.translate(ty, tx);
                    g2.rotate(rad);
                    ClassLoader cl = this.getClass().getClassLoader(); 
                    ImageIcon icon = new ImageIcon(cl.getResource("img/arrow.png"));
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
