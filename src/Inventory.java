import javax.swing.*;
import java.awt.event.*;

public class Inventory extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea invInfo;
    private JLabel InventoryImageLabel;
    private JComboBox invComboBox;
    private JButton useButton;

    public Inventory() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        InventoryImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/TestImage2.png"));

        String[] inventoryItems = {" ", "Food (1/4 lb.)", "Water (1 canteen)", "Bandages", "Medicine", "Splints", "Ammo"};
        for (int i = 0; i < inventoryItems.length; i++){
            invComboBox.addItem(inventoryItems[i]);
        }

        invComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (invComboBox.getSelectedItem() == "Food (1/4 lb.)") {
                    invInfo.setText("Food is used to decrease 'hunger' levels of your party members. \n" +
                            "Each pound of food consumed will remove 1/4 that member's total hunger.\n\n" +
                            "You currently have 100 Food (1/4 lb.)");
                }
                if (invComboBox.getSelectedItem() == "Water (1 canteen)") {
                    invInfo.setText("Water is used to decrease 'thirst' levels of your party members.\n" +
                            "Each canteen of water consumed will remove all of that member's thirst.\n\n" +
                            "You currently have 50 Water (1 canteen)");
                }
                //etc. for rest of inventory
            }
        });

        useButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //create dialogue box for using each inventory items
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Inventory dialog = new Inventory();
        dialog.setTitle("Inventory");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
