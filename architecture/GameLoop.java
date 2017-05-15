package architecture;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.javafx.geom.Point2D;
import proceduralGeneration.Room;
import proceduralGeneration.RoomGenerator;

public class GameLoop
{

    public static void main( String[] args )
    {
        Timer timer = new Timer();
        
        ArrayList<Chest> chests = new ArrayList<Chest>();
        ArrayList<Combatant> figheters = new ArrayList<Combatant>();
        Player player = new Player(new Point2D(0, 0));
        figheters.add(new Skeleton( 5, null ));
        figheters.add(player);

        ArrayList<Point> emptyList = new ArrayList<>();
        RoomGenerator roomGenerator = new RoomGenerator(emptyList);

        for(int i = 0; i < 50; i++) {
            roomGenerator.update();
        }

        Room room = new Room(figheters, roomGenerator.getWalls(10), 10);

        System.out.println("just scheduled!");


        TimerTask task = new TimerTask()
        {
            
            @Override
            public void run()
            {
                room.update();
                player.move(10, 10);
                System.out.println("player " + player.getPose());
            }
        };
        
        timer.scheduleAtFixedRate(task, 0, 100);
    }
}
