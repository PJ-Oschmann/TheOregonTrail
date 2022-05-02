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
    //private final SceneManager sceneMan = new SceneManager(continueButton,storyTextArea,imageLabel,this);

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
    //Load scene. Saves to global variable to avoid reopening textfile.
    public void loadScene(String sceneName) {
        if (!sceneIsLoaded) {
            sceneToRead = ReadText.readScene(sceneName);
            imageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/"+sceneName+".png"));
            sceneIsLoaded = true;
            continueButton.setVisible(true);
            continueScene();
            this.pack();
            this.setVisible(true);
        }
        else {
            System.out.println("SceneManager.java: Attempted to load scene " + sceneName + ", but another scene is loaded. Unload the scene first.");
        }
    }

    public void chainLoadScene(ArrayList<String> arrayOfScenes) {
        chainLoadScene(arrayOfScenes);
        this.pack();
        this.setVisible(true);
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
                //chainScenesCounter++;
                unloadScene(false);
                //continueChainScene();
            }
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
        if (destroyWindow) {closeSceneWindow();}


    }
    public void closeSceneWindow() {
        dispose();
    }

}
