
/**

 The ExtentLoader class is responsible for loading and saving various extents of the game.
 It provides methods to load and save extents for different game components such as Dungeon, DungeonRoom,
 History, Item, LiveBeing, Archetype, Monster, and Player.
 */
package MP1.ExtentLoader;

import MP1.Dungeon.Dungeon;
import MP1.Dungeon.DungeonRoom;
import MP1.Dungeon.History;
import MP1.Item.Item;
import MP1.Item.ItemOwnership;
import MP1.LiveBeing.LiveBeing;
import MP1.Monster.Monster;
import MP1.Player.Archetype;
import MP1.Player.Player;

import java.io.*;

public class ExtentLoader implements Serializable{
    /**
     * Loads all extents of game elements.
     *
     * @throws IOException            if an I/O error occurs while loading the extents.
     * @throws ClassNotFoundException if a class of the serialized object cannot be found.
     */
    public static void loadAllExtents() throws IOException, ClassNotFoundException {
        loadDungeonExtent();
        loadDungeonRoomExtent();
        loadHistoryExtent();
        loadItemExtent();
        loadLiveBeingExtent();
        loadArchetypeExtent();
        loadItemOwnershipExtent();
        loadMonsterExtent();
        loadPlayerExtent();
    }

    private static void loadDungeonExtent() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/MP1/ExtentLoader/dungeonExtent.dat"));
        Dungeon.loadExtent(in);
        in.close();
    }

    private static void loadDungeonRoomExtent() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/MP1/ExtentLoader/dungeonRoomExtent.dat"));
        DungeonRoom.loadExtent(in);
        in.close();
    }

    private static void loadHistoryExtent() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/MP1/ExtentLoader/historyExtent.dat"));
        History.loadExtent(in);
        in.close();
    }

    private static void loadItemExtent() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/MP1/ExtentLoader/itemExtent.dat"));
        Item.loadExtent(in);
        in.close();
    }

    private static void loadLiveBeingExtent() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/MP1/ExtentLoader/liveBeingExtent.dat"));
        LiveBeing.loadExtent(in);
        in.close();
    }

    private static void loadItemOwnershipExtent() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/MP1/ExtentLoader/itemOwnership.dat"));
        ItemOwnership.loadExtent(in);
        in.close();
    }

    private static void loadArchetypeExtent() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/MP1/ExtentLoader/archetypeExtent.dat"));
        Archetype.loadExtent(in);
        in.close();
    }

    private static void loadMonsterExtent() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/MP1/ExtentLoader/monsterExtent.dat"));
        Monster.loadExtent(in);
        in.close();
    }

    private static void loadPlayerExtent() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/MP1/ExtentLoader/playerExtent.dat"));
        Player.loadExtent(in);
        in.close();
    }

    /**
     * Saves all extents of game elements.
     *
     * @throws IOException if an I/O error occurs while saving the extents.
     */
    public static void saveAllExtents() throws IOException {
        saveDungeonExtent();
        saveDungeonRoomExtent();
        saveHistoryExtent();
        saveItemExtent();
        saveLiveBeingExtent();
        saveItemOwnershipExtent();
        saveArchetypeExtent();
        savePlayerExtent();
        saveMonsterExtent();
    }

    private static void saveDungeonExtent() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/MP1/ExtentLoader/dungeonExtent.dat"));
        Dungeon.saveExtent(out);
        out.close();
    }

    private static void saveDungeonRoomExtent() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/MP1/ExtentLoader/dungeonRoomExtent.dat"));
        DungeonRoom.saveExtent(out);
        out.close();
    }

    private static void saveHistoryExtent() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/MP1/ExtentLoader/historyExtent.dat"));
        History.saveExtent(out);
        out.close();
    }

    private static void saveItemOwnershipExtent() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/MP1/ExtentLoader/itemOwnership.dat"));
        ItemOwnership.saveExtent(out);
        out.close();
    }

    private static void saveItemExtent() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/MP1/ExtentLoader/itemExtent.dat"));
        Item.saveExtent(out);
        out.close();
    }

    private static void saveLiveBeingExtent() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/MP1/ExtentLoader/liveBeingExtent.dat"));
        LiveBeing.saveExtent(out);
        out.close();
    }

    private static void savePlayerExtent() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/MP1/ExtentLoader/playerExtent.dat"));
        Player.saveExtent(out);
        out.close();
    }

    private static void saveMonsterExtent() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/MP1/ExtentLoader/monsterExtent.dat"));
        Monster.saveExtent(out);
        out.close();
    }

    private static void saveArchetypeExtent() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/MP1/ExtentLoader/archetypeExtent.dat"));
        Archetype.saveExtent(out);
        out.close();
    }

}
