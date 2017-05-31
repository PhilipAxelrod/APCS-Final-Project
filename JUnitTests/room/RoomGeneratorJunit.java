package JUnitTests.room;

import architecture.characters.Monster;
import architecture.characters.Player;
import org.junit.Test;
import static org.junit.Assert.*;
import proceduralGeneration.Cell;
import proceduralGeneration.Room;
import proceduralGeneration.RoomGenerator;

import com.sun.javafx.geom.Point2D;

import java.awt.*;
import java.util.*;

public class RoomGeneratorJunit {
    @Test
    public void initCells() {
        RoomGenerator roomGenerator = new RoomGenerator(new LinkedList<>(), 10);
        for (Cell[] row : roomGenerator.cells) {
            for (Cell cell : row) {
                assertTrue(cell.isDead());
                assertTrue(!cell.willBeAlive().isPresent());
            }
        }
    }

    /**
     * Ensure that a generated room has a border of dead cells
     * so that the player cannot escape though an alive cell
     * on the border off the map
     */
    @Test
    public void testBorderDead() {
        RoomGenerator roomGenerator = new RoomGenerator();
        Room generatedRoom = roomGenerator.generateNewRoom(
                0,
                1,
                new Player()
        );

        for (int i = 0; i < 10; i++) {
            assertTrue(!generatedRoom.cells[i][0].isAlive());
            assertTrue(!generatedRoom.cells[0][i].isAlive());

            assertTrue(!generatedRoom.cells[i][roomGenerator.rows - 1].isAlive());
            assertTrue(!generatedRoom.cells[i][roomGenerator.rows - 1].isAlive());
        }
    }

    /**
     * default random seeds in a row through the cornersAlive. After one simulation,
     * the Cell above the cornersAlive should change from alive to dead
     */
    @Test
    public void testSingleSimulationUpdate() {
        RoomGenerator roomGenerator = new RoomGenerator();
        Cell aboveCenterCell = roomGenerator.cells[roomGenerator.rows / 2][roomGenerator.rows / 2 - 1];
        assertTrue(aboveCenterCell.isDead());

        roomGenerator.runSimulation();

        assertTrue(aboveCenterCell.isAlive());
    }

    /**
     * test that Monsters and Player all spawn in different places and in right
     * amounts
     */
    @Test
    public void testSpawning() {
        RoomGenerator roomGenerator = new RoomGenerator();
        Player player = new Player();

        // test for random occurences
        for (int i = 0; i < 1000; i++) {
            Room room = roomGenerator.generateNewRoom(1, 100, player);

            assertTrue(room.monsters.size() == 1);
            assertTrue(room.getChests().size() == 1);

            Point2D playerLoc = player.getPose();
            for (Monster monster : room.monsters) {
                Point2D monsterLoc = monster.getPose();

                double diffX = Math.abs(monsterLoc.x - playerLoc.x);
                double diffY = Math.abs(monsterLoc.y - playerLoc.y);
                assertTrue(diffX >= 0.001 || diffY >= 0.001);
            }
        }
    }

    /**
     * Test that "forbidden rectangles" (Areas where travel is not permitted)
     * are generated for every dead cell, and none for the alive cells
     */
    @Test
    public void testGeneratingForbiddenRectangles() {
        RoomGenerator roomGenerator = new RoomGenerator(
                Arrays.asList(
                        new Point(0, 1),
                        new Point(1, 1),
                        new Point(2, 1)),
                4
        );

        int cellength = 10;

        for (Cell[] row : roomGenerator.cells) {
            for (Cell cell : row) {
                Rectangle rectAtCell = new Rectangle(
                        cell.x * cellength,
                        cell.y * cellength,
                        cellength,
                        cellength
                );

                // Hack to get around that java lambdas do not allow
                // you to reassign to variables in lambdas.
                final boolean[] containsForbidden = {false};
                roomGenerator.getForbiddenRectangles(cellength).forEach(
                    (point2D, rectangles) ->  {
                        containsForbidden[0] |= rectangles.contains(rectAtCell);
                    });

                // There should only exist a forbidden rectangle if the cell is
                // dead
                assertTrue("Cell: " + cell + " rect: " + rectAtCell + " contains: " + containsForbidden[0],
                    containsForbidden[0] == cell.isDead()
                );
            }
        }

    }

}
