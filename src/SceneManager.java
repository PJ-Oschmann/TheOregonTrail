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
    JLabel imageLabel;
    public SceneManager(JButton continueButton, JTextArea storyTextArea, JLabel imageLabel) {
        this.continueButton = continueButton;
        this.storyTextArea = storyTextArea;
        this.imageLabel = imageLabel;
    }
    //Load scene. Saves to global variable to avoid reopening textfile.
    public void loadScene(String sceneName) {
        if (!sceneIsLoaded) {
            sceneToRead = readText.readScene(sceneName);
            imageLabel.setIcon(new javax.swing.ImageIcon("assets/"+sceneName+".png"));
            sceneIsLoaded = true;
            continueButton.setVisible(true);
            continueScene();
        }
        else {
            System.out.println("SceneManager.java: Attempted to load scene " + sceneName + ", but another scene is loaded. Unload the scene first.");
        }
    }

    //Play back a scene after the other finishes
    //Note: Scenes should be put together in their text file. Only use this if needed.
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

    //Load the next scene if they're chained together
    int chainScenesCounter = 0;
    public void continueChainScene() {
        try {
            loadScene(arrayOfScenes.get(chainScenesCounter));
        }
        catch (Exception e) {
            unloadScene();
            arrayOfScenes.clear();
            chainScenesCounter = 0;
        }
    }

    //Unload the scene by setting all values back to default.
    public void unloadScene() {
        storyTextArea.setText("");
        imageLabel.setIcon(null); //We can set a default image here as well
        readTextCounter = 0;
        sceneIsLoaded = false;
        continueButton.setVisible(false);
        chainScenes = false;
        sceneToRead.clear();

    }
}
