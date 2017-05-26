package architecture;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
        ArrayList<Monster> monsters = new ArrayList<Monster>();

        final Player player = new Player( new Point2D( 0, 0 ) );

        Skeleton skeleton = new Skeleton( 1, player, new Point2D( 100, 100 ) );
        monsters.add(skeleton);

        skeleton.printStatus();
        player.printStatus();
        System.out.println( player.getWeapon().getAccuracy() );


        RoomGenerator roomGenerator = new RoomGenerator();

        // TODO: thows index out of bounds excpetion when removed
        for ( int i = 0; i < 50; i++ )
        {
            roomGenerator.runSimulation();
        }

        // TODO: make tile width more intelligent
        final Room room = roomGenerator.generateRoom( 1,
            player.WIDTH + 50,
            player );

        System.out.println( "just scheduled!" );

        final GraphicsInterface graphicsInterface = new GraphicsInterface();

        TimerTask task = new TimerTask()
        {

            @Override
            public void run()
            {
                System.out.println("player health: " + player.getHealth());

                graphicsInterface.requestFocus();

                if ( room.atPortal() )
                {
                    incrementFloor();

                    player.restoreHealth(player.getStats()[0] / 2);

                    player.setLevel(player.getLevel() + 1);
                    System.out.println("yay, at portal!!!");
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
                        monsters.forEach( monster -> {
                            // TODO: remove second clause of if
                            if ( player.isInRange( monster ) && !monster.equals( player ) )
                            {
                                player.attack( monster );
                                System.out.println( monster + " health " + monster.getHealth() );
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
                    System.out.println( "player died!" );
                    // TODO: show losing screen and restart
                }
                monsters.removeIf( Monster::isDead );

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

            }
        };

        timer.scheduleAtFixedRate( task, 0, 100 );
    }

}
