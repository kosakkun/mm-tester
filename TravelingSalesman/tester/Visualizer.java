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
     * int   tester.N    Number of vertices.
     * int[] tester.x    The x coordinate of the vertex.
     * int[] tester.y    The y coordinate of the vertex.
     * int[] tester.v    Order of visiting vertices.
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

        /* Draw path */
        g2.setColor(new Color(0x000000));
        g2.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < tester.N; i++) {
            int a = tester.v[i];
            int b = tester.v[(i + 1) % tester.N];
            g2.drawLine(tester.x[a], tester.y[a], tester.x[b], tester.y[b]);
        }

        /* Draw vertex */
        final int R = 6;
        g2.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < tester.N; i++) {
            g2.setColor(new Color(0xFFFFFF));
            g2.fillOval(tester.x[i] - R / 2, tester.y[i] - R / 2, R, R);
            g2.setColor(new Color(0x000000));
            g2.drawOval(tester.x[i] - R / 2, tester.y[i] - R / 2, R, R);
        }

        return bi;
    }
}
