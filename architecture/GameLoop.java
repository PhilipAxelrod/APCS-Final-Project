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

    static int floor = 1;


    public static void incrementFloor()
    {
        floor++;
    }


    public static int getFloor()
    {
        return floor;
    }


    public void restartGame()
    {
        // TODO: complete
        floor = 1;
    }


    public static void main( String[] args )
    {
        // sun.java2d.opengl=true;
        // sun.java2d.opengl.
        Timer timer = new Timer();

        ArrayList<Chest> chests = new ArrayList<Chest>();
        chests.add( new Chest( 1, new Point2D( 100, 100 ) ) );
        ArrayList<Combatant> fighters = new ArrayList<Combatant>();
        ArrayList<Monster> monsters = new ArrayList<Monster>();

        final Player player = new Player( new Point2D( 0, 0 ) );

        Skeleton skeleton = new Skeleton( 1, player );
        skeleton.printStatus();
        player.printStatus();
        System.out.println( player.getWeapon().getAccuracy() );

        fighters.add( skeleton );
        fighters.add( player );
        monsters.add( skeleton );

        RoomGenerator roomGenerator = new RoomGenerator();

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

                graphicsInterface.requestFocus();

                if ( room.atPortal() )
                {
                    incrementFloor();

                    player.restoreHealth( player.getStats()[0] / 2 );
                    System.out.println( "yay, at portal!!!" );
                    room.assignSelfTo( roomGenerator.generateRoom( floor,
                        player.WIDTH + 50,
                        player ) );
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
                        fighters.forEach( combatant -> {
                            if ( player.isInRange( combatant )
                                && !combatant.equals( player ) )
                            {
                                player.attack( combatant );
                                System.out.println( combatant + " health "
                                    + combatant.getHealth() );
                            }

                        } );
                    }
                    for ( Chest chest : chests )
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

                // TODO: separate player from Monsters
                fighters.removeIf( Combatant::isDead );

                player.accelerate( xToMoveBy, yToMoveBy );
                player.move();
                room.update();

                // TODO: Make tile width more intelligent
                graphicsInterface
                    .setGameState( new GameState( roomGenerator.cells,
                        fighters,
                        player,
                        chests,
                        player.WIDTH + 50,
                        room.getPortal() ) );

                graphicsInterface.doRepaint();

            }
        };

        timer.scheduleAtFixedRate( task, 0, 100 );
    }

}
