/**
 * The location class is used to track the location of the character/party during
 * their journey from Missouri to Nebraska (Ash Hollow). This object will hold
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
    private Date date = new Date();
    private String riverChoice;
    private int markerCounter = 1; //Index of the milage marker we're at
    ArrayList<Integer> mileMarkers = new ArrayList<>(List.of(0,17, 50, 80, 108, 221, 273, 550, 591, 620, 672,
            1063, 1279, 1454, 1700, 1900, 2000, 2170));
    ArrayList<String> names = new ArrayList<>(List.of("Independence","Blue River", "Wakarusa River",
            "Kansas River", "Vermilion", "Little Blue River", "Big Blue River","Fort Kearny", "Courthouse Rock", "Chimney Rock",
            "Scotts Bluff", "Fort Laramie", "Fort Bridger", "Fort Hall", "Three Island Crossing", "Fort Boise",
            "Blue Mountains", "Oregon City"));

    /*
    Blue //kansas 17
    Wakarusa //kansas
    Kansas //kansas 80
    Big Blue //nebraska 273
    Little Blue //Nebraska 221
    Vermilion //kansas 108
    Platte River //Nebraska

     */
    public Location(OregonTrailGUI game) {
        this.game = game;
        this.pace = game.getCurrentPace();
        this.river = new RiverGUI(this, game);
        this.fort = new FortGUI(game);
        /*Fort Bridger //wyoming --
        Fort Kearney //nebraska --
        Fort Laramie //wyoming --
        Fort Hall //idaho --
        Fort Boise //idaho --
        Fort Vancouver //wyoming --

        Blue //kansas
        Wakarusa //kansas
        Kansas //kansas
        Big Blue //nebraska
        Little Blue //Nebraska
        Vermilion // ??
        Platte River //Nebraska*/
    }
    //TODO: no more cancel button


    public String getRiverChoice() {
        return riverChoice;
    }

    public void setRiverChoice(String riverChoice) {
        this.riverChoice = riverChoice;
    }

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

    public String getCurrentState() {
        int counter = markerCounter-1;
        if(counter==0){
            return "Missouri";
        }
        else if (counter>=1&&counter<4){
            return "Kansas";
        }
        else if (counter>=4&&counter<10) {
            return "Nebraska";
        }
        else if (counter>=10&&counter<12){
            return "Wyoming";
        }
        else if (counter>=12&&counter<15) {
            return "Idaho";
        }
        else {
            return "Oregon";
        }
    }
    /*
    "Independence", "Blue River", "Wakarusa River",
            "Kansas River", "Vermilion", "Little Blue River", "Big Blue River","Fort Kearny", "Courthouse Rock", "Chimney Rock",
            "Scotts Bluff", "Fort Laramie", "Fort Bridger", "Fort Hall", "Three Island Crossing", "Fort Boise",
            "Blue Mountains", "Oregon City"
     */
    boolean firstTimeInLocation=false;
    public void doStoryLine() {
        int index = 0;
        if (markerCounter-1>=0) {index=markerCounter-1;}
        else {index=markerCounter;}
        System.out.println("Location: " + names.get(index));
        if (firstTimeInLocation){
            switch (names.get(index)) {
                case "Kansas River" -> scene.loadScene("kansasRiver",date.toString());
                case "Vermilion River" -> scene.loadScene("vermilionRiver",date.toString());
                case "Little Blue River" -> scene.loadScene("littleBlueRiver",date.toString());
                case "Big Blue River" -> scene.loadScene("bigBlueRiver",date.toString());
                case "Fort Kearny" -> scene.loadScene("fortKearny",date.toString());
                case "Courthouse Rock" -> scene.loadScene("courthouseRock",date.toString());
                case "Chimney Rock" -> scene.loadScene("chimneyRock",date.toString());
                case "Scotts Bluff" -> scene.loadScene("scottsBluff",date.toString());
                case "Fort Laramie" -> scene.loadScene("fortLaramie",date.toString());
                case "Fort Bridger" -> scene.loadScene("fortBridger",date.toString());
                case "Fort Hall" -> scene.loadScene("fortHall",date.toString());
                case "Three Island Crossing" -> scene.loadScene("threeIslandCrossing",date.toString());
                case "Fort Boise" -> scene.loadScene("fortBoise",date.toString());
                case "Blue Mountains" -> scene.loadScene("blueMountains",date.toString());
                case "Oregon City" -> scene.loadScene("oregonCity",date.toString());
            }
            firstTimeInLocation=false;
        }

    }
    public void addMileage() {
        int miles;
        pace = game.getCurrentPace();
        if (pace == 0) {miles = 15;}
        else if (pace == 1) {miles = 20;}
        else if (pace == 2) {miles = 25;}
        else  {miles = 100;}
        milesTravd += miles;
        try {
            System.out.println("milesTravd="+milesTravd+">=milesMarkers[markerCounter]="+mileMarkers.get(markerCounter)+" && contains="+names.get(markerCounter).contains("River"));
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
                JOptionPane.showMessageDialog(null, "You reached " + names.get(markerCounter) +
                        "!", "CHECKPOINT", JOptionPane.INFORMATION_MESSAGE);
                currentLocation = names.get(markerCounter);
                markerCounter++;
                firstTimeInLocation=true;
            }
        }
        catch (RuntimeErrorException e){
            throw new RuntimeException("Location Class addMileage() method not measuring distance:markers correctly");
        }
    }

    public int getMilesTravd() {
        return milesTravd;
    }

    public void setMilesTravd(int milesTravd) {
        this.milesTravd = milesTravd;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getMarkerCounter() {
        return markerCounter;
    }

    public void setMarkerCounter(int markerCounter) {
        this.markerCounter = markerCounter;
    }
}