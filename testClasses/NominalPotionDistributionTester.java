package testClasses;

import architecture.augmentations.consumables.NominalPotion;

/**
 * TODO Write a one-sentence summary of your class here. TODO Follow it with
 * additional details about its purpose, what abstraction it represents, and how
 * to use it.
 *
 * @author Kevin Liu
 * @version May 7, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: TODO
 */
public class NominalPotionDistributionTester
{
    private static final int count = 20, level = 5, type = 2;


    public static void main( String[] args )
    {
        for ( int i = 5; i < count; i++ )
        {
            new NominalPotion( level, type ).printDetails();
        }
    }

}
