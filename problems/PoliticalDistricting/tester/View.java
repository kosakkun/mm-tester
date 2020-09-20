import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JPanel;

public class View extends JPanel
{
    public static final int FIELD_SIZE_X = 1000;
    public static final int FIELD_SIZE_Y = 1000;
    public static final int PADDING      = 10;
    public static final int VIEW_SIZE_X  = FIELD_SIZE_X + PADDING * 2;
    public static final int VIEW_SIZE_Y  = FIELD_SIZE_Y + PADDING * 2;
    public static final int COLOR_RANGE  = 100;
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
     * int     id.N    Board size.
     * int     id.K    Number of regions.
     * int[][] id.B    id.N * id.N Board.
     * int[][] od.R    Region number of each cell.
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

        /* Draw the cells. */
        final int CELL_SW = FIELD_SIZE_X / id.N;
        final int CELL_SH = FIELD_SIZE_Y / id.N;

        float avg = 0;
        int[] sum = new int[id.K];
        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N; c++) {
                sum[od.R[r][c]] += id.B[r][c];
                avg += (float)id.B[r][c] / (float)id.K;
            }
        }

        Color[] col = new Color[id.K];
        for (int i = 0; i < id.K; i++) {
            int diff = (Math.abs((int)avg - sum[i]) * 0xFF) / Math.min((int)avg, COLOR_RANGE);
            int alpha = (diff > 0xFF) ? 0xFF : diff;
            if (sum[i] >= avg) {
                col[i] = new Color(0xdc, 0x14, 0x3c, alpha);
            } else {
                col[i] = new Color(0x41, 0x69, 0xe1, alpha);
            }
        }

        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N; c++) {
                g2.setColor(col[od.R[r][c]]);
                g2.fillRect(c * CELL_SW, r * CELL_SH, CELL_SW, CELL_SH);
                g2.setColor(new Color(0x000000));
                g2.setFont(new Font("Arial", Font.PLAIN, 11));
                String text = "" + id.B[r][c];
                FontMetrics fm = g2.getFontMetrics();
                Rectangle rectText = fm.getStringBounds(text, g2).getBounds();
                int tw = c * CELL_SW - rectText.width / 2 + CELL_SW / 2;
                int th = r * CELL_SH - rectText.height / 2 + fm.getMaxAscent() + CELL_SH / 2;
                g2.drawString(text, tw, th);
            }
        }

        /* Draw boundaries. */
        g2.setColor(new Color(0x000000));
        g2.setStroke(new BasicStroke(2.0f));

        g2.drawLine(0, 0, FIELD_SIZE_X, 0);
        g2.drawLine(0, 0, 0, FIELD_SIZE_Y);
        g2.drawLine(FIELD_SIZE_X, 0, FIELD_SIZE_X, FIELD_SIZE_Y);
        g2.drawLine(0, FIELD_SIZE_Y, FIELD_SIZE_X, FIELD_SIZE_Y);

        for (int r = 0; r < id.N - 1; r++) {
            for (int c = 0; c < id.N; c++) {
                if (od.R[r][c] != od.R[r + 1][c]) {
                    g2.drawLine(c * CELL_SW, (r + 1) * CELL_SH, (c + 1) * CELL_SW, (r + 1) * CELL_SH);
                }
            }
        }
        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N - 1; c++) {
                if (od.R[r][c] != od.R[r][c + 1]) {
                    g2.drawLine((c + 1) * CELL_SW, r * CELL_SH, (c + 1) * CELL_SW, (r + 1) * CELL_SH);
                }
            }
        }

        return bi;
    }
}
