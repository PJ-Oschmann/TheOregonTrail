/**
 * This class creates a window for Forts that contains an image, a text area containing options, and a text box
 * for selecting options. The user can choose to go to the local shop, rest at an inn to restore health and hunger,
 * repair their wagon if it is damaged, or leave the fort and continue on their journey.
 */

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
        this.setUndecorated(true);
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

    /**
     * Sets the text in the prompt area.
     * @param message - Message to be written in the prompt area.
     */
    public void setText(String message) {
        promptTextArea.setText(message);
    }

    /**
     * Sets the input text box to a blank String.
     */
    private void resetTextBox() {
        inputText.setText("");
    }

    /**
     * If invalid input is entered, clear the text box and notify the user that their input was not recognized.
     */
    private void badInput() {
        resetTextBox();
        staticMethods.notValidInput();
    }

    /**
     * Handles fort option. The user can choose to go to the local shop, rest at an inn to restore health and hunger,
     *  * repair their wagon if it is damaged, or leave the fort and continue on their journey.
     * @param option - the desired option (1 to shop, 2 to go to the inn, 3 to go to the wagon shop, 4 to leave the
     *               fort)
     */
    public void seeFort(String option) {
        switch (option) {
            case "1" -> shop();
            case "2" -> rest();
            case "3" -> wagonShop();
            case "4" -> leaveFort();
            default -> badInput();
        }
    }

    /**
     * Centers the window on the screen.
     */
    private void center() {
        Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
        int windowDimensions = 700; //Square window
        int height = (screenRes.height/2)-(windowDimensions/2);
        int width = (screenRes.width/2)-(windowDimensions/2);
        this.setLocation(width,height);
        this.setMinimumSize(new Dimension(windowDimensions,windowDimensions));
        this.setUndecorated(true);
    }

    /**
     * Opens the shop GUI and clears the input text box.
     */
    private void shop() {
        game.openShop(); resetTextBox();
    }

    /**
     * Allows the player to rest . If they have enough money, then can pay $4 per party member alive to sleep. 25 HP
     * is recovered, and hunger is set to 0. There is a 50% chance that any character can have their sickness removed
     * if that character is sick. If the player doesn't have enough money to sleep at the inn, they won't be able to.
     * The input text box is cleared after.
     */
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
        resetTextBox();
    }

    /**
     * Allows the player to repair their wagon for $12 if it's broken. If it's not broken, the wagon will not be
     * modified. If the player doesn't have enough money to repair the wagon, they won't be able to repair it. The input
     * text box is cleared after.
     */
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
        resetTextBox();
    }

    /**
     * Player "leaves the fort" and the window is closed.
     */
    private void leaveFort() {
        dispose();
    }

}
