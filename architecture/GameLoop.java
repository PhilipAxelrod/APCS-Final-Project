package architecture;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.javafx.geom.Point2D;
import graphicsUtils.GraphicsInterface;
import graphicsUtils.ImageUtils;
import proceduralGeneration.Room;
import proceduralGeneration.RoomGenerator;

public class GameLoop
{

    public static void main( String[] args )
    {
            Timer timer = new Timer();

            ArrayList<Chest> chests = new ArrayList<Chest>();
            ArrayList<Combatant> figheters = new ArrayList<Combatant>();
            final Player player = new Player(new Point2D(0, 0));
            figheters.add(new Skeleton( 5, null ));
            figheters.add(player);

            ArrayList<Point> emptyList = new ArrayList<>();
            RoomGenerator roomGenerator = new RoomGenerator(emptyList);

            for(int i = 0; i < 50; i++) {
                roomGenerator.update();
            }

            final Room room = new Room(figheters, roomGenerator.getWalls(10), 10);

            System.out.println("just scheduled!");

            final GraphicsInterface graphicsInterface = new GraphicsInterface();

            try {
                graphicsInterface.setSprite(
                        ImageUtils.loadBufferedImage("Dirt_Floor.png"));
            } catch (IOException e) {
                throw new RuntimeException("Image failed to load");
            }

            graphicsInterface.drawFloor(1, 1, 100, 0, 0);


            TimerTask task = new TimerTask()
            {

                @Override
                public void run()
                {
//                    System.out.println("in run");
                    room.update();
                    int xToMoveBy = 0;
                    int yToMoveBy = 0;

                    if (graphicsInterface.arDown) {
                        yToMoveBy += 10;
                        System.out.println("player " + player.getPose());
                    }
                    if (graphicsInterface.arUp) {
                        yToMoveBy += -10;
                        System.out.println("player " + player.getPose());
                    }
                    if (graphicsInterface.arLeft) {
                        xToMoveBy += -10;
                        System.out.println("player " + player.getPose());
                    }
                    if (graphicsInterface.arRight) {
                        xToMoveBy += 10;
                        System.out.println("player " + player.getPose());
                    }

                    player.move(xToMoveBy, yToMoveBy);

                }
            };

            timer.scheduleAtFixedRate(task, 0, 100);

    }
}
