/**

 The LiveBeing class is an abstract class representing a live being in the game.
 It provides common attributes and behaviors for live beings.
 Subclasses of LiveBeing should implement the attack() method.
 */

package MP1.LiveBeing;

import MP1.ExtentLoader.ExtentLoader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public abstract class LiveBeing implements Serializable {

    private static Map<Class, List<LiveBeing>> beingExtent = new HashMap<Class, List<LiveBeing>>();

    private double maxHealth;
    private double currentHealth;
    private String name;

    /**
     * Constructs a LiveBeing with the specified maximum health, current health, and name.
     *
     * @param maxHealth     the maximum health of the LiveBeing
     * @param currentHealth the current health of the LiveBeing
     * @param name          the name of the LiveBeing
     */
    public LiveBeing(double maxHealth, double currentHealth, String name) {
        setMaxHealth(maxHealth);
        if (currentHealth < 0) {
            throw new IllegalArgumentException("Current health cannot be negative in LiveBeing constructor");
        }
        setCurrentHealth(currentHealth);
        setName(name);
        List ext = beingExtent.get(this.getClass());
        if (ext == null) {
            ext = new ArrayList();
            beingExtent.put(this.getClass(), ext);
        }
        ext.add(this);
    }

    /**
     * Performs an attack on the target LiveBeing and returns the damage dealt.
     * Subclasses should implement this method.
     *
     * @param target the LiveBeing to attack
     * @return the damage dealt
     */
    public abstract Double attack(LiveBeing target);

    /**
     * Returns the maximum health of the LiveBeing.
     *
     * @return the maximum health
     */
    public double getMaxHealth() {
        return maxHealth;
    }

    /**
     * Sets the maximum health of the LiveBeing.
     *
     * @param maxHealth the maximum health to set
     * @throws IllegalArgumentException if the maximum health is smaller than 1
     */
    public void setMaxHealth(double maxHealth) {
        if (maxHealth < 1) {
            throw new IllegalArgumentException("Max health cannot be smaller than 1");
        }
        this.maxHealth = maxHealth;
    }

    /**
     * Returns the current health of the LiveBeing.
     *
     * @return the current health
     */
    public double getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Sets the current health of the LiveBeing.
     * If the current health is negative, it is set to 0.
     * If the current health is greater than the maximum health, it is set to the maximum health.
     *
     * @param currentHealth the current health to set
     */
    public void setCurrentHealth(double currentHealth) {
        if (currentHealth < 0) {
            currentHealth = 0.0;
        }
        if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
        this.currentHealth = currentHealth;
    }

    /**
     * Returns the name of the LiveBeing.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the LiveBeing.
     *
     * @param name the name to set
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.name = name;
    }

    /**
     * Returns the extent of LiveBeing objects.
     *
     * @return the extent
     */
    public static List<LiveBeing> getExtent() {
        List<LiveBeing> list = beingExtent.get(LiveBeing.class);
        if (list == null) {
            return new ArrayList<>();
        }
        return Collections.unmodifiableList(list);
    }

    /**
     * Removes the LiveBeing object from the extent.
     */
    protected void removeFromExtent() {
        beingExtent.get(this.getClass()).remove(this);
    }

    /**
     * Adds the LiveBeing object to the extent.
     */
    private void addToExtent() {
        List<LiveBeing> ext = beingExtent.get(this.getClass());
        if (ext == null) {
            ext = new ArrayList<>();
            beingExtent.put(this.getClass(), ext);
        }
        ext.add(this);
    }

    /**
     * Saves the extent of LiveBeing objects to a file.
     *
     * @param fileName the name of the file to save
     * @throws IOException if an I/O error occurs
     */
    public static void saveExtent(ObjectOutputStream fileName) throws IOException {
        try (ObjectOutputStream outputStream = fileName) {
            outputStream.writeObject(beingExtent);
            System.out.println("Player extent saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the extent of LiveBeing objects from a file.
     *
     * @param fileName the name of the file to load
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    @SuppressWarnings("unchecked")
    public static void loadExtent(ObjectInputStream fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = fileName) {
            beingExtent = (Map<Class, List<LiveBeing>>) inputStream.readObject();
            System.out.println("Player extent loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
