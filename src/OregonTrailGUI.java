import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JPanel InventoryPanel;
    private JLabel InventoryImagePanel;
    private final Scene scene = new Scene();
    private final DebugGUI debug = new DebugGUI();
    private Random rand = new Random();


    //Our players
    private Player hattie = new Player("Hattie Campbell", 100, 0);
    private Player charles = new Player ("Charles",100,0);
    private Player augusta = new Player("Augusta",100,0);
    private Player ben = new Player("Ben",100,0);
    private Player jake = new Player("Jake",100,0);

    //game variables
    private int food = 0, ammunition = 0, medicine = 0, clothes = 0, wagonTools = 0, splint = 0, oxen = 0;
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
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);   //makes fullscreen
        frame.setVisible(true);
    }

    //Create application
    public OregonTrailGUI() {
        ImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/TestImage1.png"));
    }

    public void exitGame() {
        System.exit(0);
    }

    public int calculateHappiness(String operation, int amount) {
        if (operation=="ADD") {
            if (happiness+amount <=100) {return amount;}
            else {return 100-happiness;}
        }
        else { //equals "SUBTRACT"
            if (happiness-amount >=0) {return amount;}
            else {return 0+happiness;}
        }
    }
    public void setHappiness() {
        //Weather
        if (weather.getWeatherCondition().equals("Good")) {happiness+=calculateHappiness("ADD",5);}
        else if (weather.getWeatherCondition().equals("Bad")) {happiness-=calculateHappiness("SUBTRACT",5);}

        //Player is ill
        //Code goes here lmao
    }

    public void weatherAffectPlayer(Player player) {
        if (player.getHasClothing() == false) {
            player.setHealth(player.getHealth()-25);
            if (rand.nextInt(4)==0) {
                player.setSick(true);
            }
        }

    }
}

