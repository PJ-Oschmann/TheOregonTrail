import javax.swing.*;
import java.util.ArrayList;

public class SceneManager{

    private boolean sceneIsLoaded = false;
    private final ReadText readText = new ReadText();
    ArrayList<String> sceneToRead = new ArrayList<>();
    JButton continueButton;
    JTextArea storyTextArea;
    public SceneManager(JButton continueButton, JTextArea storyTextArea) {
        this.continueButton = continueButton;
        this.storyTextArea = storyTextArea;
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
