import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
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
    static String  title = "Traveling Salesman";
    static String  exec  = "";
    static long    seed  = 1;
    static boolean save  = false;
    static boolean vis   = false;
    static boolean debug = false;
    static boolean help  = false;

    public Main ()
    {
        InputData  id = null;
        OutputData od = null;
        double score = -1.0;

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

        if (Main.save && score >= 0) {
            saveImage(String.valueOf(Main.seed), id, od);
        }

        if (Main.vis && score >= 0) {
            visualize(id, od);
        }
    }

    private String getJsonResult (
        final InputData id,
        final OutputData od,
        final double _score)
    {
        class JsonResult {
            public long seed = Main.seed;
            public double score = _score;
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
            BufferedImage bi = view.drawImage();
            ImageIO.write(bi, "png", new File(fileName + ".png"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to save the image.");
        }
    }

    private void visualize (
        final InputData id,
        final OutputData od)
    {
        try {
            Visualizer v = new Visualizer(id, od);
            v.setVisible(true);
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

        // --save option
        options.addOption(Option.builder("o")
            .required(false)
            .longOpt("save")
            .hasArg(false)
            .desc("export the visualized result in png format.")
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
            Main.save  = cmd.hasOption("save");
            Main.vis   = cmd.hasOption("vis");
            Main.debug = cmd.hasOption("debug");
            Main.help  = cmd.hasOption("help");
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
