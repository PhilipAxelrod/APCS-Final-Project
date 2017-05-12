package proceduralGeneration;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

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
//                System.out.println( "i " + i + " j" + j  );
                cells[i][j] = new Cell();
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
}
