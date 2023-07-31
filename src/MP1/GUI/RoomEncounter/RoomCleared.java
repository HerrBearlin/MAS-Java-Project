package MP1.GUI.RoomEncounter;


import javax.swing.*;

public class RoomCleared {

    public RoomCleared() {
        showPopup();
    }

    private void showPopup() {
        JOptionPane.showMessageDialog(null, "Room Cleared! Congratulations.", "Room cleared, levea the room now.", JOptionPane.INFORMATION_MESSAGE);

    }
}