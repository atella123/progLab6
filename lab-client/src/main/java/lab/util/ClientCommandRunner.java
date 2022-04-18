package lab.util;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Objects;

import lab.commands.RequestServer;
import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.io.IOManager;
import lab.common.util.ArgumentParser;
import lab.common.util.CommandManager;
import lab.common.util.CommandRunner;
import lab.io.DatagramSocketIOManager;

public class ClientCommandRunner extends CommandRunner<String, String, Object> {

    private final RequestServer<String, String> requestCommand;

    public ClientCommandRunner(CommandManager<String> clientCommands,
            CommandRunner<String, String, Object> serverCommandRunner,
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
        Command command = getCommandManager().get(cmd);
        if (Objects.nonNull(command)) {
            return command;
        }
        if (requestCommand.getCommandManager().containsKey(cmd)) {
            return requestCommand;
        }
        return null;
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
