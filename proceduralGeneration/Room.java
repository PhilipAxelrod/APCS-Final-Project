package proceduralGeneration;

import architecture.Monster;
import architecture.Player;
import com.sun.istack.internal.NotNull;
import com.sun.javafx.geom.Point2D;

import graphicsUtils.GraphicsInterface;
import java.awt.*;
import java.util.*;
import java.util.List;

import architecture.Chest;
import architecture.Combatant;


public class Room extends Rectangle
{
    public List<Monster> combatants;

    public List<Chest> chests;

    private int cellWidth;

    Hashtable<Point2D, List<Rectangle>> forbiddenCells;

    public Cell[][] cells;
    // static final int rows = 1000;
    // static final int cols = rows;

    static GraphicsInterface graphicsInterface;

    Player player;
    Rectangle portal;
    public Room(
            List<Monster> combatants,
            Hashtable<Point2D,
            List<Rectangle>> forbiddenCells,
            int tileWidth,
            List<Chest> chests,
            Rectangle portal,
            Player player,
            Cell[][] cells)
    {
        this.combatants = combatants;
        this.forbiddenCells = forbiddenCells;
        this.cellWidth = tileWidth;
        this.chests = chests;
        this.portal = portal;
        this.player = player;
        this.cells = cells;
    }

    // get around the fact that java doesn't allow reassigment in
    // anonymous classes
    public void assignSelfTo(Room room){
        this.combatants = room.combatants;
        this.forbiddenCells = room.forbiddenCells;
        this.cellWidth = room.cellWidth;
        this.chests = room.chests;
        this.portal = room.portal;
        this.player = room.player;
    }

    public Rectangle getPortal() {
        return portal;
    }

    public boolean atPortal() {
        return portal.intersects(player.getBoundingBox());
    }

    public boolean inCollisionAtPoint(Combatant combatant, Point2D point) {

        Point2D tileKey = new Point2D(
                RoomGenerator.roundToLowestMultiple( point.x, cellWidth * cells.length),
                RoomGenerator.roundToLowestMultiple( point.y, cellWidth * cells.length) );

        List<Rectangle> tileWalls = forbiddenCells.get( tileKey );

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
                    combatant.stop();
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
        player.run();

        combatants.removeIf( Combatant::isDead );

        combatants.forEach( combatant -> {
            if (inCollision(combatant)) {
                combatant.resetPoseToPrevios();
            }
        });

        if (inCollision(player)) {
            player.resetPoseToPrevios();
        }

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
            room.runSimulation();
        }

        initGraphics();
        render( room.cells );
        //
        // while (true) {
        // if (graphicsInterface.)
        // }
    }
}
