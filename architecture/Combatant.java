package architecture;

import java.util.LinkedList;
import java.util.TimerTask;


public abstract class Combatant extends TimerTask
{

    private int level, health, mana;

    // [STR, INT, DEX, SPD, VIT, WIS, LUK]
    private int[] attributes = new int[7];

    // [HP, MP, ATK, DEF, ACC, AVO, CRIT, CRITAVO]
    private int[] stats = new int[8];

    private LinkedList<VolatileEffect> volatileEffects = new LinkedList<VolatileEffect>();

    public static final String[] attributeNames = { "STR", "INT", "DEX", "SPD",
        "VIT", "WIS", "LUK" };

    public static final String[] statNames = { "HP", "MP", "ATK", "DEF", "ACC",
        "AVO", "CRIT", "CRITAVO" };

    // Constants used to calculate stats from attributes.
    private static final double healthFactor = 4, manaFactor = 3,
                    accuracyFactor = 5, critFactor = 5, baseCrit = 5,
                    damageFactor = .332;

    /**
     * Expected raise in each attribute per level for a generic monster
     * (Skeleton).
     */
    public static final double[] attributePerLevel = { 2.0, 0.2, 1.5, 1.5, 1.0,
        0.3, 0.5 };

    // Constants used to calculate damage.
    private static final double damageBase = Math.pow( 3, 1 / 10 );

    // Constants used to calculate variation in damage dealt.
    private static final double varFactor = 1.23;

    private static final double inverseVar = Math.pow( varFactor, -1 );


    public void run()
    {
        for ( VolatileEffect effect : getTempEffects() )
            if ( effect.tick() )
                removeEffect( effect );

    }


    /**
     * This method is called when the Combatant receives a normal attack. Damage
     * is calculated using passed stats from the attacker and defensive stats of
     * the defender (this object).
     * 
     * @param atk
     *            nominal attack value of attacker
     * @param acc
     *            accuracy value of attacker
     * @param crit
     *            critical value of attacker
     * @return an array consisting of damage dealt in the first index and a code
     *         in the second index corresponding to special cases (0 for normal,
     *         1 for miss, 2 for critical hit).
     */
    public int[] receiveAttack( int atk, int acc, int crit )
    {
        int[] information = new int[2];
        information[1] = 0;

        // Test for miss
        if ( Math.random() * 100 > acc - getStats()[5] )
        {
            information[0] = 0;
            information[1] = 1;
            return information;
        }

        // Calculate damage
        int diff = atk - stats[3];

        // Generates a random number between the reciprocal of varFactor and
        // varFactor
        double var = Math.random() * ( varFactor - inverseVar ) + inverseVar;

        int damage = (int)Math
            .round( Math.pow( damageBase, diff ) * var * atk * damageFactor );

        // Test for critical hit.
        if ( Math.random() * 100 <= crit - stats[7] )
        {
            damage *= 2;
            information[1] = 2;
        }

        healthLoss( damage );

        information[0] = damage;
        return information;
    }


    /**
     * Combatant receives a volatile effect and stats are accordingly updated by
     * calling updateStats().
     * 
     * @param effect
     *            reveived effect
     */
    public void receiveEffect( VolatileEffect effect )
    {
        volatileEffects.add( effect );
        updateStats();
    }


    /**
     * Removes all VolatileEffects.
     */
    public void removeAllEffects()
    {
        volatileEffects = new LinkedList<VolatileEffect>();
    }


    /**
     * Removes given VolatileEffect.
     * 
     * @param effect
     *            VolatileEffect to remove
     * @return true if VolatileEffect removed, false if no changes made
     */
    public boolean removeEffect( VolatileEffect effect )
    {
        return volatileEffects.remove( effect );
    }


    /**
     * Updates the stats of the combatant based purely on attributes.
     */
    public void updateStats()
    {
        // HP = VIT * healthFactor
        stats[0] = (int)Math.round( attributes[4] * healthFactor );

        // MP = WIS * manaFactor
        stats[1] = (int)Math.round( attributes[5] * manaFactor );

        // ATK, DEF, ACC are calculated differently for monsters and players.

        // AVO = SPD * speedFactor
        stats[5] = (int)Math.round( attributes[3] * accuracyFactor );

        // CRIT = LUK * critFactor + baseCrit
        stats[6] = (int)Math.round( attributes[6] * critFactor + baseCrit );

        // CRITAVO = LUK * critFactor
        stats[7] = (int)Math.round( attributes[6] * critFactor );

        // Factor in buffs/debuffs
        for ( VolatileEffect effect : volatileEffects )
        {
            if ( effect instanceof ChangeEffect )
            {
                if ( ( (ChangeEffect)effect ).isAttribute() )
                    getAttributes()[( (ChangeEffect)effect )
                        .getValueIndex()] += ( (ChangeEffect)effect )
                            .getNetChange();

                else
                    getStats()[( (ChangeEffect)effect )
                        .getValueIndex()] += ( (ChangeEffect)effect )
                            .getNetChange();
            }
        }
    }


    /**
     * Restores health to the combatant. Health must remain under HP stat (max
     * HP).
     * 
     * @param value
     *            health to restore
     * @return actual health restored
     */
    public int restoreHealth( int value )
    {
        int missingHealth = stats[0] - health;

        if ( value < missingHealth )
        {
            health += value;
            return value;
        }
        else
        {
            setHealthFull();
            return missingHealth;
        }
    }


    /**
     * Restores combatant health to full (HP stat).
     * 
     * @return health restored
     */
    public int setHealthFull()
    {
        return health = stats[0];
    }


    /**
     * Restores mana to the combatant. Mana must remain under MP stat (max MP).
     * 
     * @param value
     *            mana to restore
     * @return actual mana restored
     */
    public int restoreMana( int value )
    {
        int missingMana = stats[1] - mana;

        if ( value < missingMana )
        {
            mana += value;
            return value;
        }
        else
        {
            setManaFull();
            return missingMana;
        }
    }


    /**
     * Restores combatant mana to full (MP stat).
     * 
     * @return mana restored
     */
    public int setManaFull()
    {
        return mana = stats[1];
    }


    /**
     * Deducts health from combatant. If new health is less than 0, death() is
     * called.
     * 
     * @param value
     *            health to deduct
     * @return actual health deducted
     */
    public int healthLoss( int value )
    {
        int originalHealth = health;
        if ( health <= value )
        {
            health = 0;
            death();
            return originalHealth;
        }
        else
        {
            health -= value;
            return value;
        }
    }


    /**
     * Deducts mana from combatant. New mana cannot be less than 0, else throws
     * IllegalArgumentException.
     * 
     * @param value
     *            mana to deduct
     * @return actual mana deducted
     */
    public int manaLoss( int value )
    {
        mana -= value;
        if ( mana <= 0 )
            throw new IllegalArgumentException(
                "Mana cannot be reduced below zero." );
        return value;
    }


    /**
     * Method called when health of Combatant reaches zero. Death of monster
     * removes it from the game and awards exp/items to player, while death of
     * player ends the game.
     */
    public abstract void death();


    /**
     * @return Returns the level.
     */
    public int getLevel()
    {
        return level;
    }


    /**
     * @param level
     *            The level to set.
     */
    public void setLevel( int level )
    {
        this.level = level;
    }


    /**
     * @return Returns the health.
     */
    public int getHealth()
    {
        return health;
    }


    /**
     * @param health
     *            The health to set.
     */
    public void setHealth( int health )
    {
        this.health = health;
    }


    /**
     * @return Returns the mana.
     */
    public int getMana()
    {
        return mana;
    }


    /**
     * @param mana
     *            The mana to set.
     */
    public void setMana( int mana )
    {
        this.mana = mana;
    }


    /**
     * @return Returns the attributes in array form. Attributes are (in order):
     *         [STR, INT, DEX, SPD, VIT, WIS, LUK].
     */
    public int[] getAttributes()
    {
        return attributes;
    }


    /**
     * @param attributes
     *            The attributes to set.
     */
    public void setAttributes( int[] attributes )
    {
        this.attributes = attributes;
    }


    /**
     * @return Returns the stats in array form. Stats are (in order): [HP, MP,
     *         ATK, DEF, ACC, AVO, CRIT, CRITAVO].
     */
    public int[] getStats()
    {
        return stats;
    }


    /**
     * @return Returns the tempEffects.
     */
    public LinkedList<VolatileEffect> getTempEffects()
    {
        return volatileEffects;
    }
}
