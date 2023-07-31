package MP1.Item;

import java.io.Serializable;

/**
 * The OwnershipWay enum represents the way an item is obtained.
 * It can be one of the following:
 * - STARTING: The item is obtained at the start of the game.
 * - LOOTED: The item is obtained as loot from defeating monsters or finding treasure.
 */
public enum OwnershipWay implements Serializable {
    STARTING,
    LOOTED,
}
