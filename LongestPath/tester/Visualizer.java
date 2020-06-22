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
        this.setTitle("Longest Path");
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
    public BufferedImage drawImage ()
    {
        BufferedImage bi = new BufferedImage(VIS_SIZE_X, VIS_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        /* Draw background */
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIS_SIZE_X, VIS_SIZE_Y);
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(PADDING, PADDING, FIELD_SIZE_X, FIELD_SIZE_Y);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
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
            g2.setColor(new Color(0xFFFFFF));
            g2.fillOval(id.x[v] - R2 / 2, id.y[v] - R2 / 2, R2, R2);
            g2.setColor(new Color(0x000000));
            g2.drawOval(id.x[v] - R2 / 2, id.y[v] - R2 / 2, R2, R2);
        }

        return bi;
    }
}
