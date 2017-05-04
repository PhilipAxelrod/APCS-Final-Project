package architecture;

public class ChangeEffect extends VolatileEffect
{

    private boolean isAttribute;

    private int valueIndex;

    private int netChange;


    public ChangeEffect(
        boolean isAttribute,
        int valueIndex,
        int netChange,
        int turnsRemaining )
    {
        super( turnsRemaining );
        this.isAttribute = isAttribute;
        this.valueIndex = valueIndex;
        this.netChange = netChange;
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
