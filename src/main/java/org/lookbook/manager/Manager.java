package org.lookbook.manager;

import org.lookbook.handler.Handler;
import org.lookbook.model.Clothing;
import org.lookbook.model.Sale;
import org.lookbook.model.User;
import org.lookbook.utils.Constants;
import org.lookbook.utils.Helper;

import java.util.List;

public class Manager {
    private final List<Clothing> clothes = Helper.getDataFromCsv(new Clothing(), Constants.CLOTHING_PATH);
    private final List<User> users = Helper.getDataFromCsv(new User(), Constants.USER_PATH);
    private final List<Sale> sales = Helper.getDataFromCsv(new Sale(), Constants.SALE_PATH);

    public void run() {
        String value;
        do {
            printInformation();
            value = Constants.SCANNER.nextLine().trim();

            switch (value) {
                case Constants.SHOW_CLOTHES -> Handler.showContent(clothes);
                case Constants.BUY_CLOTHES -> Handler.buyClothes(clothes, users, sales);
                case Constants.RETURN_CLOTHING -> Handler.returnClothing(clothes, sales);
                case Constants.ADD_USER -> Handler.addUser(users);
                case Constants.EXPORT_CLOTHING_FILE -> {
                    List<Clothing> availableClothes = clothes.stream().filter(Clothing::getAvailability).toList();
                    Handler.exportFile(availableClothes, Constants.CLOTHING_PATH);
                }
            }
        } while (!value.equals(Constants.EXIT_PROGRAM));
        Constants.SCANNER.close();
    }
    
    public void printInformation() {
        System.out.println("\nAvailable command list:");
        String information = "%s: View all second-hand clothes in the database\n".formatted(Constants.SHOW_CLOTHES);
        information += "%s: Buy available clothes\n".formatted(Constants.BUY_CLOTHES);
        information += "%s: Return back a clothing\n".formatted(Constants.RETURN_CLOTHING);
        information += "%s: Add a new user\n".formatted(Constants.ADD_USER);
        information += "%s: Export a file with all the available clothes\n".formatted(Constants.EXPORT_CLOTHING_FILE);
        information += "%s: Exit from the application\n".formatted(Constants.EXIT_PROGRAM);
        System.out.println(information);
    }
}
