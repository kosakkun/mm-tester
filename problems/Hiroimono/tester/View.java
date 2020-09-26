import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JPanel
{
    public final int FIELD_SIZE_X = 1000;
    public final int FIELD_SIZE_Y = 1000;
    public final int PADDING      = 25;
    public final int VIEW_SIZE_X  = FIELD_SIZE_X + PADDING * 2;
    public final int VIEW_SIZE_Y  = FIELD_SIZE_Y + PADDING * 2;
    private final InputData id;
    private final OutputData od;
    private int curTurn;

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

    public void initState () throws Exception
    {
        curTurn = 0;
    }

    public boolean nextState () throws Exception
    {
        if (curTurn >= od.M) {
            return false;
        } else {
            curTurn++;
            return true;
        }
    }

    public boolean isFinish () throws Exception
    {
        return curTurn >= od.M;
    }

    /**
     * int   id.N    Number of vertices.
     * int[] id.x    The x coordinate of the vertex.
     * int[] id.y    The y coordinate of the vertex.
     * int   od.M    Number of visiting vertices.
     * int[] od.v    Order of visiting vertices.
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

        /* Draw path */
        final int strokeW = 6;
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.25f));
        g2.setStroke(new BasicStroke((float)strokeW));
        for (int i = 0; i < curTurn - 1; i++) {
            int a = od.v[i];
            int b = od.v[i + 1];
            g2.drawLine(id.x[a] * SCALE_X, id.y[a] * SCALE_Y,
                        id.x[b] * SCALE_X, id.y[b] * SCALE_Y);
        }

        /* Draw vertex */
        boolean[] used = new boolean[id.N];
        for (int i = 0; i < curTurn; i++) {
            used[od.v[i]] = true;
        }

        final int R = 8;
        g2.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < id.N; i++) {
            if (i == od.v[0] || i == od.v[od.v.length - 1]) {
                g2.setColor(new Color(used[i] ? 0x4169E1 : 0xFFFFFF));
            } else {
                g2.setColor(new Color(used[i] ? 0xDC143C : 0xFFFFFF));
            }
            g2.fillOval(id.x[i] * SCALE_X - R / 2, id.y[i] * SCALE_Y - R / 2, R, R);
            g2.setColor(new Color(0x000000));
            g2.drawOval(id.x[i] * SCALE_X - R / 2, id.y[i] * SCALE_Y - R / 2, R, R);
        }

        /* Draw the index */
        g2.setColor(new Color(0x000000));
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        for (int i = 0; i < curTurn; i++) {
            String text = "" + i;
            int px = id.x[od.v[i]] * SCALE_X + (int)((double)R / 1.4);
            int py = id.y[od.v[i]] * SCALE_Y - (int)((double)R / 1.4);
            g2.setColor(new Color(0x000000));
            g2.drawString(text, px, py);
        }

        return bi;
    }
}
