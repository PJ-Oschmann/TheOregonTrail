import javax.swing.*;

public class OregonTrailGUI {

    private JPanel MainPanel;
    private JLabel ImageLabel;
    private JPanel InventoryPanel;
    private JPanel IMGPanel;
    private JPanel BottomPanel;
    private JTextField StoryTextField;
    private JPanel GeneralPanel;
    private JPanel InvMainPanel;
    private JPanel InvBottomPanel;
    private JPanel InvGeneralPanel;
    private JScrollBar scrollBar1;
    private JTextField textField1;


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new OregonTrailGUI().MainPanel);
        frame.setTitle("The Oregon Trail -- Remake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

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
    }

    //Create application
    public OregonTrailGUI(){
        ImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/TestImage1.png"));
    }
}
