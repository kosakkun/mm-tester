import java.io.OutputStream;
import java.io.FileOutputStream;
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
    static String  title = "Hiroimono";
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
        try {
            InputData  id = InputData.genInputData(seed);
            OutputData od = OutputData.runCommand(exec, id);
            int _score = Checker.calcScore(id, od);

            class JsonResult {
                public long seed = Main.seed;
                public int score = _score;
                // public int N = id.N;
                // ...
            }

            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(new JsonResult());
            System.out.println(result);

            if (debug) {
                saveText( "input-" + seed + ".txt", id.toString());
                saveText("output-" + seed + ".txt", od.toString());
            }

            if ((svgif || svpng || vis) && _score >= 0) {
                visualize(id, od);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("An exception occurred while running your program.");
            System.out.println("{\"seed\":" + seed + ",\"score\":-1}");
        }
    }

    private void visualize (
        final InputData id,
        final OutputData od)
    {
        try {
            Visualizer v = new Visualizer(id, od);
            if (svpng) v.saveImage(String.valueOf(seed));
            if (svgif) v.saveAnimation(String.valueOf(seed), delay);
            if (vis) {
                v.setVisible(true);
                v.startAnimation(delay);
            } else {
                v.dispose();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Visualization failed.");
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
            seed  = Long.parseLong(cmd.getOptionValue("seed"));
            exec  = cmd.getOptionValue("exec");
            svpng = cmd.hasOption("save-png");
            svgif = cmd.hasOption("save-gif");
            vis   = cmd.hasOption("vis");
            debug = cmd.hasOption("debug");
            help  = cmd.hasOption("help");

            if (cmd.hasOption("delay")) {
                delay = Long.parseLong(cmd.getOptionValue("delay"));
            }
        } 
        catch (ParseException e) {
            System.err.println(e);
            help = true;
        }

        if (help) {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("Tester.jar", options);
            System.exit(0);
        }

        Main m = new Main();
    }
}
