import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

public class Visualizer extends JPanel
{
    final int FIELD_SIZE_X = 1000;
    final int FIELD_SIZE_Y = 1000;
    final int PADDING      = 10;
    final int VIS_SIZE_X   = FIELD_SIZE_X + PADDING * 2;
    final int VIS_SIZE_Y   = FIELD_SIZE_Y + PADDING * 2;
    final Tester tester;

    public Visualizer (final Tester _tester) {
        this.tester = _tester;
    }

    public Dimension getDimension () {
        return new Dimension(VIS_SIZE_X, VIS_SIZE_Y);
    }

    public void saveImage (String fileName) {
        try {
            BufferedImage bi = drawImage();
            ImageIO.write(bi, "png", new File(fileName +".png"));
        }
        catch (Exception e) {
            System.err.println("Visualizer failed to save the image.");
            e.printStackTrace();
        }
    }

    @Override
    public void paint (Graphics g) {
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
     * int     tester.N      Number of vertices.
     * int     tester.M      Number of edges.
     * int[]   tester.a      Edge vertex A.
     * int[]   tester.b      Edge vertex B.
     * int[][] tester.edge   True is connected, false is not connected.   
     * int[]   tester.col    The color of the i-th vertex.
     *
     * @see Tester
     */
    private BufferedImage drawImage () {

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

        /* Draw edges information. */
        final int cell_width  = FIELD_SIZE_X / tester.N;
        final int cell_height = FIELD_SIZE_Y / tester.N;
        final float max_c = 30.0f;
        for (int x = 0; x < tester.N; x++) {
            for (int y = 0; y < tester.N; y++) {
                if (!tester.edge[x][y]) continue;
                float cval = Math.min(max_c, (float)tester.col[x]);
                Color c = Color.getHSBColor((0.4f / max_c) * cval, 1.0f, 1.0f);
                g2.setColor(c);
                g2.fillRect(cell_width * x, cell_width * y, cell_width, cell_height);
            }
        }
        for (int x = 0; x < tester.N; x++) {
            for (int y = 0; y < tester.N; y++) {
                if (!tester.edge[x][y]) continue;
                float cval = Math.min(max_c, (float)tester.col[y]);
                Color c = Color.getHSBColor((0.4f / max_c) * cval, 1.0f, 1.0f);
                g2.setColor(c);
                g2.fillRect(cell_width * x + 2, cell_width * y + 2, cell_width - 4, cell_height - 4);
            }
        }

        return bi;
    }
}
