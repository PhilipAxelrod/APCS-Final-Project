package graphicsUtils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import architecture.Combatant;
import architecture.GameState;
import proceduralGeneration.Cell;


public class GraphicsInterface extends JPanel implements KeyListener, ActionListener
{
    private GameState gameState;
    //current sprite to be loaded
    private BufferedImage sprite;
    //graphics class, dictated from java.awt.graphics
    private Graphics graphic;
    //individual JFrame for the game
    private JFrame frame;
    //KeyEvent object
    KeyEvent e;
    // KeyPress Booleans. Pressed down=True, Not Pressed=False
    
    
    public boolean arUp = false, arRight=false, arLeft=false, arDown=false;

    //Create the GUI
    public GraphicsInterface()
    {
        setFocusable(false);
        //Fix the size of the JFrame
        setMinimumSize( new Dimension( 1400, 1400 ) );
        setPreferredSize( new Dimension( 1400, 1400 ) );
        //Add components to the JFrame
        frame = new JFrame();
        Container contentPaine = frame.getContentPane();
        frame.setLayout( new BorderLayout() );        
        frame.getContentPane().add( this, BorderLayout.CENTER );
        frame.pack();        
        frame.addKeyListener( this );        
        frame.setResizable( false );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLocationRelativeTo( null );
//        graphic = getGraphics();
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
     */
    public void drawFloor(int width, int height, int blockSize)
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

    public void drawFloor_1(int startX, int startY, int width, int height, int blockSize) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                paint(  sprite,
                        startX + blockSize * row,
                        startY + blockSize * col,
                        blockSize,
                        blockSize,
                        graphic );
            }
        }
    }


    public static void main( String[] args )
    {
        GraphicsInterface graphicsInterface = new GraphicsInterface();
        graphicsInterface.drawFloor( 100, 100, 100 );
        
  
    }

    //change the booleans based on KeyEvents
    @Override
    public void keyPressed( KeyEvent e )
    {
        int keyCode = e.getKeyCode();
        if ( KeyEvent.getKeyText( keyCode ).equals( "Left" ) )
        {
            arLeft = true;
        }
        if ( KeyEvent.getKeyText( keyCode ).equals( "Up" ) )
        {
            arUp = true;
        }
        if ( KeyEvent.getKeyText( keyCode ).equals( "Right" ) )
        {
            arRight = true;
        }
        if ( KeyEvent.getKeyText( keyCode ).equals( "Down" ) )
        {
            arDown = true;
        }
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

    public void render( Cell[][] cell)
    {
        loadSprite("Dirt_Floor.png");

        int side = 100;

        for ( int i = 0; i < cell.length; i++ )
        {
            for ( int j = 0; j < cell[0].length; j++ )
            {
                if(cell[i][j].isAlive()) {
                    drawFloor_1( i * side, j * side, 1, 1, side );
                }
            }
        }
    }

    public void renderGrid(Cell[][] cells)
    {
        render(cells);
//        System.out.println( currX+" "+currY );
     }

    public void renderCharacter(Combatant combatant) {
        loadSprite("ConcretePowderMagenta.png");

        drawFloor_1(
                (int) combatant.getPose().x,
                (int) combatant.getPose().y,
                1,
                1,
                combatant.WIDTH
        );

    }

     public void clearGrid() {
         graphic.clearRect(0, 0, frame.getWidth(), frame.getHeight());
     }

     public void setGameState(GameState gameState) {
        this.gameState = gameState;
     }

     @Override
     public void paint(Graphics g) {
        // TODO: this.graphic = g; MUST MUST MUST BE CALLED BEFORE super.paint(g);
         this.graphic = g;
         super.paint(g);
//         System.out.println("in repaint");
        if (gameState != null) {
            renderGrid(gameState.cells);
            gameState.combatants.forEach(this::renderCharacter);
        }
     }

     public void clear() {
         graphic.clearRect(0, 0, frame.getWidth(), frame.getHeight());
     }


     public void doRepaint() {
        frame.getContentPane().repaint();
     }

     public void requestFocus() {
        frame.requestFocus();
     }

//     public void addCharacter()
}
