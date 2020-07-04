import java.io.File;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.imageio.ImageIO;

public class Visualizer extends JFrame
{
    private View view;
    private Dimension size;

    public Visualizer (
        final InputData id,
        final OutputData od)
    {
        view = new View(id, od);
        size = new Dimension(View.VIEW_SIZE_X, View.VIEW_SIZE_Y);
        this.getContentPane().add(view);
        this.getContentPane().setPreferredSize(size);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle(Main.title);
    }

    public void saveImage (final String fileName)
    {
        try {
            BufferedImage bi = view.drawImage();
            ImageIO.write(bi, "png", new File(fileName + ".png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Visualizer failed to save the image.");
        }
    }
}
