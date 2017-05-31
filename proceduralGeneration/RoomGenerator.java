package proceduralGeneration;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import architecture.augmentations.equipment.Chest;
import architecture.characters.Monster;
import architecture.characters.Player;
import architecture.characters.Skeleton;
import com.sun.javafx.geom.Point2D;

/**
 * Handles creating a room with Monsters, Chests, floor layout, and the Player
 * randomly spawned. Uses a modified Conway's game of Life simulation to
 * generate a random floor layout
 *
 * @author Philip Axelrod
 * @version May 8, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class RoomGenerator
{
    // TODO: making this 4 breaks unit tests for generating walls
    public static final int HashTileGridLength = 10;

    final public Cell[][] cells;

    List<Cell> aliveAvailibleCells = new ArrayList<>();

    public final int rows;
    public final int cols;

    private final Point center;


    private void initCells(int rows)
    {

        for ( int i = 0; i < cells.length; i++ )
        {
            for ( int j = 0; j < cells[0].length; j++ )
            {
                cells[i][j] = new Cell( i, j );
            }
        }
    }


    public RoomGenerator( List<Point> initAlive, int rows )
    {
        this.rows = rows;
        this.cols = rows;

        cells = new Cell[rows][rows];
        center = new Point(rows / 2, rows / 2);
        initCells(rows);
        for ( Point point : initAlive )
        {
            cells[point.x][point.y].isAlive = true;
        }
    }


    public RoomGenerator()
    {
        // TODO: hackish!
        this( Arrays.<Point> asList(
                new Point( 5 - 1, 5),
                new Point(5, 5),
                new Point( 5 + 1, 5),
                new Point( 5 + 2, 5 ),
                new Point( 5 + 3, 5 )),
                10);
    }


    /**
     * 
     * Used to decide if a cell is alive
     * 
     * @param numAlive
     * @return
     */
    private boolean simulationRule( int numAlive, Cell currCell )
    {
        if ( ( numAlive == 8 || numAlive == 3 ) && !currCell.isAlive )
        {
            return true;
        }
        return currCell.isAlive && numAlive <= 9 && numAlive >= 1;
    }


    public void runSimulation()
    {
        updateFutureRoomCellStates();
        updateQueuedStates();
    }


    private Cell getRandomAvailibleCell()
    {
        int randomIndex = (int)( Math.random() * aliveAvailibleCells.size() );
        return aliveAvailibleCells.get( randomIndex );
    }


    public void spawnPlayer( Player player, int cellLength )
    {
        Cell randomCell = getRandomAvailibleCell();
        player.moveTo( randomCell.x * cellLength, randomCell.y * cellLength );
        aliveAvailibleCells.remove( randomCell );
    }


    public void spawnEnemies( List<Monster> combatants, int cellLength )
    {
        combatants.forEach( enemy -> {
            Cell randomCell = getRandomAvailibleCell();
            enemy.moveTo( randomCell.x * cellLength,
                randomCell.y * cellLength );

            // make sure that no 2 enemies spawn in the same place
            aliveAvailibleCells.remove( randomCell );
        } );
    }

    public List<Monster> createEnemies(int floor, int cellLength, Player player) {
        int numEnemies = (int) (floor * 1.5);
        List<Monster> ret = new LinkedList<>();
        for ( int i = 0; i < numEnemies; i++ )
        {
            // TODO: enemy distribution
            ret.add( new Skeleton( floor, player ) );
        }
        spawnEnemies( ret, cellLength );
        return ret;
    }


    public ConcurrentLinkedQueue<Chest> createChests( int floor, int cellLength )
    {
        int numChests = (int)( 1.5 * floor );
        ConcurrentLinkedQueue<Chest> ret = new ConcurrentLinkedQueue<Chest>();
        for ( int i = 0; i < numChests; i++ ) {
            Cell randomAliveCell = getRandomAvailibleCell();
            Chest chest = new Chest( floor,
                                     new Point2D( randomAliveCell.x * cellLength,
                                    randomAliveCell.y * cellLength ) );
            ret.add( chest );
            aliveAvailibleCells.remove( randomAliveCell );
        }
        return ret;
    }


    public Rectangle createPortal(int cellLength )
    {
        Cell randomCell = getRandomAvailibleCell();
        Rectangle portal = new Rectangle( randomCell.x * cellLength,
            randomCell.y * cellLength,
            cellLength,
            cellLength );
        aliveAvailibleCells.remove( randomCell );
        return portal;
    }


    private void updateFutureRoomCellStates()
    {
        for ( int row = 0; row < rows; row++ )
        {
            for ( int col = 0; col < cols; col++ )
            {
                int aliveNeighbors = getNeighborsAlive( row, col );
                cells[row][col].willBeAlive = Optional.of(
                        simulationRule( aliveNeighbors, cells[row][col] ));
            }
        }
    }


    public void updateQueuedStates()
    {
        for ( Cell[] row : cells ) {
            for (Cell cell : row) {
                cell.isAlive = cell.willBeAlive.orElse(cell.isAlive);
                cell.willBeAlive = Optional.empty();
            }
        }
        updateAliveAvaibleCells();
    }

    private void updateAliveAvaibleCells() {
        List<Cell> newAliveCells = new ArrayList<Cell>();

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if ( cell.isAlive )
                {
                    newAliveCells.add( cell );
                }
            }
        }
        aliveAvailibleCells = newAliveCells;
    }

    private int getNeighborsAlive( int x, int y )
    {
        int toRet = 0;
        Iterator<Optional<Cell>> neighbors = getNeighbors( x, y );
        while ( neighbors.hasNext() )
        {
            Optional<Cell> currCell = neighbors.next();
            if ( currCell.isPresent() )
            {
                if ( currCell.get().isAlive )
                {
                    toRet++;
                }
            }

        }
        return toRet;
    }


    private Iterator<Optional<Cell>> getNeighbors( int x, int y )
    {
        ArrayList<Optional<Cell>> ret = new ArrayList<Optional<Cell>>();
        for ( int i = -1; i <= 1; i++ )
        {
            for ( int j = -1; j <= 1; j++ )
            {
                // Don't count current cell
                if ( !( i == 0 && j == 0 ) )
                {
                    int neighborX = x + i;
                    int neighborY = y + j;

                    if ( withinWidth( neighborX ) && withinHeight( neighborY ) )
                    {
                        ret.add( Optional.of( cells[neighborX][neighborY] ) );
                    }
                    else
                    {
                        ret.add( Optional.empty() );
                    }
                }
            }
        }

        return ret.iterator();
    }


    private boolean withinWidth( int toCheck )
    {
        return toCheck >= 0 && toCheck < cols;
    }


    private boolean withinHeight( int toCheck )
    {
        return toCheck >= 0 && toCheck < rows;
    }



    static float roundToLowestMultiple( float toRound, int nearest )
    {
        return (float)( (int)toRound / nearest ) * nearest;
    }


    /**
     * split a list of forbiddenAreas into a hashtable of point keys and
     * forbiddenAreas of the "tile"
     * 
     * @param forbiddenAreas
     * @return
     */
    private Hashtable<Point2D, List<Rectangle>> segment(
            List<Rectangle> forbiddenAreas,
            int width)
    {
        Hashtable<Point2D, List<Rectangle>> ret = new Hashtable<>();
        int tileWidth = rows * width / HashTileGridLength;

        for ( Rectangle forbiddenRectangle : forbiddenAreas )
        {
            float x = roundToLowestMultiple( forbiddenRectangle.x, tileWidth );
            float y = roundToLowestMultiple( forbiddenRectangle.y, tileWidth );

            Point2D tileKey = new Point2D( x, y );

            if ( ret.containsKey( tileKey ) )
            {
                ret.get( tileKey ).add( forbiddenRectangle );
            }
            else
            {
                LinkedList<Rectangle> initWallList = new LinkedList<>();
                initWallList.add( forbiddenRectangle );
                ret.put( tileKey, initWallList );
            }
        }

        return ret;
    }


    /**
     * sets all the cells at the border to be dead to ensure that the player cannot
     * escape when a live cell is near the border
     */
    private void killBorders() {
        // vertical border
        for (int col = 0; col < cols; col++) {
            cells[col][0].willBeAlive = Optional.of(false);

            cells[col][rows - 1].willBeAlive = Optional.of(false);
        }

        // horizantal borders
        for (int row = 0; row < rows; row++) {
            cells[0][row].willBeAlive = Optional.of(false);

            cells[cols - 1][row].willBeAlive = Optional.of(false);
        }
        updateQueuedStates();
    }

    // length in pixels
    public Hashtable<Point2D, List<Rectangle>> getForbiddenRectangles(
        final int lengthOfCell )
    {
        List<Rectangle> walls = new LinkedList<>();

        for ( int r = 0; r < rows; r++ )
        {
            for ( int c = 0; c < cols; c++ )
            {
                Cell cell = cells[c][r];
                if ( !cell.isAlive )
                {
                    walls.add(
                            new Rectangle(
                                    cell.x * lengthOfCell,
                                cell.y * lengthOfCell,
                                lengthOfCell,
                                lengthOfCell ) );
                }
            }
        }

        return segment( walls, lengthOfCell );
    }


    /**
     * used when creating new room
     */
    private void killAllCells()
    {
        for ( Cell[] row : cells )
        {
            for ( Cell cell : row )
            {
                cell.isAlive = false;
                cell.willBeAlive = Optional.empty();
            }
        }
    }


    private void plantRandomSeed( int numSeeds )
    {
        for ( int i = 0; i < numSeeds; i++ )
        {
            int randomX = (int) (rows * Math.random());
            int randomY = (int) (rows * Math.random());

            cells[randomX][randomY].isAlive = true;
        }
    }

    private void randomizeCells(int simulationRuns) {
        killAllCells();

        // ensure there are enough seeds so that there are no islands
        plantRandomSeed( rows * rows / 5 );

        for ( int i = 0; i < simulationRuns; i++ )
        {
            runSimulation();
        }

        killBorders();
    }

    public Room generateRoom(int floor, int cellLength, Player player) {
        updateAliveAvaibleCells();

        spawnPlayer(player, cellLength);
        return new Room(
                createEnemies(floor, cellLength, player),
                getForbiddenRectangles(cellLength),
                cellLength,
                createChests(floor, cellLength),
                createPortal(cellLength),
                player,
                cells);
    }

    public Room generateNewRoom(int floor, int cellLength, Player player, int simulationRuns) {
        randomizeCells(simulationRuns);
        return generateRoom(floor, cellLength, player);
    }

    public Room generateNewRoom(int floor, int cellLength, Player player ) {
        return generateNewRoom(floor, cellLength, player, 500);
    }
}
