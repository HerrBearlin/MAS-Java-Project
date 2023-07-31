package MP1.Player;

import MP1.Dungeon.DungeonRoom;
import MP1.Dungeon.History;
import MP1.Dungeon.RoomStatus;
import MP1.ExtentLoader.ExtentLoader;
import MP1.Item.*;
import MP1.LiveBeing.LiveBeing;
import MP1.Monster.Monster;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;


/**
 * The Player class represents a player character in a game. It extends the LiveBeing class,
 * inheriting attributes and methods related to health and character management. Players
 * are interactive entities controlled by users and possess various attributes and capabilities.
 *
 * The Player class maintains information such as the player's archetype, equipped weapon and armor,
 * current position in the game's dungeon, and a collection of owned items. It also supports
 * associations with historical records, allowing tracking of the player's progress and actions.
 *
 * Players can perform actions such as attacking other entities, using items, and navigating
 * within the game world. They can enter and mark dungeon rooms as cleared, subject to certain conditions.
 *
 * The Player class implements the Serializable interface to support object serialization, allowing
 * instances of Player to be saved and loaded from streams or files.
 *
 * Player extents are stored in a static Map structure that holds a collection of players categorized
 * by their respective classes. The extents can be saved and loaded using the provided static methods.
 */
public class Player extends LiveBeing implements Serializable {
    private static Map<Class, List<Player>> playerExtent = new HashMap<>();

   //complex attribute
   private Archetype archetypeUsed;
   private Item weapon;
   private Item armour;

   //ATTRIBUTE RELATIONSHIP
    private Set<ItemOwnership> itemOwnerships = new HashSet<>();

   //VARIABLES FOR WALKING IN THE DUNGEON
   private Integer currentX;
   private Integer currentY;

    // Association with History (0 to *)
    private Set<History> histories = new HashSet<>();

    public Player(double maxHealth, double currentHealth, String name, Archetype archetypeUsed, Integer currentX, Integer currentY) {
        super(maxHealth, currentHealth, name);
        try{
            setArchetypeUsed(archetypeUsed);
            setWeapon(archetypeUsed.getStartingWeapon());
            setArmour(archetypeUsed.getStartingArmour());
            System.out.println("Creating a player.");
            System.out.println("Giving starting weapon.");
            if (archetypeUsed.getStartingWeapon() != null) {
                ItemOwnership weaponOwnership = new ItemOwnership(archetypeUsed.getStartingWeapon(), this, OwnershipWay.STARTING);
                addOwnership(weaponOwnership);
                System.out.println("Ownership with starting weapon added.");
            }
            System.out.println("Giving starting armour.");
            if (archetypeUsed.getStartingArmour() != null) {
                ItemOwnership armourOwnership = new ItemOwnership(archetypeUsed.getStartingArmour(), this, OwnershipWay.STARTING);
                addOwnership(armourOwnership);
                System.out.println("Ownership with starting armour added.");
            }
            System.out.println("Giving starting healing Item.");
            if (archetypeUsed.getStartingHealingItem() != null) {
                ItemOwnership healingItemOwnership = new ItemOwnership(archetypeUsed.getStartingHealingItem(), this, OwnershipWay.STARTING);
                addOwnership(healingItemOwnership);
                System.out.println("Ownership with starting healing item added.");
            }
            setCurrentX(currentX);
            setCurrentY(currentY);
            addToExtent();
        }catch (Exception e){
            removeFromExtent();
        }
    }

    public Player(double maxHealth, double currentHealth, String name, Archetype archetype, Item weapon, Item armour, Integer currentX, Integer currentY) {
        super(maxHealth, currentHealth, name);
        try{
            setArchetypeUsed(archetype);
            setWeapon(weapon);
            setArmour(armour);
            setCurrentX(currentX);
            setCurrentY(currentY);
            addToExtent();
        }catch (Exception e){
            removeFromExtent();
        }
    }

    public void setArchetypeUsed(Archetype archetypeUsed) throws Exception {
        if(this.archetypeUsed == archetypeUsed)
        {
            return;
        }
        if(archetypeUsed == null && this.archetypeUsed == null)
        {
            throw new IllegalArgumentException("Archetype cannot be null.");
        }else if(this.archetypeUsed == null && archetypeUsed != null)
        {
            this.archetypeUsed = archetypeUsed;
            archetypeUsed.addPlayer(this);
        }else if(this.archetypeUsed != null && archetypeUsed == null)
        {
            Archetype prevArchetype = this.archetypeUsed;
            this.archetypeUsed = null;
            prevArchetype.removePlayer(this);
        }else if(this.archetypeUsed != null && archetypeUsed != null)
        {
            this.archetypeUsed = archetypeUsed;
            archetypeUsed.addPlayer(this);
        }else {
            throw new Exception("Error whilst setting the archetypeUsed for Player.");
        }
    }

    public Archetype getArchetypeUsed() {
        return archetypeUsed;
    }

    public Item getWeapon() {
        return weapon;
    }

    public void setWeapon(Item weapon) {
        //if item is not a weapon
        if(!weapon.getItemTypes().contains(ItemType.WEAPON))
        {
            throw new IllegalArgumentException("Item declared as weapon is not a weapon");
        }
        this.weapon =  weapon;
    }

    public Item getArmour() {

        return armour;
    }

    public void setArmour(Item armour) {
        if(!armour.getItemTypes().contains(ItemType.ARMOUR))
        {
            throw new IllegalArgumentException("Item declared as armour is not armour");
        }
        this.armour =  armour;
    }

    public Set<ItemOwnership> getItemOwnerships() {
        return itemOwnerships;
    }

    public void addOwnership(ItemOwnership i){
        if(i == null)
        {
            throw new IllegalArgumentException("Item ownership cannot be null for adding ownership");
        }
        if(i.getPlayer() != this)
        {
            throw new IllegalArgumentException("Player is not part of the ownership");
        }

        this.itemOwnerships.add(i);
    }

    public void removeOwnership(ItemOwnership i){
        if(!this.itemOwnerships.contains(i))
        {
            throw new IllegalArgumentException("This player does not own this item");
        }
        this.itemOwnerships.remove(i);
        i.remove();
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(Integer currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(Integer currentY) {
        this.currentY = currentY;
    }


    public void addHistory(History history) {
        if (history == null) {
            throw new IllegalArgumentException("History cannot be null.");
        }
        histories.add(history);
        history.setPlayer(this);
    }

    public void removeHistory(History history) {
        if (history == null) {
            throw new IllegalArgumentException("History cannot be null.");
        }
        if (histories.contains(history)) {
            histories.remove(history);
            history.setPlayer(null); // Set the history's player to null without triggering its removeHistory() method
        }
    }

    public Set<History> getHistories() {
        return histories;
    }

    public static Map<Class, List<Player>> getPlayerExtent() {
        return Collections.unmodifiableMap(playerExtent);
    }

    private void addToExtent() {
        List<Player> ext = playerExtent.get(this.getClass());
        if (ext == null) {
            ext = new ArrayList<>();
            playerExtent.put(this.getClass(), ext);
        }
        ext.add(this);
    }
    public static void saveExtent(ObjectOutputStream fileName) {
        try (ObjectOutputStream outputStream = fileName) {
            outputStream.writeObject(playerExtent);
            System.out.println("Player extent saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadExtent(ObjectInputStream fileName) {
        try (ObjectInputStream inputStream = fileName) {
            playerExtent = (Map<Class, List<Player>>) inputStream.readObject();
            System.out.println("Player extent loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public void move() {
//        History history = null;
//        DungeonRoom currentRoom = null;
//
//        // Find the current room of the player
//        for (History h : getHistories()) {
//            if (h.getPlayer() == this) {
//                history = h;
//                currentRoom = history.getDungeonRoom();
//                break;
//            }
//        }
//
//        if (currentRoom == null) {
//            System.out.println("Player is not in any room.");
//            return;
//        }
//
//        List<DungeonRoom> availableRooms = DungeonRoom.getAvailableRoomsForThePlayer(this);
//        if (availableRooms.isEmpty()) {
//            System.out.println("No available rooms to move.");
//            return;
//        }
//
//        System.out.println("Available rooms to move:");
//        int index = 1;
//        for (DungeonRoom room : availableRooms) {
//            System.out.println(index + ". " + room.getDescription());
//            index++;
//        }
//
//        Scanner scanner = new Scanner(System.in);
//        int choice;
//        do {
//            System.out.print("Enter the number of the room to move to: ");
//            choice = scanner.nextInt();
//        } while (choice < 1 || choice > availableRooms.size());
//
//        DungeonRoom chosenRoom = availableRooms.get(choice - 1);
//        setCurrentX(chosenRoom.getX());
//        setCurrentY(chosenRoom.getY());
//        System.out.println("Moved to room: " + chosenRoom.getDescription());
//    }

    @Override
    public Double attack(LiveBeing target) {
        if (target == null) {
            throw new IllegalArgumentException("Target cannot be null.");
        }

        if (weapon != null) {
            double damage = weapon.getDamage();
            if(weapon instanceof Vampiric)
            {
                ((Vampiric) weapon).healWhenDamaging();
            }
            target.setCurrentHealth(target.getCurrentHealth() - damage);
            System.out.println(getName() + " attacked " + target.getName() + " using " + weapon.getItemName() + " for " + damage + " damage.");
        } else {
            System.out.println(getName() + " does not have a weapon equipped and cannot attack.");
        }
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weapon.getDamage();
    }

    public void useItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        for (ItemOwnership ownership : itemOwnerships) {
            if (ownership.getItem() == item) {
                if (item.getItemTypes().contains(ItemType.HEALINGITEM)) {
                    double healingAmount = item.getAmountHealed();
                    double newHealth = getCurrentHealth() + healingAmount;
                    setCurrentHealth(Math.min(newHealth, getMaxHealth()));
                    System.out.println(getName() + " used " + item.getItemName() + " and healed for " + healingAmount + " health.");
                }

                if (item.getDurabilityType() == DurabilityType.PASSIVE)
                {
                    ownership.getItem().decreaseDurability();
                    System.out.println(getName() + " used " + item.getItemName() + " and its durability decreased.");
                }

                if (item.getDurabilityType() == DurabilityType.CONSUMABLE)
                {
                    ownership.getItem().decreaseCharges();
                    System.out.println(getName() + " used " + item.getItemName() + " and its charges decreased.");
                }

                return;
            }
        }

        System.out.println(getName() + " does not own the item " + item.getItemName() + " and cannot use it.");
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Player other = (Player) obj;
        return Objects.equals(this.getName(), other.getName());  // Compare the necessary field(s) for equality
    }

    //boolean ownershipExists = false;
    //        for(ItemOwnership i: itemOwnerships)
    //        {
    //            if(i.getItem().getItemName().equals(weapon.getItemName()))
    //            {
    //                ownershipExists = true;
    //            }
    //        }
    //        if(ownershipExists)
    //        {
    //
    //        }
    //        itemOwnerships.add(new ItemOwnership(weapon, this, 1));

    public void EnterTheRoom(DungeonRoom room){
        Set<History> histories = this.getHistories();
        boolean correct = false;
        for (History h: histories) {
            if(h.getPlayer().equals(this) && h.getDungeonRoom().equals(room))
            {
                correct = true;
            }
        }
        if(correct !=true)
        {
            throw new IllegalArgumentException("Cannot enter the room, player does not have appropriate history");
        }
        setCurrentX(room.x);
        setCurrentY(room.y);
        room.getPlayerHistory(this).setRoomStatus(RoomStatus.VISITED);
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void MarkRoomAsCleared(DungeonRoom room){
        Set<History> histories = this.getHistories();
        boolean correct = false;
        for (History h: histories
        ) {
            if(h.getPlayer().equals(this) && h.getDungeonRoom().equals(room))
            {
                correct = true;
            }
        }
        if(correct !=true)
        {
            throw new IllegalArgumentException("Cannot clear the room, player does not have appropriate history");
        }
        for (Monster m : room.getMonsters())
        {
            if(m.getCurrentHealth() > 0)
            {
                throw new IllegalArgumentException("Cannot clear the room, there are monsters alive");
            }
        }
        room.getPlayerHistory(this).setRoomStatus(RoomStatus.CLEARED);
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
