package org.lookbook.handler;

import java.util.List;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import org.lookbook.Main;
import org.lookbook.model.Clothing;
import org.lookbook.model.Sale;
import org.lookbook.model.Model;
import org.lookbook.model.User;
import org.lookbook.utils.Constants;
import org.lookbook.utils.Helper;
import java.util.Map;
import java.util.Date;
import java.util.Map.Entry;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

public class Handler {

    public static <T extends Model> void showContent(List<T> content) {
        if (content.isEmpty()) {
            System.out.println("There is no content to show.");
            return;
        }

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow((Object[])content.get(0).getCsvHeader());
        at.addRule();
        
        for (T ch : content) {
            at.addRow((Object[])ch.getAllInformation());
            at.addRule();
        }

        at.getContext().setWidth(90);
        at.setTextAlignment(TextAlignment.CENTER);
        System.out.println(at.render());
    }

    public static void buyClothes(List<Clothing> clothes, List<User> users, List<Sale> sales) {
        System.out.println("You are going to buy new clothes! NOTE: You can always return to the main page by writing \"Q\"");
        System.out.println("Please insert the ID of the user that wants to buy it");
        
        int userId = Helper.validateId(users);
        if (userId < 0) return;

        System.out.println("Please insert the ID of the clothing that they want to buy");
        int clothingId = Helper.validateId(clothes);
        if (clothingId < 0) return;

        Clothing desiredClothes = Helper.getModelFromId(clothes, clothingId);
        if (!desiredClothes.getAvailability()) {
            System.out.println("Sorry, the clothing you are trying to buy is not available.");
            return;
        }

        desiredClothes.setAvailability(false);
        int saleId = Helper.getMaxInt(sales) + 1; 
        sales.add(new Sale(saleId, userId, clothingId));

        User buyer = Helper.getModelFromId(users, userId);
        System.out.printf("Congratulations %s %s bought this clothing: %s %s for %s!%n", buyer.getName(), buyer.getSurname(), desiredClothes.getTypology(), desiredClothes.getBrand(), desiredClothes.getFormattedPrice());
     }

    public static void returnClothing(List<Clothing> clothes, List<Sale> sales) {
        System.out.println("You are going to return a clothing! NOTE: You can always return to the main page by writing \"Q\"");
        System.out.println("Please insert the ID of the sale of the clothing that you want to return");

        int saleId = Helper.validateId(sales);
        if (saleId < 0) return;

        Sale desiredSale = Helper.getModelFromId(sales, saleId);

        int clothingId = desiredSale.getId();
        Clothing returnedClothing = Helper.getModelFromId(clothes, clothingId);

        sales.remove(desiredSale);
        returnedClothing.setAvailability(true);
        System.out.printf("You successfully returned this clothing: %s %s%n", returnedClothing.getTypology(), returnedClothing.getBrand());
    }

    public static void addUser(List<User> users) {
        System.out.println("You are going to add a new user! NOTE: You can always return to the main page by writing \"Q\"");

        int userId = Helper.getMaxInt(users) + 1;

        Map<String, String> texts = new LinkedHashMap<>();
        texts.put("name", "Please insert the name of the user that you want to add.");
        texts.put("surname", "Please insert the surname of the user that you want to add.");
        texts.put("date", "Please insert the date of their birthday in the format: %s.".formatted(Constants.DEFAULT_TIME_PATTERN));
        texts.put("address", "Please insert their address.");
        texts.put("documentId", "Please insert their document id.");

        Map<String, String> results = new Hashtable<>();
        for (Entry<String, String> element : texts.entrySet()){
            System.out.println(element.getValue());
            String result;

            if (!element.getKey().equals("date")) {
                result = Constants.SCANNER.nextLine();
            } else {
                result = Helper.validateDate();
            }

            if (result.equalsIgnoreCase("q")) return;
            results.put(element.getKey(), result);
        }

        DateFormat df = new SimpleDateFormat(Constants.DEFAULT_TIME_PATTERN);
        Date birthday = null;

        // We don't really care about this exception because we handle it already in Helper.validateDate().
        try {
            birthday = df.parse(results.get("date"));
        } catch (Exception ignore) {}

        User newUser = new User(userId, results.get("name"), results.get("surname"), birthday, results.get("address"), results.get("documentId"));
        users.add(newUser);

        showContent(newUser.toList());
        System.out.println("I successfully added the user to my database.");
    }

    public static <T extends Model> void exportFile(List<T> model, String filename) {
        if (model.isEmpty()) {
            System.out.println("There are no elements to export.");
            return;
        }

        String dirPath;
        try {
            dirPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (Exception e) {
            System.out.println("An error happened while exporting your file.");
            return;
        }

        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");

        new File(dirPath + "/exported-data/").mkdir();
        Helper.getCsvFromData(model, dirPath + "\\exported-data\\" + filename + "_" + formatter.format(today.getTime()) + ".csv");
    }
}
