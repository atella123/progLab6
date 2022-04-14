package lab.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lab.common.commands.Command;

public final class CommandWithArguments implements Serializable {

    final Class<? extends Command> commandClass;
    final ArrayList<Object> arguments;

    public CommandWithArguments(Class<? extends Command> commandClass, Object... arguments) {
        this.commandClass = commandClass;
        this.arguments = new ArrayList<>(Arrays.asList(arguments));
    }

    public Class<? extends Command> getCommandClass() {
        return commandClass;
    }

    public List<Object> getArgumnets() {
        return arguments;
    }

}
