import javax.swing.*;
import java.awt.event.*;

public class Shop extends JDialog {
    private JPanel shopPane;
    private JPanel shopMenu;
    private JComboBox shopComboBox;
    private JTextArea shopInfo;
    private JTextField shopInput;
    private JLabel inputLabel;
    private JPanel mainPanel;
    private JLabel shopImage;
    //B = Buy; S = Sell
    private final int clothesBuyPrice = 10;
    private final int ammoBuyPrice = 4;
    private final int foodBuyPrice = 3; //for 5 units of food
    private final int medBuyPrice = 3;
    private final int splintBuyPrice = 8;
    private final int wagonToolsBuyPrice = 10;
    private final int oxenBuyPrice = 10;

    private final int clothesSellPrice = 8;
    private final int ammoSellPrice = 3;
    private final int foodSellPrice = 2; // for 5 units of food
    private final int medSellPrice = 2;
    private final int splintSellPrice = 5;
    private final int toolsSellPrice = 8;
    private final int oxenSellPrice = 8;
    private int money;

    public Shop(int money, int food, int ammo, int medicine, int clothes, int wagonTools, int splints, int oxen) {
        setContentPane(shopPane);
        setModal(true);
        shopImage.setIcon(new javax.swing.ImageIcon("src/assets/images/Shop.png"));
        if (shopComboBox.getSelectedIndex() == 0) {
            displayMenu();
        }

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(closeWindow);

        // call onCancel() on ESCAPE
        shopPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void displayMenu() {
        shopInfo.setText(
            """
            u fucking wu
            """
        );
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private WindowAdapter closeWindow = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            onCancel();
        }
    };
}