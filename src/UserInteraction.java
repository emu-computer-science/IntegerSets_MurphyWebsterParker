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
            // Add other cases here as you implement them
            case ADD: handleAdd(); break;
            case SORT: handleSort(); break;
            case REVERSE: handleReverse(); break;
            case LABEL: handleLabel(); break;
            case HELP: handleHelp(); break;
            case SAVE: handleSave(); break;
            case RESTORE: handleRestore(); break;
            case QUIT: handleQuit(); break;
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

    // Method shells for you to implement later:

    private void handleAdd() {
        // TODO: implement ADD command
    }

    private void handleSort() {
        // TODO: implement SORT command
    }

    private void handleReverse() {
        // TODO: implement REVERSE command
    }

    private void handleLabel() {
        // TODO: implement LABEL command
    }

    private void handleHelp() {
        // TODO: implement HELP command
    }

    private void handleSave() {
        // TODO: implement SAVE command
    }

    private void handleRestore() {
        // TODO: implement RESTORE command
    }

    private void handleQuit() {
        // TODO: implement QUIT command (if different from EXIT)
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
        SHOW, NEW, ADD, SORT, REVERSE, LABEL, DELETE, HELP, EXIT, SELECT, SAVE, RESTORE, QUIT
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
