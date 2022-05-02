import javax.swing.*;
import java.util.Random;
public class River {
    private OregonTrailGUI game;
    private Random rand = new Random();
    private Wagon wagon;
    public River(OregonTrailGUI game) {
        this.game = game;
        this.wagon = game.getWagon();
    }

    //Booleans: true = crossed; false = didn't. (Still true if someone gets hurt/sick/etc but crossed successfully)

    public boolean takeFerry() {
        boolean crossed = true;
        if (game.getMoney() >= 20) {
            game.setMoney(game.getMoney() - 20);

        }
        else {
            staticMethods.notEnoughMoney();
            crossed = false;
        }
        return crossed;
    }

    public boolean buildRaft() {
        boolean crossed = true;
        if (game.getWagonTools()>=2) {
            game.setWagonTools(game.getWagonTools()-2);

            //10% 1 oxen dies
            if (rand.nextInt(9)==0) {
                game.setOxen(game.getOxen()-1);
                if (game.getOxen()==0) {
                    crossed = false;
                }
            }

            //15% for someone to get sick
            if (rand.nextInt(99)<=15) {
                int characterIndex = rand.nextInt(4);
                if (!game.characterArrayList.get(characterIndex).isSick()) {
                    game.characterArrayList.get(characterIndex).setSick(true);
                }
            }

            //10% wagon damage
            if (rand.nextInt(9)==0) {
                wagon.setState(1);
            }
        }
        return crossed;
    }

    public boolean crossAlone() {
        boolean crossed = true;
        //10% chance 1 oxen dies
        if (rand.nextInt(9)==0 && game.getOxen()>0) {
            game.setOxen(game.getOxen()-1);
            if (game.getOxen()==0) {
                crossed = false;
            }
        }

        //or 20% chance 2 oxen die
        else if (rand.nextInt(4)==0) {
            if (game.getOxen()==1) {
                game.setOxen(game.getOxen()-1);
                if (game.getOxen()==0) {
                    crossed = false;
                }
            }
            else {
                game.setOxen(game.getOxen()-2);
                if (game.getOxen()==0) {
                    crossed = false;
                }
            }
        }

        //5% chance someone drowns
        if (rand.nextInt(19)==0) {
            int characterIndex = rand.nextInt(4);
            if (game.characterArrayList.get(characterIndex).getHealth()>0) {
                game.characterArrayList.get(characterIndex).setHealth(0);
            }
        }

        //15% chance someone gets sick
        if (rand.nextInt(99)<=15) {
            int characterIndex = rand.nextInt(4);
            if (game.characterArrayList.get(characterIndex).isSick() == false) {
                game.characterArrayList.get(characterIndex).setSick(true);
            }
        }

        //10% chance wagon breaks
        if (rand.nextInt(9)==0) {
            wagon.setState(1);
        }
        return crossed;
    }
}
