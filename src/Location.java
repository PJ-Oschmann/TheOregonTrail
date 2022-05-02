import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Location {

    public Location() {
/**
 * The location class is used to track the location of the character/party during
 * their journey from Missouri to Nebraska (Ash Hollow). This object will hold
 * the name of the location the party is at in the game and the location will change
 * depending on how much distance they have traveled.
 */

        ArrayList<Integer> mileMarkers = new ArrayList<Integer>(List.of(0,317,550, 591,620, 672 ,1063,1279, 1454, 1700, 1900, 2000));
        ArrayList<String> names = new ArrayList<String>(List.of("Independence", "Fort Kearny", "Courthouse Rock",
                "Scotts Bluff", "Fort Laramie", "Fort Bridger", "Fort Hall", "Three Island Crossing", "Fort Boise", "Oregon City"));

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
}