package graphicsUtils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import keyboardInputs.KeyEventDemo;

public class GraphicsInterface extends Canvas implements KeyListener,
ActionListener
{
    private BufferedImage sprite;

    private Graphics graphic;

    private JFrame frame;
    
    
    KeyEvent e;
    
    public boolean arUp, arRight, arLeft, arDown;
    
    KeyEventDemo testKey;
    
    public GraphicsInterface()
    {
        setMinimumSize( new Dimension(700,700) );
        setMaximumSize( new Dimension(700,700) );
        setPreferredSize( new Dimension(700,700) );

        frame = new JFrame();
        frame.setLayout( new BorderLayout() );
        frame.add( this, BorderLayout.CENTER );
        frame.pack();
        frame.addKeyListener( this );
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
        
        BufferedImage spriteSheet = null;
        try
        {
            spriteSheet = ImageUtils.loadBufferedImage("Dirt_Floor.png");
        }
        catch (IOException ex)
        {
            Logger.getLogger(
                    GraphicsInterface.class.getName() ).log(Level.SEVERE,
                    null,
                    ex);
        }

        SpriteSheet ss = new SpriteSheet(spriteSheet);

        // TODO: take as constructor paramter
        sprite = ss.grabSprite( 0, 0, 48, 48 );
        paint(graphic);
        System.out.println("graphics interface initGraphics() finished!");
        
    }

    public void setSprite(BufferedImage newSprite) {
        this.sprite = newSprite;
    }

    public void loadSprite(String absolutePath) {
        try {
            this.sprite = ImageUtils.loadBufferedImage(absolutePath);
        } catch (IOException e) {
            throw new RuntimeException("image loadig failed");
        }
    }
    
    public void paint(BufferedImage sprite, int x, int y, int width, int height, Graphics g)
    {       
        g.drawImage(sprite, x, y, width, height, frame);
 
    }

    /**
     * TODO: take image as parameter
     * @param width
     * @param height
     * @param blockSize the length in pixels of each individual block
     * @param xTopLeftCorner
     * @param yTopLeftConer
     */
    public void drawFloor(int width, int height, int blockSize, int xTopLeftCorner, int yTopLeftConer)
    {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                paint(
                    sprite,
                    xTopLeftCorner + blockSize * row,
                    yTopLeftConer + blockSize * col,
                    blockSize,
                    blockSize,
                    graphic);
            }
        }
        System.out.println("Draw floor finished!");
    }
    
    

    public static void main (String[] args)
    {
        GraphicsInterface graphicsInterface = new GraphicsInterface();
        try {
            graphicsInterface.setSprite(
                    ImageUtils.loadBufferedImage("Dirt_Floor.png"));
        } catch (IOException e) {
            throw new RuntimeException("Image failed to load");
        }
        graphicsInterface.drawFloor(1, 1, 100, 0, 0);

    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed( KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        if (KeyEvent.getKeyText(keyCode).equals( "Left" ))
        {
            arLeft=true;
            System.out.println( "leftPress" );
        }
        if (KeyEvent.getKeyText(keyCode).equals( "Up" ))
        {
            arUp=true;
            System.out.println( "upPress" );
        }
        if (KeyEvent.getKeyText(keyCode).equals( "Right" ))
        {
            arRight=true;
            System.out.println( "rightPress" );
        }
        if (KeyEvent.getKeyText(keyCode).equals( "Down" ))
        {
            arDown=true;
            System.out.println( "down" );
        }
        
    }

    @Override
    public void keyReleased( KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        if (KeyEvent.getKeyText(keyCode).equals( "Left" ))
        {
            arLeft=false;
        }
        if (KeyEvent.getKeyText(keyCode).equals( "Up" ))
        {
            arUp=false;
        }
        if (KeyEvent.getKeyText(keyCode).equals( "Right" ))
        {
            arRight=false;
        }
        if (KeyEvent.getKeyText(keyCode).equals( "Down" ))
        {
            arDown=false;
        }
        
    }

    @Override
    public void keyTyped( KeyEvent arg0 )
    {
        // TODO Auto-generated method stub
        
    }
}
