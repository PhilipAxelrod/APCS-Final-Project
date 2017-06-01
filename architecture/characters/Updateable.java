package architecture.characters;

import graphicsUtils.GraphicsInterface;
import proceduralGeneration.Room;


/**
 * Implemented by classes which require periodic updating.
 *
 * @author Kevin Liu
 * @version May 31, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public interface Updateable
{
    /**
     * Updates the object based on its specific needs, to be called every game
     * tick
     * 
     * @param graphicsInterface
     *            the GraphicsInterface
     * @param room
     *            the Room in which the Updateable is
     */
    public void update( GraphicsInterface graphicsInterface, Room room );
}
