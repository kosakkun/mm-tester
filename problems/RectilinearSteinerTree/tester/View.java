import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JPanel
{
    public final int PANNEL_SIZE = 10;
    public final int PADDING     = 0;
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
        this.FIELD_SIZE_X = PANNEL_SIZE * (InputData.MAX_X + 1);
        this.FIELD_SIZE_Y = PANNEL_SIZE * (InputData.MAX_Y + 1);
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
            System.err.println("Visualizer failed to draw.");
            e.printStackTrace();
        }
    }

    /**
     * int   id.N      The number of panels placed in advance.
     * int[] id.x      The x coordinate of panels placed in advance.
     * int[] id.y      The y coordinate of panels placed in advance.
     * int   od.M      The number of panels to be placed later.
     * int[] od.ax     The x coordinate of panels to be placed later.
     * int[] od.ay     The y coordinate of panels to be placed later.
     *
     * int   InputData.MAX_X
     * int   InputData.MAX_Y
     *
     * @see InputData
     * @see OutputData
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
           point (x, y) in the current coordinate system.*/
        g2.translate(PADDING, PADDING);

        /* Draw pannels */
        g2.setColor(new Color(0xdc143c));
        for (int i = 0; i < id.N; i++) {
            g2.fillRect(id.x[i] * PANNEL_SIZE, id.y[i] * PANNEL_SIZE, PANNEL_SIZE, PANNEL_SIZE);
        }

        g2.setColor(new Color(0x4169e1));
        for (int i = 0; i < od.M; i++) {
            g2.fillRect(od.ax[i] * PANNEL_SIZE, od.ay[i] * PANNEL_SIZE, PANNEL_SIZE, PANNEL_SIZE);
        }

        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(new Color(0xD3D3D3));
        for (int i = 0; i <= InputData.MAX_X + 1; i++) {
            g2.drawLine(i * PANNEL_SIZE, 0, i * PANNEL_SIZE, FIELD_SIZE_Y);
        }
        for (int i = 0; i <= InputData.MAX_Y + 1; i++) {
            g2.drawLine(0, i * PANNEL_SIZE, FIELD_SIZE_X, i * PANNEL_SIZE);
        }

        return bi;
    }
}
