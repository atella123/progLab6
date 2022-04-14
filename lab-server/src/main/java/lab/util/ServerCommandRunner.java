package lab.util;

import java.util.Arrays;

import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.io.IOManager;
import lab.common.util.ArgumentParser;
import lab.common.util.CommandManager;
import lab.common.util.CommandRunner;

public class ServerCommandRunner extends CommandRunner<String, String> {

    public ServerCommandRunner(CommandManager<String> commandManager, ArgumentParser<Object> argumentParser,
            IOManager<String, CommandResponse> io) {
        super(commandManager, argumentParser, io);
    }

    @Override
    public Command parseCommand(String arg) {
        return getCommandManager().get(arg.split("\\s+")[0].trim());
    }

    @Override
    public Object[] parseArgumentsFromReadedObject(String arg) {
        String[] splittedArg = arg.split("\\s+");
        return Arrays.copyOfRange(splittedArg, 1, splittedArg.length);
    }

}
