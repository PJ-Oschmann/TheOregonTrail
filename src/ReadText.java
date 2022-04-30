import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class ReadText {

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
        } catch (Exception e) {
            System.out.println("ReadText.java: Oops! Let's peek at the error:" + e);
            textStringArray.add("Aye caramba, something went wrong. Check the console for an exception.");

        }
        return textStringArray;
    }

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
}
