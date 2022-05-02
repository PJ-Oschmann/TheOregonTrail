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
    private int happiness, money, food, ammo, medicine, clothes, tools, splints, oxen;
    private final OregonTrailGUI game;
    private  String item;
    private Character selectedCharacter;
    private JPanel InterfacePanel;
    private JPanel imagePanel;
    private JLabel imageLabel;
    private JPanel statsPanel;

    public Party(OregonTrailGUI game, String item) {
        //instantiating variables
        this.game = game;
        this.item = item;
        setGlobalVar();
        initializePartyTextArea(item);
        initializePartyTextArea(item);
        this.setMinimumSize(new Dimension(1000,1000));
        //promptTextPane.setText("Select a character to give this " + item.toLowerCase() + " item to!");
        //this.setUndecorated(true);
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
            }
        });
        updateStats();
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

    private void initializePartyTextArea(String itemName) {
        promptTextArea.setText(String.format(
                """
                Please select the character you would like to use %s on using the input text area below:
                
                H: Hattie
                C: Charles
                A: Augusta
                B: Ben
                J: Jake
                
                Press ESC at anytime to exit this menu.
                """, itemName));
    }

    /**
     * Selects a character to use for the desired action.
     * @param character - The character being selected
     * @return A string of the character selected. If the selection was invalid, "INVALID" is returned.
     */
    private boolean selectCharacter(String character) {
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
        partyStatsText = partyStatsText.replace("$Money","$"+money);
        partyStatsText = partyStatsText.replace("$Happiness",Integer.toString(happiness));
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
        updateStats();

    }
    private void eatFood() {
        if (game.getFood()>0) {
            game.setFood(game.getFood()-1);
            selectedCharacter.setHunger(calculateHunger(selectedCharacter,2));
            promptTextArea.setText(selectedCharacter.getName() + " ate some food!");
            staticMethods.resetNFC();
        }
        else {
            staticMethods.notEnoughItem("food");
        }

    }

    private void takeMeds() {
        if(!selectedCharacter.isSick()) {
            JOptionPane.showMessageDialog(null,selectedCharacter.getName()+" is not sick. " +
                    "Please pick another character.",selectedCharacter.getName()+"'s Lack of Illness",
                    JOptionPane.PLAIN_MESSAGE);
        }
        else {
            if(game.getMedicine()>0){
                game.setMedicine(game.getMedicine()-1);
                selectedCharacter.setSick(false);
                promptTextArea.setText(selectedCharacter.getName()+" has been cured!");
            }
            else {
                staticMethods.notEnoughItem("medicine");
            }
        }
    }

    private void equipClothes() {
        if (selectedCharacter.getHasClothing()) {
            JOptionPane.showMessageDialog(null,selectedCharacter.getName()+" already has " +
                    "protective clothing.",selectedCharacter.getName()+"'s Clothing",JOptionPane.PLAIN_MESSAGE);
        }
        else {
            if (game.getClothes()>0) {
                game.setClothes(game.getClothes()-1);
                selectedCharacter.setHasClothing(true);
                promptTextArea.setText(selectedCharacter.getName()+" now has protective " + "clothing.");
            }
            else {
                staticMethods.notEnoughItem("clothes");
            }
        }
    }

    private void useSplints() {
        if (!selectedCharacter.isInjured()) {
            JOptionPane.showMessageDialog(null,selectedCharacter.getName()+" is not injured. " +
                    "Please select another character.",selectedCharacter.getName()+"'s Lack of Injury",
                    JOptionPane.PLAIN_MESSAGE);
        }
        else {
            if (game.getSplints()>0){
                selectedCharacter.setInjured(false);
                promptTextArea.setText(selectedCharacter.getName()+" now has " + "protective clothing.");
            }
            else {
                staticMethods.notEnoughItem("splints");
            }


        }

    }


    private final WindowAdapter windowClose = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            onCancel();
        }
    };
}
