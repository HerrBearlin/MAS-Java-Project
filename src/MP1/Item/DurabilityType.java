package MP1.Item;

import java.io.Serializable;

/**
 * The DurabilityType enum represents the type of durability for an item.
 * It can be one of the following:
 * - CONSUMABLE: The item is consumable and its durability decreases with use.
 * - PASSIVE: The item is passive and does not have durability.
 */
public enum DurabilityType implements Serializable {
    CONSUMABLE,
    PASSIVE
}
