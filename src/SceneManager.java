import javax.swing.*;
import java.util.ArrayList;

public class SceneManager{

    private boolean sceneIsLoaded = false;
    private boolean chainScenes = false;
    ArrayList<String> sceneToRead = new ArrayList<>();
    ArrayList<String> arrayOfScenes = new ArrayList<>();
    JButton continueButton;
    JTextArea storyTextArea;
    JLabel imageLabel;
    Scene sceneWindow;
    public SceneManager(JButton continueButton, JTextArea storyTextArea, JLabel imageLabel, Scene sceneWindow) {
        this.continueButton = continueButton;
        this.storyTextArea = storyTextArea;
        this.imageLabel = imageLabel;
        this.sceneWindow = sceneWindow;
    }
    //Load scene. Saves to global variable to avoid reopening textfile.
    public void loadScene(String sceneName) {
        if (!sceneIsLoaded) {
            sceneToRead = ReadText.readScene(sceneName);
            imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/"+sceneName+".png"));
            sceneIsLoaded = true;
            continueButton.setVisible(true);
            continueScene();
        }
        else {
            System.out.println("SceneManager.java: Attempted to load scene " + sceneName + ", but another scene is loaded. Unload the scene first.");
        }
    }

    //Load scene. Saves to global variable to avoid reopening textfile.
    public void loadScene(String sceneName, String currentDate) {
        if (!sceneIsLoaded) {
            sceneToRead = ReadText.readScene(sceneName);
            sceneToRead.add(0,currentDate);
            imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/"+sceneName+".png"));
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
        chainScenesCounter = 0;
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
            if (!chainScenes) {unloadScene(true);}
            else {
                chainScenesCounter++;
                unloadScene(false);
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
            arrayOfScenes.clear();
            unloadScene(true);
        }
    }

    //Unload the scene by setting all values back to default.
    public void unloadScene(boolean destroyWindow) {
        storyTextArea.setText("");
        imageLabel.setIcon(null); //We can set a default image here as well
        readTextCounter = 0;
        sceneIsLoaded = false;
        continueButton.setVisible(false);
        chainScenes = false;
        sceneToRead.clear();
        if (destroyWindow) {sceneWindow.closeSceneWindow();}


    }
}
