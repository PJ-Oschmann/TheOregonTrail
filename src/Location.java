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
    private int milesTravd = 0;
    private String currentLocation = "Independence";
    private int markerCounter = 1; //Index of the milage marker we're at
    ArrayList<Integer> mileMarkers = new ArrayList<>(List.of(0,17, 50, 80, 108, 221, 273, 550, 591,620, 672 ,1063,1279, 1454, 1700, 1900, 2000));
    ArrayList<String> names = new ArrayList<>(List.of("Independence", "Blue River", "Wakarusa River", "Kansas River", "Vermilion", "Little Blue River", "Big Blue River","Fort Kearny", "Courthouse Rock", "Chimney Rock",
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

    public void addMileage() {
        int miles;
        pace = game.getCurrentPace();
        if (pace == 0) {miles = 15;}
        else if (pace == 1) {miles = 20;}
        else {miles = 25;}
        milesTravd += miles;
        try {
            if (milesTravd >= mileMarkers.get(markerCounter) && !names.get(markerCounter).equals(currentLocation)) {
                JOptionPane.showMessageDialog(null, "You reached " + names.get(markerCounter) +
                        "!", "CHECKPOINT", JOptionPane.INFORMATION_MESSAGE);
                currentLocation = names.get(markerCounter);
                milesTravd=mileMarkers.get(markerCounter);
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
}