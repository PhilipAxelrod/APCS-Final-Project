package JUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import architecture.*;


/**
 * TODO Write a one-sentence summary of your class here. TODO Follow it with
 * additional details about its purpose, what abstraction it represents, and how
 * to use it.
 *
 * @author Kevin Liu
 * @version May 18, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: TODO
 */
public class ArmorTest
{
    private static final int LEVEL = 1;


    private static boolean acceptableRange( Armor armor )
    {
        if ( !( armor.getType() >= 0 && armor.getType() <= 4 ) )
            return false;
        if ( !( armor.getMaterial() >= 0 && armor.getMaterial() <= 2 ) )
            return false;

        return true;
    }




    @Test
    public void constructor()
    {
        Armor armor = new Armor( LEVEL );
        assertTrue( acceptableRange( armor ) );

        assertNotNull( armor.getDefense() );
        assertNotNull( armor.getDefenseBoosts() );
        assertNotNull( armor.getNormalBoosts() );
        assertNotNull( armor.getSpecialBoosts() );
        assertEquals( armor.getTotalBoosts().length, 7 );
    }

}
