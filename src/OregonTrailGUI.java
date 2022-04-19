import javax.swing.*;

public class OregonTrailGUI {

    private JPanel GeneralPanel;
    private JTextField thisAreaWillBeTextField;
    private JLabel ImageLabel;
    private JMenu menu1;
    private JMenuItem menu1Item1;

    public static void main(String[] args) {
        OregonTrailGUI game = new OregonTrailGUI();
    }

    //Create application
    public OregonTrailGUI(){
        initialize();
        if(true) {
            ImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/TestImage1.png"));
        }
    }

    //initialize frame contents
    private void initialize() {
        JFrame frame = new JFrame();
        frame.setTitle("The Oregon Trail -- Remake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);
        menu1 = new JMenu("menu1");
        menu.add(menu1);

        menu1.add(menu1Item1);
    }
}
