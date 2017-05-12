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

    private Combatant combatant;


    /**
     * @param turnsRemaining
     *            the duration of the effect; a negative duration creates a
     *            permanent effect (for the duration of the battle)
     * @param combatant
     *            the target of the effect
     */
    public VolatileEffect( int turnsRemaining, Combatant combatant )
    {
        this.turnsRemaining = turnsRemaining;
        this.combatant = combatant;
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
     * @return Returns the combatant.
     */
    public Combatant getCombatant()
    {
        return combatant;
    }


    /**
     * Called when effect is removed.
     */
    public abstract void clear();
}
