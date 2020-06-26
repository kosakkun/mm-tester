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
        this.setTitle("Clustering");
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
     * int    id.N      Number of vertexs.
     * int    id.K      Number of centor vertexs.
     * int[]  id.x      The x coordinate of the vertex.
     * int[]  id.y      The y coordinate of the vertex.
     * int[]  od.cx     The x coordinate of the centor vertex.
     * int[]  od.cy     The y coordinate of the centor vertex.
     *
     * int[]  Checker.getRoots(id, od):
     *        Index of the cluster to which the i-th vertex belongs.
     *
     * @see InputData
     * @see OutputData
     */
    public BufferedImage drawImage ()
    {
        BufferedImage bi = new BufferedImage(VIS_SIZE_X, VIS_SIZE_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(new Color(0xD3D3D3));
        g2.fillRect(0, 0, VIS_SIZE_X, VIS_SIZE_Y);
        g2.setColor(new Color(0xFFFFFF));
        g2.fillRect(PADDING, PADDING, FIELD_SIZE_X, FIELD_SIZE_Y);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system.*/
        g2.translate(PADDING, PADDING);

        /* Draw lines */
        int[] roots = Checker.getRoots(id, od);
        Color[] dotColor = new Color[id.N];
        for (int i = 0; i < id.N; i++) {
            final int bidx = roots[i];
            Color c = Color.getHSBColor((1.0f / (float)id.K) * (float)bidx, 1.0f, 0.95f);
            g2.setColor(c);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(id.x[i], id.y[i], od.cx[bidx], od.cy[bidx]);
        }

        /* Draw vertexs */
        final int R1 = 8;
        for (int i = 0; i < id.N; i++) {
            Color c = Color.getHSBColor((1.0f / (float)id.K) * (float)roots[i], 1.0f, 0.95f);
            g2.setColor(c);
            g2.fillOval(id.x[i] - R1 / 2, id.y[i] - R1 / 2, R1, R1);
            g2.setColor(new Color(0x000000));
            g2.drawOval(id.x[i] - R1 / 2, id.y[i] - R1 / 2, R1, R1);
        }

        final int R2 = 12;
        g2.setStroke(new BasicStroke(3.0f));
        for (int i = 0; i < id.K; i++) {
            Color c = Color.getHSBColor((1.0f / (float)id.K) * (float)i, 1.0f, 1.0f);
            g2.setColor(c);
            g2.fillOval(od.cx[i] - R2 / 2, od.cy[i] - R2 / 2, R2, R2);
            g2.setColor(new Color(0x000000));
            g2.drawOval(od.cx[i] - R2 / 2, od.cy[i] - R2 / 2, R2, R2);
        }

        return bi;
    }
}
