package architecture;

/**
 * A class to model attribute and stat-changing VolatileEffects. Each
 * ChangeEffect object modifies one attribute or stat by a nominal
 * (non-proportional) value and expires after a fixed number of turns or the
 * ending of the battle.
 *
 * @author Kevin Liu
 * @version May 5, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class ChangeEffect extends VolatileEffect
{

    private boolean isAttribute;

    private int valueIndex;

    private int netChange;


    /**
     * @param isAttribute
     *            true if instantiated ChangeEffect modifies attributes, false
     *            if modifies stats
     * @param valueIndex
     *            the index of the modified type (see Combatant class for
     *            details)
     * @param netChange
     *            the change (positive or negative) applied to the modified
     *            value
     * @param duration
     *            how long the ChangeEffect lasts
     */
    public ChangeEffect(
        boolean isAttribute,
        int valueIndex,
        int netChange,
        int duration )
    {
        super( duration );
        this.isAttribute = isAttribute;
        this.valueIndex = valueIndex;
        this.netChange = netChange;
    }


    @Override
    public void clear()
    {
    }


    /**
     * @return Returns the isAttribute.
     */
    public boolean isAttribute()
    {
        return isAttribute;
    }


    /**
     * @return Returns the index.
     */
    public int getValueIndex()
    {
        return valueIndex;
    }


    /**
     * @return Returns the netChange.
     */
    public int getNetChange()
    {
        return netChange;
    }

}
