package architecture;

/**
 * Represents a temporary/volatile effect, which expires upon a fixed duration
 * or the end of the battle. The specific effect is handled by children;
 * duration/timing is handled by this class.
 *
 * @author Kevin Liu
 * @version May 8, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public abstract class VolatileEffect
{
    private int turnsRemaining;


    /**
     * @param turnsRemaining
     *            the duration of the effect; a negative duration creates a
     *            permanent effect (for the duration of the battle)
     * 
     */
    public VolatileEffect( int turnsRemaining )
    {
        this.turnsRemaining = turnsRemaining;
    }


    /**
     * Represents a turn passing.
     * 
     * @return whether or not the effect has expired (true if expired, false if
     *         persisting)
     */
    public boolean tick()
    {
        turnsRemaining--;
        return ( turnsRemaining == 0 );
    }


    /**
     * @return Returns the turnsRemaining.
     */
    public int getTurnsRemaining()
    {
        return turnsRemaining;
    }


    /**
     * Called when effect is removed.
     */
    public abstract void clear();
}
