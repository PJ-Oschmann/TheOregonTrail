import javax.management.RuntimeErrorException;
import javax.swing.*;
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
    private String traderAsk;
    private int halfOff;
    private ArrayList<String> nameArrayList = new ArrayList<>(List.of("Felicia","Mia","Kristin","Katrina","Janet",
            "Almudena","Chika","Mary","Nicole","Jessica","Maxine","Stephany","Kendra","Kendall","Kenifer","Elise",
            "Anna","Lizzy","Minnie","Ida","Florence","Martha","Nellie","Lena","Agnes","Candace","Jane","April", "Jordan",
            "Skyler","Sonia","Joanne","Crystal","Melissa","Amy","Sharron","Kelly","Shelly","Chrysanthemum","Ally",
            "Sally","Maria"));
    private ArrayList<String> itemArrayList;
    private int tradeAmt;
    private String tradeItem;

    private int tradeGiveAmt;
    private int food, ammunition, medicine, clothes, wagonTools, splints, oxen, money, happiness;
    private ArrayList<Character> characterArrayList;

    public RandomEventGUI(OregonTrailGUI game) {
        // call onCancel() when cross is clicked
        this.game = game;
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

    public void checkForRandomEvent() {
        int n = game.rand.nextInt(9);
        if (n == 0) {
            setGlobalVar();
            setContentPane(contentPane);
            setModal(true);

            String event = eventName();
            this.setTitle("RANDOM EVENT");
            doEvent(event);

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
            temp = game.rand.nextInt(3);
            if (temp == 0) {
                return "encounterTraveler"; //player encounters another traveler
            } else if (temp == 1) {
                return "smallStream"; //player encounters
            } else {
                return "wagonFound"; //found abandoned wagon
            }
        } else {
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
            case "encounterTraveler":
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/encounterTraveler.png"));
                inputField.addActionListener(encounterAL);
                encounterTraveler();
                break;
            case "smallStream":
                this.imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/smallStream.png"));
                inputField.addActionListener(streamAL);
                promptPane.setText("""
                You found a small stream!
                
                S: SWIM (Happiness++)
                F: FISHING (Food++)
                """);
                break;
            case "wagonFound":  //TODO: FIX THIS @@KEN@@


                promptPane.setText(String.format(
                        """
                        Your party has stumbled upon an abandoned wagon on the side of the road.
                        You find some resources after scavenging around the area!
                        
                        YOU FIND:
                        %d DOLLARS
                        %d boxes of AMMUNITION
                        %d sets of CLOTHING
                        %d usable SPLINTS
                        %d sets of WAGON TOOLS
                        
                        Press "C" to continue on your journey.
                        """
                ));
                game.calculateHappiness(5);
                inputField.addActionListener(closeAL);

                break;

            //Bad events
            case "injury":
                if (!characterArrayList.get(characterIndex).isInjured()) {
                    characterArrayList.get(characterIndex).setInjured(true);
                    promptPane.setText(characterArrayList.get(characterIndex).getName() + " got injured. Press \"C\" to continue.");
                    inputField.addActionListener(closeAL);
                } else {
                    characterArrayList.get(characterIndex).setInjured(true);
                    promptPane.setText(characterArrayList.get(characterIndex).getName() + " got injured again. Press \"C\" to continue.");
                    inputField.addActionListener(closeAL);
                }
                break;
            case "wagonDamage":
                game.calculateHappiness(-5);
                game.wagon.setState(game.wagon.getState() + 1);
                if (game.wagon.getState() == 0) {
                    game.checkIfLost();
                }
                promptPane.setText("As you traveled your wagon hit a rock and became damaged. Everyone is saddened. Press " +
                        "\"C\" to continue.");
                inputField.addActionListener(closeAL);
                break;
            case "foodSpoiled":
                double spoiledFoodDb = game.getFood() * .2;
                int spoiledFood = (int) spoiledFoodDb;
                game.calculateFood(-spoiledFood);
                promptPane.setText("Some of your food spoiled. You lost " + spoiledFood + " food. Press \"C\" to continue.");
                inputField.addActionListener(closeAL);
                break;
            case "illness":
                if (!characterArrayList.get(characterIndex).isSick()) {
                    promptPane.setText(characterArrayList.get(characterIndex).getName() + " has fallen sick! Press \"C\" to continue.");
                    characterArrayList.get(characterIndex).setSick(true);
                    inputField.addActionListener(closeAL);
                }

                break;
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
    }

    private void trade(int step) {
        resetItemArrayList();
        traderAsk = itemArrayList.get(game.rand.nextInt(8));

    }

    private void resetItemArrayList() {
        itemArrayList = new ArrayList<>(List.of("money", "clothes", "ammunition","food","medicine",
                "splints","tools", "oxen"));
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
            if (inputField.getText().equalsIgnoreCase("T")) {
                trade(1);
            }
            else if (inputField.getText().equalsIgnoreCase("S")) {
                shareStories();
                onCancel();
            }
        }
    };


    private final ActionListener closeAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (inputField.getText().equalsIgnoreCase("C")) {
                passBackVar();
                dispose();
            }
        }
    };

    private final ActionListener streamAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
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
}
