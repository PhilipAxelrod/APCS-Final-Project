package architecture.augmentations;

/**
 * Represents all potions (recover, buffing, etc.). This class only handles
 * calculation of variability factor.
 *
 * @author Kevin Liu
 * @version May 7, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public abstract class Potion implements Consumable
{

    private double var = 1;

    private static final double varFactor = 1.5;

    private static final double inverseVar = Math.pow( varFactor, -1 );


    /**
     * Instantiates a Potion of random variability factor.
     */
    protected Potion()
    {
        var = randomVar();
    }


    /**
     * Instantiates a Potion with given variability factor.
     * 
     * @param var
     *            variability factor
     */
    protected Potion( double var )
    {
        this.var = var;
    }


    /**
     * Generates a random variation (var) factor of pseudo-bell-curve
     * distribution of median 1 between varFactor ^-2 to varFactor^2.
     * 
     * @return a random variation factor
     */
    private static double randomVar()
    {
        double var = 1;
        for ( int i = 0; i < 2; i++ )
            var *= Math.random() * ( varFactor - inverseVar ) + inverseVar;

        return var;
    }


    /**
     * Getter for variability factor.
     * 
     * @return variability factor
     */
    protected double getVar()
    {
        return var;
    }

}
