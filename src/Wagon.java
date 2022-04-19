/**
 * The Wagon class keeps track of all consumables in an array known as the "Consumable Partition."
 * It can generate Consumable objects and append them to said array, as well as remove
 * Consumable objects at a specified index. The number of Consumable objects in the
 * Consumable Partition can be returned as well.
 *
 * The Wagon class also keeps track of the current pace of the wagon, as an integer. A pace of
 * 1 = resting, 2 = steady, 3 = strenuous, and 4 = grueling.
 *
 * This class also keeps track of the amount of rations available to the party. When there are
 * 3 pounds (instances) of Consumables available to each party member, the size is "filling."
 * For 2 pounds per party member, the size is "meager."
 * For 1 pound per party member, the size is "bare bones."
 */

//!!!! IMPORTANT !!!! Some lines commented out as not all files are present. They are commented for the code to compile.

public class Wagon {
    //private int foodAmount;
    private int pace;


    //private final Consumable[] consumablePart = new Consumable[2000];

    //Pace is currently set to the Wagon. Should it one day be set to an "Oxen" class?
    //Paces:
    /*
    1 = Resting
    2 = Steady
    3 = Strenuous
    4 = Grueling
     */

    /**
     * Constructs the Wagon object.
     * @param foodAmount - the amount (in pounds) of food we want to add to the wagon
     * @param pace - Set the default pace of the wagon.
     */
    public Wagon(int foodAmount,int pace) {
        //this.foodAmount = foodAmount; //Food amount is hopefully deprecated
        consumableGenerator(foodAmount,"Beans","Cooked Food",5);
        this.pace = pace;
    }

    /**
     * Generate consumable objects and add them to the wagon's consumablePart array.
     * @param times - How many times the generator should be run
     * @param name - Name of the consumable that will be generated
     * @param type - Type of consumable that will be generated
     * @param restoreHealth - The amount of health that the consumable will restore.
     */
    public void consumableGenerator(int times, String name, String type, int restoreHealth) { //Add params for name, type, and restoreHealth for Consumable
        for (int i=0;i<times;i++) {
            //Find out where to add the next food:
            int counter = getConsumablePartSize();
            System.out.println("Wagon.java: Generating some yummy beans in consumablePart at index " + counter);
            //consumablePart[counter] = new Consumable(name,type,restoreHealth);
        }
    }

    /**
     * Set the rations size.
     * 3 pounds for the entire party = "filling"
     * 2 pounds for the entire party = "meager"
     * 1 pound for the entire party = "bare bones"
     * @param playerListSize - Size of the player party
     * @return String of the ration size
     */
    public String setRations(int playerListSize) {
        String rationSize;
        int foodCount = getConsumablePartSize();
        if (foodCount >= 3*playerListSize) {
            rationSize = "filling";
        }
        else if (foodCount >= 2*playerListSize && foodCount <3*playerListSize) {
            rationSize = "meager";
        }
        else if (foodCount <2*playerListSize) {
            rationSize = "bare bones";
        }
        else {
            rationSize = "NO RATION SIZE";
        }
        return rationSize;
    }

    /**
     * Set the pace of the wagon.
     * 1 = resting
     * 2 = steady
     * 3 = strenuous
     * 4 = grueling
     * @param pace - The pace of the wagon being set.
     */
    public void setPace(int pace) {
        this.pace = pace;
    }

    /**
     * Get the current pace as a string
     * 1 = resting
     * 2 = steady
     * 3 = strenuous
     * 4 = grueling
     * @return the current pace as a string.
     */
    public String getPace(){
        String currentPace;
        if (pace == 1) {
            currentPace = "resting";
        }
        else if (pace == 2) {
            currentPace = "steady";
        }
        else if (pace == 3) {
            currentPace = "strenuous";
        }
        else if (pace == 4) {
            currentPace = "grueling";
        }
        else {
            currentPace = "NO PACE SET";
        }
        return  currentPace;

    }

    /**
     * Get the current pace in its integer
     * 1 = resting
     * 2 = steady
     * 3 = strenuous
     * 4 = grueling
     * @return pace as integer
     */
    public int getPaceInt() {
        return pace;
    }

    /**
     * Get the size of the Consumable Partition of the Wagon.
     * Since it is an array with a size of 2000, we need to find the first null reference in the array.
     * @return the size of the Consumable Partition
     */
    public int getConsumablePartSize() {
        boolean found = false;
        int counter = 0;
        //while (!found) {
        //    if (consumablePart[counter] == null) {found=true;}
         //   else {counter++;}
      //  }
        return counter;
    }


    /**
     * Destroy the referenced consumable object in the Consumable Partition of the Wagon.
     * @param index - The index of the array to destroy (make null).
     */
    public void destroyConsumable(int index) {
        try {
            System.out.println("Wagon.java: Destroyed consumable at index " + index);
            //consumablePart[index] = null;
        }
        catch (Exception e) {
            System.out.println("Wagon.java: There are probably no more index in the wagon, but let's take a look at that exception: "  + e);
        }
    }
}

