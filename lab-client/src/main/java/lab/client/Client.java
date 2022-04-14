package lab.client;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import lab.commands.ExecuteScript;
import lab.commands.RequestServer;
import lab.common.commands.Add;
import lab.common.commands.AddIfMax;
import lab.common.commands.Clear;
import lab.common.commands.Command;
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
import lab.io.DatagramSocketIOManager;
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
            CommandManager<String> commandManager = new CommandManager<>();
            ArgumentParser<Object> argumentParser = new ArgumentParser<>();
            ClientCommandRunner runner = new ClientCommandRunner(commandManager, argumentParser,
                    new IOManager<>(io::readLine,
                            response -> {
                                if (response.hasPrintableResult()) {
                                    System.out.println(response.getMessage());
                                }
                                if (response.hasCollectionToPrint()) {
                                    for (Person p : response.getCollection()) {
                                        System.out.println(p);
                                    }
                                }
                            }));
            updateArgumentParser(argumentParser, runner, createServerCommandsMap());
            commandManager.setCommands(
                    createCommands(port, createServerCommandsMap(), argumentParser, runner));
            runner.run();
        } catch (SocketException e) {
            io.write("Socket couldn't be binded");
        }
        scanner.close();
    }

    public static void updateArgumentParser(ArgumentParser<Object> argumentParser, CommandRunner<String, ?> runner,
            CommandManager<String> commandManager) {
        argumentParser.add(Command.class, commandManager::get);
        argumentParser.add(Integer.class, x -> Integer.valueOf(x.toString()));
        argumentParser.add(String.class, Object::toString);
        argumentParser.add(Person.class,
                x -> PersonParser.parsePerson(new IOManager<>(runner.getReader(), System.out::println)));
        argumentParser.add(Coordinates.class,
                x -> CoordinatesParser.parseCoordinates(new IOManager<>(runner.getReader(), System.out::println)));
        argumentParser.add(Location.class,
                x -> LocationParser.parseLocation(new IOManager<>(runner.getReader(), System.out::println)));
        argumentParser.add(Country.class,
                x -> DataReader.readEnumValue(new IOManager<>(runner.getReader(), System.out::println), Country.class));
        argumentParser.add(Color.class,
                x -> DataReader.readEnumValue(new IOManager<>(runner.getReader(), System.out::println), Color.class));
        argumentParser.add(File.class, x -> new File(x.toString()));
    }

    public static int getServerPort(String[] args, Writter<String> writter) {
        final int defaultPort = 1234;
        if (args.length < 2) {
            writter.write("Set default port");
            return defaultPort;
        }
        try {
            return Integer.valueOf(args[1]);
        } catch (NumberFormatException e) {
            writter.write(
                    "Second agrument must either be valid integer or not present (if so will be set to default value)");
        }
        return -1;
    }

    public static Map<String, Command> createCommands(int port, CommandManager<String> serverCommands,
            ArgumentParser<Object> argumentParser, CommandRunner<String, ?> commandRunner) throws SocketException {
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("",
                new RequestServer<String>(new DatagramSocketIOManager(new InetSocketAddress("localhost", port)),
                        serverCommands,
                        argumentParser));
        commands.put("exit", new Exit());
        commands.put("execute_script", new ExecuteScript(commandRunner));
        return commands;
    }

    public static CommandManager<String> createServerCommandsMap() {
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
        return new CommandManager<>(commands);
    }
}
