import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.imageio.ImageWriter;
import javax.imageio.ImageIO;
import javax.imageio.IIOImage;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.util.Iterator;

public class Visualizer extends JFrame
{
    private View view;
    private Dimension size;

    public Visualizer (
        final InputData id,
        final OutputData od)
    {
        view = new View(id, od);
        size = new Dimension(View.VIEW_SIZE_X, View.VIEW_SIZE_Y);
        this.getContentPane().add(view);
        this.getContentPane().setPreferredSize(size);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle(Main.title);
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

    public void saveAnimation (
        final String fileName,
        final long delay)
    {
        try {
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
            System.err.println("Visualizer failed to save the gif animation.");
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
}
