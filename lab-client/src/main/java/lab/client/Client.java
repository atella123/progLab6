package lab.client;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import lab.commands.ExecuteScript;
import lab.common.commands.Add;
import lab.common.commands.AddIfMax;
import lab.common.commands.Clear;
import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.commands.Exit;
import lab.common.commands.FilterLessThanNationality;
import lab.common.commands.GroupCountingByPassportID;
import lab.common.commands.Help;
import lab.common.commands.History;
import lab.common.commands.Info;
import lab.common.commands.MinByCoordinates;
import lab.common.commands.RemoveByID;
import lab.common.commands.RemoveGreater;
import lab.common.commands.Show;
import lab.common.commands.Update;
import lab.common.data.Color;
import lab.common.data.Coordinates;
import lab.common.data.Location;
import lab.common.data.Country;
import lab.common.data.Person;
import lab.common.io.IOManager;
import lab.common.io.Writter;
import lab.common.parsers.CoordinatesParser;
import lab.common.parsers.LocationParser;
import lab.common.parsers.PersonParser;
import lab.common.util.ArgumentParser;
import lab.common.util.CommandManager;
import lab.common.util.CommandRunner;
import lab.common.util.DataReader;
import lab.common.util.DefaultCommandRunner;
import lab.util.ClientCommandRunner;

public final class Client {

    private Client() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IOManager<String, String> io = new IOManager<>(() -> {
            System.out.print("% ");
            if (scanner.hasNext()) {
                return scanner.nextLine();
            }
            return "";
        }, System.out::println);
        int port = getServerPort(args, io::write);
        if (port == -1) {
            scanner.close();
            return;
        }
        try {
            createCommandRunner(port, io).run();
        } catch (SocketException e) {
            io.write("Socket couldn't be binded");
        }
        scanner.close();
    }

    public static ClientCommandRunner createCommandRunner(int port, IOManager<String, ?> io) throws SocketException {
        CommandManager<String> clientCommandManager = new CommandManager<>();
        ArgumentParser<Object> argumentParser = new ArgumentParser<>();
        IOManager<String, CommandResponse> commandRunnerIO = new IOManager<>(io::readLine,
                response -> {
                    if (response.hasPrintableResult()) {
                        System.out.println(response.getMessage());
                    }
                    if (response.hasCollectionToPrint()) {
                        for (Person p : response.getCollection()) {
                            System.out.println(p);
                        }
                    }
                });
        CommandManager<String> serverCommandManager = new CommandManager<>(createServerCommandsMap());
        CommandRunner<String, String> toServerCommandRunner = new DefaultCommandRunner(
                serverCommandManager,
                argumentParser, commandRunnerIO);
        ClientCommandRunner runner = new ClientCommandRunner(clientCommandManager, toServerCommandRunner,
                argumentParser, new InetSocketAddress("localhost", port), commandRunnerIO);
        updateArgumentParser(argumentParser, runner, serverCommandManager);
        clientCommandManager.setCommands(createCommands(runner));
        return runner;
    }

    public static void updateArgumentParser(ArgumentParser<Object> argumentParser, CommandRunner<String, ?> runner,
            CommandManager<String> commandManager) {
        argumentParser.add(Command.class, commandManager::get);
        argumentParser.add(Integer.class, x -> {
            try {
                return Integer.valueOf(x.toString());
            } catch (NumberFormatException e) {
                return null;
            }
        });
        argumentParser.add(String.class, Client::convertToString);
        argumentParser.add(Country.class,
                x -> DataReader.readEnumValue(convertToString(x), Country.class));
        argumentParser.add(Color.class,
                x -> DataReader.readEnumValue(convertToString(x), Color.class));
        argumentParser.add(File.class, x -> {
            if (Objects.nonNull(x)) {
                return new File(x.toString());
            }
            return null;
        });
        argumentParser.add(Person.class,
                x -> PersonParser.parsePerson(new IOManager<>(runner.getReader(), System.out::println)));
        argumentParser.add(Coordinates.class,
                x -> CoordinatesParser.parseCoordinates(new IOManager<>(runner.getReader(), System.out::println)));
        argumentParser.add(Location.class,
                x -> LocationParser.parseLocation(new IOManager<>(runner.getReader(), System.out::println)));
    }

    public static String convertToString(Object o) {
        if (Objects.isNull(o)) {
            return null;
        }
        return o.toString();
    }

    public static int getServerPort(String[] args, Writter<String> writter) {
        final int defaultPort = 1234;
        if (args.length < 1) {
            writter.write("Set default port");
            return defaultPort;
        }
        try {
            return Integer.valueOf(args[0]);
        } catch (NumberFormatException e) {
            writter.write(
                    "Second agrument must either be valid integer or not present (if so will be set to default value)");
        }
        return -1;
    }

    public static Map<String, Command> createCommands(CommandRunner<String, ?> commandRunner) {
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("exit", new Exit());
        commands.put("execute_script", new ExecuteScript(commandRunner));
        return commands;
    }

    public static Map<String, Command> createServerCommandsMap() {
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("help", new Help());
        commands.put("info", new Info());
        commands.put("show", new Show());
        commands.put("add", new Add());
        commands.put("update", new Update());
        commands.put("remove_by_id", new RemoveByID());
        commands.put("clear", new Clear());
        commands.put("add_if_max", new AddIfMax());
        commands.put("remove_greater", new RemoveGreater());
        commands.put("history", new History());
        commands.put("min_by_coordinates", new MinByCoordinates());
        commands.put("group_counting_by_passport_id", new GroupCountingByPassportID());
        commands.put("filter_less_than_nationality", new FilterLessThanNationality());
        return commands;
    }
}
