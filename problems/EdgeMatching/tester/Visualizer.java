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
        this.setTitle("Edge Matching");
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
    final private int PANNEL_SIZE = 30;
    final private int PADDING     = 10;
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
        FIELD_SIZE_X = PANNEL_SIZE * id.N;
        FIELD_SIZE_Y = PANNEL_SIZE * id.N;
        VIS_SIZE_X = FIELD_SIZE_X + PADDING * 2;
        VIS_SIZE_Y = FIELD_SIZE_Y + PADDING * 2;
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

    private Color getColor (
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
        final float ColorS = 0.75f;
        final float ColorB = 0.90f;
        float ColorH = 0.0f;
        int ColorA = MATCH;

        switch (pos) {
            case 'U': {
                if (y == 0) {
                    ColorA = UNMATCH;
                }
                else if (cD[x][y - 1] != cU[x][y]) {
                    ColorA = UNMATCH;
                }
                ColorH = (1.0f / (float)C) * (float)cU[x][y];
                break;
            }
            case 'R': {
                if (x == N - 1) {
                    ColorA = UNMATCH;
                } else if (cR[x][y] != cL[x + 1][y]) {
                    ColorA = UNMATCH;
                }
                ColorH = (1.0f / (float)C) * (float)cR[x][y];
                break;
            }
            case 'D': {
                if (y == N - 1) {
                    ColorA = UNMATCH;
                } else if (cD[x][y] != cU[x][y + 1]) {
                    ColorA = UNMATCH;
                }
                ColorH = (1.0f / (float)C) * (float)cD[x][y];
                break;
            }
            case 'L': {
                if (x == 0) {
                    ColorA = UNMATCH;
                } else if (cR[x - 1][y] != cL[x][y]) {
                    ColorA = UNMATCH;
                }
                ColorH = (1.0f / (float)C) * (float)cL[x][y];
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
            g2.setColor(getColor(od.x[i], od.y[i], id.N, id.C, 'U', cU, cR, cD, cL));
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
            g2.setColor(getColor(od.x[i], od.y[i], id.N, id.C, 'R', cU, cR, cD, cL));
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
            g2.setColor(getColor(od.x[i], od.y[i], id.N, id.C, 'D', cU, cR, cD, cL));
            int[] posX = {od.x[i] * PANNEL_SIZE,
                          od.x[i] * PANNEL_SIZE + PANNEL_SIZE,
                          od.x[i] * PANNEL_SIZE + PANNEL_SIZE / 2};
            int[] posY = {od.y[i] * PANNEL_SIZE + PANNEL_SIZE,
                          od.y[i] * PANNEL_SIZE + PANNEL_SIZE,
                          od.y[i] * PANNEL_SIZE + PANNEL_SIZE / 2};
            g2.fillPolygon(posX, posY, 3);
        }

        /* Right */
        for (int i = 0; i < PN; i++) {
            g2.setColor(getColor(od.x[i], od.y[i], id.N, id.C, 'L', cU, cR, cD, cL));
            int[] posX = {od.x[i] * PANNEL_SIZE,
                          od.x[i] * PANNEL_SIZE,
                          od.x[i] * PANNEL_SIZE + PANNEL_SIZE / 2};
            int[] posY = {od.y[i] * PANNEL_SIZE,
                          od.y[i] * PANNEL_SIZE + PANNEL_SIZE,
                          od.y[i] * PANNEL_SIZE + PANNEL_SIZE / 2};
            g2.fillPolygon(posX, posY, 3);
        }



        return bi;
    }
}
