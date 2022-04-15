package lab.commands;

import java.util.Arrays;

import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.commands.CommandResult;
import lab.common.util.ArgumentParser;
import lab.common.util.CommandRunner;
import lab.common.util.CommandWithArguments;
import lab.io.DatagramSocketIOManager;

public final class RequestServer<R> extends Command {

    private final DatagramSocketIOManager io;
    private CommandRunner<R, R> toServerCommandRunner;
    private ArgumentParser<Object> argumentParser;

    public RequestServer(DatagramSocketIOManager io, CommandRunner<R, R> toServerCommandRunner,
            ArgumentParser<Object> argumentParser) {
        super(true);
        this.io = io;
        this.toServerCommandRunner = toServerCommandRunner;
        this.argumentParser = argumentParser;
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isVaildArgument(args)) {
            return new CommandResponse(CommandResult.ERROR, "Illegal argument");
        }
        Command command = (Command) args[0];
        Object[] parsedArgs = toServerCommandRunner.parseArguments(command, Arrays.copyOfRange(args, 1, args.length));
        if (!command.isVaildArgument(parsedArgs)) {
            return new CommandResponse(CommandResult.ERROR, "Illegal argument");
        }
        CommandWithArguments commandWithArguments = new CommandWithArguments(command.getClass(), parsedArgs);
        io.write(commandWithArguments);
        return io.readLine();
    }

    @Override
    public boolean isVaildArgument(Object... args) {
        if (args.length > 0 && args[0] instanceof Command) {
            return toServerCommandRunner.getCommandManager().containsValue((Command) args[0]);
        }
        return false;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class[] {
                Command.class };
    }

    @Override
    public String getMan() {
        return "request_server command_name arguments... : запросить сервер выполнить указанную комманду с указанными аргументами";
    }

    public ArgumentParser<Object> getArgumentParser() {
        return argumentParser;
    }

    public void setArgumentParser(ArgumentParser<Object> argumentParser) {
        this.argumentParser = argumentParser;
    }

    public CommandRunner<R, R> getToServerCommandRunner() {
        return toServerCommandRunner;
    }

    public void setToServerCommandRunner(CommandRunner<R, R> toServerCommandRunner) {
        this.toServerCommandRunner = toServerCommandRunner;
    }

}
