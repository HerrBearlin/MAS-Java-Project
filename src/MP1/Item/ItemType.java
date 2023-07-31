package MP1.Item;

import java.io.Serializable;

/**
 * The ItemType enum represents the type of an item.
 * It can be one of the following:
 * - WEAPON: The item is a weapon.
 * - ARMOUR: The item is an armor.
 * - HEALINGITEM: The item is a healing item.
 */
public enum ItemType implements Serializable {
    WEAPON,
    ARMOUR,
    HEALINGITEM
}