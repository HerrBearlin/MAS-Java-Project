package MP1.Dungeon;

import MP1.Monster.Monster;
import MP1.Player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * The Dungeon class represents a dungeon that contains multiple dungeon rooms.
 */
public class Dungeon implements Serializable {

    private static Map<Class, List<Dungeon>> dungeonExtent = new HashMap<>();
    Set<DungeonRoom> rooms = new HashSet<>();

    private String dungeonName;
    private static int dungeonWidth = 4;
    private static int dungeonHeight = 4;

    /**
     * Creates a new Dungeon object with the specified name.
     *
     * @param dungeonName the name of the dungeon
     */
    public Dungeon(String dungeonName) {
        try {
            setDungeonName(dungeonName);
            addToExtent();
        } catch (Exception e) {
            removeFromExtent();
        }
    }

    /**
     * Gets the set of rooms in the dungeon.
     *
     * @return the set of rooms in the dungeon
     */
    public Set<DungeonRoom> getRooms() {
        return rooms;
    }

    /**
     * Sets the set of rooms in the dungeon.
     *
     * @param rooms the set of rooms to set
     */
    public void setRooms(Set<DungeonRoom> rooms) {
        this.rooms = rooms;
    }

    /**
     * Creates a new dungeon room with the specified description, monsters, and coordinates.
     *
     * @param roomDescription the description of the room
     * @param monstersInRoom  the map of monsters in the room
     * @param prevX           the previous X coordinate of the room
     * @param prevY           the previous Y coordinate of the room
     * @throws IllegalArgumentException if the room description is null or blank, or if the monsters in the room are null
     * @throws IllegalStateException    if the maximum limit of rooms is reached or adjacent rooms are already occupied
     */
    public void createDungeonRoom(String roomDescription, Map<String, Monster> monstersInRoom, int prevX, int prevY) {
        // Validation checks
        if (roomDescription == null || roomDescription.isBlank()) {
            throw new IllegalArgumentException("Room description is null. Cannot create room.");
        }
        if (monstersInRoom == null) {
            throw new IllegalArgumentException("There must be some monsters in the room. Monsters in Room is null.");
        }

        // Maximum X and Y coordinates for a 4x4 dungeon
        int maxX = dungeonWidth - 1;
        int maxY = dungeonHeight - 1;

        // Set of occupied coordinates
        Set<String> occupiedCoordinates = new HashSet<>();
        for (DungeonRoom room : rooms) {
            occupiedCoordinates.add(room.getX() + "," + room.getY());
        }

        // Find the next available coordinates for the new room
        int newX = 0; // Start with X=0
        int newY = 0; // Start with Y=0

        while (true) {
            String coordinates = newX + "," + newY;
            if (!occupiedCoordinates.contains(coordinates)) {
                break;
            }

            if (newX < maxX) {
                newX++;
            } else if (newY < maxY) {
                newX = 0;
                newY++;
            } else {
                throw new IllegalStateException("Invalid state while creating dungeon rooms. Maximum limit reached or adjacent rooms already occupied.");
            }
        }

        DungeonRoom newRoom = new DungeonRoom(this, roomDescription, monstersInRoom, newX, newY);
        System.out.println("Room with coordinates: (" + newX + ", " + newY + ")");
        rooms.add(newRoom);
    }

    /**
     * Gets the name of the dungeon.
     *
     * @return the name of the dungeon
     */
    public String getDungeonName() {
        return dungeonName;
    }

    /**
     * Sets the name of the dungeon.
     *
     * @param dungeonName the name of the dungeon
     * @throws IllegalArgumentException if the dungeon name is blank or empty
     */
    private void setDungeonName(String dungeonName) {
        if (dungeonName.isBlank() || dungeonName.isEmpty()) {
            throw new IllegalArgumentException("Dungeon name cannot be empty.");
        }
        this.dungeonName = dungeonName;
    }

    /**
     * Gets the width of the dungeon.
     *
     * @return the width of the dungeon
     */
    public static int getDungeonWidth() {
        return dungeonWidth;
    }

    /**
     * Sets the width of the dungeon.
     *
     * @param dungeonWidth the width of the dungeon
     * @throws IllegalArgumentException if the width is 0 or less
     */
    private static void setDungeonWidth(int dungeonWidth) {
        if (dungeonWidth <= 0) {
            throw new IllegalArgumentException("Width cannot be 0 or less.");
        }
        Dungeon.dungeonWidth = dungeonWidth;
    }

    /**
     * Gets the height of the dungeon.
     *
     * @return the height of the dungeon
     */
    public static int getDungeonHeight() {
        return dungeonHeight;
    }

    /**
     * Sets the height of the dungeon.
     *
     * @param dungeonHeight the height of the dungeon
     * @throws IllegalArgumentException if the height is 0 or less
     */
    public static void setDungeonHeight(int dungeonHeight) {
        if (dungeonHeight <= 0) {
            throw new IllegalArgumentException("Height cannot be 0 or less.");
        }
        Dungeon.dungeonHeight = dungeonHeight;
    }

    /**
     * Adds a room to the dungeon.
     *
     * @param room the room to add
     * @throws IllegalArgumentException if the room is null or already exists in the dungeon
     */
    public void addRoom(DungeonRoom room) {
        if (room == null) {
            throw new IllegalArgumentException("Cannot add a null DungeonRoom to the Dungeon.");
        }
        if (!rooms.contains(room)) {
            rooms.add(room);
            room.setDungeonMadeOf(this);
        }
    }

    /**
     * Removes a dungeon room from the dungeon.
     *
     * @param dungeonRoom the dungeon room to remove
     * @throws IllegalArgumentException  if the dungeon room is null or does not exist in the dungeon
     * @throws IllegalStateException     if there are no dungeon rooms in the dungeon
     */
    public void removeDungeonRoom(DungeonRoom dungeonRoom) {
        if (dungeonRoom == null) {
            throw new IllegalArgumentException("Cannot remove a null DungeonRoom from the Dungeon.");
        }
        if (rooms.isEmpty()) {
            throw new IllegalStateException("No DungeonRooms exist in the Dungeon. Cannot remove.");
        }
        if (!rooms.contains(dungeonRoom)) {
            throw new IllegalArgumentException("The specified DungeonRoom does not exist in the Dungeon.");
        }
        dungeonRoom.remove();
        rooms.remove(dungeonRoom);
    }

    /**
     * Removes the dungeon and all its rooms.
     */
    public void remove() {
        for (DungeonRoom d : rooms) {
            d.remove();
            removeDungeonRoom(d);
        }
        rooms.clear();
    }

    /**
     * Adds the dungeon to the extent.
     */
    public void addToExtent() {
        List<Dungeon> ext = dungeonExtent.get(this.getClass());
        if (ext == null) {
            ext = new ArrayList<>();
            dungeonExtent.put(this.getClass(), ext);
        }
        ext.add(this);
    }

    /**
     * Removes the dungeon from the extent.
     */
    protected void removeFromExtent() {
        dungeonExtent.get(this.getClass()).remove(this);
    }

    /**
     * Saves the dungeon extent to a file.
     *
     * @param fileName the name of the file to save the extent to
     */
    public static void saveExtent(ObjectOutputStream fileName) {
        try (ObjectOutputStream outputStream = fileName) {
            outputStream.writeObject(dungeonExtent);
            System.out.println("Dungeon extent saved." + dungeonExtent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the dungeon extent from a file.
     *
     * @param fileName the name of the file to load the extent from
     */
    @SuppressWarnings("unchecked")
    public static void loadExtent(ObjectInputStream fileName) {
        try (ObjectInputStream inputStream = fileName) {
            dungeonExtent = (Map<Class, List<Dungeon>>) inputStream.readObject();
            System.out.println("Dungeon extent loaded." + dungeonExtent);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the dungeon extent.
     *
     * @return the dungeon extent
     */
    public static Map<Class, List<Dungeon>> getDungeonExtent() {
        return dungeonExtent;
    }

    /**
     * Displays the coordinates of all the dungeon rooms.
     */
    public void showAllDungeonRooms() {
        for (DungeonRoom room : rooms) {
            System.out.println("Room: (" + room.getX() + ", " + room.getY() + ")");
        }
    }

    /**
     * Gets the dungeon room with the specified coordinates.
     *
     * @param x the X coordinate of the room
     * @param y the Y coordinate of the room
     * @return the dungeon room with the specified coordinates, or null if not found
     */
    public DungeonRoom getRoomWithCoord(int x, int y) {
        for (DungeonRoom room : rooms) {
            if (room.getX() == x && room.getY() == y) {
                return room;
            }
        }
        return null;
    }
}
