package proceduralGeneration;

public class Cell
{
    Boolean isAlive;
    Boolean willBeAlive = null;

    final int x;
    final int y;

    public Cell(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }
}
