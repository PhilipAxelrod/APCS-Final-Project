package proceduralGeneration;

public class Cell
{
    Boolean isAlive;
    Boolean willBeAlive = null;

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

    public boolean willBeAlive() {
        return willBeAlive;
    }

    @Override
    public String toString() {
        return "x "  + x  + " y " + y + " " + isAlive;
    }
}
