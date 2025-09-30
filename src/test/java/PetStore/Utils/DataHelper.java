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

    public static List<String[]>readDataFromCsv(String filename) {
        List<String[]> records = new ArrayList<>();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
            if (is == null) {
                throw new IllegalStateException("CSV not found on classpath: " + filename);
            }
            try (
                    Reader rd = new InputStreamReader(is);
                    CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader().setSkipHeaderRecord(true)
                        .setTrim(true)
                        .setIgnoreEmptyLines(true)
                        .setAllowMissingColumnNames(true)
                        .build()
                        .parse(rd)) {

                for (CSVRecord rec : parser) {
                    int n = rec.size();
                    if (n == 0) continue;

                    String[] row = new String[n];
                    for (int i = 0; i < n; i++) {
                        String v = rec.get(i);
                         row[i] = v;
                    }
                    records.add(row);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read CSV: " + filename, e);
        }

        return records;
    }

}
