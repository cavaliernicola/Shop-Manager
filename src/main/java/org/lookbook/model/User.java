package org.lookbook.model;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.lookbook.utils.Constants;
import java.util.List;

public class User implements Model {
    @CsvBindByName(column = "ID", required = true)
    private int id;

    @CsvBindByName(column = "Nome")
    private String name;

    @CsvBindByName(column = "Cognome")
    private String surname;

    @CsvBindByName(column = "Data di nascita")
    @CsvDate(Constants.DEFAULT_TIME_PATTERN)
    private Date birthday;

    @CsvBindByName(column = "Indirizzo")
    private String address;

    @CsvBindByName(column = "Documento ID")
    private String documentId;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }
    
    public String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DEFAULT_TIME_PATTERN);
        return formatter.format(this.birthday.getTime());
    }

    public User () {}

    public User(int id, String name, String surname, Date birthday, String address, String documentId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.address = address;
        this.documentId = documentId;
    }
    
    @Override
    public String toString() {
      return "%s %s %s %s %s %s".formatted(id, name, surname, birthday, address, documentId);
    }

    public String[] getAllInformation() {
        return new String[] {Integer.toString(this.id), this.name, this.surname, this.getFormattedDate(), this.address, this.documentId};
    }

    public String[] getCsvHeader() {
        return new String[]{"ID", "Nome", "Cognome", "Data di nascita", "Indirizzo", "Documento ID"};
    }

    public List<User> toList() {
        List<User> uList= new ArrayList<>();
        uList.add(this);
        return uList;
    }
}
