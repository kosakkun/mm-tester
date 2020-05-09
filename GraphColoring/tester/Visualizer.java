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
     * int     id.N      Number of vertices.
     * int     id.M      Number of edges.
     * int[]   id.a      Edge vertex A.
     * int[]   id.b      Edge vertex B.
     * int[]   od.c      The color of the i-th vertex.
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
           point (x, y) in the current coordinate system. */
        g2.translate(PADDING, PADDING);

        boolean[][] edge = new boolean[id.N][id.N];
        for (int i = 0; i < id.M; i++) {
            edge[id.a[i]][id.b[i]] = true;
            edge[id.b[i]][id.a[i]] = true;
        }

        int cntc = 1;
        int[] col = new int[id.N];
        int[] recol = new int[id.N];
        for (int i = 0; i < id.N; i++) {
            if (recol[od.c[i]] == 0) {
                recol[od.c[i]] = cntc++;
            }
        }
        for (int i = 0; i < id.N; i++) {
            col[i] = recol[od.c[i]];
        }

        /* Draw edges information. */
        final int cell_width  = FIELD_SIZE_X / id.N;
        final int cell_height = FIELD_SIZE_Y / id.N;
        final float max_c = 30.0f;
        for (int x = 0; x < id.N; x++) {
            for (int y = 0; y < id.N; y++) {
                if (!edge[x][y]) continue;
                float cval = Math.min(max_c, (float)col[x]);
                Color c = Color.getHSBColor((0.4f / max_c) * cval, 1.0f, 1.0f);
                g2.setColor(c);
                g2.fillRect(cell_width * x, cell_width * y, cell_width, cell_height);
            }
        }
        for (int x = 0; x < id.N; x++) {
            for (int y = 0; y < id.N; y++) {
                if (!edge[x][y]) continue;
                float cval = Math.min(max_c, (float)col[y]);
                Color c = Color.getHSBColor((0.4f / max_c) * cval, 1.0f, 1.0f);
                g2.setColor(c);
                g2.fillRect(cell_width * x + 2, cell_width * y + 2, cell_width - 4, cell_height - 4);
            }
        }

        return bi;
    }
}
