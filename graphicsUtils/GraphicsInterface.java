package graphicsUtils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Dimension;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicsInterface extends Canvas
{
    private BufferedImage sprite;

    private Graphics graphic;

    private JFrame frame;
    
    public GraphicsInterface()
    {
        setMinimumSize( new Dimension(700,700) );
        setMaximumSize( new Dimension(700,700) );
        setPreferredSize( new Dimension(700,700) );

        frame = new JFrame();
        frame.setLayout( new BorderLayout() );
        frame.add( this, BorderLayout.CENTER );
        frame.pack();
        
        JPanel panel = new JPanel();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);        
        Container c = frame.getContentPane();
        c.add(panel);
        graphic = getGraphics();
        frame.setVisible(true);
        init();
    }

    private void init()
    {
        
        BufferedImage spriteSheet = null;
        try
        {
            spriteSheet = ImageUtils.loadBufferedImage("Dirt_Floor.png");
        }
        catch (IOException ex)
        {
            Logger.getLogger(
                    GraphicsInterface.class.getName() ).log(Level.SEVERE,
                    null,
                    ex);
        }

        SpriteSheet ss = new SpriteSheet(spriteSheet);

        // TODO: take as constructor paramter
        sprite = ss.grabSprite( 0, 0, 48, 48 );
        paint(graphic);
        System.out.println("graphics interface init() finished!");
    }

    public void setSprite(BufferedImage newSprite) {
        this.sprite = newSprite;
    }

    public void loadSprite(String absolutePath) {
        try {
            this.sprite = ImageUtils.loadBufferedImage(absolutePath);
        } catch (IOException e) {
            throw new RuntimeException("image loadig failed");
        }
    }
    
    public void paint(BufferedImage sprite, int x, int y, int width, int height, Graphics g)
    {       
        g.drawImage(sprite, x, y, width, height, frame);
    }

    /**
     * TODO: take image as parameter
     * @param width
     * @param height
     * @param blockSize the length in pixels of each individual block
     * @param xTopLeftCorner
     * @param yTopLeftConer
     */
    public void drawFloor(int width, int height, int blockSize, int xTopLeftCorner, int yTopLeftConer)
    {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                paint(
                    sprite,
                    xTopLeftCorner + blockSize * row,
                    yTopLeftConer + blockSize * col,
                    blockSize,
                    blockSize,
                    graphic);
            }
        }
        System.out.println("Draw floor finished!");
    }

    public static void main (String[] args)
    {
        GraphicsInterface graphicsInterface = new GraphicsInterface();
        try {
            graphicsInterface.setSprite(
                    ImageUtils.loadBufferedImage("Dirt_Floor.png"));
        } catch (IOException e) {
            throw new RuntimeException("Image failed to load");
        }
        graphicsInterface.drawFloor(1, 1, 100, 0, 0);
    }
}
