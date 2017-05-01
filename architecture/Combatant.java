package architecture;

import java.util.HashMap;
import java.util.TimerTask;



public abstract class Combatant extends TimerTask
{

    int level, health, mana;

    int[] attributes = new int[7]; // [STR, INT, DEX, SPD, VIT, WIS, LUK]

    int[] stats = new int[8]; // [HP, MP, ATK, DEF, ACC, AVO, CRIT, CRITAVO]

    public static final String[] attributeNames = { "STR", "INT", "DEX", "SPD",
        "VIT", "WIS", "LUK" };

    public static final String[] statNames = { "HP", "MP", "ATK", "DEF", "ACC",
        "AVO", "CRIT", "CRITAVO" };

    static final int healthFactor = 4;

    static final int manaFactor = 3;

    static final int accuracyFactor = 5;

    static final int critFactor = 5;

    static final int baseCrit = 5;

    static final double damageBase = Math.pow( 3, 1 / 10 );

    static final double varFactor = 1.23;

    static final double inverseVar = Math.pow( varFactor, -1 );


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
     * @return nominal damage dealt to this object
     */
    public int receiveAttack( int atk, int acc, int crit )
    {
        // Test for miss
        if ( Math.random() * 100 < acc - stats[5] )
            return 0;

        // Calculate damage
        int diff = atk - stats[3];

        // Generates a random number between the reciprocal of varFactor and
        // varFactor
        double var = Math.random() * ( varFactor - inverseVar ) + inverseVar;

        int damage = (int)Math
            .round( Math.pow( damageBase, diff - 2 ) / 3 * var );

        // Test for critical hit.
        if ( Math.random() * 100 >= crit - stats[7] )
            damage *= 2;

        healthLoss( damage );

        return damage;
    }


    /**
     * Method called when health of Combatant reaches zero. Death of monster
     * removes it from the game and awards exp/items to player, while death of
     * player ends the game.
     */
    public abstract void death();


    /**
     * Updates the stats of the combatant based purely on attributes.
     */
    public void updateStats()
    {
        // HP = VIT * healthFactor
        stats[0] = attributes[4] * healthFactor;

        // MP = WIS * manaFactor
        stats[1] = attributes[5] * manaFactor;

        // ATK, DEF, ACC are calculated differently for monsters and players.

        // AVO = SPD * speedFactor
        stats[5] = attributes[3] * accuracyFactor;

        // CRIT = LUK * critFactor + baseCrit
        stats[6] = attributes[6] * critFactor + baseCrit;

        // CRITAVO = LUK * critFactor
        stats[7] = attributes[6] * critFactor;
    }


    /**
     * Restores health to the combatant. Health must remain under HP stat (max
     * HP).
     * 
     * @param value
     *            health to restore
     * @return actual health restored
     */
    public int healthRestore( int value )
    {
        int missingHealth = stats[0] - health;

        if ( value < missingHealth )
        {
            health += value;
            return value;
        }
        else
        {
            health = stats[0];
            return missingHealth;
        }
    }


    /**
     * Restores mana to the combatant. Mana must remain under MP stat (max MP).
     * 
     * @param value
     *            mana to restore
     * @return actual mana restored
     */
    public int manaRestore( int value )
    {
        int missingMana = stats[1] - mana;

        if ( value < missingMana )
        {
            mana += value;
            return value;
        }
        else
        {
            mana = stats[1];
            return missingMana;
        }
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
        if ( health <= value )
        {
            health = 0;
            death();
            return health;
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
     * @return Returns the level.
     */
    public int getLevel()
    {
        return level;
    }


    /**
     * @return Returns the health.
     */
    public int getHealth()
    {
        return health;
    }


    /**
     * @return Returns the mana.
     */
    public int getMana()
    {
        return mana;
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
     * @return Returns the stats in array form. Stats are (in order): [HP, MP,
     *         ATK, DEF, ACC, AVO, CRIT, CRITAVO].
     */
    public int[] getStats()
    {
        return stats;
    }
}
