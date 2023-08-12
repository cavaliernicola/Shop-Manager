package org.lookbook.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.input.BOMInputStream;
import org.lookbook.Main;
import org.lookbook.model.Model;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;

public class ModelHelper {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> List<T> convertCsvToModel(T model, String path) {
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

    public static <T extends Model> boolean convertModelToCsv(List<T> beans, String path) {
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
            return false;
        }
        return true;
    }

    public static <T extends Model> T getModelFromId(List<T> model, int id) {
       return model.stream().filter(md -> md.getId() == id).findFirst().get();
    }

    public static <T extends Model> int getModelMaxId(List<T> model) {
        return model.stream().max(Comparator.comparingInt(Model::getId)).get().getId();
    }
}
