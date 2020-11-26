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
     * int     id.N    Number of vertices.
     * int[]   id.x    The x coordinate of the vertex.
     * int[]   id.y    The y coordinate of the vertex.
     * int[][] id.G    Graph.
     * long    id.type Scoring Type.
     * int[]   od.v    Order of visiting vertices.
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

        final int SCALE_X = FIELD_SIZE_X / InputData.MAX_X;
        final int SCALE_Y = FIELD_SIZE_Y / InputData.MAX_Y;

        /* Draw grid */
        g2.setColor(new Color(0xDDDDDD));
        g2.setStroke(new BasicStroke(1.0f));

        for (int x = 0; x <= InputData.MAX_X; x++) {
            g2.drawLine(x * SCALE_X, 0, x * SCALE_X, FIELD_SIZE_X);
        }

        for (int y = 0; y <= InputData.MAX_Y; y++) {
            g2.drawLine(0, y * SCALE_Y, FIELD_SIZE_Y, y * SCALE_Y);
        }

        /* Draw path */
        switch (id.type) {
        case 1:
            g2.setColor(new Color(0x000000));
            g2.setStroke(new BasicStroke(1.0f));
            for (int i = 0; i < id.N; i++) {
                int a = od.v[i];
                int b = od.v[(i + 1) % id.N];
                g2.drawLine(id.x[a] * SCALE_X, id.y[a] * SCALE_Y, id.x[b] * SCALE_X, id.y[b] * SCALE_Y);
            }
            break;
        case 2:
            g2.setColor(new Color(0x000000));
            g2.setStroke(new BasicStroke(1.5f));
            for (int i = 0; i < id.N; i++) {
                int a = od.v[i];
                int b = od.v[(i + 1) % id.N];
                g2.drawLine(id.x[a] * SCALE_X, id.y[a] * SCALE_Y, id.x[a] * SCALE_X, id.y[b] * SCALE_Y);
                g2.drawLine(id.x[a] * SCALE_X, id.y[b] * SCALE_Y, id.x[b] * SCALE_X, id.y[b] * SCALE_Y);
            }
            break;
        case 3:
            for (int i = 0; i < id.N; i++) {
                int a = od.v[i];
                int b = od.v[(i + 1) % id.N];
                int dx = Math.abs(id.x[a] - id.x[b]);
                int dy = Math.abs(id.y[a] - id.y[b]);
                g2.setColor(new Color(dx >= dy ? 0x000000 : 0x4169e1));
                g2.setStroke(new BasicStroke(dx >= dy ? 1.0f : 2.0f));
                g2.drawLine(id.x[a] * SCALE_X, id.y[a] * SCALE_Y, id.x[a] * SCALE_X, id.y[b] * SCALE_Y);
                g2.setColor(new Color(dx >= dy ? 0xdc143c : 0x000000));
                g2.setStroke(new BasicStroke(dx >= dy ? 2.0f : 1.0f));
                g2.drawLine(id.x[a] * SCALE_X, id.y[b] * SCALE_Y, id.x[b] * SCALE_X, id.y[b] * SCALE_Y);
            }
            break;
        case 4:
            int max_idx = -1;
            double max_dist = -1;
            g2.setColor(new Color(0x000000));
            g2.setStroke(new BasicStroke(1.0f));
            for (int i = 0; i < id.N; i++) {
                int a = od.v[i];
                int b = od.v[(i + 1) % id.N];
                g2.drawLine(id.x[a] * SCALE_X, id.y[a] * SCALE_Y, id.x[b] * SCALE_X, id.y[b] * SCALE_Y);
                double dx = id.x[a] - id.x[b];
                double dy = id.y[a] - id.y[b];
                if (max_dist < Math.sqrt(dx * dx + dy * dy)) {
                    max_dist = Math.sqrt(dx * dx + dy * dy);
                    max_idx  = i;
                }
            }

            g2.setColor(new Color(0xdc143c));
            g2.setStroke(new BasicStroke(2.0f));
            int a = od.v[max_idx];
            int b = od.v[(max_idx + 1) % id.N];
            g2.drawLine(id.x[a] * SCALE_X, id.y[a] * SCALE_Y, id.x[b] * SCALE_X, id.y[b] * SCALE_Y);
            break;
        default:
            System.err.println("Invalid scoring type: " + id.type);
            throw new Exception();
        }

        /* Draw vertex */
        final int R = 6;
        g2.setStroke(new BasicStroke(1.0f));
        for (int i = 0; i < id.N; i++) {
            g2.setColor(new Color(0xFFFFFF));
            g2.fillOval(id.x[i] * SCALE_X - R / 2, id.y[i] * SCALE_Y - R / 2, R, R);
            g2.setColor(new Color(0x000000));
            g2.drawOval(id.x[i] * SCALE_X - R / 2, id.y[i] * SCALE_Y - R / 2, R, R);
        }

        return bi;
    }
}