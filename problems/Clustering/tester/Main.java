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
    static boolean save = false;
    static boolean vis  = false;
    static boolean debug = false;

    public Main ()
    {
        try {
            InputData  id = InputData.genInputData(seed);
            OutputData od = OutputData.runCommand(exec, id);

            double score = Checker.calcScore(id, od);
            System.out.println(getJsonString(id, od, score));

            if (debug) {
                saveText( "input-" + seed + ".txt", id.toString());
                saveText("output-" + seed + ".txt", od.toString());
            }

            if (!(save || vis) || score < 0) {
                System.exit(0);
            }

            try {
                Visualizer v = new Visualizer(id, od);
                if (save) v.saveImage(String.valueOf(seed));
                if (vis ) v.setVisible(true);
                else      v.dispose();
            }
            catch (Exception e) {
                e.printStackTrace();
                System.err.println("Visualization failed.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("{\"seed\":" + seed + ",\"score\":-1.0}");
            System.err.println("An exception occurred while running your program.");
        }
    }

    private String getJsonString (
        final InputData id,
        final OutputData od,
        final double _score)
        throws Exception
    {
        class JsonInfo {
            public long seed = Main.seed;
            public double score = _score;
            // public int N = id.N;
            // ...
        }

        JsonInfo info = new JsonInfo();
        ObjectMapper mapper = new ObjectMapper();
        String ret = mapper.writeValueAsString(info);
        return ret;
    }

    private void saveText (
        final String fileName,
        final String text)
        throws Exception
    {
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file);
        fw.write(text);
        fw.close();
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
            .desc("output the visualized result in png format.")
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
            save  = cmd.hasOption("save");
            vis   = cmd.hasOption("vis");
            debug = cmd.hasOption("debug");
            help  = cmd.hasOption("help");
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
