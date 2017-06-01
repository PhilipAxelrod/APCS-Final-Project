package JUnitTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import com.sun.javafx.geom.Point2D;
import org.junit.Test;

import architecture.augmentations.equipment.Chest;
import architecture.augmentations.equipment.Weapon;
import architecture.augmentations.Item;
import architecture.augmentations.consumables.NominalPotion;
import architecture.characters.Player;


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
    private Player player = new Player(null);
    private static final int LEVEL = 1;
    private static final Point2D loc = new Point2D( 0, 0 );


    private static List<Item> getItems()
    {
        List<Item> items = new LinkedList<Item>();
        items.add( new Weapon( 1 ) );
        items.add( new NominalPotion( 1 ) );
        return items;
    }

    @Test
    public void constructor()
    {
        Chest chest = new Chest(LEVEL, Point2D loc);
    }

}
