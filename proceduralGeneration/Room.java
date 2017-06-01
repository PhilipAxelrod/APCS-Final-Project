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
 * Handles updating Monsters, Player, Chests, and collision detection.
 *
 * @author Philip Axelrod
 * @version May 8, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class Room
{
    public List<Monster> monsters;
    
    private List<Message> messages = new LinkedList<Message>();

    private ConcurrentLinkedQueue<Chest> chests;

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


    /**
     *
     * @return whether the player has reached the portal to the next level
     */
    public boolean atPortal()
    {
        return portal.intersects( player.getBoundingBox() );
    }


    /**
     * Tells if the combatant would be in a collision at the given point.
     * It uses a hashing scheme where it looks in a Hashtable of forbidden
     * areas. They key represents the top left corner of a subdivision of the
     * map, and the value is a list of all forbidden Areas in that subdivision.
     * A list of forbidden areas near the combatant is retrieved by looking in this
     * table with the top left corner the combatant is currently in. This way
     * collision detection only occurs for forbidden areas near the player, and
     * not all of them, saving computation time.
     *
     * @param combatant the {@link Combatant} to check if currently in collision
     * @param point the point he'd be at
     * @return if combatant is in a collision at point
     */
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
                        point.x + Combatant.WIDTH,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ),
                RoomGenerator.roundToLowestMultiple(
                        point.y,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ) );

        Point2D bottomLeftTileKey = new Point2D(
                RoomGenerator.roundToLowestMultiple(
                        point.x,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ),
                RoomGenerator.roundToLowestMultiple(
                        point.y + Combatant.HEIGHT,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ) );

        Point2D bottomRightTileKey = new Point2D(
                RoomGenerator.roundToLowestMultiple(
                        point.x + Combatant.HEIGHT,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ),
                RoomGenerator.roundToLowestMultiple(
                        point.y + Combatant.WIDTH,
                        cellWidth * cells.length / RoomGenerator.HashTileGridLength ) );

        List<Rectangle> emptyList = new LinkedList<>();

        List<Rectangle> forbiddenRectangles = (forbiddenAreas.getOrDefault( topLeftTileKey, emptyList ));

        for ( Rectangle forbiddenRectangle : forbiddenRectangles )
        {
            if ( forbiddenRectangle.intersects(combatant.getBoundingBox()) )
            {
                return true;
            }
        }

        for ( Rectangle forbiddenRectangle : forbiddenAreas.getOrDefault(topRightTileKey, emptyList ))
        {
            if ( forbiddenRectangle.intersects(combatant.getBoundingBox()) )
            {
                return true;
            }
        }

        for ( Rectangle forbiddenRectangle : forbiddenAreas.getOrDefault(bottomLeftTileKey, emptyList ))
        {
            if ( forbiddenRectangle.intersects(combatant.getBoundingBox()) )
            {
                return true;
            }
        }

        for ( Rectangle forbiddenRectangle : forbiddenAreas.getOrDefault(bottomRightTileKey, emptyList ))
        {
            if ( forbiddenRectangle.intersects(combatant.getBoundingBox()) )
            {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param combatant
     * @return
     */
    public boolean inCollision( Combatant combatant )
    {
        return inCollisionAtPoint( combatant, combatant.getPose() );
    }


    /**
     * updates the list of Monsters, Chests, and Player  by calling their
     * respective update methods. If a Combatant or a Player is in a collision
     * their velocity is set to 0 and position reset to their previous,
     * none colliding position. It removes all Chests and Monsters that
     * have been opened or have died.
     *
     * @param graphicsInterface GraphicsInterface needed by Player to get
     *                          information about the keyboard
     */
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

        for ( Monster c : monsters )
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
                Combatant.WIDTH + 50,
                portal,
                messages ) );
    }

    public Hashtable<Point2D, List<Rectangle>> getForbiddenAreas() {
        return forbiddenAreas;
    }

    public ConcurrentLinkedQueue<Chest> getChests() {
        return chests;
    }
}
