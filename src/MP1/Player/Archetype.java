
package MP1.Player;

import MP1.Item.Item;
import MP1.Item.ItemType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
/**

 The "Archetype" class represents a player archetype in the game.
 It is used to define the initial attributes and items for a player character.
 The Archetype class implements the Serializable interface for object serialization.
 The Archetype has the following attributes:
 name: The name of the archetype.
 startingWeapon: The initial weapon item for the archetype.
 startingArmour: The initial armour item for the archetype.
 startingHealingItem: The initial healing item for the archetype.
 playersWithThisArchetype: A list of players that have chosen this archetype.
 The Archetype can be instantiated with a specified name, starting weapon, starting armour,
 and starting healing item.
 This class provides getter and setter methods for the attributes, as well as methods to add
 and remove players from the archetype.
 The Archetype class also provides static methods to save and load the archetype extent,
 which is a map that stores all archetype instances created in the game.
 Note: This class depends on the "Player" class and the "Item" class for functionality.
 @see Player
 @see Item
 @see Serializable
 */
public class Archetype implements Serializable {

    private static Map<Class, List<Archetype>> archetypeExtent = new HashMap<>();

    private String name;
    private Item startingWeapon;
    private Item startingArmour;
    private Item startingHealingItem;

    private List<Player> playersWithThisArchetype = new ArrayList<>();

    public Archetype(String name, Item startingWeapon, Item startingArmour, Item startingHealingItem) {
        try {
            setName(name);
            if(startingWeapon != null)
            {
                setStartingWeapon(startingWeapon);
            }
            if(startingArmour != null)
            {
                setStartingArmour(startingArmour);
            }
            if(startingHealingItem != null)
            {
                setStartingHealingItem(startingHealingItem);
            }

            addToExtent();
        } catch (Exception e){
            removeFromExtent();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Archetype must have a name");
        }
        this.name = name;
    }

    public Item getStartingWeapon() {
        return startingWeapon;
    }

    public void setStartingWeapon(Item startingWeapon) {
        if (!startingWeapon.getItemTypes().contains(ItemType.WEAPON)) {
            throw new IllegalArgumentException("Starting weapon is not a weapon");
        }
        this.startingWeapon = startingWeapon;
    }

    public Item getStartingArmour() {
        return startingArmour;
    }

    public void setStartingArmour(Item startingArmour) {
        if (!startingArmour.getItemTypes().contains(ItemType.ARMOUR)) {
            throw new IllegalArgumentException("Starting weapon is not an armour");
        }
        this.startingArmour = startingArmour;
    }

    public Item getStartingHealingItem() {
        return startingHealingItem;
    }

    public void setStartingHealingItem(Item startingHealingItem) {
        if (!startingHealingItem.getItemTypes().contains(ItemType.HEALINGITEM)) {
            throw new IllegalArgumentException("Starting weapon is not a healing item");
        }
        this.startingHealingItem = startingHealingItem;
    }

    public List<Player> getPlayersWithThisArchetype() {
        return new ArrayList<>(playersWithThisArchetype);
    }

    public void addPlayer(Player p) throws Exception {
        if (p == null) {
            throw new IllegalArgumentException("Player is null, cannot be added to archetype");
        }
        if (playersWithThisArchetype.contains(p)) {
            return;
        }
        playersWithThisArchetype.add(p);
        p.setArchetypeUsed(this);
    }

    public void removePlayer(Player p) throws Exception {
        if (playersWithThisArchetype.isEmpty()) {
            throw new IllegalArgumentException("Cannot remove a player from an empty list");
        }
        if (p == null) {
            throw new IllegalArgumentException("Player is null, cannot be removed from archetype list");
        }
        if (playersWithThisArchetype.contains(p)) {
            playersWithThisArchetype.remove(p);
            p.setArchetypeUsed(null);
        }
    }

    private void addToExtent() {
        List<Archetype> ext = archetypeExtent.get(this.getClass());
        if (ext == null) {
            ext = new ArrayList<>();
            archetypeExtent.put(this.getClass(), ext);
        }
        ext.add(this);
    }

    protected void removeFromExtent() {
        archetypeExtent.get(this.getClass()).remove(this);
    }

    public static void saveExtent(ObjectOutputStream fileName) {
        try (ObjectOutputStream outputStream = fileName) {
            outputStream.writeObject(archetypeExtent);
            System.out.println("Archetype extent saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadExtent(ObjectInputStream fileName) {
        try (ObjectInputStream inputStream = fileName) {
            archetypeExtent = (Map<Class, List<Archetype>>) inputStream.readObject();
            System.out.println("Archetype extent loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Map<Class, List<Archetype>> getArchetypeExtent() {
        return Collections.unmodifiableMap(archetypeExtent);
    }
}