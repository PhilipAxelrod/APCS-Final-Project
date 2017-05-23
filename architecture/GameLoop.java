package architecture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.javafx.geom.Point2D;
import graphicsUtils.GraphicsInterface;
import graphicsUtils.ImageUtils;
import proceduralGeneration.Room;
import proceduralGeneration.RoomGenerator;


public class GameLoop
{

    static int tickNum = 0;


    public static void incrementTickNum()
    {
        tickNum++;
    }


    public static int getTickNum()
    {
        return tickNum;
    }


    public static void main( String[] args )
    {
        Timer timer = new Timer();

        ArrayList<Chest> chests = new ArrayList<Chest>();
        chests.add( new Chest( 1, new Point2D( 100, 100 ) ) );
        ArrayList<Combatant> fighters = new ArrayList<Combatant>();
        ArrayList<Monster> monsters = new ArrayList<Monster>();

        final Player player = new Player( new Point2D( 0, 0 ) );

        Skeleton skeleton = new Skeleton(1, player);

        fighters.add( skeleton);
        fighters.add( player );
        monsters.add(skeleton);

        RoomGenerator roomGenerator = new RoomGenerator();

        for ( int i = 0; i < 50; i++ )
        {
            roomGenerator.runSimulation();
        }

        // TODO: make tile width more intelligent
        final Room room = roomGenerator.generateRoom(
                1,
                player.WIDTH + 1,
                player);



        System.out.println( "just scheduled!" );

        final GraphicsInterface graphicsInterface = new GraphicsInterface();

//        graphicsInterface.renderGrid(room.cells, 100, graphicsInterface.getGraphics());

        System.out.println("finished with graphics");
        try
        {
            graphicsInterface
                    .setSprite( ImageUtils.loadBufferedImage( "Dirt_Floor.png" ) );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "Image failed to load" );
        }

//        roomGenerator.spawnPlayer( player, player.WIDTH + 1 );
//        roomGenerator.spawnEnemies(monsters, player.WIDTH + 1);

        TimerTask task = new TimerTask()
        {

            @Override
            public void run()
            {
                // incrementTickNum();
                graphicsInterface.requestFocus();

                if (room.atPortal()) {
                    System.out.println("yay, at portal!!!");
                    room.assignSelfTo(
                            roomGenerator.generateRoom(0, 0, player)
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
                if ( graphicsInterface.isQPressed())
                {
                    if ( player.canAttack )
                    {
                        fighters.forEach( combatant -> {
                            if ( player.isInRange( combatant ) && !combatant.equals( player ) )
                            {
                                player.attack( combatant );
                                System.out.println( combatant + " health " + combatant.getHealth() );
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
                }

                player.accelerate( xToMoveBy, yToMoveBy );
                player.move();
                room.update();
                player.restoreHealth( 10000 );

                // TODO: Make tile width more intelligent
                graphicsInterface.setGameState(
                        new GameState(
                                roomGenerator.cells,
                                fighters,
                                player,
                                chests,
                                player.WIDTH + 1,
                                room.getPortal()) );

                graphicsInterface.doRepaint();

            }
        };

        timer.scheduleAtFixedRate( task, 0, 100 );
    }

//    private static void makeNewRoom(Room room, Room newRoom) {
//        room
//    }
}
