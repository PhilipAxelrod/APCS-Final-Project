package SpriteSheetTester.SpriteSheetTest;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;

import javax.imageio.ImageIO;
public class BufferedImageLoader
{

    public BufferedImage loadImage(String pathRelativeToThis) throws IOException
    {
        URL url = this.getClass().getResource( pathRelativeToThis );
        BufferedImage img = ImageIO.read( url );
        return img;
    }
    
}
