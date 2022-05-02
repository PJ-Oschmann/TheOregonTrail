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
    private String traderItem;
    private int halfOff;
    private ArrayList<String> nameArrayList = new ArrayList<>(List.of("Felicia","Mia","Kristin","Katrina","Janet",
            "Almudena","Chika","Mary","Nicole","Jessica","Maxine","Stephany","Kendra","Kendall","Kenifer","Elise",
            "Anna","Lizzy","Minnie","Ida","Florence","Martha","Nellie","Lena","Agnes","Candace","Jane","April", "Jordan",
            "Skyler","Sonia","Joanne","Crystal","Melissa","Amy","Sharron","Kelly","Shelly","Chrysanthemum","Ally",
            "Sally","Maria"));
    private ArrayList<String> itemArrayList = new ArrayList<>(List.of("clothes","ammunition","food","medicine",
            "splints","tools"));

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
            this.pack();
            this.setVisible(true);

            String event = "encounterTraveler";
            this.setTitle("RANDOM EVENT");
            doEvent(event);
        }
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
        promptPane.setText("You encounter travelers " + nameArrayList.get(randName1) + " and " +
                nameArrayList.get(randName2) + " on your travels. What would you like to do?\nT=Trade S=Share Stories");
        inputField.addActionListener(encounterAL);
    }

    private void trade(int step) {
        traderItem = itemArrayList.get(game.rand.nextInt(5));
        halfOff = -1;
        if (step==1) {
            inputField.removeActionListener(encounterAL);
            promptPane.setText("What would you like to trade? Enter it in the format 'Number' 'Item' (E.g., 5 F for 5 Food)");
            inputField.addActionListener(tradeALWant);
            inputField.setText("");
        }
        else if (step==2) {
            inputField.setText("");
            inputField.removeActionListener(tradeALWant);
            do {
                if (tradeItem.equalsIgnoreCase("F")) {halfOff = foodAmt/2;}
                else if (tradeItem.equalsIgnoreCase("A")) {halfOff = ammoAmt/2;}
                else if (tradeItem.equalsIgnoreCase("M")) {halfOff = medsAmt/2;}
                else if (tradeItem.equalsIgnoreCase("C")) {halfOff = clothesAmt/2;}
                else if (tradeItem.equalsIgnoreCase("S")) {halfOff = splintAmt/2;}
                else if (tradeItem.equalsIgnoreCase("T")) {halfOff = toolsAmt/2;}
                else { staticMethods.notValidInput(); }
                } while (halfOff == -1);
            promptPane.setText(nameArrayList.get(randName1) + " and " + nameArrayList.get(randName2) + " want at least $" +
                    halfOff + " worth of " + traderItem + ". Would you like to trade?\nY=Yes N=No (Abandon Trade)");
            inputField.addActionListener(tradeAbandonNo);
        }
        else if (step==3) {
            inputField.setText("");
            inputField.removeActionListener(tradeAbandonNo);
            promptPane.setText("You must trade at least $"+halfOff+" worth of " + traderItem+ ". How much would you" +
                    " like to trade?");
            inputField.addActionListener(tradeALgive);
        }
        else if (step==4) {
            String itemName = "";
            if (tradeItem.equalsIgnoreCase("F")) {itemName = "Food";}
            else if (tradeItem.equalsIgnoreCase("A")) {itemName = "Ammunition";}
            else if (tradeItem.equalsIgnoreCase("M")) {itemName = "Medicine";}
            else if (tradeItem.equalsIgnoreCase("C")) {itemName = "Clothes";}
            else if (tradeItem.equalsIgnoreCase("S")) {itemName = "Splints";}
            else if (tradeItem.equalsIgnoreCase("T")) {itemName = "Wagon Tools";}
            inputField.setText("");
            inputField.removeActionListener(tradeALgive);
            promptPane.setText("Yahoo, you earned " + tradeAmt + " " + itemName + "!");
            if (tradeItem.equalsIgnoreCase("F")) {game.setFood(game.getFood()+tradeAmt);}
            else if (tradeItem.equalsIgnoreCase("A")) {game.setAmmunition(game.getAmmunition()+tradeAmt);}
            else if (tradeItem.equalsIgnoreCase("M")) {game.setMedicine(game.getMedicine()+tradeAmt);}
            else if (tradeItem.equalsIgnoreCase("C")) {game.setClothes(game.getClothes()+tradeAmt);}
            else if (tradeItem.equalsIgnoreCase("S")) {game.setSplints(game.getSplints()+tradeAmt);}
            else if (tradeItem.equalsIgnoreCase("T")) {game.setWagonTools(game.getWagonTools()+tradeAmt);}
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
                dispose();
            }
        }
    };

    private final ActionListener tradeALWant = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int itemAmt=0;
            //TODO: Check for valid input
            try {
                tradeAmt = Integer.parseInt(inputField.getText().substring(0,1));
                tradeItem = inputField.getText().substring(2,3);
                trade(2);

            }
            catch (Exception oops) {
                JOptionPane.showMessageDialog(null,"Sorry, you couldn't be understood. Please try " +
                        "again, using the correct format.","Whoops",JOptionPane.ERROR_MESSAGE);
                System.out.println("RandomEventGUI.java: Something went wrong. Let's take a peek at the error: " + oops);
            }
        }
    };

    private final ActionListener tradeAbandonNo = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (inputField.getText().equalsIgnoreCase("Y")) {
                trade(3);
            }
            else {
                JOptionPane.showMessageDialog(null,"You wave goodbye and abandon the trade.",
                        "Abandoning Trade",JOptionPane.PLAIN_MESSAGE);
                dispose();
            }
        }
    };

    private final ActionListener tradeALgive = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO: Check for valid input
            try {
                int price=0;
                int invAmt=0;
                tradeGiveAmt = Integer.parseInt(inputField.getText().substring(0,1));
                System.out.println("REG: tradeAmtGive=" + tradeGiveAmt);
                System.out.println("REG: tradeItem="+ traderItem);

                if (traderItem.equalsIgnoreCase("clothes")) {price = clothesAmt;invAmt=game.getClothes();}
                else if (traderItem.equalsIgnoreCase("ammunition")) {price = ammoAmt; invAmt=game.getAmmunition();}
                else if (traderItem.equalsIgnoreCase("food")) {price = foodAmt; invAmt=game.getFood();}
                else if (traderItem.equalsIgnoreCase("medicine")) {price = medsAmt; invAmt=game.getMedicine();}
                else if (tradeItem.equalsIgnoreCase("splints")) {price = splintAmt; invAmt=game.getSplints();}
                else if (tradeItem.equalsIgnoreCase("tools")) {price = toolsAmt; invAmt=game.getWagonTools();}

                //TODO: make string about not having enough lmao
                if (tradeGiveAmt*price>=halfOff && tradeGiveAmt<=invAmt){
                    trade(4);
                }
                else if (tradeGiveAmt>=invAmt) {
                    String[] buttons = {"Abandon Trade","Change value"};
                    int choice = JOptionPane.showOptionDialog(null,"You don't have enough of this item to trade.","Not enough items",JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,null,buttons,null);
                    if (choice==JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null,"You abandoned the trade.","Abandoning trade",JOptionPane.PLAIN_MESSAGE);
                        dispose();
                    }
                    else {
                        inputField.setText("");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"You need to trade at least $" +
                            halfOff + " worth of " + traderItem + ", but you only traded $" + tradeGiveAmt*price+". Please " +
                            "trade the required amount.");
                    trade(3);
                }


            }
            catch (Exception oops) {
                JOptionPane.showMessageDialog(null,"Sorry, you couldn't be understood. Please try " +
                        "again, using the correct format.","Woops",JOptionPane.ERROR_MESSAGE);
                System.out.println("RandomEventGUI.java: Something went wrong. Let's take a peek at the error: " + oops);
            }

        }
    };

    private final ActionListener closeAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (inputField.getText().equalsIgnoreCase("C")) {
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
        int characterIndex = game.rand.nextInt(4);
        Character character = characterArrayList.get(characterIndex);
        if (game.rand.nextInt(9)==0) {
            if (!character.isInjured()) {
                character.setInjured(true);

                promptPane.setText(character.getName()+" got injured swimming. Press 'C' to continue.");
            }
            else {
                promptPane.setText(character.getName()+" got injured again while swimming. Press 'C' to continue");
            }
            inputField.removeActionListener(streamAL);
            inputField.addActionListener(closeAL);
        }
        else {
            promptPane.setText("Your party had a great time splashing around the stream. Your happiness increased by " +
                    "20! Press 'C' to continue.");
            game.calculateHappiness(20);
            inputField.removeActionListener(streamAL);
            inputField.addActionListener(closeAL);
        }
    }

    private void fish() {
        int fishAmount=0;
        fishAmount=game.rand.nextInt(10);
        inputField.removeActionListener(streamAL);
        inputField.addActionListener(closeAL);
        promptPane.setText("You caught " + fishAmount + " fish! Press 'C' to continue.");

    }

    private void shareStories() {
        JOptionPane.showMessageDialog(null,"You had a jolly old time sharing stories and earned 10 happiness!","Share Stories",JOptionPane.PLAIN_MESSAGE);
        game.setHappiness(game.calculateHappiness(10));
    }

    public void doEvent(String event) {
        ArrayList<Character> characterArrayList = game.getCharacterArrayList();
        int characterIndex = game.rand.nextInt(4);

        //Good events
        switch (event) {
            case "encounterTraveler":
                inputField.addActionListener(encounterAL);
                encounterTraveler();
                break;
            case "smallStream":
                inputField.addActionListener(streamAL);
                promptPane.setText("""
                You found a small stream!
                
                S: SWIM (Happiness++)
                F: FISHING (Food++)
                """);
                break;
            case "wagonFound":  //TODO: FIX THIS @@KEN@@
                int newItem = game.rand.nextInt(5);
                int amount = game.rand.nextInt(3) + 1;
                String newItemName = "";
                if (newItem == 0) {
                    newItemName = "Clothes";
                } else if (newItem == 1) {
                    newItemName = "Ammunition";
                } else if (newItem == 2) {
                    newItemName = "Medicine";
                } else if (newItem == 3) {
                    newItemName = "Splints";
                } else {
                    newItemName = "Wagon Tools";
                }
                promptPane.setText("You found " + amount + " " + newItemName + "! Press 'C' to continue");
                game.calculateHappiness(5);
                inputField.addActionListener(closeAL);

                break;

            //Bad events
            case "injury":
                if (!characterArrayList.get(characterIndex).isInjured()) {
                    characterArrayList.get(characterIndex).setInjured(true);
                    promptPane.setText(characterArrayList.get(characterIndex).getName() + " got injured. Press 'C' to continue.");
                    inputField.addActionListener(closeAL);
                } else {
                    characterArrayList.get(characterIndex).setInjured(true);
                    promptPane.setText(characterArrayList.get(characterIndex).getName() + " got injured again. Press 'C' to continue.");
                    inputField.addActionListener(closeAL);
                }
                break;
            case "wagonDamage":
                game.calculateHappiness(-5);
                game.wagon.setState(game.wagon.getState() - 1);
                if (game.wagon.getState() == 0) {
                    game.checkIfLost();
                }
                promptPane.setText("As you traveled your wagon hit a rock and became damaged. Everyone is saddened. Press " +
                        "'C' to continue.");
                inputField.addActionListener(closeAL);
                break;
            case "foodSpoiled":
                double spoiledFoodDb = game.getFood() * .2;
                int spoiledFood = (int) spoiledFoodDb;
                game.calculateFood(-spoiledFood);
                promptPane.setText("Some of your food spoiled. You lost " + spoiledFood + " food. Press 'C' to continue.");
                inputField.addActionListener(closeAL);
                break;
            case "illness":
                if (!characterArrayList.get(characterIndex).isSick()) {
                    promptPane.setText(characterArrayList.get(characterIndex).getName() + " has fallen sick! Press 'C' to continue.");
                    characterArrayList.get(characterIndex).setSick(true);
                    inputField.addActionListener(closeAL);
                }

                break;
        }
    }
}
