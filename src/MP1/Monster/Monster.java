package MP1.Monster;

import MP1.Dungeon.DungeonRoom;
import MP1.ExtentLoader.ExtentLoader;
import MP1.Item.Item;
import MP1.Item.ItemOwnership;
import MP1.Item.OwnershipWay;
import MP1.LiveBeing.LiveBeing;
import MP1.Player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Monster extends LiveBeing implements Serializable {

    private static final String EXTENT_DATA = "extent.dat";

    private static List<Monster> monsterExtent = new ArrayList<>();

    private double minDamage;

    //class attribute - all monsters have a maxDamage set, its a class attribute
    private static double maxDamage = 10.0;

    //optional attribute, so it is in a different constructor
    private double maxDamageModifier;

    // Multi-value association: 1..3 treasures
    private Set<Item> treasure = new HashSet<>();

    private DungeonRoom inhabitedRoom;


//    public Monster(String name, double maxHealth, double currentHealth, double minDamage, Set<Item> treasure, double maxDamageModifier) throws Exception {
//        super(maxHealth, currentHealth, name);
//        try{
//            if(monsterExtent.size() > 0 && monsterExtent != null)
//            {
//                setId(monsterExtent.stream().max(Comparator.comparing(m -> m.getId())).get().getId() + 1);
//            }else if(monsterExtent.size() == 0)
//            {
//                setId(0);
//            }
//            setName(name);
//            if(minDamage < 0)
//            {
//                throw new Exception("Error whilst creating Monster, negative minDamage.");
//            }else {
//                setMinDamage(minDamage);
//            }
//            if(treasure.size() > 0 && treasure != null && treasure.size() <= 3)
//            {
//                setTreasure(treasure);
//            } else{
//                throw new Exception("Error whilst creating Monster, no treasure? Or treasure too big?");
//            }
//            monsterExtent.add(this);
//        }catch( Exception e){
//            removeFromExtent();
//        }
//        setMaxDamageModifier(maxDamageModifier);
//    }

    public Monster(String name, double maxHealth, double currentHealth, double minDamage, double maxDamageModifier, Set<Item> treasure) throws Exception {
        super(maxHealth, currentHealth, name);

        System.out.println("Creating a monster");
        try {

            setName(name);
            if(minDamage < 0)
            {
                throw new Exception("Error whilst creating Monster, negative minDamage.");
            }else {
                setMinDamage(minDamage);
            }


            if(maxDamageModifier == 0.0)
            {
                setMaxDamageModifier(maxDamage);
            } else {
                setMaxDamageModifier(maxDamageModifier);
            }
            if(treasure.size() > 0 && treasure != null && treasure.size() <= 3)
            {
                setTreasure(treasure);
            } else{
                throw new Exception("Error whilst creating Monster, no treasure? Treasure too big?");
            }
            monsterExtent.add(this);
        }catch( Exception e){
            removeFromExtent();
        }



    }

    //DYMAMIC INHERITANCE

    public Monster(double maxHealth, double currentHealth, String name, Monster oldType) {
        super(maxHealth, currentHealth, name);
        try{

            setMaxDamageModifier(oldType.getMaxDamageModifier());
            setMinDamage(oldType.getMinDamage());
            setTreasure(oldType.getTreasure());
            monsterExtent.add(this);
            oldType.removeFromExtent();
        }catch (Exception e)
        {
            removeFromExtent();
            /**
             * ASK ABOUT THIS
             */
            oldType.getExtent().add(this);
        }


    }



    public double getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(double minDamage) {
        if(minDamage < 0)
        {
            throw new IllegalArgumentException("minDamage cannot be negative");
        }
        this.minDamage = minDamage;
    }

    public static double getMaxDamage() {
        return maxDamage;
    }


    public static void setMaxDamage(double maxDamage) {
        if(maxDamage < 0)
        {
            throw new IllegalArgumentException("maxDamage cannot be negative");
        }
        Monster.maxDamage = maxDamage;
    }

    public double getMaxDamageModifier() {
        return maxDamageModifier;
    }

    public double getCurrentMaxDamage(){
        if(maxDamage == 0.0 && maxDamageModifier == 0.0)
        {
            return 0.0;
        }
        Double maxDamageFinal = maxDamage - maxDamageModifier;
        if(maxDamageFinal <= 0 )
        {
            return 0.0;
        }
        else return maxDamageFinal;
    }

    public void setMaxDamageModifier(double maxDamageModifier) {
        if(maxDamageModifier < 0) {
            throw new IllegalArgumentException("maxDamageModifer cannot be negative");
        }else {
            this.maxDamageModifier = maxDamageModifier;
        }
    }

    public Set<Item> getTreasure() {
        return Collections.unmodifiableSet(treasure);
    }

    public void setTreasure(Set<Item> treasure) {
        // existing code
        for (Item i : treasure) {
            if (i == null) {
                throw new IllegalArgumentException("One of the items in the treasure is null");
            }
            if (i.getTreasuringMonster() != null) {
                throw new IllegalArgumentException("One of the items is already treasured by another monster");
            }
            System.out.println("This item added to monster treasure: " + i);
            i.setTreasuringMonster(this);
        }
        this.treasure = treasure;
    }

    public void addTreasure(Item item)
    {
        if(this.treasure.size() >= 3 || item == null || treasure.contains(item))
        {
            throw new IllegalArgumentException("Treasure full or already inside the treasure.");
        }
        item.setTreasuringMonster(this);
        treasure.add(item);
    }

    public void removeTreasure(Item item)
    {
        if(this.treasure.size() == 1)
        {
            throw new IllegalArgumentException("Cannot remove treasure, there must be at least one item in treasure.");
        }
        this.treasure.remove(item);
        item.setTreasuringMonster(null);

    }


    public static List<Monster> getMonsterExtent() {
        //GET EXTENT SORTED BY NAME
       return Collections.unmodifiableList(monsterExtent);
    }

    private static void setMonsterExtent(List<Monster> monsterExtent) {
        Monster.monsterExtent = monsterExtent;
    }

    public static void saveExtent(ObjectOutputStream stream) throws IOException {
        stream.writeObject(monsterExtent);
    }

    public static void loadExtent(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        monsterExtent = (List<Monster>) stream.readObject();
    }

    public static List<Monster> findMonsterByName(String mName){
        if(mName == null || mName.isBlank())
        {
            throw new IllegalArgumentException("String error in finding monster by name.");
        }
        List<Monster> monsterExtent = getMonsterExtent();
        return monsterExtent.stream().filter(monster -> monster.getName().equalsIgnoreCase(mName)).collect(Collectors.toList());
    }

    public DungeonRoom getDungeonRoom(){
        return inhabitedRoom;
    }


    public void setInhabitedRoom(DungeonRoom dungeonRoom) throws Exception {
        if(this.inhabitedRoom == dungeonRoom){
            return;
        }
        if(dungeonRoom == null && this.inhabitedRoom == null)
        {
            throw new IllegalArgumentException("Dungeon room is null. Cannot set it for monster");
        }else if(this.inhabitedRoom == null && dungeonRoom != null)
        {
            this.inhabitedRoom = dungeonRoom;
            dungeonRoom.addMonsterToRoom(this);
        }else if(this.inhabitedRoom != null && dungeonRoom == null)
        {
            DungeonRoom prevDungeonRoom = this.inhabitedRoom;
            this.inhabitedRoom = null;
            prevDungeonRoom.removeMonsterFromRoom(this);
        }else if(this.inhabitedRoom != null && dungeonRoom !=null)
        {
            this.inhabitedRoom = dungeonRoom;
            dungeonRoom.addMonsterToRoom(this);
        }else {
            throw new Exception("Error whilst setting the room for Monster");
        }

    }

    public void DropLoot(Player player){
        if(this.treasure.isEmpty())
        {
            throw new IllegalArgumentException("Cannot drop loot, no loot in the monster"   );
        }
        if(this.getCurrentHealth() > 0)
        {
            throw new IllegalArgumentException("Cannot drop loot, monster is still alive"   );
        }

        for (Item i : this.treasure) {
            ItemOwnership newOwnership = new ItemOwnership(i, player, OwnershipWay.LOOTED);
        }

    }
    @Override
    public void setName(String name) {
        super.setName(name);
        for (Monster m : monsterExtent) {
           if( m.getName().equalsIgnoreCase(name))
           {
               throw new IllegalArgumentException("Name of the monster must be unique.");
           }
        }
        if(inhabitedRoom != null)
        {
            //automatically updates if changed
            inhabitedRoom.getMonstersInRoom().remove(name);
            inhabitedRoom.getMonstersInRoom().put(name, this);
        }

    }

    @Override
    public String toString() {
        return "Monster |" +
                " name= " + getName() + "| treasures= " + treasure.size() + " |";
    }

    @Override
    public abstract Double attack(LiveBeing target);


    @Override
    protected void removeFromExtent() {
        super.removeFromExtent();
        //remove additional things like association
        //removing association
        try {
            this.setInhabitedRoom(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public abstract void cast(LiveBeing castTarget);

}
