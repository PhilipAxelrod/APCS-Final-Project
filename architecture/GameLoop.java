package architecture;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

<<<<<<< HEAD
import proceduralGeneration.Room;

public class GameLoop {
=======
>>>>>>> Monster v1.0 commit

public class GameLoop
{

    public static void main( String[] args )
    {
        Timer timer = new Timer();
        
        ArrayList<Chest> chests = new ArrayList<Chest>();
        ArrayList<Combatant> figheters = new ArrayList<Combatant>();
        
        Room room = new Room(figheters);

<<<<<<< HEAD
<<<<<<< HEAD
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
=======
        TimerTask runnable = new Skeleton( 1 )
=======
        TimerTask runnable = new Skeleton( 5, null )
>>>>>>> everything
        {
            @Override
            public void run()
            {
                System.out.println( "I'm bob!" );
            }
        };

        System.out.println( "just scheduled!" );

        timer.schedule( runnable, 10000 );
>>>>>>> Monster v1.0 commit
    }
}
