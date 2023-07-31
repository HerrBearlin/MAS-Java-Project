package MP1.GUI.Inventory;

import MP1.ExtentLoader.ExtentLoader;
import MP1.GUI.RoomEncounter.RoomEncounter;
import MP1.Item.DurabilityType;
import MP1.Item.Item;
import MP1.Item.ItemOwnership;
import MP1.Item.ItemType;
import MP1.Player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;


public class InventoryViewInCombat extends JFrame {
    private JPanel inventoryPanel;
    private JButton backButton;

    public InventoryViewInCombat(Player player, RoomEncounter roomEncounter) {
        initComponents( player);
        displayPlayerInventory();
        setLocationRelativeTo(null); // Center the window on the screen

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // Update the room data in the RoomEncounter window
                roomEncounter.updateRoomData();
            }
        });
    }

    private void initComponents(Player player) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Inventory");
        setResizable(true);

        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(inventoryPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the parent component of the InventoryView frame
                Component parentComponent = InventoryViewInCombat.this.getParent();

                // Make the parent component visible (assumes it is a JFrame)
                if (parentComponent instanceof JFrame) {
                    JFrame parentFrame = (JFrame) parentComponent;
                    parentFrame.setVisible(true);
                }

                // Close the InventoryView frame
                dispose();
            }
        });

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(backButton, BorderLayout.SOUTH);
    }

    private void displayPlayerInventory() {
        // Get the current player instance
        Player player = Player.getPlayerExtent().get(Player.class).get(0);

        // Get the item ownership extent of the player
        List<ItemOwnership> itemOwnershipList = player.getItemOwnerships().stream().toList();

        // Iterate over each item ownership
        for (ItemOwnership itemOwnership : itemOwnershipList) {
            Item item = itemOwnership.getItem();

            // Create a panel for each item
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            // Create a label to display the item name
            JLabel nameLabel = new JLabel(item.getItemName());
            itemPanel.add(nameLabel);

            // Create buttons for each item
            JButton equipButton = new JButton("Equip");
            JButton useButton = new JButton("Use");
            JButton discardButton = new JButton("Discard");
            JButton detailsButton = new JButton("Details");

            // Add action listeners to the buttons
            equipButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Perform the equip action
                    if(item.getItemTypes().contains(ItemType.WEAPON)) {
                        player.setWeapon(item);
                        JOptionPane.showMessageDialog(InventoryViewInCombat.this, "Equipped the weapon: " + item.getItemName());
                    }else if (item.getItemTypes().contains(ItemType.ARMOUR)) {
                        player.setArmour(item);
                        JOptionPane.showMessageDialog(InventoryViewInCombat.this, "Equipped the armour: " + item.getItemName());
                    }
                    try {
                        ExtentLoader.saveAllExtents();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            useButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(item.getItemTypes().contains(ItemType.HEALINGITEM)) {
                       item.heal(player);
                        JOptionPane.showMessageDialog(InventoryViewInCombat.this, "Used the healing item: " + item.getItemName()
                                + "\nPlayer healed for " + item.getAmountHealed() + " points."
                                + "\nPlayer's current health: " + player.getCurrentHealth() + "/" + player.getMaxHealth());
                        // Decrease the item's charges/durability
                        if(item.getDurabilityType().equals(DurabilityType.CONSUMABLE))
                        {
                            item.decreaseCharges();
                        }else {
                            item.decreaseDurability();
                        }
                    }else{
                        JOptionPane.showMessageDialog(InventoryViewInCombat.this, "Cannot use Item:  " + item.getItemName()
                                + "It's not a healing item. " );
                    }





                }
            });

            discardButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Perform the discard action
                    item.remove();
                    inventoryPanel.remove(itemPanel);

                    // Refresh the inventory panel to reflect the changes
                    inventoryPanel.revalidate();
                    inventoryPanel.repaint();

                    JOptionPane.showMessageDialog(InventoryViewInCombat.this, "Discarded the item: " + item.getItemName());

                    try {
                        ExtentLoader.saveAllExtents();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            detailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ItemDetailsPopup itemDetailsPopup = new ItemDetailsPopup(item);
                    itemDetailsPopup.setVisible(true);
                }
            });

            // Add the buttons to the item panel
            itemPanel.add(equipButton);
            itemPanel.add(useButton);
            itemPanel.add(discardButton);
            itemPanel.add(detailsButton);

            // Add the item panel to the inventory panel
            inventoryPanel.add(itemPanel);
        }

        // Refresh the inventory panel to reflect the changes
        inventoryPanel.revalidate();
        inventoryPanel.repaint();

        pack();
    }

    private void showItemDetails(Item item){

    }
}