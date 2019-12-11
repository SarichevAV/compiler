package models;

public class AssemblerCommand {
    private CommandNames command;
    private String argument;

    public AssemblerCommand(CommandNames command, String argument) {
        this.command = command;
        this.argument = argument;
    }

    public AssemblerCommand(CommandNames command) {
        this.command = command;
    }

    public CommandNames getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssemblerCommand command1 = (AssemblerCommand) o;

        if (command != command1.command) return false;
        return argument != null ? argument.equals(command1.argument) : command1.argument == null;
    }

    @Override
    public int hashCode() {
        int result = command != null ? command.hashCode() : 0;
        result = 31 * result + (argument != null ? argument.hashCode() : 0);
        return result;
    }
}
