import javax.swing.*;

public class RandomEvent {
    private final java.util.Random rand = new java.util.Random();
    private int happiness;
    private int type;

    RandomEvent (int happiness) {
        int mood = eventChance(happiness);
    }

   int eventChance (int happiness) {
       if (happiness < 75 && happiness > 25) {
           return 1; //1=neutral
       } else if (happiness >= 75) {
           return 2; //2 is happy
       } else {
           return 0; //0 is sad
       }
   }

    boolean eventType (int mood){
        int temp;
        boolean isGood;
        if (mood == 1){
           temp = rand.nextInt(2); //0 is bad 1 is good
            if (temp == 1)
            {
                isGood = true;
            }
            else {
                isGood = false;
            }
        }
        else if (mood == 2){
            temp = rand.nextInt(4);
            if(temp == 0){
                isGood = false;
            }
            else{
                isGood = true;
            }
        }
        else{
            temp = rand.nextInt(4);
            if(temp == 0){
                isGood = true;
            }
            else
            {
                isGood = false;
            }
        }
        return isGood;
    }

    String eventName(){
        boolean isGood = eventType(eventChance(happiness));
        int temp;
        if(isGood){
            temp = rand.nextInt(3);
            if(temp == 0){
                return "encounterTraveler"; //player encounters another traveler
            }
            else if(temp == 1){
                return "smallStream"; //player encounters
            }
            else{
                return "wagonFound"; //found abandoned wagon
            }
        }
        else{
            temp = rand.nextInt(4);
            if(temp == 0){
                return "injury"; //random party member is injured
            }
            else if (temp == 1){
                return "wagonDamage"; //wagon is damaged during the travels
            }
            else if (temp == 2){
                return "foodSpoiled"; //some food spoils
            }
            else {
                return "illness"; //random party member falls ill
            }
        }
    }
}

