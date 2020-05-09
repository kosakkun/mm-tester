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
    final private int PANNEL_SIZE  = 10;
    final private int PADDING      = 10;
    final private int FIELD_SIZE_X;
    final private int FIELD_SIZE_Y;
    final private int VIS_SIZE_X;
    final private int VIS_SIZE_Y;
    final InputData id;
    final OutputData od;

    public View (
        final InputData _id,
        final OutputData _od)
    {
        this.id = _id;
        this.od = _od;
        FIELD_SIZE_X = PANNEL_SIZE * (Generator.MAX_X + 1);
        FIELD_SIZE_Y = PANNEL_SIZE * (Generator.MAX_Y + 1);
        VIS_SIZE_X   = FIELD_SIZE_X + PADDING * 2;
        VIS_SIZE_Y   = FIELD_SIZE_Y + PADDING * 2;
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
     * int   id.N      The number of panels placed in advance.
     * int[] id.x      The x coordinate of panels placed in advance.
     * int[] id.y      The y coordinate of panels placed in advance.
     * int   od.M      The number of panels to be placed later.
     * int[] od.ax     The x coordinate of panels to be placed later.
     * int[] od.ay     The y coordinate of panels to be placed later.
     *
     * int   Generator.MAX_X
     * int   Generator.MAX_Y
     *
     * @see InputData
     * @see OutputData
     * @see Generator
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

        /* Draw pannels */
        g2.setColor(new Color(0xdc143c));
        for (int i = 0; i < id.N; i++) {
            g2.fillRect(id.x[i] * PANNEL_SIZE, id.y[i] * PANNEL_SIZE, PANNEL_SIZE, PANNEL_SIZE);
        }

        g2.setColor(new Color(0x4169e1));
        for (int i = 0; i < od.M; i++) {
            g2.fillRect(od.ax[i] * PANNEL_SIZE, od.ay[i] * PANNEL_SIZE, PANNEL_SIZE, PANNEL_SIZE);
        }

        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(new Color(0xD3D3D3));
        for (int i = 1; i <= Generator.MAX_X + 1; i++) {
            g2.drawLine(i * PANNEL_SIZE, 0, i * PANNEL_SIZE, FIELD_SIZE_Y);
        }
        for (int i = 1; i <= Generator.MAX_Y + 1; i++) {
            g2.drawLine(0, i * PANNEL_SIZE, FIELD_SIZE_X, i * PANNEL_SIZE);
        }

        return bi;
    }
}
