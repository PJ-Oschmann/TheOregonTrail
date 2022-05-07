/**
 * This is a public class containing statically used elements across different classes and the main game class.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class staticMethods {
    //noFoodCounter used to track if no food has been consumed in 3 days, which is one of our loss conditions.
    public static int noFoodCounter = 0;

    /**
     * The notValidInput method is one of our globally used error notifications for the user if they input something
     * invalid into a textfield actionListener.
     */
    public static void notValidInput() {
        JOptionPane.showMessageDialog(null, "That is not a valid input",
                "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * The resetNFC method is triggered when food is consumed, bringing back the value to 0. This means that some food
     * has been consumed and the party should not lose for not eating.
     */
    public static void resetNFC() {
        noFoodCounter = 0;
    }

    /**
     * The increment NFC is another branch of the methods relating to the loss condition of not eating food. If no food
     * is consumed in a given day then the amount of days that no food has been consumed increments.
     */
    public static void incrementNFC() {
        noFoodCounter++;
    }

    /**
     * noFoodCounter getter.
     * @return the number of days that no food has been consumed.
     */
    public static int getNFC() {
        return noFoodCounter;
    }

    /**
     * The notEnoughMoney method is used in the shop, fort, and river classes to notify the user that they do not have
     * enough currency to select a specific option or action.
     */
    public static void notEnoughMoney() {
        JOptionPane.showMessageDialog(null, "You don't have enough money to do this.",
                "INVALID", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * The notEnoughItem method is used across the inventory and activities classes to notify the user that they do not
     * have sufficient resource to complete a selected option or input.
     * @param item the String-format item that they are lacking.
     */
    public static void notEnoughItem(String item) {
        JOptionPane.showMessageDialog(null,"You don't have enough " + item + " to do this.",
                "INVALID",JOptionPane.ERROR_MESSAGE);
    }

    /**
     * static method to read images to display into the JAR
     */
    public static ImageIcon getImage(String path) {
        InputStream is = OregonTrailGUI.class.getResourceAsStream(path);
        if (is == null) {
            return null;
        }
        try {
           return new ImageIcon(ImageIO.read(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
