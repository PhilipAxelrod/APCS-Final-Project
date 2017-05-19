package architecture;

import proceduralGeneration.Cell;

import java.util.List;

public class GameState {
    public final Cell[][] cells;
    public final List<Combatant> combatants;

    public GameState(Cell[][] cells, List<Combatant> combatants) {
        this.cells = cells;
        this.combatants = combatants;
    }
}
