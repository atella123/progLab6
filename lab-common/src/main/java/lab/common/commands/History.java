package lab.common.commands;

import java.util.stream.Collectors;

import lab.common.util.CommandRunner;

public final class History extends Command {

    private CommandRunner commands;

    public History(CommandRunner commands) {
        super();
        this.commands = commands;
    }

    @Override
    public CommandResponse execute(String arg) {
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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((commands == null) ? 0 : commands.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        History other = (History) obj;
        if (commands == null) {
            if (other.commands != null) {
                return false;
            }
        } else if (!commands.equals(other.commands)) {
            return false;
        }
        return true;
    }
}
