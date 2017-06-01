package architecture.characters;

import architecture.augmentations.*;
import architecture.augmentations.consumables.Potion;
import architecture.augmentations.equipment.Armor;
import architecture.augmentations.equipment.Chest;
import architecture.augmentations.equipment.Weapon;

import com.sun.javafx.geom.Point2D;
import graphicsUtils.GraphicsInterface;
import proceduralGeneration.Room;

import java.awt.*;
import java.util.List;


/**
 * The class of the main character, the protagonist, of the game. Player handles
 * inventory management, growth (leveling up), etc.
 *
 * @author Kevin Liu
 * @version May 8, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class Player extends Combatant implements Updateable
{

    /**
     * The ratio between level-up requirements of level n and level n-1;
     */
    public static final double expGrowth = 1.5;

    /**
     * Level-up requirement of starting level
     */
    public static final int baseExp = 100;

    private int exp = 0, expLimit;

    // [Headgear, Chestpiece, Legpiece, Gloves, Shoes]
    private Armor[] equippedArmor = new Armor[5];

    private Weapon equippedWeapon;

    private static final int[] startingAttributes = { 14, 14, 9, 9, 7, 9, 4 };





    /**
     * @param startPose the starting location
     */
    public Player( Point2D startPose )
    {
        super();
        // Set starting level and attributes
        setLevel( 1 );
        setBaseAttributes( startingAttributes );

        // Set starting equipment
        equippedWeapon = new Weapon();
        for ( int i = 0; i < equippedArmor.length; i++ )
            equippedArmor[i] = new Armor( 1, i, 1 );

        updateAttributes();

        setExpLimit();
        setHealthFull();
        setManaFull();
        this.topLeftCorner = startPose;
    }


    @Override
    public void update( GraphicsInterface graphicsInterface, Room room )
    {
        run();
        int xToMoveBy = 0;
        int yToMoveBy = 0;
        if ( graphicsInterface.isArDown() )
        {
            yToMoveBy += 20;
        }
        if ( graphicsInterface.isArUp() )
        {
            yToMoveBy += -20;
        }
        if ( graphicsInterface.isArLeft() )
        {
            xToMoveBy += -20;
        }
        if ( graphicsInterface.isArRight() )
        {
            xToMoveBy += 20;
        }
        if ( graphicsInterface.eKey() )
        {
            restoreHealth( 100000 );
        }
        if ( graphicsInterface.isQPressed() )
        {
            if ( canAttack )
            {
                getWeapon().rotate( 360 );

                room.monsters.forEach( monster -> {
                    if ( isInRange( monster ) )
                    {
                        attack( monster );
                    }
                } );
            }
            for ( Chest chest : room.getChests() )
            {
                if ( canOpen( chest ) )
                    chest.acquireAll( this );
            }
        }

        accelerate( xToMoveBy, yToMoveBy );
        move();
        getWeapon().updateRotation();
    }


    @Override
    public void render( GraphicsInterface graphicsInterface, Graphics g )
    {
        graphicsInterface.loadSprite( "ConcretePowderMagenta.png" );

        int thisX = (int)this.getPose().x;
        int thisY = (int)this.getPose().y;

        graphicsInterface.placeImage( thisX, thisY, WIDTH, HEIGHT, g );

        double fractionOfHealth = (double)( getHealth() ) / getStats().getHP();

        graphicsInterface.loadSprite( "healthbar.png" );
        // health bar
        graphicsInterface.placeImage( thisX,
            thisY - HEIGHT / 8,
            (int)( WIDTH * fractionOfHealth ),
            10,
            g );

        double fractionOfMana = (double)( getMana() ) / getStats().getMP();
        graphicsInterface.loadSprite( "manabar.png" );

        // mana bar
        graphicsInterface.placeImage( thisX,
            thisY - HEIGHT / 4,
            (int)( WIDTH * fractionOfMana ),
            10,
            g );

        double fractionOfAction = (double)( getActionBar() ) / actionLimit;

        // mana bar
        graphicsInterface.loadSprite( "actionbar.png" );
        graphicsInterface.placeImage( thisX,
            thisY - 3 * HEIGHT / 8,
            (int)( WIDTH * fractionOfAction ),
            10,
            g );

    }


    /**
     * Adds exp to the Player and calls levelUp() if exp exceeds limit.
     * 
     * @param exp
     * @return true if levelUp() is called
     */
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


    /**
     * Sets the new exp limit after a level up, scaling exponentially with
     * level.
     */
    private void setExpLimit()
    {
        expLimit = (int)Math
            .round( baseExp * Math.pow( expGrowth, getLevel() - 1 ) );
    }


    /**
     * Called when player levels up; level is incremented, SPD is increased, and
     * 1/2 of HP and mana is restored. Exp must exceed expLimit.
     */
    public void levelUp()
    {
        level++;
        // SPD
        baseAttributes[3]++;
        restoreHealth( getStats().getHP() / 2 );
        restoreMana( getStats().getMP() / 2 );

        exp -= expLimit;
        setExpLimit();

        if ( exp >= expLimit )
            levelUp();

        updateAttributes();
    }


    @Override
    public void updateAttributes()
    {
        resetAttributes();
        // Add equippedWeapon boost

        if ( equippedWeapon != null )
        {
            for ( int i = 0; i < 7; i++ )
                getModifiedAttributes()[i] += equippedWeapon
                    .getTotalBoosts()[i];
        }
        // printStatus();

        // Add equippedArmor boosts
        for ( Armor armor : equippedArmor )
            if ( armor != null )
                for ( int i = 0; i < 7; i++ )
                    getModifiedAttributes()[i] += armor.getTotalBoosts()[i];

        // printStatus();

        updateStats();

    }


    /**
     * Adds a List of boosts to player equipment OR heals the player. This
     * method is called when the Player opens a Chest.
     * 
     * @param contents
     *            boosts or health recovery
     *
     */
    public void acquireAll( List<Item> contents )
    {
        for ( Item item : contents )
        {
            if ( item instanceof Weapon )
            {
                equippedWeapon.addBoost( new AttributeBoost( 1, 1 ) );
                equippedWeapon.addBoost( new AttributeBoost( 5, 1 ) );
            }
            else if ( item instanceof Armor )
            {
                Armor armor = (Armor)( item );
                equippedArmor[armor.getType()].addDefense( 1 );
                equippedArmor[armor.getType()]
                    .addBoost( new AttributeBoost( /* spd */3, 1 ) );
                if ( Math.random() > 0.5 )
                    equippedArmor[armor.getType()]
                        .addBoost( new AttributeBoost( 4, 1 ) );
            }
            else if ( item instanceof Potion )
            {
                restoreHealth( getStats().getHP() / 2 );
            }
            updateAttributes();
        }
        contents.clear();
    }


    @Override
    protected void updateStats()
    {
        super.updateStats();

        // ATK = (STR or INT) + MT
        if ( equippedWeapon != null )
        {
            if ( !equippedWeapon.isMagicDamage() )
                stats.setATK( stats.getATK() + getModifiedAttributes()[0] );
            else
                stats.setATK( stats.getATK() + getModifiedAttributes()[1] );

            stats.setATK( stats.getATK() + equippedWeapon.getMight() );
        }
        else
            stats.setATK( getModifiedAttributes()[0] );

        // DEF = sumOf(defense of equippedArmor)
        for ( Armor armor : equippedArmor )
            if ( armor != null )
                stats.setDEF( stats.getDEF() + armor.getDefense() );

        // ACC = (DEX or WIS) * accuracyFactor + ACC(equippedWeapon)
        if ( equippedWeapon != null )
        {
            stats.setACC( equippedWeapon.getAccuracy() );
            if ( equippedWeapon.isMagicDamage() )
                stats.setACC( (int)Math.round( stats.getACC()
                    + getModifiedAttributes()[2] * accuracyFactor ) );
            else
                stats.setACC( (int)Math.round( stats.getACC()
                    + getModifiedAttributes()[5] * accuracyFactor ) );
        }
        else
            stats.setACC( 80 );
        // printStatus();
    }


    /**
     * Returns the equippedWeapon.
     * 
     * @return equippedWeapon
     */
    public Weapon getWeapon()
    {
        return equippedWeapon;
    }


    @Override
    public int getRange()
    {
        return 450;
    }


    /**
     * Tests if Player can open given chest
     * 
     * @param chest
     *            the chest in question
     * @return true if chest is within 100 units, otherwise false
     */
    public boolean canOpen( Chest chest )
    {
        return ( Point2D.distance( topLeftCorner.x,
            topLeftCorner.y,
            chest.getPose().x,
            chest.getPose().y ) <= 100 );
    }

}
