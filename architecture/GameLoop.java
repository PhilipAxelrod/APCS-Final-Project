package architecture;

import java.awt.*;
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

    public static void incrementTickNum() {
        tickNum++;
    }

    public static int getTickNum(){
        return tickNum;
    }

    public static void main( String[] args )
    {
            Timer timer = new Timer();

            ArrayList<Chest> chests = new ArrayList<Chest>();
            ArrayList<Combatant> figheters = new ArrayList<Combatant>();
            final Player player = new Player(new Point2D(0, 0), 100);
            figheters.add(new Skeleton( 5, player ));
            figheters.add(player);

            ArrayList<Point> emptyList = new ArrayList<>();
            RoomGenerator roomGenerator = new RoomGenerator();

            for(int i = 0; i < 10; i++) {
                roomGenerator.update();
            }

            final Room room = new Room(
                    figheters,
                    roomGenerator.getWalls(player.WIDTH),
                    player.WIDTH * 3);

            System.out.println("just scheduled!");

            final GraphicsInterface graphicsInterface = new GraphicsInterface();

            try {
                graphicsInterface.setSprite(
                        ImageUtils.loadBufferedImage("Dirt_Floor.png"));
            } catch (IOException e) {
                throw new RuntimeException("Image failed to load");
            }



            TimerTask task = new TimerTask()
            {

                @Override
                public void run()
                {
//                    incrementTickNum();
                    graphicsInterface.requestFocus();
                    int xToMoveBy = 0;
                    int yToMoveBy = 0;

                    if (graphicsInterface.isArDown()) {
                        yToMoveBy += 10;
                    }
                    if (graphicsInterface.isArUp()) {
                        yToMoveBy += -10;
                    }
                    if (graphicsInterface.isArLeft()) {
                        xToMoveBy += -10;
                    }
                    if (graphicsInterface.isArRight()) {
                        xToMoveBy += 10;
                    }
                    if (graphicsInterface.isQPressed()/* || true*/) {
                        if (player.canAttack) {
                            figheters.forEach(combatant -> {
                                if (player.getPose().distance(combatant.getPose()) <= player.getRange() && player != combatant) {
                                    combatant.receiveAttack(
                                            1000,
                                            1000,
                                            1000
                                    );
                                    System.out.println(combatant + " health " + combatant.getHealth());
                                }
                            });
                        }
                    }

                    if (player.isDead()) {
                        System.out.println("player died!");
                    }

                    player.move(xToMoveBy, yToMoveBy);
                    room.update();
                    player.restoreHealth(10000);
                    figheters.removeIf(Combatant::isDead);
                    graphicsInterface.setGameState(
                            new GameState(
                                    roomGenerator.cells,
                                    figheters,
                                    player
                            )
                    );

                    graphicsInterface.doRepaint();

                }
            };

            timer.scheduleAtFixedRate(task, 0, 100);

    }
}
