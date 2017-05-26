package architecture.augmentations;

import java.util.LinkedList;
import java.util.List;


/**
 * Represents armors
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
     */
    public Armor( int level, int type )
    {
        this( level, type, generateMaterial() );
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


    private static int generateType()
    {
        return (int)( Math.random() * ARMORTYPES[0].length );
    }


    private static int generateMaterial()
    {
        return (int)( Math.random() * armorMaterials.length );
    }


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


    private static List<Integer> generateDefenseBoosts( int level )
    {
        final int boostLimit = 3, boostRadius = 2, startingLevel = 1;

        double factor = generateFactor( boostLimit, level, startingLevel );

        List<Integer> boosts = new LinkedList<Integer>();

        while ( Math.random() < factor )
            boosts.add((int) Math.round(level / 15 + generateVar(boostRadius)));

        return boosts;
    }


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
