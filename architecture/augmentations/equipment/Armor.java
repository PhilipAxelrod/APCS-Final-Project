package architecture.augmentations.equipment;

import java.util.LinkedList;
import java.util.List;

import architecture.augmentations.AttributeBoost;


/**
 * Represents armor worn by the Player. Each Armor has a material and type. From
 * these (and level), the Armor's various normal AttributeBoosts are determined.
 * Defense is similarly calculated.
 *
 * @author Kevin Liu
 * @version May 11, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class Armor extends Equipment
{

    // types: [Headgear, Chestpiece, Legpiece, Gloves, Shoes]
    private int type;

    // [Plate, Leather, Cloth]
    private int material;

    private int defense;

    private List<Integer> defenseBoosts = new LinkedList<Integer>();

    private static final double[] defenseRatios = { 2, 5, 4, 1, 1 };

    private static final double ratioSum = sumOf( defenseRatios );

    private static final double defenseScaling = 2.0;

    private static final int[] attributes = { 3, 4, 5 };

    /**
     * All types of armor, divided by material and slot arranged in a
     * two-dimensional string array.
     */
    public static final String[][] ARMORTYPES = {
        { "Helmet", "Chestplate", "Platelegs", "Gauntlets", "Boots" },
        { "Cap", "Tunic", "Leggings", "Leather Gloves", "Leather Boots" },
        { "Hat", "Robe Top", "Robe Bottom", "Gloves", "Shoes" } };

    /**
     * Materials of armor
     */
    public static final String[] armorMaterials = { "Plate", "Leather",
        "Cloth" };


    /**
     * @param level
     *            relative strength
     */
    public Armor( int level )
    {
        this( level, generateType(), generateMaterial() );
    }


    /**
     * @param level
     *            relative strength
     * @param type
     *            helmet, chestpiece, etc.
     * @param material
     *            the material with which the armor is made
     */
    public Armor( int level, int type, int material )
    {
        this.type = type;
        this.material = material;
        defense = generateDefense( level, type, material );
        defenseBoosts = generateDefenseBoosts( level );

        setNormalBoosts( generateNormalBoosts( level, material ) );
        setSpecialBoosts( generateSpecialBoosts( level ) );

        initializeBoosts();
    }


    /**
     * Generate a type of armor
     * 
     * @return an int that grabs from the 2Darray.
     */
    private static int generateType()
    {
        return (int)( Math.random() * ARMORTYPES[0].length );
    }


    /**
     * Generate the material it's made of
     * 
     * @return an int that determines the material of the armor, based on the
     *         2Darray.
     */
    private static int generateMaterial()
    {
        return (int)( Math.random() * armorMaterials.length );
    }


    /**
     * Generate the defense of the player.
     * 
     * @param level
     *            The current level of the player.
     * @param type
     *            The Type of armor.
     * @param material
     *            The material the armor piece is made of.
     * @return The defence in total of the player, rounded to an int.
     */
    private static int generateDefense( int level, int type, int material )
    {
        double def = ( defenseScaling * level + 20 ) * defenseRatios[type]
            / ratioSum;

        switch ( material )
        {
            case 0:
                def += 1;

            case 1:
                def += 0;

            case 2:
                def += -1;
        }

        int defInt = (int)Math.round( def );

        if ( defInt < 0 )
            defInt = 0;

        return defInt;
    }


    /**
     * List all possible sources of bonus defense of the player
     * 
     * @param level
     *            The current level of the player
     * @return A list holding all the sources of bonus defense.
     */
    private static List<Integer> generateDefenseBoosts( int level )
    {
        final int boostLimit = 3, boostRadius = 2, startingLevel = 1;

        double factor = generateFactor( boostLimit, level, startingLevel );

        List<Integer> boosts = new LinkedList<Integer>();

        while ( Math.random() < factor )
            boosts.add(
                (int)Math.round( level / 15 + generateVar( boostRadius ) ) );

        return boosts;
    }


    /**
     * Generates a List of AttributeBoost based on the type of armor
     * 
     * @param level
     *            relative strength and number of boosts
     * @param material
     *            type of boosts
     * @return a List of AttributeBoost which are dependent on the type
     */
    private static List<AttributeBoost> generateNormalBoosts(
        int level,
        int material )
    {
        final int boostLimit = 5, boostRadius = 1, startingLevel = 1;

        double factor = generateFactor( boostLimit, level, startingLevel );

        List<AttributeBoost> boosts = new LinkedList<AttributeBoost>();

        switch ( material )
        {
            case 0:
                boosts.add( new AttributeBoost( 3, -1 ) );

            case 1:
                boosts.add( new AttributeBoost( 3, 1 ) );

            case 2:
                boosts.add( new AttributeBoost( 1, 1 ) );
        }

        while ( Math.random() < factor )
            boosts.add( new AttributeBoost(
                attributes[(int)( Math.random() * attributes.length )],
                (int)Math.round( level / 10 + generateVar( boostRadius ) ) ) );

        return boosts;
    }


    /**
     * Add all possible sources of defense
     * 
     * @param defense
     *            The base defense of the player, before any additional sources.
     */
    public void addDefense( int defense )
    {
        this.defense += defense;
    }


    @Override
    public String[][] types()
    {
        return ARMORTYPES;
    }


    /**
     * @return Returns the type.
     */
    public int getType()
    {
        return type;
    }


    /**
     * @return Returns the material.
     */
    public int getMaterial()
    {
        return material;
    }


    /**
     * @return Returns the defense.
     */
    public int getDefense()
    {
        return defense;
    }


    /**
     * @return Returns the defenseBoosts.
     */
    public List<Integer> getDefenseBoosts()
    {
        return defenseBoosts;
    }


    private static double sumOf( double[] array )
    {
        double sum = 0;
        for ( double i : array )
            sum += i;
        return sum;

    }

}
