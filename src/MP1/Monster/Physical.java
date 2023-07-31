package MP1.Monster;

import MP1.Dungeon.DungeonRoom;
import MP1.ExtentLoader.ExtentLoader;
import MP1.Item.Item;
import MP1.LiveBeing.LiveBeing;
import MP1.Player.Player;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;


/**

 The "Physical" class represents a type of monster that inflicts physical damage in the game.
 It extends the "Monster" class and implements the "Serializable" interface for object serialization.
 The Physical monster has the following attributes:
 physicalDamage: the amount of physical damage the monster can inflict.
 The Physical monster can be instantiated with a specified name, maximum health, current health,
 minimum damage, maximum damage modifier, set of treasure items, and physical damage.
 The Physical monster can also be instantiated with a specified maximum health, current health,
 name, old monster type, and physical damage.
 The Physical monster has the following behaviors:
 Healing: It can heal a given live being or itself by a specified amount.
 Casting: It can cast a healing spell on a monster target, healing them or itself.
 Attacking: It can attack a target, inflicting physical damage.
 The Physical monster overrides the "attack" method from the Monster class to implement the
 specific behavior for physical damage attack.
 The Physical monster also overrides the "cast" method from the Monster class to implement
 the specific behavior for casting healing spells.
 This class provides getter and setter methods for the physical damage attribute.
 Note: This class depends on the "Monster" class, "LiveBeing" class, "Player" class, "DungeonRoom" class,
 "Item" class, and the "ExtentLoader" class for functionality.
 @see Monster
 @see LiveBeing
 @see Player
 @see DungeonRoom
 @see Item
 @see ExtentLoader
 @see Serializable
 */

public class Physical extends Monster implements Serializable {

    private double physicalDamage;

    public Physical(String name, double maxHealth, double currentHealth, double minDamage, double maxDamageModifier, Set<Item> treasure, double physicalDamage) throws Exception {
        super(name, maxHealth, currentHealth, minDamage, maxDamageModifier, treasure);
        setPhysicalDamage(physicalDamage);
    }

    public Physical(double maxHealth, double currentHealth, String name, Monster oldType, double physicalDamage) {
        super(maxHealth, currentHealth, name, oldType);
        this.physicalDamage = physicalDamage;
    }

    public void heal(LiveBeing liveBeing, double healAmount){
        if(liveBeing.getCurrentHealth() > 0 && liveBeing != null)
        {
            liveBeing.setCurrentHealth(liveBeing.getCurrentHealth() + healAmount);
        }
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void healSelf(double healAmount){
        heal(this, healAmount);
    }


    public double getPhysicalDamage() {
        return physicalDamage;
    }

    public void setPhysicalDamage(double physicalDamage) {
        if (physicalDamage < 0) {
            throw new IllegalArgumentException("Physical damage cannot be negative.");
        }
        this.physicalDamage = physicalDamage;


    }

    @Override
    public void cast(LiveBeing castTarget) {
        if(castTarget.getClass() != Monster.class || castTarget == null)
        {
            throw new IllegalArgumentException("This target is not a monster or monster is null");
        }
        if(castTarget == this)
        {
            healSelf(2.0);
        }else{
            heal(castTarget, 2.0);
        }

        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    @Override
    public Double attack(LiveBeing target) {
        if (target == null) {
            throw new IllegalArgumentException("Target cannot be null.");
        }
        if (this.getCurrentHealth() <= 0) {
            return 0.0;
        }

        // Generate a random damage value between minDamage and currentMaxDamage
        double randomDamage = getMinDamage() + Math.random() * (getCurrentMaxDamage() - getMinDamage());

        // Apply the magical damage modifier
        double modifiedDamage = randomDamage + getPhysicalDamage();

        if (target.getClass() == Player.class)
        {
            modifiedDamage = modifiedDamage - ((Player) target).getArmour().getDefence();
            ((Player) target).getArmour().decreaseDurability();
        }
        // Deal the damage to the target
        double currentHealth = target.getCurrentHealth();
        double newHealth = currentHealth - modifiedDamage;

        target.setCurrentHealth(newHealth);
        System.out.println("Monster attacks the player for " + modifiedDamage);
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return modifiedDamage;
    }
}
