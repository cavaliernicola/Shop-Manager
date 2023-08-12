package org.lookbook.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.lookbook.model.Model;

public class Validator {

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

    public static String validateDate() {
        String input = Constants.SCANNER.nextLine().trim();
        if (input.equalsIgnoreCase("q")) return input;
    
        try {     
            DateFormat df = new SimpleDateFormat(Constants.DEFAULT_TIME_PATTERN);
            Date givenDate = df.parse(input);
            Date today = new Date();
    
            if (givenDate.after(today)) {
                System.out.println("The date can't be in the future, try again.");
                return validateDate();
            }
    
        } catch (NullPointerException | IllegalArgumentException | ParseException e) {
            System.out.printf("You must insert the date in the format %s, try again.%n", Constants.DEFAULT_TIME_PATTERN);
            return validateDate();
        }
        return input;
    }
    
}
