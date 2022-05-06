/**
 * Activities object for daily activities the user can interact with as they travel in the game.
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Activities {
    private final ArrayList<Character> characterArrayList;
    private final OregonTrailGUI game;
    private int cloDACounter = 0;
    public boolean hasWritten = false;

    //Action listener tracker booleans to make sure they are properly removed when done interacting with this class
    private boolean inActivitiesMenu;
    private boolean inGameMenu = false;
    private final boolean gameMenulistener = false;

    public Activities(OregonTrailGUI game) {
        this.game = game;
        characterArrayList = game.getCharacterArrayList();
    }

    /**
     * Displays the activity menu for the user to see and interact with in the GAME text area.
     * Called when the user inputs the letter "A" or "a" from the main game Menu. The original action listener from the
     * game is removed and replaced with the activityMenuListener with the inActivitiesMenu and manageListeners method
     * calls.
     */
    public void displayActivityMenu() {
        inActivitiesMenu();
        manageListeners();
        game.storyTextArea.setText(String.format(
            """
            DAILY ACTIVITIES SELECTION (CONSUMES)
            You have %d daily actions remaining.
            
            H: Hunting                  (ONE ACTION, ONE AMMO BOX)
            F: Foraging                 (TWO ACTIONS)
            C: Sowing a set of clothes  (ONE ACTION)
            W: Repairing the wagon      (ONE ACTION, ONE WAGON TOOL)
            J: Write in your Journal    (ONE ACTION, LIMIT ONCE A DAY)
            S: Sleep                    (ONE ACTION)
            
            A: ADDITIONAL INFORMATION ON THE DAILY ACTIVITIES
            
            R: RETURN TO PREVIOUS MENU
            """, game.getDailyActions()
        ));
    }

    /**
     * Shortcut methods that sets the booleans corresponding with the menu being displayed to the user. Accessed for
     * ease of determining which ActionListener to set as active or inactive with respect to the menu being displayed to
     * the user.
     */
    private void inActivitiesMenu() {
        inActivitiesMenu = true;
        inGameMenu = false;
    }
    private void inGameMenu() {
        inActivitiesMenu = false;
        inGameMenu = true;
    }

    /**
     * Method called to determine which menuListener to add or remove with respect to the current menu being displayed
     * to the user. Called right after the inActivitiesMenu() or inGameMenu() methods to set and remove the
     * ActionListeners corresponding to the current state. This ensures that there will not be multiple of the same
     * ActionListener active or both active/inactive.
     */
    private void manageListeners() {
        boolean activitiesMenuListener = true;
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

    /**
     * This is the ActionListener used in the activity menu for daily actions the user can take. The userInput textfield
     * reads an input as a single letter uppercase string and performs methods corresponding to the action selected.
     * The default case informs the user that they have entered an illegal or invalid input and the textfield is cleared
     * after the user enters any text.
     */
    public  ActionListener activityMenuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = game.userInput.getText().toUpperCase();
            switch (input) {
                case "H" -> { hunt(); displayActivityMenu(); } //hunting
                case "F" -> { forage(); displayActivityMenu(); } //foraging
                case "C" -> { makeClothes(); displayActivityMenu(); } //clothes
                case "W" -> { repairWagon(); displayActivityMenu(); } //wagon repairs
                case "J" -> { writeJournal(); displayActivityMenu(); } //journaling
                case "S" -> { sleep(); displayActivityMenu(); } //sleep
                case "A" -> displayAdditionalInfo(); //get additional info
                case "R" -> returnToGameMenu();
                default -> staticMethods.notValidInput();
            }
            game.userInput.setText("");
        }
    };

    /**
     * Returns to the main game screen and displays information corresponding to the current day and state of the game
     * to the user. This calls the methods that will reset the ActionListener state to respond to the gameMenu
     * ActionListener and to also remove the activityMenuListener. The stats of characters of our party (if any change
     * from daily activities) will also be updated when returning to the main game menu.
     */
    public void returnToGameMenu() {
        inGameMenu();
        manageListeners();
        game.writeGameInfo();
        game.updateStats();
    }

    /**
     * This method displays additional details on the different selections the user can make in the activity menu.
     */
    private void displayAdditionalInfo(){
        game.storyTextArea.setText(
                """
                MORE INFO ABOUT DAILY ACTIVITIES:
                
                H: Hunting yields 5 to 15 food, while consuming 1 daily action & 1 ammo
                
                F: Foraging is free but takes two daily actions and yields 1 to 6 food
                
                C: Sowing Clothes consumes 1 daily action, takes 3 instances to make one set of clothes
                
                W: If the wagon is damaged, you can repair it with the necessary tools.
                
                J: You can write in your journal once a day at most to gain some happiness
                
                S: Sleeping helps recover 5 HP for each character alive for a short nap
                
                R: Return to the game menu from before selecting the daily actions or activities menu
                """
        );
    }

    /**
     * This method determines the wagonState from the object wagon in the OregonTrailGUI object. It is called if the
     * user selects to repair their wagon in the daily activities menu and has constraints for whether the user is
     * allowed to repair their wagon or not. If they fulfill the conditions to repair their wagon this method removes
     * one set of wagon tools and returns their wagon state to not damaged.
     */
    private void repairWagon() {
        if (game.getDailyActions() >= 1) {
            if (game.getWagonTools() >= 1 && game.wagon.getState() == 1) {
                game.wagon.setState(0);
                game.setWagonTools(game.getWagonTools() - 1);
                game.setDailyActions(game.getDailyActions() - 1);
            }
            else if (game.wagon.getState() == 0) {
                JOptionPane.showMessageDialog(null,"Your wagon is already in good shape!","WAGON " +
                        "IS FINE",JOptionPane.PLAIN_MESSAGE);
            }
            else {
                staticMethods.notEnoughItem("WAGON TOOLS");
            }
        }
    }

    /**
     * The hunt method is called from the activitiesMenuListener and checks to see the player has the ammunition and
     * daily actions available to go hunting. If the user goes hunting, an interactive dialogue box pops up to give a
     * "game"-like feeling. Then, it provides them with a random amount of food depending on randomly generated integer.
     */
    public void hunt() {
        if (game.getDailyActions() >= 1) {
            if (game.getAmmunition() >= 1) {
                int newFood;
                String[] shootButton = {"Shoot!"};
                JOptionPane.showOptionDialog(null, "You point your gun into the woods...",
                        "Hunting Activity", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, shootButton, null);
                game.setAmmunition(game.getAmmunition() - 1);
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
                game.setFood(game.getFood() + newFood);
                game.setDailyActions(game.getDailyActions() - 1);
            } else {
                staticMethods.notEnoughItem("AMMUNITION");
            }
        }
        else {
            staticMethods.notEnoughItem("DAILY ACTIONS");
        }
    }

    /**
     * The forage method checks if the user has sufficient daily actions and is a "free" action the user can take to
     * get more food. A dialogue box simulates a "game"-like experience and the user receives a number of foods based
     * on the randomly generated integer.
     */
    public void forage() {
        if (game.getDailyActions() >= 2) {
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
            game.setFood(game.getFood() + newFood);
            game.setDailyActions(game.getDailyActions() - 2);
        }
        else {
            staticMethods.notEnoughItem("DAILY ACTIONS");
        }
    }

    /**
     * This method is called from the daily activities when the user wants to make clothes. It takes a total of 3
     * separate makeClothes() actions to make one complete set of clothes. There are different dialogues that inform the
     * user of the progress in terms of making a new set of clothes. Further, there are constraints set to make sure the
     * user has a daily action available for this.
     */
    public void makeClothes() {
        if (game.getDailyActions() >= 1) {
            if (this.cloDACounter == 1) {
               this.cloDACounter++;
                JOptionPane.showMessageDialog(null, "You have begun making a set of clothes.\n" +
                        "Tired, you decide to finish making the clothes later.", "Clothes-making Activity", JOptionPane.PLAIN_MESSAGE);
            } else if (this.cloDACounter == 2)  {
                this.cloDACounter++;
                JOptionPane.showMessageDialog(null, "You resume sowing your previously unfinished" +
                        "set of clothes.\nTired, you decide to finish making the clothes.", "Clothes-making Activity",
                        JOptionPane.PLAIN_MESSAGE);
            }
            else if (cloDACounter == 3) {
                cloDACounter = 0;
                JOptionPane.showMessageDialog(null, "You made a fresh set of clothes. " +
                        "They have been added to your inventory.", "Clothes-making Activity", JOptionPane.PLAIN_MESSAGE);
                game.setClothes(game.getClothes() + 1);
            }
            game.setDailyActions(game.getDailyActions() - 1);
        }
        else {
            staticMethods.notEnoughItem("DAILY ACTIONS");
        }
    }

    /**
     * This method gets the false/true if the user has already written in their journal or not in the specified date.
     * @return whether the user has already written in their journal or not yet.
     */
    public boolean getHasWritten() {
        return hasWritten;
    }

    /**
     * This method is used to set the hasWritten variable equivalent to the parameter supplied.
     * @param hasWritten whether the user has written in their journal or not today.
     */
    public void setHasWritten(boolean hasWritten) {
        this.hasWritten = hasWritten;
    }

    /**
     * This method is called when the user wants to take time to write in their journal as a daily action. If they have
     * sufficient daily actions or have already written in their journal there are restrictions to prevent this method
     * will inform the user that they cannot call this method and return them to the activity menu. Otherwise, the
     * hasWritten variable will be set to true and the user's party will gain 5 happiness.
     */
    public void writeJournal() {
        if (game.getDailyActions() >= 1) {
            if (!hasWritten) {
                setHasWritten(true);
                JOptionPane.showMessageDialog(null, "You take out your journal and pen and begin to write",
                        "Writing Activity", JOptionPane.PLAIN_MESSAGE);
                game.calculateHappiness(5);
                game.setDailyActions(game.getDailyActions() - 1);
            } else {
                JOptionPane.showMessageDialog(null, "You have already written in your Journal today.",
                        "Nothing to write, head empty", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            staticMethods.notEnoughItem("DAILY ACTIONS");
        }
    }

    /**
     * This method corresponds with the sleep action that the user can take if they have at least one daily action
     * remaining. If they have sufficient daily actions, this method regenerates 5hp to each character that is alive in
     * the party. Otherwise, this method will notify the user through a dialogue box that they have insufficient daily
     * actions.
     */
    public void sleep() {
        if (game.getDailyActions() >= 1) {
            JOptionPane.showMessageDialog(null,"Everyone takes a quick nap and recovers 5 HP!",
                    "NAP TIME",JOptionPane.PLAIN_MESSAGE);
            for (Character character : characterArrayList) {
                if (!character.getIsDead()) {
                    game.calculateHealth(character, 5);
                }
            }
            game.setDailyActions(game.getDailyActions() - 1);
        }
    }
}
