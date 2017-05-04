package architecture;

public abstract class VolatileEffect
{
    private int turnsRemaining;


    /**
     * @param turnsRemaining
     *            the duration of the effect; a negative duration creates a
     *            permanent effect (for the duration of the battle)
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
}
