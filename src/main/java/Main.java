import gui.MainFrame;
import util.PropertiesStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HashMap<String,String> properties = new HashMap<>();
        try {
            Scanner in = new Scanner(new File("properties.txt"));
            String line;
            while (in.hasNextLine()){
                line = in.nextLine();
                properties.put(line.split("=")[0], line.split("=")[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PropertiesStorage storage = PropertiesStorage.getInstance();
        storage.setProperties(properties);

        MainFrame mainFrame = new MainFrame();
    }

}
