package lab.common.util;

import java.util.Arrays;

import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.io.IOManager;

public class DefaultCommandRunner extends CommandRunner<String, String, Object> {

    public DefaultCommandRunner(CommandManager<String> commandManager, ArgumentParser<Object> argumentParser,
            IOManager<String, CommandResponse> io) {
        super(commandManager, argumentParser, io);
    }

    @Override
    public Command parseCommand(String arg) {
        return getCommandManager().get(arg.split("\\s+")[0].trim());
    }

    @Override
    public String[] parseArgumentsFromReadedObject(String arg) {
        String[] splittedArg = arg.split("\\s+");
        return Arrays.copyOfRange(splittedArg, 1, splittedArg.length);
    }

}
