import java.util.*;

public class UserInteraction {

    private int currentSetIndex = -1; // Tracks which set is currently selected

    // Handles user input and dispatches commands
    public boolean handleInput(String input) {
        try {
            Command command = parseCommand(input);
            return dispatch(command);
        } catch (InvalidCommandException e) {
            System.out.println("Invalid command: " + e.getMessage());
            return true;
        }
    }

    // Dispatches the command to the appropriate handler method
    private boolean dispatch(Command command) throws InvalidCommandException {
        switch (command.getType()) {
            case SHOW: handleShow(command); break;
            case NEW: handleNew(command); break;
            case DELETE: handleDelete(command); break;
            case SELECT: handleSelect(command); break;
            case ADD: handleAdd(command); break;
            case REMOVE: handleRemove(command); break;
            case RANDOMIZE: handleRandomize(); break;
            case SORT: handleSort(); break;
            case REVERSE: handleReverse(); break;
            case SAVE: handleSave(command); break;
            case RESTORE: handleRestore(command); break;
            case HELP: handleHelp(); break;
            case EXIT: case QUIT: return false;
            default: throw new InvalidCommandException("Unrecognized command.");
        }
        return true;
    }

    // Lists all current sets
    private void handleShow(Command command) {
        CollectionSetsOfIntegers.listAll();
    }

    // Creates a new set from input integers
    private void handleNew(Command command) throws InvalidCommandException {
        List<String> args = command.getArgs();
        if (args.isEmpty()) {
            throw new InvalidCommandException("NEW command requires at least one integer.");
        }

        try {
            int[] newSet = new int[args.size()];
            for (int i = 0; i < args.size(); i++) {
                newSet[i] = Integer.parseInt(args.get(i));
            }
            CollectionSetsOfIntegers.createSet(newSet);
            System.out.println("New set created: " + Arrays.toString(newSet));
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("NEW command only accepts integers.");
        }
    }

    // Deletes a set based on label
    private void handleDelete(Command command) throws InvalidCommandException {
        if (command.getArgs().size() != 1) {
            throw new InvalidCommandException("Usage: DELETE <letter>");
        }

        String arg = command.getArgs().get(0).toUpperCase();
        char label = arg.charAt(0);

        if (label < 'A' || label >= 'A' + CollectionSetsOfIntegers.sets.size()) {
            throw new InvalidCommandException("Invalid set label: " + label);
        }

        int index = label - 'A';
        CollectionSetsOfIntegers.deleteSet(index);
        System.out.println("Set " + label + " deleted.");
    }

    // Selects a set based on label
    private void handleSelect(Command command) throws InvalidCommandException {
        if (command.getArgs().size() != 1) {
            throw new InvalidCommandException("Usage: SELECT <letter>");
        }

        String arg = command.getArgs().get(0).toUpperCase();
        char label = arg.charAt(0);

        if (label < 'A' || label >= 'A' + CollectionSetsOfIntegers.sets.size()) {
            throw new InvalidCommandException("Invalid set label: " + label);
        }

        currentSetIndex = label - 'A';
        System.out.println("Set " + label + " has been selected.");
    }

    // Adds values to the selected set
    private void handleAdd(Command command) {
        if (currentSetIndex == -1) {
            System.out.println("There is no currently selected set to add values to.");
            return;
        }
        List<String> args = command.getArgs();
        if (args.isEmpty()) {
            System.out.println("No integers entered to add.");
            return;
        }
        int[] valuesToAdd = new int[args.size()];
        try {
            for (int i = 0; i < args.size(); i++) {
                valuesToAdd[i] = Integer.parseInt(args.get(i));
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter only integers.");
            return;
        }
        int[] currentSet = CollectionSetsOfIntegers.sets.get(currentSetIndex);
        int[] updatedSet = SetOfIntegers.addValue(currentSet, valuesToAdd);
        CollectionSetsOfIntegers.sets.set(currentSetIndex, updatedSet);
        System.out.println("Values added. New set: " + Arrays.toString(updatedSet));
    }

    // Removes values from the selected set
    private void handleRemove(Command command) {
        if (currentSetIndex == -1) {
            System.out.println("There is no currently selected set to remove values from.");
            return;
        }
        List<String> args = command.getArgs();
        if (args.isEmpty()) {
            System.out.println("No integers entered to remove.");
            return;
        }
        int[] valuesToRemove = new int[args.size()];
        try {
            for (int i = 0; i < args.size(); i++) {
                valuesToRemove[i] = Integer.parseInt(args.get(i));
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter only integers.");
            return;
        }
        int[] currentSet = CollectionSetsOfIntegers.sets.get(currentSetIndex);
        int[] updatedSet = SetOfIntegers.removeValue(currentSet, valuesToRemove);
        CollectionSetsOfIntegers.sets.set(currentSetIndex, updatedSet);
        System.out.println("Values removed. New set: " + Arrays.toString(updatedSet));
    }

    // Randomizes the selected set
    private void handleRandomize() {
        if (currentSetIndex == -1) {
            System.out.println("There is no currently selected set to randomize.");
            return;
        }
        int[] currentSet = CollectionSetsOfIntegers.sets.get(currentSetIndex);
        int[] randomizedSet = SetOfIntegers.randomSort(currentSet);
        CollectionSetsOfIntegers.sets.set(currentSetIndex, randomizedSet);
        System.out.println("The set has been randomized, resulting in " + Arrays.toString(randomizedSet));
    }

    // Sorts the selected set in increasing order
    private void handleSort() {
        if (currentSetIndex == -1) {
            System.out.println("There is no currently selected set to sort.");
            return;
        }
        int[] currentSet = CollectionSetsOfIntegers.sets.get(currentSetIndex);
        int swaps = insertionSort(currentSet);
        CollectionSetsOfIntegers.sets.set(currentSetIndex, currentSet);
        System.out.println("It took " + swaps + " swaps to sort the set and resulted in: " + Arrays.toString(currentSet));
    }

    // Reverses the selected set
    private void handleReverse() {
        if (currentSetIndex == -1) {
            System.out.println("There is no currently selected set to reverse.");
            return;
        }
        int[] currentSet = CollectionSetsOfIntegers.sets.get(currentSetIndex);
        int n = currentSet.length;
        for (int i = 0; i < n / 2; i++) {
            int temp = currentSet[i];
            currentSet[i] = currentSet[n - 1 - i];
            currentSet[n - 1 - i] = temp;
        }
        CollectionSetsOfIntegers.sets.set(currentSetIndex, currentSet);
        System.out.println("The set has been reversed: " + Arrays.toString(currentSet));
    }

    // Saves all sets to a file
    private void handleSave(Command command) throws InvalidCommandException {
        if (command.getArgs().size() != 1)
            throw new InvalidCommandException("SAVE requires a filename.");

        String filename = command.getArgs().get(0);
        try {
            CollectionSetsOfIntegers.save(filename);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // Restores sets from a file
    private void handleRestore(Command command) throws InvalidCommandException {
        if (command.getArgs().size() != 1)
            throw new InvalidCommandException("RESTORE requires a filename.");

        String filename = command.getArgs().get(0);
        try {
            CollectionSetsOfIntegers.restore(filename);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Error restoring file: " + e.getMessage());
        }
    }

    // Displays available commands
    private void handleHelp() {
        System.out.println("NEW: Create a new set.");
        System.out.println("SHOW: Shows created sets.");
        System.out.println("SELECT: Selects set to modify.");
        System.out.println("ADD: Adds values to selected set.");
        System.out.println("REMOVE: Removes values from selected set.");
        System.out.println("SORT: Sorts selected set in increasing order.");
        System.out.println("REVERSE: Reverses the selected set.");
        System.out.println("RANDOMIZE: Randomizes the selected set.");
        System.out.println("SAVE: Saves all sets to a file.");
        System.out.println("RESTORE: Restores sets from a file.");
        System.out.println("HELP: Displays this help menu.");
        System.out.println("EXIT: Terminates the program.");
    }

    // Simple insertion sort that counts swaps
    private int insertionSort(int[] array) {
        int swaps = 0;
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
                swaps++;
            }
            array[j + 1] = key;
        }
        return swaps;
    }

    // Parses the input into a command and its arguments
    private Command parseCommand(String input) throws InvalidCommandException {
        if (input == null || input.isBlank()) {
            throw new InvalidCommandException("Command cannot be empty.");
        }

        String[] parts = input.trim().split("\\s+");
        String keyword = parts[0].toUpperCase();
        CommandType type;

        try {
            type = CommandType.valueOf(keyword);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("Unknown command: " + keyword);
        }

        List<String> args = Arrays.asList(parts).subList(1, parts.length);
        return new Command(type, args);
    }

    // Inner class to represent a command
    public static class Command {
        private final CommandType type;
        private final List<String> args;

        public Command(CommandType type, List<String> args) {
            this.type = type;
            this.args = args;
        }

        public CommandType getType() { return type; }
        public List<String> getArgs() { return args; }
    }

    // Enum for all supported command types
    private enum CommandType {
        NEW, SHOW, SELECT, DELETE,
        SORT, REVERSE, RANDOMIZE,
        ADD, REMOVE,
        SAVE, RESTORE,
        HELP, EXIT, QUIT
    }

    // Exception class for handling invalid commands
    public static class InvalidCommandException extends Exception {
        public InvalidCommandException(String message) {
            super(message);
        }
    }
}

