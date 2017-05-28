package architecture.characters;

import graphicsUtils.GraphicsInterface;
import proceduralGeneration.Room;

public interface Updateable {
    void update(GraphicsInterface graphicsInterface, Room room);
}
