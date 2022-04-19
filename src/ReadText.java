import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadText {

    public ReadText() {

    }

    public ArrayList<String> readScene(String scene) {

        ArrayList<String> textStringArray = new ArrayList<String>();
        scene = "assets/" + scene + ".txt";
        File file = new File(scene);
        try {
            Scanner scanner = new Scanner(file);
            int lineInt = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                textStringArray.add(line);
                lineInt++;
            }
        } catch (Exception e) {
            System.out.println("ReadText.java: Oops! Let's peek at the error:" + e);
            textStringArray.add("Aye caramba, something went wrong. Check the console for an exception.");

        }
        return textStringArray;

    }
}
