/*
 * Created by JFormDesigner
 */

package MP1.GUI.ActionMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

import MP1.Dungeon.DungeonRoom;
import MP1.GUI.History.HistoryMenu;
import MP1.GUI.Inventory.InventoryView;
import MP1.GUI.MakeAMove.MakeAMove;
import MP1.Player.Player;
import net.miginfocom.swing.*;


public class ActionMenu extends JFrame {
    public ActionMenu(Player player) {
        initComponents(player);
        setLocationRelativeTo(null); // Center the window on the screen
    }

    private void initComponents(Player player) {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Jan Malicki
        actionMenuPanel = new JPanel();
        label1 = new JLabel();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();


        setTitle("Action Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(actionMenuPanel, BorderLayout.CENTER);
        //======== actionMenuPanel ========
        {
            actionMenuPanel.setForeground(new Color(0x99ff99));
            actionMenuPanel.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder (
            0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder
            . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,java . awt. Color .
            red ) ,actionMenuPanel. getBorder () ) ); actionMenuPanel. addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java .
            beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
            actionMenuPanel.setLayout(new MigLayout(
                "fillx,hidemode 3,align center center",
                // columns
                "rel[50:n:71,fill]rel" +
                "[410,fill]" +
                "[50:n:71,fill]",
                // rows
                "[313,grow]0" +
                "[108]0" +
                "[68]0" +
                "[83]0" +
                "[62]"));

            //---- label1 ----
            label1.setText("Action Menu");
            label1.setFont(new Font("Segoe UI", Font.PLAIN, 48));
            label1.setForeground(Color.orange);
            label1.setBackground(new Color(0xffff99));
            actionMenuPanel.add(label1, "cell 1 0,align center top,grow 0 0");

            //---- button1 ----
            button1.setText("Make a Move");
            button1.setFont(new Font("Segoe UI", Font.PLAIN, 28));
            button1.setForeground(Color.black);
            button1.setBackground(new Color(0x666666));
            actionMenuPanel.add(button1, "cell 1 1,grow");
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Close the Action Menu frame
                    dispose();

                    // Open the MakeAMove frame
                    JFrame makeAMoveFrame = new JFrame("Make a Move");
                    makeAMoveFrame.setSize(400, 400);
                    List<DungeonRoom> dungeonRoomList = DungeonRoom.getAvailableRoomsForThePlayer(player);
                    MakeAMove makeAMove = new MakeAMove(dungeonRoomList, player);
                    makeAMoveFrame.add(makeAMove); // Add makeAMove to the content pane
                    makeAMoveFrame.setLocationRelativeTo(null);
                    makeAMoveFrame.setVisible(true);
                }
            });
            //---- button2 ----
            button2.setText("See Inventory");
            button2.setFont(new Font("Segoe UI", Font.PLAIN, 28));
            button2.setForeground(Color.black);
            button2.setBackground(new Color(0x666666));
            actionMenuPanel.add(button2, "cell 1 2");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Close the Action Menu frame
                    dispose();

                    // Open the InventoryView frame
                    InventoryView inventoryView = new InventoryView(player, ActionMenu.this);
                    inventoryView.setVisible(true);
                }
            });
            //---- button3 ----
            button3.setText("History");
            button3.setFont(new Font("Segoe UI", Font.PLAIN, 28));
            button3.setForeground(Color.black);
            button3.setBackground(new Color(0x666666));
            actionMenuPanel.add(button3, "cell 1 3");
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Close the Action Menu frame
                    dispose();

                    // Open the History frame
                    HistoryMenu historyMenu = new HistoryMenu(player);
                    historyMenu.setVisible(true);
                }
            });

            //---- button4 ----
            button4.setText("Exit");
            button4.setFont(new Font("Segoe UI", Font.PLAIN, 28));
            button4.setForeground(Color.black);
            button4.setBackground(new Color(0x666666));
            actionMenuPanel.add(button4, "cell 1 4");
            button4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Exit the application
                    System.exit(0);
                }
            });

            pack();
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Jan Malicki
    public JPanel actionMenuPanel;
    private JLabel label1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
