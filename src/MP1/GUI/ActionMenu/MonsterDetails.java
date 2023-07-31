/*
 * Created by JFormDesigner
 */

package MP1.GUI.ActionMenu;

import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;


public class MonsterDetails extends JFrame {
    public MonsterDetails() {
        initComponents();
        pack();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Jan Malicki
        panel1 = new JPanel();
        vSpacer1 = new JPanel(null);
        label1 = new JLabel();
        button1 = new JButton();
        panel2 = new JPanel();
        label2 = new JLabel();
        label3 = new JLabel();
        textField1 = new JTextField();
        vSpacer2 = new JPanel(null);
        textField2 = new JTextField();
        panel3 = new JPanel();
        label4 = new JLabel();
        label6 = new JLabel();
        label5 = new JLabel();
        textField3 = new JTextField();
        textField5 = new JTextField();
        textField4 = new JTextField();
        panel4 = new JPanel();
        label7 = new JLabel();
        scrollPane1 = new JScrollPane();
        Treasure = new JList();

        //======== this ========
        setForeground(SystemColor.textInactiveText);
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[]",
            // rows
            "[]" +
            "[]" +
            "[41]" +
            "[]"));

        //======== panel1 ========
        {
            panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (
            new javax. swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e"
            , javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM
            , new java .awt .Font ("Dialo\u0067" ,java .awt .Font .BOLD ,12 )
            , java. awt. Color. red) ,panel1. getBorder( )) ); panel1. addPropertyChangeListener (
            new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
            ) {if ("borde\u0072" .equals (e .getPropertyName () )) throw new RuntimeException( )
            ; }} );
            panel1.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[446,fill]" +
                "[50:170,fill]",
                // rows
                "[]"));
            panel1.add(vSpacer1, "cell 0 0");

            //---- label1 ----
            label1.setText(" Monster Details");
            label1.setFont(new Font("Segoe UI", Font.PLAIN, 36));
            panel1.add(label1, "cell 0 0");

            //---- button1 ----
            button1.setText("Back");
            button1.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            panel1.add(button1, "cell 1 0,align right top,grow 0 0");
        }
        contentPane.add(panel1, "cell 0 0");

        //======== panel2 ========
        {
            panel2.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[402,fill]0" +
                "[fill]0" +
                "[fill]0" +
                "[411,fill]",
                // rows
                "[]0" +
                "[]"));

            //---- label2 ----
            label2.setText("Name");
            panel2.add(label2, "cell 0 0,align center center,grow 0 0");

            //---- label3 ----
            label3.setText("Name");
            panel2.add(label3, "cell 3 0,align center center,grow 0 0");

            //---- textField1 ----
            textField1.setEditable(false);
            textField1.setText("Monster 1");
            panel2.add(textField1, "cell 0 1,align center center,grow 0 0");
            panel2.add(vSpacer2, "cell 1 1");

            //---- textField2 ----
            textField2.setText("15/15");
            textField2.setEditable(false);
            panel2.add(textField2, "cell 3 1,alignx center,growx 0");
        }
        contentPane.add(panel2, "cell 0 1");

        //======== panel3 ========
        {
            panel3.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[402,fill]" +
                "[fill]0" +
                "[fill]" +
                "[411,fill]",
                // rows
                "[]0" +
                "[]0" +
                "[]"));

            //---- label4 ----
            label4.setText("MinDamage");
            panel3.add(label4, "cell 0 0,align center center,grow 0 0");

            //---- label6 ----
            label6.setText("MaxDamage");
            panel3.add(label6, "cell 1 0,align center center,grow 0 0");

            //---- label5 ----
            label5.setText("MonsterType");
            panel3.add(label5, "cell 3 0,align center center,grow 0 0");

            //---- textField3 ----
            textField3.setText("2");
            textField3.setEditable(false);
            panel3.add(textField3, "cell 0 1,alignx center,growx 0");

            //---- textField5 ----
            textField5.setText("2");
            textField5.setEditable(false);
            panel3.add(textField5, "cell 1 1,alignx center,growx 0");

            //---- textField4 ----
            textField4.setText("Physical");
            textField4.setEditable(false);
            panel3.add(textField4, "cell 3 1,alignx center,growx 0");
        }
        contentPane.add(panel3, "cell 0 2");

        //======== panel4 ========
        {
            panel4.setLayout(new MigLayout(
                "hidemode 3,align center center",
                // columns
                "[543,grow,center]",
                // rows
                "[52]" +
                "[]"));

            //---- label7 ----
            label7.setText("Treasure");
            label7.setFont(new Font("Segoe UI", Font.PLAIN, 26));
            panel4.add(label7, "cell 0 0");

            //======== scrollPane1 ========
            {

                //---- Treasure ----
                Treasure.setFont(new Font("Segoe UI", Font.PLAIN, 24));
                scrollPane1.setViewportView(Treasure);
            }
            panel4.add(scrollPane1, "cell 0 1,alignx center,growx 0");
        }
        panel2.setVisible(false);
        panel2.setEnabled(false);
        contentPane.add(panel4, "cell 0 3");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Jan Malicki
    private JPanel panel1;
    private JPanel vSpacer1;
    private JLabel label1;
    private JButton button1;
    private JPanel panel2;
    private JLabel label2;
    private JLabel label3;
    private JTextField textField1;
    private JPanel vSpacer2;
    private JTextField textField2;
    private JPanel panel3;
    private JLabel label4;
    private JLabel label6;
    private JLabel label5;
    private JTextField textField3;
    private JTextField textField5;
    private JTextField textField4;
    private JPanel panel4;
    private JLabel label7;
    private JScrollPane scrollPane1;
    private JList Treasure;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
