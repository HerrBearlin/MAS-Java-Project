package MP1.Dungeon;

import MP1.Monster.Monster;
import MP1.Player.Player;

import java.io.*;
import java.util.*;
/**
 * The DungeonRoom class represents a room in a dungeon. Each room is part of a Dungeon
 * and may contain monsters and have historical records associated with it.
 *
 * Rooms are defined by their description, coordinates, and the monsters present within them.
 * The DungeonRoom class provides methods to manage and interact with the monsters in a room,
 * retrieve room descriptions, and access historical records for the room.
 *
 * DungeonRoom instances are stored in a static Map called 'roomExtent', which categorizes the rooms
 * by their respective classes. This allows for efficient retrieval and management of room instances.
 * The roomExtent can be saved and loaded using the provided static methods.
 */
public class DungeonRoom implements Serializable {

    private static Map<Class, List<DungeonRoom>> roomExtent = new HashMap<>();

    private Dungeon dungeonMadeOf;
    private String roomDescription;
    private Map<String, Monster> monstersInRoom = new HashMap<>();
    private Set<History> histories = new HashSet<>();

    public Integer x, y;

    public DungeonRoom(Dungeon dungeonMadeOf, String roomDescription, Map<String, Monster> monstersInRoom, Integer x, Integer y) {
        try {
            setDungeonMadeOf(dungeonMadeOf);
            setRoomDescription(roomDescription);
            setMonstersInRoom(monstersInRoom);
            setX(x);
            setY(y);
            addToExtent();
        } catch (Exception e) {
            removeFromExtent();
        }
    }

    public Map<String, Monster> getMonstersInRoom() {
        if (monstersInRoom != null && !monstersInRoom.isEmpty()) {
            return monstersInRoom;
        } else {
            return Collections.emptyMap();
        }
    }

    public List<Monster> getMonsters() {
        return new ArrayList<>(monstersInRoom.values());
    }

    public Monster getMonsterWithName(String monsterName) {
        if (monsterName != null) {
            return monstersInRoom.get(monsterName);
        }
        return null;
    }

    public void addMonsterToRoom(Monster m) throws Exception {
        if (m == null) {
            throw new IllegalArgumentException("Monster is null, cannot add to room");
        }
        if (monstersInRoom.containsKey(m.getName())) {
            return;
        }
        if (monstersInRoom.size() >= 3) {
            throw new IllegalArgumentException("There can only be up to a maximum of 3 monsters in a room. Cannot add more monsters.");
        }
        monstersInRoom.put(m.getName(), m);
        m.setInhabitedRoom(this);
    }

    public void removeMonsterFromRoom(Monster m) throws Exception {
        if (m == null) {
            throw new IllegalArgumentException("Monster is null, cannot remove from the room");
        }
        if (monstersInRoom.containsValue(m)) {
            monstersInRoom.remove(m.getName());
            m.setInhabitedRoom(null);
        }
    }

    public String getDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        if (roomDescription != null) {
            this.roomDescription = roomDescription;
        }
    }


    public Dungeon getDungeonMadeOf() {
        return dungeonMadeOf;
    }

    protected void setDungeonMadeOf(Dungeon dungeonMadeOf) {
        if (this.dungeonMadeOf != null && this.dungeonMadeOf != dungeonMadeOf) {
            this.dungeonMadeOf.removeDungeonRoom(this);
        }
        this.dungeonMadeOf = dungeonMadeOf;
        if (dungeonMadeOf != null) {
            dungeonMadeOf.addRoom(this);
        }
    }

    public void setMonstersInRoom(Map<String, Monster> monstersInRoom) {
        if (monstersInRoom != null) {
            this.monstersInRoom = monstersInRoom;
        }
    }

    public void setX(Integer x) {
        if (x == null || x < 0) {
            throw new IllegalArgumentException("Invalid value for X. X must be a non-null positive number.");
        }
        this.x = x;
    }

    public void setY(Integer y) {
        if (y == null || y < 0) {
            throw new IllegalArgumentException("Invalid value for Y. Y must be a non-null positive number.");
        }
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Set<History> getHistories() {
        return histories;
    }

    public History getPlayerHistory(Player player) {
        for (History history : histories) {
            if (history.getPlayer().equals(player)) {
                return history;
            }
        }
        return null;  // Return null if no history found for the player
    }


    public void addHistory(History history) {
        if (history == null) {
            throw new IllegalArgumentException("History cannot be null.");
        }
        histories.add(history);
        history.setDungeonRoom(this);
    }

    public void removeHistory(History history) {
        if (history == null) {
            throw new IllegalArgumentException("History cannot be null.");
        }
        histories.remove(history);
        history.setDungeonRoom(null);
    }

    public void remove() {
        for (Monster m : monstersInRoom.values()) {
            try {
                m.setInhabitedRoom(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (dungeonMadeOf != null) {
            dungeonMadeOf.removeDungeonRoom(this);
        }
        for (History history : histories) {
            history.setDungeonRoom(null);
        }
        removeFromExtent();
    }

    private void addToExtent() {
        List<DungeonRoom> ext = roomExtent.get(this.getClass());
        if (ext == null) {
            ext = new ArrayList<>();
            roomExtent.put(this.getClass(), ext);
        }
        ext.add(this);
    }

    private void removeFromExtent() {
        roomExtent.get(this.getClass()).remove(this);
    }

    public static void saveExtent(ObjectOutputStream fileName) {
        try (ObjectOutputStream outputStream = fileName) {
            outputStream.writeObject(roomExtent);
            System.out.println("DungeonRoom extent saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadExtent(ObjectInputStream fileName) {
        try (ObjectInputStream inputStream = fileName) {
            roomExtent = (Map<Class, List<DungeonRoom>>) inputStream.readObject();
            System.out.println("DungeonRoom extent loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Map<Class, List<DungeonRoom>> getRoomExtent() {
        return roomExtent;
    }

    public static List<DungeonRoom> getAvailableRoomsForThePlayer(Player player) {
        List<DungeonRoom> availableRooms = new ArrayList<>();

        int currentPlayerX = player.getCurrentX();
        int currentPlayerY = player.getCurrentY();

        for (History history : History.getExtent()) {
            DungeonRoom room = history.getDungeonRoom();
            int roomX = room.getX();
            int roomY = room.getY();

            if ((roomX == currentPlayerX && roomY == currentPlayerY) ||
                    (roomX == currentPlayerX + 1 && roomY == currentPlayerY) ||
                    (roomX == currentPlayerX && roomY == currentPlayerY + 1) ||
                    (roomX == currentPlayerX - 1 && roomY == currentPlayerY) ||
                    (roomX == currentPlayerX && roomY == currentPlayerY - 1) ||
                    (roomX == currentPlayerX + 1 && roomY == currentPlayerY - 1)) {
                availableRooms.add(room);
            }
        }



        return availableRooms;
    }

    public String getName(){
        return this.getX() + " || " + this.getY();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        DungeonRoom otherRoom = (DungeonRoom) obj;
        return Objects.equals(x, otherRoom.x) &&
                Objects.equals(y, otherRoom.y);
    }
}