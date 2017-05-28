package graphicsUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.*;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.javafx.geom.Point2D;

import architecture.augmentations.Chest;
import architecture.augmentations.Monster;
import architecture.characters.CombatResult;
import architecture.characters.Combatant;
import architecture.GameState;
import architecture.augmentations.weapons.Weapon;
import proceduralGeneration.Cell;


public class GraphicsInterface extends JPanel
                implements
                KeyListener,
                ActionListener
{
    private GameState gameState;

    // current sprite to be loaded
    private BufferedImage sprite;

    // graphics class, dictated from java.awt.graphics

    // individual JFrame for the game
    private JFrame frame;

    // KeyEvent object
    KeyEvent e;
    // KeyPress Booleans. Pressed down=True, Not Pressed=False

    private boolean arUp, arRight, arLeft, arDown, qKey, eKey = false;


    public boolean isArUp()
    {
        return arUp;
    }


    public boolean isArRight()
    {
        return arRight;
    }


    public boolean isArLeft()
    {
        return arLeft;
    }


    public boolean isArDown()
    {
        return arDown;
    }


    public boolean isQPressed()
    {
        return qKey;
    }


    // Create the GUI
    public GraphicsInterface()
    {
        setFocusable( false );
        // Fix the size of the JFrame
        setMinimumSize( new Dimension( 1400, 1400 ) );
        setPreferredSize( new Dimension( 1400, 1400 ) );
        // Add components to the JFrame
        frame = new JFrame();
        Container contentPaine = frame.getContentPane();
        frame.setLayout( new BorderLayout() );
        frame.getContentPane().add( this, BorderLayout.CENTER );
        frame.pack();
        frame.addKeyListener( this );
        frame.setResizable( false );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLocationRelativeTo( null );
        // graphic = getGraphics();
        frame.setVisible( true );
        init( frame.getGraphics() );
    }


    private void init( Graphics g )
    {
        // Load the sprite into the BufferedImage, to placeImage.
        BufferedImage spriteSheet = null;
        // Try Catch if you can't find file
        try
        {
            spriteSheet = ImageUtils.loadBufferedImage( "Dirt_Floor.png" );
        }
        catch ( IOException ex )
        {
            Logger.getLogger( GraphicsInterface.class.getName() )
                .log( Level.SEVERE, null, ex );
        }

        System.out.println( "graphics interface initGraphics() finished!" );

    }


    // Set the sprite to the BufferedImage
    public void setSprite( BufferedImage newSprite )
    {
        this.sprite = newSprite;
    }


    // Load the Sprite into the sprite sheet
    public void loadSprite( String absolutePath )
    {
        try
        {
            this.globalImage = ImageUtils.loadBufferedImage( absolutePath );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "image loading failed" );
        }
    }


    public BufferedImage getSprite( String absolutePath )
    {
        try
        {
            return ImageUtils.loadBufferedImage( absolutePath );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "image loading failed" );
        }
    }

    BufferedImage globalImage;


    // draw the image onto the JFrame
    public void placeImage(
        BufferedImage sprite,
        int x,
        int y,
        int width,
        int height,
        Graphics g )
    {
        g.drawImage( sprite, x, y, width, height, frame );
    }


    public void placeImage(
        String pathToImage,
        int startX,
        int startY,
        int width,
        int height,
        Graphics g )
    {
        // if (globalImage.getPropertyNames())
        placeImage( /* getSprite(pathToImage) */globalImage,
            startX,
            startY,
            width,
            height,
            g );
    }


    // change the booleans based on KeyEvents
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
        if ( KeyEvent.getKeyText( keyCode ).equals( "Q" ) )
        {
            qKey = true;
        }
        if ( KeyEvent.getKeyText( keyCode ).equals( "E" ) )
        {
            eKey = true;
        }
    }


    // change the booleans based on KeyEvents
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
        if ( KeyEvent.getKeyText( keyCode ).equals( "Q" ) )
        {
            qKey = false;
        }
        if ( KeyEvent.getKeyText( keyCode ).equals( "E" ) )
        {
            eKey = false;
        }

    }


    public boolean eKey()
    {
        return eKey;
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


    public void renderGrid( Cell[][] cells, int cellLength, Graphics graphics )
    {

        loadSprite( "Dirt_Floor.png" );
        for ( int i = 0; i < cells.length; i++ )
        {
            for ( int j = 0; j < cells[0].length; j++ )
            {
                if ( cells[i][j].isAlive() )
                {
                    placeImage( "Dirt_Floor.png",
                        i * cellLength,
                        j * cellLength,
                        cellLength,
                        cellLength,
                        graphics );
                }
            }
        }
    }


    public void renderCharacter( Combatant combatant, Graphics g )
    {
        loadSprite( "ConcretePowderMagenta.png" );
        placeImage( "ConcretePowderMagenta.png",
            (int)combatant.getPose().x,
            (int)combatant.getPose().y,
            combatant.WIDTH,
            combatant.HEIGHT,
            g );

    }


    public void renderCombatResult( CombatResult result, Graphics g )
    {
        if ( result == null )
            return;
        Point2D loc = result.getDefender().getPose();

        if ( result.isHit() )
        {
            if ( result.isCritical() )
                g.drawString( "Critical Hit!", (int)loc.x, (int)loc.y );
            g.drawString( "-" + result.getDamage(), (int)loc.x, (int)loc.y );
        }
        else
            g.drawString( "Miss!", (int)loc.x, (int)loc.y );
    }


    public void renderPortal( Rectangle portal, int cellLength, Graphics g )
    {
        loadSprite( "portal.png" );

        placeImage( "portal.png",
            portal.x,
            portal.y,
            portal.width,
            portal.height,
            g );
    }


    public void renderWeapon( Weapon weapon, Combatant combatant, Graphics g )
    {
        // if ( weapon.getType()[0] == 0 )
        {
            try
            {
                globalImage = ImageUtils.loadBufferedImage( "portal.png" );
            }
            catch ( Exception e )
            {
                throw new RuntimeException( "yay, loading failed" );
            }

            loadSprite( "default_sword.png" );

            // TODO: Remove hard coding of weapon size
            placeImage( "default_sword.png",
                (int)( combatant.getPose().x + 2D / 3 * combatant.WIDTH ),
                (int)( combatant.getPose().y + 2D / 3 * combatant.HEIGHT ),
                100,
                100,
                g );
        }
        // else
        {
            // throw new NotImplementedException();
        }
    }


    public void renderChest( Chest chest, Graphics g )
    {
        Point2D loc = chest.getPose();
        loadSprite( "Chest.png" );
        placeImage( "Chest.png",
            (int)loc.x,
            (int)loc.y,
            chest.WIDTH,
            chest.HEIGHT,
            g );
    }


    public void setGameState( GameState gameState )
    {
        this.gameState = gameState;
    }


    public void doRepaint()
    {
        frame.getContentPane().repaint();
    }


    // double lastTranslationTime = System.currentTimeMillis() / 1000D;
    @Override
    public void paint( Graphics g )
    {
        super.paint( g );

        double lastTime = System.currentTimeMillis() / 1000D;
        if ( gameState != null )
        {
            // ensure player is always in the center
            g.translate(
                -(int)gameState.player.getPose().x + frame.getWidth() / 2,
                -(int)gameState.player.getPose().y + frame.getHeight() / 2 );

            renderGrid( gameState.cells, gameState.cellLength, g );

            gameState.chests.forEach( chest -> renderChest( chest, g ) );
            gameState.monsters.forEach( monster -> monster.render( this, g ) );
            gameState.monsters
                .forEach( monster -> renderCombatResult( monster.result, g ) );
            gameState.player.render( this, g );
            renderCombatResult( gameState.player.result, g );

            renderWeapon( gameState.player.getWeapon(), gameState.player, g );

            renderPortal( gameState.portal, gameState.cellLength, g );

            for ( Chest chest : gameState.chests )
            {
                if ( !chest.isEmpty() )
                    renderChest( chest, g );
                else
                    gameState.chests.remove( chest );
            }
            renderCombatResult( null, g );
        }

        double currTime = System.currentTimeMillis() / 1000D;
        // System.out.println("drawing took: " + (currTime - lastTime));
        lastTime = currTime;
    }


    public void requestFocus()
    {
        frame.requestFocus();
    }
}
