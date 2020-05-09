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
    static boolean vis   = false;
    static boolean save  = false;
    static boolean debug = false;

    private String getJsonString (final Object obj)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String ret = mapper.writeValueAsString(obj);
            return ret;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("JSON generation failed.");
            return "";
        }
    }

    public static void saveText (
        final String fileName,
        final String text)
    {
        try {
            File file = new File(fileName);
            FileWriter fw = new FileWriter(file);
            fw.write(text);
            fw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(
                "Failed to save file " + fileName + ".");
        }
    }

    public Main ()
    {
        try {
            InputData  id = InputData.genInputData(seed);
            OutputData od = OutputData.runCommand(exec, id);
            Visualizer v = new Visualizer(id, od);
            long score = Checker.calcScore(id, od);

            if (save && score >= 0) {
                v.saveAnimation(String.valueOf(seed), delay);
            }

            if (vis && score >= 0) {
                v.setVisible(true);
                v.startAnimation(delay);
            } else {
                v.dispose();
            }

            if (debug) {
                saveText( "input-" + seed + ".txt", id.toString());
                saveText("output-" + seed + ".txt", od.toString());
            }

            class JsonInfo {
                public long seed;
                public long score;
            }

            JsonInfo info = new JsonInfo();
            info.seed = seed;
            info.score = score;

            System.out.println(getJsonString(info));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("{\"seed\":" + seed + ",\"score\":-1}");
            System.err.println("Failed to get result from your answer.");
        }
    }

    public static void main (String[] args) {

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

        // --delay option
        options.addOption(Option.builder("l")
            .required(false)
            .longOpt("delay")
            .hasArg(true)
            .type(Number.class)
            .argName("ms")
            .desc("frame delay time [ms].")
            .build());

        // --save option
        options.addOption(Option.builder("o")
            .required(false)
            .longOpt("save")
            .hasArg(false)
            .desc("output gif animation.")
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
            vis   = cmd.hasOption("vis");
            save  = cmd.hasOption("save");
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
