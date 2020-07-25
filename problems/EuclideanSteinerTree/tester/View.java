import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

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
     * int    id.N    Number of input vertices.
     * int[]  id.x    The x coordinate of the input vertex.
     * int[]  id.y    The y coordinate of the input vertex.
     * int    od.M    Number of output vertices.
     * int[]  od.ax   The x coordinate of the output vertex.
     * int[]  od.ay   The y coordinate of the output vertex.
     *
     * LineSegment[] getMinimumPinningTree(N, x[], y[])
     *
     * @see InputData
     * @see OutputData
     * @see Checker
     * @see LineSegment
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

        int nx[] = new int[id.N + od.M];
        int ny[] = new int[id.N + od.M];
        for (int i = 0; i < id.N; i++) {
            nx[i] = id.x[i];
            ny[i] = id.y[i];
        }
        for (int i = id.N; i < id.N + od.M; i++) {
            nx[i] = od.ax[i - id.N];
            ny[i] = od.ay[i - id.N];
        }

        LineSegment[] MST1 = Checker.getMinimumPinningTree(id.N, id.x, id.y);
        LineSegment[] MST2 = Checker.getMinimumPinningTree(id.N + od.M, nx, ny);

        /* Draw edges */
        g2.setColor(new Color(0x999999));
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[]{3}, 0));
        for (int i = 0; i < MST1.length; i++) {
            g2.drawLine(MST1[i].x1, MST1[i].y1, MST1[i].x2, MST1[i].y2);
        }

        for (int i = 0; i < MST2.length; i++) {
            boolean addLine = false;
            for (int j = 0; j < MST1.length; j++) {
                if (MST2[i].equals(MST1[j])) {
                    addLine = true;
                    break;
                }
            }
            if (addLine) {
                g2.setColor(new Color(0x000000));
                g2.setStroke(new BasicStroke(1.5f));
            } else {
                g2.setColor(new Color(0x32cd32));
                g2.setStroke(new BasicStroke(1.5f));
            }
            g2.drawLine(MST2[i].x1, MST2[i].y1, MST2[i].x2, MST2[i].y2);
        }

        /* Draw vertex */
        final int R1 = 6;
        for (int i = 0; i < id.N; i++) {
            g2.setColor(new Color(0xDC143C));
            g2.fillOval(id.x[i] - R1 / 2, id.y[i] - R1 / 2, R1, R1);
        }
        
        final int R2 = 6;
        for (int i = 0; i < od.M; i++) {
            g2.setColor(new Color(0x4169E1));
            g2.fillOval(od.ax[i] - R2 / 2, od.ay[i] - R2 / 2, R2, R2);
        }

        return bi;
    }
}
