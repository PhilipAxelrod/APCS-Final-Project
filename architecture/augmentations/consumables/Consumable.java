package architecture.augmentations.consumables;

import architecture.augmentations.Item;
import architecture.characters.Player;

/**
 *  Represents items which can be consumed.
 *
 *  @author  Kevin Liu
 *  @version May 11, 2017
 *  @author  Period: 5
 *  @author  Assignment: APCS Final
 *
 *  @author  Sources: none
 */
public interface Consumable extends Item
{
    /**
     * Uses the item, activating its effect and removing it from the inventory.
     * 
     * @param player
     *            the player
     * @return true if used successfully, else false
     */
    public boolean use( Player player );
}
