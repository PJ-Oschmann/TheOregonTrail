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
    private ArrayList<String> nameArrayList = new ArrayList<>(List.of("Felicia","Mia","Kristin","Katrina","Janet",
            "Almudena","Chika","Mary","Nicole","Jessica","Maxine","Stephany","Kendra","Kendall","Kenifer","Elise",
            "Anna","Lizzy","Minnie","Ida","Florence","Martha","Nellie","Lena","Agnes","Candace","Jane","April", "Jordan",
            "Skyler","Sonia","Joanne","Crystal","Melissa","Amy","Sharron","Kelly","Shelly","Chrysanthemum","Ally",
            "Sally","Maria"));
    private ArrayList itemArrayList;
    private int food, ammunition, medicine, clothes, wagonTools, splints, oxen, money, happiness;
    private ArrayList<Character> characterArrayList;
    private int randName1, randName2;
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
                return "encounterTravelers"; //player encounters another traveler
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
        int ind;
        do {
            ind = game.rand.nextInt(4);
        } while (characterArrayList.get(ind).getIsDead());

        //Good events
        switch (event) {
            case "encounterTravelers" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/encounterTraveler.png"));
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
                if (!characterArrayList.get(ind).isInjured()) {
                    characterArrayList.get(ind).setInjured(true);
                    promptPane.setText(characterArrayList.get(ind).getName() + " got injured.\nPress \"C\" to continue.");
                    inputField.addActionListener(closeAL);
                } else {
                    characterArrayList.get(ind).setInjured(true);
                    promptPane.setText(characterArrayList.get(ind).getName() + " got injured again.\nPress \"C\" to " +
                            "continue.");
                    inputField.addActionListener(closeAL);
                }
                game.checkNewDeaths();
                game.checkIfLost();
            }
            case "wagonDamage" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/wagonDamage.png"));
                game.calculateHappiness(- 5);
                game.wagon.setState(game.wagon.getState() + 1);
                if (game.wagon.getState() == 2) {
                    game.checkIfLost();
                }
                promptPane.setText("As you traveled your wagon hit a rock and became damaged. Everyone is saddened.\n" +
                        "Press \"C\" to continue.");
                inputField.addActionListener(closeAL);
            }
            case "foodSpoiled" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/spoiledFood.png"));
                double spoiledFoodDb = game.getFood() * .2;
                int spoiledFood = (int) spoiledFoodDb;
                game.calculateFood(-spoiledFood);
                promptPane.setText("Some of your food spoiled. You lost " + spoiledFood + " food.\nPress \"C\" " +
                        "to continue.");
                inputField.addActionListener(closeAL);
            }
            case "illness" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/illness.png"));
                if (!characterArrayList.get(ind).isSick()) {
                    promptPane.setText(characterArrayList.get(ind).getName() + " has fallen sick!\nPress \"C\" " +
                            "to continue.");
                    characterArrayList.get(ind).setSick(true);
                    inputField.addActionListener(closeAL);
                }
                else {
                    promptPane.setText(characterArrayList.get(ind).getName() + "has relapsed into their illness!\n" +
                            "Press \"C\" to continue.");
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

    private void resetNAItems() {
        itemArrayList = new ArrayList<>(List.of("money", "clothes", "ammunition","food","medicine"));
    }

    private final ActionListener encounterAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputField.addFocusListener(inputHelp);
            isEncounterAL = true;
            String i = inputField.getText().toUpperCase();
            inputField.removeActionListener(encounterAL);
            switch (i) {
                case "T" -> initiateTrade();
                case "S" -> {  shareStories(); onCancel(); }
                default -> staticMethods.notValidInput();
            }
            inputField.setText("");
        }
    };

    private void initiateTrade() {
        promptPane.setText(
                """
                The travelers you encountered are generous enough to
                offer a beneficial trade for a resource you may need
                more of. Enter the resource you want more of:
                
                F: FOOD
                A: AMMO
                M: MEDICINE
                C: CLOTHES
                W: WAGON TOOLS
                S: SPLINTS
                O: OXEN
                
                R: CANCEL THE TRADE
                """
        );
        inputField.addActionListener(e -> {
            String in = inputField.getText().toUpperCase();
            inputField.setText("");
            int r = game.rand.nextInt(7);
            int itemCount = 0;
            String item = "";
            switch (in) {
                case "F" -> {
                    switch (r) { //$12
                        case 0 -> { item = "MONEY"; itemCount = 6; }
                        case 1 -> { item = "AMMO"; itemCount = 2; }
                        case 2 -> { item = "MEDICINE"; itemCount = 2; }
                        case 3 -> { item = "CLOTHES"; itemCount = 1; }
                        case 4 -> { item = "WAGON TOOLS"; itemCount = 1;}
                        case 5 -> { item = "SPLINTS"; itemCount = 1; }
                        case 6 -> { item = "OXEN"; itemCount = 1; }
                    }
                    promptPane.setText(String.format(
                            """
                            The other party is happy to provide you with some food.
                            They offer you 20 units of food for %d %s.
                            
                            Y: ACCEPT THE TRADE
                            N: DECLINE THE TRADE
                            """, itemCount, item
                    ));
                    makeTrade(item, itemCount, "FOOD", 20);
                }
                case "A" -> {
                    switch (r) { //$18
                        case 0 -> { item = "MONEY"; itemCount = 10; }
                        case 1 -> { item = "FOOD"; itemCount = 15; }
                        case 2 -> { item = "MEDICINE"; itemCount = 4; }
                        case 3 -> { item = "CLOTHES"; itemCount = 1; }
                        case 4 -> { item = "WAGON TOOLS"; itemCount = 1;}
                        case 5 -> { item = "SPLINTS"; itemCount = 1; }
                        case 6 -> { item = "OXEN"; itemCount = 1; }
                    }
                    promptPane.setText(String.format(
                            """
                            The other party is happy to provide you with some AMMUNITION.
                            They offer you 6 boxes of AMMUNITION for %d %s.
                            
                            Y: ACCEPT THE TRADE
                            N: DECLINE THE TRADE
                            """, itemCount, item
                    ));
                    makeTrade(item, itemCount, "AMMUNITION", 6);
                }
                case "M" -> {
                    switch (r) { //$15
                        case 0 -> { item = "MONEY"; itemCount = 9; }
                        case 1 -> { item = "FOOD"; itemCount = 15; }
                        case 2 -> { item = "AMMO"; itemCount = 4; }
                        case 3 -> { item = "CLOTHES"; itemCount = 1; }
                        case 4 -> { item = "WAGON TOOLS"; itemCount = 1;}
                        case 5 -> { item = "SPLINTS"; itemCount = 1; }
                        case 6 -> { item = "OXEN"; itemCount = 1; }
                    }
                    promptPane.setText(String.format(
                            """
                            The other party is happy to provide you with some MEDICINE.
                            They offer you 5 vials of MEDICINE for %d %s.
                            
                            Y: ACCEPT THE TRADE
                            N: DECLINE THE TRADE
                            """, itemCount, item
                    ));
                    makeTrade(item, itemCount, "MEDICINE", 5);
                }
                case "C" -> {
                    switch (r) { //$20
                        case 0 -> { item = "MONEY"; itemCount = 12; }
                        case 1 -> { item = "FOOD"; itemCount = 20; }
                        case 2 -> { item = "AMMO"; itemCount = 5; }
                        case 3 -> { item = "MEDICINE"; itemCount = 4; }
                        case 4 -> { item = "WAGON TOOLS"; itemCount = 1;}
                        case 5 -> { item = "SPLINTS"; itemCount = 2; }
                        case 6 -> { item = "OXEN"; itemCount = 1; }
                    }
                    promptPane.setText(String.format(
                            """
                            The other party is happy to provide you with some CLOTHES.
                            They offer you 2 sets of CLOTHES for %d %s.
                            
                            Y: ACCEPT THE TRADE
                            N: DECLINE THE TRADE
                            """, itemCount, item
                    ));
                    makeTrade(item, itemCount, "CLOTHES", 2);
                }
                case "W" -> {
                    switch (r) { // $20
                        case 0 -> { item = "MONEY"; itemCount = 14; }
                        case 1 -> { item = "FOOD"; itemCount = 22; }
                        case 2 -> { item = "AMMO"; itemCount = 5; }
                        case 3 -> { item = "MEDICINE"; itemCount = 4; }
                        case 4 -> { item = "CLOTHES"; itemCount = 1;}
                        case 5 -> { item = "SPLINTS"; itemCount = 2; }
                        case 6 -> { item = "OXEN"; itemCount = 1; }
                    }
                    promptPane.setText(String.format(
                            """
                            The other party is happy to provide you with some WAGON TOOLS.
                            They offer you 2 sets of WAGON TOOLS for %d %s.
                            
                            Y: ACCEPT THE TRADE
                            N: DECLINE THE TRADE
                            """, itemCount, item
                    ));
                    makeTrade(item, itemCount, "WAGON TOOLS", 2);
                }
                case "S" -> {
                    switch (r) { //$24
                        case 0 -> { item = "MONEY"; itemCount = 15; }
                        case 1 -> { item = "FOOD"; itemCount = 25; }
                        case 2 -> { item = "AMMO"; itemCount = 4; }
                        case 3 -> { item = "MEDICINE"; itemCount = 6; }
                        case 4 -> { item = "CLOTHES"; itemCount = 2;}
                        case 5 -> { item = "WAGON TOOLS"; itemCount = 2; }
                        case 6 -> { item = "OXEN"; itemCount = 1; }
                    }
                    promptPane.setText(String.format(
                            """
                            The other party is happy to provide you with some SPLINTS.
                            They offer you 3 SPLINTS for %d %s.
                            
                            Y: ACCEPT THE TRADE
                            N: DECLINE THE TRADE
                            """, itemCount, item
                    ));
                    makeTrade(item, itemCount, "SPLINTS", 3);
                }
                case "O" -> {
                    switch (r) { //$20
                        case 0 -> { item = "MONEY"; itemCount = 12; }
                        case 1 -> { item = "FOOD"; itemCount = 23; }
                        case 2 -> { item = "AMMO"; itemCount = 4; }
                        case 3 -> { item = "MEDICINE"; itemCount = 4; }
                        case 4 -> { item = "CLOTHES"; itemCount = 2;}
                        case 5 -> { item = "WAGON TOOLS"; itemCount = 1; }
                        case 6 -> { item = "SPLINTS"; itemCount = 2; }
                    }
                    promptPane.setText(String.format(
                            """
                            The other party is happy to provide you with some OXEN.
                            They offer you 2 OXEN for %d %s.
                            
                            Y: ACCEPT THE TRADE
                            N: DECLINE THE TRADE
                            """, itemCount, item
                    ));
                    makeTrade(item, itemCount, "OXEN",2);
                }
                case "R" -> {
                    tradeCancelled();
                }
                default -> staticMethods.notValidInput();
            }
            inputField.removeActionListener((ActionListener) e);
        });
    }

    private int checkQuantity(String n) {
        int q = 0;
        switch (n) {
            case "MONEY" -> { q = money; }
            case "FOOD" -> { q = food; }
            case "AMMUNITION" -> { q = ammunition; }
            case "MEDICINE" -> { q = medicine;}
            case "CLOTHES" -> { q = clothes; }
            case "WAGON TOOLS" -> { q = wagonTools; }
            case "SPLINTS" -> { q = splints; }
            case "OXEN" -> { q = oxen; }
        }
        return q;
    }

    private void setQuantity(String n, int amt, String get, int received) {
        switch (n) {
            case "MONEY" -> { money -= amt; }
            case "FOOD" -> { food -= amt; }
            case "AMMUNITION" -> { ammunition -= amt; }
            case "MEDICINE" -> { medicine -= amt; }
            case "CLOTHES" -> { clothes -= amt; }
            case "WAGON TOOLS" -> { wagonTools -= amt; }
            case "SPLINTS" -> { splints -= amt; }
            case "OXEN" -> { oxen -= amt; }
        }
        switch (get) {
            case "FOOD" -> { food += 20; }
            case "AMMUNITION" -> { ammunition += 6; }
            case "MEDICINE" -> { medicine += 5; }
            case "CLOTHES" -> { clothes += 2; }
            case "WAGON TOOLS" -> { wagonTools += 2; }
            case "SPLINTS" -> { splints += 3; }
            case "OXEN" -> { oxen += 2; }
        }
        tradeSuccessful(n, amt, get, received);
    }

    private void tradeSuccessful(String name, int amount, String getItem, int received) {
        JOptionPane.showMessageDialog(null, String.format(
                """
                You successfully exchanged:
                %d %s for %d %s
                
                You thank the travelers for their time and help
                and wish them safety for their future endeavors.
                """, amount, name, received, getItem
        ), "TRADE SUCCESS", JOptionPane.INFORMATION_MESSAGE);
        onCancel();
    }

    private void makeTrade(String n, int amt, String get, int received) {
        inputField.addActionListener(e2 -> {
            String yn = inputField.getText().toUpperCase();
            inputField.removeActionListener((ActionListener) e2);
            switch (yn) {
                case "Y" -> {
                    if (checkQuantity(n) >= amt) {
                        setQuantity(n, amt, get, received);
                    }
                    else {
                        staticMethods.notEnoughItem(n);
                        tradeCancelled();
                    }
                }
                case "N" -> tradeCancelled();
                default -> throw new RuntimeException("pls just make the trade");
            }
        });
    }

    private void tradeCancelled() {
        JOptionPane.showMessageDialog(null, "You thank the other travelers for their " +
                        "time and wish them safety during their journey.", "TRADE CANCELLED",
                JOptionPane.PLAIN_MESSAGE);
        onCancel();
    }

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
        int ind;
        if (game.rand.nextInt(9)==0) {
            do {
                ind = game.rand.nextInt(5);
            } while (characterArrayList.get(ind).getIsDead());
            Character character = characterArrayList.get(ind);
            character.setInjured(true);
            promptPane.setText("How unlucky! " + character.getName()+" got injured swimming.\nHe also took some damage as" +
                    "a result.\nPress \"C\" to continue.");
            game.checkNewDeaths();
            game.checkIfLost();
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
        JOptionPane.showMessageDialog(null,"You had a wonderful time sharing stories and earned " +
                "10 happiness!","Share Stories",JOptionPane.PLAIN_MESSAGE);
        happiness += 10;
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
