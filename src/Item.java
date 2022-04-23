/**
 * Item class designed to handle any items that are
 * usable by the character/party. Our current Item class
 *  handles food, ammunition, medicine, clothes, wagon tools, splints, and oxen.
 */
public class Item {
    private String name;
    private String type; //Food, Ammunition, Medicine, Clothes, Wagon Tools, Splints, Oxen



    //For now let's assume each food is just a pound.
    /**
     * Constructor for the consumable class, any consumable
     * creation must have a name and type. Since we are only
     * working with food it must also have a restoreHealth
     * function
     */
    Item (String name, String type) {
        this.name = name;
        this.type = type;
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

}
