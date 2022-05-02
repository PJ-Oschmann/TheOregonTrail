import javax.swing.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Location {
    private int pace;
    private int milesTravd=0;
    private String currentLocation="Independence";
    private int markerCounter = 0; //Index of the milage marker we're at
    ArrayList<Integer> mileMarkers = new ArrayList<Integer>(List.of(0,317,550, 591,620, 672 ,1063,1279, 1454, 1700, 1900, 2000));
    ArrayList<String> names = new ArrayList<String>(List.of("Independence", "Fort Kearny", "Courthouse Rock",
            "Scotts Bluff", "Fort Laramie", "Fort Bridger", "Fort Hall", "Three Island Crossing", "Fort Boise", "Oregon City"));
    public Location(OregonTrailGUI game) {
        this.pace = game.getCurrentPace();
        /**
         * The location class is used to track the location of the character/party during
         * their journey from Missouri to Nebraska (Ash Hollow). This object will hold
         * the name of the location the party is at in the game and the location will change
         * depending on how much distance they have traveled.
         */





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
        if (pace==0) {miles = 15;}
        else if (pace == 1) {miles = 20;}
        else {miles = 25;}
        if (milesTravd+miles>=mileMarkers.get(markerCounter) && !names.get(markerCounter).equals(currentLocation)) {

            String[] buttons = {"Stop here","Keep going"};
            int stopOption = JOptionPane.showOptionDialog(null,"You reached " + names.get(markerCounter) + ", would "
            + "you like to stop here?",names.get(markerCounter),JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
                    null,buttons,null);
            if (stopOption==JOptionPane.YES_OPTION) {
                milesTravd=mileMarkers.get(markerCounter);
            }
            else {
                milesTravd+=miles;
            }
            markerCounter++;
            currentLocation=names.get(markerCounter);
        }
        else {
            milesTravd+=miles;
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