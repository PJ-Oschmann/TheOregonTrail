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
    private int milesTravd = 0;
    private String currentLocation = "Independence";
    private String riverChoice;
    private int markerCounter = 1; //Index of the milage marker we're at
    ArrayList<Integer> mileMarkers = new ArrayList<>(List.of(0,17, 50, 80, 108, 221, 273, 550, 591, 620, 672,
            1063, 1279, 1454, 1700, 1900, 2000, 2170));
    ArrayList<String> names = new ArrayList<>(List.of("Independence", "Blue River", "Wakarusa River",
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
        river.pack();
        river.setVisible(true);
        river.crossRiver();
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
            if (milesTravd >= mileMarkers.get(markerCounter)  && names.get(markerCounter).contains("River")) {
                crossRiver();
                currentLocation = names.get(markerCounter);
                milesTravd=mileMarkers.get(markerCounter);
                markerCounter++;
            }

            //Landmark
            else if (milesTravd >= mileMarkers.get(markerCounter) && !names.get(markerCounter).equals("Independence")) {
                JOptionPane.showMessageDialog(null, "You reached " + names.get(markerCounter) +
                        "!", "CHECKPOINT", JOptionPane.INFORMATION_MESSAGE);
                currentLocation = names.get(markerCounter);
                markerCounter++;
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