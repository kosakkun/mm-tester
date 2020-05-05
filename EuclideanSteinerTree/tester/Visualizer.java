import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

public class Visualizer extends JPanel
{
    final int FIELD_SIZE_X = 1000;
    final int FIELD_SIZE_Y = 1000;
    final int PADDING      = 10;
    final int VIS_SIZE_X   = FIELD_SIZE_X + PADDING * 2;
    final int VIS_SIZE_Y   = FIELD_SIZE_Y + PADDING * 2;
    final Tester tester;

    public Visualizer (final Tester _tester) {
        this.tester = _tester;
    }

    public Dimension getDimension () {
        return new Dimension(VIS_SIZE_X, VIS_SIZE_Y);
    }

    public void saveImage (String fileName) {
        try {
            BufferedImage bi = drawImage();
            ImageIO.write(bi, "png", new File(fileName +".png"));
        }
        catch (Exception e) {
            System.err.println("Visualizer failed to save the image.");
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
     * int    tester.N            Number of input vertices.
     * int    tester.M            Number of output vertices.
     * int[]  tester.x            The x coordinate of the input vertex.
     * int[]  tester.y            The y coordinate of the input vertex.
     * int[]  tester.ax           The x coordinate of the output vertex.
     * int[]  tester.ay           The y coordinate of the output vertex.
     * LineSegment[] teater.MST1  The line segments of the initial minimum spanning tree.
     * LineSegment[] tester.MST2  The line segments of the minimum spanning tree with added vertices.
     *
     * @see Tester
     * @see LineSegment
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

        /* Draw edges */
        g2.setColor(new Color(0x999999));
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[]{3}, 0));
        for (int i = 0; i < tester.MST1.length; i++) {
            g2.drawLine(tester.MST1[i].x1, tester.MST1[i].y1, 
                        tester.MST1[i].x2, tester.MST1[i].y2);
        }

        for (int i = 0; i < tester.MST2.length; i++) {
            boolean addLine = false;
            for (int j = 0; j < tester.MST1.length; j++) {
                if (tester.MST2[i].equals(tester.MST1[j])) {
                    addLine = true;
                    break;
                }
            }
            if (addLine) {
                g2.setColor(new Color(0x000000));
                g2.setStroke(new BasicStroke(1.5f));
            } else {
                g2.setColor(new Color(0x32cd32));
                g2.setStroke(new BasicStroke(1.5f));
            }
            g2.drawLine(tester.MST2[i].x1, tester.MST2[i].y1, 
                        tester.MST2[i].x2, tester.MST2[i].y2);
        }

        /* Draw vertex */
        final int R1 = 6;
        for (int i = 0; i < tester.N; i++) {
            g2.setColor(new Color(0xDC143C));
            g2.fillOval(tester.x[i] - R1 / 2, tester.y[i] - R1 / 2, R1, R1);
        }
        
        final int R2 = 6;
        for (int i = 0; i < tester.M; i++) {
            g2.setColor(new Color(0x4169E1));
            g2.fillOval(tester.ax[i] - R2 / 2, tester.ay[i] - R2 / 2, R2, R2);
        }

        return bi;
    }
}
