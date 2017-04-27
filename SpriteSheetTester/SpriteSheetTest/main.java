package SpriteSheetTester.SpriteSheetTest;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import java.io.IOException;

import javax.swing.JFrame;

public class main extends JFrame
{
    BufferedImage sprite;
    
    public main()
    {
        setSize(800,600);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
    }
    private void init()
    {
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage spriteSheet = null;
        try
        {
            spriteSheet = loader.loadImage("Dirt_Floor.png");
        }
        catch (IOException ex)
        {
            Logger.getLogger( main.class.getName() ).log(Level.SEVERE, null,ex);
        }
        SpriteSheet ss = new SpriteSheet(spriteSheet);
        sprite = ss.grabSprite( 0, 0, 47, 47 );
    }
    public void paint(Graphics g)
    {
        g.drawImage(sprite, 100, 100, 100, 100, null);
        g.drawImage(sprite, 200, 100, 100, 100, null);
        g.drawImage(sprite, 300, 100, 100, 100, null);
        g.drawImage(sprite, 100, 200, 100, 100, null);
        g.drawImage(sprite, 100, 300, 100, 100, null);
        g.drawImage(sprite, 200, 200, 100, 100, null);
        g.drawImage(sprite, 200, 300, 100, 100, null);
        g.drawImage(sprite, 300, 200, 100, 100, null);
        g.drawImage(sprite, 300, 300, 100, 100, null);
        repaint();
    }
    public static void main(String[] args)
    {
        main main = new main();
    }
}
