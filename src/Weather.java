/**
 * This class keeps track of the current weather, both in terms of temperature and a
 * description. For temperatures:
 *      * Less than 10: very cold
 *      * 10-29: cold
 *      * 30-49: cool
 *      * 50-69: warmprivate String rationSize;
 *      * 70-89: hot
 *      * Greater than or equal to 90: very hot
 * The weather is set randomly based on a random temperature between 1 and 100. The
 * description is set afterward.
 */
public class Weather {
    private final java.util.Random rand = new java.util.Random();
    //private final String[] weatherType = new String[]{"FREEZING","COLD","WARM","HOT"};
    private String currentWeather;
    private int temperature;

    /**
     * Constructor for Weather class
     */
    public Weather() {
        currentWeather = "";
        temperature = 0;
    }

    /**
     * Getter for the current weather conditions
     * @return current weather as a String.
     */
    public String getWeather() {
        if (currentWeather.equals("")) {
            return "NO WEATHER SET";
        }
        else {
            return currentWeather;
        }
    }

    /**
     * Set the temperature to a random number between 1-100
     */
    public void setTemperature() {
        temperature = rand.nextInt(100)+1;

    }

    /**
     * Set the weather to a random temperature and set the current weather conditions String
     * based on that temperature.
     * Less than 10: very cold
     * 10-29: cold
     * 30-49: cool
     * 50-69: warm
     * 70-89: hot
     * Greater than or equal to 90: very hot
     */
    public void setWeather() {
        setTemperature();
        if (temperature < 10) {
            currentWeather = "very cold";
        }
        else if (temperature <30) {
            currentWeather = "cold";
        }
        else if (temperature <50) {
            currentWeather = "cool";
        }
        else if (temperature < 70) {
            currentWeather = "warm";
        }
        else if (temperature < 90) {
            currentWeather = "hot";
        }
        else {
            currentWeather = "very hot";
        }

        System.out.println("Weather.java: Current weather is " + getWeather());
    }
}
