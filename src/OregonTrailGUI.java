import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

public class OregonTrailGUI {
    private JPanel MainPanel;
    private JPanel IMGPanel;
    private JLabel ImageLabel;
    private JPanel BottomPanel;
    private JTextField StoryTextField;
    private JPanel GeneralPanel;
    private JPanel BenPanel;
    private JPanel AugustaPanel;
    private JPanel CharlesPanel;
    private JPanel HattiePanel;
    private JTextArea storyTextArea;
    private JPanel JakePanel;
    private JTextField userInput;
    private JLabel mainInputLabel;
    private JTextPane hattieStats;
    private JTextPane charlesStats;
    private JTextPane augustaStats;
    private JTextPane benStats;
    private JTextPane jakeStats;
    private JPanel InventoryPanel;
    private JLabel InventoryImagePanel;
    private final Scene scene = new Scene();
    //private final DebugGUI debug = new DebugGUI();
    private Random rand = new Random();

    //Our players
    public Character hattie = new Character("Hattie Campbell", 100, 0);
    public Character charles = new Character("Charles",100,0);
    public Character augusta = new Character("Augusta",100,0);
    public Character ben = new Character("Ben",100,0);
    private Character jake = new Character("Jake",100,0);

    public ArrayList<Character> characterArrayList = new ArrayList<>(List.of(hattie,charles,augusta,ben,jake));
    //private ArrayList<Character> allCharacters = new ArrayList<>(List.of(hattie,charles,augusta,ben,jake));

    //game variables
    public int money = 200, food = 0, ammunition = 0, medicine = 0, clothes = 0, wagonTools = 0, splints = 0, oxen = 0;
    private boolean isGameWon = false, isGameLost = false;
    public int happiness = 75;
    private Weather weather = new Weather();
    private Wagon wagon = new Wagon();
    private Date date = new Date();
    private boolean isTraveling = false;
    private static boolean inMenu = true;
    private static boolean inGame = false;
    private int currentPace = 0; //0=Steady, 1=Strenuous, 2=Grueling
    private int sickCharacters = 0;
    private Timer travelClock = new Timer(5000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            travel();
        }
    });

    public static void main(String[] args) {
        OregonTrailGUI game = new OregonTrailGUI();
        JFrame frame = new JFrame();
        frame.setContentPane(game.MainPanel);
        frame.setTitle("The Oregon Trail -- Remake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Give the application the System's theme.
        //Delete this line if the app won't start
        setTheme();

        frame.pack();
        addUIMenuBar(frame);
        frame.setVisible(true);
    }

    /**
     * Constructor for OregonTrailGUI
     */
    //Create application
    public OregonTrailGUI() {
        userInput.addFocusListener(inputHelp);
        if(inMenu) {
            ImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/MainMenu.png"));
            displayMainMenu();
            userInput.addActionListener(menuListener);
        }
    }

    /**
     * Sets the application's UI to follow the System's look and feel,
     * instead of using the default Metal theme.
     * On Windows, native Windows (win32) elements are used.
     * On Linux, the GTK2 theme is used.
     * On MacOS, the MacOS theme is used. Probably.
     */
    public static void setTheme() {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            //Don't set it
            //(If setting the theme fails, the code simply is not run.)
        }
        for (Window window : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
        }
    }

    //Menu Bar Method
    public static void addUIMenuBar(JFrame frame){
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menuMain = new JMenu("Main");
        menuBar.add(menuMain);
        JMenu menuAbout= new JMenu("About");
        menuBar.add(menuAbout);
        JMenu menuHelp = new JMenu("Help");
        menuBar.add(menuHelp);

        //MAIN menu bar items
        JMenuItem mainMenu = new JMenuItem("Main Menu");
        JMenuItem exitApp = new JMenuItem("Exit");
        menuMain.add(mainMenu);
        menuMain.add(exitApp);

        //ABOUT menu bar items
        JMenuItem projectDescription = new JMenuItem("Project Description");
        JMenuItem aboutProject = new JMenuItem("About This Project");
        JMenuItem aboutHattie = new JMenuItem("About Hattie Campbell");
        JMenuItem imageCredits = new JMenuItem("Image Credits");
        menuAbout.add(projectDescription);
        menuAbout.add(aboutProject);
        menuAbout.add(aboutHattie);
        menuAbout.add(imageCredits);

        //HELP menu bar items
        JMenuItem howToWin = new JMenuItem("How do I win?");
        JMenuItem howToPlay = new JMenuItem("How do I play?");
        JMenuItem howToLose = new JMenuItem("How do I lose?");
        menuHelp.add(howToWin);
        menuHelp.add(howToPlay);
        menuHelp.add(howToLose);

        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);   //makes fullscreen
        frame.setVisible(true);

        //Main:
        mainMenu.addActionListener(returnMainMenuItem);
        exitApp.addActionListener(exitMenuItem);

        //About:
        projectDescription.addActionListener(projDescMenuItem);
        aboutProject.addActionListener(aboutProjMenuItem);
        aboutHattie.addActionListener(aboutHattieMenuItem);
        imageCredits.addActionListener(imgCredMenuItem);

        //Help:
        howToWin.addActionListener(winMenuItem);
        howToPlay.addActionListener(playMenuItem);
        howToLose.addActionListener(loseMenuItem);
        menuBar.setVisible(true);
    }

    /**
     * Shows dialog asking the user if they want to exit. If the user selects "yes," the game
     * exits gracefully (returns 0).
     */
    public static void exitGame() {
        if (JOptionPane.showConfirmDialog(null,"Are you sure you want to quit?",
                "Exit?",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void introScene() {
        stopContTravel();
        scene.loadScene("intro");
        inGame = true;
        loadStatusPanels();
        openShop();
        userInput.addActionListener(gameMenu);
        ImageLabel.setIcon(new javax.swing.ImageIcon(""));
        weather.setRandomWeather();
        writeGameInfo();
    }

    private void loadStatusPanels(){
        HattiePanel.setVisible(true);
        CharlesPanel.setVisible(true);
        AugustaPanel.setVisible(true);
        BenPanel.setVisible(true);
        JakePanel.setVisible(true);
    }

    /**
     * Displays a dialog box the screen. Asks as a preset to maintain a common style across all "About" messages.
     * A cute picture of Oregon is shown for some visual flair, and a new line is created every 15 words.
     * @param message - The message to display to the user
     * @param title - The title of the message box.
     */
    //Oregon image from http://www.clker.com/clipart-oregon-3.html
    private static void showAbout(String message, String title) {
        int breakTextAt = 15; //Where to break the text
        StringBuilder newMessage = new StringBuilder(message);
        int wordCounter = 0;
        ImageIcon oregonIcon = new ImageIcon("assets/oregonState.png");

        for (int i=0;i<message.length();i++) {
            if (message.charAt(i)==' ') {
                wordCounter++;
                if (wordCounter==breakTextAt) {newMessage.setCharAt(i,'\n'); wordCounter=0;}
            }

        }

        JOptionPane.showMessageDialog(null,"<html><h1>"+title+"</h1><br>"+newMessage,title,
                JOptionPane.PLAIN_MESSAGE,oregonIcon);
    }

    public void displayHelpMenu() {
        storyTextArea.setText(
                """
                INPUT DIALOGUE BOX HELP MENU
                
                Options available for input dialog box:
                
                H: HELP
                I: INVENTORY
                P: SET PACE
                
                More information can be found in the menu bars at the top of this window.
                """
        );
    }

    //We can use a method to run any daily methods.
    //We should plan on allowing it to be called back-to-back.
    //^This means if anything requires user interaction,
    //execution should be paused! (Otherwise the days
    //will continue!) Dialogue boxes should handle
    //this. I hope.

    /**
     * Progress the game by 1 day. Each day:
     * - The date advanced by 1 day
     * - The weather is randomized
     * - Happiness decreases if any characters are sick
     * - Any sick characters undergo their ramifications
     * The game info gets updated.
     */
    public void travel() {
        sickCharacters = countSickCharacters();
        weatherAffectPlayer();
        impactHappiness();
        date.advanceDate();
        weather.setRandomWeather();
        if (sickCharacters>0) {handleSickCharacters();}
        writeGameInfo();
        killPlayer();
        checkIfLost();
        doStoryLine();
        //anything else that changes on the day.
    }

    /**
     * Travel until the player says otherwise. A day passes every second.
     */
    public void continuousTravel() {
        if (!isTraveling) {travelClock.start(); isTraveling=true;}
        else {travelClock.stop(); isTraveling=false;}

    }

    /**
     * Explicitly stop continuous travel. This method is not
     * used by the player; rather, it is used in-code if the
     * player must stop travelling.
     */
    public void stopContTravel() {
        travelClock.stop();
        isTraveling= false;
    }
    //Check for scenarios to continue the story line.
    //Most things happen based on distance+location
    //Journal entries can appear based on the date.
    //When modifying this code, do so in chronological order
    //instead of just appending to the end for readability.

    //Important! If loading a scene, remember to stop continuous travel first! (stopContTravel())
    public void doStoryLine() {
        //Journal for 3/19/1861
        if (date.toString().equals("March 19, 1861")) {
            stopContTravel();
            scene.loadScene("1861-3-19");
        }

        //Journal for 3/20/1861
        if (date.toString().equals("March 20, 1861")) {
            stopContTravel();
            scene.loadScene("1861-3-20");
        }
    }
    /**
     * Writes all game info to a text area. It includes:
     * The player's current location
     * The current date
     * The current weather conditions
     * The party's happiness level
     * The current pace
     * The current rations
     */
    //TODO: Implement Strings for location, pace, and rations
    //Call this function whenever the game info is updated.
    public void writeGameInfo() {
        String gameInfo = """
                Location: $location
                Date: $date
                -----------------
                Weather: $weather
                Party Happiness: $happiness
                Pace: $pace
                Rations: $rations
                """;
        gameInfo = gameInfo.replace("$location","LOCATION FIXME");
        gameInfo = gameInfo.replace("$date",date.toString());
        gameInfo = gameInfo.replace("$weather",weather.toString());
        gameInfo = gameInfo.replace("$happiness",Integer.toString(happiness));
        gameInfo = gameInfo.replace("$pace",currPaceToString());
        gameInfo = gameInfo.replace("$rations", String.valueOf(getFood()));
        storyTextArea.setText(gameInfo);
        updateStats();
    }

    /**
     * Calculate happiness to add or subtract. This method ensures that
     * happiness won't exceed 100 or become less than 0.
     * @param amount - The amount of happiness to add or subtract. (Positive = add; negative = subtract)
     * @return the amount of happiness to add or subtract.
     */
    public int calculateHappiness(int amount) {
        if (happiness - amount < 0) { happiness = 0; }
        else if (happiness + amount > 100) { happiness = 100; }
        else happiness += amount;
        return happiness;
    }

    /**
     *Impact happiness based on factors.
     * Good weather increases happiness by 5; bad weather decreases it by 5.
     */
    //Figured "setHappiness" wasn't appropriate. Is there a better name we can use?
    //Should each factor have its own method?
    public void impactHappiness() {
        //Weather
        if (weather.getWeatherCondition().equals("Good")) {happiness = calculateHappiness(5);}
        else if (weather.getWeatherCondition().equals("Bad")) {happiness = calculateHappiness(-5);}
        //Player is ill
        happiness = calculateHappiness(-2*sickCharacters);
    }

    /**
     * If a given character is not wearing protective clothing, they will be harmed by
     * cold weather. Their health will decrease by 25 HP each day, and they have a 1/4
     * chance of getting ill.
     */
    public void weatherAffectPlayer() {
        for (Character character : characterArrayList) {
            if (!character.getHasClothing()) {
                character.setHealth(character.getHealth() - 25);
                if (rand.nextInt(4) == 0) {
                    character.setSick(true);
                }
            }
        }


    }

    /**
     * Check if the game is lost. The game is lost if:
     * - there are no healthy oxen
     * - the wagon breaks
     * - the party's happiness equals 0
     * - everyone dies
     * - no one has food available for 3 consecutive days.
     * @return false if the game is not lost, true if the game is lost
     */

    //TODO: Lack of food for 3 days
    public boolean checkIfLost() {
        boolean isLost = false;
        String message = "";

        //If there are no healthy oxen
        //TODO: Re-implement oxen system
        //If the wagon breaks
        if (wagon.getState() == 2) {
            message= "Your wagon broke. You can't continue";
            isLost = true;
        }

        //If party happiness == 0
        else if (happiness <= 0) { //Using "less than" in case it somehow goes below 0.
            message = "Everyone is beyond sad. Happiness is but a memory. You can't continue.";
            isLost = true;
        }

        //If everyone is dead
        else if (hattie.getHealth()<=0 && charles.getHealth() <= 0 && augusta.getHealth() <= 0 && ben.getHealth() <= 0
                && jake.getHealth() <= 0) {
            message = "Everyone literally died. Ghosts don't go to Oregon. You can't continue.";
            isLost = true;
        }

        String[] gameOverChoices = {"Play again","Main Menu"};
        if (isLost) {
            if (JOptionPane.showOptionDialog(null,message,"Game Over",
                    JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,gameOverChoices,
                    null)==JOptionPane.YES_OPTION) {
                resetGame();
            }
            else {
                JOptionPane.showMessageDialog(null,"FIXME: Main Menu not implemented.");
            }
        }
        //if (isLost){JOptionPane.showMessageDialog(null,message,"Game Over",JOptionPane.PLAIN_MESSAGE);}
        return isLost;

    }

    /**
     * Count the number of sick characters. For each sick character, 2 happiness is lost.
     * @return
     */
    public int countSickCharacters() {
        int counter = 0;
        for (Character character : characterArrayList) {
           if(character.isSick()) {
               counter++;
           }
        }
        return counter;
    }

    /**
     * Handles sickness for all characters. Each day a character is sick, they lose 5 HP.
     * They are cured naturally after 5 days.
     */
    public void handleSickCharacters() {
        for (Character character : characterArrayList) {
            if (character.getDaysSick()>=5) {
                character.setSick(false);
                JOptionPane.showMessageDialog(null, character.getName()+" has been cured!",character.getName()+"'s Illness",JOptionPane.PLAIN_MESSAGE);
            }
            if (character.isSick()) {
                character.setDaysSick(character.getDaysSick()+1);
                character.setHealth(character.getHealth() - 5);
            }
        }
    }

    /**
     * Check the Oxen ArrayList to see if all are injured.
     * @return true if all oxen are injured, false if at least one is not.
     */
    /*
    public boolean checkAllOxenInjured() {

        boolean allInjured = true;
        if (!oxenArrayList.isEmpty()) {
            for (Oxen oxen : oxenArrayList) {
                if (!oxen.isInjured()) {

                    allInjured = false;
                }
            }
        }
        else {allInjured= false;}

        return allInjured;
    }

     */

    private ActionListener menuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (userInput.getText().equalsIgnoreCase("E")) {
                exitGame();
            }
            else if (userInput.getText().equalsIgnoreCase("P")) {
                inMenu = false;
                introScene();
                userInput.removeActionListener(menuListener);

            }
            userInput.setText("");
        }
    };

    private void displayMainMenu() {
    storyTextArea.setText(
            """
            MAIN MENU
            
            P: PLAY THE GAME, TRAVEL THE OREGON TRAIL
            E: EXIT
            """
        );
    }

    private ActionListener gameMenu = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (userInput.getText().equalsIgnoreCase("I")) {
                openInventory();
            }
            else if (userInput.getText().equalsIgnoreCase("H")) {
                displayHelpMenu();
            }
            else if (userInput.getText().equalsIgnoreCase("P")) {
                currentPace = setPace();
                writeGameInfo();
            }
            else if (userInput.getText().equalsIgnoreCase("T")) {
                travel();
            }
            else if (userInput.getText().equalsIgnoreCase("C")) {
                continuousTravel();
            }
            userInput.setText("");
        }
    };

    private void openInventory() {
        Inventory inv = new Inventory(this);
        inv.pack();
        inv.setVisible(true);
    }

    private void openShop() {
        Shop shop = new Shop(this);
        shop.pack();
        shop.setVisible(true);
    }

    //check broke
    private int setPace() {
        int newPaceInt = 0;
        String newPace = JOptionPane.showInputDialog("Please set a new pace:\n1 - Steady\n2 - Strenuous\n3 - Grueling");
        if (newPace.equals("1") || newPace.equals("2") || newPace.equals("3")) {
            newPaceInt = Integer.parseInt(newPace)-1;
        }
        else {newPaceInt = setPace();}
        return newPaceInt;
    }

    private static ActionListener returnMainMenuItem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO: Reset image to main menu, character states to 0, location to 0, date to 0, money to 0, etc.
            inGame = false;
            inMenu = true;
        }
    };

    private static ActionListener exitMenuItem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            exitGame();
        }
    };

    private static ActionListener projDescMenuItem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showAbout("The Oregon Trail is an educational game to teach students about the 1800s trip from " +
                    "Missouri to Oregon. This project aims to recreate this experience using the Java programming " +
                    "language. With incredible high-resolution graphics, the experience is more immersive than ever " +
                    "before",
                    "Project Description");
        }
    };

    private static ActionListener aboutProjMenuItem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showAbout("This project was completed by Team Hollenberg Station, consisting of Aleece Al-Olimat, " +
                    "Ken Zhu, and PJ Oschmann",
                    "About This Project");
        }
    };

    private static ActionListener aboutHattieMenuItem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO: fill this out with persona/storyboarding
            showAbout("(Replace me with actual text) Hattie is a young lass who is setting out for a new life " +
                    "in Oregon. Her twin sister died. What a shame.",
                    "About Hattie");
        }
    };

    private static ActionListener imgCredMenuItem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showAbout("Images created by Aleece Al-Olimat.\nImage of Oregon by Kendra of Clker.com, in the " +
                    "Public Domain.",
                    "Image Credits");
        }
    };

    private static ActionListener winMenuItem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showAbout("The main goal of The Oregon Trail is to simulate travel across the country from the " +
                    "Mideast to Oregon City, Oregon. The way you win this game is if you have at least one party member" +
                    " alive by the time you get to Oregon City.",
                    "How do I win in the Oregon Trail Game?");
        }
    };

    private static ActionListener playMenuItem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showAbout("The Oregon Trail game can be fully played purely with text input. There is an text input " +
                    "box usually in the bottom right area of the screen where you can type a letter and press enter to" +
                    "input an action or selection. There are also some dropdown menus in the Shop and Inventory that you " +
                    "can navigate with your mouse for easier access. \n Ultimately, you will need to use items to keep" +
                    " your party members healthy, happy, and alive until you reach your destination.",
                    "How do I play The Oregon Trail?");
        }
    };

    private static ActionListener loseMenuItem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showAbout("The Oregon Trail has many perilous obstacles that can cause you to lose the game. These " +
                    "obstacles are historically accurate and are difficulties pioneers who traversed the country in the " +
                    "mid 1800s likely faced when traveling across the country. Ranging from bad weather, starvation, " +
                    "disease, injury and more, you lose the game if your party can no longer travel. This means running " +
                    "out of food, all party members dying, your wagon breaking from use and damage, the party giving up, " +
                    "or having no oxen left to pull your cart.","How do I lose The Oregon Trail?");
        }
    };

    private FocusAdapter inputHelp = new FocusAdapter() { //Grey text for input box when not focused on
        @Override
        public void focusGained(FocusEvent e) {
            if (userInput.getText().trim().equals("Enter 'H' to display input options")) {
                userInput.setText("");
                userInput.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (userInput.getText().trim().equals("")) {
                userInput.setText("Enter 'H' to display input options");
                userInput.setForeground(new Color(147, 147,147));
            }
        }
    };

    //Be sure this respects defaults should any be changed during development!
    public void resetGame() {
        hattie  = new Character("Hattie Campbell", 100, 0);
        charles = new Character("Charles",100,0);
        augusta = new Character("Augusta",100,0);
        ben = new Character("Ben",100,0);
        jake = new Character("Jake",100,0);
        characterArrayList = new ArrayList<>(List.of(hattie,charles,augusta,ben,jake));
        //allCharacters = new ArrayList<>(List.of(hattie,charles,augusta,ben,jake));
        food = 0; ammunition = 0; medicine = 0; clothes = 0; wagonTools = 0; splints = 0; oxen = 0;
        isGameWon = false; isGameLost = false;
        happiness=100;
        weather = new Weather();
        wagon = new Wagon();
        date = new Date();
        date.setDate(3,18,1861);
        isTraveling = false;
        introScene();
        writeGameInfo();

    }

    public String currPaceToString() {
        String pace;
        if (currentPace==0) {pace="Steady";}
        else if (currentPace==1) {pace="Strenuous";}
        else {pace="Grueling";}
        return pace;
    }

    //TODO: IMPLEMENT THIS ON SETTING PACE IN MAIN
    public void setCurrentPace(int newPace) {
        this.currentPace = newPace;
    }

    //IMPLEMENT ONE DAY GRACE PERIOD BEFORE DYING
    public void killPlayer() {
        for (Character character : characterArrayList) {
            if (character.getHealth() <=0) {
                JOptionPane.showMessageDialog(null,character.getName()+" died.");
                updateStats();
            }
        }

    }
    ArrayList<JTextPane> arrayOfPanes = new ArrayList<>(List.of(hattieStats,charlesStats,augustaStats,benStats,jakeStats));

    /**
     * Update the status of each player. If the player is dead, their status is marked "DEAD."
     */
    public void updateStats() {
        int characterIndex = 0;
        String statsText = """
                HP: $HP
                Clothing: $Clothing
                Healthiness: $Healthiness
                Injured: $Injured
                """;
        for (JTextPane stats : arrayOfPanes) {
            if (characterArrayList.get(characterIndex).getHealth() <= 0) {
                stats.setText("DEAD");

            } else {
                String newText = statsText;
                newText = newText.replace("$HP", Integer.toString(characterArrayList.get(characterIndex).getHealth()));
                newText = newText.replace("$Clothing", characterArrayList.get(characterIndex).hasClothingToString());
                newText = newText.replace("$Healthiness",characterArrayList.get(characterIndex).isSickToString());
                newText = newText.replace("$Injured",characterArrayList.get(characterIndex).isInjuredToString());
                stats.setText(newText);

            }
            characterIndex++;
        }
    }

    public ArrayList<Character> getCharacterArrayList() {
        return characterArrayList;
    }

    public void setCharacterArrayList(ArrayList<Character> characterArrayList) {
        this.characterArrayList = characterArrayList;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    public int getMedicine() {
        return medicine;
    }

    public void setMedicine(int medicine) {
        this.medicine = medicine;
    }

    public int getClothes() {
        return clothes;
    }

    public void setClothes(int clothes) {
        this.clothes = clothes;
    }

    public int getWagonTools() {
        return wagonTools;
    }

    public void setWagonTools(int wagonTools) {
        this.wagonTools = wagonTools;
    }

    public int getSplints() {
        return splints;
    }

    public void setSplints(int splints) {
        this.splints = splints;
    }

    public int getOxen() {
        return oxen;
    }

    public void setOxen(int oxen) {
        this.oxen = oxen;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

}

