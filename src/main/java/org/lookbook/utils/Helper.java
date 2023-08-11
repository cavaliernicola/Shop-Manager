package org.lookbook.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Date;

import org.apache.commons.io.input.BOMInputStream;
import org.lookbook.Main;
import org.lookbook.model.Model;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;

public class Helper {
    public static <T extends Model> int validateId(List<T> model) {
        String stringId = Constants.SCANNER.nextLine().trim();
        int intId;
        
        // An id can be only positive, therefore if the user wants to quit, we return a negative one.
        if (stringId.equalsIgnoreCase("q")) return -1;

        try {
            intId = Integer.parseInt(stringId);
        }  catch (NumberFormatException ex) {
            System.out.println("You must insert a number, not a string, try again.");
            return validateId(model);
        } 

        boolean hasId = model.stream().anyMatch(user -> user.getId() == intId);
        if (hasId) return intId;
        else {
            System.out.println("The id you inserted is not in the database, try again.");
            return validateId(model);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> List<T> getDataFromCsv(T model, String path) {
        List<T> beans = null;
        try {               
            BOMInputStream s = BOMInputStream.builder().setInputStream(Main.class.getResourceAsStream("/data/" + path + ".csv")).get();
            InputStreamReader isr = new InputStreamReader(s, StandardCharsets.UTF_8);
            beans = new CsvToBeanBuilder(isr)
                    .withType(model.getClass())
                    .withSeparator(';')
                    // The ID is required therefore we check if it's null or not.
                    .withFilter(lines -> lines[0] != null && !lines[0].isEmpty())
                    .build().parse();
        } catch (NullPointerException | IOException e) {
            System.out.printf("Couldn't load the file: %s.csv, make sure it's in resources/data/ folder.%n", path);
            System.exit(0);
        } catch (RuntimeException e) {
            System.out.printf("The file %s seems not to match the expected file.%n", path);
            System.exit(0);
        }
        return beans;
    }

    public static <T extends Model> void getCsvFromData(List<T> beans, String path) {
        try {
            Writer writer = new FileWriter(path, StandardCharsets.UTF_8);
            String[] headerRecord = beans.get(0).getCsvHeader();
            ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                .withSeparator(';')
                .build();
            csvWriter.writeNext(headerRecord);
            
            for (T model : beans) {
                csvWriter.writeNext(model.getAllInformation());
            }

            csvWriter.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("An error happened while exporting your file, make sure you don't have a file with the same name opened in another process.");
            return;
        }
        System.out.printf("The file has been successfully exported to this path: %s%n", path);
    }

    public static <T extends Model> T getModelFromId(List<T> model, int id) {
       return model.stream().filter(md -> md.getId() == id).findFirst().get();
    }

    public static <T extends Model> int getMaxInt(List<T> model) {
        return model.stream().max(Comparator.comparingInt(Model::getId)).get().getId();
    }

    public static String validateDate() {
        String input = Constants.SCANNER.nextLine().trim();
        if (input.equalsIgnoreCase("q")) return input;

        try {     
            DateFormat df = new SimpleDateFormat(Constants.DEFAULT_TIME_PATTERN);
            Date givenDate = df.parse(input);
            Date today = new Date();

            if (givenDate.after(today)) {
                System.out.println("The date can't be in the future.");
                return validateDate();
            }

        } catch (NullPointerException | IllegalArgumentException | ParseException e) {
            System.out.printf("You must insert the date in the format %s%n", Constants.DEFAULT_TIME_PATTERN);
            return validateDate();
        }
        return input;
    }
}
