import javax.swing.*;
import java.util.ArrayList;

public class OregonTrailUI {
    private JPanel mainPanel;
    public ReadText readText = new ReadText();

    public static void main(String[] args) {
        JFrame frame = new JFrame("The Oregon Trail MVP");
        frame.setContentPane(new OregonTrailUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(400, 320);

    }
    public OregonTrailUI() {
        readScene("TestScene");
    }

    //Example code. Will use labels in GUI. Perhaps implement a "wait to continue" thing in GUI.
    public void readScene(String scene) {
        ArrayList<String> sceneToRead = readText.readScene(scene);
        for (int i=0;i<sceneToRead.size();i++) {
            System.out.println(sceneToRead.get(i));
        }
    }
}


