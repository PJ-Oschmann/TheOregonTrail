import javax.swing.*;

public class RandomEvent {
    private final java.util.Random rand = new java.util.Random();
    private int happiness = 0;
    //initialize random event variables: type (good/bad), happiness
    //if neutral --> 50/50 of event happening
    //if happiness < 25 --> 75% chance of bad event, 25% chance of good
    //if happiness >75 --> 75% chance of good event, 25% chance of bad



    //initialize RandomEvent constructor taking in happiness

    //method to determine good or bad event (using happiness, needs 3 scenarios)


    //depending on good or bad, generate new random number to determine scenario

    //call that method in the RandomEvent.generate() which returns a string form of what random event
    //occurs to the main class

    public RandomEvent(int happiness) {
        this.happiness = happiness;

    }



}

