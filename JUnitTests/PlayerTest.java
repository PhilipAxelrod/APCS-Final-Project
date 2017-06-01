package JUnitTests;


import architecture.characters.Player;
import org.junit.Test;
import com.sun.javafx.geom.Point2D;

import static junit.framework.TestCase.*;

public class PlayerTest {

    @Test
    public void constructor()
    {
        Player player = new Player();
        assertEquals(new Point2D(0, 0), player.getPose());
    }

    @Test
    public  void levelUp()
    {
        Player player = new Player();
        int level = player.getLevel();
        player.gainExp(100);
        assertTrue(player.getLevel() > level);
        level = player.getLevel();
        player.gainExp(100000);
        assertTrue(player.getLevel() > level + 1);
    }
}
