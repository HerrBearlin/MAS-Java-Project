
/**

 The Vampiric class represents a Vampiric item, which is a type of item that can heal the user when damaging.
 It extends the Item class and implements the HealingItem interface.
 It provides methods to set the damage modifier and healing modifier of the Vampiric item,
 as well as perform healing when damaging and healing a live being.
 */
package MP1.Item;

import MP1.ExtentLoader.ExtentLoader;
import MP1.LiveBeing.LiveBeing;

import java.io.IOException;
import java.io.Serializable;
import java.util.EnumSet;

public class Vampiric extends Item implements HealingItem, Serializable {

    private double damageModifier;
    private double healingModifier;

    /**
     * Constructs a Vampiric item with the specified parameters.
     *
     * @param itemTypes       the set of item types
     * @param itemName        the name of the item
     * @param itemDescription the description of the item
     * @param damage          the damage value of the item
     * @param defence         the defence value of the item
     * @param amountHealed    the amount healed by the item
     * @param durabilityType  the durability type of the item
     * @param maxCharges      the maximum charges of the item
     * @param maxDurability   the maximum durability of the item
     * @param damageModifier  the damage modifier of the Vampiric item
     * @param healingModifier the healing modifier of the Vampiric item
     */
    public Vampiric(EnumSet<ItemType> itemTypes, String itemName, String itemDescription,
                    Double damage, Double defence, Double amountHealed, DurabilityType durabilityType, Double maxCharges, Double maxDurability,
                    double damageModifier, double healingModifier) {
        super(itemTypes, itemName, itemDescription, damage, defence, amountHealed, durabilityType, maxCharges, maxDurability);
        try {
            setDamageModifier(damageModifier);
            setHealingModifier(healingModifier);
            if (!itemTypes.contains(ItemType.HEALINGITEM) && !itemTypes.contains(ItemType.WEAPON)) {
                throw new IllegalArgumentException("This item cannot be created as Vampiric. It does not have HealingItem and Weapon types.");
            }
        } catch (Exception e) {
            removeFromExtent();
        }
    }

    /**
     * Sets the damage modifier of the Vampiric item.
     *
     * @param damageModifier the damage modifier to be set
     * @throws IllegalArgumentException if the damage modifier is negative
     */
    public void setDamageModifier(double damageModifier) {
        if (damageModifier < 0) {
            throw new IllegalArgumentException("Damage modifier cannot be negative.");
        }
        this.damageModifier = damageModifier;
    }

    /**
     * Sets the healing modifier of the Vampiric item.
     *
     * @param healingModifier the healing modifier to be set
     * @throws IllegalArgumentException if the healing modifier is negative
     */
    public void setHealingModifier(double healingModifier) {
        if (healingModifier < 0) {
            throw new IllegalArgumentException("Healing modifier cannot be negative.");
        }
        this.healingModifier = healingModifier;
    }

    /**
     * Performs healing when damaging with the Vampiric item.
     * Calls the heal() method and saves the extent after healing.
     */
    public void healWhenDamaging() {
        heal();
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void heal(LiveBeing liveBeing) {
        if (!this.getItemTypes().contains(ItemType.HEALINGITEM) && this.getItemTypes().contains(ItemType.WEAPON)) {
            throw new IllegalArgumentException("This item is not a Vampiric Item.");
        }
        liveBeing.setCurrentHealth(liveBeing.getCurrentHealth() + getAmountHealed() - this.healingModifier);
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
