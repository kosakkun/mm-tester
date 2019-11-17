import java.io.File;
import java.util.Arrays;
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
    final int FIELD_HEIGHT = 1000;
    final int FIELD_WIDTH  = 1000;
    final int VEHICLE_VIEW_WIDTH = 250;
    final int PADDING = 10;
    final int VIS_SIZE_X = FIELD_WIDTH + VEHICLE_VIEW_WIDTH + PADDING * 2;
    final int VIS_SIZE_Y = FIELD_HEIGHT + PADDING * 2;
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
     * int      tester.N           Number of destinations.
     * int      tester.M           Number of trucks.
     * int      tester.depotX      Warehouse coordinates.
     * int      tester.depotY      Warehouse coordinates.
     * int[]    tester.x           The coordinates of the i-th destination.
     * int[]    tester.y           The coordinates of the i-th destination.
     * int[]    tester.cap         Loading capacity of the i-th truck.
     * int[]    tester.speed       The traveling speed of the i-th track.
     *
     * int      tester.K           The number of lines in the answer.
     * int[]    tester.T           Track number.
     * int[]    tester.L           Number of destinations.
     * int[][]  tester.D           Destination number.
     * double[] tester.dist        Total mileage of the i-th track.
     * double[] tester.time        Total travel time for the i-th track.
     * int[]    tester.last_idx    The destination number last delivered by the i-th truck.
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
        g2.fillRect(PADDING, PADDING, FIELD_WIDTH, FIELD_HEIGHT);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(PADDING, PADDING);

        /* Draw delivery routes */
        g2.setStroke(new BasicStroke(1.5f));
        int[] last_idx = new int[tester.M];
        Arrays.fill(last_idx, -1);
        for (int i = 0; i < tester.K; i++) {
            Color c = Color.getHSBColor((1.0f / (float)tester.M) * (float)tester.T[i], 1.0f, 0.80f);
            g2.setColor(c);
            if (last_idx[tester.T[i]] >= 0) {
                g2.drawLine(tester.x[last_idx[tester.T[i]]], tester.y[last_idx[tester.T[i]]], 
                            tester.depotX, tester.depotY);
            }
            int cur_x = tester.depotX;
            int cur_y = tester.depotY;
            for (int j = 0; j < tester.L[i]; j++) {
                int nxt_x = tester.x[tester.D[i][j]];
                int nxt_y = tester.y[tester.D[i][j]];
                g2.drawLine(cur_x, cur_y, nxt_x, nxt_y);
                cur_x = nxt_x;
                cur_y = nxt_y;
            }
            last_idx[tester.T[i]] = tester.D[i][tester.L[i] - 1];
        }

        /* Draw the destinations */
        final int R1 = 6;
        g2.setStroke(new BasicStroke(1.5f));
        for (int i = 0; i < tester.K; i++) {
            Color c = Color.getHSBColor((1.0f / (float)tester.M) * (float)tester.T[i], 1.0f, 0.95f);
            for (int j = 0; j < tester.L[i]; j++) {
                int idx = tester.D[i][j];
                g2.setColor(c);
                g2.fillOval(tester.x[idx] - R1 / 2, tester.y[idx] - R1 / 2, R1, R1);
                g2.setColor(new Color(0x000000));
                g2.drawOval(tester.x[idx] - R1 / 2, tester.y[idx] - R1 / 2, R1, R1);
            }
        }

        /* Draw the last destination visited by each track. */
        final int R2 = 10;
        g2.setStroke(new BasicStroke(3.0f));
        for (int i = 0; i < tester.M; i++) {
            if (last_idx[i] < 0) continue;
            Color c = Color.getHSBColor((1.0f / (float)tester.M) * (float)i, 1.0f, 0.95f);
            g2.setColor(c);
            g2.fillOval(tester.x[last_idx[i]] - R2 / 2, tester.y[last_idx[i]] - R2 / 2, R2, R2);
            g2.setColor(new Color(0x000000));
            g2.drawOval(tester.x[last_idx[i]] - R2 / 2, tester.y[last_idx[i]] - R2 / 2, R2, R2);
        }

        /* Draw the depot */
        final int R3 = 16;
        g2.setColor(new Color(0x000000));
        g2.fillOval(tester.depotX - R3 / 2, tester.depotY - R3 / 2, R3, R3);


        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(FIELD_WIDTH + PADDING, 0);

        /* Draw information of vehicles */
        final int vht = FIELD_HEIGHT / 10;
        final int vwt = VEHICLE_VIEW_WIDTH - 10;
        g2.setStroke(new BasicStroke(2.0f));
        for (int i = 0; i < tester.M; i++) {
            g2.setColor(new Color(0xFFFFFF));
            g2.fillRect(0, vht * i, vwt, vht - 10);
            Color c = Color.getHSBColor((1.0f / (float)tester.M) * (float)i, 1.0f, 0.95f);
            g2.setColor(c);
            g2.drawRect(0, vht * i, vwt, vht - 10);
        }

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(10, 0);

        int worst_idx = -1;
        double worst_time = -1.0;
        for (int i = 0; i < tester.M; i++) {
            if (worst_time < tester.dist[i] / (double)tester.speed[i]) {
                worst_time = tester.dist[i] / (double)tester.speed[i];
                worst_idx = i;
            }
        }

        for (int i = 0; i < tester.M; i++) {
            g2.setColor(new Color(0x000000));
            FontMetrics fm = g2.getFontMetrics();
            char[] ch0 = ("Vehicle #" + i).toCharArray();
            char[] ch1 = ("capacity : " + tester.cap[i]).toCharArray();
            char[] ch2 = ("speed : " + tester.speed[i]).toCharArray();
            char[] ch3 = ("distance : " + tester.dist[i]).toCharArray();
            char[] ch4 = ("time : " + tester.dist[i] / (double)tester.speed[i] + "\n").toCharArray();
            g2.setFont(new Font("Courier", Font.BOLD, 14));
            g2.drawChars(ch0, 0, ch0.length, 0, i * 100 + 20);
            g2.setFont(new Font("Courier", Font.BOLD, 12));
            g2.drawChars(ch1, 0, ch1.length, 0, i * 100 + 34);
            g2.drawChars(ch2, 0, ch2.length, 0, i * 100 + 49);
            g2.drawChars(ch3, 0, ch3.length, 0, i * 100 + 64);
            if (i == worst_idx) {
                g2.setFont(new Font("Courier", Font.BOLD, 12));
                g2.setColor(new Color(0xFF0000));
            }
            g2.drawChars(ch4, 0, ch4.length, 0, i * 100 + 79);
            
        }
        return bi;
    }

}
