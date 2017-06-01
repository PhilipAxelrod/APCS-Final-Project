package architecture.augmentations.consumables;

import architecture.characters.Player;

/**
 * Represents potions which recover nominal amounts of health and/or mana.
 * Calculation of said amount is handled by this class (based on level). All
 * instantiated potions must use a level value. Type and variability factor can
 * be given or calculated randomly.
 *
 * @author Kevin Liu
 * @version May 7, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class NominalPotion extends RecoveryPotion
{
    private int value;

    private static final double maxFactor = 0.6;

    private static final double[] vitalFactors = { 4, 3, 2.5 };


    /**
     * Instantiates a nominal recovery potion of random type and random
     * variability factor.
     * 
     * @param level
     *            relative strength of potion
     */
    public NominalPotion( int level )
    {
        super();
        assignValue( level );
    }


    /**
     * Sets the recovery value of the potion based on its type and level.
     * 
     * @param level
     *            the level of the potion
     */
    private void assignValue( int level )
    {
        double vitalMax = vitalFactors[getType()] * level;
        value = (int)Math.round( maxFactor * vitalMax * getVar() );
    }


    @Override
    public boolean use( Player player )
    {
        switch ( getType() )
        {
            case 0:
                player.restoreHealth( value );
                break;

            case 1:
                player.restoreMana( value );
                break;

            case 2:
                player.restoreHealth( value );
                player.restoreMana( value );
                break;
        }
        return true;
    }

}
