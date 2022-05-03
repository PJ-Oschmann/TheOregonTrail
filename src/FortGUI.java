import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class FortGUI extends JDialog {
    private JPanel contentPane;
    public JTextField inputText;
    private JTextArea promptTextArea;
    public JLabel fortImage;
    private JButton buttonOK;
    private OregonTrailGUI game;
    private Random rand = new Random();

    public FortGUI(OregonTrailGUI game) {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //TODO: FIX THE CENTERING
        center();
        this.setUndecorated(true);
        this.game = game;

        inputText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seeFort(inputText.getText());
            }
        });
    }

    public void setText(String message) {
        promptTextArea.setText(message);
    }

    private void resetFort() {
        inputText.setText("");
    }
    private void badInput() {
        resetFort();
        staticMethods.notValidInput();
    }
    public void seeFort(String option) {
        switch (option) {
            case "1" -> shop();
            case "2" -> rest();
            case "3" -> wagonShop();
            case "4" -> leaveFort();
            default -> badInput();
        }
    }

    private void center() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setMinimumSize(new Dimension(200,250));
        int width = screenSize.width/2-this.getWidth()/2;
        int height = screenSize.height/2-this.getHeight()/2;
        this.setLocation(width, height);
    }

    private void shop() {
        game.openShop();resetFort();
    }

    private void rest() {
        ArrayList<Character> characterArrayList = game.getCharacterArrayList();
        int price = characterArrayList.size()*4;
        if (game.getMoney() >= price) {
            int choice = JOptionPane.showOptionDialog(null,"Resting at the inn will cost your party a total of" +
                    " $" + price + ". Rest up?","REST?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
            if (choice==JOptionPane.YES_OPTION) {
                //Add 25 HP to everyone, sets hunger to 0
                for (Character character : characterArrayList) {
                    game.calculateHealth(character,25);
                    character.setHunger(0);
                    if (rand.nextInt(2)==0 && character.isSick()){
                        character.setSick(false);
                    }
                }
                JOptionPane.showMessageDialog(null,"Everyone wakes up well rested!","GOOD MORNING!",
                        JOptionPane.PLAIN_MESSAGE);
            }
            else { //If "No"
              JOptionPane.showMessageDialog(null,"You leave the inn.","LEAVING INN",JOptionPane.PLAIN_MESSAGE);
            }
        }
        else {
            staticMethods.notEnoughMoney();
        }
        resetFort();
    }

    private void wagonShop() {
        if (game.getMoney() >= 12 && game.getWagon().getState()==1) {
            int choice = JOptionPane.showOptionDialog(null,"Repairing your wagon will cost you" +
                    " $12. Repair it?","REPAIR WAGON?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                    null,null);
            if (choice==JOptionPane.YES_OPTION) {
                game.getWagon().setState(0);
                JOptionPane.showMessageDialog(null,"You repaired your wagon!","WAGON REPAIRED",
                        JOptionPane.PLAIN_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"You didn't repair your wagon","WAGON STILL DAMAGED",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
        else if (game.getWagon().getState()==0) {
            JOptionPane.showMessageDialog(null,"Your wagon is not damaged!","WAGON IS FINE",
                    JOptionPane.PLAIN_MESSAGE);
        }
        else {
            staticMethods.notEnoughMoney();
        }
        resetFort();
    }

    private void leaveFort() {
        dispose();
    }

}
