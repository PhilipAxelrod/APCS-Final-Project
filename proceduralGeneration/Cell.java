package proceduralGeneration;

import org.junit.Test;

import java.util.Optional;

public class Cell
{
    Boolean isAlive;
    Optional<Boolean> willBeAlive = Optional.empty();

    final int x;
    final int y;

    public Cell(int x, int y) {
        isAlive = false;
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Optional<Boolean> willBeAlive() {
        return willBeAlive;
    }

    @Override
    public String toString() {
        return "x "  + x  + " y " + y + " " + isAlive;
    }
}
