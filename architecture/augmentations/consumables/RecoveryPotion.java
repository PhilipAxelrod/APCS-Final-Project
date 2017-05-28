package architecture.augmentations.consumables;

/**
 * Represents the subset of Potions which recover health and/or mana. Specific
 * calculation of recovery values (either nominal or proportional of maximum
 * health/mana) is handled by children classes.
 *
 * @author Kevin Liu
 * @version May 7, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public abstract class RecoveryPotion extends Potion
{
    private int type;

    private static final double[] typeDistribution = { 40, 40, 20 };

    private static final String[] types = { "Health", "Mana", "Dual" };


    /**
     * Instantiates a recovery potion of random type (health, mana, dual) and
     * random variability factor.
     */
    protected RecoveryPotion()
    {
        this( randomType() );
    }


    /**
     * Instantiates a recovery potion of given type (health, mana, dual) and
     * random variability factor.
     * 
     * @param type
     *            the type of potion
     */
    public RecoveryPotion( int type )
    {
        super();
        this.type = type;
    }


    /**
     * Instantiates a recovery potion of random type (health, mana, dual) and
     * given variability factor.
     * 
     * @param var
     *            variability factor
     */
    public RecoveryPotion( double var )
    {
        this( randomType(), var );
    }


    /**
     * Instantiates a recovery potion of given type (health, mana, dual) and
     * given variability factor.
     * 
     * @param type
     *            potion type
     * @param var
     *            variability factor
     */
    public RecoveryPotion( int type, double var )
    {
        super( var );
        this.type = type;
    }


    /**
     * Generates a random potion type (0, 1, or 2) based on the probability
     * distribution typeDistribution.
     * 
     * @return a potion type of 0, 1, or 2
     */
    private static int randomType()
    {
        double point = Math.random() * sumOf( typeDistribution );
        double range = 0;

        for ( int j = 0; j < 3; j++ )
        {
            range += typeDistribution[j];
            if ( point < range )
                return j;
        }
        return -1;
    }


    /**
     * Getter for type
     * 
     * @return type
     */
    protected int getType()
    {
        return type;
    }


    /**
     * Returns a string array of potion types.
     * 
     * @return types of potions
     */
    public String[] getTypes()
    {
        return types;
    }


    private static double sumOf( double[] array )
    {
        double sum = 0;
        for ( double i : array )
            sum += i;
        return sum;

    }


    /**
     * For testing only. Prints information regarding the potion.
     */
    public abstract void printDetails();

}
