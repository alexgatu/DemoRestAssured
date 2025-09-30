package PetStore.Utils;

import PetStore.DTO.Category;
import PetStore.DTO.Pet;
import PetStore.DTO.Tag;

import java.io.*;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataHelper {

    public static Pet generateCustomPet(int petId, String petName, String status, int tagId, String tagName, int catId, String catName ){
        Tag tag = new Tag(tagId, tagName);
        Category category = new Category(catId, catName);
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        List<String> photoUrls = new ArrayList<>();
        photoUrls.add("https://myurl.com");
        return new Pet(petId, category, petName, photoUrls, tags, status);
    }

    public static Pet generateRandomPet(){
        return generateCustomPet(910, "bobitza", "sold", 1, "mytag", 1, "dog");
    }

    public static int extractIntegerFromObject(Object o) {
        int ret = 0;
        try {
            ret = Integer.parseInt(o.toString());
        }
        catch (NumberFormatException nfex) {
            System.out.println("Could not convert " + o);
            System.out.println(nfex.getMessage());
        }
        return ret;
    }

    public static List<Object[]>readDataFromCsv(String filename) {
        List<Object[]> records = new ArrayList<>();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            Reader rd = new InputStreamReader(is);
            CSVParser csvParser = new CSVParser(rd, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord record : csvParser) {
                records.add(new Object[] {record.toList().toArray()});
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + filename, e);
        }
        System.out.println(Thread.currentThread().getContextClassLoader() + filename);
        /*
        try (FileReader fileReader = new FileReader(Thread.currentThread().getContextClassLoader() + filename);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
                records.add(new Object[] {record.toList()});
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + filename, e);
        }
        */
        return records;
    }

}
