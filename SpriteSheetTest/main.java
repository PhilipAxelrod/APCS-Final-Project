package SpriteSheetTest;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class main extends Canvas
{
    BufferedImage sprite;
   
    Graphics graphic;
    
    JFrame frame = new JFrame();
    
    public main()
    {
        setMinimumSize( new Dimension(700,700) );
        setMaximumSize( new Dimension(700,700) );
        setPreferredSize( new Dimension(700,700) );
        frame.setLayout( new BorderLayout() );
        frame.add( this, BorderLayout.CENTER );
        frame.pack();
        
        JPanel panel = new JPanel();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);        
        Container c = frame.getContentPane();
        c.add(panel);
        graphic = getGraphics();
        frame.setVisible(true);
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
        sprite = ss.grabSprite( 0, 0, 48, 48 );
        paint(graphic);
        System.out.println("testGarbage2");

    }
    
    public void paint(BufferedImage sprite, int x, int y, int width, int height, Graphics g)
    {       
        g.drawImage(sprite, x, y, width, height, null);
    
    }
    public void drawFloor(int width, int height, Graphics g)
    {
        int hcount=height;
        int vcount=width;
        while(hcount>=0)
        {
            vcount=5;
            while(vcount>=0)
            {
                paint(sprite, 100*hcount, 100*vcount, 100, 100, null);
                
                vcount--;
            }
            hcount--;
        }
        System.out.println("testGarbage");
    }

    public static void main (String[] args)
    {
        new main();
        
    }
}
