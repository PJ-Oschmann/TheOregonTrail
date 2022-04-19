/**
 * Consumable class designed to handle any consumables that are
 * usable by the character/party. Our current consumable class
 * only handles food and the health restoration function.
 */
public class Consumable {
    private String name;
    private String type; //Raw Food, Cooked Food, Drink, Hard drink [For MVP just Cooked Food]
    private int restoreHealth;

    //For now let's assume each food is just a pound.
    /**
     * Constructor for the consumable class, any consumable
     * creation must have a name and type. Since we are only
     * working with food it must also have a restoreHealth
     * function
     *
     * IMPLEMENT THIRST/WATER LATER
     */
    Consumable(String name, String type, int restoreHealth) {
        this.name = name;
        this.type = type;
        this.restoreHealth = restoreHealth;
    }

    /**
     * name getter
     * @return String name - the name of the consumable
     */
    public String getName() {
        return name;
    }

    /**
     * type getter
     * @return String type - the type of the consumable
     */
    public String getType() {
        return type;
    }

    /**
     * restoreHealth getter
     * @return int restoreHealth - the quantity of health that the consumable will restore
     */
    public int getRestoreHealth() {
        return restoreHealth;
    }

    /**
     * name setter
     * @param name - the new name that the consumable will be set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * type setter
     * @param type - the new type that the consumable will be set to
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * restoreHealth setter
     * @param restoreHealth - the new value for restoreHealth
     */
    public void setRestoreHealth(int restoreHealth) {
        this.restoreHealth = restoreHealth;
    }
}
