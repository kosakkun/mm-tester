import java.io.File;
import java.io.FileWriter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main
{
    static String seed = "";
    static String exec = "";
    static long delay = 100;
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
            if (debug) {
                saveText( "input-" + seed + ".txt", tester.getInputString());
                saveText("output-" + seed + ".txt", tester.getOutputString());
            }
            if (vis) {
                tester.initPuzzle();
                Visualizer v = new Visualizer(tester);
                v.visualize();
                Thread.sleep(2000);
                while (tester.nextPuzzle()) {
                    v.repaint();
                    Thread.sleep(delay);
                }
            } else {
                tester.initPuzzle();
                while (tester.nextPuzzle());
            }
            System.out.println(getJsonString(tester));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("{\"seed\":" + seed + ",\"score\":-1}");
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
            } else if (args[i].equals("-debug")) {
                debug = true;
            } else if (args[i].equals("-delay")) {
                delay = Long.parseLong(args[++i]);   
            }
        }
        Main m = new Main();
    }
}
