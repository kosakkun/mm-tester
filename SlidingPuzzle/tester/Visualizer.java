import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

public class Visualizer extends JPanel
{
    final int PANNEL_SIZE = 40;
    final int PADDING     = 10;
    final int FIELD_SIZE_X;
    final int FIELD_SIZE_Y;
    final int VIS_SIZE_X;
    final int VIS_SIZE_Y;
    final Tester tester;

    public Visualizer (final Tester _tester) {
        this.tester = _tester;
        FIELD_SIZE_X = PANNEL_SIZE * _tester.N;
        FIELD_SIZE_Y = PANNEL_SIZE * _tester.N;
        VIS_SIZE_X = FIELD_SIZE_X + PADDING * 2;
        VIS_SIZE_Y = FIELD_SIZE_Y + PADDING * 2;
    }

    public Dimension getDimension () {
        return new Dimension(VIS_SIZE_X, VIS_SIZE_Y);
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
     * int     turn       Number of moves from the initial board.
     * int[][] curBoard   Current board condition.
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

        /* Draw pannels */
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        for (int x = 0; x < tester.N; x++) {
            for (int y = 0; y < tester.N; y++) {
                if (tester.curBoard[x][y] < 0) continue;
                int pos_x = x * PANNEL_SIZE;
                int pos_y = y * PANNEL_SIZE;
                int num = tester.curBoard[x][y];
                g2.setColor(new Color(0xFFFFFF));
                g2.fillRect(pos_y, pos_x, PANNEL_SIZE, PANNEL_SIZE);
                g2.setColor(Color.getHSBColor((1.0f / (float)(tester.N * tester.N)) * (float)num, 1.0f, 0.95f));
                g2.fillRect(pos_y + 1, pos_x + 1, PANNEL_SIZE - 2, PANNEL_SIZE - 2);
                char[] ch = ("" + num).toCharArray();
                g2.setColor(new Color(0x000000));
                g2.drawChars(ch, 0, ch.length, pos_y + 20 - ch.length * 4, pos_x + 25);
            }
        }

        return bi;
    }
}
