import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class Party extends JDialog {
    private JPanel contentPane;
    private JTextPane charlesStats;
    private JTextPane hattieStats;
    private JTextPane augustaStats;
    private JTextPane benStats;
    private JTextPane jakeStats;
    private JTextField userInput;
    private JTextPane partyStats;
    private JTextArea partyTextArea;
    private final ArrayList<Character> characterArrayList;

    public Party(Character hattie, Character charles, Character augusta, Character ben, Character jake, int happiness, int money) {
        this.setTitle("Party");
        this.setMinimumSize(new Dimension(1000,300));
        characterArrayList = new ArrayList<>(List.of(hattie, charles, augusta, ben, jake));
        //this.setUndecorated(true);
        setContentPane(contentPane);
        setModal(true);

        userInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectCharacter(userInput.getText());
                userInput.setText("");
            }
        });
        updateStats(money, happiness);
    }

    /**
     * Selects a character to use for the desired action.
     * @param character - The character being selected
     * @return A string of the character selected. If the selection was invalid, "INVALID" is returned.
     */
    public String selectCharacter(String character) {
        String selectedCharacter = "INVALID"; //This should be handled
        if (character.equalsIgnoreCase("H")) {
            selectedCharacter = "HATTIE";
        } else if (character.equalsIgnoreCase("C")) {
            selectedCharacter = "CHARLES";
        } else if (character.equalsIgnoreCase("A")) {
            selectedCharacter = "AUGUSTA";
        } else if (character.equalsIgnoreCase("B")) {
            selectedCharacter = "BEN";
        } else if (character.equalsIgnoreCase("J")) {
            selectedCharacter = "JAKE";
        }
        return selectedCharacter;
    }


    ArrayList<JTextPane> arrayOfPanes = new ArrayList<>(List.of(hattieStats, charlesStats, augustaStats, benStats, jakeStats));
    /**
     * Update the status of each player. If the player is dead, their status is marked "DEAD."
     */
    public void updateStats(int money, int happiness) {
        String partyStatsText= """
                Money: $Money
                Happiness: $Happiness
                """;
        partyStatsText = partyStatsText.replace("$Money","$"+money);
        partyStatsText = partyStatsText.replace("$Happiness",Integer.toString(happiness));
        partyStats.setText(partyStatsText);
        int characterIndex = 0;
        String statsText = """
                HP: $HP
                Clothing: $Clothing
                Healthiness: $Healthiness
                """;
        for (JTextPane stats : arrayOfPanes) {
            if (characterArrayList.get(characterIndex).getHealth() <= 0) {
                stats.setText("DEAD");

            } else {
                String newText = statsText;
                newText = newText.replace("$HP", Integer.toString(characterArrayList.get(characterIndex).getHealth()));
                newText = newText.replace("$Clothing", characterArrayList.get(characterIndex).hasClothingToString());
                newText = newText.replace("$Healthiness",characterArrayList.get(characterIndex).isSickToString());
                stats.setText(newText);

            }
            characterIndex++;
        }
    }



}
