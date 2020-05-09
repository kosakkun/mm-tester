import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.imageio.ImageIO;

public class Visualizer extends JFrame
{
    private View view;

    public Visualizer (
        final InputData id,
        final OutputData od)
    {
        view = new View(id, od);
        this.getContentPane().add(view);
        this.getContentPane().setPreferredSize(view.getDimension());
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public void saveImage (final String fileName)
    {
        try {
            BufferedImage bi = view.drawImage();
            ImageIO.write(bi, "png", new File(fileName +".png"));
        }
        catch (Exception e) {
            System.err.println("Visualizer failed to save the image.");
            e.printStackTrace();
        }
    }
}

class View extends JPanel
{
    final private int FIELD_SIZE_X = 1000;
    final private int FIELD_SIZE_Y = 1000;
    final private int PADDING      = 10;
    final private int VIS_SIZE_X   = FIELD_SIZE_X + PADDING * 2;
    final private int VIS_SIZE_Y   = FIELD_SIZE_Y + PADDING * 2;
    final InputData id;
    final OutputData od;

    public View (
        final InputData _id,
        final OutputData _od)
    {
        this.id = _id;
        this.od = _od;
    }

    public Dimension getDimension ()
    {
        return new Dimension(VIS_SIZE_X, VIS_SIZE_Y);
    }

    @Override
    public void paint (Graphics g)
    {
        try {
            BufferedImage bi = drawImage();
            g.drawImage(bi, 0, 0, VIS_SIZE_X, VIS_SIZE_Y, null);
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
    public BufferedImage drawImage ()
    {
        BufferedImage bi = new BufferedImage(VIS_SIZE_X, VIS_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        /* Draw the background */
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(PADDING, PADDING, FIELD_SIZE_X, FIELD_SIZE_Y);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system. */
        g2.translate(PADDING, PADDING);

        /* Draw the disk */
        g2.setColor(new Color(0xF4F4FF));
        for (int i = 0; i < od.M; i++) {
            g2.fillOval(od.xr[i] - id.R, od.yr[i] - id.R, id.R * 2, id.R * 2);
        }
        g2.setColor(new Color(0x4169e1));
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
        g2.translate(-PADDING, -PADDING);

        /* Draw the edge of this image */
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIS_SIZE_X, PADDING);
        g2.fillRect(0, 0, PADDING, VIS_SIZE_Y);
        g2.fillRect(VIS_SIZE_X - PADDING, 0, PADDING, VIS_SIZE_Y);
        g2.fillRect(0, VIS_SIZE_Y - PADDING, VIS_SIZE_X, 10);

        return bi;
    }
}
