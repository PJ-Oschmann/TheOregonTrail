import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Random;

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
    private JPanel InventoryPanel;
    private JLabel InventoryImagePanel;
    private final Scene scene = new Scene();
    private final DebugGUI debug = new DebugGUI();
    private Random rand = new Random();

    //Our players
    private Character hattie = new Character("Hattie Campbell", 100, 0);
    private Character charles = new Character("Charles",100,0);
    private Character augusta = new Character("Augusta",100,0);
    private Character ben = new Character("Ben",100,0);
    private Character jake = new Character("Jake",100,0);

    //game variables
    private int food = 0, ammunition = 0, medicine = 0, clothes = 0, wagonTools = 0, splints = 0, oxen = 0;
    private boolean isGameWon = false, isGameLost = false;
    private int happiness;
    private Weather weather = new Weather();

    private static OregonTrailGUI game = new OregonTrailGUI();

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(game.MainPanel);
        frame.setTitle("The Oregon Trail -- Remake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        JMenuBar menuBar = new JMenuBar();
        menuBar.setVisible(true);
        frame.setJMenuBar(menuBar);

        JMenu menuMain = new JMenu("MAIN");
        menuBar.add(menuMain);
        JMenu menuAbout= new JMenu("ABOUT");
        menuBar.add(menuAbout);

        //Give the application the System's theme.
        //Delete this line if the app won't start
        setTheme();

//TODO: Make menu buttons do things
        JMenuItem mainMenu = new JMenuItem("MAIN MENU . . .");//Prompts are you sure window if game condition is not win/lose
        JMenuItem exitApp = new JMenuItem("EXIT . . .");     //Prompts are you sure window if game condition is not win/lose
        menuMain.add(mainMenu);                                      //returns to main menu, resets game
        menuMain.add(exitApp);                                      //exits app
        JMenuItem projectDescription = new JMenuItem("Project Description . . .");
        JMenuItem aboutProject = new JMenuItem("About the Project . . .");
        JMenuItem aboutHattie = new JMenuItem("About Hattie Campbell . . .");
        JMenuItem imageCredits = new JMenuItem("Image Credits . . .");
        menuAbout.add(projectDescription);
        menuAbout.add(aboutProject);
        menuAbout.add(aboutHattie);
        menuAbout.add(imageCredits);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);   //makes fullscreen
        frame.setVisible(true);


        //Action Listeners for menu:

        //Main:

        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Close your eyes and imagine going back to the main menu here...");
            }
        });
        exitApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });

        //About:

        projectDescription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout("The Oregon Trail is an educational game to teach students about the 1800s trip from Missouri to Oregon. This project aims to recreate this experience using the Java programming language. With incredible high-resolution graphics, the experience is more immersive than ever before","Project Description");
            }
        });

        aboutProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout("This project was completed by Team Hollenberg Station, consisting of Aleece Al-Olimat, Ken Zhu, and PJ Oschmann","About This Project");
            }
        });

        aboutHattie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout("(Replace me with actual text) Hattie is a young lass who is setting out for a new life in Oregon. Her twin sister died. What a shame.","About Hattie");
            }
        });
        imageCredits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout("Images created by Aleece Al-Olimat.\nImage of Oregon by Kendra of Clker.com, in the Public Domain.","Image Credits");
            }
        });



    }


    public static void setTheme() {
        //Set the theme to match the system!
        //On Windows, it should use Win32 elements
        //On Linux, it uses the GTK2 theme
        //On MacOS, we will never know i guess
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }
        for (Window window : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
        }
    }

    public static void exitGame() {
        if (JOptionPane.showConfirmDialog(null,"Are you sure you want to quit?","Exit?",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            System.exit(0);
        }

    }

    /**
     * Displays a dialog box the screen. Asks as a preset to maintain a common style across all "About" messages.
     * A cute picture of Oregon is shown for some visual flair, and a new line is created every 15 words.
     * @param message - The message to display to the user
     * @param title - The title of the message box.
     */

    //Oregon image from http://www.clker.com/clipart-oregon-3.html
    public static void showAbout(String message, String title) {
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

        JOptionPane.showMessageDialog(null,"<html><h1>"+title+"</h1><br>"+newMessage,title,JOptionPane.PLAIN_MESSAGE,oregonIcon);
    }

    //Create application
    public OregonTrailGUI() {

        ImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/TestImage1.png"));
        userInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//TODO: CREATE USER INPUT FIELD CODE
                if (userInput.getText().equalsIgnoreCase("I")) {
                    Inventory inv = new Inventory(food, ammunition, medicine, clothes, wagonTools, splints, oxen);
                    inv.pack();
                    inv.setVisible(true);
                }
                else if (userInput.getText().equalsIgnoreCase("H")) {
                    storyTextArea.setText(
                            """
                            INPUT DIALOGUE BOX HELP MENU
                            
                            OPTIONS AVAILABLE FOR INPUT DIALOGUE BOX:
                            H: HELP
                            I: INVENTORY
                            P: PARTY
                            
                            IF YOU ARE INSIDE OF A TOWN OR FORT:
                            W: GO TO THE LOCAL WAGON REPAIR MECHANIC
                            S: VISIT THE LOCAL SHOP
                            R: REST AT THE LOCAL INN
                            """
                    );
                }
                else if (userInput.getText().equalsIgnoreCase("P")) {
//TODO: IMPLEMENT PARTY MENU AND DETAILS DIALOGUE CLASS
                }
                userInput.setText("");
            }
        });
        userInput.addFocusListener(new FocusAdapter() { //Grey text for input box when not focused on
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
        });

    }



    public int calculateHappiness(String operation, int amount) {
        if (operation == "ADD") {
            if (happiness+amount <=100) {return amount;}
            else {return 100-happiness;}
        }
        else { //equals "SUBTRACT"
            if (happiness - amount >= 0) {return amount;}
            else {return 0+happiness;}
        }
    }
    public void setHappiness() {
        //Weather
        if (weather.getWeatherCondition().equals("Good")) {happiness += calculateHappiness("ADD",5);}
        else if (weather.getWeatherCondition().equals("Bad")) {happiness -= calculateHappiness("SUBTRACT",5);}

        //Player is ill
        //Code goes here lmao
    }

    public void weatherAffectPlayer(Character player) {
        if (!player.getHasClothing()) {
            player.setHealth(player.getHealth() - 25);
            if (rand.nextInt(4) == 0) {
                player.setSick(true);
            }
        }

    }
}

