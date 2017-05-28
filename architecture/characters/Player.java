package architecture.characters;

import architecture.augmentations.*;
import architecture.augmentations.consumables.Consumable;
import architecture.augmentations.consumables.Potion;
import architecture.augmentations.equipment.Chest;
import architecture.augmentations.weapons.Sword;
import architecture.augmentations.weapons.Weapon;
import com.sun.javafx.geom.Point2D;
import graphicsUtils.GraphicsInterface;
import proceduralGeneration.Room;

import java.awt.*;
import java.security.InvalidParameterException;
import java.util.LinkedList;
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
            restoreHealth( 100 );
        }
        if ( graphicsInterface.isQPressed() )
        {
            // TODO: make can attack (action mare better) so that is not needed
            if ( canAttack )
            {
                getWeapon().rotate(360);


                room.monsters.forEach( monster -> {
                    if ( isInRange( monster ) )
                    {
                        attack( monster );
                    }
                } );
            }
            for ( Chest chest : room.chests )
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
    public void attack( Combatant defender )
    {
        super.attack( defender );
    }


    @Override
    public void render( GraphicsInterface graphicsInterface, Graphics g )
    {
        graphicsInterface.loadSprite( "ConcretePowderMagenta.png" );

        int thisX = (int)this.getPose().x;
        int thisY = (int)this.getPose().y;

        graphicsInterface.placeImage( "ConcretePowderMagenta.png",
            thisX,
            thisY,
            WIDTH,
            HEIGHT,
            g );

        double fractionOfHealth = (double)( getHealth() ) / getStats().getHP();

        graphicsInterface.loadSprite( "healthbar.png" );
        // health bar
        graphicsInterface.placeImage( "healthbar.png",
            thisX,
            thisY - HEIGHT / 8,
            (int)( WIDTH * fractionOfHealth ),
            10,
            g );

        double fractionOfMana = (double)( getMana() ) / getStats().getMP();
        graphicsInterface.loadSprite( "manabar.png" );

        // mana bar
        graphicsInterface.placeImage( "manabar.png",
            thisX,
            thisY - HEIGHT / 4,
            (int)( WIDTH * fractionOfMana ),
            10,
            g );

        double fractionOfAction = (double) (getActionBar()) / actionLimit;

        // mana bar
        graphicsInterface.loadSprite( "actionbar.png" );
        graphicsInterface.placeImage( "actionbar.png",
            thisX,
            thisY - 3 * HEIGHT / 8,
            (int)( WIDTH * fractionOfAction ),
            10,
            g );

    }

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

    // [Headgear, Chestpiece, Legpiece, Gloves, Shoes]
    private Armor[] equippedArmor = new Armor[5];

    private Weapon equippedWeapon;

    private static final int[] startingAttributes = { 14, 14, 9, 9, 7, 9, 4 };


    /**
     * Creates a default starting player.
     */
    public Player()
    {
        super();
        // Set starting level and attributes
        setLevel( 1 );
        // Set starting equipment

        // TODO: more intelligent weapons?
        equippedWeapon = new Sword();

        for ( int i = 0; i < equippedArmor.length; i++ )
            equippedArmor[i] = new Armor( 1, i, 1 );

        setBaseAttributes( startingAttributes );
        // printStatus();
        updateAttributes();
        setExpLimit();
        setHealthFull();
        setManaFull();
    }


    public Player( Point2D startPose, int initHealth )
    {
        this();
        this.topLeftCorner = startPose;
        this.restoreHealth( initHealth );
    }


    public Player( Point2D startPose )
    {
        this();
        this.topLeftCorner = startPose;
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
     * Called when player levels up; level is incremented, 5 attribute points
     * (AP) are added, and 1/2 of HP and mana is restored. Exp must exceed
     * expLimit.
     */
    public void levelUp()
    {
        setLevel( getLevel() + 1 );
        attributePoints += 1;
        restoreHealth( getStats().getHP() / 2 );
        restoreMana( getStats().getMP() / 2 );

        exp -= expLimit;
        setExpLimit();

        if ( exp >= expLimit )
            levelUp();

    }


    /**
     * Increases a baseAttribute by a single point at the cost of an attribute
     * point.
     * 
     * @param attribute
     *            index of the attribute
     */
    public void assignAttributePoint( int attribute )
    {
        if ( attribute < 0 || attribute >= 7 )
            throw new InvalidParameterException(
                "attribute index must fall between 0 and 6, inclusive" );

        if ( attributePoints > 0 )
        {
            getBaseAttributes()[attribute]++;
            attributePoints--;
            updateAttributes();
        }
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
     * Adds a List of boosts to player equipment OR heals the player.
     * 
     * @param contents
     *            boosts or health recovery
     *
     */
    public void acquireAll( List<Item> contents )
    {
        for ( Item item : contents )
        {
            System.out.println( item );
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
     * Equips a weapon from the inventory, simultaneously removing any
     * previously equipped weapon back to the inventory.
     * 
     * @param weapon
     *            weapon to equip
     */
    public void equip( Weapon weapon )
    {
        if ( inventory.contains( weapon ) )
        {
            unEquipWeapon();

            equippedWeapon = weapon;
            discard( weapon );

            updateAttributes();
        }
    }


    /**
     * Equips a piece of armor from the inventory, simultaneously removing any
     * conflicting armor back to the inventory.
     * 
     * @param armor
     *            armor to equip
     */
    public void equip( Armor armor )
    {
        if ( inventory.contains( armor ) )
        {
            unEquipArmor( armor.getType() );

            equippedArmor[armor.getType()] = armor;
            discard( armor );

            updateAttributes();
        }
    }


    /**
     * Returns a piece of equipped armor of type type back to the inventory.
     * 
     * @param type
     *            the slot/type of armor to remove
     */
    public void unEquipArmor( int type )
    {
        if ( equippedArmor[type] != null )
        {
            acquire( equippedArmor[type] );
            equippedArmor[type] = null;

            updateAttributes();
        }
    }


    /**
     * Returns the equipped weapon back to the inventory.
     */
    public void unEquipWeapon()
    {
        if ( equippedWeapon != null )
        {
            acquire( equippedWeapon );
            equippedWeapon = null;

            updateAttributes();
        }
    }


    /**
     * Adds an item to the inventory of Player
     * 
     * @param item
     *            item to be added
     */
    public void acquire( Item item )
    {
        inventory.add( item );
    }


    /**
     * Adds a Collection of items to the inventory of Player
     * 
     * @param items
     *            items to be added
     */
    public void acquire( List<Item> items )
    {
        if ( items != null )
            inventory.addAll( items );
    }


    /**
     * Removes an item from the inventory
     * 
     * @param item
     *            item to be removed
     * @return true if item removed, false if item not found
     */
    public boolean discard( Item item )
    {
        return inventory.remove( item );
    }


    /**
     * Uses a consumable in Player inventory
     * 
     * @param consumable
     *            consumable to be used
     * @return true if used, false if consumable not found
     */
    public boolean use( Consumable consumable )
    {

        if ( inventory.contains( consumable ) )
        {
            consumable.use( this );
            discard( consumable );
            return true;
        }
        return false;
    }


    @Override
    public void run()
    {
        super.run();
    }


    @Override
    public void death()
    {
        // TODO Auto-generated method stub

    }


    public Weapon getWeapon()
    {
        return equippedWeapon;
    }


    @Override
    public int getRange()
    {
        if ( equippedWeapon == null )
            return 1;

        else
            return equippedWeapon.getRange();
    }


    public boolean canOpen( Chest chest )
    {
        return ( Point2D.distance( topLeftCorner.x,
            topLeftCorner.y,
            chest.getPose().x,
            chest.getPose().y ) <= 100 );
    }


    /**
     * @return Returns the equippedArmor.
     */
    public Armor[] getEquippedArmor()
    {
        return equippedArmor;
    }

}
