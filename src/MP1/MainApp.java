package MP1;

import MP1.ExtentLoader.ExtentLoader;
import MP1.GUI.ActionMenu.ActionMenu;
import MP1.GUI.RoomEncounter.GameOver;
import MP1.Player.Player;

import javax.swing.*;
import java.io.IOException;

public class MainApp {


    public static void main(String[] args) {

        try {
            ExtentLoader.loadAllExtents();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Player player = Player.getPlayerExtent().get(Player.class).get(0);
            ActionMenu frame = new ActionMenu(player);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}