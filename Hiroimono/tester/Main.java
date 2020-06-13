import java.io.File;
import java.io.FileWriter;
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
    static long seed = 1;
    static String exec = "";
    static long delay = 100;
    static boolean save_gif = false;
    static boolean save_png = false;
    static boolean vis  = false;
    static boolean debug = false;

    private String getJsonString (
        final InputData id,
        final OutputData od,
        final long score)
        throws Exception
    {
        class JsonInfo {
            public long seed;
            public long score;
        }

        JsonInfo info = new JsonInfo();
        info.seed = seed;
        info.score = score;
        
        ObjectMapper mapper = new ObjectMapper();
        String ret = mapper.writeValueAsString(info);
        return ret;
    }

    public static void saveText (
        final String fileName,
        final String text)
        throws Exception
    {
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file);
        fw.write(text);
        fw.close();
    }

    public Main ()
    {
        try {
            InputData  id = InputData.genInputData(seed);
            OutputData od = OutputData.runCommand(exec, id);
            long score = Checker.calcScore(id, od);
            System.out.println(getJsonString(id, od, score));

            if (debug) {
                saveText( "input-" + seed + ".txt", id.toString());
                saveText("output-" + seed + ".txt", od.toString());
            }

            if (!(save_png || save_gif || vis) || score < 0) {
                System.exit(0);
            }

            try {
                Visualizer v = new Visualizer(id, od);
                if (save_png) v.saveImage(String.valueOf(seed));
                if (save_gif) v.saveAnimation(String.valueOf(seed), delay);
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
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("{\"seed\":" + seed + ",\"score\":-1}");
            System.err.println("An exception occurred while running your program.");
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
            .desc("output the visualized result in png format.")
            .build());

        // --save-gif option
        options.addOption(Option.builder("g")
            .required(false)
            .longOpt("save-gif")
            .hasArg(false)
            .desc("output gif animation.")
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
            .desc("write the input and output of <command> as a text file.")
            .build());

        // --help option
        options.addOption(Option.builder("h")
            .required(false)
            .longOpt("help")
            .hasArg(false)
            .desc("print this message.")
            .build());


        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        boolean help = false;

        try {
            cmd = parser.parse(options, args);
            seed  = Long.parseLong(cmd.getOptionValue("seed"));
            exec  = cmd.getOptionValue("exec");
            save_png = cmd.hasOption("save-png");
            save_gif = cmd.hasOption("save-gif");
            vis   = cmd.hasOption("vis");
            debug = cmd.hasOption("debug");
            help  = cmd.hasOption("help");

            if (cmd.hasOption("delay")) {
                delay = Long.parseLong(cmd.getOptionValue("delay"));
            }
        } 
        catch (ParseException e) {
            e.printStackTrace();
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
