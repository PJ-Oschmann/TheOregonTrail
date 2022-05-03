import javax.management.RuntimeErrorException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class RandomEventGUI extends JDialog {
    private JPanel contentPane;
    private JTextField inputField;
    private JPanel inputPanel;
    private JPanel textPanel;
    private JPanel imagePanel;
    private JLabel imageLabel;
    private JTextPane promptPane;
    private final OregonTrailGUI game;

    private int clothesAmt = 8;
    private int ammoAmt = 2;
    private int foodAmt = 2;
    private int medsAmt = 2;
    private int splintAmt = 5;
    private int toolsAmt = 8;
    private Object traderAsk;
    private int halfOff;
    private ArrayList<String> nameArrayList = new ArrayList<>(List.of("Felicia","Mia","Kristin","Katrina","Janet",
            "Almudena","Chika","Mary","Nicole","Jessica","Maxine","Stephany","Kendra","Kendall","Kenifer","Elise",
            "Anna","Lizzy","Minnie","Ida","Florence","Martha","Nellie","Lena","Agnes","Candace","Jane","April", "Jordan",
            "Skyler","Sonia","Joanne","Crystal","Melissa","Amy","Sharron","Kelly","Shelly","Chrysanthemum","Ally",
            "Sally","Maria"));
    private ArrayList itemArrayList;
    private int tradeAmt;
    private String tradeItem;

    private int tradeGiveAmt;
    private int food, ammunition, medicine, clothes, wagonTools, splints, oxen, money, happiness;
    private ArrayList<Character> characterArrayList;
    private boolean isStreamAL = false, isEncounterAL = false, isCloseAL = false,  isNativeAL = false;

    public RandomEventGUI(OregonTrailGUI game) {
        this.game = game;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        inputField.addFocusListener(inputHelp);
    }

    public void checkForRandomEvent() {
        int n = game.rand.nextInt(9);
        if (n == 0) {
            setGlobalVar();
            setContentPane(contentPane);
            setModal(true);

            String event = eventName();
            this.setTitle("RANDOM EVENT");
            doEvent(event);

            this.setUndecorated(true);
            this.pack();
            this.setVisible(true);
        }
    }

    public void forceRandomEvent() {
        setGlobalVar();
        setContentPane(contentPane);
        setModal(true);

        String event = "encounterTravelers"; //insert test event here
        this.setTitle("FORCED EVENT");
        doEvent(event);

        this.pack();
        this.setVisible(true);
    }

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

    private void onCancel() {
        closeActionListeners();
        passBackVar();
        dispose();
    }

    int eventChance(int happiness) {
        if (happiness < 75 && happiness > 25) {
            return 1; //1=neutral
        } else if (happiness >= 75) {
            return 2; //2 is happy
        } else {
            return 0; //0 is sad
        }
    }

    boolean eventType(int mood) {
        int temp;
        boolean isGood;
        if (mood == 1) {
            temp = game.rand.nextInt(2); //0 is bad 1 is good
            if (temp == 1) {
                isGood = true;
            } else {
                isGood = false;
            }
        } else if (mood == 2) {
            temp = game.rand.nextInt(4);
            if (temp == 0) {
                isGood = false;
            } else {
                isGood = true;
            }
        } else {
            temp = game.rand.nextInt(4);
            if (temp == 0) {
                isGood = true;
            } else {
                isGood = false;
            }
        }
        return isGood;
    }

    String eventName() {
        boolean isGood = eventType(eventChance(happiness));
        int temp;
        if (isGood) {
            temp = game.rand.nextInt(4);
            if (temp == 0) {
                return "encounterTraveler"; //player encounters another traveler
            } else if (temp == 1) {
                return "smallStream"; //player encounters
            } else if (temp == 2) {
                return "wagonFound"; //found abandoned wagon
            }
            else {
                return "nativeAmericanEncounter";
            }
        }
        else {
            temp = game.rand.nextInt(4);
            if (temp == 0) {
                return "injury"; //random party member is injured
            } else if (temp == 1) {
                return "wagonDamage"; //wagon is damaged during the travels
            } else if (temp == 2) {
                return "foodSpoiled"; //some food spoils
            } else {
                return "illness"; //random party member falls ill
            }
        }
    }

    public void doEvent(String event) {
        ArrayList<Character> characterArrayList = game.getCharacterArrayList();
        int characterIndex = game.rand.nextInt(4);

        //Good events
        switch (event) {
            case "encounterTraveler" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/encounterTraveler.png"));
                inputField.addActionListener(encounterAL);
                encounterTraveler();
            }
            case "smallStream" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/smallStream.png"));
                inputField.addActionListener(streamAL);
                promptPane.setText("""
                        You found a small stream!
                                        
                        S: SWIM (Happiness++)
                        F: FISHING (Food++)
                        """);
            }
            case "wagonFound" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/abandonedWagon.png"));
                int dollars = wagonFound("D");
                int ammo = wagonFound("A");
                int clothes = wagonFound("C");
                int splints = wagonFound("S");
                int wTools = wagonFound("W");
                promptPane.setText(String.format(
                        """
                                Your party has stumbled upon an abandoned wagon on the
                                side of the road. You find some resources after scavenging 
                                around the area!
                                                        
                                YOU FOUND:
                                %d DOLLARS
                                %d boxes of AMMUNITION
                                %d sets of CLOTHING
                                %d usable SPLINTS
                                %d sets of WAGON TOOLS
                                                        
                                Press "C" to continue on your journey.
                                """, dollars, ammo, clothes, splints, wTools
                ));
                game.calculateHappiness(5);
                inputField.addActionListener(closeAL);
            }
            case "nativeAmericanEncounter" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/encounterNative.png"));
                promptPane.setText(
                        """
                        You encounter a Native American traveling to Oklahoma in
                        search of a new home. They ask for any supplies you can spare to
                        help them with their journey.
                        
                        C: CONTINUE
                        """
                );
                inputField.addActionListener(nativeAL);
            }

            //Bad events
            case "injury" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/injury.png"));
                if (!characterArrayList.get(characterIndex).isInjured()) {
                    characterArrayList.get(characterIndex).setInjured(true);
                    promptPane.setText(characterArrayList.get(characterIndex).getName() + " got injured. Press \"C\" to continue.");
                    inputField.addActionListener(closeAL);
                } else {
                    characterArrayList.get(characterIndex).setInjured(true);
                    promptPane.setText(characterArrayList.get(characterIndex).getName() + " got injured again. Press \"C\" to continue.");
                    inputField.addActionListener(closeAL);
                }
            }
            case "wagonDamage" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/wagonDamage.png"));
                game.calculateHappiness(-5);
                game.wagon.setState(game.wagon.getState() + 1);
                if (game.wagon.getState() == 0) {
                    game.checkIfLost();
                }
                promptPane.setText("As you traveled your wagon hit a rock and became damaged. Everyone is saddened. Press " +
                        "\"C\" to continue.");
                inputField.addActionListener(closeAL);
            }
            case "foodSpoiled" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/spoiledFood.png"));
                double spoiledFoodDb = game.getFood() * .2;
                int spoiledFood = (int) spoiledFoodDb;
                game.calculateFood(-spoiledFood);
                promptPane.setText("Some of your food spoiled. You lost " + spoiledFood + " food. Press \"C\" to continue.");
                inputField.addActionListener(closeAL);
            }
            case "illness" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/illness.png"));
                if (!characterArrayList.get(characterIndex).isSick()) {
                    promptPane.setText(characterArrayList.get(characterIndex).getName() + " has fallen sick! Press \"C\" to continue.");
                    characterArrayList.get(characterIndex).setSick(true);
                    inputField.addActionListener(closeAL);
                }
            }
            default -> throw new RuntimeException("Something bad happened in the doEvent method");
        }
    }

    private int wagonFound(String i) {
        switch (i.toUpperCase()) {
            case "D" -> { return game.rand.nextInt(13); }
            case "A" -> { return game.rand.nextInt(3); }
            case "C", "S", "W" -> { return game.rand.nextInt(2); }
            default -> throw new RuntimeException("There was an error with wagonFound method in RandomEvent class");
        }
    }

    int randName1;
    int randName2;
    private void genNames() {
        randName1 = game.rand.nextInt(nameArrayList.size());
        randName2 = game.rand.nextInt(nameArrayList.size());
        if (randName2==randName1) {
            genNames();
        }
    }
    public void encounterTraveler() {
        genNames();
        promptPane.setText(String.format(
                """
                You encounter the travelers %s and %s. What would you like to do?
                
                T: TRADE FOR NEEDED RESOURCES
                S: SHARE STORIES BY THE CAMPFIRE
                
                You can enter "T" to trade for resources you are low on.
                You can enter "S" to share stories of your travels and increase party happiness.
                """, nameArrayList.get(randName1), nameArrayList.get(randName2)
        ));
        inputField.addActionListener(encounterAL);
    }

    private void trade(int step) {
        resetTraderItems();
        traderAsk = itemArrayList.get(game.rand.nextInt(8));
    }

    private void resetTraderItems() {
        itemArrayList = new ArrayList<>(List.of("money", "clothes", "ammunition","food","medicine",
                "splints","tools", "oxen"));
    }

    private void resetNAItems() {
        itemArrayList = new ArrayList<>(List.of("money", "clothes", "ammunition","food","medicine"));
    }

    private void removeItemfromList(String i) {
        try {
            switch (i.toUpperCase()) {
                case "MONEY" -> itemArrayList.remove(0);
                case "CLOTHES" -> itemArrayList.remove(1);
                case "AMMO" -> itemArrayList.remove(2);
                case "FOOD" -> itemArrayList.remove(3);
                case "MEDICINE" -> itemArrayList.remove(4);
                case "SPLINTS" -> itemArrayList.remove(5);
                case "TOOLS" -> itemArrayList.remove(6);
                case "OXEN" -> itemArrayList.remove(7);
                default -> staticMethods.notValidInput();
            }
        }
        catch (RuntimeErrorException e) {
            throw new RuntimeException("RandomEventGUI removing item from list in trader encounter broken.");
        }
    }

    private final ActionListener encounterAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            isEncounterAL = true;
            String i = inputField.getText().toUpperCase();
            switch (i) {
                case "T" -> {} //trade here
                case "S" -> { shareStories(); onCancel(); }
                default -> staticMethods.notValidInput();
            }
        }
    };

    private final ActionListener nativeAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            isNativeAL = true;
            if (inputField.getText().equalsIgnoreCase("C")) {
                resetNAItems();
                int itemIndex = game.rand.nextInt(itemArrayList.size());
                String item = "";
                int ask = 0, have = 0;
                switch (itemIndex) {
                    case 0 -> { item = "DOLLARS"; ask = game.rand.nextInt(6) + 8; have = game.getMoney(); }
                    case 1 -> { item = "CLOTHES"; ask = game.rand.nextInt(1) + 1; have = game.getClothes(); }
                    case 2 -> { item = "AMMUNITION"; ask = game.rand.nextInt(4) + 2; have = game.getAmmunition(); }
                    case 3 -> { item = "FOOD"; ask = game.rand.nextInt(11) + 10; have = game.getFood(); }
                    case 4 -> { item = "MEDICINE"; ask = game.rand.nextInt(4) + 2; have = game.getMedicine(); }
                }

                promptPane.setText(String.format(
                        """
                        They ask if you will donate %d %s out of goodwill to help them
                        out on their relocation.
                        
                        If you donate some supplies, you and your party will gain a
                        significant amount of happiness.
                        
                        You have %d %s in your inventory.
                        
                        D: DONATE
                        A: APOLOGIZE
                        """, ask, item, have, item
                ));
                nativeAL2(ask, itemIndex, have);
                inputField.removeActionListener(nativeAL);
            }
        }
    };
    private void nativeAL2(int ask, int itemInd, int have) {
        String item = String.valueOf(itemArrayList.get(itemInd));
        inputField.addActionListener(event -> {
            String in = inputField.getText().toUpperCase();
            switch (in) {
                case "D" -> {
                    if (have >= ask) {
                        switch (itemInd) {
                            case 0 -> money -= ask;
                            case 1 -> clothes -= ask;
                            case 2 -> ammunition -= ask;
                            case 3 -> food -= ask;
                            case 4 -> medicine -= ask;
                        }
                        happiness += 20;
                        JOptionPane.showMessageDialog(null, String.format(
                                """
                                You gather some %s from your belongings that you have
                                to spare to another fellow traveler. After sharing some
                                brief conversation, they express great gratitude towards
                                your kind gesture and you feel warm-hearted.
                                
                                HAPPINESS INCREASED BY 20.
                                """, item), "GOOD SAMARITAN", JOptionPane.INFORMATION_MESSAGE);
                        onCancel();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "You don't have enough " +
                                item + "to help them out.\nYou can " + "apologize and wish them luck and move on " +
                                "instead.", "NOT ENOUGH " + item, JOptionPane.ERROR_MESSAGE);
                    }
                }
                case "A" -> { promptPane.setText(
                        """
                        You feel great sympathy towards the struggling Native American
                        who is also in the process of a cross-country pilgrimage. You
                        apologize and wish them the best on their travels.
                        """);
                    onCancel();
                }
                default -> staticMethods.notValidInput();
            }
        });
    }

    private void closeActionListeners(){
        if (isCloseAL) {
            inputField.removeActionListener(closeAL);
            isCloseAL = false;
        }
        if (isEncounterAL) {
            inputField.removeActionListener(encounterAL);
            isEncounterAL = false;
        }
        if (isStreamAL) {
            inputField.removeActionListener(streamAL);
            isStreamAL = false;
        }
        if (isNativeAL) {
            inputField.removeActionListener(nativeAL);
            isNativeAL = false;
        }
    }

    private final ActionListener closeAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            isCloseAL = true;
            if (inputField.getText().equalsIgnoreCase("C")) {
                passBackVar();
                dispose();
            }
        }
    };

    private final ActionListener streamAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            isStreamAL = true;
            if (inputField.getText().equalsIgnoreCase("S")) {
                inputField.setText("");
                swim();
            }
            else if (inputField.getText().equalsIgnoreCase("F")) {
                inputField.setText("");
                fish();
            }
            else {
                inputField.setText("");
                staticMethods.notValidInput();
            }
        }
    };

    private void swim() {
        ArrayList<Character> characterArrayList = game.getCharacterArrayList();
        int characterIndex = game.rand.nextInt(characterArrayList.size());
        Character character = characterArrayList.get(characterIndex);
        if (game.rand.nextInt(9)==0) {
            character.setInjured(true);
            promptPane.setText("How unlucky! " + character.getName()+" got injured swimming. \nPress \"C\" to continue.");
        }
        else {
            promptPane.setText("Your party had a great time splashing around the stream. Your happiness increased by " +
                    "20! \nPress \"C\" to continue.");
            game.calculateHappiness(20);
        }
        inputField.removeActionListener(streamAL);
        inputField.addActionListener(closeAL);
    }

    private void fish() {
        int fishAmount;
        fishAmount = game.rand.nextInt(10);
        inputField.removeActionListener(streamAL);
        inputField.addActionListener(closeAL);
        promptPane.setText("You caught " + fishAmount + " fish! Press \"C\" to continue.");

    }

    private void shareStories() {
        JOptionPane.showMessageDialog(null,"You had a jolly old time sharing stories and earned " +
                "10 happiness!","Share Stories",JOptionPane.PLAIN_MESSAGE);
        game.setHappiness(game.calculateHappiness(10));
    }

    private final FocusAdapter inputHelp = new FocusAdapter() { //Grey text for input box when not focused on
        @Override
        public void focusGained(FocusEvent e) {
            inputField.setText("");
            inputField.setForeground(Color.BLACK);
        }

        @Override
        public void focusLost(FocusEvent e) {
            inputField.setText("Input Option Here");
            inputField.setForeground(new Color(147, 147,147));
        }
    };
}
