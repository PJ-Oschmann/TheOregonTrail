import javax.swing.*;
import java.awt.event.*;
import java.text.MessageFormat;

public class Inventory extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea invInfo;
    private JLabel InventoryImageLabel;
    private JPanel mainPanel;
    private JPanel textPanel;
    private JPanel innerPanel;
    private JButton useButton;
    private JComboBox<String> invComboBox;

    public Inventory(int food, int ammunition, int medicine, int clothes, int wagonTools, int splint, int oxen) {
        //instantiating private vars

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //Image goes here
        InventoryImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/Inventory.png"));

        invComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (invComboBox.getSelectedItem() == "SELECT AN INVENTORY ITEM") {
                    invInfo.setText("Please select an inventory message using the dropdown menu.\n" +
                            "Or enter a letter below corresponding the item you would like to view:\n" +
                            "F: FOOD\nA: AMMUNITION\nM: MEDICINE\n C: CLOTHES\n ");
                }

                //etc. for rest of inventory
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

    //example runner
    public static void main(String[] args) {
        int food = 0, ammunition = 0, medicine = 0, clothes = 0, wagonTools = 0, splint = 0, oxen = 0;
        Inventory dialog = new Inventory(food, ammunition, medicine, clothes, wagonTools, splint, oxen);
        dialog.setTitle("Inventory");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
