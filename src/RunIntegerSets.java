import java.util.Scanner;
import java.util.*;

public class RunIntegerSets {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInteraction ui = new UserInteraction();

        System.out.println("Welcome to Integer Sets Manager!");
        System.out.println("Enter commands (NEW, SHOW, SELECT, DELETE, SORT, REVERSE, RANDOMIZE, ADD, REMOVE, SAVE, RESTORE, HELP, EXIT)");

        boolean running = true;

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String commandWord = input.split("\\s+")[0].toUpperCase();
            List<String> argsList = new ArrayList<>();

            switch (commandWord) {
                case "NEW":
                    System.out.println("Enter integers separated by spaces:");
                    String integersLine = scanner.nextLine().trim();
                    if (!integersLine.isEmpty()) {
                        argsList = Arrays.asList(integersLine.split("\\s+"));
                    }
                    input = commandWord + " " + String.join(" ", argsList);
                    break;
                case "SELECT":
                case "DELETE":
                    String[] parts = input.split("\\s+");
                    if (parts.length == 1) {
                        System.out.println("Enter set label (letter):");
                        String label = scanner.nextLine().trim();
                        input = commandWord + " " + label;
                    }
                    break;
                case "ADD":
                case "REMOVE":
                    System.out.println("Enter integers to " + commandWord.toLowerCase() + " separated by spaces:");
                    String valuesLine = scanner.nextLine().trim();
                    if (!valuesLine.isEmpty()) {
                        argsList = Arrays.asList(valuesLine.split("\\s+"));
                        input = commandWord + " " + String.join(" ", argsList);
                    }
                    break;
                case "SAVE":
                case "RESTORE":
                    System.out.println("Enter filename:");
                    String filename = scanner.nextLine().trim();
                    input = commandWord + " " + filename;
                    break;
                default:
                    break;
            }

            running = ui.handleInput(input);
        }

        System.out.println("Exiting program. Goodbye!");
        scanner.close();
    }
}


