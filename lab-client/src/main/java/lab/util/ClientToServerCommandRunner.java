package lab.util;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Arrays;

import lab.commands.RequestServer;
import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.io.IOManager;
import lab.common.util.ArgumentParser;
import lab.common.util.CommandManager;
import lab.common.util.CommandRunner;
import lab.io.DatagramSocketIOManager;

public class ClientToServerCommandRunner extends CommandRunner<String, String> {

    private final RequestServer<String, String> requestCommand;

    public ClientToServerCommandRunner(CommandManager<String> clientCommands,
            CommandRunner<String, String> serverCommandRunner,
            ArgumentParser<Object> argumentParser,
            InetSocketAddress serverAddress,
            IOManager<String, CommandResponse> io) throws SocketException {
        super(clientCommands, argumentParser, io);
        requestCommand = new RequestServer<>(new DatagramSocketIOManager(serverAddress), serverCommandRunner);
    }

    @Override
    @SuppressWarnings("unckeched")
    public Command parseCommand(String arg) {
        String cmd = arg.trim().split("\\s+")[0];
        return getCommandManager().getOrDefault(cmd, requestCommand);
    }

    @Override
    public String[] parseArgumentsFromReadedObject(String arg) {
        String[] splittedString = arg.trim().split("\\s+");
        if (parseCommand(arg) != requestCommand) {
            splittedString = Arrays.copyOfRange(splittedString, 1, splittedString.length);
        }
        return splittedString;
    }

    public static ArgumentParser<Object> createRunnerArgumentParser(CommandManager<String> serverCommands) {
        ArgumentParser<Object> argumentParser = new ArgumentParser<>();
        argumentParser.add(Command.class, serverCommands::get);
        return argumentParser;
    }

}
