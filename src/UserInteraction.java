import java.util.*;

public class UserInteraction {

    private int currentSetIndex = -1;

    public boolean handleInput(String input) {
        try {
            Command command = parseCommand(input);
            return dispatch(command);
        } catch (InvalidCommandException e) {
            System.out.println("Invalid command: " + e.getMessage());
            return true;
        }
    }

    private boolean dispatch(Command command) throws InvalidCommandException {
        switch (command.getType()) {
            case SHOW: handleShow(command); break;
            case NEW: handleNew(command); break;
            case DELETE: handleDelete(command); break;
            case SELECT: handleSelect(command); break;
            case EXIT: return false;
            case ADD: handleAdd(); break;
            case RANDOMIZE: handleRandomize(); break;
            case SORT: handleSort(); break;
            case REVERSE: handleReverse(); break;
            case HELP: handleHelp(); break;
            case SAVE: handleSave(command); break;
            case RESTORE: handleRestore(command); break;
            default: throw new InvalidCommandException("Unrecognized command.");
        }
        return true;
    }

    private void handleShow(Command command) {
        CollectionSetsOfIntegers.listAll();
    }

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

    private void handleAdd() {
        if (currentSetIndex == -1) {
            System.out.println("There is no currently selected set to add values to.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter integers to add separated by spaces:");
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            System.out.println("No integers entered to add.");
            return;
        }
        String[] tokens = line.split("\\s+");
        int[] valuesToAdd = new int[tokens.length];
        try {
            for (int i = 0; i < tokens.length; i++) {
                valuesToAdd[i] = Integer.parseInt(tokens[i]);
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

    private void handleRandomize() {
//If statement to verify that there is a sort to set.
    	if (currentSetIndex == -1) {
    		System.out.println("There is no currently selected set to randomize.");
    		return;
    	}
//Retrieve set from CollectionSetsOfIntegers.
    	int[] currentSet = CollectionSetsOfIntegers.sets.get(currentSetIndex);
//Run randomSort method from SetOfIntegers
    	int[] randomizedSet = SetOfIntegers.randomSort(currentSet);
    	CollectionSetsOfIntegers.sets.set(currentSetIndex, randomizedSet);
//Output for Random Sort.
    	System.out.println("The set has been randomized, resulting in " + Arrays.toString(randomizedSet));
}
    
    private void handleSort() {
//If statement to verify that there is a sort to set.
        if (currentSetIndex == -1) {
            System.out.println("There is no currently selected set to sort.");
            return;
        }
//Retrieve set from CollectionSetsOfIntegers
        int[] currentSet = CollectionSetsOfIntegers.sets.get(currentSetIndex);        
// Swaps calls the amount of swaps the insertion sort took and displays in the output below.
        int swaps = insertionSort(currentSet);
        CollectionSetsOfIntegers.sets.set(currentSetIndex, currentSet);
//Output for Sort.
        System.out.println("It took " + swaps + " swaps to sort the set and resulted in [increasing] ");
     
    }
//Insertion sort algorithm to sort the sets in increasing order. Every time there is a pass, the counter swaps increases to get a final count.
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

    private void handleReverse() {
//If statement to verify that there is a sort to set.
        if (currentSetIndex == -1) {
            System.out.println("There is no currently selected set to reverse.");
            return;
        }
//Retrieve set from CollectionSetsOfIntegers, int n gets the length of the set, for loop reverses the order of values in the set.
//For loop is complete when every value has been placed in reverse order.
        int[] currentSet = CollectionSetsOfIntegers.sets.get(currentSetIndex);
        int n = currentSet.length;
        for (int i = 0; i < n / 2; i++) {
            int temp = currentSet[i];
            currentSet[i] = currentSet[n - 1 - i];
            currentSet[n - 1 - i] = temp;
        }
        CollectionSetsOfIntegers.sets.set(currentSetIndex, currentSet);
//Output for Reverse Sort.
        System.out.println("The set has been reversed: " + Arrays.toString(currentSet));
    }
//Display Help Menu.
    private void handleHelp() {
        	System.out.println("NEW: Create a new set."); 
        	System.out.println("SHOW: Shows created sets.");
        	System.out.println("SELECT: Selects set to modify.");
        	System.out.println("ADD: After selecting a set, adds values to the set.");
        	System.out.println("DELETE: After selecting a set, removes values from the set.");
        	System.out.println("SORT: After selecting a set, sorts the given set in increasing order.");
        	System.out.println("REVERSE: After selecting a set, reverses the order of the set.");
        	System.out.println("RANDOMIZE: After selecting a set, randomizes the order of the set.");
        	System.out.println("SAVE: After selecting a set, saves the values of a set into a file.");
        	System.out.println("RESTORE: Restores previous set versions.");
        	System.out.println("EXIT: Terminates the program.");
    }
    
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

    // --- Supporting classes ---

    public static class Command {
        private CommandType type;
        private List<String> args;

        public Command(CommandType type, List<String> args) {
            this.type = type;
            this.args = args;
        }

        public CommandType getType() {
            return type;
        }

        public List<String> getArgs() {
            return args;
        }
    }

    public enum CommandType {
        SHOW, NEW, ADD, SORT, REVERSE, RANDOMIZE, DELETE, HELP, EXIT, SELECT, SAVE, RESTORE, QUIT
    }

    public static class InvalidCommandException extends Exception {
        public InvalidCommandException(String message) {
            super(message);
        }
    }

    // Simple command parser (you can improve this later)
    private Command parseCommand(String input) throws InvalidCommandException {
        String[] parts = input.trim().split("\\s+");
        if (parts.length == 0) {
            throw new InvalidCommandException("Empty input.");
        }

        String keyword = parts[0].toUpperCase();
        CommandType type;

        try {
            type = CommandType.valueOf(keyword);
        } catch (IllegalArgumentException e) {
            throw new InvalidCommandException("Unknown command: " + keyword);
        }

        List<String> args = new ArrayList<>();
        for (int i = 1; i < parts.length; i++) {
            args.add(parts[i]);
        }

        return new Command(type, args);
    }
}
