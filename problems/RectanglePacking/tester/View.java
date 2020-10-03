import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JPanel
{
    public final int PADDING = 0;
    public final int FIELD_SIZE_X;
    public final int FIELD_SIZE_Y;
    public final int VIEW_SIZE_X;
    public final int VIEW_SIZE_Y;
    private final InputData id;
    private final OutputData od;

    public View (
        final InputData id,
        final OutputData od)
    {
        this.FIELD_SIZE_X = OutputData.BOX_SIZE;
        this.FIELD_SIZE_Y = OutputData.BOX_SIZE;
        this.VIEW_SIZE_X  = FIELD_SIZE_X + PADDING * 2;
        this.VIEW_SIZE_Y  = FIELD_SIZE_Y + PADDING * 2;
        this.id = id;
        this.od = od;
        this.setPreferredSize(new Dimension(VIEW_SIZE_X, VIEW_SIZE_Y));
    }

    @Override
    public void paint (Graphics g)
    {
        try {
            BufferedImage bi = drawImage();
            g.drawImage(bi, 0, 0, VIEW_SIZE_X, VIEW_SIZE_Y, null);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Visualizer failed to draw.");
        }
    }

    /**
     * int    id.N    Number of rectangles.
     * int[]  id.h    The height of the i-th rectangle.
     * int[]  id.w    The width of the i-th rectangle.
     * int[]  id.x    The x coordinate of the lower left corner of the rectangle.
     * int[]  id.y    The y coordinate of the lower left corner of the rectangle.
     * int    Checker.calcScore(id, od)
     *
     * @see InputData
     * @see OutputData
     * @see Checker
     */
    public BufferedImage drawImage () throws Exception
    {
        BufferedImage bi = new BufferedImage(VIEW_SIZE_X, VIEW_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        /* Draw background */
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(0, 0, VIEW_SIZE_X, VIEW_SIZE_Y);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system. */
        g2.translate(PADDING, PADDING);

        /* Draw rectangles */
        for (int i = 0; i < id.N; i++) {
            Color c = Color.getHSBColor((1.0f / (float)id.N) * (float)i, 0.60f, 1.0f);
            g2.setColor(c);
            g2.fillRect(od.x[i], FIELD_SIZE_Y - od.y[i] - id.h[i], id.w[i], id.h[i]);
            g2.setColor(new Color(0x3F3F3F));
            g2.drawRect(od.x[i], FIELD_SIZE_Y - od.y[i] - id.h[i], id.w[i], id.h[i]);
        }

        /* Draw score */
        int score = Checker.calcScore(id, od);
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawLine(0, FIELD_SIZE_Y - score, FIELD_SIZE_X, FIELD_SIZE_Y - score);
    
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        String text = "Score = " + score;
        FontMetrics fm = g2.getFontMetrics();
        Rectangle rectText = fm.getStringBounds(text, g2).getBounds();
        int tw = FIELD_SIZE_X / 2 - rectText.width / 2;
        int th = FIELD_SIZE_Y - score - rectText.height / 2;
        g2.setColor(new Color(0x000000));
        g2.drawString(text, tw, th);
     
        return bi;
    }
}
