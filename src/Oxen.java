import java.util.Random;
import java.util.random.*;
public class Oxen {
    private boolean isInjured = false;
    private int injuryChance = 20; //0-100. Chance this ox will get injured. Changes to 5% if there are more oxen
    private final Random rand = new Random();
    public Oxen() {

    }


    public boolean isInjured() {
        return isInjured;
    }

    public void setInjured(boolean injured) {
        isInjured = injured;
    }

    public boolean getInjured() {return isInjured;}

    public int getInjuryChance() {
        return injuryChance;
    }

    public void setInjuryChance(int injuryChance) {
        this.injuryChance = injuryChance;
    }

    public void injureTheOx() {
        if ((injuryChance >= rand.nextInt(100)+1) && !isInjured) {
            System.out.println("Oxen.java: The ox is injured now");
            isInjured = true;
        }
    }

}
