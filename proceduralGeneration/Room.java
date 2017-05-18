package proceduralGeneration;

import com.sun.javafx.geom.Line2D;
import com.sun.javafx.geom.Point2D;

import graphicsUtils.GraphicsInterface;
import java.awt.*;
import java.util.*;
import java.util.List;

import architecture.Chest;
import architecture.Combatant;


public class Room extends Rectangle {
    ArrayList<Combatant> enemies;
    ArrayList<Chest> chests;

    final int tiles;
    Hashtable<Point2D, List<Line2D>> walls;
       
    Cell[][] cells;
    static final int rows = 1000;
    static final int cols = rows;

    static GraphicsInterface graphicsInterface;
    
    public Room(ArrayList<Combatant> enemies, Hashtable<Point2D, List<Line2D>> walls, int tiles)
    {
        this.enemies = enemies;
        this.walls = walls;
        this.tiles = tiles;
    }
    
    public void update(  )
    {
       for(Combatant c : enemies) {
           c.run();
       }
       enemies.forEach(combatant -> {
         Point2D currPose = combatant.getPose();

         Point2D tileKey = new Point2D(
                 RoomGenerator.roundToLowestMultiple(currPose.x, tiles),
                 RoomGenerator.roundToLowestMultiple(currPose.y, tiles)
         );

         List<Line2D> tileWalls = walls.get(tileKey);

         if (tileWalls != null) {
             tileWalls.forEach(wall -> {
                 // Sketch af collision detection
                 if (wall.intersects(currPose.x, currPose.y, combatant.WIDTH, combatant.HEIGHT)) {
                     combatant.resetPoseToPrevios();
                     System.out.println("lol, it actually worked");
                 }
             });
         }
       });
    }
  
    private static int randomInt(int min, int max) {
        int range = max - min + 1;
        return min + (int)(range * Math.random());
    }

    public static void initGraphics() {
        if (graphicsInterface == null) {
            graphicsInterface = new GraphicsInterface();
        }
    }
    public void render(  GraphicsInterface graphicsInterface)
    {
        graphicsInterface.loadSprite("Dirt_Floor.png");

        int side = 100;

        int x = randomInt(1, 500);
        int y = randomInt(1, 500);

        for (int i = 0; i < 10; i++) {
            int leftRightTopDown = randomInt(1, 4);

            switch (leftRightTopDown) {
                case 1:
                   y = y - side;
                   break;
                case 2:
                    x = x + side;
                    break;
                case 3:
                    y = y + side;
                    break;
                case 4:
                    x = x - side;
            }

            graphicsInterface.drawFloor(1, 1, side, x, y);
        }
    }
    
    public static void render( Cell[][] cell)
    {
        graphicsInterface.loadSprite("Dirt_Floor.png");

        int side = 100;
        
        for ( int i = 0; i < cell.length; i++ )
        {
            for ( int j = 0; j < cell[0].length; j++ )
            {
                if(cell[i][j].isAlive()) {
                    graphicsInterface.drawFloor( 1, 1, side, i * side, j * side );
                }
            }
        }
    }
    
    public static void main(String[] args) {
        RoomGenerator room = new RoomGenerator( new ArrayList<Point>() );
        for(int i = 0;  i < 50; i++) {
            room.update();
        }

        initGraphics();
        render(room.cells);
//
//        while (true) {
//            if (graphicsInterface.)
//        }
    }
}
