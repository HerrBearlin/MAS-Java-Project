/*
 * Created by JFormDesigner
 */

package MP1.GUI.HistoryStatusUpdate;

import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;

public class HistoryStatusUpdate extends JFrame {
    public HistoryStatusUpdate() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Jan Malicki
        backButton = new JButton();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[302,grow,fill]" +
            "[grow,fill]",
            // rows
            "[]" +
            "[59]" +
            "[147]"));

        //---- backButton ----
        backButton.setText("Back");
        backButton.setForeground(new Color(0xff3300));
        contentPane.add(backButton, "cell 1 0");

        //---- label1 ----
        label1.setText("History Status Update");
        label1.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        contentPane.add(label1, "cell 0 0 2 2,align center top,grow 0 0");

        //======== scrollPane1 ========
        {

            //---- textArea1 ----
            textArea1.setText("Room history status is now: cleared");
            textArea1.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
            textArea1.setEditable(false);
            textArea1.setLineWrap(true);
            scrollPane1.setViewportView(textArea1);
        }
        contentPane.add(scrollPane1, "cell 0 2 2 1,growy");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Jan Malicki
    private JButton backButton;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
