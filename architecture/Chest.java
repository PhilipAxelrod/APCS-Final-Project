package architecture;

import java.util.ArrayList;


public class Chest extends ArrayList<Item>
{

    boolean isOpened = false;


    /**
     * Creates an empty Chest.
     */
    public Chest()
    {
        new Chest( null );
    }


    /**
     * Creates a chest with a list of Items.
     * 
     * @param items
     *            Items to add
     */
    public Chest( ArrayList<Item> items )
    {
        this.addAll( items );
    }


    public Item get( int index )
    {
        isOpened = true;
        return super.get( index );
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
