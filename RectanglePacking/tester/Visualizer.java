import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.BasicStroke;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.imageio.ImageIO;

public class Visualizer extends JFrame
{
    final int WIDTH  = 1000;
    final int HEIGHT = 1000;
    final int PADDING = 10;
    final int VIS_SIZE_X = 1000 + PADDING * 2;
    final int VIS_SIZE_Y = 1000 + PADDING * 2;
    final Tester tester;

    public Visualizer (final Tester _tester)
    {
        this.tester = _tester;
    }

    public void saveImage (String fileName)
    {
        try {
            BufferedImage bi = drawImage();
            ImageIO.write(bi, "png", new File(fileName +".png"));
        } catch (Exception e) {
            System.err.println("Visualizer failed to save the image.");
            e.printStackTrace();
        }
    }

    public void visualize ()
    {
        this.setVisible(true);
        Insets insets = getInsets();
        final int width  = VIS_SIZE_X + insets.left + insets.right;
        final int height = VIS_SIZE_Y + insets.top + insets.bottom;
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void paint (Graphics g)
    {
        try {
            BufferedImage bi = drawImage();
            g.drawImage(bi, getInsets().left, getInsets().top, VIS_SIZE_X, VIS_SIZE_Y, null);
        } catch (Exception e) {
            System.err.println("Visualizer failed to draw.");
            e.printStackTrace();
        }
    }

    /**
     * int    tester.N    Number of rectangles.
     * int[]  tester.h    The height of the rectangles.
     * int[]  tester.w    The width of the rectangles.
     * int[]  tester.x    The x coordinate of the lower left corner of the rectangle.
     * int[]  tester.y    The y coordinate of the lower left corner of the rectangle.
     *
     * @see Tester
     */
    private BufferedImage drawImage ()
    {
        BufferedImage bi = new BufferedImage(VIS_SIZE_X, VIS_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        /* Draw background */
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIS_SIZE_X, VIS_SIZE_Y);
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(PADDING, PADDING, WIDTH, HEIGHT);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(PADDING, PADDING);

        /* Draw rectangles */
        for (int i = 0; i < tester.N; i++) {
            Color c = Color.getHSBColor((1.0f / (float)tester.N) * (float)i, 0.60f, 1.0f);
            g2.setColor(c);
            g2.fillRect(tester.x[i], HEIGHT - tester.y[i] - tester.h[i], tester.w[i], tester.h[i]);
            g2.setColor(new Color(0x3F3F3F));
            g2.drawRect(tester.x[i], HEIGHT - tester.y[i] - tester.h[i], tester.w[i], tester.h[i]);
        }

        /* Draw score */
        int score = tester.getScore();
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawLine(0, HEIGHT - score, WIDTH, HEIGHT - score);

        g2.setFont(new Font("Courier", Font.BOLD, 15));
        FontMetrics fm = g2.getFontMetrics();
        char[] ch = ("Score = " + score).toCharArray();
        int x = WIDTH / 9 * 4;
        int y = HEIGHT - score - 5;
        g2.drawChars(ch, 0, ch.length, x, y);
     
        return bi;
    }
}
