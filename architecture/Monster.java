package architecture;

public abstract class Monster extends Combatant
{

    public Monster( int level )
    {

        if ( level < 1 )
            throw new InstantiationError( "Level must be greater than 0" );

        this.level = level;

        // Assigns fixed attributes based on Monster type and level
        for ( int i = 0; i < attributeDistribution().length; i++ )
            attributes[i] += Math.round( distributionRatios()[i] * level * 7 );

        // Assigns 14 points based on the probability of the Monster's attribute
        // distribution
        for ( int i = 0; i < ( 2 ) * 7; i++ )
        {
            double point = Math.random() * sumOf( attributeDistribution() );
            double range = 0;

            for ( int j = 0; j < 7; j++ )
            {
                range += attributeDistribution()[j];
                if ( point < range )
                {
                    attributes[j]++;
                    break;
                }
            }
        }

        // Sets health and mana to be full
        health = getStats()[0];
        mana = getStats()[1];
        
        updateStats();
    }


    @Override
    public void death()
    {
        // TODO Auto-generated method stub
        // Awards exp to the player equal to 100 / 6 * (1.5 ^ level)

    }


    @Override
    public void updateStats()
    {
        super.updateStats();

        // ATK = (STR or INT) + level
        if ( !isMagicDamage() )
            stats[2] = attributes[0] + level;

        else // (isMagicDamage())
            stats[2] = attributes[1] + level;

        // DEF = (level + 3) * defenseFactor
        stats[3] = (int)Math.round( ( level + 3 ) * defenseFactor() );

        // ACC = ((DEX or WIS) + level) * 4 + LUK
        if ( !isMagicDamage() )
            stats[4] = ( attributes[3] + level ) * 4 + attributes[6] + 70;
        else // (isMagicDamage())
            stats[4] = ( attributes[5] + level ) * 4 + attributes[6] + 70;
    }


    /**
     * Calculates the sum of a double[].
     * 
     * @param array
     *            double[] in question
     * @return
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
     * Used to store a String representation of the type of Monster.
     * 
     * @return String of Monster type
     */
    public abstract String type();


    /**
     * Prints a summary of
     */
    public void printStatus()
    {
        String divider = "~~~~~~~~~~~";

        System.out.println( type() + " Lv. " + level );
        System.out.println( "HP: " + health + "/" + getStats()[0] + " MP: "
            + mana + "/" + getStats()[1] );

        System.out.println( "Attributes" );
        for ( int j = 0; j < 7; j++ )
        {
            System.out.print(
                Combatant.attributeNames[j] + " " + getAttributes()[j] + " " );
        }

        System.out.println( "\nStats" );
        for ( int j = 2; j < Combatant.statNames.length; j++ )
        {
            System.out
                .print( Combatant.statNames[j] + " " + getStats()[j] + " " );
        }

        System.out.println( "\n" + divider );
    }

}
