package MP1.GUI.RoomEncounter;

import javax.swing.*;

public class GameOver {

    public GameOver() {
        showPopup();
    }

    private void showPopup() {
        JOptionPane.showMessageDialog(null, "Game Over! You lost the game.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        closeGame();
    }

    private void closeGame() {
        System.exit(0); // Close the entire game
    }
}