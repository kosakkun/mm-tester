import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JPanel
{
    public final int PANNEL_SIZE = 40;
    public final int PADDING     = 1;
    public final int FIELD_SIZE_X;
    public final int FIELD_SIZE_Y;
    public final int VIEW_SIZE_X;
    public final int VIEW_SIZE_Y;
    private final InputData id;
    private final OutputData od;
    private int curTurn;
    private int[][] curB;
    private int bposR;
    private int bposC;

    public View (
        final InputData id,
        final OutputData od)
    {
        this.FIELD_SIZE_X = PANNEL_SIZE * id.N;
        this.FIELD_SIZE_Y = PANNEL_SIZE * id.N;
        this.VIEW_SIZE_X = FIELD_SIZE_X + PADDING * 2;
        this.VIEW_SIZE_Y = FIELD_SIZE_Y + PADDING * 2;
        this.id = id;
        this.od = od;
        this.curB = new int[id.N][id.N];
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
        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N; c++) {
                curB[r][c] = id.B[r][c];
                if (curB[r][c] < 0) {
                    bposR = r;
                    bposC = c;
                }
            }
        }
    }

    public boolean nextState () throws Exception
    {
        if (curTurn >= od.M) {
            return false;
        }

        if (Checker.movePannel(id.N, od.r[curTurn], od.c[curTurn], curB)) {
            bposR = od.r[curTurn];
            bposC = od.c[curTurn];
        }

        curTurn++;
        return true;
    }

    public boolean isFinish ()
    {
        return curTurn >= od.M;
    }

    /**
     * int     id.N       Board size.
     * int     od.M       Number of moves.
     * int[]   od.r       Row coordinate to slide.
     * int[]   od.c       Column coordinate to slide.
     * int     bposR      Row coordinate of the blank.
     * int     bposC      Column coordinate of the blank.
     * int     curTurn    Number of moves from the initial board.
     * int[][] curB       Current board.
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

        /* Draw pannels */
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        for (int r = 0; r < id.N; r++) {
            for (int c = 0; c < id.N; c++) {
                if (curB[r][c] < 0) continue;
                int pos_x = r * PANNEL_SIZE;
                int pos_y = c * PANNEL_SIZE;
                int num = curB[r][c];
                g2.setColor(new Color(0xFFFFFF));
                g2.fillRect(pos_y, pos_x, PANNEL_SIZE, PANNEL_SIZE);
                g2.setColor(Color.getHSBColor((1.0f / (float)(id.N * id.N)) * (float)num, 1.0f, 0.95f));
                g2.fillRect(pos_y + 1, pos_x + 1, PANNEL_SIZE - 2, PANNEL_SIZE - 2);
                
                String text = "" + num;
                FontMetrics fm = g2.getFontMetrics();
                Rectangle rectText = fm.getStringBounds(text, g2).getBounds();
                int tw = pos_y - rectText.width / 2 + PANNEL_SIZE / 2;
                int th = pos_x - rectText.height / 2 + fm.getMaxAscent() + PANNEL_SIZE / 2;
                g2.setColor(new Color(0x000000));
                g2.drawString(text, tw, th);
            }
        }

        /* Mark panels to move. */
        if (curTurn < od.M) {
            int dx = bposR - od.r[curTurn];
            int dy = bposC - od.c[curTurn];
            if (!(dx == 0 || dy == 0) || (dx == 0 && dy == 0)) {
                int sx = od.r[curTurn];
                int sy = od.c[curTurn];
                g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
                g2.fillRect(PANNEL_SIZE * sy, PANNEL_SIZE * sx,
                            PANNEL_SIZE, PANNEL_SIZE);
                g2.setColor(new Color(0x000000));
                g2.setStroke(new BasicStroke(2.0f));
                g2.drawLine(PANNEL_SIZE * sy, PANNEL_SIZE * sx,
                            PANNEL_SIZE * (sy + 1), PANNEL_SIZE * (sx + 1));
                g2.drawLine(PANNEL_SIZE * (sy + 1), PANNEL_SIZE * sx,
                            PANNEL_SIZE * sy, PANNEL_SIZE * (sx + 1));
                g2.drawRect(PANNEL_SIZE * sy, PANNEL_SIZE * sx,
                            PANNEL_SIZE, PANNEL_SIZE);
            }
            else {
                int rxp = od.r[curTurn] + dx + 1;
                int ryp = od.c[curTurn] + dy + 1;
                int sxp = (dx == 0) ? 1 : Math.abs(dx);
                int syp = (dy == 0) ? 1 : Math.abs(dy);
                g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
                g2.fillRect(Math.min(ryp, od.c[curTurn]) * PANNEL_SIZE,
                            Math.min(rxp, od.r[curTurn]) * PANNEL_SIZE, 
                            syp * PANNEL_SIZE, sxp * PANNEL_SIZE);
                int rxf = od.r[curTurn] + dx;
                int ryf = od.c[curTurn] + dy;
                int sxf = (dx == 0) ? 1 : Math.abs(dx) + 1;
                int syf = (dy == 0) ? 1 : Math.abs(dy) + 1;
                g2.setColor(new Color(0x000000));
                g2.setStroke(new BasicStroke(2.0f));
                g2.drawRect(Math.min(ryf, od.c[curTurn]) * PANNEL_SIZE,
                            Math.min(rxf, od.r[curTurn]) * PANNEL_SIZE, 
                            syf * PANNEL_SIZE, sxf * PANNEL_SIZE);

                /* Direction */
                g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.4f));
                g2.fillOval(od.c[curTurn] * PANNEL_SIZE + PANNEL_SIZE / 4,
                            od.r[curTurn] * PANNEL_SIZE + PANNEL_SIZE / 4,
                            PANNEL_SIZE / 2, PANNEL_SIZE / 2);

                g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.8f));
                int tx = bposR * PANNEL_SIZE + PANNEL_SIZE / 2;
                int ty = bposC * PANNEL_SIZE + PANNEL_SIZE / 2;
                double rad = 0.0;
                if (dx < 0) rad = 0.0;
                if (dx > 0) rad = Math.PI;
                if (dy < 0) rad = Math.PI * 1.5;
                if (dy > 0) rad = Math.PI * 0.5;

                g2.translate(ty, tx);
                g2.rotate(rad);

                int[] posX = {-PANNEL_SIZE / 8, 0, PANNEL_SIZE / 8};
                int[] posY = {PANNEL_SIZE / 8, -PANNEL_SIZE / 8, PANNEL_SIZE / 8};
                g2.fillPolygon(posX, posY, 3);

                g2.rotate(-rad);
                g2.translate(-ty, -tx);
            }
        }

        return bi;
    }
}
