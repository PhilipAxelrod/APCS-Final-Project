package architecture;

import java.util.Timer;
import java.util.TimerTask;

import architecture.characters.Combatant;
import architecture.characters.Player;
import com.sun.javafx.geom.Point2D;
import graphicsUtils.GraphicsInterface;
import proceduralGeneration.Room;
import proceduralGeneration.RoomGenerator;


/**
 * This class starts and runs the program, instantiating the game clock which
 * controls updates the game and game graphics. It resets the player when he dies,
 * handles generating new levels when the player reaches the portal,
 *
 * @author Philip Axelrod
 * @version May 31, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class GameLoop
{
    public static void main( String[] args )
    {
        Timer timer = new Timer();

        TimerTask task = new TimerTask()
        {
            RoomGenerator roomGenerator = new RoomGenerator();

            GraphicsInterface graphicsInterface = new GraphicsInterface();

            int curentFloor = 1;

            int deadTicks = 0;

            final Player player = new Player( new Point2D( 0, 0 ) );

            Room room = roomGenerator.generateNewRoom( curentFloor,
                Player.WIDTH + 50,
                player );

            double startTime = System.currentTimeMillis();

            int iter = 0;


            private void reset()
            {
                player.restoreHealth( 100 );
                player.setLevel( 1 );

                curentFloor = 1;
                room = roomGenerator.generateNewRoom( curentFloor,
                    Player.WIDTH + 50,
                    player );
            }


            @Override
            public void run()
            {
                graphicsInterface.requestFocus();

                if ( player.isDead() )
                {
                    if ( deadTicks >= 500 )
                    {
                        reset();
                    }

                    deadTicks++;
                }
                else
                {
                    room.update( graphicsInterface );
                    deadTicks = 0;
                }

                if ( room.atPortal() )
                {
                    player.stop();
                    player.restoreHealth( player.getStats().getHP() / 2 );
                    curentFloor++;
                    room = roomGenerator.generateNewRoom(
                            curentFloor,
                            Player.WIDTH + 50,
                            player );
                }

                graphicsInterface.doRepaint();
            }
        };

        timer.scheduleAtFixedRate( task, 0, 10 );
    }

}
