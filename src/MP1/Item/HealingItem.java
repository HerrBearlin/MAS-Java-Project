package MP1.Item;

import MP1.LiveBeing.LiveBeing;

import java.io.Serializable;

public interface HealingItem extends Serializable {
    default void heal() {}

    void heal(LiveBeing liveBeing);
}
