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
    public static final int FIELD_SIZE_X = 1000;
    public static final int FIELD_SIZE_Y = 1000;
    public static final int PADDING      = 10;
    public static final int VIEW_SIZE_X  = FIELD_SIZE_X + PADDING * 2;
    public static final int VIEW_SIZE_Y  = FIELD_SIZE_Y + PADDING * 2;
    private final InputData id;
    private final OutputData od;

    public View (
        final InputData id,
        final OutputData od)
    {
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
     * int    id.N    Number of vertexs.
     * int    id.K    Number of centor vertexs.
     * int[]  id.x    The x coordinate of the vertex.
     * int[]  id.y    The y coordinate of the vertex.
     * int[]  od.cx   The x coordinate of the centor vertex.
     * int[]  od.cy   The y coordinate of the centor vertex.
     *
     * int[]  Checker.getRoots(id, od):
     *        Index of the cluster to which the i-th vertex belongs.
     *
     * @see InputData
     * @see OutputData
     */
    public BufferedImage drawImage () throws Exception
    {
        BufferedImage bi = new BufferedImage(VIEW_SIZE_X, VIEW_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(0, 0, VIEW_SIZE_X, VIEW_SIZE_Y);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system. */
        g2.translate(PADDING, PADDING);

        /* Draw Voronoi diagram */
        g2.setStroke(new BasicStroke(2.0f));
        for (int r = 1500; r > 0; r -= 2) {
            for (int i = 0; i < id.K; i++) {
                Color c = Color.getHSBColor((1.0f / (float)id.K) * (float)i, 0.2f, 1.0f);
                g2.setColor(c);
                g2.drawOval(od.cx[i] - r / 2, od.cy[i] - r / 2, r, r);
            }
        }

        /* Draw vertexs */
        final int R1 = 6;
        int[] roots = Checker.getRoots(id, od);
        g2.setStroke(new BasicStroke(1.0f));
        for (int i = 0; i < id.N; i++) {
            Color c = Color.getHSBColor((1.0f / (float)id.K) * (float)roots[i], 1.0f, 0.95f);
            g2.setColor(c);
            g2.fillOval(id.x[i] - R1 / 2, id.y[i] - R1 / 2, R1, R1);
            g2.setColor(new Color(0x000000));
            g2.drawOval(id.x[i] - R1 / 2, id.y[i] - R1 / 2, R1, R1);
        }

        final int R2 = 10;
        g2.setColor(new Color(0x000000));
        g2.setStroke(new BasicStroke(3.0f));
        for (int i = 0; i < id.K; i++) {
            Color c = Color.getHSBColor((1.0f / (float)id.K) * (float)i, 1.0f, 1.0f);
            g2.fillOval(od.cx[i] - R2 / 2, od.cy[i] - R2 / 2, R2, R2);
        }

        return bi;
    }
}
