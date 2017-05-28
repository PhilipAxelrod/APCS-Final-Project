package JUnitTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import com.sun.javafx.geom.Point2D;
import org.junit.Test;

import architecture.augmentations.equipment.Chest;
import architecture.augmentations.Item;
import architecture.augmentations.consumables.NominalPotion;
import architecture.characters.Player;
import architecture.augmentations.weapons.Weapon;


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


    // TODO: remove
//    /**
//     * Tests the no args constructor.
//     */
//    @Test
//    public void cosntructorNoArgs()
//    {
//        Chest chest = new Chest();
//        assertTrue( chest.isEmpty() );
//        assertFalse( chest.isOpened() );
//    }


    /**
     * Tests the List of Item constructor.
     */
    @Test
    public void constructorListItem()
    {
        List<Item> items = getItems();
        Chest chest = new Chest( new Point2D(0, 0), items);

        assertFalse( chest.isEmpty() );
        assertEquals( items, chest.getContents() );
        assertTrue( chest.isOpened() );
        // TODO: uncomment
//        assertFalse( chest.acquireAll( new Armor( 1 ), player ); );
//        assertTrue( chest.acquireAll( items.get( 0 ), player ); );
//
//        assertTrue( chest.acquireAll( items.get( 1 ), player ); );
        assertTrue( chest.isEmpty() );
        
        // test acquire and get contents seperately and acquireall
    }

}
