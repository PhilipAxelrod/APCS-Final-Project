package architecture.augmentations;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.TimerTask;

import com.sun.javafx.geom.Point2D;

import architecture.augmentations.consumables.Potion;
import architecture.augmentations.weapons.Weapon;
import architecture.characters.Renderable;
import graphicsUtils.GraphicsInterface;


/**
 * A Message is a temporary sign, used to inform the user of the acquisition of
 * power ups.
 *
 * @author Kevin Liu
 * @version May 27, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class Message extends TimerTask implements Renderable
{

    private static final int DURATION = 1000;

    private int elapsed = 0;

    private boolean expired = false;

    private String message;

    private Point2D pos;


    public Message( Item item, Point2D pos )
    {
        this.pos = pos;
        if ( item instanceof Potion )
            message = "Health Restored!";
        else if ( item instanceof Weapon )
            message = "Attack Increased!";
        else if ( item instanceof Armor )
            message = "Defense Increased!";
    }


    @Override
    public void render( GraphicsInterface graphicsInterface, Graphics g )
    {
        g.setColor( Color.WHITE );
        g.setFont( new Font( "Arial Italic", 0, 20 ) );
        g.drawString( message, (int)pos.x, (int)pos.y );
    }


    @Override
    public void run()
    {
        if ( elapsed < DURATION )
            elapsed++;
        else if ( !expired )
            expired = true;
    }


    /**
     * @return Returns the expired.
     */
    public boolean isExpired()
    {
        return expired;
    }


    /**
     * @return Returns the message.
     */
    public String getMessage()
    {
        return message;
    }


    /**
     * @return Returns the pos.
     */
    public Point2D getPos()
    {
        return pos;
    }

}
