import java.util.Scanner;

import java.util.*;

public class RunIntegerSets {

    public static void main(String[] args) {
       // trial driver to test method functionality
    	Scanner scanner = new Scanner(System.in);
        UserInteraction ui = new UserInteraction();

        System.out.println("Welcome to Integer Sets Manager!");
        System.out.println("Enter commands (NEW, SHOW, SELECT, DELETE, EXIT).");

        boolean running = true;

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String commandWord = input.split("\\s+")[0].toUpperCase();

            List<String> argsList = new ArrayList<>();

            if ("NEW".equals(commandWord)) {
                System.out.println("Enter integers separated by spaces:");
                String integersLine = scanner.nextLine().trim();
                if (!integersLine.isEmpty()) {
                    argsList = Arrays.asList(integersLine.split("\\s+"));
                }
                input = commandWord + " " + String.join(" ", argsList);
            } else if ("SELECT".equals(commandWord) || "DELETE".equals(commandWord)) {
                String[] parts = input.split("\\s+");
                if (parts.length == 1) {
                    System.out.println("Enter set label (letter):");
                    String label = scanner.nextLine().trim();
                    input = commandWord + " " + label;
                }
            }

            running = ui.handleInput(input);
        }

        System.out.println("Exiting program. Goodbye!");
        scanner.close();
    }
}


