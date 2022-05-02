import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Activities {
    private int food;
    private int ammunition;
    private int medicine;
    private int clothes;
    private int wagonTools;
    private int splints;
    private int oxen;
    private int money;
    private ArrayList<Character> characterArrayList;
    private int happiness;
    private OregonTrailGUI game;
    private int dailyActions;
    private int cloDACounter = 0;
    public int journCounter = 0;
    private Random rand = new Random();

    public Activities(OregonTrailGUI game) {
        this.game = game;
        setGlobalVar();
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
        this.dailyActions = game.getDailyActions();
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

    public void displayActivitiesMenu() {
        game.storyTextArea.setText(String.format(
            """
            DAILY ACTIVITIES SELECTION (COSTS)
            You have %d daily actions remaining.
            
            H: Hunting (ONE ACTION, ONE AMMO BOX)
            F: Foraging (TWO ACTIONS)
            C: Sowing a set of clothes (ONE ACTION)
            W: Repairing the wagon (ONE ACTION, ONE WAGON TOOL)
            J: Write in your Journal (ONE ACTION, LIMIT ONCE A DAY)
            S: Sleep (ONE ACTION)
            
            A: ADDITIONAL INFORMATION ON THE DAILY ACTIVITIES
            
            Enter R to return to the previous menu.
            """, game.getDailyActions()
        ));
        game.userInput.removeActionListener(game.menuListener);
        game.userInput.addActionListener(activityMenuListener);
    }

    private ActionListener activityMenuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = game.userInput.getText().toUpperCase();
            switch (input) {
                case "H" -> { hunt(); } //hunting
                case "F" -> { forage(); } //foraging
                case "C" -> { makeClothes(); } //clothes
                case "W" -> { repairWagon(); } //wagon repairs
                case "J" -> { writeJournal(); } //journaling
                case "S" -> { sleep(); } //sleep
                case "A" -> { displayAdditionalInfo(); } //get additional info
                case "R" -> returnToGameMenu();
                default -> { staticMethods.notValidInput(); }
            }
        }
    };

    public void returnToGameMenu() {
        game.updateStats();
        game.writeGameInfo();
        passBackVar();
    }

    private void displayAdditionalInfo(){
        game.storyTextArea.setText(
                """
                MORE INFO ABOUT DAILY ACTIVITIES:
                
                Hunting
                Foraging
                Sowing Clothes
                Repairing the wagon
                
                """
        );
    }

    private void repairWagon() {
        if (wagonTools >= 1) {
            game.wagon.setState(0);
            wagonTools -= 1;
            dailyActions -= 1;
        }
        else {
            staticMethods.notEnoughItem("WAGON TOOLS");
        }
    }

    public void hunt() {
        if (ammunition >= 1) {
            int newFood;
            String[] shootButton = {"Shoot!"};
            JOptionPane.showOptionDialog(null,"You point your gun into the woods...",
                    "Hunting Activity",JOptionPane.YES_OPTION,JOptionPane.PLAIN_MESSAGE,null,shootButton,null);
            int n = rand.nextInt(99);
                if (n < 9) {
                    newFood = 5;
                } else if (n < 24) {
                    newFood = 7;
                } else if (n < 44) {
                    newFood = 9;
                } else if (n < 74) {
                    newFood = 11;
                } else if (n < 89) {
                    newFood = 13;
                } else {
                    newFood = 15;
                }
            JOptionPane.showMessageDialog(null,"You found " + newFood + " food!","Hunting activity",
                    JOptionPane.PLAIN_MESSAGE);
            food = newFood;
            dailyActions -= 1;
        }
        else {
            staticMethods.notEnoughItem("AMMUNITION");
        }
    }

    public void forage() {
        int newFood;
        String[] searchButton = {"Look Around!"};
        JOptionPane.showOptionDialog(null,"You enter the woods, being careful to watch your step...",
                "Foraging Activity",JOptionPane.YES_OPTION,JOptionPane.PLAIN_MESSAGE,null,searchButton,null);
        int n = rand.nextInt(99);
            if (n < 9) {
                newFood = 1;
            } else if (n < 24) {
                newFood = 2;
            } else if (n < 44) {
                newFood = 3;
            } else if (n < 69) {
                newFood = 4;
            } else if (n < 89) {
                newFood = 5;
            } else {
                newFood = 6;
            }
        JOptionPane.showMessageDialog(null,"You found " + newFood + " food!",
                "Foraging activity",JOptionPane.PLAIN_MESSAGE);
        food = newFood;
        dailyActions -= 2;
    }

    public void makeClothes() {
        if (cloDACounter <= 2) {
            cloDACounter++;
            JOptionPane.showMessageDialog(null,"You have begun making a set of clothes. " +
                    "Tired, you decide to finish making the clothes later.","Clothes-making Activity",JOptionPane.PLAIN_MESSAGE);
        }
        else if (cloDACounter == 3){
            cloDACounter = 0;
            JOptionPane.showMessageDialog(null,"You made a fresh set of clothes. " +
                    "They have been added ot your inventory.","Clothes-making Activity",JOptionPane.PLAIN_MESSAGE);
            clothes += 1;
        }
        dailyActions -= 1;
    }

    public int getJournCounter() {
        return journCounter;
    }

    public void setJournCounter(int journCounter) {
        this.journCounter = journCounter;
    }

    public void writeJournal() {
        if (journCounter == 0) {
            journCounter++;
            JOptionPane.showMessageDialog(null,"You take out your journal and pen and begin to write",
                    "Writing Activity",JOptionPane.PLAIN_MESSAGE);
            happiness = game.calculateHappiness(5);
            dailyActions -= 1;
        }
        else {
            JOptionPane.showMessageDialog(null,"You have already written in your Journal today.",
                    "Nothing to write, head empty", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sleep() {
        for (Character character : characterArrayList) {
           game.calculateHealth(character,5);
        }
        dailyActions -= 1;
    }
}
