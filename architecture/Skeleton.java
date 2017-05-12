package architecture;

/**
 * A balanced, physical monster. All stat assumptions are based on this
 * monster's scaling.
 *
 * @author Kevin Liu
 * @version May 11, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class Skeleton extends Monster
{

    /**
     * @param level
     *            level of Skeleton
     * @param player
     *            the player
     */
    public Skeleton( int level, Player player )
    {
        super( level, player );
    }


    @Override
    public boolean isMagicDamage()
    {
        return false;
    }


    @Override
    public double defenseFactor()
    {
        return 3.0;
    }


    @Override
    public double[] attributeDistribution()
    {
        double[] distribution = { 20, 2, 15, 15, 10, 3, 5 };
        return distribution.clone();
    }


    @Override
    public String type()
    {
        return "Skeleton";
    }

}
