package org.lookbook.model;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import org.lookbook.utils.Constants;
import org.lookbook.utils.ConvertItalianToBoolean;
import com.opencsv.bean.CsvCustomBindByName;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Clothing implements Model {
    @CsvBindByName(column = "ID", required = true)
    private int id;

    @CsvBindByName(column = "Data Inserimento")
    @CsvDate(Constants.DEFAULT_TIME_PATTERN)
    private Date addedDate;

    @CsvBindByName(column = "Tipologia")
    private String typology;

    @CsvBindByName(column = "Marca")
    private String brand;

    @CsvBindByName(column = "Taglia")
    private String size;

    // We capture only the first part of the string, with the number
    @CsvBindByName(column = "Prezzo", capture="([^ ]+) .*")
    private int price;

    @CsvCustomBindByName(column = "Disponibile", converter = ConvertItalianToBoolean.class)
    private boolean available;

    public int getId() {
        return this.id;
    }

    public String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DEFAULT_TIME_PATTERN);
        return formatter.format(this.addedDate.getTime());
    }

    public String getTypology() {
        return this.typology;
    }

    public String getBrand() {
        return this.brand;
    }
    
    public String getSize() {
        return this.size;
    }

    public int getPrice() {
        return this.price;
    }

    public String getFormattedPrice() {
        return this.price + " " + "EUR";
    }

    public boolean getAvailability() {
        return this.available;
    }

    public String getFormattedAvailability() {
        return this.available ? "SI" : "NO";
    }

    public void setAvailability(boolean value) {
        this.available = value;
    }

    public String[] getAllInformation() {
        return new String[] {Integer.toString(this.id), this.getFormattedDate(), this.typology, this.brand, this.size, this.getFormattedPrice(), this.getFormattedAvailability()};
    }

    public String[] getCsvHeader() {
        return new String[]{"ID", "Data Inserimento", "Tipologia", "Marca", "Taglia", "Prezzo", "Disponibile"};
    }
}
