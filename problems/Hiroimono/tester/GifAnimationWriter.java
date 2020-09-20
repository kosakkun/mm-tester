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

public class GifAnimationWriter
{
    private ImageWriter writer;
    private ImageOutputStream stream;

    public GifAnimationWriter (
        final String fileName)
        throws Exception
    {
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("gif");
        writer = it.next();
        stream = ImageIO.createImageOutputStream(new File(fileName + ".gif"));
        writer.setOutput(stream);
        writer.prepareWriteSequence(null);
    }

    public void close () throws Exception
    {
        writer.endWriteSequence();
        stream.close();
    }

    public void addFrame (
        final BufferedImage frame,
        final long delay)
        throws Exception
    {
        IIOMetadata metadata = getMetadata(delay, writer, frame);
        writer.writeToSequence(new IIOImage(frame, null, metadata), null);
    }

    private IIOMetadata getMetadata (
        final long delay,
        final ImageWriter writer,
        final BufferedImage frame)
        throws Exception
    {
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(frame), iwp);
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
