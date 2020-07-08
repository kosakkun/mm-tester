import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class View extends JPanel
{
    public static final int FIELD_SIZE_X = 1000;
    public static final int FIELD_SIZE_Y = 1000;
    public static final int MARGIN       = 10;
    public static final int PADDING      = 0;
    public static final int VIEW_SIZE_X  = FIELD_SIZE_X + MARGIN * 2 + PADDING * 2;
    public static final int VIEW_SIZE_Y  = FIELD_SIZE_Y + MARGIN * 2 + PADDING * 2;
    private final InputData id;
    private final OutputData od;

    public View (
        final InputData _id,
        final OutputData _od)
    {
        this.id = _id;
        this.od = _od;
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
     * int   id.N     Number of vertices.
     * int   id.R     The radius of the disk.
     * int[] id.xp    x coordinate of the i-th vertex.
     * int[] id.yp    y coordinate of the i-th vertex.
     * int   od.M     Number of disks.
     * int[] od.xr    x coordinate of the i-th disk.
     * int[] od.yr    y coordinate of the i-th disk.
     *
     * @see InputData
     * @see OutputData
     */
    public BufferedImage drawImage () throws Exception
    {
        BufferedImage bi = new BufferedImage(VIEW_SIZE_X, VIEW_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        /* Draw the background */
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(MARGIN, MARGIN, FIELD_SIZE_X + PADDING * 2, FIELD_SIZE_Y + PADDING * 2);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system. */
        g2.translate(MARGIN + PADDING, MARGIN + PADDING);

        /* Draw the disk */
        g2.setColor(new Color(0x41, 0x69, 0xe1, 0x1F));
        for (int i = 0; i < od.M; i++) {
            g2.fillOval(od.xr[i] - id.R, od.yr[i] - id.R, id.R * 2, id.R * 2);
        }
        g2.setColor(new Color(0x4169e1));
        g2.setStroke(new BasicStroke(1.0f));
        for (int i = 0; i < od.M; i++) {
            g2.drawOval(od.xr[i] - id.R, od.yr[i] - id.R, id.R * 2, id.R * 2);
        }

        /* Draw the vertex */
        final int R1 = 4;
        for (int i = 0; i < id.N; i++) {
            g2.setColor(new Color(0x454552));
            g2.fillOval(id.xp[i] - R1 / 2, id.yp[i] - R1 / 2, R1, R1);
            g2.setColor(new Color(0x000000));
            g2.drawOval(id.xp[i] - R1 / 2, id.yp[i] - R1 / 2, R1, R1);
        }

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system. */
        g2.translate(- MARGIN - PADDING, - MARGIN - PADDING);

        /* Draw the edge of this image */
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(0, MARGIN, VIEW_SIZE_X, PADDING);
        g2.fillRect(MARGIN, 0, PADDING, VIEW_SIZE_Y);
        g2.fillRect(VIEW_SIZE_X - MARGIN - PADDING, 0, PADDING, VIEW_SIZE_Y);
        g2.fillRect(0, VIEW_SIZE_Y - MARGIN - PADDING, VIEW_SIZE_X, PADDING);
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIEW_SIZE_X, MARGIN);
        g2.fillRect(0, 0, MARGIN, VIEW_SIZE_Y);
        g2.fillRect(VIEW_SIZE_X - MARGIN, 0, MARGIN, VIEW_SIZE_Y);
        g2.fillRect(0, VIEW_SIZE_Y - MARGIN, VIEW_SIZE_X, MARGIN);

        return bi;
    }
}
