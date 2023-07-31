package MP1.Item;

import MP1.Dungeon.Dungeon;
import MP1.ExtentLoader.ExtentLoader;
import MP1.Monster.Monster;
import MP1.Player.Archetype;
import MP1.Player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class ItemOwnership implements Serializable {
    private static Map<Class, List<ItemOwnership>> itemExtent = new HashMap<>();
    private Item item;
    private Player player;
    private OwnershipWay ownershipWay;


    /**
     * Constructs an ItemOwnership object with the specified item, player, and ownership way.
     * Adds the item ownership to the extent.
     *
     * @param item          the item to be owned
     * @param player        the player who owns the item
     * @param ownershipWay  the way the item is owned
     */
    public ItemOwnership(Item item, Player player, OwnershipWay ownershipWay) {
        try {
            setItem(item);
            setPlayer(player);
            setOwnershipWay(ownershipWay);
            addToExtent();
        } catch (Exception e) {
            removeFromExtent();
        }
    }

    /**
     * Retrieves the item owned by this ItemOwnership.
     *
     * @return the item owned
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the item owned by this ItemOwnership.
     *
     * @param item the item to be owned
     * @throws IllegalArgumentException if the passed item is null or there is already an item assigned
     */
    public void setItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Passed item is null.");
        }
        if (this.item != null) {
            throw new IllegalArgumentException("Cannot set an Item, there is already an item, cannot change it.");
        }
        this.item = item;
        this.item.addOwnership(this);
    }

    /**
     * Retrieves the player who owns this item.
     *
     * @return the player who owns the item
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player who owns this item.
     *
     * @param player the player who owns the item
     * @throws IllegalArgumentException if the player is null or there is already a player assigned
     */
    public void setPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("player is null.");
        }
        if (this.player != null) {
            throw new IllegalArgumentException("Cannot set a Player, there is already a player assigned, cannot change it.");
        }
        this.player = player;
        this.player.addOwnership(this);
    }

    /**
     * Retrieves the ownership way of this item.
     *
     * @return the ownership way of the item
     */
    public OwnershipWay getOwnershipWay() {
        return ownershipWay;
    }

    /**
     * Sets the ownership way of this item.
     *
     * @param ownershipWay the ownership way of the item
     * @throws IllegalArgumentException if the ownership way is null
     */
    public void setOwnershipWay(OwnershipWay ownershipWay) {
        if (ownershipWay == null) {
            throw new IllegalArgumentException("Ownership way cannot be null");
        }
        this.ownershipWay = ownershipWay;
    }

    /**
     * Removes this item ownership from the extent and removes the references to the item and player.
     * Saves the extent after removal.
     */
    public void remove() {
        removeFromExtent();

        if (this.player != null) {
            player.getItemOwnerships().remove(this);
            this.player = null;
        }

        if (this.item != null) {
            item.getItemOwnerships().remove(this);
            this.item = null;
        }

        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes this item ownership from the extent.
     */
    protected void removeFromExtent() {
        List<ItemOwnership> ext = itemExtent.get(getClass());
        if (ext != null) {
            ext.remove(this);
        }
    }

    /**
     * Adds this item ownership to the extent.
     */
    public void addToExtent() {
        List<ItemOwnership> ext = itemExtent.get(this.getClass());
        if (ext == null) {
            ext = new ArrayList<>();
            itemExtent.put(this.getClass(), ext);
        }
        ext.add(this);
    }

    /**
     * Retrieves the extent of all item ownerships.
     *
     * @return the list of item ownerships
     */
    public static List<ItemOwnership> getItemOwnershipExtent() {
        List<ItemOwnership> ext = itemExtent.get(ItemOwnership.class);
        if (ext == null) {
            return new ArrayList<>();
        }
        return Collections.unmodifiableList(ext);
    }

    /**
     * Saves the item ownership extent to the specified ObjectOutputStream.
     *
     * @param stream the ObjectOutputStream to save the extent to
     * @throws IOException if an I/O error occurs during saving
     */
    public static void saveExtent(ObjectOutputStream stream) throws IOException {
        try (ObjectOutputStream outputStream = stream) {
            outputStream.writeObject(itemExtent);
            System.out.println("ItemOwnership extent saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the item ownership extent from the specified ObjectInputStream.
     *
     * @param fileName the ObjectInputStream to load the extent from
     */
    @SuppressWarnings("unchecked")
    public static void loadExtent(ObjectInputStream fileName) {
        try (ObjectInputStream inputStream = fileName) {
            itemExtent = (Map<Class, List<ItemOwnership>>) inputStream.readObject();
            System.out.println("ItemOwnership extent loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
