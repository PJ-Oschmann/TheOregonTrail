import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Activities {
    private int food;
    private int ammunition;
    private int medicine;
    private int clothes;
    private int wagonTools;
    private int splints;
    private int oxen;
    private int money;
    private ArrayList<Character> characterArrayList;
    private int happiness;
    private OregonTrailGUI game;
    private int dailyActions;
    private int cloDACounter = 0;
    private int journCounter = 0;
    private Random rand = new Random();

    public Activities(OregonTrailGUI game) {
        this.game = game;
        setGlobalVar();
    }

    private void setGlobalVar() {
        this.food = game.getFood();
        this.ammunition = game.getAmmunition();
        this.medicine = game.getMedicine();
        this.clothes = game.getClothes();
        this.wagonTools = game.getWagonTools();
        this.splints = game.getSplints();
        this.oxen = game.getOxen();
        this.money = game.getMoney();
        this.characterArrayList = game.getCharacterArrayList();
        this.happiness = game.getHappiness();
        this.dailyActions = game.getDailyActions();
    }

    private boolean checkDailyActions() {
        boolean doAction = false;
        if (dailyActions>0) {
            dailyActions-=1;
            doAction = true;
        }
        return doAction;
    }

    public void hunt() {
        int newFood = 0;
        String[] shootButton = {"Shoot!"};
        JOptionPane.showOptionDialog(null,"You point your gun into the woods...",
                "Hunting Activity",JOptionPane.YES_OPTION,JOptionPane.PLAIN_MESSAGE,null,shootButton,null);
        if (checkDailyActions()) {
            if(rand.nextInt(9)==0) {
                newFood = 5;
            }
            else if (rand.nextInt(99)<=15) {
                newFood = 7;
            }
            else if (rand.nextInt(4)==0) {
                newFood = 9;
            }
            else if (rand.nextInt(99)<=30) {
                newFood = 11;
            }
            else if (rand.nextInt(99)<=15) {
                newFood = 13;
            }
            else if (rand.nextInt(9)==0) {
                newFood = 15;
            }
        }
        JOptionPane.showMessageDialog(null,"You found " + newFood + " food!","Hunting activity",
                JOptionPane.PLAIN_MESSAGE);
        food = newFood;
    }

    public void forage() {
        int newFood = 0;
        String[] searchButton = {"Look Around!"};
        JOptionPane.showOptionDialog(null,"You enter the woods, being careful to watch your step...",
                "Foraging Activity",JOptionPane.YES_OPTION,JOptionPane.PLAIN_MESSAGE,null,searchButton,null);
        if (checkDailyActions()) {
            if(rand.nextInt(9)==0) {
                newFood = 0;
            }
            else if (rand.nextInt(99)<=15) {
                newFood = 1;
            }
            else if (rand.nextInt(4)==0) {
                newFood = 2;
            }
            else if (rand.nextInt(3)<=0) {
                newFood = 3;
            }
            else if (rand.nextInt(4)==0) {
                newFood = 4;
            }
            else if (rand.nextInt(9)==0) {
                newFood = 5;
            }
        }
        JOptionPane.showMessageDialog(null,"You found " + newFood + " food!",
                "Foraging activity",JOptionPane.PLAIN_MESSAGE);
        food = newFood;
    }

    public void makeClothes() {
        if (checkDailyActions()) {
            if (cloDACounter <= 1) {
                cloDACounter++;
                JOptionPane.showMessageDialog(null,"You have begun making a set of clothes. " +
                        "Tired, you decide to finish making the clothes later.","Clothes-making Activity",JOptionPane.PLAIN_MESSAGE);
            }
            else {
                cloDACounter=0;
                JOptionPane.showMessageDialog(null,"You made a fresh set of clothes. " +
                        "They have been added ot your inventory.","Clothes-making Activity",JOptionPane.PLAIN_MESSAGE);
                clothes+=1;
            }
        }
    }

    public void writeJournal() {
        if (checkDailyActions() && journCounter ==0) {
            journCounter++;
            JOptionPane.showMessageDialog(null,"You take out your journal and pen and begin to write",
                    "Writing Activity",JOptionPane.PLAIN_MESSAGE);
            happiness = game.calculateHappiness(5);
        }
    }

    public void sleep() {
        if (checkDailyActions()) {
            for (Character character : characterArrayList) {
               game.calculateHealth(character,5);
            }
        }
    }



}
