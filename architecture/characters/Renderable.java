package architecture.characters;

import graphicsUtils.GraphicsInterface;

import java.awt.*;


/**
 * Interface for classes renderable on the game screen. All implementing classes
 * must include a render method.
 *
 * @author Philip Axelrod
 * @version May 31, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public interface Renderable
{
    /**
     * Render's the object onto Graphics
     * 
     * @param graphicsInterface
     *            the GraphicsInterface
     * @param g
     *            the Graphics
     */
    public void render( GraphicsInterface graphicsInterface, Graphics g );
}
