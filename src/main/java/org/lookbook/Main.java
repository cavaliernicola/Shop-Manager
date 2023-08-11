package org.lookbook;

import org.lookbook.manager.Manager;
import java.io.Console;

public class Main {
    public static void main(String[] args) {
        Console console = System.console();
        if (console != null) {
            Manager controller = new Manager();
            controller.run();
        } else {
            System.out.println("Sorry, this application can't work without a console.");
        }
    }
}