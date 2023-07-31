package MP1.ImplementationTests;

import MP1.Dungeon.Dungeon;
import MP1.Dungeon.DungeonRoom;
import MP1.Dungeon.History;
import MP1.Dungeon.RoomStatus;
import MP1.ExtentLoader.ExtentLoader;
import MP1.Item.*;
import MP1.Monster.Monster;
import MP1.Monster.Physical;
import MP1.Player.Archetype;
import MP1.Player.Player;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class SaveExtentTest implements Serializable {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        EnumSet<ItemType> itemTypes4 = EnumSet.of(ItemType.WEAPON);
        String itemName4 = "Starter Axe";
        String itemDescription4 = "A basic axe for beginners";
        Double damage4 = 5.0;
        Double defence4 = 0.0;
        Double amountHealed4 = 0.0;
        DurabilityType durabilityType4 = DurabilityType.PASSIVE;
        Double maxCharges4 = 0.0;
        Double maxDurability4 = 10.0;

        Item startingWeapon = new Item(itemTypes4, itemName4, itemDescription4, damage4, defence4, amountHealed4,
                durabilityType4, maxCharges4, maxDurability4);

        EnumSet<ItemType> armorTypes = EnumSet.of(ItemType.ARMOUR);
        String armorName = "Starter Armor";
        String armorDescription = "Basic armor for protection";
        Double armorDamage = 0.0;
        Double armorDefence = 5.0;
        Double armorAmountHealed = 0.0;
        DurabilityType armorDurabilityType = DurabilityType.PASSIVE;
        Double armorMaxCharges = 0.0;
        Double armorMaxDurability = 20.0;

        Item startingArmour = new Item(armorTypes, armorName, armorDescription, armorDamage, armorDefence, armorAmountHealed,
                armorDurabilityType, armorMaxCharges, armorMaxDurability);

        // Create the starting healing item
        EnumSet<ItemType> healingItemTypes = EnumSet.of(ItemType.HEALINGITEM);
        String healingItemName = "Potion";
        String healingItemDescription = "A healing potion";
        Double healingItemDamage = 0.0;
        Double healingItemDefence = 0.0;
        Double healingItemAmountHealed = 15.0;
        DurabilityType healingItemDurabilityType = DurabilityType.CONSUMABLE;
        Double healingItemMaxCharges = 3.0;
        Double healingItemMaxDurability = 0.0;

        Item startingHealingItem = new Item(healingItemTypes, healingItemName, healingItemDescription, healingItemDamage, healingItemDefence,
                healingItemAmountHealed, healingItemDurabilityType, healingItemMaxCharges, healingItemMaxDurability);


        Archetype warriorArchetype = new Archetype("Warrior", startingWeapon, startingArmour, startingHealingItem);
        Archetype archerArchetype = new Archetype("Archer", null, startingArmour, startingHealingItem);


        String itemName5 = "NEW Axe";
        String itemDescription5 = "A basic axe for beginners";
        Double damage5 = 10.0;
        Double defence5 = 0.0;
        Double amountHealed5 = 0.0;
        DurabilityType durabilityType5 = DurabilityType.PASSIVE;
        Double maxCharges5 = 0.0;
        Double maxDurability5 = 100.0;

        Item newWeapon = new Item(itemTypes4, itemName5, itemDescription5, damage5, defence5, amountHealed5,
                durabilityType5, maxCharges5, maxDurability5);

        Player player1 = new Player(100.0, 100.0, "Juanito", warriorArchetype, 0, 0);
        Player player2 = new Player(100.0, 100.0, "Juanito2", warriorArchetype, 0, 0);

        ItemOwnership weaponBetterOwnership = new ItemOwnership(newWeapon, player1, OwnershipWay.LOOTED);

// Save Player extent
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("playerExtent.dat"))) {
            Player.saveExtent(out);
        }


    //create a dungeon with rooms
        Dungeon dungeon = new Dungeon("DungeonTest");
        int prevX = 0;
        int prevY = 0;

        for (int x = 0; x < Dungeon.getDungeonWidth(); x++) {
            for (int y = 0; y < Dungeon.getDungeonHeight(); y++) {
                Map<String, Monster> monsters = new HashMap<>();


                // Create the first item
                EnumSet<ItemType> itemTypes1 = EnumSet.of(ItemType.WEAPON, ItemType.HEALINGITEM);
                String itemName1 = "Sword";
                String itemDescription1 = "A powerful sword";
                Double damage1 = 10.0;
                Double defence1 = 0.0;
                Double amountHealed1 = 5.0;
                DurabilityType durabilityType1 = DurabilityType.CONSUMABLE;
                Double maxCharges1 = 10.0;
                Double maxDurability1 = 0.0;

                Item item1 = new Item(itemTypes1, itemName1, itemDescription1, damage1, defence1, amountHealed1,
                        durabilityType1, maxCharges1, maxDurability1);

                // Create the second item
                EnumSet<ItemType> itemTypes2 = EnumSet.of(ItemType.ARMOUR);
                String itemName2 = "Shield";
                String itemDescription2 = "A sturdy shield";
                Double damage2 = 0.0;
                Double defence2 = 1.0;
                Double amountHealed2 = 0.0;
                DurabilityType durabilityType2 = DurabilityType.PASSIVE;
                Double maxCharges2 = 0.0;
                Double maxDurability2 = 50.0;

                Item item2 = new Item(itemTypes2, itemName2, itemDescription2, damage2, defence2, amountHealed2,
                        durabilityType2, maxCharges2, maxDurability2);

                // Create the third item
                EnumSet<ItemType> itemTypes3 = EnumSet.of(ItemType.WEAPON, ItemType.ARMOUR);
                String itemName3 = "Hammer";
                String itemDescription3 = "A heavy hammer";
                Double damage3 = 15.0;
                Double defence3 = 10.0;
                Double amountHealed3 = 0.0;
                DurabilityType durabilityType3 = DurabilityType.CONSUMABLE;
                Double maxCharges3 = 5.0;
                Double maxDurability3 = 0.0;

                Item item3 = new Item(itemTypes3, itemName3, itemDescription3, damage3, defence3, amountHealed3,
                        durabilityType3, maxCharges3, maxDurability3);

                Item item4 = new Item(itemTypes2, itemName2, itemDescription2, damage2, defence2, amountHealed2,
                        durabilityType2, maxCharges2, maxDurability2);

                Item item5 = new Item(itemTypes2, itemName2, itemDescription2, damage2, defence2, amountHealed2,
                        durabilityType2, maxCharges2, maxDurability2);

                Item item6 = new Item(itemTypes2, itemName2, itemDescription2, damage2, defence2, amountHealed2,
                        durabilityType2, maxCharges2, maxDurability2);


                Set<Item> treasure = new HashSet<>();
                treasure.add(item1);
                treasure.add(item2);
                treasure.add(item3);

                Set<Item> treasure2 = new HashSet<>();
                treasure2.add(item4);
                treasure2.add(item5);
                treasure2.add(item6);


                try {
                    Monster dragon2 = new Physical("Dragon2  "+ y + x, 50.0, 50.0, 1.0, 3.0, treasure2, 1.0);
                    Monster dragon = new Physical("Dragon  " + y + x, 50.0, 50.0, 1.0, 3.0, treasure, 1.0);
                    monsters.put("Dragon", dragon);
                    monsters.put("Dragon2", dragon2);
                    dungeon.createDungeonRoom("Room at (" + x + ", " + y + ")", monsters, x, y);
                    DungeonRoom room = dungeon.getRoomWithCoord(x, y);
                    room.setRoomDescription("Room at (" + room.getX() + ", " + room.getY() + ")");

                    if (room != null) {
                        History roomHistory = new History(player1, room, RoomStatus.NOTVISITED);
                        room.addHistory(roomHistory);
                        player1.addHistory(roomHistory);
                        System.out.println(room.getPlayerHistory(player1));
                        System.out.println("Room and monster created for (" + x + ", " + y + ").");
                    } else {
                        System.out.println("Error creating dungeon room: Room is null for (" + x + ", " + y + ").");
                    }

                    prevX = x;
                    prevY = y;
                } catch (Exception e) {
                    System.out.println("Error creating monster: " + e.getMessage());
                }
            }
        }

        for (DungeonRoom room: dungeon.getRooms()) {
            System.out.println(room.getPlayerHistory(player1));
        }
        ExtentLoader.saveAllExtents();


//        player1.getArchetypeUsed();
//        // Display Archetypes
//        Map<Class, List<Archetype>> archetypeExtent = Archetype.getArchetypeExtent();
//        System.out.println("Archetypes in extent:");
//        for (List<Archetype> archetypes : archetypeExtent.values()) {
//            for (Archetype archetype : archetypes) {
//                System.out.println(archetype.getName());
//                System.out.println(archetype.getPlayersWithThisArchetype() + "\n");
//                System.out.println(archetype.getStartingWeapon() + " | " + archetype.getStartingArmour() + " | " + archetype.getStartingHealingItem());
//
//            }
//        }
//        System.out.println(player1.getWeapon());
//
//        System.out.println(player1.getItemOwnerships());
//
//
//        // Display Items
//        List<Item> itemExtent = Item.getExtent();
//        System.out.println("Items in extent:");
//        for (Item item : itemExtent) {
//            System.out.println(item.getItemName());
//
//        }
//
//        // Display ItemOwnerships of a player
//        Player player = player1; // Choose the player for which you want to display the ItemOwnerships
//
//        System.out.println("ItemOwnerships of " + player.getName() + ":");
//        Set<ItemOwnership> itemOwnershipSet = player.getItemOwnerships();
//        for (ItemOwnership itemOwnership : itemOwnershipSet) {
//            System.out.println(itemOwnership.getItem().getItemName());
//        }
//        // Display Items
//        List<ItemOwnership> itemOwnershipExtent = ItemOwnership.getItemOwnershipExtent();
//        System.out.println("ItemOwnerships in extent:");
//        for(ItemOwnership itemOwnership: itemOwnershipExtent)
//        {
//            System.out.println(itemOwnership.getOwnershipWay() + " | " + itemOwnership.getPlayer() + " | " + itemOwnership.getItem());
//        }
//
//        Item item1 = startingWeapon;
//        Set<ItemOwnership> itemOwnershipSet2 = item1.getItemOwnerships();
//        for (ItemOwnership itemOwnership : itemOwnershipSet2) {
//            System.out.println(itemOwnership.getPlayer().getName());
//        }
    }
}
