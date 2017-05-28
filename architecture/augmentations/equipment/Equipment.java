package architecture.augmentations.equipment;

import architecture.augmentations.AttributeBoost;
import architecture.augmentations.Item;

import java.util.LinkedList;
import java.util.List;


/**
 * A class to describe general equipment. Equipment stores and calculates boost
 * totals (extra bonuses to equipment stats). Generating boost values, base
 * stats, and other Equipment type-specific tasks are handled by children
 * classes (Weapon, Armor, etc.).
 *
 * @author Kevin Liu
 * @version May 7, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public abstract class Equipment implements Item
{

    /**
     * normal boosts which scale with level
     */
    private List<AttributeBoost> normalBoosts = new LinkedList<AttributeBoost>();

    /**
     * random boosts, more common at higher levels
     */
    private List<AttributeBoost> specialBoosts = new LinkedList<AttributeBoost>();

    protected int[] totalBoosts;


    /**
     * Equipment cannot be in use to upgrade.
     * 
     * @param boost
     *            the boost to add
     */
    public void addBoost( AttributeBoost boost )
    {
        specialBoosts.add( boost );
        totalBoosts[boost.getAttribute()] += boost.getValue();
    }


    /**
     * Called when equipment is instantiated; calculates stats based on boosts.
     * If a boost is added after Equipment instantiation,
     * addBoost(AttributeBoost boost) is used to calculate instead.
     */
    protected void initializeBoosts()
    {
        totalBoosts = new int[7];
        for ( int i = 0; i < totalBoosts.length; i++ )
            totalBoosts[i] = 0;

        for ( AttributeBoost boost : normalBoosts )
            totalBoosts[boost.getAttribute()] += boost.getValue();

        for ( AttributeBoost boost : specialBoosts )
            totalBoosts[boost.getAttribute()] += boost.getValue();
    }


    protected static List<AttributeBoost> generateSpecialBoosts( int level )
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

    /**
     * Stores a list of types of equipment. Implementation of Weapon stores
     * types of weapons, implementation of Armor stores types of armor, etc.
     * 
     * @return String[] of equipment types
     */
    public abstract String[][] types();


    /**
     * Generates the factor used to calculate number of boosts.
     * 
     * @param boostLimit
     *            the average total number of boosts possible (as level
     *            approaches infinity)
     * @param level
     *            relative number of boosts
     * @param startingLevel
     *            the level from which boosts could possibly appear
     * @return the boost factor
     */
    protected static double generateFactor(
        int boostLimit,
        int level,
        int startingLevel )
    {
        double temp = boostLimit - Math.pow( Math.E,
            -( ( level - startingLevel )
                - 2 * boostLimit * Math.log( boostLimit ) )
                / ( 2 * boostLimit ) );
        return 1 - 1 / ( 1 + temp );
    }
    

    /**
     * @return Returns the normalBoosts.
     */
    public List<AttributeBoost> getNormalBoosts()
    {
        return normalBoosts;
    }


    /**
     * @param normalBoosts
     *            The normalBoosts to set.
     */
    protected void setNormalBoosts( List<AttributeBoost> normalBoosts )
    {
        this.normalBoosts = normalBoosts;
    }


    /**
     * @return Returns the specialBoosts.
     */
    public List<AttributeBoost> getSpecialBoosts()
    {
        return specialBoosts;
    }


    /**
     * @param specialBoosts
     *            The specialBoosts to set.
     */
    protected void setSpecialBoosts( List<AttributeBoost> specialBoosts )
    {
        this.specialBoosts = specialBoosts;
        initializeBoosts();
    }


    /**
     * @return Returns the totalBoosts.
     */
    public int[] getTotalBoosts()
    {
        return totalBoosts;
    }


    /**
     * Generates a random number between radius and -radius to be added.
     * 
     * @param radius
     *            positive and negative limit of random number
     * @return variability value
     */
    protected static double generateVar( double radius )
    {
        double var = 0;
        for ( int i = 0; i < 2; i++ )
            var += Math.random() * ( 2 * radius ) - radius;

        return var;
    }

}
