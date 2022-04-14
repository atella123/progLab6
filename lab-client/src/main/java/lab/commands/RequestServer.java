package lab.commands;

import java.util.ArrayList;
import java.util.Arrays;

import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.commands.CommandResult;
import lab.common.util.ArgumentParser;
import lab.common.util.CommandManager;
import lab.common.util.CommandWithArguments;
import lab.io.DatagramSocketIOManager;

public final class RequestServer<R> extends Command {

    private final DatagramSocketIOManager io;
    private CommandManager<R> commandManager;
    private ArgumentParser<Object> argumentParser;

    public RequestServer(DatagramSocketIOManager io, CommandManager<R> commandManager,
            ArgumentParser<Object> argumentParser) {
        super(true);
        this.io = io;
        this.commandManager = commandManager;
        this.argumentParser = argumentParser;
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isVaildArgument(args)) {
            return new CommandResponse(CommandResult.ERROR, "Illegal argument");
        }
        Command command = (Command) args[0];
        Object[] arguments = convertArguments(command, Arrays.copyOfRange(args, 1, args.length));
        CommandWithArguments commandWithArguments = new CommandWithArguments(
                command.getClass(), arguments);
        io.write(commandWithArguments);
        return io.readLine();
    }

    @Override
    public boolean isVaildArgument(Object... args) {
        if (args.length > 0 && args[0] instanceof Command) {
            return commandManager.containsValue((Command) args[0]);
        }
        return false;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class[] {
                Command.class };
    }

    public Object[] convertArguments(Command command, Object[] argumentsToParse) {
        Class<?>[] argumentClasses = command.getArgumentClasses();
        ArrayList<Object> arguments = new ArrayList<>(argumentClasses.length);
        arguments.addAll(Arrays.asList(argumentsToParse));
        arguments.addAll(Arrays.asList(new Object[argumentClasses.length - argumentsToParse.length]));
        for (int i = 0; i < argumentClasses.length; i++) {
            arguments.set(i, argumentParser.convert(argumentClasses[i], arguments.get(i)));
        }
        return arguments.toArray();
    }

    @Override
    public String getMan() {
        return "request_server command_name arguments... : запросить сервер выполнить указанную комманду с указанными аргументами";
    }

    public CommandManager<R> getCommandManager() {
        return commandManager;
    }

    public ArgumentParser<Object> getArgumentParser() {
        return argumentParser;
    }

    public void setCommandManager(CommandManager<R> commandManager) {
        this.commandManager = commandManager;
    }

    public void setArgumentParser(ArgumentParser<Object> argumentParser) {
        this.argumentParser = argumentParser;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((argumentParser == null) ? 0 : argumentParser.hashCode());
        result = prime * result + ((commandManager == null) ? 0 : commandManager.hashCode());
        result = prime * result + ((io == null) ? 0 : io.hashCode());
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
        RequestServer<?> other = (RequestServer<?>) obj;
        if (argumentParser == null) {
            if (other.argumentParser != null) {
                return false;
            }
        } else if (!argumentParser.equals(other.argumentParser)) {
            return false;
        }
        if (commandManager == null) {
            if (other.commandManager != null) {
                return false;
            }
        } else if (!commandManager.equals(other.commandManager)) {
            return false;
        }
        if (io == null) {
            return other.io == null;
        }
        return io.equals(other.io);
    }

}
