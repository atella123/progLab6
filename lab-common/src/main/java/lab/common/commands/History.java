package lab.common.commands;

import java.util.stream.Collectors;

import lab.common.util.CommandRunner;

public final class History extends Command {

    private CommandRunner<?, ?> commands;

    public History() {
        super();
    }

    public History(CommandRunner<?, ?> commands) {
        super(true);
        this.commands = commands;
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isExecutableInstance) {
            return new CommandResponse(CommandResult.ERROR, "Execute called on unexecutable instance");
        }
        return new CommandResponse(CommandResult.SUCCESS,
                commands.getHistory().stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    @Override
    public String toString() {
        return "History";
    }

    public String getMan() {
        return "history : вывести последние 11 команд (без их аргументов)";
    }

    @Override
    public boolean isVaildArgument(Object... args) {
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((commands == null) ? 0 : commands.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        History other = (History) obj;
        if (commands == null) {
            return other.commands == null;
        }
        return commands.equals(other.commands);
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class<?>[0];
    }

}
