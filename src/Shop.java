import javax.swing.*;
import java.awt.event.*;

public class Shop extends JDialog {
    private JPanel shopPane;
    private JPanel shopMenu;
    private JComboBox shopComboBox;
    private JTextArea shopInfo;
    private JTextField shopInput;
    private JLabel inputLabel;
    private JPanel mainPanel;
    private JLabel shopImage;
    //B = Buy; S = Sell
    private final int clothesBuyPrice = 10;
    private final int ammoBuyPrice = 4;
    private final int foodBuyPrice = 3; //for 5 units of food
    private final int medBuyPrice = 3;
    private final int splintBuyPrice = 8;
    private final int toolsBuyPrice = 10;
    private final int oxenBuyPrice = 10;

    private final int clothesSellPrice = 8;
    private final int ammoSellPrice = 3;
    private final int foodSellPrice = 2; // for 5 units of food
    private final int medSellPrice = 2;
    private final int splintSellPrice = 5;
    private final int toolsSellPrice = 8;
    private final int oxenSellPrice = 8;
    private int money;

    public Shop(int money, int food, int ammo, int medicine, int clothes, int wagonTools, int splints, int oxen) {
        setContentPane(shopPane);
        setModal(true);
        shopImage.setIcon(new javax.swing.ImageIcon("src/assets/images/Shop.png"));
        if (shopComboBox.getSelectedIndex() == 0) {
            displayMenu();
        }

        shopComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int input = shopComboBox.getSelectedIndex();
                switch (input) {
                    case 0 -> displayMenu();
                    case 1 -> displayFood(food, foodBuyPrice, foodSellPrice);
                    case 2 -> displayAmmo(ammo, ammoBuyPrice, ammoSellPrice);
                    case 3 -> displayMed(medicine, medBuyPrice, medSellPrice);
                    case 4 -> displayClothes(clothes, clothesBuyPrice, clothesSellPrice);
                    case 5 -> displayWT(wagonTools, toolsBuyPrice, toolsSellPrice);
                    case 6 -> displaySplints(splints, splintBuyPrice, splintSellPrice);
                    case 7 -> displayOxen(oxen, oxenBuyPrice, oxenSellPrice);
                    default -> shopInfo.setText("ERROR IN SWITCH");
                }
            }});

        shopInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = shopInput.getText().toUpperCase();
                switch (input) {
                    case "I" -> { displayMenu(); shopComboBox.setSelectedIndex(0); }
                    case "F" -> { displayFood(food, foodBuyPrice, foodSellPrice); shopComboBox.setSelectedIndex(1); }
                    case "A" -> { displayAmmo(ammo, ammoBuyPrice, ammoSellPrice); shopComboBox.setSelectedIndex(2); }
                    case "M" -> { displayMed(medicine, medBuyPrice, medSellPrice); shopComboBox.setSelectedIndex(3); }
                    case "C" -> { displayClothes(clothes, clothesBuyPrice, clothesSellPrice); shopComboBox.setSelectedIndex(4); }
                    case "W" -> { displayWT(wagonTools, toolsBuyPrice, toolsSellPrice); shopComboBox.setSelectedIndex(5); }
                    case "S" -> { displaySplints(splints, splintBuyPrice, splintSellPrice); shopComboBox.setSelectedIndex(6); }
                    case "O" -> { displayOxen(oxen, oxenBuyPrice, oxenSellPrice); shopComboBox.setSelectedIndex(7); }
                    default -> displayMenu();
                }

                shopInput.setText("");
            }
        });


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(closeWindow);

        // call onCancel() on ESCAPE
        shopPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    //Main Shop Menu
    private void displayMenu() {
        shopInfo.setText(
            """
            WELCOME TO THE SHOP!
            
            Here you can buy and sell items during your travels.
            Use the dropdown box to navigate the different items that
            can be purchased and sold. You can also input the letter
            corresponding with the item you would like to view more
            information on:
            
            F: FOOD (5 units)
            A: AMMUNITION
            M: MEDICINE
            C: CLOTHES
            W: WAGON TOOLS
            S: SPLINTS
            O: OXEN
            
            M: RETURN TO THIS MENU
            
            Press ESC to exit the SHOP screen.
            """
        );
    }

    private void displayFood(int food, int bPrice, int sPrice) {
        shopInfo.setText(
                """
                
                """
        );
    }

    private void displayAmmo(int ammo, int bPrice, int sPrice) {
        shopInfo.setText(
                """
                
                """
        );
    }

    private void displayMed(int meds, int bPrice, int sPrice) {
        shopInfo.setText(
                """
                
                """
        );
    }

    private void displayClothes(int clothes, int bPrice, int sPrice) {
        shopInfo.setText(
                """
                
                """
        );
    }

    private void displayWT(int wagonTools, int bPrice, int sPrice) {
        shopInfo.setText(
                """
                
                """
        );
    }

    private void displaySplints(int splints, int bPrice, int sPrice) {
        shopInfo.setText(
                """
                
                """
        );
    }

    private void displayOxen(int oxen, int bPrice, int sPrice) {
        shopInfo.setText(
                """
                
                """
        );
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private WindowAdapter closeWindow = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            onCancel();
        }
    };
}