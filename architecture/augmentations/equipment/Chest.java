package architecture.augmentations.equipment;

import java.util.LinkedList;
import java.util.List;

import architecture.augmentations.Armor;
import architecture.augmentations.Item;
import architecture.augmentations.Message;
import architecture.augmentations.consumables.Potion;
import architecture.augmentations.weapons.Weapon;
import architecture.characters.Monster;
import architecture.characters.Player;
import com.sun.javafx.geom.Point2D;


/**
 * A container for items which can be looted by the player.
 *
 * @author Kevin Liu
 * @version May 11, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class Chest
{
    List<Item> contents;

    boolean isOpened = false;

    /**
     * the location of the Chest
     */
    protected Point2D topLeftCorner;

    /**
     * width of Chest
     */
    public static final int WIDTH = 99;

    /**
     * height of Chest
     */
    public static final int HEIGHT = WIDTH;

    private List<Message> messages;


    /**
     * Creates a chest with randomly generated items. used
     * 
     * @param level
     *            relative strength and quantity of items
     * @param loc
     *            Pont2D location
     * 
     */
    public Chest( int level, Point2D loc )
    {
        this.contents = Monster.generateItems( level );
        topLeftCorner = loc;
        messages = generateMessages( contents, loc );
    }


    public Chest( Point2D loc, List<Item> contents )
    {
        this.contents = contents;
        topLeftCorner = loc;
        messages = generateMessages( contents, loc );

    }


    private static List<Message> generateMessages(
        List<Item> items,
        Point2D loc )
    {
        List<Message> messages = new LinkedList<Message>();
        // for ( Item item : items )
        // messages.add( new Message( item, loc ) );
        for ( int i = 0; i < items.size(); i++ )
        {
            Point2D temp = new Point2D( loc.x, loc.y - 30 * i );
            messages.add( new Message( items.get( i ), temp ) );
        }
        return messages;
    }


    /**
     * Gets the contents of the chest.
     * 
     * @return a List<Item> of the chests's contents
     */
    public List<Item> getContents()
    {
        return contents;
    }


    /**
     * Adds all items to Player's inventory.
     * 
     * @param player
     *            the player
     */
    public void acquireAll( Player player )
    {
        player.acquireAll( contents );
        contents.clear();
    }


    /**
     * Checks if chest has contents by calling the isEmpty() method of contents.
     * 
     * @return whether or not contents is empty
     */
    public boolean isEmpty()
    {
        return contents.isEmpty();
    }


    /**
     * Checks whether or not the contents of the Chest has been accessed.
     * 
     * @return whether or not the contents of the Chest has been accessed.
     */
    public boolean isOpened()
    {
        return isOpened;
    }


    public Point2D getPose()
    {
        return topLeftCorner;
    }


    protected Point2D bottomRightCorner()
    {
        return new Point2D( topLeftCorner.x + WIDTH, topLeftCorner.y + HEIGHT );
    }


    /**
     * @return Returns the messages.
     */
    public List<Message> getMessages()
    {
        return messages;
    }

}
