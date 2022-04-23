public class CheckIfLost {

    //                                       !!!!   THIS IS JUST AN IDEA   !!!!
    //
    //We can just use this class to check all conditions for losing. Then, we just need to call the isLost method to see
    //if the game is lost.

    //Perhaps it can just be moved to the GUI. Maybe it's more appropriate on its own. Idk.

    public Player mainPlayer;

    //We should include any objects that need something checked.
    //The main player is included as an example.
    public CheckIfLost(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    //We can put all losing conditions here, and then just call this method to check if we need to end the game
    public boolean isLost() {
        if (mainPlayer.getHealth() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
