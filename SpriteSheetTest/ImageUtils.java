package SpriteSheetTest;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class ImageUtils
{
    public static BufferedImage loadBufferedImage(String absolutePath) throws IOException
    {
        return ImageIO.read(new File(absolutePath) );
    }
}
