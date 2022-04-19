package lab.commands;

import java.util.Arrays;

import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.commands.CommandResult;
import lab.common.util.CommandManager;
import lab.common.util.CommandRunner;
import lab.common.util.CommandWithArguments;
import lab.io.DatagramSocketIOManager;

public final class RequestServer<R, C> extends Command {

    private final DatagramSocketIOManager io;
    private final CommandRunner<R, C> toServerCommandRunner;

    public RequestServer(DatagramSocketIOManager io, CommandRunner<R, C> toServerCommandRunner) {
        super(true);
        this.io = io;
        this.toServerCommandRunner = toServerCommandRunner;
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isVaildArgument(args)) {
            return new CommandResponse(CommandResult.ERROR, "Illegal argument");
        }
        if (!toServerCommandRunner.getCommandManager().containsKey(args[0])) {
            return new CommandResponse(CommandResult.ERROR, "Unknown command");
        }
        Command command = toServerCommandRunner.getCommandManager().get(args[0]);
        Object[] parsedArgs = toServerCommandRunner.parseArguments(command, Arrays.copyOfRange(args, 1, args.length));
        if (!command.isVaildArgument(parsedArgs)) {
            return new CommandResponse(CommandResult.ERROR, "Illegal argument");
        }
        CommandWithArguments commandWithArguments = new CommandWithArguments(command.getClass(), parsedArgs);
        io.write(commandWithArguments);
        return io.read();
    }

    @Override
    public boolean isVaildArgument(Object... args) {
        return args.length > 0;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class[] {
                Object.class };
    }

    @Override
    public String getMan() {
        return "request_server command_name arguments... : запросить сервер выполнить указанную комманду с указанными аргументами";
    }

    public CommandManager<C> getCommandManager() {
        return toServerCommandRunner.getCommandManager();
    }
}
