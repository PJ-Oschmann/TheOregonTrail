/**
 * ReadText class is a public class containing public static methods that are used throughout the program generally for
 * reading various textfiles from our src/assets/text folder.
 */

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.io.*;

public class ReadText {

    /**
     * Reads lines from a text file with the name of the scene it should be called. Each line from the text file is
     * stored into an array that is manipulated later.
     * @param scene name of the scene/text file associated with the scene,
     * @return textStringArray that contains one line of the text file in consecutive order of how it was read.
     */
    public static ArrayList<String> readTextFile(String scene) {
        ArrayList<String> textStringArray = new ArrayList<>();
        scene = "assets/text/" + scene + ".txt";
        InputStream is = OregonTrailGUI.class.getResourceAsStream(scene);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                textStringArray.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return textStringArray;
    }

    /**
     * Picks a random oxen name from the provided text file
     * @return oxen name randomly selected
     */
    public static String generateOxenName() {
        ArrayList<String> oxenNames = ReadText.readTextFile("oxenNames");
        Random rand = new Random();
        int nameIndex = rand.nextInt(oxenNames.size());
        return oxenNames.get(nameIndex);
    }
}
