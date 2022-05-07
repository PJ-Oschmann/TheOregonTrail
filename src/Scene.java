/**
 * Scene provides a GUI for text-based scenes. It can present an image to give the scene illustration,
 * and reads a text file to present text. The scenes are presented line-by-line so that the player is not overwhelmed
 * by text. A "continue" button is provided to progress through the text scene. The player can also simply press 'C'
 * to continue as well.
 */
import javax.management.RuntimeErrorException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Timer;

public class Scene extends JDialog {
    private boolean sceneIsLoaded = false;
    private boolean chainScenes = false;
    ArrayList<String> sceneToRead = new ArrayList<>();
    ArrayList<String> arrayOfScenes = new ArrayList<>();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea storyTextArea;
    private JLabel imageLabel;
    private JButton continueButton;

    //Constructor
    public Scene() {
        this.setTitle("Scene");
        Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
        int windowDimensions = 700; //Square window
        int height = (screenRes.height/2)-(windowDimensions/2);
        int width = (screenRes.width/2)-(windowDimensions/2);
        this.setLocation(width,height);
        this.setMinimumSize(new Dimension(windowDimensions,windowDimensions));
        this.setUndecorated(true);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continueScene();
            }
        });
        storyTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar()=='c') {
                    continueScene();
                }
            }
        });
        continueButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar()=='c') {
                    continueScene();
                }
            }
        });
    }

    /**
     * Gets the path for an image to present. fort.png is presented when a scene as "fort" in its name, and river.png
     * is presented when a scene as "river" in its name. Specialized images are used for individual landmarks.
     * @return String containing the path to the image without the file extension (which is presumed to be .png)
     */
    private String getImagePath(String sceneName) {
        if (sceneName.toLowerCase().contains("fort")) {
            return "fort";
        }
        else if (sceneName.toLowerCase().contains("river")) {
            return "river";
        }
        else if (sceneName.equalsIgnoreCase("blueMountains")) {
            return "landmarks/blueMountains";
        }
        else if (sceneName.equalsIgnoreCase("chimneyRock")) {
            return "landmarks/chimneyspeak";
        }
        else if (sceneName.equalsIgnoreCase("courtHouseRock")) {
            return "landmarks/courthousejail";
        }
        else if (sceneName.equalsIgnoreCase("oregonCity")) {
            return "landmarks/oregonCity";
        }
        else if (sceneName.equalsIgnoreCase("scottsBluff")) {
            return "landmarks/scottsbluff";
        }
        else if (sceneName.equalsIgnoreCase("threeIslandCrossing")) {
            return "landmarks/threeislandcrossing";
        }
        else if (sceneName.equalsIgnoreCase("intro")) {
            return "intro";
        }
        else {
            return "NOIMAGE";
        }
    }

    /**
     * Loads the scene. The ReadText class is used to read the desired text file (specified in sceneName) and saves it
     * to an ArrayList. The scene is marked as "loaded" and the scene Window is opened.
     * If a scene is already loaded, another scene cannot be loaded until the previous scene is over.
     * @param sceneName - Name of the scene file, without the file extension or path.
     */
    //Load scene. Saves to global variable to avoid reopening textfile.
    public void loadScene(String sceneName) {
        if (!sceneIsLoaded) {
            sceneToRead = ReadText.readTextFile(sceneName);
            imageLabel.setIcon(new ImageIcon("src/assets/images/"+getImagePath(sceneName)+".png"));
            sceneIsLoaded = true;
            continueScene();
            this.pack();
            this.setVisible(true);
        }
        else {
            throw new RuntimeException("There was an error in unloading the previous scene.");
        }
    }

    /**
     * Loads the scene. The ReadText class is used to read the desired text file (specified in sceneName) and saves it
     * to an ArrayList. The scene is marked as "loaded" and the scene Window is opened. The current date is prepended
     * to the beginning of the scene. This is particularly used for journal entries.
     * If a scene is already loaded, another scene cannot be loaded until the previous scene is over.
     * @param sceneName - Name of the scene file, without the file extension or path.
     * @param currentDate - String of the current date, written in "Month, DD, YYYY" format.
     */
    public void loadScene(String sceneName, String currentDate) {
        if (!sceneIsLoaded) {
            sceneToRead = ReadText.readTextFile(sceneName);
            sceneToRead.add(0, currentDate);
            imageLabel.setIcon(staticMethods.getImage("assets/images/"+sceneName+".png"));
            sceneIsLoaded = true;
            continueScene();
            this.pack();
            this.setVisible(true);
        }
        else {
            throw new RuntimeException("There was an error in unloading the previous scene.");
        }
    }

    int readTextCounter = 0;

    /**
     * Continues the scene to the next line when the "Continue" button is pressed or the 'C' key is pressed.
     */
    public void continueScene() {
        try {
            storyTextArea.setText(sceneToRead.get(readTextCounter));
            readTextCounter++;
        }
        //Exception thrown if end of file is reached. Unload the scene.
        catch (Exception e) {
            if (!chainScenes) {unloadScene(true);}
            else {
                //chainScenesCounter++;
                unloadScene(false);
                //continueChainScene();
            }
        }
    }

    /**
     * Unloads the scene. All elements are reset to their default, blank state and the window is closed.
     * @param destroyWindow
     */
    public void unloadScene(boolean destroyWindow) {
        storyTextArea.setText("");
        imageLabel.setIcon(null); //We can set a default image here as well
        readTextCounter = 0;
        sceneIsLoaded = false;
        chainScenes = false;
        sceneToRead.clear();
        if (destroyWindow) {closeSceneWindow();}


    }

    /**
     * Closes the scene window.
     */
    public void closeSceneWindow() {
        dispose();
    }

}
