import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

public class View extends JPanel
{
    public static final int PANNEL_SIZE = 30;
    public static final int PADDING = 0;
    public static int FIELD_SIZE_X;
    public static int FIELD_SIZE_Y; 
    public static int VIEW_SIZE_X;
    public static int VIEW_SIZE_Y;
    private final InputData id;
    private final OutputData od;

    public View (
        final InputData id,
        final OutputData od)
    {
        View.FIELD_SIZE_X = PANNEL_SIZE * id.N;
        View.FIELD_SIZE_Y = PANNEL_SIZE * id.N;
        View.VIEW_SIZE_X = FIELD_SIZE_X + PADDING * 2;
        View.VIEW_SIZE_Y = FIELD_SIZE_Y + PADDING * 2;
        this.id = id;
        this.od = od;
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

    private Color getEdgeColor (
        final int x,
        final int y,
        final int N,
        final int C,
        final char pos,
        final int[][] cU,
        final int[][] cR,
        final int[][] cD,
        final int[][] cL)
        throws Exception
    {
        final int UNMATCH  = 0x2F;
        final int MATCH    = 0xFF;
        float ColorS = 0.75f;
        float ColorB = 1.00f;
        float ColorH = 0.00f;
        int   ColorA = 0x00;

        switch (pos) {
            case 'U': {
                ColorH = (1.0f / (float)C) * (float)cU[x][y];
                ColorA = ((y == 0) || (cD[x][y - 1] != cU[x][y])) ? UNMATCH : MATCH;
                break;
            }
            case 'R': {
                ColorH = (1.0f / (float)C) * (float)cR[x][y];
                ColorA = ((x == N - 1) || (cR[x][y] != cL[x + 1][y])) ? UNMATCH : MATCH;
                break;
            }
            case 'D': {
                ColorH = (1.0f / (float)C) * (float)cD[x][y];
                ColorA = ((y == N - 1) || (cD[x][y] != cU[x][y + 1])) ? UNMATCH : MATCH;
                break;
            }
            case 'L': {
                ColorH = (1.0f / (float)C) * (float)cL[x][y];
                ColorA = ((x == 0) || (cR[x - 1][y] != cL[x][y])) ? UNMATCH : MATCH;
                break;
            }
            default: {
                throw new Exception();
            }
        }

        Color ct = Color.getHSBColor(ColorH, ColorS, ColorB);
        return new Color(ct.getRed(), ct.getGreen(), ct.getBlue(), ColorA);
    }

    /**
     * int   id.N   Number of vertices.
     * int   id.C   Number of colors.
     * int[] id.U   Color of the top edge of the i th panel.
     * int[] id.D   Color of the bottom edge of the i-th panel.
     * int[] id.L   Color of the left side of the i-th panel.
     * int[] id.R   Color on the right side of the i-th panel.
     * int[] od.x   The x coordinate of the i-th panel.
     * int[] od.y   The y coordinate of the i-th panel.
     * int[] od.r   Panel rotation angle (0: 0°, 1: 90°, 2: 180°, 3: 270°).
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
        g2.setColor(new Color(0x000000));
        g2.fillRect(0, 0, VIEW_SIZE_X, VIEW_SIZE_Y);

        /* Converts the origin of the graphics context to a 
           point (x, y) in the current coordinate system. */
        g2.translate(PADDING, PADDING);

        /* Draw pannels */
        int[][] cU = new int[id.N][id.N];
        int[][] cD = new int[id.N][id.N];
        int[][] cL = new int[id.N][id.N];
        int[][] cR = new int[id.N][id.N];

        final int PN = id.N * id.N;
        for (int i = 0; i < PN; i++) {
            int[] col = {id.U[i], id.R[i], id.D[i], id.L[i]};
            cU[od.x[i]][od.y[i]] = col[(4 + 0 - od.r[i]) % 4];
            cR[od.x[i]][od.y[i]] = col[(4 + 1 - od.r[i]) % 4];
            cD[od.x[i]][od.y[i]] = col[(4 + 2 - od.r[i]) % 4];
            cL[od.x[i]][od.y[i]] = col[(4 + 3 - od.r[i]) % 4];
        }

        /* Draw pannels. */
        g2.setStroke(new BasicStroke(0.0f));

        /* Top */
        for (int i = 0; i < PN; i++) {
            g2.setColor(getEdgeColor(od.x[i], od.y[i], id.N, id.C, 'U', cU, cR, cD, cL));
            int[] posX = {od.x[i] * PANNEL_SIZE,
                          od.x[i] * PANNEL_SIZE + PANNEL_SIZE,
                          od.x[i] * PANNEL_SIZE + PANNEL_SIZE / 2};
            int[] posY = {od.y[i] * PANNEL_SIZE,
                          od.y[i] * PANNEL_SIZE,
                          od.y[i] * PANNEL_SIZE + PANNEL_SIZE / 2};
            g2.fillPolygon(posX, posY, 3);
        }

        /* Right */
        for (int i = 0; i < PN; i++) {
            g2.setColor(getEdgeColor(od.x[i], od.y[i], id.N, id.C, 'R', cU, cR, cD, cL));
            int[] posX = {od.x[i] * PANNEL_SIZE + PANNEL_SIZE,
                          od.x[i] * PANNEL_SIZE + PANNEL_SIZE,
                          od.x[i] * PANNEL_SIZE + PANNEL_SIZE / 2};
            int[] posY = {od.y[i] * PANNEL_SIZE,
                          od.y[i] * PANNEL_SIZE + PANNEL_SIZE,
                          od.y[i] * PANNEL_SIZE + PANNEL_SIZE / 2};
            g2.fillPolygon(posX, posY, 3);
        }

        /* Bottom */
        for (int i = 0; i < PN; i++) {
            g2.setColor(getEdgeColor(od.x[i], od.y[i], id.N, id.C, 'D', cU, cR, cD, cL));
            int[] posX = {od.x[i] * PANNEL_SIZE,
                          od.x[i] * PANNEL_SIZE + PANNEL_SIZE,
                          od.x[i] * PANNEL_SIZE + PANNEL_SIZE / 2};
            int[] posY = {od.y[i] * PANNEL_SIZE + PANNEL_SIZE,
                          od.y[i] * PANNEL_SIZE + PANNEL_SIZE,
                          od.y[i] * PANNEL_SIZE + PANNEL_SIZE / 2};
            g2.fillPolygon(posX, posY, 3);
        }

        /* Left */
        for (int i = 0; i < PN; i++) {
            g2.setColor(getEdgeColor(od.x[i], od.y[i], id.N, id.C, 'L', cU, cR, cD, cL));
            int[] posX = {od.x[i] * PANNEL_SIZE,
                          od.x[i] * PANNEL_SIZE,
                          od.x[i] * PANNEL_SIZE + PANNEL_SIZE / 2};
            int[] posY = {od.y[i] * PANNEL_SIZE,
                          od.y[i] * PANNEL_SIZE + PANNEL_SIZE,
                          od.y[i] * PANNEL_SIZE + PANNEL_SIZE / 2};
            g2.fillPolygon(posX, posY, 3);
        }

        /* Draw borders. */
        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(new Color(0x000000));

        for (int x = 0; x <= id.N; x++) {
            g2.drawLine(x * PANNEL_SIZE, 0, x * PANNEL_SIZE, FIELD_SIZE_Y);
        }

        for (int y = 0; y <= id.N; y++) {
            g2.drawLine(0, y * PANNEL_SIZE, FIELD_SIZE_X, y * PANNEL_SIZE);
        }

        return bi;
    }
}
