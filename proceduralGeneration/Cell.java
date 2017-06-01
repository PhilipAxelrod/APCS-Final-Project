package proceduralGeneration;

import java.util.Optional;

/**
 * Represents a Cell in conways game of life. Has a dead and alive state
 * a queued, future dead and alive state, and an x,y position
 */
public class Cell
{
    boolean isAlive;
    Optional<Boolean> willBeAlive = Optional.empty();

    public final int x;
    public final int y;

    public Cell(int x, int y) {
        isAlive = false;
        willBeAlive = Optional.empty();

        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public boolean isDead() {
        return !isAlive;
    }

    public Optional<Boolean> willBeAlive() {
        return willBeAlive;
    }

    @Override
    public String toString() {
        return "x: "  + x  + " y: " + y + " alive: " + isAlive;
    }
}
