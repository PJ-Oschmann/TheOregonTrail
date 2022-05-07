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

    public Shop(OregonTrailGUI game){
        maximize();
        this.game = game;
        setContentPane(shopPane);
        setModal(true);
        this.setTitle("THE SHOP");
        shopImage.setIcon(staticMethods.getImage("assets/images/Shop.png"));
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
                    case 1 -> { displayFood(game.getFood()); inItem();}
                    case 2 -> { displayAmmo(game.getAmmunition()); inItem(); }
                    case 3 -> { displayMed(game.getMedicine()); inItem(); }
                    case 4 -> { displayClothes(game.getClothes()); inItem(); }
                    case 5 -> { displayWT(game.getWagonTools()); inItem(); }
                    case 6 -> { displaySplints(game.getSplints()); inItem(); }
                    case 7 -> { displayOxen(game.getOxen()); inItem(); }
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

    /**
     * This method establishes a specific way for the dialogue form to open and display on the user's screen.
     */
    private void maximize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setMinimumSize(new Dimension(screenSize.width-100,screenSize.height-100));
        int width = screenSize.width/2-this.getWidth()/2;
        int height = screenSize.height/2-this.getHeight()/2;
        this.setLocation(width, height);
    }

    /**
     * These methods are used to track the location of the Shop GUI form that the user is in or seeing
     */
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

    /**
     * The displayMenu method prints out the menu displaying information about the shop and what the user can do.
     */
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
            
            You will begin the journey with 4 oxen, however,
            it is recommended you purchase more.
            
            R: RETURN TO THIS MENU
            
            Press ESC to exit the SHOP once you are finished shopping.
            """, game.getMoney()
        ));
    }

    /**
     * The displayFood method prints out the available options the user can take in this "item" page and the description
     * of the game shop item "Food".
     * @param food is the quantity of food that is available in the user's inventory.
     */
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

    /**
     * The displayAmmo method prints out the available options the user can take in this "item" page and the description
     * of the game shop item "Ammunition".
     * @param ammunition is the quantity of ammunition that is available in the user's inventory.
     */
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

    /**
     * The displayMed method prints out the available options the user can take in this "item" page and the description
     * of the game shop item "Medicine".
     * @param meds is the quantity of Medicine that is available in the user's inventory.
     */
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

    /**
     * The displayClothes method prints out the available options the user can take in this "item" page and the description
     * of the game shop item "Clothes".
     * @param clothes is the quantity of clothes that is available in the user's inventory.
     */
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

    /**
     * The displayWT method prints out the available options the user can take in this "item" page and the description
     * of the game shop item "Wagon Tools".
     * @param wagonTools is the quantity of wagon tools that is available in the user's inventory.
     */
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

    /**
     * The displaySplints method prints out the available options the user can take in this "item" page and the description
     * of the game shop item "Splints".
     * @param splints is the quantity of splints that is available in the user's inventory.
     */
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

    /**
     * The displayOxen method prints out the available options the user can take in this "item" page and the description
     * of the game shop item "Oxen".
     * @param oxen is the quantity of oxen that is available in the user's inventory.
     */
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

    /**
     * Provides a confirmation message asking if the user is sure that they want to leave the shop, exiting the
     * dialogue form if they enter yes.
     */
    private void onCancel() {
        if (JOptionPane.showConfirmDialog(null,"Are you sure you want to leave the shop?",
                "Leave SHOP?",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    /**
     * calls the onCancel method when the user presses the "X" on the window or hits the ESC key.
     */
    private WindowAdapter closeWindow = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            onCancel();
        }
    };

    /**
     * This FocusAdapter creates a grey string of text in the textfield the user will interact with. Upon selecting the
     * textfield, the grey text will be set to empty and the foreground font color will change to black. When they click
     * off of the textfield, the area will be emptied out and the grey text will return.
     */
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

    /**
     * This action listener is responsible for reading the user inputs in the textfield while they are on the menu
     * area of the shop gui form
     */
    private final ActionListener shopMenuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = shopInput.getText().toUpperCase();
            switch (input) {
                case "R" -> { displayMenu(); shopComboBox.setSelectedIndex(0); }
                case "F" -> { displayFood(game.getFood()); shopComboBox.setSelectedIndex(1); }
                case "A" -> { displayAmmo(game.getAmmunition()); shopComboBox.setSelectedIndex(2); }
                case "M" -> { displayMed(game.getMedicine()); shopComboBox.setSelectedIndex(3); }
                case "C" -> { displayClothes(game.getClothes()); shopComboBox.setSelectedIndex(4); }
                case "W" -> { displayWT(game.getWagonTools()); shopComboBox.setSelectedIndex(5); }
                case "S" -> { displaySplints(game.getSplints()); shopComboBox.setSelectedIndex(6); }
                case "O" -> { displayOxen(game.getOxen()); shopComboBox.setSelectedIndex(7); }
                default -> staticMethods.notValidInput();
            }
            shopInput.setText("");
        }
    };

    /**
     * This action listener is responsible for reading the user inputs in the textfield while they are on the item
     * area or have selected an item from the shop menu of the shop gui form
     */
    private final ActionListener shopItemListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = shopInput.getText().toUpperCase();
            switch (input) {
                case "B" -> buyItem();
                case "S" -> sellItem();
                case "R" -> { shopComboBox.setSelectedIndex(0); inMenu(); }
                default -> { staticMethods.notValidInput(); }
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

    /**
     * These methods help keep track of where the user is inside the shop and help manage the listeners to make sure
     * they dont duplicate or overlap.
     */
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

    /**
     * The enterBuyQuantity method is called from the buyItem method to ask the user how much of the item they want to
     * purchase. It also restricts and notifies the user from inputting a quantity that costs more than the money they
     * have to spend.
     * @param item the item the user wants to purchase from the shop
     * @param itemCost the cost per purchasable unit of the item the user wants to purchase
     * @return the quantity the user wants to purchase of the item
     */
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
                                            """, game.getMoney(), itemCost, item)));
                            if (quantity * itemCost > game.getMoney()) {
                                staticMethods.notEnoughMoney();
                            }
                } while (quantity * itemCost > game.getMoney());
            }
            catch (InputMismatchException e) {
                e.printStackTrace();
                staticMethods.notValidInput();
            }
        return quantity;
    }

    /**
     * The enterSellQuantity method is called from the sellItem method to ask the user how much of the item they want to
     * sell. It also restricts and notifies the user from inputting a quantity that is greater than the amount of
     * the item they have in their inventory.
     * @param item the item the user wants to sell to the shop
     * @return the quantity the user wants to sell of the item
     */
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
                    staticMethods.notEnoughItem(itemName);
                }
            } while (quantity > item);
        }
        catch (InputMismatchException e) {
            e.printStackTrace();
            staticMethods.notValidInput();
        }
        return quantity;
    }

    /**
     * The buyItem method reads the comboBox Index to realize which item the user is trying to act on in the shop. This
     * provides a corresponding string name of the item, and then calls the method checkBuy to confirm the quantity.
     */
    private void buyItem() {
        int index = shopComboBox.getSelectedIndex();
        String itemName = "";
        switch (index) {
            case 1 -> { itemName = "FOOD"; game.setFood(checkBuy(itemName, game.getFood(), foodBuyPrice)); } //food
            case 2 -> { itemName = "AMMUNITION"; game.setAmmunition(checkBuy(itemName, game.getAmmunition(), ammoBuyPrice));} //ammo
            case 3 -> { itemName = "MEDICINE"; game.setMedicine(checkBuy(itemName, game.getMedicine(), medBuyPrice)); } //meds
            case 4 -> { itemName = "CLOTHES"; game.setClothes(checkBuy(itemName, game.getClothes(), clothesBuyPrice)); } //clothes
            case 5 -> { itemName = "WAGON TOOLS"; game.setWagonTools(checkBuy(itemName, game.getWagonTools(), toolsBuyPrice)); } //wagonTools
            case 6 -> { itemName = "SPLINTS"; game.setSplints(checkBuy(itemName, game.getSplints(), splintBuyPrice)); } //splints
            case 7 -> { itemName = "OXEN"; game.setOxen(checkBuy(itemName, game.getOxen(), oxenBuyPrice)); } //oxen
            default -> { staticMethods.notValidInput(); }
        }
        shopComboBox.setSelectedIndex(0);
    }

    /**
     * The sellItem method reads the comboBox Index to realize which item the user is trying to act on in the shop. This
     * provides a corresponding string name of the item, and then calls the method checkSell to confirm the quantity.
     */
    private void sellItem() {
        int index = shopComboBox.getSelectedIndex();
        String itemName = "";
        switch (index) {
            case 1 -> { itemName = "FOOD"; game.setFood(checkSell(itemName, game.getFood())); } //food
            case 2 -> { itemName = "AMMUNITION"; game.setAmmunition(checkSell(itemName, game.getAmmunition())); } //ammo
            case 3 -> { itemName = "MEDICINE"; game.setMedicine(checkSell(itemName, game.getMedicine())); } //meds
            case 4 -> { itemName = "CLOTHES"; game.setClothes(checkSell(itemName, game.getClothes())); } //clothes
            case 5 -> { itemName = "WAGON TOOLS"; game.setWagonTools(checkSell(itemName, game.getWagonTools())); } //wagonTools
            case 6 -> { itemName = "SPLINTS"; game.setSplints(checkSell(itemName, game.getSplints())); } //splints
            case 7 -> { itemName = "OXEN"; game.setOxen(checkSell(itemName, game.getOxen())); } //oxen
            default -> { staticMethods.notValidInput(); }
        }
        shopComboBox.setSelectedIndex(0);
    }

    /**
     * The checkBuy method is responsible to make sure the constraints around the purchase are valid. After validating
     * the constraints using the enterBuyQuantity method. If the quantity entered is 0 the transaction is cancelled.
     * There is a confirmation popup window that displays the amount of money the user will be spending for the quantity
     * and name of the item.
     * @param itemName name of the item the user is purchasing
     * @param item the quantity of the item the user is trying to purchase
     * @param itemCost the cost of the item the user is trying to purchase
     * @return the integer quantity of the item the user purchases
     */
    private int checkBuy(String itemName, int item, int itemCost) {
        int quantity = enterBuyQuantity(itemName, itemCost);
        if ( quantity == 0 ) {
            transactionCancelled();
        }
        else {
            int buyPrice;
            switch (itemName) {
                case "FOOD" -> { buyPrice = foodBuyPrice; shopComboBox.setSelectedIndex(1); }
                case "AMMUNITION" -> { buyPrice = ammoBuyPrice; shopComboBox.setSelectedIndex(2); }
                case "MEDICINE" -> { buyPrice = medBuyPrice; shopComboBox.setSelectedIndex(3); }
                case "CLOTHES" -> { buyPrice = clothesBuyPrice; shopComboBox.setSelectedIndex(4); }
                case "WAGON TOOLS" -> { buyPrice = toolsBuyPrice; shopComboBox.setSelectedIndex(5); }
                case "SPLINTS" -> { buyPrice = splintBuyPrice; shopComboBox.setSelectedIndex(6); }
                case "OXEN" -> { buyPrice = oxenBuyPrice; shopComboBox.setSelectedIndex(7); }
                default -> { throw new RuntimeException("error in buying item"); }
            }
            int costOfPurchase = quantity * buyPrice;
            if (game.getMoney() < costOfPurchase) {
                staticMethods.notEnoughMoney();
            }
            else {
                boolean yn = confirmBuy(itemName, costOfPurchase, quantity);
                if (yn) {
                    game.setMoney(game.getMoney() - costOfPurchase);
                    item = item + quantity;
                    buyDialogue(itemName, costOfPurchase, quantity);
                }
                else {
                    transactionCancelled();
                }
            }
        }
        return item;
    }

    /**
     * The checkSell method is responsible to make sure the constraints around the selling of the item are valid. After
     * validating the constraints using the enterSellQuantity method. If the quantity entered is 0 the transaction is
     * cancelled. There is a confirmation popup window that displays the amount of money the user will be receiving for
     * the quantity and name of the item.
     * @param itemName name of the item the user is selling
     * @param item the quantity of the item the user is trying to sell
     * @return the integer quantity of the item the user sells
     */
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
        return item;
    }

    /**
     * The buyDialogue method displays a confirmation window to inform the user of the transaction they made
     * @param name name of the item
     * @param cost how much the item costs per unit
     * @param amount quantity of the item purchased
     */
    private void buyDialogue(String name, int cost, int amount) {
        if (name.equals("FOOD")) {
            amount *= 5;
        }
        JOptionPane.showMessageDialog(null,
                String.format("You've purchased %d units of %s for $%d.", amount, name, cost));
    }

    /**
     * The sellDialogue method displays a confirmation window to inform the user of the transaction they made
     * @param name name of the item
     * @param amount quantity of the item sold
     */
    private void sellDialogue(String name, int profit, int amount) {
        if (name.equals("FOOD")) {
            amount *= 5;
        }
        JOptionPane.showMessageDialog(null,
                String.format("You've sold %d units of %s for $%d", amount, name, profit));
    }

    /**
     * The confirmBuy method pops up a confirmation window that asks if the user is certain of the transaction they
     * want to make.
     * @param name name of the item
     * @param cost how much the item costs
     * @param amount quantity of the item the user is asking to purchase
     * @return true if the user selects the YES option in the JOptionPane to confirm, false if the user selected the
     *         NO option
     */
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

    /**
     * The transactionCancelled method pops up a dialogue to notify the user that the transaction was cancelled.
     */
    private void transactionCancelled() {
        JOptionPane.showMessageDialog(null, "The transaction was cancelled.","CANCELLED",
                JOptionPane.PLAIN_MESSAGE);
    }
}