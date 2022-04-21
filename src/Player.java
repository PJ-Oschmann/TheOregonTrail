import javax.swing.*;

public class Player {

    //Variables
    private int health;
    private int hunger; //Presumably this is the "food value."
    private int happiness;
    private String name;
    private String role;
    private String currentHealth;
    private String currentHappiness;

    /**
     * Constructor for the Player class. Every player shall have a default health count,
     * name, role, and default hunger count.
     * @param health - Amount of health set by default.
     * @param name - Name of the player.
     * @param role - Role of the player/
     * @param hunger - Amount of hunger set by default.
     */
    public Player(int health, String name, String role, int hunger) {
        this.health = health; //We can report a string "good, poor, very poor, etc" based on this number.
        this.name = name;
        this.role = role; //Perhaps just pass one default value for now
        this.hunger = hunger;
    }

    //Getters

    /**
     * Get the current health of the player and return a string based on how much they have.
     * 0-33 is "good" health.
     * 34-64 is "fair" health.
     * 65-104 is "poor" health.
     * 105-139 is "very poor" health.
     * 140 and greater shows that the player is about to die.
     * @return the string value of currentHealth
     */

    /*
    public String healthToString() {
        if (health >=0 && health < 34) {
            currentHealth = "good";
        }
        else if (health >= 34 && health < 65) {
            currentHealth = "fair";
        }
        else if (health >=65 && health < 105) {
            currentHealth = "poor";
        }
        else if (health >= 105 && health < 139) {
            currentHealth = "very poor";
        }
        else if (health > 140) {
            currentHealth = "about to die";
            //Presumably we'll have a death class or method to check and handle for death cases
            //JOptionPane.showMessageDialog(null,"You died! Game over.");
            System.exit(0);
        }
        return currentHealth;
    }
    */

    public String healthToString() {
        if (health <=100 && health >80) {
            currentHealth = "good";
        }
        else if (health <= 80 && health >60) {
            currentHealth = "fair";
        }
        else if (health <= 60 && health >40) {
            currentHealth = "poor";
        }
        else if (health <=40 && health > 20) {
            currentHealth = "very poor";
        }
        else if (health <= 20) {
            currentHealth = "about to die";
        }
        return currentHealth;
    }

    /**
     * Get the current happiness of the player and return a string based on how happy they are
     * 0-24 implies the player is unhappy.
     * 25-75 implies the player is content.
     * 75-100 implies the player is euphoric.
     * @return the string value of currentHappiness
     */
    public String happinessToString() {
        if (happiness < 25) {
            currentHappiness = "Unhappy";
        }
        else if (happiness >25 && happiness <=75) {
            currentHappiness = "Content";
        }
        else {
            currentHappiness = "Euphoric";
        }
        return currentHappiness;
    }



    /**
     * Consume food from the wagon. Removes 1 consumable from the wagon and restores health
     * @param wagon - Object for the Wagon to take consumables from.
     */
    public void consume(Wagon wagon) {
        int counter = wagon.getConsumablePartSize()-1; //Subtract 1 as this function returns the next open spot in array
        if (counter > 0) {
            if (health-25>=0) {
                health-=5; //NOTE: Hard-coded value! This should be associated with the exact consumable object.
            }
            else {
                health = 0;
            }
            wagon.destroyConsumable(counter);
        }
        else {
            System.out.println("Player.java: No more consumables!");
        }
    }

    /**
     * Player's health getter
     * @return the Player's health as an integer
     */
    public int getHealth() {
        return health;
    }

    /**
     * Player's hunger getter.
     * @return the Player's hunger as an integer
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * Player's name getter
     * @return the Player's name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Player's role getter
     * @return the Player's role as a String
     */
    public String getRole() {
        return role;
    }

    /**
     * Player's happiness getter
     * @return the player's happiness as an integer
     */
    public int getHappiness() {
        return happiness;
    }

    //Setters

    /**
     * Player's health setter
     * @param health - Integer for what the Player's health should be set to.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Player's hunger setter (UNUSED IN THIS MVP)
     * @param hunger - Integer for what the Player's hunger should be set to.
     */
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    /**
     * Player's name setter (UNUSED IN THIS MVP)
     * @param name - String for what the Player's name should be set to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Player's role setter (UNUSED IN THIS MVP)
     * @param role - String for what the Player's role should be set to.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Player's happiness setter
     * @param happiness - Integer for what the Player's happiness should be set to.
     */
    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }


}

