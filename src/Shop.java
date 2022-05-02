import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.InputMismatchException;

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
    private static final int clothesBuyPrice = 10;
    private static final int ammoBuyPrice = 4;
    private static final int foodBuyPrice = 3; //for 5 units of food
    private static final int medBuyPrice = 3;
    private static final int splintBuyPrice = 8;
    private static final int toolsBuyPrice = 10;
    private static final int oxenBuyPrice = 10;

    private static final int clothesSellPrice = 8;
    private static final int ammoSellPrice = 3;
    private static final int foodSellPrice = 2; // for 5 units of food
    private static final int medSellPrice = 2;
    private static final int splintSellPrice = 5;
    private static final int toolsSellPrice = 8;
    private static final int oxenSellPrice = 8;
    private boolean menuListenerActive = true;
    private boolean itemListenerActive = false;
    private boolean inMenu = true;
    private boolean inItem = false;


    private final OregonTrailGUI game;

    public int money; int food; int ammunition; int medicine; int clothes; int wagonTools; int splints; int oxen;

    public Shop(OregonTrailGUI game){
        this.game = game;
        setGlobalVar();
        setContentPane(shopPane);
        setModal(true);
        this.setTitle("THE SHOP");
        shopImage.setIcon(new javax.swing.ImageIcon("src/assets/images/Shop.png"));
        shopInput.addActionListener(shopMenuListener);
        shopInput.addFocusListener(inputHelp);
        displayMenu();
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) { shopInput.requestFocusInWindow(); }
            @Override
            public void windowClosing(WindowEvent e) { }
            @Override
            public void windowClosed(WindowEvent e) { }
            @Override
            public void windowIconified(WindowEvent e) { }
            @Override
            public void windowDeiconified(WindowEvent e) { }
            @Override
            public void windowActivated(WindowEvent e) { }
            @Override
            public void windowDeactivated(WindowEvent e) { }
        });

        shopComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int input = shopComboBox.getSelectedIndex();
                switch (input) {
                    case 0 -> { displayMenu(); inMenu(); }
                    case 1 -> { displayFood(food); inItem();}
                    case 2 -> { displayAmmo(ammunition); inItem(); }
                    case 3 -> { displayMed(medicine); inItem(); }
                    case 4 -> { displayClothes(clothes); inItem(); }
                    case 5 -> { displayWT(wagonTools); inItem(); }
                    case 6 -> { displaySplints(splints); inItem(); }
                    case 7 -> { displayOxen(oxen); inItem(); }
                }
                if (inMenu && !menuListenerActive) {
                    menuSelected();
                }
                else if (inItem && !itemListenerActive) {
                    itemSelected();
                }
            }});

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

    private void inMenu() {
        inMenu = true;
        inItem = false;
        shopInput.setText("");
    }

    private void inItem() {
        inItem = true;
        inMenu = false;
        shopInput.setText("");
    }

    //Main Shop Menu
    private void displayMenu() {
        shopInfo.setText(String.format(
            """
            WELCOME TO THE SHOP!
            
            Here you can buy and sell items during your travels.
            Use the dropdown box to navigate the different items that
            can be purchased and sold. You can also input the letter
            corresponding with the item you would like to view more
            information on:
            
            YOU CURRENTLY HAVE $%d.
            
            F: FOOD (5 units)
            A: AMMUNITION
            M: MEDICINE
            C: CLOTHES
            W: WAGON TOOLS
            S: SPLINTS
            O: OXEN
            
            You will begin the journey with 4 oxen, however, it is recommended
            you purchase more.
            
            R: RETURN TO THIS MENU
            
            Press ESC to exit the SHOP once you are finished shopping.
            """, money
        ));
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
                You have $%d.
                
                Enter "B" to buy FOOD.
                Enter "S" to sell FOOD.
                Enter "R" to return to the SHOP MENU.
                """, foodBuyPrice, foodSellPrice, food, game.getMoney()
        ));
    }

    private void displayAmmo(int ammunition) {
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
                You have $%d.
                
                Enter "B" to buy AMMUNITION.
                Enter "S" to sell AMMUNITION.
                Enter "R" to return to the SHOP MENU.
                """, ammoBuyPrice, ammoSellPrice, ammunition, game.getMoney()
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
                You have $%d.
                
                Enter "B" to buy MEDICINE.
                Enter "S" to sell MEDICINE.
                Enter "R" to return to the SHOP MENU.
                """, medBuyPrice, medSellPrice, meds, game.getMoney()
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
                You have $%d.
                
                Enter "B" to buy CLOTHES.
                Enter "S" to sell CLOTHES.
                Enter "R" to return to the SHOP MENU.
                """, clothesBuyPrice, clothesSellPrice, clothes, game.getMoney()
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
                You have $%d.
                
                Enter "B" to buy WAGON TOOLS.
                Enter "S" to sell WAGON TOOLS.
                Enter "R" to return to the SHOP MENU.
                """, toolsBuyPrice, toolsSellPrice, wagonTools, game.getMoney()
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
                You have $%d.
                
                Enter "B" to buy SPLINTS.
                Enter "S" to sell SPLINTS.
                Enter "R" to return to the SHOP MENU.
                """, splintBuyPrice, splintSellPrice, splints, game.getMoney()
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
                You have $%d.
                
                Enter "B" to buy OXEN.
                Enter "S" to sell OXEN.
                Enter "R" to return to the SHOP MENU.
                """, oxenBuyPrice, oxenSellPrice, oxen, game.getMoney()
        ));
    }

    private void onCancel() {
        if (JOptionPane.showConfirmDialog(null,"Are you sure you want to leave the shop?",
                "Leave SHOP?",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            passBackVar();
            dispose();
        }
    }

    private WindowAdapter closeWindow = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            onCancel();
        }
    };

    private final FocusAdapter inputHelp = new FocusAdapter() { //Grey text for input box when not focused on
        @Override
        public void focusGained(FocusEvent e) {
            shopInput.setText("");
            shopInput.setForeground(Color.BLACK);
        }

        @Override
        public void focusLost(FocusEvent e) {
            shopInput.setText("Input Option Here");
            shopInput.setForeground(new Color(147, 147,147));
        }
    };

    private final ActionListener shopMenuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = shopInput.getText().toUpperCase();
            switch (input) {
                case "R" -> { displayMenu(); shopComboBox.setSelectedIndex(0); }
                case "F" -> { displayFood(food); shopComboBox.setSelectedIndex(1); }
                case "A" -> { displayAmmo(ammunition); shopComboBox.setSelectedIndex(2); }
                case "M" -> { displayMed(medicine); shopComboBox.setSelectedIndex(3); }
                case "C" -> { displayClothes(clothes); shopComboBox.setSelectedIndex(4); }
                case "W" -> { displayWT(wagonTools); shopComboBox.setSelectedIndex(5); }
                case "S" -> { displaySplints(splints); shopComboBox.setSelectedIndex(6); }
                case "O" -> { displayOxen(oxen); shopComboBox.setSelectedIndex(7); }
                default -> staticMethods.notValidInput();
            }
            shopInput.setText("");
        }
    };

    private final ActionListener shopItemListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = shopInput.getText().toUpperCase();
            switch (input) {
                case "B" -> buyItem();
                case "S" -> sellItem();
                case "R" -> { shopComboBox.setSelectedIndex(0); inMenu(); }
                default -> { staticMethods.notValidInput(); shopComboBox.setSelectedIndex(0); inMenu(); }
            }
            if (inMenu && !menuListenerActive) {
                menuSelected();
            } else if (inItem & !itemListenerActive) {
                itemSelected();
            }
            else if (menuListenerActive && itemListenerActive || !menuListenerActive && !itemListenerActive) {
                throw new IllegalArgumentException("Shop didn't function correctly in shopItemListener.");
            }
            shopInput.setText("");
        }
    };

    private void setGlobalVar() {
        this.food = game.getFood();
        this.ammunition = game.getAmmunition();
        this.medicine = game.getMedicine();
        this.clothes = game.getClothes();
        this.wagonTools = game.getWagonTools();
        this.splints = game.getSplints();
        this.oxen = game.getOxen();
        this.money = game.getMoney();
    }

    public void passBackVar() {
        game.setFood(this.food);
        game.setAmmunition(this.ammunition);
        game.setMedicine(this.medicine);
        game.setClothes(this.clothes);
        game.setWagonTools(this.wagonTools);
        game.setSplints(this.splints);
        game.setOxen(this.oxen);
        game.setMoney(this.money);
    }

    private void itemSelected(){
        shopInput.removeActionListener(shopMenuListener);
        shopInput.addActionListener(shopItemListener);
        itemListenerActive = true;
        menuListenerActive = false;
    }

    private void menuSelected() {
        shopInput.addActionListener(shopMenuListener);
        shopInput.removeActionListener(shopItemListener);
        itemListenerActive = false;
        menuListenerActive = true;
    }

    private int enterBuyQuantity(String item, int itemCost) {
        int quantity = 0;
            try {
                do {
                    quantity = Integer.parseInt(JOptionPane.showInputDialog(
                            String.format(
                                    """
                                            You have $%d available to spend. This item costs $%d.
                                            How many %s would you like to purchase?
                                            (Enter 0 to cancel):
                                            """, money, itemCost, item)));
                            if (quantity * itemCost > money) {
                                staticMethods.notEnoughMoney();
                            }
                } while (quantity * itemCost > money);
            }
            catch (InputMismatchException e) {
                e.printStackTrace();
                staticMethods.notValidInput();
            }
        return quantity;
    }

    private int enterSellQuantity(String itemName, int item) {
        int quantity = 0;
        try {
            do {
                quantity = Integer.parseInt(JOptionPane.showInputDialog(
                        String.format("""
                                You have %d %s in your INVENTORY.
                                How many %s would you like to sell?
                                (Enter 0 to cancel):""", item, itemName, itemName)
                ));
                if (quantity == 0) { break; }
                else if (quantity > item) {
                    notEnoughQuantity(itemName);
                }
            } while (quantity > item);
        }
        catch (InputMismatchException e) {
            e.printStackTrace();
            staticMethods.notValidInput();
        }
        return quantity;
    }

    private void buyItem() {
        int index = shopComboBox.getSelectedIndex();
        String itemName = "";
        switch (index) {
            case 1 -> { itemName = "FOOD"; food = checkBuy(itemName, food, foodBuyPrice); } //food
            case 2 -> { itemName = "AMMUNITION"; ammunition = checkBuy(itemName, ammunition, ammoBuyPrice);} //ammo
            case 3 -> { itemName = "MEDICINE"; medicine = checkBuy(itemName, medicine, medBuyPrice); } //meds
            case 4 -> { itemName = "CLOTHES"; clothes = checkBuy(itemName, clothes, clothesBuyPrice); } //clothes
            case 5 -> { itemName = "WAGON TOOLS"; wagonTools = checkBuy(itemName, wagonTools, toolsBuyPrice); } //wagonTools
            case 6 -> { itemName = "SPLINTS"; splints = checkBuy(itemName, splints, splintBuyPrice); } //splints
            case 7 -> { itemName = "OXEN"; oxen = checkBuy(itemName, oxen, oxenBuyPrice); } //oxen
            default -> { staticMethods.notValidInput(); }
        }
    }

    private void sellItem() {
        int index = shopComboBox.getSelectedIndex();
        String itemName = "";
        switch (index) {
            case 1 -> { itemName = "FOOD"; food = checkSell(itemName, food); } //food
            case 2 -> { itemName = "AMMUNITION"; ammunition = checkSell(itemName, ammunition);} //ammo
            case 3 -> { itemName = "MEDICINE"; medicine = checkSell(itemName, medicine); } //meds
            case 4 -> { itemName = "CLOTHES"; clothes = checkSell(itemName, clothes); } //clothes
            case 5 -> { itemName = "WAGON TOOLS"; wagonTools = checkSell(itemName, wagonTools); } //wagonTools
            case 6 -> { itemName = "SPLINTS"; splints = checkSell(itemName, splints); } //splints
            case 7 -> { itemName = "OXEN"; oxen = checkSell(itemName, oxen); } //oxen
            default -> { staticMethods.notValidInput(); }
        }
        game.setMoney(money);
    }

    private int checkBuy(String itemName, int item, int itemCost) {
        int quantity = enterBuyQuantity(itemName, itemCost);
        if ( quantity == 0 ) {
            transactionCancelled();
        }
        else {
            int buyPrice;
            switch (itemName) {
                case "FOOD" -> { buyPrice = foodBuyPrice; }
                case "AMMUNITION" -> { buyPrice = ammoBuyPrice; }
                case "MEDICINE" -> { buyPrice = medBuyPrice; }
                case "CLOTHES" -> { buyPrice = clothesBuyPrice; }
                case "WAGON TOOLS" -> { buyPrice = toolsBuyPrice; }
                case "SPLINTS" -> { buyPrice = splintBuyPrice; }
                case "OXEN" -> { buyPrice = oxenBuyPrice; }
                default -> { throw new RuntimeException("error in buying item"); }
            }
            int costOfPurchase = quantity * buyPrice;
            if (money < costOfPurchase) {
                staticMethods.notEnoughMoney();
            }
            else {
                boolean yn = confirmBuy(itemName, costOfPurchase, quantity);
                if (yn) {
                    money -= costOfPurchase;
                    item = item + quantity;
                    buyDialogue(itemName, costOfPurchase, quantity);
                }
                else {
                    transactionCancelled();
                }
            }
        }
        game.setMoney(money);
        return item;
    }

    private int checkSell(String itemName, int item) {
        int quantity = enterSellQuantity(itemName, item);
        if (quantity == 0) {
            transactionCancelled();
        }
        else {
        int sellPrice;
            switch (itemName) {
                case "FOOD" -> { sellPrice = foodSellPrice; }
                case "AMMUNITION" -> { sellPrice = ammoSellPrice; }
                case "MEDICINE" -> { sellPrice = medSellPrice; }
                case "CLOTHES" -> { sellPrice = clothesSellPrice; }
                case "WAGON TOOLS" -> { sellPrice = toolsSellPrice; }
                case "SPLINTS" -> { sellPrice = splintSellPrice; }
                case "OXEN" -> { sellPrice = oxenSellPrice; }
                default -> { throw new RuntimeException("error in selling item");}
            }
            int moneyEarned = quantity * sellPrice;
            item -= quantity;
            game.setMoney(game.getMoney() + moneyEarned);
            sellDialogue(itemName, moneyEarned, quantity);
        }
        game.setMoney(money);
        return item;
    }

    private void buyDialogue(String name, int cost, int amount) {
        if (name.equals("FOOD")) {
            amount *= 5;
        }
        JOptionPane.showMessageDialog(null,
                String.format("You've purchased %d units of %s for $%d.", amount, name, cost));
    }

    private void sellDialogue(String name, int profit, int amount) {
        if (name.equals("FOOD")) {
            amount *= 5;
        }
        JOptionPane.showMessageDialog(null,
                String.format("You've sold %d units of %s for $%d", amount, name, profit));
    }

    private boolean confirmBuy(String name, int cost, int amount) {
        boolean confirmed;
        if (name.equals("FOOD")) {
            amount *= 5;
        }
        confirmed = JOptionPane.showConfirmDialog(null,
                String.format("Are you sure you want to buy %d units of %s for %d dollars?", amount, name, cost),
                "ARE YOU SURE?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        return confirmed;
    }

    private void notEnoughQuantity(String itemName) {
        JOptionPane.showMessageDialog(null,
                String.format("You don't have enough %s to make this transaction.", itemName), "INVALID",
                JOptionPane.ERROR_MESSAGE);
    }

    private void transactionCancelled() {
        JOptionPane.showMessageDialog(null, "The transaction was cancelled.","CANCELLED",
                JOptionPane.PLAIN_MESSAGE);
    }
}