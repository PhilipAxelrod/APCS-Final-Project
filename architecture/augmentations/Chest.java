package architecture.augmentations;

import java.util.LinkedList;
import java.util.List;

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


    /**
     * Creates an empty Chest.
     */
    public Chest()
    {
        this( new LinkedList<Item>() );
    }


    /**
     * Creates a chest with randomly generated items.
     * 
     * @param level
     *            relative strength and quantity of items
     */
    public Chest( int level )
    {
        this();
        contents = Monster.generateItems( level );
    }


    /**
     * Creates a chest with a list of Items.
     * 
     * @param contents
     *            Items to add
     */
    public Chest( List<Item> contents )
    {
        this.contents = contents;
        topLeftCorner = new Point2D( 0, 0 );
    }


    /**
     * Creates an empty Chest.
     * 
     * @param loc
     *            Pont2D location
     * 
     */
    public Chest( Point2D loc )
    {
        this( new LinkedList<Item>(), loc );
    }


    /**
     * Creates a chest with randomly generated items.
     * 
     * @param level
     *            relative strength and quantity of items
     * @param loc
     *            Pont2D location
     * 
     */
    public Chest( int level, Point2D loc )
    {
        this( loc );
        contents = Monster.generateItems( level );
    }


    /**
     * Creates a chest with a list of Items.
     * 
     * @param contents
     *            Items to add
     * @param loc
     *            Pont2D location
     */
    public Chest( List<Item> contents, Point2D loc )
    {
        this.contents = contents;
        topLeftCorner = loc;
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

}
