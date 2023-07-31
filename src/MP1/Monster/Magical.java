package MP1.Monster;

import MP1.Dungeon.DungeonRoom;
import MP1.ExtentLoader.ExtentLoader;
import MP1.Item.Item;
import MP1.LiveBeing.LiveBeing;
import MP1.Player.Player;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

public class Magical extends Monster implements Serializable {

    private double magicalDamage;

    public Magical(String name, double maxHealth, double currentHealth, double minDamage, Set<Item> treasure, double maxDamageModifier,  double magicalDamage) throws Exception {
        super(name, maxHealth, currentHealth, minDamage,maxDamageModifier, treasure);
        setMagicalDamage(magicalDamage);
    }

    public Magical( String name, double maxHealth, double currentHealth, double minDamage, double maxDamageModifier, Set<Item> treasure, DungeonRoom inhabitedRoom, double magicalDamage) throws Exception {
        super(name, maxHealth, currentHealth, minDamage, maxDamageModifier, treasure);
        setMagicalDamage(magicalDamage);
    }

    public Magical(double maxHealth, double currentHealth, String name, Monster oldType, double magicalDamage) {
        super(maxHealth, currentHealth, name, oldType);
        setMagicalDamage(magicalDamage);
    }

    public double getMagicalDamage() {
        return magicalDamage;
    }

    public void setMagicalDamage(double magicalDamage) {
        if (magicalDamage < 0 || magicalDamage > getCurrentMaxDamage()) {
            throw new IllegalArgumentException("Magical damage cannot be negative or greater than currentMaxDamage.");
        }
        this.magicalDamage = magicalDamage;


    }

    public void debuff(Player player, Double dmgModifier){
        if(player == null)
        {
            throw new IllegalArgumentException("Player cannot be null to debuff it");
        }
        Double playerWeaponDamage = player.getWeapon().getDamage();
        Double finalModifiedDamage = playerWeaponDamage - dmgModifier;
        player.getWeapon().setDamage(finalModifiedDamage);

    }

    @Override
    public void cast(LiveBeing castTarget) {
        if (castTarget == null || !(castTarget instanceof Player)) {
            throw new IllegalArgumentException("The target is not a valid Player or is null.");
        }
        debuff((Player) castTarget, 2.0);
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

        // Generate a random damage value between minDamage and currentMaxDamage
        double randomDamage = getMinDamage() + Math.random() * (getCurrentMaxDamage() - getMinDamage());

        // Apply the magical damage modifier
        double modifiedDamage = randomDamage + getMagicalDamage();

        // Deal the damage to the target
        double currentHealth = target.getCurrentHealth();
        double newHealth = currentHealth - modifiedDamage;

        target.setCurrentHealth(newHealth);
        try {
            ExtentLoader.saveAllExtents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modifiedDamage;
    }
}
