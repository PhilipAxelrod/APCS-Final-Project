package architecture;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import proceduralGeneration.Room;

public class GameLoop {

    public static void main(String[] args) {
        Timer timer = new Timer();
        
        ArrayList<Chest> chests = new ArrayList<Chest>();
        ArrayList<Combatant> figheters = new ArrayList<Combatant>();
        
        Room room = new Room(figheters);

        System.out.println("just scheduled!");
           
        TimerTask task = new TimerTask()
        {
            
            @Override
            public void run()
            {
                room.update();
                
            }
        };
        
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
}
