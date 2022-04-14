package lab.util;

import java.net.SocketException;
import java.util.Arrays;

import lab.commands.RequestServer;
import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.io.IOManager;
import lab.common.util.ArgumentParser;
import lab.common.util.CommandManager;
import lab.common.util.CommandRunner;

public class ClientCommandRunner extends CommandRunner<String, String> {

    public ClientCommandRunner(CommandManager<String> clientCommands,
            ArgumentParser<Object> argumentParser,
            IOManager<String, CommandResponse> io) throws SocketException {
        super(clientCommands, argumentParser, io);
    }

    @Override
    @SuppressWarnings("unckeched")
    public Command parseCommand(String arg) {
        String cmd = arg.split("\\s+")[0].trim();
        if (getCommandManager().containsKey(cmd)) {
            return getCommandManager().get(cmd);
        }
        RequestServer<?> requestCommand = (RequestServer<?>) getCommandManager().get("");

        if (requestCommand.getCommandManager().containsKey(cmd)) {
            return requestCommand;
        }
        return null;
    }

    @Override
    public String[] parseArgumentsFromReadedObject(String arg) {
        String[] splittedString = arg.trim().split("\\s+");
        if (!(parseCommand(arg) instanceof RequestServer)) {
            splittedString = Arrays.copyOfRange(splittedString, 1, splittedString.length);
        }
        return splittedString;
    }

    public void setRequestCommandArgumentParser(ArgumentParser<Object> argumentParser) {
        ((RequestServer<?>) getCommandManager().get("")).setArgumentParser(argumentParser);
    }

    public static ArgumentParser<Object> createRunnerArgumentParser(CommandManager<String> serverCommands) {
        ArgumentParser<Object> argumentParser = new ArgumentParser<>();
        argumentParser.add(Command.class, serverCommands::get);
        return argumentParser;
    }

}
