package architecture;

import java.util.LinkedList;
import java.util.List;


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
     * Creates an empty Chest.
     */
    public Chest()
    {
        this.contents = new LinkedList<Item>();
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
    }


    /**
     * Gets the contents of the chest.
     * 
     * @return a List<Item> of the chests's contents
     */
    public List<Item> getContents()
    {
        isOpened = true;
        return contents;
    }


    /**
     * Adds the Item, item, to Player's inventory, if item is present.
     * 
     * @param item
     *            item to add
     * @param player
     *            the player
     * @return true if successful, false if unsuccessful
     */
    public boolean acquire( Item item, Player player )
    {
        if ( contents.remove( item ) )
        {
            player.acquire( item );
            return true;
        }
        return false;
    }


    /**
     * Adds all items to Player's inventory.
     * 
     * @param player
     *            the player
     */
    public void acquireAll( Player player )
    {
        player.acquire( contents );
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

}
