package architecture;

public abstract class Potion implements Consumable
{
    protected int type;

    protected double var = 1;

    private static final double[] typeDistribution = { 40, 40, 20 };

    private static final double varFactor = 1.5;

    private static final double inverseVar = Math.pow( varFactor, -1 );

    protected static final int[] vitalFactors = { 4, 3, 2 };


    public Potion()
    {
        // Assigns random multiplier with bell-curve distribution around 1;
        for ( int i = 0; i < 2; i++ )
            var *= Math.random() * ( varFactor - inverseVar ) + inverseVar;

        // Determines potion type based on type distributions
        double point = Math.random() * sumOf( typeDistribution );
        double range = 0;

        for ( int j = 0; j < 3; j++ )
        {
            range += typeDistribution[j];
            if ( point < range )
            {
                type = j;
                break;
            }
        }

    }


    /**
     * Calculates the sum of a double[].
     * 
     * @param array
     *            double[] in question
     * @return
     */
    private static double sumOf( double[] array )
    {
        double sum = 0;
        for ( double i : array )
            sum += i;
        return sum;

    }

}
