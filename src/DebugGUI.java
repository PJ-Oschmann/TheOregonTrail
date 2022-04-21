import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DebugGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton shopTestButton;
    private JTextArea debugTextArea;
    private JButton sceneGUITestButton;
    private JButton clearTextButton;


    //Declare classes to test here
    Game game = new Game();
    Shop shop = new Shop(game);

    public DebugGUI() {
        setContentPane(contentPane);
        setModal(true);


        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        clearTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearDebugText();
            }
        });
        shopTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testShop();
            }
        });
    }

    public void open() {
        this.setMinimumSize(new Dimension(400,400));
        this.setTitle("Debug Menu");
        this.pack();
        this.setVisible(true);

    }
    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void clearDebugText() {
        debugTextArea.setText("");
    }

    private void dbPrint(String message) {
        if (debugTextArea.getText().equals("")) {debugTextArea.setText(message);}
        else {debugTextArea.setText(debugTextArea.getText()+"\n"+message);}

    }

    private void testShop() {
        game.setMoney(50.0);
        shop.sellItem("w",1);
        dbPrint("Party's money is currently " + game.getMoney());

    }

    public static void main(String[] args) {
        DebugGUI dialog = new DebugGUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
