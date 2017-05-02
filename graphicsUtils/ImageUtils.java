package graphicsUtils;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

public class ImageUtils
{
    public static BufferedImage loadBufferedImage(String absolutePath) throws IOException
    {
        return ImageIO.read(new File(absolutePath) );
    }
}
