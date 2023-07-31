package MP1.GUI.Inventory;

import MP1.Item.HealingItem;
import MP1.Item.Item;
import MP1.Item.ItemType;

import javax.swing.*;

public class ItemDetailsPopup extends JFrame {

    public ItemDetailsPopup(Item item) {
        initComponents(item);
        setLocationRelativeTo(null); // Center the window on the screen
    }

    private void initComponents(Item item) {

        // Create and configure the components for the popup window
        JLabel nameLabel = new JLabel("Name: " + item.getItemName());
        JLabel descriptionLabel = new JLabel("Description: " + item.getItemDescription());
        JLabel itemTypeLabel = new JLabel("Item Type(s): " + item.getItemTypes().toString());

        // Create a panel and add the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(nameLabel);
        panel.add(descriptionLabel);
        panel.add(itemTypeLabel);

        // Add the panel to the popup window
        getContentPane().add(panel);

        // Configure the popup window
        setTitle("Item Details");
        setSize(300, 200);
        // Check the item's class to display the appropriate attributes
        if (item.getItemTypes().contains(ItemType.WEAPON)) {
            Item weapon =  item;
            JLabel damageLabel = new JLabel("Damage: " + weapon.getDamage());
            // Add the damage label to the popup window
            panel.add(damageLabel);
        } else if (item.getItemTypes().contains(ItemType.ARMOUR)) {
            Item armour =  item;
            JLabel defenceLabel = new JLabel("Defence: " + armour.getDefence());
            // Add the defence label to the popup window
            panel.add(defenceLabel);
        } else if (item.getItemTypes().contains(ItemType.HEALINGITEM)) {
            Item healingItem =  item;
            JLabel amountHealedLabel = new JLabel("Amount Healed: " + healingItem.getAmountHealed());
            // Add the amount healed label to the popup window
            panel.add(amountHealedLabel);
        }


    }
}