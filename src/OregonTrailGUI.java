import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OregonTrailGUI {

    private JPanel MainPanel;
    private JPanel IMGPanel;
    private JLabel ImageLabel;
    private JPanel BottomPanel;
    private JTextField StoryTextField;
    private JPanel GeneralPanel;
    private JButton InventoryTestButton;
    private JPanel HattiePanel;
    private JPanel Char2Panel;
    private JPanel Char3Panel;
    private JPanel Char4Panel;
    private JButton partyInfoTestButton;
    private JPanel InventoryPanel;
    private JLabel InventoryImagePanel;
    private JButton button2;
    private JTextField textField1;
    private static OregonTrailGUI game = new OregonTrailGUI();

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(game.MainPanel);
        frame.setTitle("The Oregon Trail -- Remake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
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
        frame.setVisible(true);


    }

    //Create application
    public OregonTrailGUI(){
        ImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/TestImage1.png"));

        InventoryTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inventory inv = new Inventory();
                inv.pack();
                inv.setVisible(true);
            }
        });

    }
}
