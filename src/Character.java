import javax.swing.*;

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
    public boolean isDead;
    private boolean canDie;

    /**
     * Constructor for the Player class. Every player shall have a default health count,
     * name, role, and default hunger count.
     * @param health - Amount of health set by default.
     * @param name - Name of the player.
     * @param hunger - Amount of hunger set by default.
     */
    public Character(String name, int health, int hunger, boolean isAdult) {
        this.health = health;
        this.name = name;
        this.hunger = hunger;
        this.isAdult = isAdult;
        this.isDead = false;
        this.canDie = true;
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

    /**
     * This method is called when the user enters the code word to turn on "THANOS/God Mode". This ensures they have a
     * near-infinite quantity of all resources and the party characters cannot die.
     */
    public void godMode() {
        this.canDie = false;
        this.setHasClothing(true);
    }

    /**
     * This method is used to set the daysInjured variable to the parameter daysInjured
     * @param daysInjured resetting the daysInjured to 0 on a given character
     */
    public void setDaysInjured(int daysInjured) {
        this.daysInjured = daysInjured;
    }

    /**
     * Gets daysInjured variable
     * @return number of daysInjured an injured character is
     */
    public int getDaysInjured() {
        return daysInjured;
    }

    /**
     * Returns whether the character is dead or not
     * @return true if character is dead, false otherwise
     */
    public boolean getIsDead() {
        return isDead;
    }

    /**
     * This method is used to change the status of a character in terms of whether they are alive or not. The canDie
     * variable is associated with the GodMode cheat that makes it so the characters cannot die.
     * @param isDead will set the character to being dead if true, otherwise nothing will happen to the character
     */
    public void setIsDead(boolean isDead) {
        if (this.canDie) {
            this.isDead = isDead;
            this.canDie = false;
            JOptionPane.showMessageDialog(null,this.getName() + " died.");
        }
        else {
            this.health = 100;
            this.hunger = 0;
        }
    }

    /**
     * Player's hunger setter
     * @param hunger - Integer for what the Player's hunger should be set to.
     */
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    /**
     * This method is used when the user equips clothes onto a character and sets their clothing status to true.
     * @param hasClothing the parameter hasClothing is used to allocate a true or false to equipping the character.
     *                    in question with clothing
     */
    public void setHasClothing(boolean hasClothing) {this.hasClothing = hasClothing;}

    /**
     * Returns whether the character has clothing equipped or does not have clothing equipped.
     * @return true if the character has clothing equipped, false if the character does not have clothing equipped.
     */
    public boolean getHasClothing() {return hasClothing;}

    /**
     * Returns the sick/ill status of a character
     * @return true if the character is sick, false if the character is not sick.
     */
    public boolean isSick() {
        return isSick;
    }

    /**
     * This method is used to print out the character sickness status to the character stats panel in the party
     * dialogue window
     * @return printed string format of their sickness status and how many days they have been sick if they are sick
     */
    public String isSickToString() {
        String sickString = "Healthy";
        if (isSick) {sickString = "Sick, day " + daysSick;}
        return sickString;
    }

    /**
     * This method is used to change the sickness status of a from healthy to sick or sick to getting sick again
     * @param sick sets the characters' sickness status to sick if true.,=
     */
    public void setSick(boolean sick) {
        if (!this.isSick && sick) {
            JOptionPane.showMessageDialog(null, String.format("%s has gotten sick.\n" +
                            "Use medicine to cure them!", this.getName()), "Someone got sick",
                    JOptionPane.WARNING_MESSAGE);
        }
        else if (this.isSick && sick) {
            JOptionPane.showMessageDialog(null, String.format("%s has caught a new sickness.\n" +
                            "Use medicine to cure them!", this.getName()), "Someone got sick again...",
                    JOptionPane.WARNING_MESSAGE);
        }
        isSick = sick;
    }

    /**
     * Days sick getter.
     * @return the number of days the character has been sick for.
     */
    public int getDaysSick() {
        return daysSick;
    }

    /**
     * daysSick setter.
     * @param daysSick the number of days the character has been sick for. Usually only used for resetting the variable
     *                 daysSick to 0 if the character gets sick or was sick and gets sick again.
     */
    public void setDaysSick(int daysSick) {
        this.daysSick = daysSick;
    }

    /**
     * This method is used to print out the character hasClothing status to the character stats panel in the party
     * dialogue window
     * @return printed string format of their hasClothing status
     */
    public String hasClothingToString() {
        String clothingString = "Not protected from harsh weathers";
        if (hasClothing) {
            clothingString = "Protected from harsh weathers";
        }
        return clothingString;
    }

    /**
     * isInjured getter
     * @return true if the character is injured, false if they are not
     */
    public boolean isInjured() {
        return isInjured;
    }

    /**
     * this method setInjured changes character attributes when they become injured. On injury the character loses
     * 10 health which can leda to death and sets their daysInjured to 0, essentially resetting any progress they have
     * made on healing if they were already injured.
     * @param injured true if the character gets injured.
     */
    public void setInjured(boolean injured) {
        isInjured = injured;
        this.setHealth(this.getHealth() - 10);
        this.setDaysInjured(0);
        if (this.getHealth() < 0) {
            this.setHealth(0);
            this.setIsDead(true);
        }
    }

    /**
     * This method is used to print out the character isInjured status to the character stats panel in the party
     * dialogue window.
     * @return printed string format of their isInjured status.
     */
    public String isInjuredToString() {
        String injured = "Not injured";
        if (isInjured) {
            injured = "Injured, day" + daysInjured;
        }
        return injured;
    }

    /**
     * Gets whether the character is an adult or not
     * @return true if the character is an adult, false if the character is not an adult
     */
    public boolean isAdult() {
        return isAdult;
    }

    /**
     * isAdult setter
      * @param adult true if the character should be an adult, false otherwise
     */
    public void setAdult(boolean adult) {
        isAdult = adult;
    }
}

