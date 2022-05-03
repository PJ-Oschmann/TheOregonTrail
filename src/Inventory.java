import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Inventory extends JDialog {
    private JPanel contentPane;
    private JTextArea invInfo;
    private JLabel InventoryImageLabel;
    private JPanel mainPanel;
    private JPanel textPanel;
    private JComboBox<String> invComboBox;
    private JTextField userInput;
    private JLabel invInputLabel;
    private ArrayList<Character> characterArrayList;
    private int food, ammunition, medicine, clothes, wagonTools, splints, oxen, money, happiness;
    private final OregonTrailGUI game;
    private boolean inMenu, inItem, menuActionListener, itemActionListener;

    public Inventory(OregonTrailGUI game) {
        //instantiating private vars
        this.game = game;
        maximize();
        setGlobalVar();

        this.setTitle("INVENTORY");
        setContentPane(contentPane);
        setModal(true);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                userInput.requestFocusInWindow();
                displayMenu();
                userInput.addActionListener(menuInputListener);
                userInput.addFocusListener(inputHelp);
            }
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

        //Image goes here
        InventoryImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/Inventory.png"));

        invComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int input = invComboBox.getSelectedIndex();
                switch (input) {
                    case 0 -> { displayMenu(); inMenu(); }
                    case 1 -> { displayFood(food); inItem(); }
                    case 2 -> { displayAmmo(ammunition); inItem(); }
                    case 3 -> { displayMed(medicine);inItem();  }
                    case 4 -> { displayClothes(clothes); inItem(); }
                    case 5 -> { displayWT(wagonTools); inItem(); }
                    case 6 -> { displaySplints(splints); inItem(); }
                    case 7 -> { displayOxen(oxen); inItem(); }
                    default -> invInfo.setText("ERROR IN SWITCH");
                }
                if (inItem && !itemActionListener) {
                    userInput.removeActionListener(menuInputListener);
                    userInput.addActionListener(itemInputListener);
                    itemActionListener = true;
                    menuActionListener = false;
                }
                else if (inMenu && !menuActionListener) {
                    userInput.removeActionListener(itemInputListener);
                    userInput.addActionListener(menuInputListener);
                    menuActionListener = true;
                    itemActionListener = false;
                }
        }});

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(windowClose);

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private ActionListener menuInputListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = userInput.getText().toUpperCase();
            switch (input) {
                case "I" -> { displayMenu(); invComboBox.setSelectedIndex(0); }
                case "F" -> { displayFood(food); invComboBox.setSelectedIndex(1); }
                case "A" -> { displayAmmo(ammunition); invComboBox.setSelectedIndex(2); }
                case "M" -> { displayMed(medicine); invComboBox.setSelectedIndex(3); }
                case "C" -> { displayClothes(clothes); invComboBox.setSelectedIndex(4); }
                case "W" -> { displayWT(wagonTools); invComboBox.setSelectedIndex(5); }
                case "S" -> { displaySplints(splints); invComboBox.setSelectedIndex(6); }
                case "O" -> { displayOxen(oxen); invComboBox.setSelectedIndex(7); }
                default -> staticMethods.notValidInput();
            }
            userInput.setText("");
        }
    };
    private void maximize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setMinimumSize(new Dimension(screenSize.width-100,screenSize.height-100));
        int width = screenSize.width/2-this.getWidth()/2;
        int height = screenSize.height/2-this.getHeight()/2;
        this.setLocation(width, height);
    }

    private ActionListener itemInputListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = invComboBox.getSelectedIndex();
            switch (index) {
                case 1 -> {
                    if (userInput.getText().equalsIgnoreCase("U")) {
                        useFood();
                    } else if (userInput.getText().equalsIgnoreCase("I")) {
                        invComboBox.setSelectedIndex(0);
                    } else {
                        staticMethods.notValidInput();
                    }
                }
                case 2, 5 -> {
                    if (userInput.getText().equalsIgnoreCase("I")) {
                        invComboBox.setSelectedIndex(0);
                    } else {
                        staticMethods.notValidInput();
                    }
                }
                case 3 -> {
                    if (userInput.getText().equalsIgnoreCase("U")) {
                        useMedicine();
                    } else if (userInput.getText().equalsIgnoreCase("I")) {
                        invComboBox.setSelectedIndex(0);
                    } else {
                        staticMethods.notValidInput();
                    }
                }
                case 4 -> {
                    if (userInput.getText().equalsIgnoreCase("E")) {
                        equipClothes();
                    } else if (userInput.getText().equalsIgnoreCase("I")) {
                        invComboBox.setSelectedIndex(0);
                    } else {
                        staticMethods.notValidInput();
                    }
                }
                case 6 -> {
                    if (userInput.getText().equalsIgnoreCase("U")) {
                        useSplints();
                    } else if (userInput.getText().equalsIgnoreCase("I")) {
                        invComboBox.setSelectedIndex(0);
                    } else {
                        staticMethods.notValidInput();
                    }
                }
                case 7 -> {
                    if (userInput.getText().equalsIgnoreCase("C")) {
                        consumeOxen();
                    } else if (userInput.getText().equalsIgnoreCase("I")) {
                        invComboBox.setSelectedIndex(0);
                    }
                    else {
                        staticMethods.notValidInput();
                    }
                }
            }
            userInput.setText("");
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
        this.characterArrayList = game.getCharacterArrayList();
        this.happiness = game.getHappiness();
    }

    private void passBackVar() {
        game.setFood(this.food);
        game.setAmmunition(this.ammunition);
        game.setMedicine(this.medicine);
        game.setClothes(this.clothes);
        game.setWagonTools(this.wagonTools);
        game.setSplints(this.splints);
        game.setOxen(this.oxen);
        game.setMoney(this.money);
        game.setCharacterArrayList(this.game.getCharacterArrayList());
        game.setHappiness(this.happiness);
    }

    //TEXT METHODS and CANCEL METHOD
    private void displayMenu() {
        invInfo.setText(
                """
                WELCOME TO THE INVENTORY!
                
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
                
                I: RETURN TO THIS MENU
                
                Press ESC to exit the INVENTORY screen.
                """);
    }

    private void displayFood(int food) {
        invInfo.setText(String.format(
                """
                FOOD is a resource that prevents your party members
                from going HUNGRY. If the party has 0 units of FOOD
                for three days in a row, the game will end.
                
                Each unit of FOOD given to a party member will
                increase their food level by 2.
                
                You have %d units of FOOD.
                
                Enter "U" to use this item.
                Enter "I" to return to the INVENTORY menu.
                """, food
        ));
    }

    private void displayAmmo(int ammo) {
        invInfo.setText(String.format(
                """
                AMMUNITION is a consumable resource used in
                combination with one daily action to go HUNTING.

                One AMMUNITION box is consumed when your party goes
                hunting. HUNTING yields about double the food for
                its cost relative to buying food (on average).
                
                You have %d boxes of AMMUNITION.
                    
                Enter "I" to return to the INVENTORY menu.
                """, ammo
        ));
    }

    private void displayMed(int med) {
        invInfo.setText(String.format(
                """
                MEDICINE is a resource that cures your party members
                of illness. When a party member is ILL, their food level
                consumption is increased by 2 a day on top of the travel
                consumption. That party member will also lose 5 HP a day
                and party happiness is reduced by 2 for each member that is
                sick each day.
                                            
                One unit of MEDICINE can cure a single party member, and
                can only be used on a character who is ILL.
                
                You have %d units of MEDICINE.
                
                Enter "U" to use this item.
                Enter "I" to return to the INVENTORY menu.
                """, med
        ));
    }

    private void displayClothes(int clothes) {
        invInfo.setText(String.format(
                """
                CLOTHES are a one-time consumable resource that will
                protect your party members from weather for the remainder
                of their journey. If a character is not protected from
                extreme weather, they may fall ILL and lose health as a
                consequence.
                
                This resource can be produced on the trip using a total of
                3 daily actions to produce clothes. This item may only be
                used on characters who do not already have a set of CLOTHES.
                
                You have %d sets of CLOTHES.
                
                Enter "E" to equip this item onto a character.
                Enter "I" to return to the INVENTORY menu.
                """, clothes
        ));
    }

    private void displayWT(int wagonTools) {
        invInfo.setText(String.format(
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

                You have %d spare WAGON TOOLS.
                
                Enter "I" to return to the INVENTORY menu.
                """, wagonTools
        ));
    }

    private void displaySplints(int splints) {
        invInfo.setText(String.format(
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
                 
                You have %d SPLINTS.
                
                Enter "U" to use this item.
                Enter "I" to return to the INVENTORY menu.
                """, splints
        ));
    }

    private void displayOxen(int oxen) {
        invInfo.setText(String.format(
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
                                            
                You have %d OXEN.
                
                Enter "C" to slaughter and harvest an OXEN for 10 FOOD.
                Enter "I" to return to the INVENTORY menu.
                """, oxen
        ));
    }

    private void useFood() {
        if (food > 0) {
            openParty("FOOD");
        }
        else {
            staticMethods.notEnoughItem("FOOD");
        }
    }

    private void useMedicine() {
        if (medicine > 0) {
            openParty("MEDICINE");
        }
        else {
            staticMethods.notEnoughItem("MEDICINE");
        }
    }

    private void equipClothes() {
        if (clothes > 0) {
            openParty("CLOTHES");
        }
        else {
            staticMethods.notEnoughItem("CLOTHES");
        }
    }

    private void useSplints() {
        if (splints > 0) {
            openParty("SPLINTS");
        }
        else {
            staticMethods.notEnoughItem("SPLINTS");
        }
    }

    private void consumeOxen() {
        //TODO: ARE YOU SURE YOU WANT TO CONSUME AN OXEN DIALOGUE Y/N
        int reply = JOptionPane.showConfirmDialog(null, "Would you like to consume an oxen?\nYou " +
                "must have at least 5 oxen to consume 1.", "Consume an Oxen", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION && oxen > 4) {
            int happinessLost;
            oxen -= 1;
            food += 10;
            if(happiness >= 7) { happinessLost = 7; happiness -= 7; }
            else { happinessLost = happiness; happiness = 0; }

            String oxenName = ReadText.generateOxenName();
            JOptionPane.showMessageDialog(null, String.format(
                    """
                    You have chosen to CONSUME one OXEN. You have gained 10 FOOD.
                    Rest in peace(s of food), %s.
                    
                    %s will be missed dearly, and your party lose %d happiness.
                    """, oxenName, oxenName, happinessLost
            ), String.format("RIP %s", oxenName), JOptionPane.INFORMATION_MESSAGE);
        }
        else if (reply == JOptionPane.YES_OPTION && oxen <= 4) {
            staticMethods.notEnoughItem("OXEN");
        }
        else if (reply == JOptionPane.NO_OPTION){
            JOptionPane.showMessageDialog(null, "You did not consume an OXEN.\nGood for you.",
                    "Your oxen live another day", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private FocusAdapter inputHelp = new FocusAdapter() { //Grey text for input box when not focused on
        @Override
        public void focusGained(FocusEvent e) {
            userInput.setText("");
            userInput.setForeground(Color.BLACK);
        }

        @Override
        public void focusLost(FocusEvent e) {
            userInput.setText("Input Option Here");
            userInput.setForeground(new Color(147, 147,147));
        }
    };

    private final WindowAdapter windowClose = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            onCancel();
        }
    };

    private void onCancel() {
        passBackVar();
        dispose();
    }

    private void openParty(String item) {
        Party party = new Party(game, item);
        party.pack();
        party.setTitle("PARTY DETAILS");
        party.setVisible(true);
    }

    private void inMenu() {
        inMenu = true;
        inItem = false;
    }

    private void inItem() {
        inItem = true;
        inMenu = false;
    }
}
