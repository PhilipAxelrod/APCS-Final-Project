package SpriteSheetTest;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class BufferedImageLoader
{

    public BufferedImage loadImage(String pathRelativeToThis) throws IOException
    {
        String path = pathRelativeToThis;
        BufferedImage img = ImageIO.read(new File("Dirt_Floor.png") );
        return img;
    }
    
}
