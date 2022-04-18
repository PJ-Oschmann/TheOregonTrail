import javax.swing.*;

/**
 * This class handles random events. The events are stored in an array and chosen
 * at random based on the length of the array. Random numbers can also be generated
 * from 1 to an upper number, inclusive, using this class.
 */
public class RandomEvent {
    private final java.util.Random rand = new java.util.Random();
    private final String[] eventArray = new String[]{"NONE","WEATHER","ROBBERS"}; //Used to see number of events we have without needing to count.

    Weather weather;
    Wagon wagon;

    //Check pages 12-13 of MP2_HattieCampbell_OregonTrail for random events.
    //Note: Currently, weather changes are handled as a Random Event. Do we change this?

    /**
     * Constructor for Random Events
     */
    public RandomEvent(Weather weather, Wagon wagon) {
        this.weather = weather;
        this.wagon = wagon;
    }


    /**
     * Pick an event to perform at random.
     * @return the String for which event should be performed.
     */
    public void doRandomEvent() {

        int newEvent = rand.nextInt(eventArray.length);
        System.out.println("RandomEvent.java: Random event is " + eventArray[newEvent]);
        if (newEvent==0) {
            System.out.println("RandomEvent.java: Code for NONE executes here.");

        }
        else if (newEvent==1) {
            System.out.println("RandomEvent.java: Code for WEATHER executes here.");
            setRandomWeather();

        }
        else if (newEvent==2) {
            System.out.println("RandomEvent.java: Code for ROBBERS executes here.");
            robTheWagon();

        }
        else {
            
        }

    }

    /**
     * Generates a random number, up to the bound specified. 1 is added to the final
     * value, so 0 is never returned and the upper bound becomes inclusive in the
     * set.
     * @param upperBound - Integer for what the upper bound should be, inclusive
     * @return a random number between 1 and the upper bound, inclusive.
     */
    public int generateRandomNumber(int upperBound) {
        return rand.nextInt(upperBound)+1;
    }

    public void setRandomWeather() {
        weather.setWeather();
    }
    public void robTheWagon() {
        if (generateRandomNumber(3)==3 && wagon.getConsumablePartSize() > 0) {
            int itemsToSteal = generateRandomNumber(3);
            System.out.println("OregonTrailUI.java: The wagon was robbed of " + itemsToSteal + " consumables!");
            System.out.println("OregonTrailUI.java: OLD wagon size: " + wagon.getConsumablePartSize());
            for (int i=0;i<itemsToSteal;i++) {
                int index = wagon.getConsumablePartSize()-1;
                wagon.destroyConsumable(index);
            }
            System.out.println("OregonTrailUI.java: NEW wagon size: " + wagon.getConsumablePartSize());
            JOptionPane.showMessageDialog(null, "Some robbers have found your wagon and stole " + itemsToSteal + " consumables! You now have " + wagon.getConsumablePartSize() + " consumables.");

        }
        else {
            System.out.println("OregonTrailUI.java: Not robbing.");
        }
    }
}
