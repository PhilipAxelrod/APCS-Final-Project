package architecture.characters;

import graphicsUtils.GraphicsInterface;

import java.awt.*;

import com.sun.javafx.geom.Point2D;


/**
 * A balanced, physical monster. All stat assumptions are based on this
 * monster's scaling.
 *
 * @author Kevin Liu
 * @version May 11, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class Skeleton extends Monster
{
    @Override
    public void render( GraphicsInterface graphicsInterface, Graphics g )
    {
        graphicsInterface.loadSprite("skeleton.PNG");

        int thisX = (int)this.getPose().x;
        int thisY = (int)this.getPose().y;

        graphicsInterface.placeImage("skeleton.PNG",
            thisX,
            thisY,
            WIDTH,
            HEIGHT,
            g );

        double fractionOfHealth =  (double) (getHealth()) / getStats().getHP();

        graphicsInterface.loadSprite("healthbar.png");
        // health bar
        graphicsInterface.placeImage("healthbar.png",
                thisX,
                thisY - HEIGHT / 8,
                (int)( WIDTH * fractionOfHealth ),
                10,
                g );

        double fractionOfMana = (double) (getMana()) / getStats().getMP();
        graphicsInterface.loadSprite("manabar.png");
        // mana bar
        graphicsInterface.placeImage(
                "manabar.png",
                thisX,
                thisY - HEIGHT / 4,
                (int) (WIDTH * fractionOfMana),
                10,
                g
        );
    }


    /**
     * @param level
     *            level of Skeleton
     * @param player
     *            the player
     */
    public Skeleton( int level, Player player )
    {
        super( level, player );
    }


    public Skeleton( int level, Player player, Point2D loc )
    {
        super( level, player, loc );
    }


    @Override
    public boolean isMagicDamage()
    {
        return false;
    }


    @Override
    public double defenseFactor()
    {
        return 3.0;
    }


    @Override
    public double[] attributeDistribution()
    {
        double[] distribution = { 20, 2, 15, 15, 10, 3, 5 };
        return distribution.clone();
    }


    @Override
    public String type()
    {
        return "Skeleton";
    }


    @Override
    public int getRange()
    {
        return 100;
    }

}
