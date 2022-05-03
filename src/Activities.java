import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    public Activities(OregonTrailGUI game) {
        this.game = game;
    }
    private boolean inActivitiesMenu, inGameMenu = false, activitiesMenuListener = true, gameMenulistener = false;

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
        game.setDailyActions(this.dailyActions);
    }

    public void displayActivitiesMenu() {
        inActivitiesMenu();
        game.storyTextArea.setText(String.format(
            """
            DAILY ACTIVITIES SELECTION (CONSUMES)
            You have %d daily actions remaining.
            
            H: Hunting (ONE ACTION, ONE AMMO BOX)
            F: Foraging (TWO ACTIONS)
            C: Sowing a set of clothes (ONE ACTION)
            W: Repairing the wagon (ONE ACTION, ONE WAGON TOOL)
            J: Write in your Journal (ONE ACTION, LIMIT ONCE A DAY)
            S: Sleep (ONE ACTION)
            
            A: ADDITIONAL INFORMATION ON THE DAILY ACTIVITIES
            
            R: RETURN TO PREVIOUS MENU
            """, game.getDailyActions()
        ));
        manageListeners();
    }

    private void inActivitiesMenu() {
        inActivitiesMenu = true;
        inGameMenu = false;
    }

    private void inGameMenu() {
        inActivitiesMenu = false;
        inGameMenu = true;
    }

    private void manageListeners() {
        if (inActivitiesMenu && !activitiesMenuListener) {
            game.userInput.addActionListener(activityMenuListener);
            game.userInput.removeActionListener(game.gameMenu);
            inActivitiesMenu();
        }
        else if (inGameMenu && !gameMenulistener) {
            game.userInput.removeActionListener(activityMenuListener);
            game.userInput.addActionListener(game.gameMenu);
            inGameMenu();
        }
    }

    public  ActionListener activityMenuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setGlobalVar();
            String input = game.userInput.getText().toUpperCase();
            switch (input) {
                case "H" -> { hunt(); displayActivitiesMenu(); } //hunting
                case "F" -> { forage(); displayActivitiesMenu(); } //foraging
                case "C" -> { makeClothes(); displayActivitiesMenu(); } //clothes
                case "W" -> { repairWagon(); displayActivitiesMenu(); } //wagon repairs
                case "J" -> { writeJournal(); displayActivitiesMenu(); } //journaling
                case "S" -> { sleep(); displayActivitiesMenu(); } //sleep
                case "A" -> displayAdditionalInfo(); //get additional info
                case "R" -> returnToGameMenu();
                default -> staticMethods.notValidInput();
            }
            passBackVar();
            game.userInput.setText("");
        }
    };

    public void returnToGameMenu() {
        inGameMenu();
        manageListeners();
        passBackVar();
        game.writeGameInfo();
        game.updateStats();
    }

    private void displayAdditionalInfo(){
        game.storyTextArea.setText(
                """
                MORE INFO ABOUT DAILY ACTIVITIES:
                
                Hunting yields 5 to 15 food, while consuming 1 daily action & 1 ammo
                
                Foraging is free but takes two daily actions and yields 1 to 6 food
                
                Sowing Clothes consumes 1 daily action, takes 3 instances to make one set of clothes
                
                Repairing the wagon allows you to mend the damage on the wagon if it is damaged
                
                You can write in your journal once a day at most to gain some happiness
                
                Sleeping helps recover 5 HP for a short nap
                """
        );
    }

    private void repairWagon() {
        if (checkDailyActions()) {
            if (wagonTools >= 1 && game.wagon.getState()==1) {
                game.wagon.setState(0);
                wagonTools -= 1;
                dailyActions -= 1;
            }
            else if (game.wagon.getState()==0) {
                JOptionPane.showMessageDialog(null,"Your wagon is already in good shape!","WAGON " +
                        "IS FINE",JOptionPane.PLAIN_MESSAGE);
            }
            else {
                staticMethods.notEnoughItem("WAGON TOOLS");
            }
        }
    }

    public void hunt() {
        if (checkDailyActions()) {
            if (ammunition >= 1) {
                int newFood;
                String[] shootButton = {"Shoot!"};
                JOptionPane.showOptionDialog(null, "You point your gun into the woods...",
                        "Hunting Activity", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, shootButton, null);
                int n = game.rand.nextInt(99);
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
                JOptionPane.showMessageDialog(null, "You found " + newFood + " food!", "Hunting activity",
                        JOptionPane.PLAIN_MESSAGE);
                food = newFood;
                dailyActions -= 1;
            } else {
                staticMethods.notEnoughItem("AMMUNITION");
            }
        }
    }

    public void forage() {
        if (checkDailyActions()) {
            int newFood;
            String[] searchButton = {"Look Around!"};
            JOptionPane.showOptionDialog(null, "You enter the woods, being careful to watch your step...",
                    "Foraging Activity", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, searchButton, null);
            int n = game.rand.nextInt(99);
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
            JOptionPane.showMessageDialog(null, "You found " + newFood + " food!",
                    "Foraging activity", JOptionPane.PLAIN_MESSAGE);
            food = newFood;
            dailyActions -= 2;
        }
    }

    public void makeClothes() {
        if (checkDailyActions()) {
            if (cloDACounter <= 2) {
                cloDACounter++;
                JOptionPane.showMessageDialog(null, "You have begun making a set of clothes. " +
                        "Tired, you decide to finish making the clothes later.", "Clothes-making Activity", JOptionPane.PLAIN_MESSAGE);
            } else if (cloDACounter == 3) {
                cloDACounter = 0;
                JOptionPane.showMessageDialog(null, "You made a fresh set of clothes. " +
                        "They have been added ot your inventory.", "Clothes-making Activity", JOptionPane.PLAIN_MESSAGE);
                clothes += 1;
            }
            dailyActions -= 1;
        }
    }

    public int getJournCounter() {
        return journCounter;
    }

    public void setJournCounter(int journCounter) {
        this.journCounter = journCounter;
    }

    public void writeJournal() {
        if (checkDailyActions()) {
            if (journCounter == 0) {
                journCounter++;
                JOptionPane.showMessageDialog(null, "You take out your journal and pen and begin to write",
                        "Writing Activity", JOptionPane.PLAIN_MESSAGE);
                happiness += 5;
                dailyActions -= 1;
            } else {
                JOptionPane.showMessageDialog(null, "You have already written in your Journal today.",
                        "Nothing to write, head empty", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean checkDailyActions() {
        if (dailyActions>0) {
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null,"Sorry, you're out of daily actions!","NO MORE " +
                    "DAILY ACTIONS",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void sleep() {
        if (checkDailyActions()) {
            JOptionPane.showMessageDialog(null,"Everyone takes a quick nap and recovers 5 HP!",
                    "NAP TIME",JOptionPane.PLAIN_MESSAGE);
            for (Character character : characterArrayList) {
                game.calculateHealth(character, 5);
            }
            dailyActions -= 1;
        }
    }
}
