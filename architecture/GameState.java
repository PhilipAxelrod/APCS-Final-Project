package architecture;

import proceduralGeneration.Cell;

import java.util.List;

public class GameState {
    public final Cell[][] cells;
    public final List<Combatant> combatants;
    public final Player player;

    public GameState(Cell[][] cells, List<Combatant> combatants, Player player) {
        this.cells = cells;
        this.combatants = combatants;
        this.player = player;
    }
}
