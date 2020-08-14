import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageWriter;
import javax.imageio.ImageIO;
import javax.imageio.IIOImage;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.util.Iterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main
{
    static String  title = "Sliding Puzzle";
    static String  exec  = "";
    static long    seed  = 1;
    static long    delay = 100;
    static boolean svgif = false;
    static boolean svpng = false;
    static boolean vis   = false;
    static boolean debug = false;
    static boolean help  = false;

    public Main ()
    {
        InputData  id = null;
        OutputData od = null;
        long score = -1;

        try {
            id = InputData.genInputData(Main.seed);
            od = OutputData.runCommand(Main.exec, id);
            score = Checker.calcScore(id, od);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("An exception occurred while running your program.");
        }
        finally {
            String result = getJsonResult(id, od, score);
            System.out.println(result);
        }

        if (Main.debug) {
            saveText( "input-" + Main.seed + ".txt", id.toString());
            saveText("output-" + Main.seed + ".txt", od.toString());
        }

        if (Main.svpng && score >= 0) {
            saveImage(String.valueOf(Main.seed), id, od);
        }

        if (Main.svgif && score >= 0) {
            saveAnimation(String.valueOf(Main.seed), id, od, Main.delay);
        }

        if (Main.vis && score >= 0) {
            visualize(id, od, Main.delay);
        }
    }

    private String getJsonResult (
        final InputData id,
        final OutputData od,
        final long _score)
    {
        class JsonResult {
            public long seed = Main.seed;
            public long score = _score;
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

    private void saveText (
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

    private void saveImage (
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

    private void saveAnimation (
        final String fileName,
        final InputData id,
        final OutputData od,
        final long delay)
    {
        try {
            View view = new View(id, od);
            Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("gif");
            ImageWriter writer = it.next();
            File file = new File(fileName + ".gif");
            ImageOutputStream stream = ImageIO.createImageOutputStream(file);
            writer.setOutput(stream);
            writer.prepareWriteSequence(null);
            view.initState();

            do {
                BufferedImage image = view.drawImage();
                IIOMetadata metadata = getMetadata(view.isFinish() ? 2000 : delay, writer, image);
                writer.writeToSequence(new IIOImage(image, null, metadata), null);
            } while (view.nextState());

            writer.endWriteSequence();
            stream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to save the gif animation.");
        }
    }

    private IIOMetadata getMetadata (
        final long delay,
        final ImageWriter writer,
        final BufferedImage image)
        throws Exception
    {
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), iwp);
        String metaFormat = metadata.getNativeMetadataFormatName();
        IIOMetadataNode root = (IIOMetadataNode)metadata.getAsTree(metaFormat);
        
        IIOMetadataNode gctrl = new IIOMetadataNode("GraphicControlExtension");
        gctrl.setAttribute("delayTime", "" + (delay / 10));
        gctrl.setAttribute("disposalMethod", "none");
        gctrl.setAttribute("userInputFlag", "FALSE");
        gctrl.setAttribute("transparentColorFlag", "FALSE");
        gctrl.setAttribute("transparentColorIndex","0");
        root.appendChild(gctrl);
        
        IIOMetadataNode appext = new IIOMetadataNode("ApplicationExtensions");
        IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
        child.setAttribute("applicationID", "NETSCAPE");
        child.setAttribute("authenticationCode", "2.0");
        byte[] uo = {
            //last two bytes is an unsigned short (little endian) that
            //indicates the the number of times to loop.
            //0 means loop forever.
            0x1, 0x0, 0x0
        };
        child.setUserObject(uo);
        appext.appendChild(child);
        root.appendChild(appext);

        metadata.setFromTree(metaFormat, root);
        return metadata;
    }

    private void visualize (
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

    public static void main (String[] args)
    {
        Options options = new Options();

        // --seed option
        options.addOption(Option.builder("s")
            .required(true)
            .longOpt("seed")
            .hasArg(true)
            .type(Number.class)
            .argName("seed")
            .desc("set a random seed. (required)")
            .build());

        // --exec option
        options.addOption(Option.builder("e")
            .required(true)
            .longOpt("exec")
            .hasArg(true)
            .type(String.class)
            .argName("command")
            .desc("set the execution command of the solver. (required)")
            .build());

        // --vis option
        options.addOption(Option.builder("v")
            .required(false)
            .longOpt("vis")
            .hasArg(false)
            .desc("visualize the result.")
            .build());

        // --save-png option
        options.addOption(Option.builder("p")
            .required(false)
            .longOpt("save-png")
            .hasArg(false)
            .desc("export the visualized result in png format.")
            .build());

        // --save-gif option
        options.addOption(Option.builder("g")
            .required(false)
            .longOpt("save-gif")
            .hasArg(false)
            .desc("export gif animation.")
            .build());

        // --delay option
        options.addOption(Option.builder("l")
            .required(false)
            .longOpt("delay")
            .hasArg(true)
            .type(Number.class)
            .argName("ms")
            .desc("frame delay time [ms].")
            .build());

        // --debug option
        options.addOption(Option.builder("d")
            .required(false)
            .longOpt("debug")
            .hasArg(false)
            .desc("export the input and output of <command> as a text file.")
            .build());

        // --help option
        options.addOption(Option.builder("h")
            .required(false)
            .longOpt("help")
            .hasArg(false)
            .desc("print this message.")
            .build());

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            Main.seed  = Long.parseLong(cmd.getOptionValue("seed"));
            Main.exec  = cmd.getOptionValue("exec");
            Main.svpng = cmd.hasOption("save-png");
            Main.svgif = cmd.hasOption("save-gif");
            Main.vis   = cmd.hasOption("vis");
            Main.debug = cmd.hasOption("debug");
            Main.help  = cmd.hasOption("help");

            if (cmd.hasOption("delay")) {
                Main.delay = Long.parseLong(cmd.getOptionValue("delay"));
            }
        } 
        catch (ParseException e) {
            System.err.println(e);
            Main.help = true;
        }

        if (Main.help) {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("Tester.jar", options);
            System.exit(0);
        }

        Main m = new Main();
    }
}
