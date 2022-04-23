import javax.swing.*;
import java.awt.event.*;

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
                    invInfo.setText("""
                            Please select an INVENTORY item using the dropdown menu
                            or enter the letter the letter in the dialogue box
                            corresponding with the item you would like to view:
                            
                            F: FOOD
                            A: AMMUNITION
                            M: MEDICINE
                            C: CLOTHES
                            W: WAGON TOOLS
                            S: SPLINTS
                            O: OXEN
                            """
                    );
                }
                if (invComboBox.getSelectedItem() == "F: FOOD") {
                    invInfo.setText(String.format(
                            """
                            Food is a resource that prevents your party members
                            from going HUNGRY. If the party has 0 units of FOOD
                            for three days in a row, the game will end.
                            
                            Each unit of FOOD given to a party member will
                            increase their food level by 2.
                            
                            You have %d units of FOOD.
                            
                            Type "U" to use this item.
                            Type "M" to return to the INVENTORY menu.
                            """, food
                    ));
                }
                if (invComboBox.getSelectedItem() == "A: AMMUNITION") {
                    invInfo.setText(String.format(
                            """
                            Ammunition is a consumable resource used in
                            combination with one daily action to go HUNTING.

                            One ammunition box is consumed when your party goes
                            hunting. HUNTING yields about double the food for
                            its cost relative to buying food (on average).
                            
                            You have %d boxes of AMMUNITION.
                                
                            Type "M" to return to the INVENTORY menu.
                            """, ammunition
                    ));
                }
                if (invComboBox.getSelectedItem() == "M: MEDICINE") {
                    invInfo.setText(String.format(
                            """
                            Medicine is a resource that cures your party members
                            of illness. When a party member is ILL, their food level
                            consumption is increased by 2 a day on top of the travel
                            consumption.
                                                        
                            One unit of medicine can cure a single party member, and
                            can only be used on a sick character.
                            
                            You have %d units of MEDICINE.
                            
                            Type "U" to use this item.
                            Type "M" to return to the INVENTORY menu.
                            """, medicine
                    ));
                }
                if (invComboBox.getSelectedItem() == "C: CLOTHES") {
                    invInfo.setText(String.format(
                            """
                            Clothes are a one-time consumable resource that will
                            protect your party members from weather for the remainder
                            of their journey. If a character is not protected from
                            extreme weather, they may fall ILL and lose health as a
                            consequence.
                            
                            This resource can be produced on the trip using a total of
                            3 daily actions to produce clothes. This item may only be
                            used on characters who do not already have a set of clothes.
                            
                            You have %d sets of CLOTHES.
                            
                            Type "U" to use this item.
                            Type "M" to return to the INVENTORY menu.
                            """, clothes
                    ));
                }
                if (invComboBox.getSelectedItem() == "W: WAGON TOOLS") {
                    invInfo.setText(String.format(
                            """
                            Wagon Tools are a consumable resource used to repair the
                            wagon when it suffers damage along the journey. If your
                            wagon is in a DAMAGED state, your party is forced to
                            travel at the slowest pace. If it is left DAMAGED without
                            repair for 7 consecutive days, the wagon will become BROKEN
                            and the party will lose. There is also a risk that the wagon
                            can suffer damage while DAMAGED and become BROKEN, also
                            resulting in a game over.
                            
                            You can use this item when taking the MEND WAGON daily
                            action in between your travels. This will consume one set
                            of wagon tools and one daily action.

                            You have %d spare WAGON TOOLS.
                            
                            Type "M" to return to the INVENTORY menu.
                            """, wagonTools
                    ));
                }

                //TODO: Finish writing out inventory items and their descriptions
        }});

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
}
