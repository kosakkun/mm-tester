import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.util.Arrays;

public class View extends JPanel
{
    public static final int FIELD_SIZE_X = 1000;
    public static final int FIELD_SIZE_Y = 1000;
    public static final int PADDING      = 10;
    public static final int INFO_WIDTH   = 250;
    public static final int VIEW_SIZE_X  = FIELD_SIZE_X + PADDING * 3 + INFO_WIDTH;
    public static final int VIEW_SIZE_Y  = FIELD_SIZE_Y + PADDING * 2;
    private final InputData id;
    private final OutputData od;

    public View (
        final InputData _id,
        final OutputData _od)
    {
        this.id = _id;
        this.od = _od;
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
     * int      id.N         Number of destinations.
     * int      id.M         Number of trucks.
     * int      id.depotX    Warehouse coordinates.
     * int      id.depotY    Warehouse coordinates.
     * int[]    id.x         The coordinates of the i-th destination.
     * int[]    id.y         The coordinates of the i-th destination.
     * int[]    id.cap       Loading capacity of the i-th truck.
     * int[]    id.speed     The traveling speed of the i-th track.
     *
     * int      id.K         The number of lines in the answer.
     * int[]    id.T         Track number.
     * int[]    id.L         Number of destinations.
     * int[][]  id.D         Destination number.
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

        /* Draw delivery routes */
        g2.setStroke(new BasicStroke(1.0f));
        int[] last_idx = new int[id.M];
        Arrays.fill(last_idx, -1);
        for (int i = 0; i < od.K; i++) {
            Color c = Color.getHSBColor((1.0f / (float)id.M) * (float)od.T[i], 1.0f, 0.80f);
            g2.setColor(c);
            if (last_idx[od.T[i]] >= 0) {
                g2.drawLine(id.x[last_idx[od.T[i]]], id.y[last_idx[od.T[i]]], 
                            id.depotX, id.depotY);
            }
            int cur_x = id.depotX;
            int cur_y = id.depotY;
            for (int j = 0; j < od.L[i]; j++) {
                int nxt_x = id.x[od.D[i][j]];
                int nxt_y = id.y[od.D[i][j]];
                g2.drawLine(cur_x, cur_y, nxt_x, nxt_y);
                cur_x = nxt_x;
                cur_y = nxt_y;
            }
            last_idx[od.T[i]] = od.D[i][od.L[i] - 1];
        }

        /* Draw the destinations */
        final int R1 = 6;
        g2.setStroke(new BasicStroke(1.0f));
        for (int i = 0; i < od.K; i++) {
            Color c = Color.getHSBColor((1.0f / (float)id.M) * (float)od.T[i], 1.0f, 0.95f);
            for (int j = 0; j < od.L[i]; j++) {
                int idx = od.D[i][j];
                g2.setColor(c);
                g2.fillOval(id.x[idx] - R1 / 2, id.y[idx] - R1 / 2, R1, R1);
                g2.setColor(new Color(0x000000));
                g2.drawOval(id.x[idx] - R1 / 2, id.y[idx] - R1 / 2, R1, R1);
            }
        }

        /* Draw the last destination visited by each track. */
        final int R2 = 10;
        g2.setStroke(new BasicStroke(2.0f));
        for (int i = 0; i < id.M; i++) {
            if (last_idx[i] < 0) continue;
            Color c = Color.getHSBColor((1.0f / (float)id.M) * (float)i, 1.0f, 0.95f);
            g2.setColor(c);
            g2.fillOval(id.x[last_idx[i]] - R2 / 2, id.y[last_idx[i]] - R2 / 2, R2, R2);
            g2.setColor(new Color(0x000000));
            g2.drawOval(id.x[last_idx[i]] - R2 / 2, id.y[last_idx[i]] - R2 / 2, R2, R2);
        }

        /* Draw the depot */
        final int R3 = 16;
        g2.setColor(new Color(0x000000));
        g2.fillOval(id.depotX - R3 / 2, id.depotY - R3 / 2, R3, R3);


        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system. */
        g2.translate(FIELD_SIZE_X + PADDING * 2, 0);


        /* Draw information of vehicles */
        final int vht = FIELD_SIZE_Y / 10;
        final int vwt = INFO_WIDTH - 10;
        g2.setStroke(new BasicStroke(2.0f));
        for (int i = 0; i < id.M; i++) {
            g2.setColor(new Color(0xFFFFFF));
            g2.fillRect(0, vht * i, vwt, vht - 10);
            Color c = Color.getHSBColor((1.0f / (float)id.M) * (float)i, 1.0f, 0.95f);
            g2.setColor(c);
            g2.drawRect(0, vht * i, vwt, vht - 10);
        }

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(10, 0);

        double[] dist = Checker.getDist(id, od);
        int worst_idx = -1;
        double worst_time = -1.0;
        for (int i = 0; i < id.M; i++) {
            double time = dist[i] / (double)id.speed[i];
            if (worst_time < time) {
                worst_time = time;
                worst_idx = i;
            }
        }

        for (int i = 0; i < id.M; i++) {
            g2.setColor(new Color(0x000000));
            String ch0 = "Vehicle #" + i;
            String ch1 = "capacity : " + id.cap[i];
            String ch2 = "speed : " + id.speed[i];
            String ch3 = "distance : " + dist[i];
            String ch4 = "time : " + dist[i] / (double)id.speed[i];
            g2.setFont(new Font("Courier", Font.BOLD, 14));
            g2.drawString(ch0, 0, i * 100 + 20);
            g2.setFont(new Font("Courier", Font.BOLD, 12));
            g2.drawString(ch1, 0, i * 100 + 34);
            g2.drawString(ch2, 0, i * 100 + 49);
            g2.drawString(ch3, 0, i * 100 + 64);
            if (i == worst_idx) {
                g2.setFont(new Font("Courier", Font.BOLD, 12));
                g2.setColor(new Color(0xFF0000));
            }
            g2.drawString(ch4, 0, i * 100 + 79);
            
        }

        return bi;
    }
}
