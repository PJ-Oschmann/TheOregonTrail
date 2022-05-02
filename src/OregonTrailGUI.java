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
    public JTextArea storyTextArea;
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
    private Activities activities;

    //Our players
    private Character hattie = new Character("Hattie Campbell", 100, 0, false);
    private Character charles = new Character("Charles",100,0, true);
    private Character augusta = new Character("Augusta",100,0, true);
    private Character ben = new Character("Ben",100,0, false);
    private Character jake = new Character("Jake",100,0, false);

    public ArrayList<Character> characterArrayList = new ArrayList<>(List.of(hattie,charles,augusta,ben,jake));

    //game variables
    private int money = 200, food = 0, ammunition = 0, medicine = 0, clothes = 0, wagonTools = 0, splints = 0, oxen = 4,
    currentPace = 0, sickCharacters = 0, dailyActions = 2, happiness = 75;

    private boolean isGameWon = false, isGameLost = false;
    private Weather weather = new Weather();
    private Wagon wagon = new Wagon();
    private Date date = new Date();
    private boolean isTraveling = false;
    private static boolean inMenu = true;
    private static boolean inGame = false;
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
        game.addUIMenuBar(frame);
        frame.setVisible(true);
    }

    /**
     * Constructor for OregonTrailGUI
     */
    //Create application
    public OregonTrailGUI() {
        userInput.addFocusListener(playHelp);
        if(inMenu) {
            ImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/mainMenu.png"));
            displayMainMenu();
            userInput.addActionListener(menuListener);
        }
    }

    /**
     * Sets the application's UI to follow the System's look and feel,
     * instead of using the default Metal theme.
     * On Windows, native Windows (win32) elements are used.
     * On Linux, the GTK theme is used.
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
    public void addUIMenuBar(JFrame frame){
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
        JMenuItem aboutOregonTrail = new JMenuItem("About The Oregon Trail");
        JMenuItem aboutHattie = new JMenuItem("About Hattie Campbell");
        JMenuItem imageCredits = new JMenuItem("Image Credits");
        menuAbout.add(projectDescription);
        menuAbout.add(aboutProject);
        menuAbout.add(aboutOregonTrail);
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
        aboutOregonTrail.addActionListener(aboutTrailMenuItem);
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
        scene.loadScene("intro");
        inGame = true;
        Activities activities = new Activities(this);
        updateStats();
        loadStatusPanels();
        openShop();
        userInput.addActionListener(gameMenu);
        userInput.removeFocusListener(playHelp);
        userInput.addFocusListener(gameHelp);
        ImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/mainGame.png"));
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

    private void hideStatusPanels() {
        HattiePanel.setVisible(false);
        CharlesPanel.setVisible(false);
        AugustaPanel.setVisible(false);
        BenPanel.setVisible(false);
        JakePanel.setVisible(false);
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
                T: TRAVEL ONE DAY
                D: DAILY ACTIONS (resets each travel)
                
                ABOUT PACE:
                Hunger is increased respectively by travel speed (not including
                status ailment effects) on characters. 1, 2, or 3 hunger is added
                depending on the speed you are traveling.
                
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
        openRandomEvent();
        dailyHealthBoost(5);
        staticMethods.incrementNFC();
        sickCharacters = countSickCharacters();
        weatherAffectPlayer();
        impactHappiness();
        date.advanceDate();
        weather.setRandomWeather();
        if (sickCharacters>0) {handleSickCharacters();}
        writeGameInfo();
        killPlayer();
        checkIfLost();
        oxenInjured();
        doStoryLine();
        //anything else that changes on the day.
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
            scene.loadScene("1861-3-19");
        }

        //Journal for 3/20/1861
        if (date.toString().equals("March 20, 1861")) {
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
     * The remaining daily actions available
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
                Daily Actions Available: $daily actions
                """;
        gameInfo = gameInfo.replace("$location","LOCATION FIXME");
        gameInfo = gameInfo.replace("$date",date.toString());
        gameInfo = gameInfo.replace("$weather",weather.toString());
        gameInfo = gameInfo.replace("$happiness",Integer.toString(happiness));
        gameInfo = gameInfo.replace("$pace",currPaceToString());
        gameInfo = gameInfo.replace("$rations", String.valueOf(getFood()));
        gameInfo = gameInfo.replace("$daily actions", Integer.toString(dailyActions));
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

    public void calculateHealth(Character character, int value) {
        int newHealth = 0;
        if (value>=0) {
            if (character.getHealth() + value > 100) {
                newHealth = 100;
            } else {
                newHealth = character.getHealth() + value;
            }
        }
        if (value<0) {
            if (character.getHealth() + value < 0) {
                newHealth=0;
            }
            else {
                newHealth = character.getHealth() + value;
            }
        }
        character.setHealth(newHealth);
    }

    public int calculateFood(int value) {
        int newFood = 0;
        if (value >=0) {
            newFood = food+value;
        }
        else {
            if (food+value<0) {newFood=0;}
            else {
                newFood=food+value;
            }
        }
        return newFood;
    }

    public void dailyHealthBoost(int value) {
        for (Character character : characterArrayList) {
            calculateHealth(character, value);
        }
    }
    /**
     * If a given character is not wearing protective clothing, they will be harmed by
     * cold weather. Their health will decrease by 25 HP each day, and they have a 1/4
     * chance of getting ill.
     */
    public void weatherAffectPlayer() {
        if (weather.getWeatherCondition()=="Bad") {
            for (Character character : characterArrayList) {
                if (!character.getHasClothing()) {
                    character.setHealth(character.getHealth() - 25);
                    if (rand.nextInt(4) == 0) {
                        character.setSick(true);
                        character.setDaysSick(0);
                        JOptionPane.showMessageDialog(null, String.format("%s has gotten sick.\n" +
                                        "Use medicine to cure them!", character.getName()), "Someone got sick",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
    }

    private void oxenInjured() {
        boolean injuredOxen = false;
        boolean lessThanFour = false;
        Random rand = new Random();
        int rng = rand.nextInt(100);

        if (oxen > 3) {
            if (rng < 5) {
                injuredOxen = true;
            }
        }
        else {
            lessThanFour = true;
            if (rng < 15) {
                injuredOxen = true;
            }
        }
        String injuredOxenName = ReadText.generateOxenName();
        String[] consumeOxenChoices = {String.format("Harvest %s", injuredOxenName), String.format("Leave %s be", injuredOxenName)};
        if (injuredOxen && lessThanFour) {
            int consumeOxen = travelingOxenInjured(injuredOxenName, consumeOxenChoices);
            oxen -= 1;
            if (consumeOxen == 0) {
                consumeInjuredOxen(injuredOxenName);
            }
            else {
                leaveOxenBe(injuredOxenName);
            }
            callPETA();
        }
        else if (injuredOxen) {
            int consumeOxen = travelingOxenInjured(injuredOxenName, consumeOxenChoices);
            oxen -= 1;
            if (consumeOxen == 0) {
                consumeInjuredOxen(injuredOxenName);
            }
            else {
                leaveOxenBe(injuredOxenName);
            }
        }
    }

    private int travelingOxenInjured(String oxenName, String[] choices) {
        return JOptionPane.showOptionDialog(null,String.format("%s the oxen was injured while " +
                        "you were traveling today. You put %s out of their misery. You can choose to leave " +
                        "%s on the side of the road, or harvest their corpse for 10 units of food.",
                oxenName, oxenName, oxenName),"INJURED OXEN", JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,
                null, choices, null);
    }

    private void consumeInjuredOxen(String oxenName) {
        JOptionPane.showMessageDialog(null, String.format("You chose to make the most out of %s's" +
                        "time here with us. You gain 10 units of food.\nRest in peace(s of food) %s.", oxenName, oxenName),
                String.format("RIP %s the oxen", oxenName), JOptionPane.INFORMATION_MESSAGE);
        food += 10;
    }

    private void leaveOxenBe(String oxenName) {
        JOptionPane.showMessageDialog(null, String.format("You chose to leave %s's corpse alone" +
                        "and let nature take its course.\nRest in peace %s.", oxenName, oxenName),
                String.format("RIP %s the oxen", oxenName), JOptionPane.PLAIN_MESSAGE);
    }

    private void callPETA() {
        JOptionPane.showMessageDialog(null, "Your oxen's chance of injury when traveling is " +
                "significantly higher if you have less than 4 oxen to pull the wagon. Please buy more oxen" +
                "before we call PETA for animal abuse.", "OXEN DEFICIENCY", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Check if the game is lost. The game is lost if:
     * - there are no healthy oxen
     * - the wagon breaks
     * - the party's happiness equals 0
     * - everyone dies
     * - no one has food available for 3 consecutive days.
     */
    private void checkIfLost() {
        boolean isLost = false;
        String message = "";

        //Lack of food for 3 days
        if (staticMethods.getNFC() >= 3) {
            isLost = true;
            message = "Your party has starved to death from a lack of food.";
        }

        //If there are no healthy oxen
        //TODO: Re-implement oxen system
        else if (oxen < 0) {
            isLost = true;
            message = "You have no more healthy oxen to pull your wagon. You can't continue.";
        }

        //If the wagon breaks
        else if (wagon.getState() == 2) {
            message = "Your wagon broke. You can't continue";
            isLost = true;
        }

        //If party happiness == 0
        else if (happiness <= 0) { //Using "less than" in case it somehow goes below 0.
            message = "Everyone is beyond sad. Happiness is but a memory. You can't continue.";
            isLost = true;
        }

        //If the adults are dead
        else if (augusta.getHealth()==0 && charles.getHealth()==0) {
            message = "Augusta and Charles died. Without the guidance of their elders, the young-lings find themselves " +
                    "confused. You can't continue";
            isLost = true;
        }

        //If everyone is dead
        else if (hattie.getHealth()<=0 && charles.getHealth() <= 0 && augusta.getHealth() <= 0 && ben.getHealth() <= 0
                && jake.getHealth() <= 0) {
            message = "Everyone literally died. Ghosts don't go to Oregon. You can't continue.";
            isLost = true;
        }
        gameOver(isLost, message);
    }

    private void gameOver(boolean lose, String msg) {
        if (lose) {
            JOptionPane.showMessageDialog(null, msg, "YOU LOSE", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Count the number of sick characters. For each sick character, 2 happiness is lost.
     * @return
     */
    private int countSickCharacters() {
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
                JOptionPane.showMessageDialog(null, character.getName()+" has been cured!",
                        character.getName()+"'s Illness",JOptionPane.INFORMATION_MESSAGE);
            }
            if (character.isSick()) {
                character.setDaysSick(character.getDaysSick()+1);
                character.setHealth(character.getHealth() - 5);
            }
        }
    }

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
            else {
                staticMethods.notValidInput();
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
            String input = userInput.getText().toUpperCase();
            switch (input) {
                case "I" ->  openInventory();
                case "H" ->  displayHelpMenu();
                case "P" ->  { currentPace = setPace(); writeGameInfo(); }
                case "T" ->  travel();
                case "A" -> activities.activitiesMenu();
                case "/TEST" -> openRandomEvent();
                default -> staticMethods.notValidInput();
            }
        }
    };

    private void openInventory() {
        Inventory inv = new Inventory(this);
        inv.pack();
        inv.setVisible(true);
    }

    private void openRandomEvent() {
        if (rand.nextInt(9)==0) {
            RandomEventGUI reg = new RandomEventGUI(this);
            reg.doEvent();
            reg.pack();
            reg.setVisible(true);
            updateStats();
        }

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

    private ActionListener returnMainMenuItem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
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

    private static ActionListener aboutTrailMenuItem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showAbout("",""); //Insert text here for about trail menu item
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

    private FocusAdapter gameHelp = new FocusAdapter() { //Grey text for input box when not focused on
        @Override
        public void focusGained(FocusEvent e) {
            userInput.setText("");
            userInput.setForeground(Color.BLACK);
        }

        @Override
        public void focusLost(FocusEvent e) {
            userInput.setText("Enter 'H' to display input options");
            userInput.setForeground(new Color(147, 147,147));
        }
    };

    private FocusAdapter playHelp = new FocusAdapter() { //Grey text for input box when not focused on
        @Override
        public void focusGained(FocusEvent e) {
                userInput.setText("");
                userInput.setForeground(Color.BLACK);
        }

        @Override
        public void focusLost(FocusEvent e) {
                userInput.setText("Enter 'P' to Travel the Trail!");
                userInput.setForeground(new Color(147, 147,147));
        }
    };

    //Be sure this respects defaults should any be changed during development!
    public void resetGame() {
        hattie  = new Character("Hattie Campbell", 100, 0,false);
        charles = new Character("Charles",100,0, true);
        augusta = new Character("Augusta",100,0, true);
        ben = new Character("Ben",100,0, false);
        jake = new Character("Jake",100,0, false);
        characterArrayList = new ArrayList<>(List.of(hattie,charles,augusta,ben,jake));
        //allCharacters = new ArrayList<>(List.of(hattie,charles,augusta,ben,jake));
        money = 200; food = 0; ammunition = 0; medicine = 0; clothes = 0; wagonTools = 0; splints = 0; oxen = 4;
        isGameWon = false; isGameLost = false; inMenu = true; inGame = false;
        happiness = 75;
        weather = new Weather();
        wagon = new Wagon();
        date = new Date();
        date.setDate(3,18,1861);
        isTraveling = false;

        userInput.addFocusListener(playHelp);
        if(inMenu) {
            ImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/MainMenu.png"));
            displayMainMenu();
            userInput.addActionListener(menuListener);
            hideStatusPanels();
        }
    }

    public String currPaceToString() {
        String pace;
        if (currentPace==0) {pace="Steady";}
        else if (currentPace==1) {pace="Strenuous";}
        else {pace="Grueling";}
        return pace;
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
                Hunger: $Hunger
                """;
        for (JTextPane stats : arrayOfPanes) {
            if (characterArrayList.get(characterIndex).getHealth() <= 0) {
                stats.setText("DEAD");

            } else {
                String newText = statsText;
                newText = newText.replace("$HP", Integer.toString(characterArrayList.get(characterIndex).getHealth()));
                newText = newText.replace("$Clothing", characterArrayList.get(characterIndex).hasClothingToString());
                newText = newText.replace("$Healthiness", characterArrayList.get(characterIndex).isSickToString());
                newText = newText.replace("$Injured", characterArrayList.get(characterIndex).isInjuredToString());
                newText = newText.replace("$Hunger", String.valueOf(characterArrayList.get(characterIndex).getHunger()));
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

    public int getDailyActions() {
        return dailyActions;
    }

    public void setDailyActions(int dailyActions) {
        this.dailyActions = dailyActions;
    }

    public Wagon getWagon() {
        return wagon;
    }

    public void setWagon(Wagon wagon) {
        this.wagon = wagon;
    }
}

