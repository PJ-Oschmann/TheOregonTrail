import javax.swing.*;
import java.awt.*;
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
                    case 1 -> displayFood(food);
                    case 2 -> displayAmmo(ammo);
                    case 3 -> displayMed(medicine);
                    case 4 -> displayClothes(clothes);
                    case 5 -> displayWT(wagonTools);
                    case 6 -> displaySplints(splints);
                    case 7 -> displayOxen(oxen);
                    default -> shopInfo.setText("ERROR IN SWITCH");
                }
            }});

        shopInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = shopInput.getText().toUpperCase();
                switch (input) {
                    case "I" -> { displayMenu(); shopComboBox.setSelectedIndex(0); }
                    case "F" -> { displayFood(food); shopComboBox.setSelectedIndex(1); }
                    case "A" -> { displayAmmo(ammo); shopComboBox.setSelectedIndex(2); }
                    case "M" -> { displayMed(medicine); shopComboBox.setSelectedIndex(3); }
                    case "C" -> { displayClothes(clothes); shopComboBox.setSelectedIndex(4); }
                    case "W" -> { displayWT(wagonTools); shopComboBox.setSelectedIndex(5); }
                    case "S" -> { displaySplints(splints); shopComboBox.setSelectedIndex(6); }
                    case "O" -> { displayOxen(oxen); shopComboBox.setSelectedIndex(7); }
                    default -> displayMenu();
                }

                shopInput.setText("");
            }
        });

        shopInput.addFocusListener(inputHelp);

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
            
            Press ESC to exit the SHOP.
            """
        );
    }

    private void displayFood(int food) {
        shopInfo.setText(String.format(
                """
                FOOD is a resource that prevents your party members
                from going HUNGRY. If the party has 0 units of FOOD
                for three days in a row, the game will end.
                
                Each unit of FOOD given to a party member will
                increase their food level by 2.
                
                You may buy FOOD from THE SHOP in bundles of 5 units:
                BUYING 1 bundle of 5 units of FOOD costs $%d.
                
                You may sell FOOD to THE SHOP in bundles of 5 units:
                SELLING 1 bundle of 5 units of FOOD pays $%d.
                
                You currently have %d units of FOOD in your inventory.
                
                Enter "B" to buy FOOD.
                Enter "S" to sell FOOD.
                Enter "M" to return to the SHOP MENU.
                """, foodBuyPrice, foodSellPrice, food
        ));
    }

    private void displayAmmo(int ammo) {
        shopInfo.setText(String.format(
                """
                AMMUNITION is a consumable resource used in
                combination with one daily action to go HUNTING.

                One AMMUNITION box is consumed when your party goes
                hunting. HUNTING yields about double the food for
                its cost relative to buying food (on average).
                
                You may buy AMMUNITION from THE SHOP:
                BUYING 1 box of AMMUNITION costs $%d.
                
                You may sell AMMUNITION to THE SHOP:
                SELLING 1 box of AMMUNITION pays $%d.
                
                You have %d boxes of AMMUNITION in your INVENTORY.
                
                Enter "B" to buy AMMUNITION.
                Enter "S" to sell AMMUNITION.
                Enter "M" to return to the SHOP MENU.
                """, ammoBuyPrice, ammoSellPrice, ammo
        ));
    }

    private void displayMed(int meds) {
        shopInfo.setText(String.format(
                """
                MEDICINE is a resource that cures your party members
                of illness. When a party member is ILL, their food level
                consumption is increased by 2 a day on top of the travel
                consumption. That party member will also lose 5 HP a day
                and party happiness is reduced by 2 for each member that is
                sick each day.
                                            
                One unit of MEDICINE can cure a single party member, and
                can only be used on a character who is ILL.
                
                You may buy MEDICINE from THE SHOP:
                BUYING 1 unit of MEDICINE costs $%d.
                
                You may sell MEDICINE to THE SHOP:
                SELLING 1 unit of MEDICINE pays $%d.
                
                You have %d units if MEDICINE in your INVENTORY.
                
                Enter "B" to buy MEDICINE.
                Enter "S" to sell MEDICINE.
                Enter "M" to return to the SHOP MENU.
                """, medBuyPrice, medSellPrice, meds
        ));
    }

    private void displayClothes(int clothes) {
        shopInfo.setText(String.format(
                """
                CLOTHES are a one-time consumable resource that will
                protect your party members from weather for the remainder
                of their journey. If a character is not protected from
                extreme weather, they may fall ILL and lose health as a
                consequence.
                
                This resource can be produced on the trip using a total of
                3 daily actions to produce clothes. This item may only be
                used on characters who do not already have a set of CLOTHES.
                
                You may buy CLOTHES from THE SHOP:
                BUYING 1 set of CLOTHES costs $%d.
                
                You may sell CLOTHES to THE SHOP:
                SELLING 1 set of CLOTHES pays $%d.
                
                You have %d sets of CLOTHES in your INVENTORY.
                
                Enter "B" to buy CLOTHES.
                Enter "S" to sell CLOTHES.
                Enter "M" to return to the SHOP MENU.
                """, clothesBuyPrice, clothesSellPrice, clothes
        ));
    }

    private void displayWT(int wagonTools) {
        shopInfo.setText(String.format( //TODO: NEEDS EDITING
                """
                WAGON TOOLS are a consumable resource used to repair the
                wagon when it suffers damage along the journey. If your
                wagon is in a DAMAGED state, your party is forced to
                travel at the slowest pace. If it is left DAMAGED without
                repair for 7 consecutive days, the wagon will become BROKEN
                and the party will lose. There is also a risk that the wagon
                can suffer damage while DAMAGED and become BROKEN, also
                resulting in a game over.
                
                You can use this item when taking the MEND WAGON daily
                action in between your travels. This will consume one set
                of WAGON TOOLS and one daily action.
                
                You may buy WAGON TOOLS from THE SHOP:
                BUYING 1 set of WAGON TOOLS costs $%d.
                
                You may sell WAGON TOOLS to THE SHOP:
                SELLING 1 set of WAGON TOOLS pays $%d.
                
                You have %d sets of WAGON TOOLS in your INVENTORY.
                
                Enter "B" to buy WAGON TOOLS.
                Enter "S" to sell WAGON TOOLS.
                Enter "M" to return to the SHOP MENU.
                """, toolsBuyPrice, toolsSellPrice, wagonTools
        ));
    }

    private void displaySplints(int splints) {
        shopInfo.setText(String.format( //TODO: NEEDS EDITING
                """
                SPLINTS are used to cure a party member of the INJURED
                condition. When someone is injured, it takes one week to
                naturally recover. While they are recovering, your party
                is forced to travel at the lowest speed. They lose 5 HP
                a day and party happiness decreases by 1 per day for each
                injured party member. Additionally, you will only have one
                daily action available until no one is injured.
                                             
                You can use one SPLINT to cure one party member from the
                INJURED status. This item may only be used on characters
                who are INJURED.
                
                You may buy SPLINTS from THE SHOP:
                BUYING 1 SPLINT costs $%d.
                
                You may sell SPLINTS to THE SHOP:
                SELLING 1 SPLINT pays $%d.
                
                You have %d SPLINTS in your INVENTORY.
                
                Enter "B" to buy SPLINTS.
                Enter "S" to sell SPLINTS.
                Enter "M" to return to the SHOP MENU.
                """, splintBuyPrice, splintSellPrice, splints
        ));
    }

    private void displayOxen(int oxen) {
        shopInfo.setText(String.format( //TODO: NEEDS EDITING
                """
                OXEN are used to drive your wagon in your journey. You need
                a minimum of two OXEN to drive your cart at all available
                speeds, and one OXEN to be able to travel at all. There is
                an increased risk of injury to your OXEN if you have less
                than 4 OXEN pulling your wagon. If your OXEN becomes injured,
                you have the choice of harvesting 10 FOOD from it, or
                abandoning the OXEN.
                
                You may choose to kill an OXEN if you are low on food and
                in dire straits.
                
                You may buy OXEN from THE SHOP:
                BUYING 1 OXEN costs $%d.
                
                You may sell OXEN to THE SHOP:
                SELLING 1 OXEN pays $%d.
                
                You have %d OXEN in your INVENTORY.
                
                Enter "B" to buy OXEN.
                Enter "S" to sell OXEN.
                Enter "M" to return to the OXEN.
                """, oxenBuyPrice, oxenSellPrice, oxen
        ));
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

    private FocusAdapter inputHelp = new FocusAdapter() { //Grey text for input box when not focused on
        @Override
        public void focusGained(FocusEvent e) {
            if (shopInput.getText().trim().equals("Input Option Here")) {
                shopInput.setText("");
                shopInput.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            shopInput.setText("Input Option Here");
            shopInput.setForeground(new Color(147, 147,147));
        }
    };
}