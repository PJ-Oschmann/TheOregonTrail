import javax.swing.*;
import java.util.ArrayList;

public class SceneManager{

    private boolean sceneIsLoaded = false;
    private final ReadText readText = new ReadText();
    private boolean chainScenes = false;
    ArrayList<String> sceneToRead = new ArrayList<>();
    ArrayList<String> arrayOfScenes = new ArrayList<>();
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
            System.out.println("SceneManager.java: Attempted to load scene " + sceneName + ", but another scene is loaded. Unload the scene first.");
        }
    }

    //Play back a scene after the other finishes
    public void chainLoadScene(ArrayList<String> arrayOfScenes) {
        chainScenes = true;
        this.arrayOfScenes = arrayOfScenes;
        loadScene(arrayOfScenes.get(0));
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
            if (!chainScenes) {unloadScene();}
            else {
                chainScenesCounter++;
                unloadScene();
                continueChainScene();
            }
        }
    }

    int chainScenesCounter = 0;
    public void continueChainScene() {
        try {
            loadScene(arrayOfScenes.get(chainScenesCounter));
        }
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
