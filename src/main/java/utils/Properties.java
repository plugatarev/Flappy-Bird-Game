package utils;

import java.io.FileInputStream;
import java.io.IOException;

public class Properties {
    static public String getProperty(String key){
        java.util.Properties property = new java.util.Properties();;
        try{
            FileInputStream file = new FileInputStream("src/main/resources/game.properties");
            property.load(file);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return property.getProperty(key);
    }
}
