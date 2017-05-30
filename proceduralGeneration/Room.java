package proceduralGeneration;

import architecture.GameState;
import architecture.characters.Monster;
import architecture.characters.Player;
import com.sun.javafx.geom.Point2D;

import graphicsUtils.GraphicsInterface;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import architecture.augmentations.Message;
import architecture.augmentations.equipment.Chest;
import architecture.characters.Combatant;

/**
 * Handles updating Monsters, Player, Chests, and collision detection
 *
 * @author Philip Axelrod
 * @version May 8, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class Room extends Rectangle
{
    public List<Monster> monsters;
    
    private List<Message> messages = new LinkedList<Message>();

    // TODO: make getters
    public ConcurrentLinkedQueue<Chest> chests;

    private int cellWidth;

    Hashtable<Point2D, List<Rectangle>> forbiddenAreas;

    public final Cell[][] cells;

    Player player;

    Rectangle portal;


    public Room(
        List<Monster> combatants,
        Hashtable<Point2D, List<Rectangle>> forbiddenAreas,
        int tileWidth,
        ConcurrentLinkedQueue<Chest> chests,
        Rectangle portal,
        Player player,
        Cell[][] cells )
    {
        this.monsters = combatants;
        this.forbiddenAreas = forbiddenAreas;
        this.cellWidth = tileWidth;
        this.chests = chests;
        this.portal = portal;
        this.player = player;
        this.cells = cells;
    }


    public boolean atPortal()
    {
        return portal.intersects( player.getBoundingBox() );
    }


    public boolean inCollisionAtPoint( Combatant combatant, Point2D point )
    {

        Point2D topLeftTileKey = new Point2D(
                RoomGenerator.roundToLowestMultiple(
                        point.x,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ),
                RoomGenerator.roundToLowestMultiple(
                        point.y,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ) );

        Point2D topRightTileKey = new Point2D(
                RoomGenerator.roundToLowestMultiple(
                        point.x + combatant.WIDTH,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ),
                RoomGenerator.roundToLowestMultiple(
                        point.y,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ) );

        Point2D bottomLeftTileKey = new Point2D(
                RoomGenerator.roundToLowestMultiple(
                        point.x,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ),
                RoomGenerator.roundToLowestMultiple(
                        point.y + combatant.HEIGHT,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ) );

        Point2D bottomRightTileKey = new Point2D(
                RoomGenerator.roundToLowestMultiple(
                        point.x + combatant.HEIGHT,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ),
                RoomGenerator.roundToLowestMultiple(
                        point.y + combatant.WIDTH,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ) );

        List<Rectangle> emptyList = new LinkedList<>();

        List<Rectangle> forbidenRectangles = (forbiddenAreas.getOrDefault( topLeftTileKey, emptyList ));
        forbidenRectangles.addAll(forbiddenAreas.getOrDefault(topRightTileKey, emptyList));
        forbidenRectangles.addAll(forbiddenAreas.getOrDefault(bottomLeftTileKey, emptyList));
        forbidenRectangles.addAll(forbiddenAreas.getOrDefault(bottomRightTileKey, emptyList));


        for ( Rectangle forbiddenRectangle : forbidenRectangles )
        {
            if ( forbiddenRectangle.intersects(combatant.getBoundingBox()) )
            {
                return true;
            }
        }

        return false;
    }


    public boolean inCollision( Combatant combatant )
    {
        return inCollisionAtPoint( combatant, combatant.getPose() );
    }


    public void update( GraphicsInterface graphicsInterface )
    {
        if ( portal.intersects( player.getBoundingBox() ) )
        {
            player.restoreHealth( player.getStats().getHP() / 2 );
            player.restoreMana( player.getStats().getMP() / 2 );
        }

        player.update( graphicsInterface, this );

        for ( Chest chest : chests ) {
            if (chest.isEmpty()) {
                chests.remove(chest);
                messages.addAll(chest.getMessages());
            }
        }

        monsters.removeIf( Monster::isDead );

        for ( Combatant c : monsters )
        {
            c.run();
        }

        monsters.forEach( combatant -> {
            if ( inCollision( combatant ) ) {
                combatant.stop();
                combatant.resetPoseToPrevios();
            }
        } );

        if ( inCollision( player ) )
        {
            player.stop();
            player.resetPoseToPrevios();
        }

        graphicsInterface.setGameState( new GameState(
                cells,
                monsters,
                player,
                chests,
                player.WIDTH + 50,
                portal,
                messages ) );
    }

    public Hashtable<Point2D, List<Rectangle>> getForbiddenAreas() {
        return forbiddenAreas;
    }
}
