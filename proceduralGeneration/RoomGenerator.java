package proceduralGeneration;

import java.awt.Point;
import java.util.*;

import com.sun.javafx.geom.Line2D;
import com.sun.javafx.geom.Point2D;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RoomGenerator
{
    final Cell[][] cells = new Cell[rows][cols];
    static final int rows = 10;
    static final int cols = rows;
    
    private void initCells(  )
    {
        for ( int i = 0; i < cells.length; i++ )
        {
            for(int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }
    
    public RoomGenerator(ArrayList<Point> initAlive) {
        initCells();
        for ( Point point : initAlive )
        {
            cells[point.x][point.y].isAlive = true;
        }
    }
    
    /**
     * 
     * Used to decide if a cell is alive
     * @param numAlive
     * @return
     */
    private boolean simulationRule( int numAlive, Cell currCell )
    {
        return Math.random() < 0.5;
    }
    
    public void update(  )
    {
        updateFutureRoomCellStates();
        updateQeuedStates();
    }
    
    private void updateFutureRoomCellStates()
    {
        for (int r = 0; r < cols; r++) {
            for(int c = 0; c < rows; c++) {
                int aliveNeighbors = getNeighborsAlive( r, c );
                cells[r][c].willBeAlive = simulationRule( aliveNeighbors, cells[r][c]);
            }
        }
    }
    
    public void updateQeuedStates(  )
    {
        for ( int i = 0; i < cells.length; i++ )
        {
            for ( int j = 0; j < cells[0].length; j++ )
            {
                Cell currCell = cells[i][j];
                currCell.isAlive = currCell.willBeAlive;
                currCell.willBeAlive = null;
            }
        }
    }
    
    private int getNeighborsAlive(int x, int y )
    {
        int toRet = 0;
        Iterator<Optional<Cell>> neighbors = getNeighbors( x, y );
        while ( neighbors.hasNext() )
        {
            Optional<Cell> currCell = neighbors.next();
            if(currCell.isPresent()) {
                if(currCell.get().isAlive) {
                    toRet++;
                }
            }
            
        }
        return toRet;
    }
    
    private Iterator<Optional<Cell>> getNeighbors(int x, int y)
    {
        ArrayList<Optional<Cell>> ret = new ArrayList<Optional<Cell>>();
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= -1; j++) {
                // Don't count current cell
                if(!(i == 0 && j == 0)) {
                    int neighborX = x + i;
                    int neighborY = y + i;
                    
                    if(withinWidth( neighborX ) && withinHeight( neighborY )) {
                        ret.add(Optional.of(cells[neighborX][neighborY]));
                    } else {
                        ret.add( Optional.empty() );
                    }
                }
            }
        }
        
        return ret.iterator();
    }
    
    private boolean withinWidth( int tocheck )
    {
        return tocheck >= 0 && tocheck < cols;
    }
    
    private boolean withinHeight( int toCheck )
    {
        return toCheck >= 0 && toCheck < rows;
    }

    public Optional<Cell> above(int r, int c) {
        throw new NotImplementedException();
    }

    public Optional<Cell> right(int r, int c) {
        throw new NotImplementedException();
    }

    public Optional<Cell> below(int r, int c) {
        throw new NotImplementedException();
    }

    public Optional<Cell> left(int r, int c) {
        throw new NotImplementedException();
    }

    public Hashtable<Point, List<Wall>> getWalls(final int width) {
        ArrayList<Line2D> walls = new ArrayList<Line2D>();
        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[0].length; c++) {
                above(r, c).ifPresent(cell -> {
                    int height = cell.y * width;
                    Point2D leftCorner = new Point2D(
                            cell.x * width,
                            height);
                    Point2D rightCorner = new Point2D(
                            cell.x * width + width,
                            height);

                    walls.add(new Line2D(leftCorner, rightCorner));
                });
            }
        }
    }
}
