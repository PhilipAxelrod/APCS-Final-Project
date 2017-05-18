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

import javax.swing.*;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import keyboardInputs.KeyEventDemo;


public class GraphicsInterface extends Canvas implements KeyListener, ActionListener

{
    //current sprite to be loaded
    private BufferedImage sprite;
    //graphics class, dictated from java.awt.graphics
    private Graphics graphic;
    //individual JFrame for the game
    private JFrame frame;
    //KeyEvent object
    KeyEvent e;
    // KeyPress Booleans. Pressed down=True, Not Pressed=False
    
    
    public boolean arUp=false, arRight=false, arLeft=false, arDown=false;
    
    public int currX=0, currY=0;

    //Create the GUI
    public GraphicsInterface()
    {
        
        //Fix the size of the JFrame
        setMinimumSize( new Dimension( 700, 700 ) );
        setMaximumSize( new Dimension( 700, 700 ) );
        setPreferredSize( new Dimension( 700, 700 ) );

        //Add components to the JFrame
        frame = new JFrame();        
        frame.setLayout( new BorderLayout() );        
        frame.add( this, BorderLayout.CENTER );        
        frame.pack();        
        frame.addKeyListener( this );        
        JPanel panel = new JPanel();
        frame.setResizable( false );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLocationRelativeTo( null );
        Container c = frame.getContentPane();
        c.add( panel );
        graphic = getGraphics();
        frame.setVisible( true );
        init();
    }


    private void init()
    {
        //Load the sprite into the BufferedImage, to paint.
        BufferedImage spriteSheet = null;
        //Try Catch if you can't find file
        try
        {
            spriteSheet = ImageUtils.loadBufferedImage( "Dirt_Floor.png" );
        }
        catch ( IOException ex )
        {
            Logger.getLogger( GraphicsInterface.class.getName() ).log( Level.SEVERE, null, ex );
        }

        SpriteSheet ss = new SpriteSheet( spriteSheet );

        // Get the sprite at said coordinates (x upper, y upper, x lower, y lower)
        sprite = ss.grabSprite( 0, 0, 48, 48 );
        paint( graphic );
        System.out.println( "graphics interface initGraphics() finished!" );

    }

    //Set the sprite to the BufferedImage
    public void setSprite( BufferedImage newSprite )
    {
        this.sprite = newSprite;
    }

    //Load the Sprite into the sprite sheet
    public void loadSprite( String absolutePath )
    {
        try
        {
            this.sprite = ImageUtils.loadBufferedImage( absolutePath );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "image loading failed" );
        }
    }

    //draw the image onto the JFrame
    public void paint( BufferedImage sprite, int x, int y, int width, int height, Graphics g )
    {
        g.drawImage( sprite, x, y, width, height, frame );

    }


    /**
     * TODO: take image as parameter
     * 
     * @param width
     * @param height
     * @param blockSize
     *            the length in pixels of each individual block
     * @param xTopLeftCorner
     * @param yTopLeftConer
     */
    public void drawFloor( int width, int height, int blockSize)
    {
        for ( int row = 0; row < height; row++ )
        {
            for ( int col = 0; col < width; col++ )
            {
                paint( sprite, 100 * row, 100 * col, blockSize, blockSize, graphic );
            }
        }
        System.out.println( "Draw floor finished!" );
    }


    public static void main( String[] args )
    {
        GraphicsInterface graphicsInterface = new GraphicsInterface();
        try
        {
            graphicsInterface.setSprite( ImageUtils.loadBufferedImage( "Dirt_Floor.png" ) );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "Image failed to load" );
        }
        graphicsInterface.drawFloor( 100, 100, 100 );
        
  
    }

    //change the booleans based on KeyEvents
    @Override
    public void keyPressed( KeyEvent e )
    {
        int keyCode = e.getKeyCode();
        if ( KeyEvent.getKeyText( keyCode ).equals( "Left" ) )
        {
            currX-=100;
        }
        if ( KeyEvent.getKeyText( keyCode ).equals( "Up" ) )
        {
            currY-=100;
        }
        if ( KeyEvent.getKeyText( keyCode ).equals( "Right" ) )
        {
            currX+=100; 
        }
        if ( KeyEvent.getKeyText( keyCode ).equals( "Down" ) )
        {
            currY+=100; 
        }
        movePiece();
    }

    //change the booleans based on KeyEvents
    @Override
    public void keyReleased( KeyEvent e )
    {
        int keyCode = e.getKeyCode();
        if ( KeyEvent.getKeyText( keyCode ).equals( "Left" ) )
        {
            arLeft = false;

        }
        if ( KeyEvent.getKeyText( keyCode ).equals( "Up" ) )
        {
            arUp = false;

        }
        if ( KeyEvent.getKeyText( keyCode ).equals( "Right" ) )
        {
            arRight = false;

        }
        if ( KeyEvent.getKeyText( keyCode ).equals( "Down" ) )
        {
            arDown = false;

        }

    }


    @Override
    public void keyTyped( KeyEvent arg0 )
    {
        // TODO Auto-generated method stub

    }



    @Override
    public void actionPerformed( ActionEvent arg0 )
    {
        // TODO Auto-generated method stub
    }

    public void movePiece ()
    {
        if (currX<0)
        {
            currX=0;
        }
        if (currY<0)
        {
            currY=0;
        }
        if (currX>700)
        {
            currX=700;
        }
        if (currY>700)
        {
            currY=700;
        }
        try
        {
            this.setSprite( ImageUtils.loadBufferedImage( "Dirt_Floor.png" ) );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "Image failed to load" );
        }
        drawFloor( 100, 100, 100 );
        try
        {
            this.setSprite( ImageUtils.loadBufferedImage( "ConcretePowderMagenta.png" ) );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "Image failed to load" );
        }
        paint(sprite, currX, currY, 100, 100, graphic);
        System.out.println( currX+" "+currY );               
     }
     
}
