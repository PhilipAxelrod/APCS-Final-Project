package JUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import architecture.augmentations.AttributeBoost;


/**
 * JUnit testing for AttributeBoost
 *
 * @author Kevin Liu
 * @version May 19, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class AttributeBoostTest
{
    private static final int ATTRIBUTE = 0, VALUE = 5;


    /**
     * Tests value setting.
     */
    @Test
    public void constructor()
    {
        AttributeBoost boost = new AttributeBoost( ATTRIBUTE, VALUE );
        assertEquals( ATTRIBUTE, boost.getAttribute() );
        assertEquals( VALUE, boost.getValue() );
    }

}
