package org.lookbook.model;

import com.opencsv.bean.CsvBindByName;

public class Sale implements Model {
    @CsvBindByName(column = "ID", required = true)
    private int saleId;

    @CsvBindByName(column = "ID Utente")
    private int userId;

    @CsvBindByName(column = "ID Capo")
    private int clothingId;

    public Sale(int saleId, int userId, int clothingId) {
        this.saleId = saleId;
        this.userId = userId;
        this.clothingId = clothingId;
    }

    public Sale() {}

    public int getId() {
        return this.saleId;
    }
    
    @Override
    public String toString() {
      return "%s %s %s".formatted(saleId, userId, clothingId);
    }

    public String[] getAllInformation() {
        return new String[] {Integer.toString(saleId), Integer.toString(userId), Integer.toString(clothingId)};
    }
    
    public String[] getCsvHeader() {
        return new String[]{"ID", "ID Utente", "ID Capo"};
    } 
}
