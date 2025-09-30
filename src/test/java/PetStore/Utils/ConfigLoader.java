package PetStore.Utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.Properties;

public class ConfigLoader {

    public static Properties getConfig() {
        String envType = System.getenv("ENV");
//        System.out.println("ENV TYPE: " + envType);
        Properties properties = new Properties();
        String filename = "env" + File.separator + envType + ".properties";
//        System.out.println(filename);
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            if (is!= null) {
                properties.load(is);
            }
            else {
                System.out.println("Failed to read config file: " + filename);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read config file: " + filename, e);
        }
        return properties;

    }
}
