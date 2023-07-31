

/**

 The Item class represents an item in the game.
 It implements the Serializable and HealingItem interfaces.
 Items can have different types such as weapon, armour, or healing item.
 They can also have durability and can be owned by players or treasured by monsters.
 */

package MP1.Item;

import MP1.ExtentLoader.ExtentLoader;
import MP1.LiveBeing.LiveBeing;
import MP1.Monster.Monster;
import MP1.Player.Archetype;
import MP1.Player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
/**

 The Item class represents an item in a game.

 It implements the Serializable and HealingItem interfaces.

 <p>The Item class has attributes for various types of items,
 such as weapon, armor, and healing items. It also has attributes

 related to durability, charges, and ownership.

 <p>An item can have multiple types, which are represented by the
 ItemType enum. The durability type of an item can be either

 "CONSUMABLE" or "PASSIVE".

 <p>The Item class provides methods to get and set the attributes,
 add and remove ownership, and interact with the item's durability.

 It also provides methods for finding items by name or type.

 <p>Items are stored in an item extent, which is a mapping of item
 classes to lists of items. The item extent can be saved and loaded

 using ObjectInputStream and ObjectOutputStream.

 <p>The Item class is designed to be subclassed for specific types
 of items, such as weapons and armor, which can have additional

 attributes and behavior.

 @version 1.0
 */
public class Item implements Serializable, HealingItem {
    private static Map<Class, List<Item>> itemExtent = new HashMap<>();
    private EnumSet<ItemType> itemTypes;
    private Set<ItemOwnership> itemOwnerships = new HashSet<>();
    private Archetype archetypeUsed;
    private Monster treasuringMonster;

    //ITEM CLASS ATTRIBUTES
    private String itemName;
    private String itemDescription;

    //WEAPON CLASS ATTRIBUTES
    private Double damage;

    //ARMOUR CLASS ATTRIBUTES
    private Double defence;

    //HEALINGITEM CLASS ATTRIBUTES
    private Double amountHealed;

    //MULTI-ASPECT INHERITANCE
    private final DurabilityType durabilityType;
    private Double maxCharges;
    private Double currentCharges;
    private Double maxDurability;
    private Double currentDurability;

    public Item(EnumSet<ItemType> itemTypes, String itemName, String itemDescription,
                Double damage, Double defence, Double amountHealed, DurabilityType durabilityType,
                Double maxCharges, Double maxDurability) {
        if(durabilityType != null)
        {
            this.durabilityType = durabilityType;
        }else
        {
            throw new IllegalArgumentException("Item must have one durability type.");
        }
        try{

            setItemTypes(itemTypes);
            setItemName(itemName);
            setItemDescription(itemDescription);

            if(itemTypes.contains(ItemType.WEAPON))
            {
                setDamage(damage);
            }
            if(itemTypes.contains(ItemType.ARMOUR))
            {
                setDefence(defence);
            }
            if(itemTypes.contains(ItemType.HEALINGITEM))
            {
                setAmountHealed(amountHealed);
            }
            if(durabilityType == DurabilityType.CONSUMABLE)
            {
                setMaxCharges(maxCharges);
                setCurrentCharges(maxCharges);
            }
            if(durabilityType == DurabilityType.PASSIVE)
            {
                setMaxDurability(maxDurability);
                setCurrentDurability(maxDurability);
            }
            addToExtent();
        }catch(Exception e){
            removeFromExtent();
        }

    }

    private static List<Item> findItemByName(String itemName) {
        if (itemName == null || itemName.isBlank()) {
            throw new IllegalArgumentException("Invalid item name.");
        }
        List<Item> itemExtent = getExtent();
        return itemExtent.stream()
                .filter(i -> i.getItemName().equals(itemName))
                .collect(Collectors.toList());
    }

    private static List<Item> findItemByType(ItemType itemType) {
        if (itemType == null) {
            throw new IllegalArgumentException("Invalid item type.");
        }
        List<Item> itemExtent = getExtent();
        return itemExtent.stream()
                .filter(i -> i.getItemTypes().contains(itemType))
                .sorted(Comparator.comparing(Item::getItemName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    private void setItemTypes(EnumSet<ItemType> itemTypes)
    {
        //check for nulls
        //check the size of the collection must be bigger than 0
        if(itemTypes == null)
        {
            throw new IllegalArgumentException("Item types cannot be null");
        }
        if(itemTypes.size() <= 0)
        {
            throw new IllegalArgumentException("Item has to have at least one type.");

        }
        this.itemTypes = EnumSet.copyOf(itemTypes);


    }

    public EnumSet<ItemType> getItemTypes() {
        return itemTypes;
    }

    public Set<ItemOwnership> getItemOwnerships(){
        return itemOwnerships;
    }

    public void addOwnership(ItemOwnership i){
        if(i == null)
        {
            throw new IllegalArgumentException("Item ownership cannot be null for adding ownership");
        }
        if(i.getItem() != this)
        {
            throw new IllegalArgumentException("Item is not part of the ownership");
        }
        this.itemOwnerships.add(i);
    }

    public void removeOwnership(ItemOwnership i) {
        if (!this.itemOwnerships.contains(i)) {
           return;
        }

        this.itemOwnerships.remove(i);
        i.remove();

        Player owner = i.getPlayer();
        if (owner != null) {
            owner.removeOwnership(i);
        }
    }

    public Monster getTreasuringMonster() {
        return treasuringMonster;
    }

    public void setTreasuringMonster(Monster treasuringMonster) {
        if (this.treasuringMonster != treasuringMonster) {
            Monster previousMonster = this.treasuringMonster;
            this.treasuringMonster = treasuringMonster;

            if (previousMonster != null) {
                previousMonster.removeTreasure(this);
            }

            if (treasuringMonster != null) {
                treasuringMonster.addTreasure(this);
            }
        }


    }

    public Double getDamage() {
        return damage;
    }

    public void setDamage(Double damage) {
    if(!itemTypes.contains(ItemType.WEAPON))
    {
        throw new IllegalArgumentException("Cannot set damage, item is not weapon.");
    }
    if(damage < 0)
    {
        throw new IllegalArgumentException("damage cannot be negative");
    }
        this.damage = damage;


    }

    public Double getDefence() {
        return defence;
    }

    public void setDefence(Double defence) {
        if(!itemTypes.contains(ItemType.ARMOUR))
        {
            throw new IllegalArgumentException("Cannot set defence, item is not an armour.");
        }
        if(defence < 0)
        {
            throw new IllegalArgumentException("defence cannot be negative");
        }
        this.defence = defence;


    }

    public Double getAmountHealed() {
        return amountHealed;
    }

    public void setAmountHealed(Double amountHealed) {
        if(!itemTypes.contains(ItemType.HEALINGITEM))
        {
            throw new IllegalArgumentException("Cannot set amount healed, item is not a healing item.");
        }
        if(amountHealed < 0)
        {
            throw new IllegalArgumentException("amountHealed cannot be negative");
        }
        this.amountHealed = amountHealed;


    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        if(itemName == null || itemName.isBlank())
        {
            throw new IllegalArgumentException("itemName cannot be empty or null");
        }
        this.itemName = itemName;


    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        if(itemDescription == null || itemDescription.isBlank())
        {
            throw new IllegalArgumentException("itemDescription cannot be empty or null");
        }
        this.itemDescription = itemDescription;


    }

    public DurabilityType getDurabilityType() {
        return durabilityType;
    }

    public Double getMaxCharges() {
        return maxCharges;
    }

    public void setMaxCharges(double maxCharges) {
        if(durabilityType != DurabilityType.CONSUMABLE)
        {
            throw new IllegalArgumentException("Cannot set maxCharges. Item not a consumable.");
        }
        if(maxCharges < 0)
        {
            throw new IllegalArgumentException("Max charges cannot be negative");
        }
        this.maxCharges = maxCharges;


    }

    public double getCurrentCharges() {
        return currentCharges;
    }

    public void setCurrentCharges(Double currentCharges) {
        if(durabilityType != DurabilityType.CONSUMABLE)
        {
            throw new IllegalArgumentException("Cannot set currentCharges. Item not a consumable.");
        }
        if(currentCharges < 0)
        {
            throw new IllegalArgumentException("Current charges cannot be negative");
        }
        if(currentCharges > maxCharges)
        {
            throw new IllegalArgumentException("Current charges cannot be more than maximum");

        }
        this.currentCharges = currentCharges;
    }

    public Double getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(Double maxDurability) {
        if(durabilityType != DurabilityType.PASSIVE)
        {
            throw new IllegalArgumentException("Cannot set maxDUrability. Item not a passive.");
        }
        if(maxDurability < 0)
        {
            throw new IllegalArgumentException("Max durability cannot be negative");
        }
        this.maxDurability = maxDurability;


    }

    public double getCurrentDurability() {
        return currentDurability;
    }

    public void setCurrentDurability(double currentDurability) {
        if(durabilityType != DurabilityType.PASSIVE)
        {
            throw new IllegalArgumentException("Cannot set currentDurability. Item not a passive.");
        }
        if(currentDurability < 0)
        {
            throw new IllegalArgumentException("Max charges cannot be negative");
        }
        if(currentDurability > maxDurability)
        {
            throw new IllegalArgumentException("Current durability cannot be bigger than maximum");

        }
        this.currentDurability = currentDurability;
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void setArchetypeUsed(Archetype archetypeUsed) {
        if (this.archetypeUsed == archetypeUsed) {
            return;
        }
        if (archetypeUsed != null) {
            archetypeUsed.setStartingWeapon(this);
            archetypeUsed.setStartingArmour(this);
            archetypeUsed.setStartingHealingItem(this);
        } else {
            if (this.archetypeUsed != null) {
                this.archetypeUsed.setStartingWeapon(null);
                this.archetypeUsed.setStartingArmour(null);
                this.archetypeUsed.setStartingHealingItem(null);
            }
        }
        this.archetypeUsed = archetypeUsed;
    }

    public Archetype getArchetypeUsed() {
        return archetypeUsed;
    }

    public static List<Item> getExtent() {
        List<Item> list = itemExtent.get(Item.class);
        if (list == null) {
            return new ArrayList<>();
        }
        return Collections.unmodifiableList(list);
    }

    private void addToExtent() {
        Class<? extends Item> itemClass = getClass();
        List<Item> ext = itemExtent.computeIfAbsent(itemClass, k -> new ArrayList<>());
        ext.add(this);


    }

//    private static void setItemExtent(List<Item> itemExtent) {
//        Item.itemExtent = ;
//    }

    protected void removeFromExtent() {
        List<Item> ext = itemExtent.get(this.getClass());


        for (ItemOwnership ownership : new HashSet<>(itemOwnerships)) {
            Item ownedItem = ownership.getItem();
            Player owner = ownership.getPlayer();

            if (owner != null) {
                owner.removeOwnership(ownership);
            }

            ownedItem.removeOwnership(ownership);
        }

        if (ext != null) {
            ext.remove(this);
        }

        if (archetypeUsed != null) {
            if (archetypeUsed.getStartingWeapon() == this) {
                archetypeUsed.setStartingWeapon(null);
            }
            if (archetypeUsed.getStartingArmour() == this) {
                archetypeUsed.setStartingArmour(null);
            }
            if (archetypeUsed.getStartingHealingItem() == this) {
                archetypeUsed.setStartingHealingItem(null);
            }
        }
    }

    public static void saveExtent(ObjectOutputStream fileName) {
        try (ObjectOutputStream outputStream = fileName) {
            outputStream.writeObject(itemExtent);
            System.out.println("Item extent saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadExtent(ObjectInputStream fileName) {
        try (ObjectInputStream inputStream = fileName) {
            itemExtent = (Map<Class, List<Item>>) inputStream.readObject();
            System.out.println("Item extent loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return this.getItemName() ;
//                + " | "
//
//                + "Description: " + this.getItemDescription() + " | "
//                + "Item Types: " + this.getItemTypes();
    }

    @Override
    public void heal(LiveBeing liveBeing) {
        if (!this.itemTypes.contains(ItemType.HEALINGITEM)) {
            System.out.println("This item is not a healing item, cannot heal.");
            return;
        }
        liveBeing.setCurrentHealth(liveBeing.getCurrentHealth() + getAmountHealed());
        //HEAL THE PLAYER OR STH
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }


    public void decreaseCharges() {
        if (durabilityType != DurabilityType.CONSUMABLE) {
            throw new IllegalStateException("Cannot decrease charges. Item is not a consumable.");
        }

        if (currentCharges <= 0) {
            throw new IllegalStateException("Item has no charges remaining.");
        }

        currentCharges--;

        if (currentCharges == 0) {
            System.out.println("The " + itemName + " has been fully consumed.");
            removeFromExtent();
        }
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void remove(){
        removeFromExtent();
    }
    public void decreaseDurability() {
        if (durabilityType != DurabilityType.PASSIVE) {
            throw new IllegalStateException("Cannot decrease durability. Item is not a passive item.");
        }



        currentDurability-= 5;

        if (currentDurability <= 0 ) {
            System.out.println("The " + itemName + " has been fully depleted.");
            removeFromExtent();
        }
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
