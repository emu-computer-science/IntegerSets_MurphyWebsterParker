import java.util.*;

public class UserInteraction {

    private int currentSetIndex = -1; // Tracks the currently selected set

    public boolean handleInput(String input) {
        try {
            Command command = parseCommand(input);
            return dispatch(command);
        } catch (InvalidCommandException e) {
            System.out.println("Invalid command: " + e.getMessage());
            return true;
        }
    }

    // Dispatches the command to the selected method
    private boolean dispatch(Command command) throws InvalidCommandException {
        switch (command.getType()) {
            case NEW: handleNew(command); break;
            case SHOW: handleShow(); break;
            case SELECT: handleSelect(command); break;
            case DELETE: handleDelete(command); break;
            case SORT: handleSort(); break;
            case REVERSE: handleReverse(); break;
            case RANDOMIZE: handleRandomize(); break;
            case ADD: handleAdd(command); break;
            case REMOVE: handleRemove(command); break;
            case SAVE: handleSave(command); break;
            case RESTORE: handleRestore(command); break;
            case EXIT: case QUIT: return false;
            default: throw new InvalidCommandException("Unknown command type.");
            case EXIT: return false;
            case ADD: handleAdd(); break;
            case RANDOMIZE: handleRandomize(); break;
            case SORT: handleSort(); break;
            case REVERSE: handleReverse(); break;
            case HELP: handleHelp(); break;
            case SAVE: handleSave(); break;
            case RESTORE: handleRestore(); break;
            default: throw new InvalidCommandException("Unrecognized command.");
        }
        return true;
    }

    //  Command Handlers 

    private void handleNew(Command command) throws InvalidCommandException {
        List<String> args = command.getArgs();
        if (args.isEmpty()) {
            throw new InvalidCommandException("NEW requires at least one integer.");
        }

        try {
            int[] newSet = new int[args.size()];
            for (int i = 0; i < args.size(); i++) {
                newSet[i] = Integer.parseInt(args.get(i));
            }
            CollectionSetsOfIntegers.createSet(newSet);
            System.out.println("New set created: " + Arrays.toString(newSet));
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Only integers are allowed.");
        }
    }

    private void handleShow() {
        CollectionSetsOfIntegers.listAll();
    }

    private void handleSelect(Command command) throws InvalidCommandException {
        if (command.getArgs().size() != 1)
            throw new InvalidCommandException("SELECT requires a single letter (A, B, C...)");

        char label = command.getArgs().get(0).toUpperCase().charAt(0);
        int index = label - 'A';

        if (index < 0 || index >= CollectionSetsOfIntegers.sets.size())
            throw new InvalidCommandException("Invalid set letter.");

        currentSetIndex = index;
        System.out.println("Selected set " + label);
    }

    private void handleDelete(Command command) throws InvalidCommandException {
        if (command.getArgs().size() != 1)
            throw new InvalidCommandException("DELETE requires a letter (A, B, C...)");

        char label = command.getArgs().get(0).toUpperCase().charAt(0);
        int index = label - 'A';

        if (index < 0 || index >= CollectionSetsOfIntegers.sets.size())
            throw new InvalidCommandException("Invalid set letter.");

        CollectionSetsOfIntegers.deleteSet(index);
        System.out.println("Deleted set " + label);

        if (currentSetIndex == index) {
            currentSetIndex = -1;
        }
    }

    private void handleSort() throws InvalidCommandException {
        validateSelection();
        int[] current = CollectionSetsOfIntegers.sets.get(currentSetIndex);
        CollectionSetsOfIntegers.sets.set(currentSetIndex, SetOfIntegers.sortIncreasing(current, current));
        System.out.println("Current set sorted in increasing order.");
    }

    private void handleReverse() {
        if (currentSetIndex == -1) {
            System.out.println("No set selected.");
            return;
        }
        int[] current = CollectionSetsOfIntegers.sets.get(currentSetIndex);
        CollectionSetsOfIntegers.sets.set(currentSetIndex, SetOfIntegers.reverse(current));
        System.out.println("Current set sorted in decreasing order.");
    }

    private void handleRandomize() throws InvalidCommandException {
        validateSelection();
        int[] current = CollectionSetsOfIntegers.sets.get(currentSetIndex);
        CollectionSetsOfIntegers.sets.set(currentSetIndex, SetOfIntegers.randomSort(current));
        System.out.println("Current set randomized.");
    }

    private void handleAdd(Command command) throws InvalidCommandException {
        validateSelection();
        if (command.getArgs().isEmpty()) {
            throw new InvalidCommandException("ADD requires at least one integer.");
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

    private void handleSave() {
        // TODO: implement SAVE command
    }

    private void handleRestore() {
        // TODO: implement RESTORE command
    }

    // --- Supporting classes ---

    public static class Command {
        private CommandType type;
        private List<String> args;

        public Command(CommandType type, List<String> args) {
            this.type = type;
            this.args = args;
        }

        try {
            int[] toAdd = command.getArgs().stream().mapToInt(Integer::parseInt).toArray();
            int[] updated = SetOfIntegers.addValue(CollectionSetsOfIntegers.sets.get(currentSetIndex), toAdd);
            CollectionSetsOfIntegers.sets.set(currentSetIndex, updated);
            System.out.println("Added values: " + Arrays.toString(toAdd));
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("ADD only accepts integers.");
        }
    }

    private void handleRemove(Command command) throws InvalidCommandException {
        validateSelection();
        if (command.getArgs().isEmpty()) {
            throw new InvalidCommandException("REMOVE requires at least one integer.");
        }
=======
    public enum CommandType {
        SHOW, NEW, ADD, SORT, REVERSE, RANDOMIZE, DELETE, HELP, EXIT, SELECT, SAVE, RESTORE, QUIT
    }

        try {
            int[] toRemove = command.getArgs().stream().mapToInt(Integer::parseInt).toArray();
            int[] updated = SetOfIntegers.removeValue(CollectionSetsOfIntegers.sets.get(currentSetIndex), toRemove);
            CollectionSetsOfIntegers.sets.set(currentSetIndex, updated);
            System.out.println("Removed values: " + Arrays.toString(toRemove));
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("REMOVE only accepts integers.");
        }
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


    //  Utility Methods 

    private void validateSelection() throws InvalidCommandException {
        if (currentSetIndex == -1)
            throw new InvalidCommandException("No set selected. Use SELECT command first.");
    }

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

    //  Inner Classes 

    private static class Command {
        private final CommandType type;
        private final List<String> args;

        public Command(CommandType type, List<String> args) {
            this.type = type;
            this.args = args;
        }

        public CommandType getType() { return type; }
        public List<String> getArgs() { return args; }
    }

    private enum CommandType {
        NEW, SHOW, SELECT, DELETE,
        SORT, REVERSE, RANDOMIZE,
        ADD, REMOVE,
        SAVE, RESTORE,
        HELP, EXIT, QUIT
    }

    private static class InvalidCommandException extends Exception {
        public InvalidCommandException(String message) {
            super(message);
        }
    }
}
