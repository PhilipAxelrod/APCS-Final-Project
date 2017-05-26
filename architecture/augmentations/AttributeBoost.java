package architecture.augmentations;

/**
 * Represents boosts to attributes for equipment.
 *
 * @author Kevin Liu
 * @version May 11, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class AttributeBoost
{
    private int attribute, value;


    /**
     * @param attribute
     *            the index of the attribute to boost
     * @param value
     *            the increase of the value
     */
    public AttributeBoost( int attribute, int value )
    {
        this.attribute = attribute;
        this.value = value;
    }


    /**
     * @return Returns the attribute.
     */
    public int getAttribute()
    {
        return attribute;
    }


    /**
     * @return Returns the value.
     */
    public int getValue()
    {
        return value;
    }

}
