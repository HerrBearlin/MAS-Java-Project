/*
 * Created by JFormDesigner
 */

package MP1.GUI.History;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

import MP1.GUI.ActionMenu.ActionMenu;
import MP1.Player.Player;
import net.miginfocom.swing.*;


public class HistoryMenu extends JFrame {
    public HistoryMenu(Player player) {
        initComponents(player);
    }

    private void initComponents(Player player) {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Jan Malicki
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        button1 = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]",
            // rows
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("Room History");
        label1.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        contentPane.add(label1, "cell 0 0,alignx center,growx 0");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1, "cell 0 1,aligny bottom,grow 100 0");

        //---- button1 ----
        button1.setText("Back");
        button1.setFont(button1.getFont().deriveFont(button1.getFont().getStyle() | Font.ITALIC));
        button1.setForeground(Color.red);
        contentPane.add(button1, "cell 0 1,aligny top,growy 0");
        pack();
        setLocationRelativeTo(getOwner());
        //---- button1 ----
        button1.setText("Back");
        button1.setFont(button1.getFont().deriveFont(button1.getFont().getStyle() | Font.ITALIC));
        button1.setForeground(Color.red);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the History Menu frame
                dispose();

                // Open the Action Menu frame
                ActionMenu actionMenu = null;

                    actionMenu = new ActionMenu(player);

                actionMenu.setSize(600, 700);
                actionMenu.setLocationRelativeTo(null);
                actionMenu.setVisible(true);
            }
        });
        contentPane.add(button1);

        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Jan Malicki
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
