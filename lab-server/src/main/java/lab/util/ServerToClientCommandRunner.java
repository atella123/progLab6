package lab.util;

import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.io.IOManager;
import lab.common.util.ArgumentParser;
import lab.common.util.CommandManager;
import lab.common.util.CommandRunner;
import lab.common.util.CommandWithArguments;

public class ServerToClientCommandRunner extends CommandRunner<CommandWithArguments, Class<? extends Command>, Object> {
    public ServerToClientCommandRunner(CommandManager<Class<? extends Command>> commandManager,
            ArgumentParser<Object> argumentParser, IOManager<CommandWithArguments, CommandResponse> io) {
        super(commandManager, argumentParser, io);
    }

    @Override
    public Command parseCommand(CommandWithArguments arg) {
        return getCommandManager().get(arg.getCommandClass());
    }

    @Override
    public Object[] parseArgumentsFromReadedObject(CommandWithArguments arg) {
        return arg.getArgumnets().toArray();
    }

}
