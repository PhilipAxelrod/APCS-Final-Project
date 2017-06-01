package JUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import architecture.augmentations.equipment.Armor;


/**
 * JUnit testing for Armor
 *
 * @author Kevin Liu
 * @version May 18, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class ArmorTest
{
    private static final int LEVEL = 1, TYPE = 0, MATERIAL = 0;


    /**
     * Tests most functions of the constructor using a randomly generated type
     * and material.
     */
    @Test
    public void constructorBasic()
    {
        Armor armor = new Armor( LEVEL );

        // Check if type and material are within acceptable bounds
        assertTrue( ( armor.getType() >= 0 && armor.getType() <= 4 ) );
        assertTrue( ( armor.getMaterial() >= 0 && armor.getMaterial() <= 2 ) );

        // Test defense value
        assertTrue( armor.getDefense() >= 0 );

        // Test boost initialization
        assertNotNull( armor.getDefenseBoosts() );
        assertNotNull( armor.getNormalBoosts() );
        assertNotNull( armor.getSpecialBoosts() );

        // Test total boosts
        assertEquals( 7, armor.getTotalBoosts().length );
    }


    /**
     * Tests the explicitly stated constructor's material aspect (type is tested
     * in other test).
     */
    @Test
    public void constructorTypeMaterial()
    {
        Armor armor = new Armor( LEVEL, TYPE, MATERIAL );
        assertTrue( armor.getMaterial() == MATERIAL );
        assertTrue( armor.getType() == TYPE );
    }
    
    /**
     * Tests the defense value generation.
     */
    @Test
    public void defValue()
    {
        Armor armor = new Armor(LEVEL, TYPE, MATERIAL);
        assertEquals(5, armor.getDefense());
    }

}
