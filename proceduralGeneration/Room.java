package proceduralGeneration;

import architecture.Player;
import com.sun.javafx.geom.Point2D;

import graphicsUtils.GraphicsInterface;
import java.awt.*;
import java.util.*;
import java.util.List;

import architecture.Chest;
import architecture.Combatant;


public class Room extends Rectangle
{
    ArrayList<Combatant> combatants;

    ArrayList<Chest> chests;

    private final int tileWidth;

    Hashtable<Point2D, List<Rectangle>> walls;

    Cell[][] cells;
    // static final int rows = 1000;
    // static final int cols = rows;

    static GraphicsInterface graphicsInterface;

    Player player;
    Rectangle portal;
    public Room(
            ArrayList<Combatant> combatants,
            Hashtable<Point2D, List<Rectangle>> walls,
            int tileWidth,
            ArrayList<Chest> chests,
            Rectangle portal, Player player)
    {
        this.combatants = combatants;
        this.walls = walls;
        this.tileWidth = tileWidth;
        this.chests = chests;
        this.portal = portal;
        this.player = player;
    }

    public Rectangle getPortal() {
        return portal;
    }


    public boolean inCollisionAtPoint(Combatant combatant, Point2D point) {

        Point2D tileKey = new Point2D(
                RoomGenerator.roundToLowestMultiple( point.x, tileWidth ),
                RoomGenerator.roundToLowestMultiple( point.y, tileWidth ) );

        List<Rectangle> tileWalls = walls.get( tileKey );

        if ( tileWalls != null )
        {
            for ( Rectangle forbiddenTile : tileWalls )
            {
                if ( forbiddenTile.intersects(
                        point.x,
                        point.y,
                        combatant.WIDTH,
                        combatant.HEIGHT ) )
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean inCollision(Combatant combatant) {
        return inCollisionAtPoint(combatant, combatant.getPose());
    }

    public void update()
    {
        if (portal.intersects(player.getBoundingBox())) {
            System.out.println("yay, reached portal");
            player.restoreHealth(player.getStats()[0] / 2);
            player.restoreMana(player.getStats()[1] / 2);
        }

        for ( Combatant c : combatants )
        {
            c.run();
        }

        combatants.forEach( combatant -> {
            if (inCollision(combatant)) {
                combatant.resetPoseToPrevios();
            }
        });
    }


    private static int randomInt( int min, int max )
    {
        int range = max - min + 1;
        return min + (int)( range * Math.random() );
    }


    public static void initGraphics()
    {
        if ( graphicsInterface == null )
        {
            graphicsInterface = new GraphicsInterface();
        }
    }


    @Deprecated
    public void render( GraphicsInterface graphicsInterface )
    {
        graphicsInterface.loadSprite( "Dirt_Floor.png" );

        int side = 100;

        int x = randomInt( 1, 500 );
        int y = randomInt( 1, 500 );

        for ( int i = 0; i < 10; i++ )
        {
            int leftRightTopDown = randomInt( 1, 4 );

            switch ( leftRightTopDown )
            {
                case 1:
                    y = y - side;
                    break;
                case 2:
                    x = x + side;
                    break;
                case 3:
                    y = y + side;
                    break;
                case 4:
                    x = x - side;
            }

            // graphicsInterface.drawImage(1, 1, side, x, y, );
        }
    }


    @Deprecated
    public static void render( Cell[][] cell )
    {
        graphicsInterface.loadSprite( "Dirt_Floor.png" );

        int side = 100;

        for ( int i = 0; i < cell.length; i++ )
        {
            for ( int j = 0; j < cell[0].length; j++ )
            {
                if ( cell[i][j].isAlive() )
                {
                    // graphicsInterface.drawImage( 1, 1, side, i * side, j *
                    // side, );
                }
            }
        }
    }


    public static void main( String[] args )
    {
        RoomGenerator room = new RoomGenerator( new ArrayList<Point>() );
        for ( int i = 0; i < 50; i++ )
        {
            room.update();
        }

        initGraphics();
        render( room.cells );
        //
        // while (true) {
        // if (graphicsInterface.)
        // }
    }
}
