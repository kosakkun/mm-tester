import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Visualizer extends JFrame
{
    private View view;

    public Visualizer (
        final InputData id,
        final OutputData od)
    {
        view = new View(id, od);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        layout.setConstraints(view, gbc);

        this.setLayout(layout);
        this.getContentPane().add(view);
        this.pack();
        this.setResizable(false);
        this.setTitle(Main.title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startAnimation (
        final long delay)
        throws Exception
    {
        while (true) {
            view.initState();
            this.repaint();
            Thread.sleep(1000);
            while (view.nextState()) {
                this.repaint();
                Thread.sleep(delay);
            }
            Thread.sleep(1000);
        }
    }
}
