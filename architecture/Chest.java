package architecture;

import java.util.LinkedList;
import java.util.List;


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
     * @param items
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
     * @return true if successful, false if unsuccessful
     */
    public boolean acquire( Item item )
    {
        if ( contents.remove( item ) )
        {
            // Player.acquire(item);
            return true;
        }
        return false;
    }


    /**
     * Adds all items to Player's inventory.
     */
    public void acquireAll()
    {
        for ( Item item : contents )
        { // Player.acquire(item);
        }
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
