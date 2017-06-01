package architecture;

import architecture.augmentations.Message;
import architecture.augmentations.equipment.Chest;
import architecture.characters.Monster;
import architecture.characters.Player;
import proceduralGeneration.Cell;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * The current state of the game. The GameState class contains all active
 * elements of the game: all Chests, Monsters, Messages, the Room, the PLayer,
 * etc.
 *
 * @author Philip Axelrod
 * @version May 31, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class GameState
{
    public final Cell[][] cells;

    public final List<Monster> monsters;

    public final Player player;

    public final ConcurrentLinkedQueue<Chest> chests;

    // how big each tile is to generate rooms
    public final int cellLength;

    public final Rectangle portal;

    public final List<Message> messages;


    public GameState(
        Cell[][] cells,
        List<Monster> monsters,
        Player player,
        ConcurrentLinkedQueue<Chest> chests,
        int cellLength,
        Rectangle portal,
        List<Message> messages )
    {
        this.cells = cells;
        this.monsters = monsters;
        this.player = player;
        this.chests = chests;
        this.cellLength = cellLength;
        this.portal = portal;
        this.messages = messages;
    }
}
