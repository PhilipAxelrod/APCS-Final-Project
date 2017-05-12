package proceduralGeneration;

import graphicsUtils.GraphicsInterface;
import java.awt.*;
import java.util.ArrayList;
import architecture.Chest;
import architecture.Combatant;


public class Room extends Rectangle {
    ArrayList<Combatant> enemies;
    ArrayList<Chest> chests;
       
    Cell[][] cells;
    static final int rows = 1000;
    static final int cols = rows;
    
    public Room(ArrayList<Combatant> enemies)
    {
        this.enemies = enemies;
    }
    
    public void update(  )
    {
       for(Combatant c : enemies) {
           c.run();
       }
    }
  
    private static int randomInt(int min, int max) {
        int range = max - min + 1;
        return min + (int)(range * Math.random());
    }
    
    public void render(  )
    {
        GraphicsInterface graphicsInterface = new GraphicsInterface();
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
        GraphicsInterface graphicsInterface = new GraphicsInterface();
        graphicsInterface.loadSprite("Dirt_Floor.png");

        int side = 100;
        
        for ( int i = 0; i < cell.length; i++ )
        {
            for ( int j = 0; j < cell[0].length; j++ )
            {
                if(cell[i][j].isAlive) {
                    graphicsInterface.drawFloor( 1, 1, side, i * side, j * side );
                }
            }
        }
    }
    
    public static void main(String[] args) {
//        Room room = new Room( null);
        RoomGenerator room = new RoomGenerator( new ArrayList<Point>() );
        for(int i = 0;  i < 50; i++) {
            room.update();
        }
        
        render(room.cells);
    }
}
