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
