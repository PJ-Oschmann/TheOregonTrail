/**
 * This class keeps track of the current weather, both in terms of a description.
 * The weather has a 10% chance of being "good," a 10% of being "bad," and an 80% chance of
 * being "neutral." Neutral weather may have light rain or light wind. Bad weather may be
 * extremely cold or extremely hot, stormy, or foggy.
 * Good weather increases the party's happiness by 5. Bad weather decreases it by 5.
 */
public class Weather {
    private final java.util.Random rand = new java.util.Random();
    private String currentWeather;
    private String weatherCondition;


    /**
     * Constructor for Weather class
     */
    public Weather() {
        currentWeather = "";
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
            int selectExtra = rand.nextInt(3);

            if (selectTemperature == 0) {
                temperatureCondition = "Freezing";
            }
            else {
                temperatureCondition = "Blazing hot";
            }

            if (selectExtra == 0) {
                extraCondition = " and really windy";
            }
            else if (selectExtra == 1) {
                extraCondition = " and stormy";
            }
            else { //if selectExtra == 2
                extraCondition = " and foggy";
            }

        }
        currentWeather = temperatureCondition+extraCondition;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }
}

