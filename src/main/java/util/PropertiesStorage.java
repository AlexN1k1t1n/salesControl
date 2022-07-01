package util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class PropertiesStorage {

    HashMap<String,String> properties;
    private static PropertiesStorage storage = new PropertiesStorage();

    private PropertiesStorage(){}

    private PropertiesStorage(HashMap<String,String> properties){
        this.properties = properties;
    }

    public HashMap<String,String> getProperties(){
        return properties;
    }

    public boolean updateProperty(String key, String value){
        if (!properties.containsKey(key)){
            return false;
        }
        properties.replace(key,value);
        try {
            PrintWriter pw = new PrintWriter("properties.txt");
            String data = "";
            for (Map.Entry<String,String> set : properties.entrySet()){
                data += set.getKey() + "=" + set.getValue() + "\n";
            }
            pw.print(data);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public static PropertiesStorage getInstance(){
        return storage;
    }

}
