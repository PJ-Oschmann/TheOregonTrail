import javax.swing.*;

public class staticMethods {

    public static int noFoodCounter = 0;
    public static void notValidInput() {
        JOptionPane.showMessageDialog(null, "That is not a valid input",
                "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public static void resetNFC() {
        noFoodCounter = 0;
    }

    public static void incrementNFC() {
        noFoodCounter++;
    }

    public static int getNFC() {
        return noFoodCounter;
    }

    public static void notEnoughMoney() {
        JOptionPane.showMessageDialog(null, "You don't have enough money to do this.",
                "INVALID", JOptionPane.ERROR_MESSAGE);
    }

    public static void notEnoughItem(String item) {
        JOptionPane.showMessageDialog(null,"You don't have enough " + item + " to do this.",
                "INVALID",JOptionPane.ERROR_MESSAGE);
    }
}
