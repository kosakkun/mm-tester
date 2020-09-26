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
    public final int FIELD_SIZE_X = 1000;
    public final int FIELD_SIZE_Y = 1000;
    public final int PADDING      = 10;
    public final int VIEW_SIZE_X  = FIELD_SIZE_X + PADDING * 2;
    public final int VIEW_SIZE_Y  = FIELD_SIZE_Y + PADDING * 2;
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
     * int   id.N  Number of vertices.
     * int   id.M  Number of edges.
     * int[] id.x  The x coordinate of the vertex.
     * int[] id.y  The y coordinate of the vertex.
     * int[] id.a  Edge vertex A.
     * int[] id.b  Edge vertex B.
     * int   od.K  Number of vertices on simple path.
     * int[] od.v  Simple path.
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
           point (x, y) in the current coordinate system. */
        g2.translate(PADDING, PADDING);

        /* Draw the graph */
        final int R1 = 6;
        g2.setColor(new Color(0xE0E0E0));
        g2.setStroke(new BasicStroke(1.0f));
        for (int i = 0; i < id.M; i++) {
            int a = id.a[i];
            int b = id.b[i];
            g2.drawLine(id.x[a], id.y[a], id.x[b], id.y[b]);
        }
        g2.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < id.N; i++) {
            g2.setColor(new Color(0xFFFFFF));
            g2.fillOval(id.x[i] - R1 / 2, id.y[i] - R1 / 2, R1, R1);
            g2.setColor(new Color(0xE0E0E0));
            g2.drawOval(id.x[i] - R1 / 2, id.y[i] - R1 / 2, R1, R1);
        }

        /* Draw the path */
        final int R2 = 6;
        g2.setColor(new Color(0x000000));
        g2.setStroke(new BasicStroke(1.0f));
        for (int i = 1; i < od.K; i++) {
            int a = od.v[i - 1];
            int b = od.v[i];
            g2.drawLine(id.x[a], id.y[a], id.x[b], id.y[b]);
        }
        g2.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < od.K; i++) {
            int v = od.v[i];
            if (i == 0 || i == od.K - 1) {
                g2.setColor(new Color(0x32cd32));
            } else {
                g2.setColor(new Color(0xDC143C)); 
            }
            g2.fillOval(id.x[v] - R2 / 2, id.y[v] - R2 / 2, R2, R2);
            g2.setColor(new Color(0x000000));
            g2.drawOval(id.x[v] - R2 / 2, id.y[v] - R2 / 2, R2, R2);
        }

        return bi;
    }
}
