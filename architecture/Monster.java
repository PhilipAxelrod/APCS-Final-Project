package architecture;

public abstract class Monster extends Combatant
{
    /**
     * The portion of exp of the level-up requirement awarded to the player of
     * the same level upon Monster's death.
     */
    private static final double expPerLevel = 1.0 / 6.0;

    int exp;

    Item item;
    Player player;


    public Monster( int level, Item item )
    {

        if ( level < 5 )
            throw new InstantiationError( "Level at least 5" );

        setLevel( level );
        this.item = item;

        exp = (int)Math.round( Player.baseExp * expPerLevel
            * Math.pow( Player.expGrowth, level - 5 ) );

        // Assigns fixed attributes based on Monster type and level
        for ( int i = 0; i < attributeDistribution().length; i++ )
            getAttributes()[i] += Math
                .round( distributionRatios()[i] * ( getLevel() - 2 ) * 7 );

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
                    getAttributes()[j]++;
                    break;
                }
            }
        }

        updateStats();

        // Sets health and mana to be full
        setHealthFull();
        setManaFull();
    }


    @Override
    public void death()
    {
        // System.out.println( type() + " Lv. " + getLevel() + " is dead." );
        // TODO Auto-generated method stub
        // Awards exp to the player equal to 100 / 6 * (1.5 ^ level)

        player.gainExp( exp );
    }


    @Override
    public void updateStats()
    {
        super.updateStats();

        // ATK = (STR or INT) + level
        if ( !isMagicDamage() )
            getStats()[2] = getAttributes()[0] + getLevel();

        else // (isMagicDamage())
            getStats()[2] = getAttributes()[1] + getLevel();

        // DEF = (level + 2) * defenseFactor
        getStats()[3] = (int)Math
            .round( ( getLevel() + 2 ) * defenseFactor() - 4 );

        // ACC = ((DEX or WIS) + level) * 4 + LUK + 75
        if ( !isMagicDamage() )
            getStats()[4] = ( getAttributes()[3] + getLevel() ) * 4
                + getAttributes()[6] + 75;
        else // (isMagicDamage())
            getStats()[4] = ( getAttributes()[5] + getLevel() ) * 4
                + getAttributes()[6] + 75;
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
     * Prints a summary of Monster's type, level, HP, MP, attributes, and stats.
     */
    public void printStatus()
    {
        String divider = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

        System.out.println( type() + " Lv. " + getLevel() );
        System.out.println( "HP: " + getHealth() + "/" + getStats()[0] + " MP: "
            + getMana() + "/" + getStats()[1] );

        System.out.println( "Attributes" );
        for ( int j = 0; j < 7; j++ )
        {
            System.out.print(
                Combatant.attributeNames[j] + " " + getAttributes()[j] + " " );
        }

        System.out.println( "\nStats" );
        for ( int j = 0; j < Combatant.statNames.length; j++ )
        {
            System.out
                .print( Combatant.statNames[j] + " " + getStats()[j] + " " );
        }

        System.out.println( "\n" + divider );
    }


    public void printDistributionRatios()
    {
        for ( int i = 0; i < 7; i++ )
        {
            System.out.print( Combatant.attributeNames[i] + ": "
                + (int)( distributionRatios()[i] * 700 ) / 100.0 + " " );
        }

    }


    public void printVitals()
    {
        String divider = "~~~~~~~~~~~";

        System.out.println( type() + " Lv. " + getLevel() );
        System.out.println( "HP: " + getHealth() + "/" + getStats()[0] + " MP: "
            + getMana() + "/" + getStats()[1] );
        System.out.println( divider );
    }

}
