package architecture;

import architecture.augmentations.Chest;
import architecture.augmentations.Monster;
import architecture.characters.Player;
import proceduralGeneration.Cell;

import java.awt.*;
import java.util.List;


public class GameState
{
    public final Cell[][] cells;

    public final List<Monster> monsters;

    public final Player player;

    public final List<Chest> chests;

    // how big each tile is to generate rooms
    public final int cellLength;

    public final Rectangle portal;


    public GameState(
        Cell[][] cells,
        List<Monster> monsters,
        Player player,
        List<Chest> chests,
        int cellLength,
        Rectangle portal)
    {
        this.cells = cells;
        this.monsters = monsters;
        this.player = player;
        this.chests = chests;
        this.cellLength = cellLength;
        this.portal = portal;
    }
}
