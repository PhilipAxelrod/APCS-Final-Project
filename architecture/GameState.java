package architecture;

import architecture.augmentations.Message;
import architecture.augmentations.equipment.Chest;
import architecture.characters.Monster;
import architecture.characters.Player;
import proceduralGeneration.Cell;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


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
