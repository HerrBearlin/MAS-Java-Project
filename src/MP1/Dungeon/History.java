package MP1.Dungeon;

import MP1.ExtentLoader.ExtentLoader;
import MP1.Monster.Monster;
import MP1.Player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The History class represents the history of a player's interaction with a dungeon room.
 * It stores the room status, the player, and the dungeon room associated with the history.
 * The History class is serializable, allowing it to be saved and loaded from a file.
 */
public class History implements Serializable {
    private static final long serialVersionUID = 1L;

    private static Map<Class<?>, List<History>> historyExtent = new HashMap<>();

    private RoomStatus roomStatus;
    private Player player;
    private DungeonRoom dungeonRoom;

    /**
     * Constructs a new History object with the specified player, dungeon room, and room status.
     *
     * @param player     the player associated with the history
     * @param dungeonRoom the dungeon room associated with the history
     * @param roomStatus the room status associated with the history
     */
    public History(Player player, DungeonRoom dungeonRoom, RoomStatus roomStatus) {
        setPlayer(player);
        setDungeonRoom(dungeonRoom);
        setRoomStatus(roomStatus);
        addToExtent();
    }

    /**
     * Gets the room status associated with this history.
     *
     * @return the room status
     */
    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    /**
     * Sets the room status for this history.
     *
     * @param roomStatus the room status to set
     */
    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    /**
     * Gets the player associated with this history.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player for this history.
     *
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        if (this.player == player) {
            return;  // Exit the method if the player is already set to the same value
        }

        if (this.player != null) {
            this.player.removeHistory(this); // Remove the history from the current player
        }

        this.player = player; // Set the new player

        if (player != null && !player.getHistories().contains(this)) {
            player.addHistory(this); // Add the history to the new player if it's not already present
        }
    }

    /**
     * Gets the dungeon room associated with this history.
     *
     * @return the dungeon room
     */
    public DungeonRoom getDungeonRoom() {
        return dungeonRoom;
    }

    /**
     * Sets the dungeon room for this history.
     *
     * @param dungeonRoom the dungeon room to set
     */
    public void setDungeonRoom(DungeonRoom dungeonRoom) {
        if (this.dungeonRoom == dungeonRoom) {
            return;  // Exit the method if the dungeon room is already set to the same value
        }

        if (this.dungeonRoom != null) {
            this.dungeonRoom.removeHistory(this); // Remove the history from the current dungeon room
        }

        this.dungeonRoom = dungeonRoom; // Set the new dungeon room

        if (dungeonRoom != null && !dungeonRoom.getHistories().contains(this)) {
            dungeonRoom.addHistory(this); // Add the history to the new dungeon room if it's not already present
        }
    }

    /**
     * Returns a copy of the extent of all History objects.
     *
     * @return a list containing all the History objects
     */
    public static List<History> getExtent() {
        List<History> list = historyExtent.get(History.class);
        if (list == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(list);
    }

    /**
     * Gets the number of enemies beaten in the dungeon room associated with this history.
     *
     * @return the number of enemies beaten
     */
    public int getNumberOfEnemiesBeaten() {
        int count = 0;
        DungeonRoom dungeonRoom = getDungeonRoom();
        if (dungeonRoom != null) {
            for (Monster monster : dungeonRoom.getMonsters()) {
                if (monster.getCurrentHealth() <= 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Removes this history from the extent.
     */
    protected void removeFromExtent() {
        historyExtent.remove(this.getClass());
    }

    /**
     * Adds this history to the extent.
     */
    private void addToExtent() {
        List<History> ext = historyExtent.get(this.getClass());
        if (ext == null) {
            ext = new ArrayList<>();
            historyExtent.put(this.getClass(), ext);
        }
        ext.add(this);
    }

    /**
     * Saves the history extent to a file.
     *
     * @param fileName the name of the file to save the extent to
     */
    public static void saveExtent(ObjectOutputStream fileName) {
        try (ObjectOutputStream outputStream = fileName) {
            outputStream.writeObject(historyExtent);
            System.out.println("History extent saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the history extent from a file.
     *
     * @param fileName the name of the file to load the extent from
     */
    @SuppressWarnings("unchecked")
    public static void loadExtent(ObjectInputStream fileName) {
        try (ObjectInputStream inputStream = fileName) {
            historyExtent = (Map<Class<?>, List<History>>) inputStream.readObject();
            System.out.println("History extent loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the dungeon room where the player is currently located.
     *
     * @return the dungeon room where the player is located, or null if the player is not in any room
     */
    public DungeonRoom getRoomWithPlayer() {
        int playerX = player.getCurrentX();
        int playerY = player.getCurrentY();

        for (List<History> historyList : historyExtent.values()) {
            for (History history : historyList) {
                DungeonRoom room = history.getDungeonRoom();
                if (room != null && room.getX() == playerX && room.getY() == playerY) {
                    return room;
                }
            }
        }

        return null; // Player is not in any room
    }

    @Override
    public String toString() {
        return "History{" +
                "roomStatus=" + roomStatus +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        History history = (History) obj;
        return roomStatus == history.roomStatus;
    }

    @Override
    public int hashCode() {
        return roomStatus != null ? roomStatus.hashCode() : 0;
    }
}
