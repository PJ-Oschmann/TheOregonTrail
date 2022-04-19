import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OregonTrailGUI {

    private JPanel MainPanel;
    private JPanel IMGPanel;
    private JLabel ImageLabel;
    private JPanel BottomPanel;
    private JTextField StoryTextField;
    private JPanel GeneralPanel;
    private JButton InventoryTestButton;
    private JPanel HattiePanel;
    private JPanel Char2Panel;
    private JPanel Char3Panel;
    private JPanel Char4Panel;
    private JButton partyInfoTestButton;
    private JButton continueButton;
    private JTextArea storyTextArea;
    private JPanel InventoryPanel;
    private JLabel InventoryImagePanel;
    private JButton button2;
    private JTextField textField1;
    private ReadText readText = new ReadText();
    private boolean sceneIsLoaded = false;
    ArrayList<String> sceneToRead = new ArrayList<>();
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

        JMenu menu1 = new JMenu("Menu 1");
        menuBar.add(menu1);
        JMenu menu2 = new JMenu("Menu 2");
        menuBar.add(menu2);

        JMenuItem menu1Item1 = new JMenuItem("Item 1");
        menu1.add(menu1Item1);
        JMenuItem menu2Item1 = new JMenuItem("Item 1");
        menu2.add(menu2Item1);
        frame.setVisible(true);


    }

    //Create application
    public OregonTrailGUI(){
        ImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/TestImage1.png"));

        InventoryTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inventory inv = new Inventory();
                inv.pack();
                inv.setVisible(true);
            }
        });

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continueScene();


            }
        });
        loadScene("1861-3-19");
    }



    //Load scene. Saves to global variable to avoid reopening textfile.
    public void loadScene(String sceneName) {
        if (!sceneIsLoaded) {
            sceneToRead = readText.readScene(sceneName);
            sceneIsLoaded = true;
            continueButton.setVisible(true);
            continueScene();
        }
        else {
            System.out.println("OregonTrailGUI.java: Attempted to load scene " + sceneName + ", but another scene is loaded. Unload the scene first.");
        }
    }

    //Continue reading a scene.
    int readTextCounter = 0;
    public void continueScene() {
        try {
            storyTextArea.setText(sceneToRead.get(readTextCounter));
            readTextCounter++;
        }
        //Exception thrown if end of file is reached. Unload the scene.
        catch (Exception e) {
            unloadScene();
        }

    }

    public void unloadScene() {
        storyTextArea.setText("");
        readTextCounter = 0;
        sceneIsLoaded = false;
        continueButton.setVisible(false);
    }
}
