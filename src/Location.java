/**
 * The location class is used to track the location of the character/party during
 * their journey from Missouri to Oregon City. This object will hold
 * the name of the location the party is at in the game and the location will change
 * depending on how much distance they have traveled.
 */
import javax.management.RuntimeErrorException;
import javax.swing.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Location {

    private int pace;
    private OregonTrailGUI game;
    private RiverGUI river;
    private FortGUI fort;
    private Scene scene = new Scene();
    private int milesTravd = 0;
    private String currentLocation = "Independence";
    private String riverChoice;
    private int markerCounter = 1; //Index of the milage marker we're at
    public ArrayList<Integer> mileMarkers = new ArrayList<>(List.of(0,17, 50, 80, 108, 221, 273, 550, 591, 620, 672,
            1063, 1279, 1454, 1700, 1900, 2000, 2170));
    public ArrayList<String> names = new ArrayList<>(List.of("Independence","Blue River", "Wakarusa River",
            "Kansas River", "Vermilion River", "Little Blue River", "Big Blue River","Fort Kearny", "Courthouse Rock", "Chimney Rock",
            "Scotts Bluff", "Fort Laramie", "Fort Bridger", "Fort Hall", "Three Island Crossing", "Fort Boise",
            "Blue Mountains", "Oregon City"));

    public Location(OregonTrailGUI game) {
        this.game = game;
        this.pace = game.getCurrentPace();
        this.river = new RiverGUI(this, game);
        this.fort = new FortGUI(game);
    }

    /**
     * Gets riverChoice
     * @return
     */
    public String getRiverChoice() {
        return riverChoice;
    }

    /**
     * Sets riverChoice
     * @param riverChoice
     */
    public void setRiverChoice(String riverChoice) {
        this.riverChoice = riverChoice;
    }

    /**
     * Gives the user three choices to cross the river. If the player enters "1," they can cross by ferry for $20. If
     * they enter "2," they can build a raft using their wagon tools. If they enter "3," they can attempt to swim
     * across. When  method is called, the River window is shown.
     */
    public void crossRiver() {
        river.setText("You reached " + names.get(markerCounter) + "! How would you like to cross? You can:\n" +
                "1 - Take the Ferry for $20\n2 - Build a raft using 2 of your wagon tools\n3 - Attempt to swim" +
                " across.");
        river.inputText.setText("");
        river.pack();
        river.riverImage.setIcon(new javax.swing.ImageIcon("src/assets/images/river.png"));
        river.setVisible(true);
        river.crossRiver();
    }

    /**
     * Gives the user 4 choices of what to do at a fort. If the player enters "1," they can go to the local shop. If
     * they enter "2," they can recover health and hunger at the local shop. If they enter "3," they can repair their
     * wagon if it is damaged. If they enter "4," they leave the fort.
     */
    public void seeFort() {
        fort.setText("You reached " + names.get(markerCounter) + "! You can:\n" +
                "1 - Grab goods from the local shop\n" +
                "2 - Rest up at the inn\n" +
                "3 - Repair a damaged wagon at the local Wagon Repair Shop\n" +
                "4 - Leave the fort and continue your journey");
        fort.inputText.setText("");
        fort.pack();
        fort.fortImage.setIcon(new javax.swing.ImageIcon("src/assets/images/fort.png"));
        fort.setVisible(true);
    }

    /**
     * Discovers the current U.S. state the player is in.
     * @return the String of whatever U.S. state the player is in.
     */
    public String getCurrentState() {
        if(markerCounter-1==0){
            return "Missouri";
        }
        else if (markerCounter-1>=1&&markerCounter-1<4){
            return "Kansas";
        }
        else if (markerCounter-1>=4&&markerCounter-1<10) {
            return "Nebraska";
        }
        else if (markerCounter-1>=10&&markerCounter-1<12){
            return "Wyoming";
        }
        else if (markerCounter-1>=12&&markerCounter-1<15) {
            return "Idaho";
        }
        else {
            return "Oregon";
        }
    }

    /**
     * Progresses the story line. Loads scenes when a landmark, river, or fort is reached.
     */
    boolean firstTimeInLocation=false;
    public void doStoryLine() {
        int index = 0;
        if (markerCounter-1>=0) {index=markerCounter-1;}
        else {index=markerCounter;}
        if (firstTimeInLocation){
            switch (names.get(index)) {
                case "Kansas River" -> scene.loadScene("kansasRiver",game.date.toString());
                case "Vermilion River" -> scene.loadScene("vermilionRiver",game.date.toString());
                case "Little Blue River" -> scene.loadScene("littleBlueRiver",game.date.toString());
                case "Big Blue River" -> scene.loadScene("bigBlueRiver",game.date.toString());
                case "Fort Kearny" -> scene.loadScene("fortKearny",game.date.toString());
                case "Courthouse Rock" -> scene.loadScene("courthouseRock",game.date.toString());
                case "Chimney Rock" -> scene.loadScene("chimneyRock",game.date.toString());
                case "Scotts Bluff" -> scene.loadScene("scottsBluff",game.date.toString());
                case "Fort Laramie" -> scene.loadScene("fortLaramie",game.date.toString());
                case "Fort Bridger" -> scene.loadScene("fortBridger",game.date.toString());
                case "Fort Hall" -> scene.loadScene("fortHall",game.date.toString());
                case "Three Island Crossing" -> scene.loadScene("threeIslandCrossing",game.date.toString());
                case "Fort Boise" -> scene.loadScene("fortBoise",game.date.toString());
                case "Blue Mountains" -> scene.loadScene("blueMountains",game.date.toString());
                case "Oregon City" -> scene.loadScene("oregonCity",game.date.toString());
            }
            firstTimeInLocation=false;
        }

    }

    /**
     * Adds total mileage when called. Acts as "daily traveled mileage."
     * When the pace is set to 0, 15 miles are traveled daily.
     * When pace is set to 1, 20 miles are traveled daily.
     * When pace is set to 2, 25 miles are traveled daily.
     */
    public void addMileage() {
        int miles;
        pace = game.getCurrentPace();
        if (pace == 0) {miles = 15;}
        else if (pace == 1) {miles = 20;}
        else if (pace == 2) {miles = 25;}
        else  {miles = 100;}
        milesTravd += miles;
        try {
            //River
            if ((milesTravd >= mileMarkers.get(markerCounter)) && (names.get(markerCounter).contains("River"))) {
                crossRiver();
                currentLocation = names.get(markerCounter);
                milesTravd=mileMarkers.get(markerCounter);
                markerCounter++;
                firstTimeInLocation=true;
            }
            //Fort
            else if ((milesTravd >= mileMarkers.get(markerCounter)) && (names.get(markerCounter).contains("Fort"))) {
                seeFort();
                currentLocation = names.get(markerCounter);
                milesTravd=mileMarkers.get(markerCounter);
                markerCounter++;
                firstTimeInLocation=true;
            }
            //Landmark
            else if (milesTravd >= mileMarkers.get(markerCounter) && !names.get(markerCounter).equals("Independence")) {
                currentLocation = names.get(markerCounter);
                markerCounter++;
                firstTimeInLocation=true;
            }
        }
        catch (RuntimeErrorException e){
            throw new RuntimeException("Location Class addMileage() method not measuring distance:markers correctly");
        }
    }

    /**
     * Gets the total miles traveled.
     * @return the total miles traveled.
     */
    public int getMilesTravd() {
        return milesTravd;
    }

    /**
     * Sets the total miles traveled.
     * @param milesTravd - Number of miles to set.
     */
    public void setMilesTravd(int milesTravd) {
        this.milesTravd = milesTravd;
    }

    /**
     * Gets the current location
     * @return the current location
     */
    public String getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Sets the current location
     * @param currentLocation - the current location to set
     */
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * Gets the current marker counter, which tracks what landmark is coming up next.
     * @return the marker counter
     */
    public int getMarkerCounter() {
        return markerCounter;
    }

    /**
     * Sets the current marker counter, which tracks what landmark is coming up next.
     * @param markerCounter - the marker counter to set.
     */
    public void setMarkerCounter(int markerCounter) {
        this.markerCounter = markerCounter;
    }
}