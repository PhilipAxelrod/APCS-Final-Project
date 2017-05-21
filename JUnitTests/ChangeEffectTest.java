package JUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import architecture.ChangeEffect;


/**
 * Tester for ChangeEffect and its abstract parent class VolatileEffect
 *
 * @author Kevin Liu
 * @version May 19, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class ChangeEffectTest
{
    private static final boolean ISATTRIBUTE = true;

    private static final int VALUEINDEX = 0, NETCHANGE = 5, DURATION = 5;


    /**
     * Tests the constructor.
     */
    @Test
    public void constructor()
    {
        ChangeEffect effect = new ChangeEffect( ISATTRIBUTE,
            VALUEINDEX,
            NETCHANGE,
            DURATION );
        assertEquals( ISATTRIBUTE, effect.isAttribute() );
        assertEquals( VALUEINDEX, effect.getValueIndex() );
        assertEquals( NETCHANGE, effect.getNetChange() );
        assertEquals( DURATION, effect.getTurnsRemaining() );
    }


    /**
     * Tests the timer of the effect.
     */
    @Test
    public void tick()
    {
        ChangeEffect effect = new ChangeEffect( ISATTRIBUTE,
            VALUEINDEX,
            NETCHANGE,
            DURATION );
        assertFalse( effect.tick() );
        assertFalse( effect.tick() );
        assertFalse( effect.tick() );
        assertFalse( effect.tick() );
        assertTrue( effect.tick() );
    }

}
