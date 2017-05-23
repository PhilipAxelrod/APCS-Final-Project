package architecture;

import proceduralGeneration.Cell;

import java.awt.*;
import java.util.List;


public class GameState
{
    public final Cell[][] cells;

    public final List<Combatant> combatants;

    public final Player player;

    public final List<Chest> chests;

    // how big each tile is to generate rooms
    public final int cellLength;

    public final Rectangle portal;


    public GameState(
        Cell[][] cells,
        List<Combatant> combatants,
        Player player,
        List<Chest> chests,
        int cellLength,
        Rectangle portal)
    {
        this.cells = cells;
        this.combatants = combatants;
        this.player = player;
        this.chests = chests;
        this.cellLength = cellLength;
        this.portal = portal;
    }
}
