package MP1.Dungeon;

import java.io.Serializable;

/**
 * The RoomStatus enum represents the status of a dungeon room.
 * It can be one of the following:
 * - NOTVISITED: The room has not been visited by the player.
 * - VISITED: The room has been visited by the player.
 * - CLEARED: The room has been cleared by defeating all the monsters.
 */
public enum RoomStatus implements Serializable {
    NOTVISITED,
    VISITED,
    CLEARED
}
