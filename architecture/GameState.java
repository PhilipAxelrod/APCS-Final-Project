package architecture;

import proceduralGeneration.Cell;

import java.util.List;


public class GameState
{
    public final Cell[][] cells;

    public final List<Combatant> combatants;

    public final Player player;

    public final List<Chest> chests;

    // how big each tile is to generate rooms
    public final int tileLength;


    public GameState(
        Cell[][] cells,
        List<Combatant> combatants,
        Player player,
        List<Chest> chests,
        int tileLength)
    {
        this.cells = cells;
        this.combatants = combatants;
        this.player = player;
        this.chests = chests;
        this.tileLength = tileLength;
    }
}
