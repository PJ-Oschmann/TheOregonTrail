import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private JLabel questionText;
    private JTextPane promptTextPane;
    private JTextArea partyTextArea;
    public ArrayList<Character> characterArrayList;
    private int happiness, money, food, ammo, medicine, clothes, tools, splints, oxen;
    private final OregonTrailGUI game;
    private  String item;
    private Character selectedCharacter;

    public Party(OregonTrailGUI game, String item) {
        //instantiating variables
        this.game = game;
        this.item = item;
        setGlobalVar();
        this.setTitle("PARTY");
        this.setMinimumSize(new Dimension(1000,300));

        //this.setUndecorated(true);
        setContentPane(contentPane);
        setModal(true);

        userInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectCharacter(userInput.getText());
                doAction(item);
                game.writeGameInfo();
                userInput.setText("");
            }
        });
        updateStats(money, happiness);
        userInput.addFocusListener(inputHelp);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(windowClose);

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Selects a character to use for the desired action.
     * @param character - The character being selected
     * @return A string of the character selected. If the selection was invalid, "INVALID" is returned.
     */
    public void selectCharacter(String character) {
        int charIndex;
        if (character.equalsIgnoreCase("H")) {
            charIndex = 0;
        } else if (character.equalsIgnoreCase("C")) {
            charIndex = 1;
        } else if (character.equalsIgnoreCase("A")) {
            charIndex = 2;
        } else if (character.equalsIgnoreCase("B")) {
            charIndex = 3;
        } else if (character.equalsIgnoreCase("J")) {
            charIndex = 4;
        }
        else {
            staticMethods.notValidInput();
        }
        selectedCharacter = characterArrayList.get(charIndex);
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
                Injured: $Injured
                
                """;
        for (JTextPane stats : arrayOfPanes) {
            if (characterArrayList.get(characterIndex).getHealth() <= 0) {
                stats.setText("DEAD");

            } else {
                String newText = statsText;
                newText = newText.replace("$HP", Integer.toString(characterArrayList.get(characterIndex).getHealth()));
                newText = newText.replace("$Clothing", characterArrayList.get(characterIndex).hasClothingToString());
                newText = newText.replace("$Healthiness",characterArrayList.get(characterIndex).isSickToString());
                newText = newText.replace("$Injured",characterArrayList.get(characterIndex).isInjuredToString());
                stats.setText(newText);

            }
            characterIndex++;
        }
    }

    private void setGlobalVar() {
        this.happiness = game.getHappiness();
        this.money = game.getMoney();
        this.characterArrayList = game.getCharacterArrayList();
        this.food = game.getFood();
        this.ammo = game.getAmmunition();
        this.medicine = game.getMedicine();
        this.clothes = game.getClothes();
        this.tools = game.getWagonTools();
        this.splints = game.getSplints();
        this.oxen = game.getOxen();
    }

    private void passBackVar() {
        game.setHappiness(this.happiness);
        game.setMoney(this.money);
        game.setCharacterArrayList(this.characterArrayList);
        game.setFood(this.food);
        game.setAmmunition(this.ammo);
        game.setMedicine(this.medicine);
        game.setClothes(this.clothes);
        game.setWagonTools(this.tools);
        game.setSplints(this.splints);
        game.setOxen(this.oxen);
    }

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

    private void onCancel() {
        passBackVar();
        dispose();
    }


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

    private void doAction(String item) {
        if (item.equals("FOOD")) {eatFood();}
        else if (item.equals("MEDICINE")) {takeMeds();}
        else if (item.equals("CLOTHES")) {equipClothes();}
        else if (item.equals("SPLINTS")) {useSplints();}
        dispose();
    }
    private void eatFood() {
        selectedCharacter.setHunger(calculateHunger(selectedCharacter,2));
    }

    private void takeMeds() {
        if(selectedCharacter.isSick()) {
            JOptionPane.showMessageDialog(null,selectedCharacter.getName()+" is not sick. Please pick another character.",selectedCharacter.getName()+"'s Lack of Illness",JOptionPane.PLAIN_MESSAGE);
        }
        else {
            selectedCharacter.setSick(false);
            JOptionPane.showMessageDialog(null,selectedCharacter.getName()+" has been cured!",selectedCharacter.getName()+"'s Illness",JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void equipClothes() {
        if (selectedCharacter.getHasClothing()) {
            JOptionPane.showMessageDialog(null,selectedCharacter.getName()+" already has protective clothing.",selectedCharacter.getName()+"'s Clothing",JOptionPane.PLAIN_MESSAGE);
        }
        else {
            selectedCharacter.setHasClothing(true);
            JOptionPane.showMessageDialog(null,selectedCharacter.getName()+" now has protective clothing.",selectedCharacter.getName()+"'s Clothing",JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void useSplints() {
        if (!selectedCharacter.isInjured()) {
            JOptionPane.showMessageDialog(null,selectedCharacter.getName()+" is not injured. Please select another character.",selectedCharacter.getName()+"'s Lack of Injury",JOptionPane.PLAIN_MESSAGE);
        }
        else {
            selectedCharacter.setInjured(false);
            JOptionPane.showMessageDialog(null,selectedCharacter.getName()+" now has protective clothing.",selectedCharacter.getName()+"'s Clothing",JOptionPane.PLAIN_MESSAGE);
        }

    }


    private final WindowAdapter windowClose = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            onCancel();
        }
    };
}
