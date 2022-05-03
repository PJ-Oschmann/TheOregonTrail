import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiverGUI extends JDialog {
    private JPanel contentPane;
    public JTextField inputText;
    private JTextArea promptTextArea;
    public JLabel riverImage;
    private JLabel promptLabel;
    private JButton buttonOK;
    private OregonTrailGUI game;
    private Wagon wagon;
    private Location location;

    public RiverGUI(Location location, OregonTrailGUI game) {
        this.game = game;
        this.location = location;
        this.wagon = game.getWagon();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        inputText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crossRiver();
            }
        });
    }
    private void resetRiver() {
        inputText.setText("");
    }

    public void crossRiver() {
        crossChoice = inputText.getText();
        switch (crossChoice) {
            case "1" -> {
                System.out.println("1");
                if (takeFerry()) {
                    dispose();
                } else {
                    resetRiver();
                }
            }
            case "2" -> {
                System.out.println("2");
                if (buildRaft()) {
                    dispose();
                } else {
                    resetRiver();
                }
            }
            case "3" -> {
                System.out.println("3");
                if (crossAlone()) {
                    dispose();
                } else {
                    resetRiver();
                }
            }
            default -> {
                System.out.println("DEFAULT");
                staticMethods.notValidInput();
                resetRiver();
            }
        }
    }

    public void setText(String message) {
        promptTextArea.setText(message);
    }
    String crossChoice;
    public void getText() {
        location.setRiverChoice(inputText.getText());
        dispose();
    }

    //Booleans: true = crossed; false = didn't. (Still true if someone gets hurt/sick/etc but crossed successfully)
    public boolean takeFerry() {
        if (game.getMoney() >= 20) {
            game.setMoney(game.getMoney() - 20);
            return true;
        }
        else {
            staticMethods.notEnoughMoney();
        }
        return false;
    }


    public boolean buildRaft() {
        if (game.getWagonTools() >= 2) {
            game.setWagonTools(game.getWagonTools() - 2);

            //10% 1 oxen dies
            if (game.rand.nextInt(9) == 0) {
                game.setOxen(game.getOxen() - 1);
                if (game.getOxen() == 0) {
                    staticMethods.cantCross("All your oxen died while crossing.\nYou can't continue your journey.");
                    game.checkIfLost();
                    return false;
                }
            }

            //15% for someone to get sick
            if (game.rand.nextInt(99) <= 15) {
                int characterIndex = game.rand.nextInt(game.characterArrayList.size());
                game.characterArrayList.get(characterIndex).setSick(true);
            }

            //5% wagon damage
            if (game.rand.nextInt(20) == 0) {
                wagon.setState(wagon.getState() + 1);
                if (wagon.getState() == 2) {
                    game.checkIfLost();
                }
            }
            return true;
        }
        else {
            staticMethods.notEnoughItem("WAGON TOOLS");
            return false;
        }
    }

    public boolean crossAlone() {
        //10% chance 1 oxen dies
        if (game.rand.nextInt(9) == 0 && game.getOxen() > 0) {
            game.setOxen(game.getOxen() - 1);
            if (game.getOxen() <= 0) {
                game.checkIfLost();
                staticMethods.cantCross("All your oxen died while crossing. Everyone drowned.");
                return false;
            }
        }
        //or 20% chance 2 oxen die
        else if (game.rand.nextInt(4) == 0) {
            if (game.getOxen() == 1) {
                game.setOxen(game.getOxen() - 1);
            }
            else {
                game.setOxen(game.getOxen() - 2);
            }
            if (game.getOxen() <= 0) {
                game.checkIfLost();
                staticMethods.cantCross("All your oxen died while crossing. Everyone drowned.");
                return false;
            }
        }

        //5% chance someone drowns
        if (game.rand.nextInt(19) == 0) {
            boolean someoneDrowns = false;
            int characterIndex;
            do {
                characterIndex = game.rand.nextInt(game.characterArrayList.size());
                if (!game.characterArrayList.get(characterIndex).getIsDead()) {
                    game.characterArrayList.get(characterIndex).setIsDead(true);
                    someoneDrowns = true;
                    game.checkIfLost();
                }
            } while (!someoneDrowns);
        }

        //15% chance someone gets sick
        if (game.rand.nextInt(99) <= 15) {
            int characterIndex = game.rand.nextInt(game.characterArrayList.size());
            game.characterArrayList.get(characterIndex).setSick(true);
        }

        //10% chance wagon breaks
        if (game.rand.nextInt(10) == 0) {
            wagon.setState(wagon.getState() + 1);
            game.checkIfLost();
        }
        return true;
    }
}
