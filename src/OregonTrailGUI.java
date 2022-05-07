import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.swing.Timer;

public class OregonTrailGUI {
    private JPanel MainPanel;
    private JPanel IMGPanel;
    private JLabel ImageLabel;
    private JPanel BottomPanel;
    private JPanel GeneralPanel;
    private JPanel BenPanel;
    private JPanel AugustaPanel;
    private JPanel CharlesPanel;
    private JPanel HattiePanel;
    public JTextArea storyTextArea;
    private JPanel JakePanel;
    public JTextField userInput;
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
    public final Random rand = new Random();
    private final Activities activities = new Activities(this);
    private final Location location = new Location(this);
    //Our players
    private Character hattie = new Character("Hattie Campbell", 100, 0, false);
    private Character charles = new Character("Charles",100,0, true);
    private Character augusta = new Character("Augusta",100,0, true);
    private Character ben = new Character("Ben",100,0, false);
    private Character jake = new Character("Jake",100,0, false);

    public ArrayList<Character> characterArrayList = new ArrayList<>(List.of(hattie,charles,augusta,ben,jake));

    private ArrayList<JTextPane> arrayOfPanes = new ArrayList<>(List.of(hattieStats,charlesStats,augustaStats,benStats,jakeStats));

    //game variables
    private int money = 200, food = 0, ammunition = 0, medicine = 0, clothes = 0, wagonTools = 0, splints = 0, oxen = 4,
    currentPace = 0, sickCharacters = 0, injuredCharacters = 0, dailyActions = 2, happiness = 75;
    private boolean godModeOn;
    private Weather weather = new Weather();
    public Wagon wagon = new Wagon();
    private Date date = new Date();
    private static boolean inMenu = true, inGame = false, isTraveling = false, isGameWon = false, isGameLost = false;
    private boolean gameMenuAL, menuAL;
    private RandomEventGUI reg;
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
        frame.setTitle("THE OREGON TRAIL: IN A WOMAN'S VOICE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Give the application the System's theme.
        //Delete this line if the app won't start
        setTheme();

        frame.pack();
        game.addUIMenuBar(frame);
        frame.setVisible(true);
    }

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
     * On MacOS, the MacOS theme is used.
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

    /**
     * Adds a menu bar to the GUI. Contains "Main," "About," and "Help" tabs. Main allows you to return to the main
     * menu or exit the game. "About" contains the project description, project information, information about the
     * oregon trail, and information about Hattie Campbell.
     * @param frame
     */
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

    /**
     * Intro scene. The game is set to the "inGame" state and the previous "inMenu" state is disabled. A tutorial is
     * presented and its image is set to the main game image. The shop is opened for players to gather resources,
     * set to the main game image, and the shop is opened. Each character's status is then displayed in the main UI,
     * and their default stats are displayed. The information text area on the left of the UI is set to display in-game
     * information.
     */
    private void introScene() {
        scene.loadScene("intro");
        inGame = true;
        inMenu = false;
        ImageLabel.setIcon(new javax.swing.ImageIcon("assets/images/mainGame.png"));
        openShop();
        loadStatusPanels();
        updateStats();
        reg = new RandomEventGUI(this);
        userInput.addActionListener(gameMenu);
        userInput.removeFocusListener(playHelp);
        userInput.addFocusListener(gameHelp);
        weather.setRandomWeather();
        writeGameInfo();
    }

    /**
     * Shows the character status panels. Called during the intro scene when the player starts the game.
     */
    private void loadStatusPanels(){
        HattiePanel.setVisible(true);
        CharlesPanel.setVisible(true);
        AugustaPanel.setVisible(true);
        BenPanel.setVisible(true);
        JakePanel.setVisible(true);
    }

    /**
     * Hides the status panels. Called when returning to the Main Menu.
     */
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
    private static void showAbout(String message, String title) {
        int breakTextAt = 15; //Where to break the text
        StringBuilder newMessage = new StringBuilder(message);
        int wordCounter = 0;
        for (int i=0;i<message.length();i++) {
            if (message.charAt(i)==' ') {
                wordCounter++;
                if (wordCounter==breakTextAt) {newMessage.setCharAt(i,'\n'); wordCounter=0;}
            }
        }
        JOptionPane.showMessageDialog(null,"<html><h1>"+title+"</h1><br>"+newMessage,title,
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Displays help information in the informational text area on the left side of the screen.
     */
    public void displayHelpMenu() {
        storyTextArea.setText(
                """
                INPUT DIALOGUE BOX HELP MENU
                
                Options available for input dialog box:
                
                H: HELP
                I: INVENTORY
                P: SET PACE
                T: TRAVEL ONE DAY
                A: ACTIVITIES (spend daily actions here, resets each day)
                M: RETURN TO PREVIOUS MENU
                
                ABOUT PACE:
                Hunger is increased respectively by travel speed (not including
                status ailment effects) on characters. 1, 2, or 3 hunger is added
                depending on the speed you are traveling.
                
                More information can be found in the menu bars at the top of this window.
                """
        );
    }

    /**
     * Progress the game by 1 day. Each day:
     * - Daily mileage is incremented.
     * - A random event may occur.
     * - Health increases by 5.
     * - The number of sick characters are counted.
     * - The number of injured characters are counted.
     * - The number of daily actions is reset.
     * - "Bad" weather will negatively affect players.
     * - Happiness is updated based on conditions
     * - The date advanced by 1 day.
     * - The weather is randomized.
     * - The storyline proceeds if the player reaches a significant location.
     * - Characters are checked to see if they're dead.
     * - Sick characters will undergo their ramifications
     * - Injured characters will undergo their ramifications.
     * - The left-hand informational panel is updated
     * - Oxen have a chance of being injured.
     * - The game checks if the player lost the game.
     * - The locaton gets updated.
     */
    public void travel() {
        location.addMileage();
        checkForRandomEvent();
        dailyHealthBoost(5);
        if (!godModeOn) { staticMethods.incrementNFC(); }
        if (godModeOn) { wagon.setState(0); }
        sickCharacters = countSickCharacters();
        injuredCharacters = countInjuredCharacters();
        resetDailies();
        weatherAffectPlayer();
        impactHappiness();
        date.advanceDate();
        weather.setRandomWeather();
        location.doStoryLine();
        checkNewDeaths();
        if (sickCharacters > 0) { handleSickCharacters(); }
        if (injuredCharacters > 0) { handleInjuredCharacters(); }
        writeGameInfo();
        oxenInjured();
        checkIfLost();
        updateLocation();
    }

    /**
     * Checks for new deaths. If a character's health is 0, they are set to "dead."
     */
    public void checkNewDeaths() {
        for (Character character : characterArrayList) {
            if (character.getHealth() <= 0) {
                character.setIsDead(true);
            }
        }
    }

    /**
     * Resets number of daily actions. If any player is injured, this number is set to 1. Otherwise, it is set to 2.
     * The activities' pane is also marked as having been written to so that it can be updated correctly.
     */
    private void resetDailies() {
        boolean isAnyoneInjured = false;
        for (Character character : characterArrayList) {
            if (character.isInjured()){
                isAnyoneInjured = true;
            }
        }
        if (isAnyoneInjured) {
            dailyActions = 1;
        }
        else {
            dailyActions = 2;
        }
        activities.setHasWritten(false);
        if (godModeOn) {
            dailyActions = 1000;
        }
    }

    /**
     * A random event is checked for. If a random event is run, it will occur.
     */
    private void checkForRandomEvent() {
        reg.checkForRandomEvent();
    }

    /**
     * Location is checked to see if the player made it to Oregon City. If they did, the game is won.
     */
    private void updateLocation(){
        //check to see if new distance takes you to a new location
        //methods as appropriate to handle new locations and landmarks
        if (location.getMilesTravd() >= (location.mileMarkers.get(location.mileMarkers.size()- 1))) {
            gameWon();
        }
    }


    /**
     * If the player makes it to Oregon City, the player is notified that they won the game. The game is then reset so
     * the player can play again.
     */
    public void gameWon() {
        //create game win scene + ending monologue
        JOptionPane.showMessageDialog(null, "Congratulation on making it to Oregon City!\nYou" +
                "have succeeded in traveling across the country and migrating West.", "YOU WIN", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    /**
     * Gets the current pace.
     * @return the current pace as an int.
     */
    public int getCurrentPace() {
        return currentPace;
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
        this.storyTextArea.setText(String.format(
                """
                Last Location: %s, %s
                Distance Travelled: %d
                Date: %s
                -----------------
                Weather: %s
                Party Happiness: %d
                Pace: %s
                Rations Available: %d
                Days without Eating: %d/3
                Daily Actions Available: %d
                
                Enter "H" to see available input options.
                """, location.getCurrentLocation(), location.getCurrentState(), location.getMilesTravd(), date.toString(), weather.toString(),
                happiness, currPaceToString(), food, staticMethods.getNFC(), dailyActions
            ));
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
     * Impact happiness based on factors.
     * Good weather increases happiness by 5; bad weather decreases it by 5.
     */
    public void impactHappiness() {
        if (!godModeOn) {
            //Weather
            if (weather.getWeatherCondition().equals("Good")) {
                happiness = calculateHappiness(5);
            } else if (weather.getWeatherCondition().equals("Bad")) {
                happiness = calculateHappiness(-5);
            }
            //Player is ill
            happiness = calculateHappiness(-2 * sickCharacters);
            //Player is injured
            happiness = calculateHappiness(-1 * injuredCharacters);
        }
    }

    /**
     * Calculates the players health to ensure it won't go below 0. If a positive value is passed, the health is
     * increased by the passed value. If this value would exceed 100, the health is set to 100. If a negative value is
     * passed, the health is decreased by that value. If this value would exceed 0, the health is set to 0.
     * @param character - The character whose health is being modified.
     * @param value - The new value to modify the health by.
     */
    public void calculateHealth(Character character, int value) {
        int newHealth = 0;
        if (value>=0) {
            newHealth = Math.min(character.getHealth() + value, 100);
        }
        if (value<0) {
            newHealth = Math.max(character.getHealth() + value, 0);
        }
        character.setHealth(newHealth);
    }

    /**
     * Calculates the number of food to ensure it won't exceed 0 or 100.
     * @param value - the value to modify the amount of food by.
     */
    public void calculateFood(int value) {
        int newFood;
        newFood = food + value;
        food = newFood;
        if (food < 0) { food = 0; }
        else if (food > 100) { food = 100; }
    }

    /**
     * Increases the player's health by the passed value. This method is called every time the player travels.
     * @param value - The value to modify the health by.
     */
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
        if (Objects.equals(weather.getWeatherCondition(), "Bad")) {
            for (Character character : characterArrayList) {
                if (!character.getHasClothing()) {
                    character.setHealth(character.getHealth() - 25);
                    if (rand.nextInt(4) == 0) {
                        character.setSick(true);
                        character.setDaysSick(0);
                    }
                }
            }
        }
    }

    /**
     * Injure the oxen by random chance. There is a 5% chance an oxen will be injured if there is at least 4 oxen, and
     * a 15% chance an oxen will be injured if there are less than 4 oxen. A random oxen name is generated from a text
     * file containing oxen names and the player
     */
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
        if (oxen < 5) { lessThanFour = true; }
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

    /**
     * This method generates an option window to notify the user than one of their oxen were injured. Then, they
     * are given the choice of whether they want to harvest the dead oxen for food or leave it on the side of the road.
     * @param oxenName string-format name of the injured oxen.
     * @param choices the choices the user can select from.
     * @return the choice that the user selects.
     */
    private int travelingOxenInjured(String oxenName, String[] choices) {
        return JOptionPane.showOptionDialog(null,String.format("%s the oxen was injured while " +
                        "you were traveling today. You put %s out of their misery. You can choose to leave " +
                        "%s on the side of the road, or harvest their corpse for 10 units of food.",
                oxenName, oxenName, oxenName),"INJURED OXEN", JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,
                null, choices, null);
    }

    /**
     * This method is called if the user selects to consume/harvest the injured oxen and gain 10 food.
     * @param oxenName String-format name of the oxen.
     */
    private void consumeInjuredOxen(String oxenName) {
        JOptionPane.showMessageDialog(null, String.format("You chose to make the most out of %s's" +
                        "time here with us. You gain 10 units of food.\nRest in peace(s of food) %s.", oxenName, oxenName),
                String.format("RIP %s the oxen", oxenName), JOptionPane.INFORMATION_MESSAGE);
        food += 10;
    }

    /**
     * This method is called if the user selects to leave the oxen be on the side of the road.
     * @param oxenName name of the oxen.
     */
    private void leaveOxenBe(String oxenName) {
        JOptionPane.showMessageDialog(null, String.format("You chose to leave %s's corpse alone" +
                        "and let nature take its course.\nRest in peace %s.", oxenName, oxenName),
                String.format("RIP %s the oxen", oxenName), JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * This method warns the user of the dangers of traveling with fewer than 4 oxen. It is called if an oxen is
     * injured and there are fewer than 4 oxen in the party.
     */
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
    public void checkIfLost() {
        boolean isLost = false;
        String message = "";

        //Lack of food for 3 days
        if (staticMethods.getNFC() >= 3) {
            isLost = true;
            message = "Your party has starved to death from a lack of food.";
        }

        //If there are no healthy oxen
        else if (oxen <= 0) {
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
        else if (areAdultsDead()){
            message = "Augusta and Charles died. Without the guidance of their elders, the young-lings find themselves " +
                    "confused. You can't continue";
            isLost = true;
        }

        //If everyone is dead
        else if (hattie.getIsDead() && charles.getIsDead() && augusta.getIsDead() && ben.getIsDead()
                && jake.getIsDead()) {
            message = "Everyone literally died. Ghosts don't go to Oregon. You can't continue.";
            isLost = true;
        }
        gameOver(isLost, message);
    }

    /**
     * Checks to see if both adults are dead. If condition is true, the player loses
     * @return true if both parents are dead, false if one or both parents are alive
     */
    private boolean areAdultsDead() {
        int deadAdults = 0;
        for (Character character : characterArrayList) {
            if (character.isAdult() && character.getIsDead()) {
                deadAdults++;
            }
        }
        return deadAdults == 2;
    }

    /**
     *  This method is called when the game is lost and pops up a option pane notification to inform the user that they
     *  lost. The resetGame method is called following the defeat of the user/party.
     * @param lose if lose is true then the game is lost.
     * @param msg the message being displayed as to why the party lost.
     */
    private void gameOver(boolean lose, String msg) {
        if (lose) {
            JOptionPane.showMessageDialog(null, msg, "YOU LOSE", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
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
     * Counts the current number of injured characters in the party.
     * @return number of currently  injured characters.
     */
    private int countInjuredCharacters() {
        int counter = 0;
        for (Character character : characterArrayList) {
            if (character.isInjured()) {
                counter++;
            }
        }
        return  counter;
    }

    /**
     * Handles sickness for all characters. Each day a character is sick, they lose 5 HP.
     * They are cured naturally after 5 days.
     */
    private void handleSickCharacters() {
        for (Character character : characterArrayList) {
            if (character.getDaysSick() >= 5) {
                character.setSick(false);
                JOptionPane.showMessageDialog(null, character.getName()+" is no longer sick!",
                        character.getName()+"'s Illness",JOptionPane.INFORMATION_MESSAGE);
            }
            if (character.isSick()) {
                character.setDaysSick(character.getDaysSick()+1);
                character.setHealth(character.getHealth() - 5);
            }
        }
    }

    /**
     * This method is called to progress the injury status of each characters who are injuries.
     */
    private void handleInjuredCharacters() {
        for (Character character : characterArrayList) {
            if (character.getDaysInjured() >= 14) {
                character.setInjured(false);
                JOptionPane.showMessageDialog(null, character.getName()+" is no longer injured!",
                        character.getName()+ "'s Injury", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public ActionListener menuListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            menuAL = true;
            if (userInput.getText().equalsIgnoreCase("E")) {
                exitGame();
            }
            else if (userInput.getText().equalsIgnoreCase("P")) {
                inMenu = false;
                introScene();
                menuAL = false;
                userInput.removeActionListener(menuListener);
            }
            else {
                staticMethods.notValidInput();
            }
            userInput.setText("");
        }
    };

    /**
     * This method prints out the main menu screen where the user can select if they want to play the game
     * or exit the application.
     */
    private void displayMainMenu() {
    storyTextArea.setText(
            """
            MAIN MENU
            
            P: PLAY THE GAME, TRAVEL THE OREGON TRAIL
            E: EXIT
            """
        );
    }

    /**
     * This method triggers GodMode for the user and their party and is mostly used for debugging more than
     * other reasons.
     */
    private void thanos() {
        JOptionPane.showMessageDialog(null,"God Mode On!", "r/THANOSDIDNOTHINGWRONG",
                JOptionPane.INFORMATION_MESSAGE);
        this.writeGameInfo();
        money = 999999;
        oxen = 999999;
        food = 999999;
        ammunition = 999999;
        medicine = 999999;
        clothes = 999999;
        wagonTools = 999999;
        splints = 999999;
        godModeOn = true;
        currentPace = 999999;
        dailyActions = 1000;
    }

    public ActionListener gameMenu = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameMenuAL = true;
            String input = userInput.getText().toUpperCase();
            switch (input) {
                case "I" -> openInventory();
                case "H" -> displayHelpMenu();
                case "P" -> { currentPace = setPace(); writeGameInfo(); }
                case "T" -> travel();
                case "A" -> {
                    activities.displayActivityMenu();
                }
                case "F" -> reg.forceRandomEvent();
                case "M" -> writeGameInfo();
                //For debugging
                case "THANOS" -> {
                    for (Character character : characterArrayList) {
                        character.godMode();
                    }
                    thanos();
                }
                default -> staticMethods.notValidInput();
            }
            userInput.setText("");
        }
    };

    /**
     * This is the method called to create and open the inventory dialogue form.
     */
    private void openInventory() {
        Inventory inv = new Inventory(this);
        inv.pack();
        inv.setVisible(true);
    }

    /**
     * This is the method called to create and open the shop dialogue form
     */
    public void openShop() {
        Shop shop = new Shop(this);
        shop.pack();
        shop.setVisible(true);
    }

    /**
     * This method is called when the user wants to reselect the pace that the party is traveling at.
     * @return the pace the user wants the party to travel at.
     */
    private int setPace() {
        int newPaceInt;
        String newPace = JOptionPane.showInputDialog("Please set a new pace:\n1 - Steady\n2 - Strenuous\n3 - Grueling");
        if (newPace.equals("1") || newPace.equals("2") || newPace.equals("3")) {
            newPaceInt = Integer.parseInt(newPace) - 1;
        }
        else {newPaceInt = setPace();}
        return newPaceInt;
    }

    /**
     * Menu bar action listeners
     */
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
                    "before. Further, we aim to be inclusive of different groups and ethnicities that were prominent " +
                    "in the actual Oregon Trail Game but were discriminated against or stereotyped in the original game.",
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
            showAbout("The Oregon Trail was a roughly 2000-mile route from Independence, Missouri, to Oregon City," +
                    "Oregon, which was used by hundreds of thousands of American pioneers and relocated Native Americans" +
                    "in the mid-19th century to emigrate West.\n\nThe game follows Hattie Campbell, a young girl migrating with" +
                    "her family across the country in hopes of a new and prosperous future.", "About the Trail");
        }
    };
    private static ActionListener aboutHattieMenuItem = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO: fill this out with persona/storyboarding
            showAbout("Hattie Campbell is a 13 year old girl who's about to embark on a life-changing journey. " +
                            "Hattie and her family are traveling along the Oregon Trail, ",
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

    /**
     * inputfield focus adapters that point the user to input their selections into the textfield.
     */
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
        money = 200; food = 0; ammunition = 0; medicine = 0; clothes = 0; wagonTools = 0; splints = 0; oxen = 4;
        isGameWon = false; isGameLost = false; inMenu = true; inGame = false;
        happiness = 75;
        weather = new Weather();
        wagon = new Wagon();
        date = new Date();
        date.setDate(3,18,1861);
        isTraveling = false;
        location.setMilesTravd(0);
        location.setMarkerCounter(0);
        staticMethods.resetNFC();
        if (gameMenuAL) {
            userInput.removeActionListener(gameMenu);
        }
        if (menuAL) {
            userInput.removeActionListener(menuListener);
        }

        userInput.addFocusListener(playHelp);
        if(inMenu) {
            ImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/mainMenu.png"));
            displayMainMenu();
            userInput.addActionListener(menuListener);
            hideStatusPanels();
        }
    }

    /**
     * This method grabs the current pace the party is traveling at and prints it into string format for
     * display purposes.
     * @return String-format pace.
     */
    public String currPaceToString() {
        String pace;
        if (currentPace == 0) {pace = "Steady";}
        else if (currentPace == 1) {pace = "Strenuous";}
        else {pace = "Grueling";}
        return pace;
    }

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
            if (characterArrayList.get(characterIndex).getIsDead()) {
                stats.setText(String.format("%s is dead.", characterArrayList.get(characterIndex).getName()));
                arrayOfPanes.remove(characterIndex);
                characterArrayList.remove(characterIndex);
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

    /**
     * getter method for the characterArrayList arrayList
     * @return characterArrayList
     */
    public ArrayList<Character> getCharacterArrayList() {
        return characterArrayList;
    }

    /**
     * setter method for the characterArayList arrayList
     * @param characterArrayList
     */
    public void setCharacterArrayList(ArrayList<Character> characterArrayList) {
        this.characterArrayList = characterArrayList;
    }

    /**
     * gets the partys current money
     * @return int money
     */
    public int getMoney() {
        return money;
    }

    /**
     * sets the parts current money
     * @param money the new money to set the partys money value to
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * gets the partys current number of units of food in inventory
     * @return int food in inventory
     */
    public int getFood() {
        return food;
    }

    /**
     * sets the parts current number of units of food in inventory
     * @param food the new amount of food to set the users food quantity to
     */
    public void setFood(int food) {
        this.food = food;
    }

    /**
     *
     * @return
     */
    public int getAmmunition() {
        return ammunition;
    }

    /**
     *
     * @param ammunition
     */
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
        if (happiness >= 100) {
            this.happiness = 100;
        }
        else this.happiness = Math.max(happiness, 0);
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

