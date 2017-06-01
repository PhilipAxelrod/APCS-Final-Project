package JUnitTests.rooms;

import architecture.characters.Player;
import org.junit.Test;
import proceduralGeneration.Room;
import proceduralGeneration.RoomGenerator;

import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;


public class RoomJunit {
    final List<Point> cornersAlive = Arrays.asList(
            new Point(2, 2),
            new Point(0, 0));
    final int cellLength = 10;

    @Test
    public void testCollisionDetection() {
        RoomGenerator roomGenerator = new RoomGenerator(cornersAlive, 3);
        Player player = new Player();
        // move player to alive cell
        player.moveTo(
                cornersAlive.get(0).x * cellLength,
                cornersAlive.get(0).y * cellLength);

        // create floor with no monster and chests (hence floor 0)
        Room room = roomGenerator.generateRoom(0, cellLength, player);

        // Player is at alive cell, so no collision
        assertFalse(room.inCollision(player));

        // move to forbidden region
        player.moveTo(cellLength, cellLength);

        // Player is at dead cell, so yes collision
        assertTrue(room.inCollision(player));
    }
}
