package architecture;

import java.util.Timer;
import java.util.TimerTask;


import architecture.characters.Player;
import com.sun.javafx.geom.Point2D;
import graphicsUtils.GraphicsInterface;
import proceduralGeneration.Room;
import proceduralGeneration.RoomGenerator;


public class GameLoop
{
    /**
     * Instantiates a timer that
     * @param args
     */
    public static void main( String[] args )
    {
        Timer timer = new Timer();

        RoomGenerator roomGenerator = new RoomGenerator();

//        for ( int i = 0; i < 50; i++ )
//        {
//            roomGenerator.runSimulation();
//        }


        TimerTask task = new TimerTask()
        {
            GraphicsInterface graphicsInterface = new GraphicsInterface();

            int curentFloor = 1;

            int deadTicks = 0;

            final Player player = new Player( new Point2D( 0, 0 ) );

            // TODO: make tile width more intelligent
            Room room = roomGenerator.generateNewRoom(
                    curentFloor,
                player.WIDTH + 50,
                    player );

            double startTime = System.currentTimeMillis();

            int iter = 0;

            private void reset() {
                player.restoreHealth(100);
                player.setLevel(1);

                curentFloor = 1;
                room = roomGenerator.generateNewRoom(
                        curentFloor,
                        player.WIDTH + 50,
                        player
                );
            }

            @Override
            public void run()
            {
                graphicsInterface.requestFocus();

                if ( player.isDead())
                {
                    if (deadTicks >= 500) {
                        reset();
                    }

                    deadTicks++;
                } else {
                    room.update(graphicsInterface);
                    deadTicks = 0;
                }

                if ( room.atPortal() )
                {
                    player.stop();
                    player.restoreHealth( player.getStats().getHP() / 2 );
                    curentFloor++;
                    room = roomGenerator.generateNewRoom(
                            curentFloor,
                            player.WIDTH + 50,
                            player );
                }


                graphicsInterface.doRepaint();


                double currTime = System.currentTimeMillis() / 1000D;
                double timePassed = currTime - startTime;
                startTime = currTime;
                if ( timePassed * 1000 >= 15 && iter > 100 )
                {
//                    System.out.println( "went over by: " + timePassed * 1000 + "s. iter" + iter );
                }
                iter = iter + 1;
            }
        };

        timer.scheduleAtFixedRate( task, 0, 10 );
    }

}
