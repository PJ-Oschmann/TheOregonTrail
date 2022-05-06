/**
 * ReadText class is a public class containing public static methods that are used throughout the program generally for
 * reading various textfiles from our src/assets/text folder.
 */

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class ReadText {

    /**
     * Reads lines from a text file with the name of the scene it should be called. Each line from the text file is
     * stored into an array that is manipulated later.
     * @param scene name of the scene/text file associated with the scene,
     * @return textStringArray that contains one line of the text file in consecutive order of how it was read.
     */
    public static ArrayList<String> readScene(String scene) {
        ArrayList<String> textStringArray = new ArrayList<>();
        scene = "src/assets/text/" + scene + ".txt";
        File file = new File(scene);
        try {
            Scanner scanner = new Scanner(file);
            int lineInt = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                textStringArray.add(line);
                lineInt++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return textStringArray;
    }

    /**
     * Reads lines from a text file with the name of the file and stores each line as an individual element in
     * a string arraylist.
     * @param fileName string representation of the file path where the text file is located
     * @return string arraylist that contains each line in the text file as a separate element
     */
    public static ArrayList<String> readIntoArrayList(String fileName) {
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            InputStream fis = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                arrayList.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * Picks a random oxen name from the provided text file
     * @return oxen name randomly selected
     */
    public static String generateOxenName() {
        ArrayList<String> oxenNames = ReadText.readIntoArrayList("src/assets/text/oxenNames.txt");
        Random rand = new Random();
        int nameIndex = rand.nextInt(oxenNames.size());
        return oxenNames.get(nameIndex);
    }
}
