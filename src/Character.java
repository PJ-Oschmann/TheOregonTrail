public class Character {

    //Variables
    private int health;
    private int hunger; //Presumably this is the "food value."
    private int daysSick;
    private String name;
    private String currentHealth;
    private boolean hasClothing = false;
    private boolean isSick = false;
    private boolean isInjured = false;
    private int daysInjured;
    private boolean isAdult = false;

    /**
     * Constructor for the Player class. Every player shall have a default health count,
     * name, role, and default hunger count.
     * @param health - Amount of health set by default.
     * @param name - Name of the player.
     * @param hunger - Amount of hunger set by default.
     */
    public Character(String name, int health, int hunger, boolean isAdult) {
        this.health = health; //We can report a string "good, poor, very poor, etc" based on this number.
        this.name = name;
        this.hunger = hunger;
        this.isAdult = isAdult;
    }

    //Getters

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

    public void setDaysInjured(int daysInjured) {
        this.daysInjured = daysInjured;
    }

    public int getDaysInjured() {
        return daysInjured;
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

    public String isSickToString() {
        String sickString = "Healthy";
        if (isSick) {sickString="Sick, day "+daysSick;}
        return sickString;
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
    public String hasClothingToString() {
        String clothingString = "Not protected from harsh weathers";
        if (hasClothing) {
            clothingString = "Protected from harsh weathers";
        }
        return clothingString;
    }

    public boolean isInjured() {
        return isInjured;
    }

    public void setInjured(boolean injured) {
        isInjured = injured;
    }
    public String isInjuredToString() {
        String injured = "Not injured";
        if (isInjured) {
            injured = "Injured";
        }
        return injured;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }
}

