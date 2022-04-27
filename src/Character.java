public class Character {

    //Variables
    private int health;
    private int hunger; //Presumably this is the "food value."
    private int daysSick;
    private String name;
    private String currentHealth;
    private boolean hasClothing = false;
    private boolean isSick = false;

    /**
     * Constructor for the Player class. Every player shall have a default health count,
     * name, role, and default hunger count.
     * @param health - Amount of health set by default.
     * @param name - Name of the player.
     * @param hunger - Amount of hunger set by default.
     */
    public Character(String name, int health, int hunger) {
        this.health = health; //We can report a string "good, poor, very poor, etc" based on this number.
        this.name = name;
        this.hunger = hunger;
    }

    //Getters

    /**
     * Get the current health of the player and return a string based on how much they have.
     * Closer to 100 is better.
     * @return the string value of currentHealth
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


    public void setHasClothing(boolean hasClothing) {this.hasClothing = hasClothing;}
    public boolean getHasClothing() {return hasClothing;}

    public boolean isSick() {
        return isSick;
    }

    public void setSick(boolean sick) {
        isSick = sick;
    }

    public int getDaysSick() {
        return daysSick;
    }

    public void setDaysSick(int daysSick) {
        this.daysSick = daysSick;
    }
}

