import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Font;
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
     * int    tester.N    Number of rectangles.
     * int[]  tester.h    The FIELD_SIZE_Y of the rectangles.
     * int[]  tester.w    The FIELD_SIZE_X of the rectangles.
     * int[]  tester.x    The x coordinate of the lower left corner of the rectangle.
     * int[]  tester.y    The y coordinate of the lower left corner of the rectangle.
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

        /* Draw rectangles */
        for (int i = 0; i < tester.N; i++) {
            Color c = Color.getHSBColor((1.0f / (float)tester.N) * (float)i, 0.60f, 1.0f);
            g2.setColor(c);
            g2.fillRect(tester.x[i], FIELD_SIZE_Y - tester.y[i] - tester.h[i], tester.w[i], tester.h[i]);
            g2.setColor(new Color(0x3F3F3F));
            g2.drawRect(tester.x[i], FIELD_SIZE_Y - tester.y[i] - tester.h[i], tester.w[i], tester.h[i]);
        }

        /* Draw score */
        int score = tester.getScore();
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawLine(0, FIELD_SIZE_Y - score, FIELD_SIZE_X, FIELD_SIZE_Y - score);

        g2.setFont(new Font("Courier", Font.BOLD, 15));
        FontMetrics fm = g2.getFontMetrics();
        char[] ch = ("Score = " + score).toCharArray();
        int x = FIELD_SIZE_X / 9 * 4;
        int y = FIELD_SIZE_Y - score - 5;
        g2.drawChars(ch, 0, ch.length, x, y);
     
        return bi;
    }
}
