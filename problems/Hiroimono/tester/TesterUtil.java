import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TesterUtil
{
    /**
     * private constructor
     */
    private TesterUtil ()
    {
        throw new AssertionError();
    }

    public static String getJsonResult (
        final long _seed,
        final int _score,
        final InputData id,
        final OutputData od)
    {
        class JsonResult {
            public long seed = _seed;
            public int score = _score;
            // public int N = (id != null) ? id.N : 0;
            // ...
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(new JsonResult());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to create the Json result.");
            return "";
        }
    }

    public static void saveText (
        final String name,
        final String text)
    {
        try {
            OutputStream out = new FileOutputStream(name);
            out.write(text.getBytes());
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to export " + name + ".");
        }
    }

    public static void saveImage (
        final String fileName,
        final InputData id,
        final OutputData od)
    {
        try {
            View view = new View(id, od);
            view.initState();
            while (view.nextState());
            BufferedImage bi = view.drawImage();
            ImageIO.write(bi, "png", new File(fileName + ".png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to save the image.");
        }
    }

    public static void saveAnimation (
        final String fileName,
        final InputData id,
        final OutputData od,
        final long delay)
    {
        try {
            View view = new View(id, od);
            GifAnimationWriter writer = new GifAnimationWriter(fileName);

            view.initState();
            do {
                BufferedImage frame = view.drawImage();
                writer.addFrame(frame, view.isFinish() ? 2000 : delay);
            } while (view.nextState());

            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to save the gif animation.");
        }
    }

    public static void visualize (
        final InputData id,
        final OutputData od,
        final long delay)
    {
        try {
            Visualizer v = new Visualizer(id, od);
            v.setVisible(true);
            v.startAnimation(delay);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Visualization failed.");
        }
    }
}
