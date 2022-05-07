/**
 * The RandomEventGUI class and dialogue form is created in the game to manage and handle the random events that
 * occur while party is traveling across the country.
 */

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
    private final ArrayList<String> travelerNameList = new ArrayList<>(List.of("Felicia","Mia","Kristin","Katrina","Janet",
            "Almudena","Chika","Mary","Nicole","Jessica","Maxine","Stephany","Kendra","Kendall","Kennifer","Elise",
            "Anna","Lizzy","Minnie","Ida","Florence","Martha","Nellie","Lena","Agnes","Candace","Jane","April", "Jordan",
            "Skyler","Sonia","Joanne","Crystal","Melissa","Amy","Sharron","Kelly","Shelly","Chrysanthemum","Ally",
            "Sally","Maria","Brittney"));
    private ArrayList itemArrayList;

    private int randName1, randName2;
    private boolean isStreamAL = false, isEncounterAL = false, isCloseAL = false,  isNativeAL = false;

    public RandomEventGUI(OregonTrailGUI game) {
        this.game = game;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        inputField.addFocusListener(inputHelp);
    }

    /**
     * There is a 10% chance of a random event triggering each day. The checkForRandomEvent method determines whether
     * a random event happens that day or not. If it happens, then the form is made visible with a randomly generated
     * random event.
     */
    public void checkForRandomEvent() {
        int n = game.rand.nextInt(9);
        if (n == 0) {
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

    /**
     * The forceRandomEvent method is used for debugging each of the random events by forcing a particular random event
     * during the game to test.
     */
    public void forceRandomEvent() {
        setContentPane(contentPane);
        setModal(true);

        String event = "nativeAmericanEncounter"; //insert test event here
        this.setTitle("FORCED EVENT");
        doEvent(event);

        this.pack();
        this.setVisible(true);
    }

    /**
     * The method called to close existing ActionListeners and dispose of the random event window once it concluded.
     */
    private void onCancel() {
        closeActionListeners();
        dispose();
    }

    /**
     * The eventChance method returns the mood of the party when calculating the chance of a good or bad event occurring.
     * @param happiness is the overall party happiness
     * @return the mood value as sad, neutral, or happy (0/1/2 respectively)
     */
    private int eventChance(int happiness) {
        if (happiness < 75 && happiness > 25) {
            return 1; //1=neutral
        } else if (happiness >= 75) {
            return 2; //2 is happy
        } else {
            return 0; //0 is sad
        }
    }

    /**
     * The eventType method calculates whether the random event that occurs is a good or bad one. It uses the party mood
     * to determine the probabilities of such.
     * @param mood the mood of the party.
     * @return true for a good event and false for a bad one.
     */
    private boolean eventType(int mood) {
        int temp;
        boolean isGood;
        if (mood == 1) {
            temp = game.rand.nextInt(2);
            isGood = temp == 1;
        } else if (mood == 2) {
            temp = game.rand.nextInt(4);
            isGood = temp != 0;
        } else {
            temp = game.rand.nextInt(4);
            isGood = temp == 0;
        }
        return isGood;
    }

    /**
     * The eventName method generates the exact event occurring depending on whether it is good or bad at an equal
     * probability occurrence.
     * @return String name of the exact event that is occurring to the party
     */
    private String eventName() {
        boolean isGood = eventType(eventChance(game.getHappiness()));
        int temp;
        temp = game.rand.nextInt(4);
        if (isGood) {
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

    /**
     * The doEvent method calls the method corresponding to the event name passed into the method.
     * @param event the name of the event randomly generated
     */
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
                                                        
                                Enter "C" to continue on your journey.
                                """, dollars, ammo, clothes, splints, wTools
                ));
                game.setMoney(game.getMoney() + dollars);
                game.setAmmunition(game.getAmmunition() + ammo);
                game.setClothes(game.getClothes() + clothes);
                game.setSplints(game.getSplints() + splints);
                game.setWagonTools(game.getWagonTools() + wTools);
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
                    promptPane.setText(characterArrayList.get(ind).getName() + " got injured.\nEnter \"C\" to continue.");
                    inputField.addActionListener(closeAL);
                } else {
                    characterArrayList.get(ind).setInjured(true);
                    promptPane.setText(characterArrayList.get(ind).getName() + " got injured again.\nEnter \"C\" to " +
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
                        "Enter \"C\" to continue.");
                inputField.addActionListener(closeAL);
            }
            case "foodSpoiled" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/spoiledFood.png"));
                double spoiledFoodDb = game.getFood() * .2;
                int spoiledFood = (int) spoiledFoodDb;
                game.calculateFood(-spoiledFood);
                promptPane.setText("Some of your food spoiled. You lost " + spoiledFood + " food.\nEnter \"C\" " +
                        "to continue.");
                inputField.addActionListener(closeAL);
            }
            case "illness" -> {
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/illness.png"));
                if (!characterArrayList.get(ind).isSick()) {
                    promptPane.setText(characterArrayList.get(ind).getName() + " has fallen sick!\nEnter \"C\" " +
                            "to continue.");
                    characterArrayList.get(ind).setSick(true);
                    inputField.addActionListener(closeAL);
                }
                else {
                    promptPane.setText(characterArrayList.get(ind).getName() + "has relapsed into their illness!\n" +
                            "Enter \"C\" to continue.");
                }
            }
            default -> throw new RuntimeException("Something bad happened in the doEvent method");
        }
    }

    /**
     * Generates the values of resources found in the abandoned wagon.
     * @param i the string letter of the resource being randomly generated
     * @return the value of the randomly generated resource
     */
    private int wagonFound(String i) {
        switch (i.toUpperCase()) {
            case "D" -> { return game.rand.nextInt(13); }
            case "A" -> { return game.rand.nextInt(3); }
            case "C", "S", "W" -> { return game.rand.nextInt(2); }
            default -> throw new RuntimeException("There was an error with wagonFound method in RandomEvent class");
        }
    }

    /**
     * Generates the names of the travelers encountered by the party
     */
    private void genNames() {
        randName1 = game.rand.nextInt(travelerNameList.size());
        randName2 = game.rand.nextInt(travelerNameList.size());
        if (randName2==randName1) {
            genNames();
        }
    }

    /**
     * Prompts the event of encountering other travelers. Displays the information of the event to the user and
     * suggests them to enter the option they would like to spend their time doing during this occurrence.
     */
    public void encounterTraveler() {
        genNames();
        promptPane.setText(String.format(
                """
                You encounter the travelers %s and %s. What would you like to do?
                
                T: TRADE FOR NEEDED RESOURCES
                S: SHARE STORIES BY THE CAMPFIRE
                
                You can enter "T" to trade for resources you are low on.
                You can enter "S" to share stories of your travels and increase party happiness.
                """, travelerNameList.get(randName1), travelerNameList.get(randName2)
        ));
        inputField.addActionListener(encounterAL);
    }

    /**
     * Initializes the array of items the traders can request for
     */
    private void resetNAItems() {
        itemArrayList = new ArrayList<>(List.of("money", "clothes", "ammunition","food","medicine"));
    }

    /**
     * The ActionListener registered towards the encounter other travelers events. Allows the user to enter a T or an S
     * depending on whether they want to trade or share stories. The appropriate methods are also called.
     */
    private final ActionListener encounterAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputField.addFocusListener(inputHelp);
            isEncounterAL = true;
            String i = inputField.getText().toUpperCase();
            inputField.removeActionListener(encounterAL);
            switch (i) {
                case "T" -> initiateTrade();
                case "S" -> { shareStories(); onCancel(); }
                default -> staticMethods.notValidInput();
            }
            inputField.setText("");
        }
    };

    /**
     * The method used to manage the trade event between the travelers and the user's party. The party selects a
     * resource they would like to trade for and the ActionListener event created in this class handles it.
     * The user will enter a letter for the resource they want to trade for and pseudo-random quantity of a random
     * resource is requested in exchange for a set quantity of the resource requested by the party.
     */
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

    /**
     * The checkQuantity is used to verify that the party has enough of the resource requested to make an exchange
     * with the travelers.
     * @param n the name of the item that is being requested by the travelers.
     * @return the quantity of the item
     */
    private int checkQuantity(String n) {
        int q = 0;
        switch (n) {
            case "MONEY" -> { q = game.getMoney(); }
            case "FOOD" -> { q = game.getFood(); }
            case "AMMUNITION" -> { q = game.getAmmunition(); }
            case "MEDICINE" -> { q = game.getMedicine();}
            case "CLOTHES" -> { q = game.getClothes(); }
            case "WAGON TOOLS" -> { q = game.getWagonTools(); }
            case "SPLINTS" -> { q = game.getSplints(); }
            case "OXEN" -> { q = game.getOxen(); }
        }
        return q;
    }

    /**
     * Adjusts the party resources after the trade transaction is successful.
     * @param n the item name being exchanged
     * @param amt the quantity of the item being requested by the travelers
     * @param get the name of the item the party asks for
     * @param received the quantity of the item the party receives
     */
    private void setQuantity(String n, int amt, String get, int received) {
        switch (n) {
            case "MONEY" -> game.setMoney(game.getMoney() - amt);
            case "FOOD" -> game.setFood(game.getFood() - amt);
            case "AMMUNITION" -> game.setAmmunition(game.getAmmunition() - amt);
            case "MEDICINE" -> game.setMedicine(game.getMedicine() - amt);
            case "CLOTHES" -> game.setClothes(game.getClothes() - amt);
            case "WAGON TOOLS" -> game.setWagonTools(game.getWagonTools() - amt);
            case "SPLINTS" -> game.setSplints(game.getSplints() - amt);
            case "OXEN" -> game.setOxen(game.getOxen() - amt);
        }
        switch (get) {
            case "FOOD" -> game.setFood(game.getFood() + 20);
            case "AMMUNITION" -> game.setAmmunition(game.getAmmunition() + 6);
            case "MEDICINE" -> game.setMedicine(game.getMedicine() + 5);
            case "CLOTHES" -> game.setClothes(game.getClothes() + 2);
            case "WAGON TOOLS" -> game.setWagonTools(game.getWagonTools() + 2);
            case "SPLINTS" -> game.setSplints(game.getSplints() + 3);
            case "OXEN" -> game.setOxen(game.getOxen() + 2);
        }
        tradeSuccessful(n, amt, get, received);
    }

    /**
     * Prints a dialogue window that displays the overview of the transaction made with the travelers in the trade
     * scenario
     * @param name the item name being exchanged
     * @param amount the quantity of the item being requested by the travelers
     * @param getItem the name of the item the party asks for
     * @param received the quantity of the item the party receives
     */
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

    /**
     * This method is called when the trade is confirmed by the user and the constraints are satisfied. An
     * ActionListener is created in the method that received the user input whether they agree to the trade
     * conditions or not. If the trade is accepted then the setQuantity method is called that alters the resources
     * of the party as if the trade was made.
     * @param n the item name being exchanged
     * @param amt the quantity of the item being requested by the travelers
     * @param get the name of the item the party asks for
     * @param received the quantity of the item the party receives
     */
    private void makeTrade(String n, int amt, String get, int received) {
        inputField.addActionListener(e2 -> {
            String yn = inputField.getText().toUpperCase();
            switch (yn) {
                case "Y" -> {
                    if (checkQuantity(n) >= amt) {
                        setQuantity(n, amt, get, received);
                        inputField.removeActionListener((ActionListener) e2);
                    }
                    else {
                        staticMethods.notEnoughItem(n);
                        tradeCancelled();
                        inputField.removeActionListener((ActionListener) e2);
                    }
                }
                case "N" -> { tradeCancelled(); inputField.removeActionListener((ActionListener) e2); }
                default -> throw new RuntimeException("pls just make the trade");
            }
        });
    }

    /**
     * This method is called when a situation arises to cancel the trade. It terminates the window by calling the
     * onCancel method and the party resumes their journey.
     */
    private void tradeCancelled() {
        JOptionPane.showMessageDialog(null, "You thank the other travelers for their " +
                        "time and wish them safety during their journey.", "TRADE CANCELLED",
                JOptionPane.PLAIN_MESSAGE);
        onCancel();
    }

    /**
     * This ActionListener is responsible for handling the party random encounter with native americans along the trail.
     * It prompts the user the options of choosing to donate resources to the traveling native american or to apologize
     * and share empathy for them. Another ActionListener is called to conclude the exchange.
     */
    private final ActionListener nativeAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            isNativeAL = true;
            if (inputField.getText().equalsIgnoreCase("C")) {
                resetNAItems();
                inputField.setText("");
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
                inputField.removeActionListener(nativeAL);
                isNativeAL = false;
                nativeAL2(ask, itemIndex, have);
            }
        }
    };

    /**
     * This method is responsible for creating another ActionListener that handles the input situations of apologizing
     * to the native american travelers or making a donation to them. Happiness is provided to the party if they donate
     * the requested quantity of the requested resource, and it is removed from the party's inventory if they have
     * sufficient quantity of the resource.
     * @param ask the quantity of the resource requested by the native american
     * @param itemInd the index of the item that the native american is requesting for
     * @param have the quantity of the same resource owned by the party
     */
    private void nativeAL2(int ask, int itemInd, int have) {
        String item = String.valueOf(itemArrayList.get(itemInd));
        inputField.addActionListener(event -> {
            String in = inputField.getText().toUpperCase();
            inputField.setText("");
            switch (in) {
                case "D" -> {
                    if (have >= ask) {
                        switch (itemInd) {
                            case 0 -> game.setMoney(game.getMoney() - ask);
                            case 1 -> game.setClothes(game.getClothes() - ask);
                            case 2 -> game.setAmmunition(game.getAmmunition() - ask);
                            case 3 -> game.setFood(game.getFood() - ask);
                            case 4 -> game.setMedicine(game.getMedicine() - ask);
                        }
                        game.calculateHappiness(20);
                        JOptionPane.showMessageDialog(null, String.format(
                                """
                                You gather some %s from your belongings that you have
                                to spare to another fellow traveler. After sharing some
                                brief conversation, they express great gratitude towards
                                your kind gesture and you feel warm-hearted.
                                
                                HAPPINESS INCREASED BY 20.
                                """, item), "GOOD SAMARITAN", JOptionPane.INFORMATION_MESSAGE);
                        inputField.removeActionListener((ActionListener) event);
                        onCancel();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "You don't have enough " +
                                item + " to help them out.\nYou can " + "apologize and wish them luck and move on " +
                                "instead.", "NOT ENOUGH " + item, JOptionPane.ERROR_MESSAGE);
                        inputField.removeActionListener((ActionListener) event);
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

    /**
     * Checks the status of various ActionListeners and windows created or modified in this method and closes
     * ActionListeners that are still open before returning to the main game and disposing of the dialogue form.
     */
    private void closeActionListeners() {
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

    /**
     * This ActionListener is responsible for providing a "continue" sequence in most random event occurrences. This is
     * where it says "Enter "C" to continue" until disposing of the dialogue form
     */
    private final ActionListener closeAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            isCloseAL = true;
            if (inputField.getText().equalsIgnoreCase("C")) {
                dispose();
            }
        }
    };

    /**
     * This ActionListener is responsible for navigating through the small stream random event. It handles whether the
     * user decides to swim or fish in the stream.
     */
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

    /**
     * This method is called when the user selects the swim action at the small stream event and handles the small (5%)
     * probability that the character(s) may get injured from playing around in the stream.
     */
    private void swim() {
        ArrayList<Character> characterArrayList = game.getCharacterArrayList();
        int ind;
        if (game.rand.nextInt(19)==0) {
            do {
                ind = game.rand.nextInt(5);
            } while (characterArrayList.get(ind).getIsDead());
            Character character = characterArrayList.get(ind);
            if (!character.isInjured()) {
                promptPane.setText("How unlucky! " + character.getName() + " got injured swimming.\nHe also took some " +
                        "damage as a result.\nEnter \"C\" to continue.");
            }
            else if (character.isInjured()) {
                promptPane.setText("How clumsy! " + character.getName() + " got injured again while swimming.\nHe also " +
                        "took some damage as a result.\nEnter \"C\" to continue.");
            }
            character.setInjured(true);
            game.checkNewDeaths();
            game.checkIfLost();
        }
        else {
            promptPane.setText("""
                    Your party had a great time splashing around the stream.
                    Party happiness increased by 20!
                    Enter "C" to continue.""");
            game.calculateHappiness(20);
        }
        inputField.removeActionListener(streamAL);
        inputField.addActionListener(closeAL);
    }

    /**
     * This method fish handles the fishing option that the users can take in the small stream event. There is a
     * probability that they catch
     */
    private void fish() {
        int fishAmount;
        fishAmount = game.rand.nextInt(10);
        inputField.removeActionListener(streamAL);
        inputField.addActionListener(closeAL);
        if (fishAmount == 0) {
            promptPane.setText("You weren't able to catch any fish!\nEnter \"C\" to continue.");
        }
        else {
            promptPane.setText("You caught " + fishAmount + " fish!\nEnter \"C\" to continue.");
        }
    }

    /**
     * This method handles the story sharing selection option in the encountering other travelers event and gives the
     * party 10 happiness.
     */
    private void shareStories() {
        JOptionPane.showMessageDialog(null,"You had a wonderful time sharing stories and " +
                "the party gained 10 happiness!","Share Stories",JOptionPane.PLAIN_MESSAGE);
        game.calculateHappiness(10);
    }

    /**
     * This FocusAdapter creates a grey string of text in the textfield the user will interact with. Upon selecting the
     * textfield, the grey text will be set to empty and the foreground font color will change to black. When they click
     * off of the textfield, the area will be emptied out and the grey text will return.
     */
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
