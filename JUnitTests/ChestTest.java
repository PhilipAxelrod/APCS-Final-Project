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
 * Test chests.
 *
 * @author Kevin Liu
 * @author Period: 5
 * @author Assignment: APCS Final
 * @author Sources: none
 * @version May 19, 2017
 */
public class ChestTest {
    private Player player = new Player(null);
    private static final int LEVEL = 1;
    private static final Point2D loc = new Point2D(0, 0);


    @Test
    public void constructor() {
        Chest chest = new Chest(LEVEL, loc);
        assertNotNull(chest.getContents());
        assertNotNull(chest.getMessages());
        assertNotNull(chest.getPose());
    }

}
