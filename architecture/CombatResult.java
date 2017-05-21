package architecture;

/**
 * Stores the result of combat
 *
 * @author Kevin Liu
 * @version May 20, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class CombatResult
{
    private int damage;

    private boolean isHit, isCritical;

    private Combatant attacker, defender;


    /**
     * @param attacker
     *            the attacker
     * @param defender
     *            the defender
     */
    public CombatResult( Combatant attacker, Combatant defender )
    {
        super();
        this.attacker = attacker;
        this.defender = defender;
    }


    /**
     * @param damage
     *            damage dealt
     * @param isHit
     *            attack landed/didn't miss
     * @param isCritical
     *            attack landed and struck critically
     * @param attacker
     *            the attacker
     * @param defender
     *            the defender
     */
    public CombatResult(
        int damage,
        boolean isHit,
        boolean isCritical,
        Combatant attacker,
        Combatant defender )
    {
        this.damage = damage;
        this.isHit = isHit;
        this.isCritical = isCritical;
        this.attacker = attacker;
        this.defender = defender;
    }


    /**
     * @return Returns the damage.
     */
    public int getDamage()
    {
        return damage;
    }


    /**
     * @param damage
     *            The damage to set.
     */
    public void setDamage( int damage )
    {
        this.damage = damage;
    }


    /**
     * @return Returns the isHit.
     */
    public boolean isHit()
    {
        return isHit;
    }


    /**
     * @param isHit
     *            The isHit to set.
     */
    public void setHit( boolean isHit )
    {
        this.isHit = isHit;
    }


    /**
     * @return Returns the isCritical.
     */
    public boolean isCritical()
    {
        return isCritical;
    }


    /**
     * @param isCritical
     *            The isCritical to set.
     */
    public void setCritical( boolean isCritical )
    {
        this.isCritical = isCritical;
    }


    /**
     * @return Returns the attacker.
     */
    public Combatant getAttacker()
    {
        return attacker;
    }


    /**
     * @return Returns the defender.
     */
    public Combatant getDefender()
    {
        return defender;
    }

}
