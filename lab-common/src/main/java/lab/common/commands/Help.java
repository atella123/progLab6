package lab.common.commands;

import java.util.Collection;
import java.util.stream.Collectors;

public final class Help extends Command {

    private Collection<Command> commands;

    public Help() {
        super();
    }

    public Help(Collection<Command> commands) {
        super(true);
        this.commands = commands;
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isExecutableInstance) {
            return new CommandResponse(CommandResult.ERROR, "Execute called on unexecutable instance");
        }
        return new CommandResponse(CommandResult.SUCCESS,
                commands.stream().map(Command::getMan).collect(Collectors.joining("\n")));
    }

    @Override
    public String toString() {
        return "Help";
    }

    @Override
    public String getMan() {
        return "help : вывести справку по доступным командам";
    }

    @Override
    public boolean isVaildArgument(Object... args) {
        return true;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class<?>[0];
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
        Help other = (Help) obj;
        if (commands == null) {
            return other.commands == null;
        }
        return commands.equals(other.commands);
    }
}
