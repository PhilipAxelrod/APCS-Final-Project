package architecture.characters;

import java.util.LinkedList;
import java.util.List;

import architecture.augmentations.Armor;
import architecture.augmentations.Item;
import architecture.augmentations.consumables.NominalPotion;
import architecture.augmentations.weapons.Weapon;
import architecture.characters.Combatant;
import architecture.characters.Player;

import com.sun.javafx.geom.Point2D;


/**
 * Represents monsters, Combatant enemies of the game. Specific traits of each
 * monster (name, attribute distribution, etc.) are handled by children classes.
 *
 * @author Kevin Liu
 * @version May 8, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public abstract class Monster extends Combatant
{
    // TODO: make these override stuff in Combatabt
    protected final int TERMINAL_VELOCITY = 0;

    private final double FRICTION = 0.99;

    // TODO: remove arbitrary 10D
    private double ACCELERATION = TERMINAL_VELOCITY
            * ( 1 / FRICTION - 1 ) / 10D;

    /**
     * The portion of exp of the level-up requirement awarded to the player of
     * the same level upon Monster's death.
     */
    private static final double expPerLevel = 1.0 / 3.0;

    // [nominal potion, armor, weapon, accessory]
    /**
     * The relative probabilities of the type of item appearing on a monster.
     * Expressed in the order: [potion, armor, weapon, accessory].
     */
    private static final double[] itemDistribution = { 1, 1, 1 };

    /**
     * The probability that another item is added to the items of the Monster.
     */
    private static final double dropVariable = .5;

    private int exp;

    private List<Item> items;

    private Player player;

    private boolean aggro = false;


    /**
     * @param level
     *            level of monster
     * @param player
     *            the Player
     */
    public Monster( int level, Player player )
    {
        this( level, generateItems( level ), player, new Point2D( 0, 0 ) );
    }


    /**
     * @param level
     *            level of monster
     * @param items
     *            items dropped upon death
     * @param player
     *            the Player
     */
    public Monster( int level, List<Item> items, Player player )
    {
        this( level, items, player, new Point2D( 0, 0 ) );

    }


    public Monster( int level, Player player, Point2D loc )
    {
        this( level, generateItems( level ), player, loc );
    }


    public Monster( int level, List<Item> Items, Player player, Point2D loc )
    {
        super( loc );
        if ( level < 1 )
            throw new InstantiationError( "Level must be at least 1" );

        setLevel( level );
        this.items = items;
        this.player = player;

        exp = (int)Math.round( Player.baseExp * expPerLevel
            * Math.pow( Player.expGrowth, level - 1 ) );

        // Assigns fixed attributes based on Monster type and level
        for ( int i = 0; i < attributeDistribution().length; i++ )
            getBaseAttributes()[i] += Math
                .round( distributionRatios()[i] * ( getLevel() - 2 ) * 7 );

        // Assigns 42 points based on the probability of the Monster's attribute
        // distribution
        for ( int i = 0; i < ( 6 ) * 7; i++ )
        {
            double point = Math.random() * sumOf( attributeDistribution() );
            double range = 0;

            for ( int j = 0; j < 7; j++ )
            {
                range += attributeDistribution()[j];
                if ( point < range )
                {
                    getBaseAttributes()[j]++;
                    break;
                }
            }
        }

        updateAttributes();

        // Sets health and mana to be full
        setHealthFull();
        setManaFull();
    }


    /**
     * Generates the list of items Monster drops upon dying.
     * 
     * @param level
     *            relative strength of items
     * @return List<Item> of items
     */
    public static List<Item> generateItems( int level )
    {
        List<Item> items = new LinkedList<Item>();
        while ( Math.random() < dropVariable )
        {
            double point = Math.random() * sumOf( itemDistribution );
            double range = 0;

            for ( int j = 0; j < itemDistribution.length; j++ )
            {
                range += itemDistribution[j];
                if ( point < range )
                {
                    switch ( j )
                    {
                        case 0:
                            items.add( new NominalPotion( level ) );
                            break;

                        case 1:
                            items.add( new Armor( level ) );
                            break;

                        case 2:
                            items.add( new Weapon( level ) );
                            break;

                    }
                    break;
                }
            }

        }
        return items;
    }


    @Override
    public void run()
    {
        super.run();

        // Calculate range
        Point2D playerPose = player.getPose();
        double distance = Point2D.distance( playerPose.x,
            playerPose.y,
            getPose().x,
            getPose().y );
        intelligence();
        if ( canAttack && distance <= getRange() && !player.isDead() )
        {
            // System.out.println( "player attacked!" );
            attack( player );
            // canAttack = false;
        }

    }


    @Override
    public void death()
    {
        // Awards exp to the player equal to 100 / 6 * (1.5 ^ level)
        player.acquire( items );
        player.gainExp( exp );
    }


    @Override
    public void updateAttributes()
    {
        resetAttributes();
        updateStats();
    }


    @Override
    protected void updateStats()
    {
        super.updateStats();
        // ATK = (STR or INT) + level
        if ( !isMagicDamage() )
            getStats().setATK( getModifiedAttributes()[0] + getLevel() );

        else // (isMagicDamage())
            getStats().setATK( getModifiedAttributes()[1] + getLevel() );

        // DEF = (level + 2) * defenseFactor
        getStats().setDEF(
            (int)Math.round( ( getLevel() + 2 ) * defenseFactor() - 4 ) );

        // ACC = ((DEX or WIS) + level) * 4 + LUK + 75
        if ( !isMagicDamage() )
            getStats().setACC( ( getModifiedAttributes()[3] + getLevel() ) * 4
                + getBaseAttributes()[6] + 75 );
        else // (isMagicDamage())
            getStats().setACC( ( getModifiedAttributes()[5] + getLevel() ) * 4
                + getModifiedAttributes()[6] + 75 );
    }


    /**
     * Calculates the sum of a double[].
     * 
     * @param array
     *            double[] in question
     * @return the sum of elements of array
     */
    protected static double sumOf( double[] array )
    {
        double sum = 0;
        for ( double i : array )
            sum += i;
        return sum;

    }


    /**
     * Used to denote magic or physical damage type monsters
     * 
     * @return true if magical damage, false if physical damage
     */
    public abstract boolean isMagicDamage();


    /**
     * Used to store defensive capabilities of a Monster, relative to other
     * classes of Monsters
     * 
     * @return defenseFactor, used to calculate DEF based on level
     */
    public abstract double defenseFactor();


    /**
     * Used to store the relative chances of an attribute being raised each
     * level up. Actual probability is calculated by dividing each element in
     * the array by the array's sum.
     * 
     * @return relative probabilities of an attribute being raised for each
     *         level up
     */
    public abstract double[] attributeDistribution();


    /**
     * Used to store the actual probabilities of each attribute being raised for
     * each level up.
     * 
     * @return probabilities of an attribute being raised for each level up
     */
    public double[] distributionRatios()
    {
        double[] ratios = attributeDistribution();
        for ( int i = 0; i < ratios.length; i++ )
            ratios[i] /= sumOf( attributeDistribution() );
        return ratios;
    }


    /**
     * For testing. Prints out the average increase in each stat per level.
     */
    public void printDistributionRatios()
    {
        for ( int i = 0; i < 7; i++ )
        {
            System.out.print( Combatant.attributeNames[i] + ": "
                + (int)( distributionRatios()[i] * 700 ) / 100.0 + " " );
        }
    }


    /**
     * Used to store a String representation of the type of Monster.
     * 
     * @return String of Monster type
     */
    public abstract String type();


    public void intelligence()
    {
        if ( !aggro && Point2D.distance( topLeftCorner.x,
            topLeftCorner.y,
            player.topLeftCorner.x,
            player.topLeftCorner.y ) < 500 )
            aggro = true;
        else if ( aggro )
        {
            accelerate( player.getPose().x - getPose().x,
                player.getPose().y - getPose().y );
            move();
        }
    }

}
