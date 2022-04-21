import java.util.*;
import java.util.List;

public class Shop {

    private final Game game;

    //B = Buy; S = Sell
    private final int winterCloBPrice = 10;
    private final int ammunitionBPrice = 4;
    private final int foodBPrice = 3;
    private final int medBPrice = 3;
    private final int splintBPrice = 8;
    private final int toolsBPrice = 10;
    private final int oxenBPrice = 10;

    private final int winterCloSPrice = 8;
    private final int ammunitionSPrice = 3;
    private final int foodSPrice = 2;
    private final int medSPrice = 2;
    private final int splintSPrice = 5;
    private final int toolsSPrice = 8;
    private final int oxenSPrice = 8;

    private final ArrayList<String> winterCloNames = new ArrayList<>(List.of("winter clothing","w"));
    private final ArrayList<String> ammunitionNames = new ArrayList<>(List.of("ammunition","a"));
    private final ArrayList<String> foodNames = new ArrayList<>(List.of("food","f"));
    private final ArrayList<String> medicineNames = new ArrayList<>(List.of("medicine","m","meds"));
    private final ArrayList<String> splintNames = new ArrayList<>(List.of("splint","s"));
    private final ArrayList<String> toolsNames = new ArrayList<>(List.of("tools","t"));
    private final ArrayList<String> oxenNames = new ArrayList<>(List.of("oxen","o","ox","oxes"));

    private final ArrayList<ArrayList<String>> arrayOfNameArrays = new ArrayList<>(List.of(winterCloNames,ammunitionNames,foodNames,medicineNames,splintNames,toolsNames,oxenNames));
    public Shop(Game game) {
        this.game = game;
    }


    //I made a method to quickly check alt-names for items. I was bored. ~PJ
    public String checkItemName(String itemName) {
        for (int i=0;i<arrayOfNameArrays.size();i++) {
            for(int j=0;j<arrayOfNameArrays.get(i).size();j++) {
                if (arrayOfNameArrays.get(i).get(j).equals(itemName.toLowerCase())) {
                    System.out.println("Found " + itemName + " as " + arrayOfNameArrays.get(i).get(0));
                    return arrayOfNameArrays.get(i).get(0);
                }
            }
        }
        return "item_not_found";
    }
    //                                             !!! CHECKING FOR ITEM NAMES !!!
    //Item names should include any interpretation the player sees the item as.
    //This means they should be immune to case, character inputs should be allowed, and common abbreviations don't hurt anybody.
    //Add alt-names to the array list above.
    //If at some point we add items with the same first letter, their respective char assignments should
    //be made clear in the GUI

    public void buyItem(String item, int amount) {
        if (checkItemName(item).equals("winter clothing")) {
            //Add item from player's inventory
            game.setMoney(game.getMoney()-(winterCloBPrice*amount));
        }
        else if (checkItemName(item).equals("ammunition")) {
            //Add item from player's inventory
            game.setMoney(game.getMoney()-(ammunitionBPrice*amount));
        }
        else if (checkItemName(item).equals("food")) {
            //Add item from player's inventory
            game.setMoney(game.getMoney()-(foodBPrice*amount));
        }
        else if (checkItemName(item).equals("medicine")) {
            //Add item from player's inventory
            game.setMoney(game.getMoney()-(medBPrice*amount));
        }
        else if (checkItemName(item).equals("splint")) {
            //Add item from player's inventory
            game.setMoney(game.getMoney()-(splintBPrice*amount));
        }
        else if (checkItemName(item).equals("tools")) {
            //Add item from player's inventory
            game.setMoney(game.getMoney()-(toolsBPrice*amount));
        }
        else if (checkItemName(item).equals("oxen")) {
            //Add item from player's inventory
            game.setMoney(game.getMoney()-(oxenBPrice*amount));
        }
    }

    //TODO: Implement checks for if player has enough of item to sell
    public void sellItem(String item, int amount) {
        if (checkItemName(item).equals("winter clothing")) {
            //Remove item from player's inventory
            game.setMoney(game.getMoney()+(winterCloSPrice*amount));
        }
        else if (checkItemName(item).equals("ammunition")) {
            //Remove item from player's inventory
            game.setMoney(game.getMoney()+(ammunitionSPrice*amount));
        }
        else if (checkItemName(item).equals("food")) {
            //Remove item from player's inventory
            game.setMoney(game.getMoney()+(foodSPrice*amount));
        }
        else if (checkItemName(item).equals("medicine")) {
            //Remove item from player's inventory
            game.setMoney(game.getMoney()+(medSPrice*amount));
        }
        else if (checkItemName(item).equals("splint")) {
            //Remove item from player's inventory
            game.setMoney(game.getMoney()+(splintSPrice*amount));
        }
        else if (checkItemName(item).equals("tools")) {
            //Remove item from player's inventory
            game.setMoney(game.getMoney()+(toolsSPrice*amount));
        }
        else if (checkItemName(item).equals("oxen")) {
            //Remove item from player's inventory
            game.setMoney(game.getMoney()+(oxenSPrice*amount));
        }
    }
}
