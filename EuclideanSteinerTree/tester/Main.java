import java.io.File;
import java.io.FileWriter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main
{
    static String seed = "";
    static String exec = "";
    static boolean save = false;
    static boolean vis  = false;
    static boolean debug = false;

    private String getJsonString (Tester tester)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String ret = mapper.writeValueAsString(tester);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("JSON generation failed.");
            return "";
        }
    }

    private void saveText (String fileName, String text)
    {
        try {
            File file = new File(fileName);
            FileWriter fw = new FileWriter(file);
            fw.write(text);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to save file " + fileName + ".");
        }
    }

    private Main ()
    {
        try {
            Tester tester = new Tester(Long.parseLong(seed), exec);
            if ((vis || save) && tester.getScore() >= 0) {
                Visualizer v = new Visualizer(tester);
                if (save) v.saveImage(seed);
                if (vis ) v.visualize();
            }
            if (debug) {
                saveText( "input-" + seed + ".txt", tester.getInputString());
                saveText("output-" + seed + ".txt", tester.getOutputString());
            }
            System.out.println(getJsonString(tester));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("{\"seed\":" + seed + ",\"score\":-1.0}");
            System.err.println("Failed to get result from your answer.");
        }
    }

    public static void main (String[] args)
    {
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals("-seed")) {
                seed = args[++i];
            } else if (args[i].equals("-exec")) {
                exec = args[++i];
            } else if (args[i].equals("-vis")) {
                vis = true;
            } else if (args[i].equals("-save")) {
                save = true;
            } else if (args[i].equals("-debug")) {
                debug = true;
            }
        }
        Main m = new Main();
    }
}
