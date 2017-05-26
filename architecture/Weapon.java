package architecture;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;


/**
 * A class to represent weapons. Weapons are split into two damage types
 * (physical or magical) and 3 speed categories for 6 weapon types total. Weapon
 * class handles generating various stats about the weapon (might, accuracy,
 * boosts, etc.), some of which are specific to weapon equipment.
 *
 * @author Kevin Liu
 * @version May 11, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class Weapon extends Equipment
{
    private int[] type = new int[2];

    private boolean isMagicDamage;

    private int might, accuracy, range;

    private List<Integer> mightBoosts;

    /**
     * All types of weapons arranged in a two-dimensional array. The first index
     * indicates damage type (physical or magical) and the second index
     * indicates speed (slower weapons are stronger than faster counterparts).
     */
    public final static String[][] weaponTypes = {
        { "Greatsword", "Longsword", "Dagger" }, { "Staff", "Wand", "Orb" } };

    private final static double mightFactor = 1.5, accuracyFactor = 1;

    private static final double varValue = 3.0;

    private static final int[] physicalAttributes = { 0, 2, 3 },
                    magicalAttributes = { 1, 3, 5 };


    /**
     * Constructs the default (starter) weapon.
     */
    public Weapon()
    {
        type[0] = 1;
        type[1] = 1;
        isMagicDamage = ( type[0] == 1 );
        might = generateMight( 1, type );
        accuracy = generateAccuracy( 1 );
        mightBoosts = new LinkedList<Integer>();

        setNormalBoosts( new LinkedList<AttributeBoost>() );
        setSpecialBoosts( new LinkedList<AttributeBoost>() );

        if ( type[0] == 0 )
            range = 100;

        else
            range = 300;

        initializeBoosts();
    }


    /**
     * Constructs a random weapon of given level.
     * 
     * @param level
     */
    public Weapon( int level )
    {
        type = generateType();
        isMagicDamage = ( type[0] == 1 );
        might = generateMight( level, type );
        accuracy = generateAccuracy( level );
        mightBoosts = generateMightBoosts( level );

        setNormalBoosts( generateNormalBoosts( level, isMagicDamage ) );
        setSpecialBoosts( generateSpecialBoosts( level ) );

        if ( type[0] == 0 )
            range = 100;

        else
            range = 300;

        initializeBoosts();
    }


    /**
     * Generates a random damage type and weapon.
     * 
     * @return a two-element integer array; the first element is 0 for physical,
     *         1 for magical and the second element represents the specific
     *         weapon.
     */
    private static int[] generateType()
    {
        int damageType = (int)( Math.random() * weaponTypes.length );
        int[] weaponType = { damageType,
            (int)( Math.random() * weaponTypes[damageType].length ) };
        return weaponType;
    }


    /**
     * Generates a defense value based on level, type, and material.
     * 
     * @param level
     *            the relative strength
     * @param type
     *            the type of weapon the speed of the weapon; slower weapon hit
     *            harder
     * @return might might value, used to calculate attack
     */
    private static int generateMight( int level, int[] type )
    {
        double might = level * mightFactor + generateVar( varValue );

        switch ( type[1] )
        {
            case 0: // Greatsword and Staff
                return (int)Math.round( might += 4 );

            case 1: // Longsword and Wand
                return (int)Math.round( might );

            case 2: // Dagger and Orb
                return (int)Math.round( might -= 4 );

            default:
                throw new InvalidParameterException( type[1]
                    + " is an invalid physical or magical weapon type." );
        }

    }


    private static int generateAccuracy( int level )
    {
        int accuracyRadius = 5;
        return (int)Math.round(
            80 + generateVar( accuracyRadius ) + level * accuracyFactor );
    }


    /**
     * Generates a variable number and strength of might boosts for the weapon
     * (of which the average number approaches 3 as the limit of the level
     * approaches infinity).
     * 
     * @param level
     *            relative quantity and magnitude of might boosts
     * @return a List of might boosts
     */
    private static List<Integer> generateMightBoosts( int level )
    {
        final int boostLimit = 3, boostRadius = 2, startingLevel = 1;

        double factor = generateFactor( boostLimit, level, startingLevel );

        List<Integer> boosts = new LinkedList<Integer>();

        while ( Math.random() < factor )
            boosts.add(
                (int)Math.round( level / 3 + generateVar( boostRadius ) ) );

        return boosts;
    }


    private static List<AttributeBoost> generateNormalBoosts(
        int level,
        boolean isMagicDamage )
    {
        final int boostLimit = 5, boostRadius = 1, startingLevel = 1;

        double factor = generateFactor( boostLimit, level, startingLevel );

        List<AttributeBoost> boosts = new LinkedList<AttributeBoost>();

        int[] atr;
        if ( !isMagicDamage )
            atr = physicalAttributes;

        else
            atr = magicalAttributes;

        while ( Math.random() < factor )
            boosts.add( new AttributeBoost(
                atr[(int)( Math.random() * atr.length )],
                (int)Math.round( level / 4 + generateVar( boostRadius ) ) ) );

        return boosts;
    }


    private static List<AttributeBoost> generateSpecialBoosts( int level )
    {
        final int boostLimit = 3, boostRadius = 1, startingLevel = 3;

        double factor = generateFactor( boostLimit, level, startingLevel );

        List<AttributeBoost> boosts = new LinkedList<AttributeBoost>();

        if ( level < startingLevel )
            return boosts;

        while ( Math.random() < factor )
        {
            int value = (int)Math
                .round( level / 4 + generateVar( boostRadius ) );
            if ( value > 0 )
                boosts.add(
                    new AttributeBoost( (int)( Math.random() * 7 ), value ) );
        }

        return boosts;
    }


    public String[][] types()
    {
        return weaponTypes;
    }


    /**
     * @return Returns the type.
     */
    public int[] getType()
    {
        return type;
    }


    /**
     * @return Returns the isMagicDamage.
     */
    public boolean isMagicDamage()
    {
        return isMagicDamage;
    }


    /**
     * @return Returns the might.
     */
    public int getMight()
    {
        return might;
    }


    /**
     * @return Returns the accuracy.
     */
    public int getAccuracy()
    {
        return accuracy;
    }


    /**
     * @return Returns the mightBoosts.
     */
    public List<Integer> getMightBoosts()
    {
        return mightBoosts;
    }


    /**
     * @return Returns the range.
     */
    public int getRange()
    {
        return range;
    }

}
