package MP1.GUI.MakeAMove;

import MP1.Dungeon.Dungeon;
import MP1.Dungeon.DungeonRoom;
import MP1.ExtentLoader.ExtentLoader;
import MP1.GUI.RoomEncounter.GameOver;
import MP1.GUI.RoomEncounter.RoomEncounter;
import MP1.Item.Item;
import MP1.Monster.Monster;
import MP1.Player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MakeAMove extends JFrame {
    private Player player;
    private List<DungeonRoom> availableRooms;
    private JPanel buttonPanel;
    private JPanel roomDetailsPanel;
    private JLabel roomNameLabel;
    private JLabel roomDescriptionLabel;
    private JLabel roomHistoryStatusLabel;
    private JLabel monstersLabel;

    private DungeonRoom selectedRoom; // Keep track of the selected room

    public MakeAMove(List<DungeonRoom> availableRooms, Player player) {
        this.player = player;
        this.availableRooms = availableRooms;
        initComponents(availableRooms, player);
        setupLayout();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        if(player.getCurrentHealth() <= 0)
        {
            GameOver gameOver = new GameOver();
        }
    }

    private void initComponents(List<DungeonRoom> availableRooms, Player player) {
        buttonPanel = new JPanel(new GridLayout(availableRooms.size(), 1));
        roomDetailsPanel = new JPanel(new GridLayout(5, 2));
        roomDetailsPanel.setVisible(false);

        this.setSize(400, 400);
        for (DungeonRoom room : availableRooms) {
            JPanel roomButtonPanel = new JPanel(new BorderLayout());

            JButton roomButton = new JButton(room.getName());
            roomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateRoomDetails(room);
                }
            });
            roomButtonPanel.add(roomButton, BorderLayout.NORTH);

            JButton enterButton = new JButton("Enter " + room.getName());
            enterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedRoom = room; // Store the selected room
                    player.EnterTheRoom(room);
                    openRoomEncounter(room);; // Open RoomEncounter window
                }
            });
            roomButtonPanel.add(enterButton, BorderLayout.CENTER);

            buttonPanel.add(roomButtonPanel);
        }

        roomNameLabel = new JLabel("Room Name:");
        roomDescriptionLabel = new JLabel("Room Description:");
        roomHistoryStatusLabel = new JLabel("History Status:");
        monstersLabel = new JLabel("Monsters in the Room:");

        roomDetailsPanel.add(roomNameLabel);
        roomDetailsPanel.add(roomDescriptionLabel);
        roomDetailsPanel.add(roomHistoryStatusLabel);
        roomDetailsPanel.add(monstersLabel);
    }

    private void updateRoomDetails(DungeonRoom room) {
        selectedRoom = room; // Set the selected room
        roomDetailsPanel.setVisible(true);

        roomNameLabel.setText("Room Name: " + room.getName());
        roomDescriptionLabel.setText("Room Description: " + room.getDescription());

        // Check if the history status is null
        String historyStatus = room.getPlayerHistory(player) != null ? room.getPlayerHistory(player).getRoomStatus().toString() : "No history available";
        roomHistoryStatusLabel.setText("History Status: " + historyStatus);

        roomDetailsPanel.remove(monstersLabel); // Remove the existing monsters label

        // Remove previous monster buttons
        Component[] components = roomDetailsPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel && component.getName() != null && component.getName().equals("monstersPanel")) {
                roomDetailsPanel.remove(component);
                break; // Only remove the first instance of the monstersPanel
            }
        }

        // Retrieve the monsters in the room
        Map<String, Monster> monstersInRoom = room.getMonstersInRoom();

        if (!monstersInRoom.isEmpty()) {
            // Create buttons for each monster
            JPanel monstersPanel = new JPanel();
            monstersPanel.setLayout(new BoxLayout(monstersPanel, BoxLayout.Y_AXIS));
            monstersPanel.setName("monstersPanel");

            for (Monster monster : room.getMonsters()) {
                if(monster.getCurrentHealth() > 0)
                {
                    JButton monsterButton = new JButton(monster.getName());
                    monsterButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showMonsterDetails(monster);
                        }
                    });
                    monstersPanel.add(monsterButton);
                }

            }

            roomDetailsPanel.add(monstersLabel);
            roomDetailsPanel.add(monstersPanel);
        }

        revalidate();
        repaint();
    }

    private void showMonsterDetails(Monster monster) {
        JDialog dialog = new JDialog(this, "Monster Details", true);
        dialog.setLayout(new BorderLayout());

        // Create a panel to hold the monster details
        JPanel monsterDetailsPanel = new JPanel();
        monsterDetailsPanel.setLayout(new GridLayout(6, 2));

        // Add the monster details to the panel
        monsterDetailsPanel.add(new JLabel("Monster Name:"));
        monsterDetailsPanel.add(new JLabel(monster.getName()));
        monsterDetailsPanel.add(new JLabel("Monster Current Health:"));
        monsterDetailsPanel.add(new JLabel(String.valueOf(monster.getCurrentHealth())));
        monsterDetailsPanel.add(new JLabel("Monster Min Damage:"));
        monsterDetailsPanel.add(new JLabel(String.valueOf(monster.getMinDamage())));
        monsterDetailsPanel.add(new JLabel("Monster Max Damage:"));
        monsterDetailsPanel.add(new JLabel(String.valueOf(monster.getMaxDamage())));
        monsterDetailsPanel.add(new JLabel("Monster Type:"));
        monsterDetailsPanel.add(new JLabel("MonsterType"));

        Set<Item> treasureSet = monster.getTreasure();
        String[] itemNames = treasureSet.stream().map(Item::getItemName).toArray(String[]::new);
        int index = 0;
        for (Item item : treasureSet) {
            itemNames[index++] = item.getItemName();
        }
        JList<String> treasureList = new JList<>(itemNames);
        JScrollPane treasureScrollPane = new JScrollPane(treasureList);
        monsterDetailsPanel.add(new JLabel("Treasure:"));
        monsterDetailsPanel.add(treasureScrollPane);

        dialog.add(monsterDetailsPanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openRoomEncounter(DungeonRoom room) {
        setVisible(false); // Hide the MakeAMove window
        new RoomEncounter(player, room); // Open the RoomEncounter window with the player and room information
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.WEST);
        add(roomDetailsPanel, BorderLayout.CENTER);
    }
}
