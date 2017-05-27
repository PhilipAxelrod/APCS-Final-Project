package architecture;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import architecture.augmentations.Chest;
import architecture.augmentations.Monster;
import architecture.characters.Player;
import com.sun.javafx.geom.Point2D;
import graphicsUtils.GraphicsInterface;
import proceduralGeneration.Room;
import proceduralGeneration.RoomGenerator;


public class GameLoop
{

    static int floorNum = 1;


    public static void incrementFloor()
    {
        floorNum++;
    }


    public static int getFloorNum()
    {
        return floorNum;
    }


    public void restartGame()
    {
        // TODO: complete
        floorNum = 1;
    }


    public static void main( String[] args )
    {
        Timer timer = new Timer();

        final Player player = new Player( new Point2D( 0, 0 ) );

        player.printStatus();


        RoomGenerator roomGenerator = new RoomGenerator();

        // TODO: throws index out of bounds excpetion when removed
        for ( int i = 0; i < 50; i++ )
        {
            roomGenerator.runSimulation();
        }

        // TODO: make tile width more intelligent
        final Room room = roomGenerator.generateRoom(
                1,
            player.WIDTH + 50,
            player );

//        System.out.println( "just scheduled!" );

        final GraphicsInterface graphicsInterface = new GraphicsInterface();

        TimerTask task = new TimerTask()
        {

            double startTime = System.currentTimeMillis();
            int iter = 0;
            @Override
            public void run()
            {
//                System.out.println("player health: " + player.getHealth());

                graphicsInterface.requestFocus();

                if ( room.atPortal() )
                {
                    player.stop();

                    incrementFloor();

                    player.restoreHealth(player.getStats().getHP() / 2);

                    player.setLevel(player.getLevel() + 1);
//                    System.out.println("yay, at portal!!!");
                    room.assignSelfTo(
                            roomGenerator.generateRoom(
                                    floorNum,
                                    player.WIDTH + 50,
                                    player)
                    );
                }
                int xToMoveBy = 0;
                int yToMoveBy = 0;

                if ( graphicsInterface.isArDown() )
                {
                    yToMoveBy += 20;
                }
                if ( graphicsInterface.isArUp() )
                {
                    yToMoveBy += -20;
                }
                if ( graphicsInterface.isArLeft() )
                {
                    xToMoveBy += -20;
                }
                if ( graphicsInterface.isArRight() )
                {
                    xToMoveBy += 20;
                }
                if ( graphicsInterface.isQPressed() )
                {
                    if ( player.canAttack )
                    {
                        player.getStats().setACC(101);
                        // TODO: this doesn't actually work
                        room.monsters.forEach( monster -> {
                            // TODO: remove second clause of if
                            if ( player.isInRange( monster ) && !monster.equals( player ) )
                            {
                                player.restoreHealth(100);
//                                System.out.println("in range");
                                player.attack( monster )/*.getDamage()*/;
//                                System.out.println("result " + player.attack( monster ).getDamage());
//                                System.out.println( monster + " health " + monster.getHealth() );
                            }

                        } );
                    }
                    for ( Chest chest : room.chests )
                    {
                        if ( player.canOpen( chest ) )
                            chest.acquireAll( player );
                    }
                }

                if ( player.isDead() )
                {
//                    System.out.println( "player died!" );
                    // TODO: show losing screen and restart
                }
                room.monsters.removeIf( Monster::isDead );

                // TODO: for testing purposes
                // player.restoreHealth(player.getStats()[0] / 2);


                player.accelerate( xToMoveBy, yToMoveBy );
                player.move();
                room.update();

                // TODO: Make tile width more intelligent
                graphicsInterface.setGameState(
                        new GameState(
                                roomGenerator.cells,
                                room.getMonsters(),
                                player,
                                room.chests,
                                player.WIDTH + 50,
                                room.getPortal()) );

                graphicsInterface.doRepaint();
                double currTime = System.currentTimeMillis() / 1000D;
                double timePassed = currTime - startTime;
//                System.out.println("tick took: " + timePassed);
                startTime = currTime;
                if (timePassed * 1000 >= 15 && iter > 100) {
                    System.out.println("went over by: " + timePassed * 1000  +  "s. iter"  + iter);
//                    throw new RuntimeException("went ove 11 ms. Iter: " + iter );
                }
                iter = iter + 1;
            }
        };

        timer.scheduleAtFixedRate( task, 0, 10 );
    }

}
