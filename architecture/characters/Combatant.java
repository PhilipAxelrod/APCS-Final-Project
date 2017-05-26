package architecture.characters;

import com.sun.javafx.geom.Point2D;

import proceduralGeneration.Room;

import java.awt.*;
import java.util.TimerTask;


/**
 * The abstract class for all objects which engage in battle. Combatant handles
 * attribute and stats (numerical values relevant to calculating various aspects
 * of battle), storage of volatile effects, changes in health and mana, etc.
 *
 * @author Kevin Liu
 * @version May 8, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public abstract class Combatant extends TimerTask implements Renderable
{
    protected Point2D previousTopLeftCorner;

    protected Point2D topLeftCorner;

    public int WIDTH = 100;

    public int HEIGHT = WIDTH;

    public double xVelocity = 0, yVelocity = 0;

    public Room currRoom;

    private static final int TERMINAL_VELOCITY = 125;

    private static final double FRICTION = 0.90;

    private static final double ACCELERATION = TERMINAL_VELOCITY * ( 1 / FRICTION - 1 ) / 10D;


    public Combatant( Point2D initPose )
    {
        this.topLeftCorner = initPose;
    }


    public Combatant()
    {
        this( new Point2D( 0, 0 ) );
    }


    public Point2D getPose()
    {
        return topLeftCorner;
    }


    public Point2D getPreviousPose()
    {
        return previousTopLeftCorner;
    }


    protected Point2D bottomRightCorner()
    {
        return new Point2D( topLeftCorner.x + WIDTH, topLeftCorner.y + HEIGHT );
    }


    public void resetPoseToPrevios()
    {
        topLeftCorner = previousTopLeftCorner;
        previousTopLeftCorner = null;
    }


    public void move( float x, float y )
    {
        previousTopLeftCorner = topLeftCorner;
        topLeftCorner = new Point2D( topLeftCorner.x + x, topLeftCorner.y + y );
    }


    public void moveTo( float x, float y )
    {
        previousTopLeftCorner = topLeftCorner;
        topLeftCorner = new Point2D( x, y );
    }


    public Rectangle getBoundingBox()
    {
        return new Rectangle( (int)getPose().x,
            (int)getPose().y,
            WIDTH,
            HEIGHT );
    }


    public void moveTo( Point2D point2D )
    {
        moveTo( point2D.x, point2D.y );
    }


    public void accelerate( float plusX, float plusY )
    {
        xVelocity += Math.signum( plusX ) * ACCELERATION;
        yVelocity += Math.signum( plusY ) * ACCELERATION;

        xVelocity *= FRICTION;
        yVelocity *= FRICTION;
    }


    public void move()
    {
        move( (float) xVelocity, (float) yVelocity );
    }


    public void stop()
    {
        xVelocity = 0;
        yVelocity = 0;
    }

    private int level, health, mana;

    // [STR, INT, DEX, SPD, VIT, WIS, LUK]
    private int[] baseAttributes = new int[7];

    private int[] modifiedAttributes = new int[7];

    // [HP, mana, ATK, DEF, ACC, AVO, CRIT, CRITAVO]
    private Stats stats = new Stats();

    private int actionBar = 0;

    public final boolean canAttack = true;

    protected boolean isDead = false;

    /**
     * Abbreviated codes for each attribute, in order of storage.
     */
    public static final String[] attributeNames = { "STR", "INT", "DEX", "SPD",
        "VIT", "WIS", "LUK" };

    /**
     * Abbreviated codes for each stat, in order of storage.
     */
    public static final String[] statNames = { "HP", "mana", "ATK", "DEF", "ACC",
        "AVO", "CRIT", "CRITAVO" };

    // Constants used to calculate stats from attributes.
    protected static final double healthFactor = 4, manaFactor = 3,
                    accuracyFactor = 5, critFactor = 5, baseCrit = 5,
                    damageFactor = .332;

    /**
     * Expected raise in each attribute per level for a generic monster
     * (Skeleton).
     */
    @SuppressWarnings("unused")
    private static final double[] attributePerLevel = { 2.0, 0.2, 1.5, 1.5, 1.0,
        0.3, 0.5 };

    // Constants used to calculate damage.
    private static final double damageBase = Math.pow( 3, 1 / 10 );

    // Constants used to calculate variation in damage dealt.
    private static final double varFactor = 1.23;

    private static final double inverseVar = Math.pow( varFactor, -1 );

    private static final int actionLimit = 500;


    public void run()
    {

//        if ( /*actionBar >= actionLimit*/true )
//            canAttack = true;

//        else
//            actionBar += stats.DEF;

    }


    public CombatResult attack( Combatant defender )
    {
        if ( !canAttack )
        {
//            System.out.println( "can't attack" );
            return null;
        }
//        canAttack = false;
        actionBar = 0;
//        System.out.println("this:" + this + " attacking: " + defender);

        return defender.receiveAttack( stats.ATK, stats.ACC, stats.CRIT, this );
    }


    /**
     * ONLY FOR TESTING
     * 
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
     * @param attacker
     *            the attacker
     * @return a CombatResult object carrying detailed information
     */
    public CombatResult receiveAttack(
        int atk,
        int acc,
        int crit,
        Combatant attacker )
    {
        CombatResult result = new CombatResult( attacker, this );

        // Test for miss
        if ( Math.random() * 100 > acc - getStats().AVO )
        {
            result.setDamage( 0 );
            result.setHit( false );
            result.setCritical( false );
            return result;
        }
        result.setHit( true );

        // Calculate damage
        int diff = atk - stats.DEF;

        // Generates a random number between the reciprocal of varFactor and
        // varFactor
        double var = Math.random() * ( varFactor - inverseVar ) + inverseVar;

        int damage = (int)Math
            .round( Math.pow( damageBase, diff ) * var * atk * damageFactor );

        // Test for critical hit.
        if ( Math.random() * 100 <= crit - stats.CRITAVO )
        {
            damage *= 2;
            result.setCritical( true );
        }

        result.setDamage( damage );
//        System.out.println("foo this: " + this + "losing: " + damage);
        healthLoss( damage );
        printVitals();
        return result;
    }


    /**
     * @return if the Combatant is dead
     */
    public boolean isDead()
    {
        return getHealth() <= 0;
    }


    public boolean isInRange( Combatant other )
    {
        double distance = getPose().distance( other.getPose() );
        return ( distance <= getRange() );
    }


    /**
     * Updates the attributes of the Combatant, which in turn updates the stats.
     * Method is called whenever equipment or volatile effects change.
     */
    public abstract void updateAttributes();


    /**
     * @return attack range
     */
    public abstract int getRange();


    /**
     * Resets attribute to their base values. To be called only when updating
     * attributes.
     */
    protected void resetAttributes()
    {
        modifiedAttributes = baseAttributes;
    }


    /**
     * Updates the stats of the combatant based purely on attributes and
     * volatile effects.
     */
    protected void updateStats()
    {
        stats.setAll(0);

        // HP = VIT * healthFactor
        stats.HP = (int)Math.round( baseAttributes[4] * healthFactor );

        // mana = WIS * manaFactor
        stats.mana = (int)Math.round( baseAttributes[5] * manaFactor );

        // ATK, DEF, ACC are calculated differently for monsters and players.

        // AVO = SPD * speedFactor
        stats.AVO = (int)Math.round( baseAttributes[3] * accuracyFactor );

        // CRIT = LUK * critFactor + baseCrit
        stats.CRIT = (int)Math.round( baseAttributes[6] * critFactor + baseCrit );

        // CRITAVO = LUK * critFactor
        stats.CRITAVO = (int)Math.round( baseAttributes[6] * critFactor );

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
        int missingHealth = stats.HP - health;

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
    public void setHealthFull()
    {
        health = stats.HP;
    }


    /**
     * Restores mana to the combatant. Mana must remain under mana stat (max mana).
     * 
     * @param value
     *            mana to restore
     * @return actual mana restored
     */
    public int restoreMana( int value )
    {
        int missingMana = stats.mana - mana;

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
     * Restores combatant mana to full (mana stat).
     * 
     * @return mana restored
     */
    public int setManaFull()
    {
        return mana = stats.mana;
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
    public void death()
    {
        isDead = true;
    }


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
    public int[] getBaseAttributes()
    {
        return baseAttributes;
    }


    /**
     * @param baseAttributes
     *            The baseAttributes to set.
     */
    public void setBaseAttributes( int[] baseAttributes )
    {
        this.baseAttributes = baseAttributes;
    }


    /**
     * @return Returns the modifiedAttributes.
     */
    public int[] getModifiedAttributes()
    {
        return modifiedAttributes;
    }


    /**
     * @return Returns the stats in array form. Stats are (in order): [HP, mana,
     *         ATK, DEF, ACC, AVO, CRIT, CRITAVO].
     */
    public Stats getStats()
    {
        return stats;
    }


    /**
     * @return Returns the canAttack.
     */
    public boolean isCanAttack()
    {
        return canAttack;
    }


    /**
     * Prints a summary of Monster's type, level, HP, mana, attributes, and stats.
     */
    public void printStatus()
    {
//        String divider = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
//
//        System.out.println( this + " Lv. " + getLevel() );
//        System.out.println( "HP: " + getHealth() + "/" + getStats().HP + " mana: "
//            + getMana() + "/" + getStats().mana );
//
//        System.out.println( "Attributes" );
//        for ( int j = 0; j < 7; j++ )
//        {
//            System.out.print( Combatant.attributeNames[j] + " "
//                + getBaseAttributes()[j] + " " );
//        }
//
//        System.out.println( "\nStats" );
////        for ( int j = 0; j < Combatant.statNames.length; j++ )
////        {
////            System.out
////                .print( Combatant.statNames[j] + " " + getStats()[j] + " " );
////        }
//
//        System.out.println( "\n" + divider );
    }


    /**
     * For testing. Prints out the current type, level, health, and mana.
     */
    public void printVitals()
    {
        String divider = "~~~~~~~~~~~";

//        System.out.println( this + " Lv. " + getLevel() );
//        System.out.println( "HP: " + getHealth() + "/" + getStats().HP + " mana: "
//            + getMana() + "/" + getStats().mana);
//        System.out.println( divider );
    }


    public void getCurrRoom( Room room )
    {
        currRoom = room;
    }

}
