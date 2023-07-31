package MP1.GUI.RoomEncounter;

import MP1.Dungeon.DungeonRoom;
import MP1.GUI.ActionMenu.ActionMenu;
import MP1.GUI.Inventory.InventoryView;
import MP1.GUI.Inventory.InventoryViewInCombat;
import MP1.Monster.Monster;
import MP1.Player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomEncounter extends JFrame {
    private JPanel playerPanel;
    private JLabel playerNameLabel;
    private JLabel playerWeaponLabel;
    private JLabel playerArmourLabel;
    private JLabel playerHealthLabel;
    private JPanel buttonPanel;
    private JButton attackButton;
    private JButton useItemButton;
    private JButton clearRoomButton;
    private JButton leaveButton;
    private JPanel monsterPanel;
    private JLabel monsterNameLabel;
    private JLabel monsterMinDamageLabel;
    private JLabel monsterMaxDamageLabel;
    private JLabel monsterHealthLabel;
    private JPanel combatLogPanel;
    private JTextArea combatLogTextArea;

    private Monster currentMonster;
    private DungeonRoom dungeonRoom;

    public RoomEncounter(Player player, DungeonRoom dungeonRoom) {
        this.dungeonRoom =dungeonRoom;
        initComponents(player, dungeonRoom);
        setupLayout();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents(Player player, DungeonRoom dungeonRoom) {
        // Player Panel

        playerPanel = new JPanel(new GridLayout(4, 1));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playerNameLabel = new JLabel("Player Name: " + player.getName());
        playerWeaponLabel = new JLabel("Weapon: " + player.getWeapon());
        playerArmourLabel = new JLabel("Armour: " + player.getArmour());
        playerHealthLabel = new JLabel("Health: " + player.getCurrentHealth());

        playerPanel.add(playerNameLabel);
        playerPanel.add(playerWeaponLabel);
        playerPanel.add(playerArmourLabel);
        playerPanel.add(playerHealthLabel);

        // Button Panel
        buttonPanel = new JPanel(new GridLayout(4, 1));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        attackButton = new JButton("Attack");
        useItemButton = new JButton("Use Item");
        clearRoomButton = new JButton("Clear Room");
        leaveButton = new JButton("Leave");

        buttonPanel.add(attackButton);
        buttonPanel.add(useItemButton);
        buttonPanel.add(clearRoomButton);
        buttonPanel.add(leaveButton);

        clearRoomButton.setEnabled(false);
        // Monster Panel
        monsterPanel = new JPanel(new GridLayout(4, 1));
        monsterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //choose the currentMonster from the dungeonRoom
        currentMonster = dungeonRoom.getMonsters().get(0);
        if(currentMonster.getCurrentHealth() <= 0)
        {
            for (Monster m : dungeonRoom.getMonsters()) {
                if(m.getCurrentHealth() > 0)
                {
                    currentMonster = m;

                }
            }
        }
        Monster monster1 = currentMonster;
        monsterNameLabel = new JLabel("Monster Name: " + monster1.getName());
        monsterMinDamageLabel = new JLabel("Min Damage: " + monster1.getMinDamage());
        monsterMaxDamageLabel = new JLabel("Max Damage: " + monster1.getMaxDamage());
        monsterHealthLabel = new JLabel("Health: " + monster1.getCurrentHealth());

        monsterPanel.add(monsterNameLabel);
        monsterPanel.add(monsterMinDamageLabel);
        monsterPanel.add(monsterMaxDamageLabel);
        monsterPanel.add(monsterHealthLabel);

        // Combat Log Panel
        combatLogPanel = new JPanel(new BorderLayout());
        combatLogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        combatLogTextArea = new JTextArea("Combat Log");
        combatLogTextArea.setEditable(false);

        combatLogPanel.add(new JScrollPane(combatLogTextArea), BorderLayout.CENTER);

        // Main Layout
        setLayout(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(playerPanel, BorderLayout.NORTH);
        leftPanel.add(buttonPanel, BorderLayout.CENTER);
        add(leftPanel);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(monsterPanel, BorderLayout.NORTH);
        rightPanel.add(combatLogPanel, BorderLayout.CENTER);
        add(rightPanel);

        updateRoomData();

        // Action Listeners for buttons
        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                combatLogTextArea.setText("");
                combatLogTextArea.append("Player attacked the monster for: \n" + player.getWeapon().getDamage() +" damage ");

               Double dealtDamage = player.attack(currentMonster);
                currentMonster.attack(player);
                combatLogTextArea.append("Monster attacked! the player for: \n" + dealtDamage +" damage ");
                updateRoomData();
            }
        });

        useItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                combatLogTextArea.setText("");
                combatLogTextArea.append("Player opened the inventory!\n");

                // Open the InventoryView frame and pass the instance of RoomEncounter
                InventoryViewInCombat inventoryViewInCombat = new InventoryViewInCombat(player, RoomEncounter.this);
                inventoryViewInCombat.setVisible(true);
            }
        });

        clearRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                combatLogTextArea.setText("");
                combatLogTextArea.append("Room cleared!\n");
                player.MarkRoomAsCleared(dungeonRoom);
                showRoomClearedPopup();
            }
        });

        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                combatLogTextArea.setText("");
                combatLogTextArea.append("Player left the room!\n");
                // Close the RoomEncounter Menu frame
                dispose();

                // Open the Action Menu frame
                ActionMenu actionMenu = null;

                actionMenu = new ActionMenu(player);

                actionMenu.setSize(600, 700);
                actionMenu.setLocationRelativeTo(null);
                actionMenu.setVisible(true);
            }
        });
    }

    private void setupLayout() {
        pack();
        setLocationRelativeTo(null);
    }

    public void updateRoomData() {
        // Get the latest player data
        Player player = Player.getPlayerExtent().get(Player.class).get(0);

        // Update player-related labels
        playerNameLabel.setText("Player Name: " + player.getName());
        playerWeaponLabel.setText("Weapon: " + player.getWeapon());
        playerArmourLabel.setText("Armour: " + player.getArmour());
        playerHealthLabel.setText("Health: " + player.getCurrentHealth());

        //get new current monster if monster is dead
        if(currentMonster.getCurrentHealth() <= 0)
        {
            currentMonster.DropLoot(player);
            combatLogTextArea.setText("Monster defeated. \n Dropped loot: ");
            combatLogTextArea.append(currentMonster.getTreasure().toString());
            for (Monster m: dungeonRoom.getMonsters()) {
                if(m.getCurrentHealth() >=0)
                {
                    currentMonster = m;
                }
            }
            if(currentMonster.getCurrentHealth() <= 0)
            {
                attackButton.setEnabled(false);
                combatLogTextArea.setText(" ");
                combatLogTextArea.setText("\n Player defeated all monsters. \nCan now clear the room.");
                clearRoomButton.setEnabled(true);

            }
        }

        monsterHealthLabel.setText("Monster health: " + currentMonster.getCurrentHealth());
        monsterMinDamageLabel.setText("Monster minDamage " + currentMonster.getMinDamage());
        monsterNameLabel.setText("Monster name" + currentMonster.getName());
        monsterMaxDamageLabel.setText("Monsteer maxDamage " + currentMonster.getCurrentMaxDamage());


        if(player.getCurrentHealth() <= 0)
        {
            // Player has lost the game
            GameOver gameOver = new GameOver();

        }
        repaint();
    }

    private void showRoomClearedPopup() {
        JOptionPane.showMessageDialog(null, "Room cleared successfully!", "Room Cleared", JOptionPane.INFORMATION_MESSAGE);
    }
}




