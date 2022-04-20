import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Scene extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea storyTextArea;
    private JLabel imageLabel;
    private JButton continueButton;
    private final SceneManager sceneMan = new SceneManager(continueButton,storyTextArea,imageLabel,this);

    public Scene() {
        this.setTitle("Scene");
        this.setSize(800,800);
        this.setLocationRelativeTo(null);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceneMan.continueScene();
            }
        });
    }
    public void loadScene(String scene) {
        sceneMan.loadScene(scene);
        this.pack();
        this.setVisible(true);

    }

    public void chainLoadScene(ArrayList<String> arrayOfScenes) {
        sceneMan.chainLoadScene(arrayOfScenes);
        this.pack();
        this.setVisible(true);
    }

    public void closeSceneWindow() {
        dispose();
    }
    public static void main(String[] args) {
        Scene dialog = new Scene();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
