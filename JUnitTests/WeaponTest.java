package JUnitTests;

import static org.junit.Assert.*;

import architecture.augmentations.Item;
import architecture.characters.Player;
import org.junit.Test;

import architecture.augmentations.equipment.Weapon;

import java.util.LinkedList;
import java.util.List;

/**
 * Test Weapon class.
 *
 * @author Kevin Liu
 * @author Period: 5
 * @author Assignment: APCS Final
 * @author Sources: none
 * @version May 31, 2017
 */
public class WeaponTest {

    @Test
    public void constructorDefault() {
        Weapon wep = new Weapon();
        assertTrue(wep.isMagicDamage());
        assertEquals(81, wep.getAccuracy());
        assertEquals(2, wep.getMight());
        assertEquals(0.0, wep.getAngle(), 0.01);
        assertTrue(wep.getMightBoosts().isEmpty());
    }

    @Test
    public void rotation() {
        Weapon wep = new Weapon();
        wep.rotate(180);
        wep.updateRotation();
        assertNotEquals(0.0, wep.getAngle());
    }

    @Test
    public void powerUp() {
        Player player = new Player(null);
        List<Item> items = new LinkedList<>();

        items.add(new Weapon());

        int atk = player.getStats().getATK();

        player.acquireAll(items);

        assertTrue(player.getStats().getATK() == atk + 1);
    }
}
