/**
 * This class creates a GUI window for the Party class, which acts as a Character picker when using items from the
 * Inventory window.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class Party extends JDialog {
    private JPanel contentPane;
    private JTextArea charlesStats;
    private JTextArea hattieStats;
    private JTextArea augustaStats;
    private JTextArea benStats;
    private JTextArea jakeStats;
    private JTextField userInput;
    private JTextArea partyStats;
    private JLabel questionText;
    private JTextPane promptTextPane;
    private JTextArea promptTextArea;
    public ArrayList<Character> characterArrayList;
    private final OregonTrailGUI game;
    private String item;
    private Character selectedCharacter;
    private JPanel InterfacePanel;
    private JPanel imagePanel;
    public JLabel imageLabel;
    private JPanel statsPanel;

    public Party(OregonTrailGUI game, String item) {
        //instantiating variables
        this.game = game;
        this.item = item;
        initializePartyTextArea(item);
        promptTextArea.setText("Select a character to give " + item.toLowerCase() + " to!");
        this.setUndecorated(true);
        setContentPane(contentPane);
        setModal(true);

        userInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectCharacter(userInput.getText())) {
                    doAction(item);
                    game.writeGameInfo();
                    userInput.setText("");
                }
                else {
                    staticMethods.notValidInput();
                }
            }
        });

        updateStats();
        userInput.addFocusListener(inputHelp);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) { userInput.requestFocusInWindow(); }
            @Override
            public void windowClosing(WindowEvent e) { }
            @Override
            public void windowClosed(WindowEvent e) { }
            @Override
            public void windowIconified(WindowEvent e) { }
            @Override
            public void windowDeiconified(WindowEvent e) { }
            @Override
            public void windowActivated(WindowEvent e) { }
            @Override
            public void windowDeactivated(WindowEvent e) { }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
                0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Initializes the Party Text area to default values and explains that the user types the first letter of the
     * desired Character's name to select the character.
     * @param itemName - Item to be specified in the text area.
     */
    private void initializePartyTextArea(String itemName) {
        promptTextArea.setText(String.format(
                """
                Please select the character you would like to use %s on using the input text area below:
                
                %s
                %s
                %s
                %s
                %s
                
                Press ESC at anytime to exit this menu.
                """, itemName,  printChar("H"), printChar("C"), printChar("A"), printChar("B"),
                printChar("J")));
    }

    /**
     * Specifies that the user should type the first character of the desired character's name when making a selection.
     * If the character is dead, they are marked as "DEAD."
     * @param i - input character. 'H' is for Hattie, 'C' is for Charles, 'A' is for Augusta, 'B' is for Ben, and 'J'
     *          is for Jake.
     * @return string output of alive/dead status of characters when displayed in party
     */
    private String printChar(String i) {
        switch (i.toUpperCase()) {
            case "H" -> {
                if (!characterArrayList.get(0).isDead) {
                    return "H: Hattie";
                }
                else { return "DEAD"; }
            }
            case "C" -> {
                if (!characterArrayList.get(1).isDead) {
                    return "C: Charles";
                }
                else { return "DEAD"; }
            }
            case "A" -> {
                if (!characterArrayList.get(2).isDead) {
                    return "A: Augusta";
                }
                else { return "DEAD"; }
            }
            case "B" -> {
                if (!characterArrayList.get(3).isDead) {
                    return "B: Ben";
                }
                else { return "DEAD"; }
            }
            case "J" -> {
                if (!characterArrayList.get(4).isDead) {
                    return "J: Jake";
                }
                else { return "DEAD"; }
            }
            default -> throw new RuntimeException("There was an error in printing the character in Party initializePartyTextArea");
        }
    }

    /**
     * Selects a character to use for the desired action.
     * @param ch - The character being selected
     * @return A string of the character selected. If the selection was invalid, "INVALID" is returned.
     */
    private boolean selectCharacter(String ch) {
        int charIndex;
        switch (ch.toUpperCase()) {
            case "H" -> charIndex = 0;
            case "C" -> charIndex = 1;
            case "A" -> charIndex = 2;
            case "B" -> charIndex = 3;
            case "J" -> charIndex = 4;
            default -> throw new RuntimeException("Error in selecting character in party");
        }
        if (characterArrayList.get(charIndex).isDead) {
            JOptionPane.showMessageDialog(null, "That character is dead.\nYou can't use an item on" +
                    "them.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        selectedCharacter = characterArrayList.get(charIndex);
        return true;
    }

    ArrayList<JTextArea> arrayOfPanes = new ArrayList<>(List.of(hattieStats, charlesStats, augustaStats, benStats, jakeStats));
    /**
     * Update the status of each player. If the player is dead, their status is marked "DEAD."
     */
    public void updateStats() {
        String partyStatsText= """
                Money: $Money
                Happiness: $Happiness
                """;
        partyStatsText = partyStatsText.replace("$Money","$"+game.getMoney());
        partyStatsText = partyStatsText.replace("$Happiness",Integer.toString(game.getHappiness()));
        partyStats.setText(partyStatsText);
        int characterIndex = 0;
        String statsText = """
                HP: $HP
                Clothing: $Clothing
                Healthiness: $Healthiness
                Injured: $Injured
                Hunger: $Hunger
                
                """;
        for (JTextArea stats : arrayOfPanes) {
            if (characterArrayList.get(characterIndex).getHealth() <= 0) {
                stats.setText("DEAD");

            } else {
                String newText = statsText;
                newText = newText.replace("$HP", Integer.toString(characterArrayList.get(characterIndex).getHealth()));
                newText = newText.replace("$Clothing", characterArrayList.get(characterIndex).hasClothingToString());
                newText = newText.replace("$Healthiness",characterArrayList.get(characterIndex).isSickToString());
                newText = newText.replace("$Injured",characterArrayList.get(characterIndex).isInjuredToString());
                newText = newText.replace("$Hunger", String.valueOf(characterArrayList.get(characterIndex).getHunger()));
                stats.setText(newText);
            }
            characterIndex++;
        }
    }

    /**
     * Sets the input text color to grey when not focused on, and black when focused on. When no text is present
     * and the text box is not focused on, "Input Selection Here" is set into the text box.
     */
    private final FocusAdapter inputHelp = new FocusAdapter() { //Grey text for input box when not focused on
        @Override
        public void focusGained(FocusEvent e) {
            userInput.setText("");
            userInput.setForeground(Color.BLACK);
        }

        @Override
        public void focusLost(FocusEvent e) {
            userInput.setText("Input Selection Here");
            userInput.setForeground(new Color(147, 147,147));
        }
    };

    /**
     * When the window is closed, dispose of the window.
     */
    private void onCancel() {
        dispose();
    }

    /**
     * Calculates hunger to ensure it does not go below 0. It will decrease by the passed value unless that value
     * exceeds 0, in which it will simply be set to 0.
     * @param character - The character whose hunger will be modified.
     * @param hungerVal - The amount of hunger to add or subtract.
     * @return the modified amount of hunger.
     */
    private int calculateHunger(Character character, int hungerVal) {
        int newHunger;
        if (character.getHunger()-hungerVal < 0) {
            newHunger = 0;
        }
        else {
            newHunger = character.getHunger()-hungerVal;
        }
        return newHunger;
    }


    /**
     * Determines what action should be done. if the passed action is "FOOD," the eatFood() method is called. If
     * "MEDICINE" is passed, the takeMeds() method is called. If "CLOTHES" is passed, equipClothes(), is called.
     * If "SPLITS" is called, the useSplints() method is called.
     * @param item - the item being used.
     */
    private void doAction(String item) {
        switch (item) {
            case "FOOD" -> eatFood();
            case "MEDICINE" -> takeMeds();
            case "CLOTHES" -> equipClothes();
            case "SPLINTS" -> useSplints();
        }
        updateStats();

    }

    /**
     * Allows the character to eat food. 1 food item is removed from the inventory, and the selected character's hunger
     * decreases by 2. If the player doesn't have any food items in their inventory, they won't be able to eat.
     */
    private void eatFood() {
        if (game.getFood() > 0) {
            game.setFood(game.getFood() - 1);
            selectedCharacter.setHunger(calculateHunger(selectedCharacter,2));
            promptTextArea.setText(selectedCharacter.getName() + " ate some food!");
            staticMethods.resetNFC();
        }
        else {
            staticMethods.notEnoughItem("food");
        }

    }

    /**
     * Allows the player to use a medicine item. If the selected character is not sick, they won't be able to use the
     * item on that character. Otherwise, the player is cured. If the player doesn't have any medicine items, they
     * won't be able to use them.
     */
    private void takeMeds() {
        if(game.getMedicine() > 0){
            if(!selectedCharacter.isSick()) {
                JOptionPane.showMessageDialog(null,selectedCharacter.getName()+" is not sick. " +
                        "Please pick another character.",selectedCharacter.getName()+"'s Lack of Illness",
                        JOptionPane.PLAIN_MESSAGE);
            }
            else {
                    game.setMedicine(game.getMedicine() - 1);
                    selectedCharacter.setSick(false);
                    promptTextArea.setText(selectedCharacter.getName() + " has been cured!");
                }
            }
        else {
            staticMethods.notEnoughItem("medicine");
        }
    }

    /**
     * Allows the player to equip clothes, which protect the desired character from "bad" (i.e., extremely hot or cold)
     * weather. If the selected character already has this protective clothing, they won't be able to apply the clothing
     * to that character. Otherwise, the character is given protective clothing. If the player doesn't have any clothing
     * items in their inventory, they won't be able to equip any clothing.
     */
    private void equipClothes() {
        if (game.getClothes()>0) {
            if (selectedCharacter.getHasClothing()) {
                JOptionPane.showMessageDialog(null,selectedCharacter.getName()+" already has " +
                        "protective clothing.",selectedCharacter.getName()+"'s Clothing",JOptionPane.PLAIN_MESSAGE);
            }
            else {
                    game.setClothes(game.getClothes()-1);
                    selectedCharacter.setHasClothing(true);
                    promptTextArea.setText(selectedCharacter.getName()+" now has protective " + "clothing.");
                }
            }
        else {
            staticMethods.notEnoughItem("clothes");
        }
    }

    /**
     * Allows the player to use a splint, which will cure the desired character's injury. If the selected character is
     * not injured, they won't be able to use the splint on that character. Otherwise, the character's injury will
     * be cured. If the player doesn't have enough splint items in their inventory, they won't be able to use this
     * item.
     */
    private void useSplints() {
        if (game.getSplints() > 0){
            if (!selectedCharacter.isInjured()) {
                JOptionPane.showMessageDialog(null,selectedCharacter.getName()+" is not injured. " +
                        "Please select another character.",selectedCharacter.getName()+"'s Lack of Injury",
                        JOptionPane.PLAIN_MESSAGE);
            }
            else {
                    selectedCharacter.setInjured(false);
                    promptTextArea.setText(selectedCharacter.getName()+" now has " + "protective clothing.");
                }
            }
        else {
            staticMethods.notEnoughItem("splints");
        }
    }
}
