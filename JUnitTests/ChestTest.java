package JUnitTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import architecture.Armor;
import architecture.Chest;
import architecture.Item;
import architecture.NominalPotion;
import architecture.Player;
import architecture.Weapon;


/**
 * TODO Write a one-sentence summary of your class here. TODO Follow it with
 * additional details about its purpose, what abstraction it represents, and how
 * to use it.
 *
 * @author Kevin Liu
 * @version May 19, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: TODO
 */
public class ChestTest
{
    Player player = new Player();


    private static List<Item> getItems()
    {
        List<Item> items = new LinkedList<Item>();
        items.add( new Weapon( 1 ) );
        items.add( new NominalPotion( 1 ) );
        return items;
    }


    /**
     * Tests the no args constructor.
     */
    @Test
    public void cosntructorNoArgs()
    {
        Chest chest = new Chest();
        assertTrue( chest.isEmpty() );
        assertFalse( chest.isOpened() );
    }


    /**
     * Tests the List of Item constructor.
     */
    @Test
    public void constructorListItem()
    {
        List<Item> items = getItems();
        Chest chest = new Chest( items );

        assertFalse( chest.isEmpty() );
        assertEquals( items, chest.getContents() );
        assertTrue( chest.isOpened() );
        assertFalse( chest.acquire( new Armor( 1 ), player ) );
        assertTrue( chest.acquire( items.get( 0 ), player ) );

        assertTrue( chest.acquire( items.get( 1 ), player ) );
        assertTrue( chest.isEmpty() );
        
        // test acquire and get contents seperately and acquireall
    }

}