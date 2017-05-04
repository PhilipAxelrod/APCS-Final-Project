package architecture;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;


public class Player extends Combatant
{
    /**
     * The ratio between level-up requirements of level n and level n-1;
     */
    public static final double expGrowth = 1.5;

    /**
     * Level-up requirement of starting level
     */
    public static final int baseExp = 100;

    private int exp = 0, attributePoints = 0, expLimit;

    private List<Item> inventory = new LinkedList<Item>();

    private static final int[] startingAttributes = { 14, 14, 9, 9, 7, 9, 4 };


    public Player()
    {
        // Set starting level and attributes
        setLevel( 5 );
        setAttributes( startingAttributes );
        updateStats();
        setExpLimit();

        // Set starting equipment

    }


    public boolean gainExp( int exp )
    {
        this.exp += exp;
        if ( exp >= expLimit )
        {
            levelUp();
            return true;
        }
        return false;
    }


    public void setExpLimit()
    {
        expLimit = (int)Math
            .round( baseExp * Math.pow( expGrowth, getLevel() - 5 ) );
    }


    /**
     * Called when player levels up; level is incremented, 5 attribute points
     * (AP) are added, and 1/2 of HP and MP is restored. Exp must exceed
     * expLimit.
     */
    public void levelUp()
    {
        setLevel( getLevel() + 1 );
        attributePoints += 5;
        restoreHealth( getStats()[0] / 2 );
        restoreMana( getStats()[1] / 2 );

        exp -= expLimit;
        setExpLimit();

        if ( exp >= expLimit )
            levelUp();

    }


    public boolean assignAttributePoint( int attribute )
    {
        if ( attribute < 0 || attribute >= 7 )
            throw new InvalidParameterException(
                "attribute must fall between 0 and 6 inclusive" );

        if ( attributePoints <= 0 )
            return false;

        getAttributes()[attribute]++;
        attributePoints--;
        updateStats();
        return true;
    }


    public void acquire( Item item )
    {
        inventory.add( item );
    }


    public boolean discard( Item item )
    {
        return inventory.remove( item );
    }


    public boolean use( Consumable consumable )
    {
        for ( Item item : inventory )
        {
            if ( item instanceof Consumable && item.equals( consumable ) )
            {
                ( (Consumable)item ).use();
                discard( item );
                return true;
            }
        }
        return false;
    }


    @Override
    public void run()
    {
        // TODO Auto-generated method stub

    }


    @Override
    public void death()
    {
        // TODO Auto-generated method stub

    }

}
