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
     * int   tester.WIDTH    The width of the board.
     * int   tester.HEIGHT   The height of the board.
     * int   tester.N        Number of vertices.
     * int   tester.R        The radius of the disk.
     * int[] tester.px       x coordinate of the i-th vertex.
     * int[] tester.py       y coordinate of the i-th vertex.
     * int   tester.M        Number of disks.
     * int[] tester.rx       x coordinate of the i-th disk.
     * int[] tester.ry       y coordinate of the i-th disk.
     *
     * @see Tester
     */
    private BufferedImage drawImage () {

        BufferedImage bi = new BufferedImage(VIS_SIZE_X, VIS_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        /* Draw the background */
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(PADDING, PADDING, FIELD_SIZE_X, FIELD_SIZE_Y);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(PADDING, PADDING);

        /* Draw the disk */
        g2.setColor(new Color(0xF4F4FF));
        for (int i = 0; i < tester.M; i++) {
            g2.fillOval(tester.rx[i] - tester.R, tester.ry[i] - tester.R, tester.R * 2, tester.R * 2);
        }
        g2.setColor(new Color(0x4169e1));
        for (int i = 0; i < tester.M; i++) {
            g2.drawOval(tester.rx[i] - tester.R, tester.ry[i] - tester.R, tester.R * 2, tester.R * 2);
        }

        /* Draw the vertex */
        final int R1 = 4;
        for (int i = 0; i < tester.N; i++) {
            g2.setColor(new Color(0x454552));
            g2.fillOval(tester.px[i] - R1 / 2, tester.py[i] - R1 / 2, R1, R1);
            g2.setColor(new Color(0x000000));
            g2.drawOval(tester.px[i] - R1 / 2, tester.py[i] - R1 / 2, R1, R1);
        }

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(-PADDING, -PADDING);

        /* Draw the edge of this image */
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIS_SIZE_X, PADDING);
        g2.fillRect(0, 0, PADDING, VIS_SIZE_Y);
        g2.fillRect(VIS_SIZE_X - PADDING, 0, PADDING, VIS_SIZE_Y);
        g2.fillRect(0, VIS_SIZE_Y - PADDING, VIS_SIZE_X, 10);

        return bi;
    }
}
