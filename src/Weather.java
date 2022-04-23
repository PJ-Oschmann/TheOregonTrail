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
    private String currentWeather;
    private String weatherCondition;
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
    public String toString() {
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
    public void setRandomTemperature() {
        temperature = rand.nextInt(100)+1;

    }



    public void setTemperature(int temperature) {
        this.temperature = temperature;
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
    public void setRandomWeather() {

        String extraCondition="";
        String temperatureCondition;
        int selectWeatherType = rand.nextInt(10)+1;
        if (selectWeatherType == 1) {
            weatherCondition = "Good";
        }
        else if (selectWeatherType == 2) {
            weatherCondition = "Bad";
        }
        else {
            weatherCondition = "Neutral";
        }

        if (weatherCondition.equals("Good")) {
            temperatureCondition = "Really nice";
        }

        else if (weatherCondition.equals("Neutral")) {
            int selectRain = rand.nextInt(5);
            int selectTemperature = rand.nextInt(2);
            if (selectTemperature == 0) {
                temperatureCondition = "Cold";
            }
            else {
                temperatureCondition = "Warm";
            }

            if (selectRain == 0) {
                extraCondition = " with light rain";
            }
            else if (selectRain == 1) {
                extraCondition = " with light wind";
            }

        }
        else { //if weatherCondition.equals("Bad");
            int selectRain = rand.nextInt(5);
            int selectTemperature = rand.nextInt(2);
            int selectExtra = rand.nextInt(4);

            if (selectTemperature == 0) {
                temperatureCondition = "Really cold";
            }
            else {
                temperatureCondition = "Really warm";
            }

            if (selectExtra == 0) {
                extraCondition = " and freezing";
            }
            else if (selectExtra == 1) {
                extraCondition = " and really windy";
            }
            else if (selectExtra == 2) {
                extraCondition = " and stormy";
            }
            else { //if selectExtra == 3
                extraCondition = " and foggy";
            }

        }
        currentWeather = temperatureCondition+extraCondition;

        /*
        setRandomTemperature();
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

         */

        System.out.println("Weather.java: Current weather is " + currentWeather);
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

}

