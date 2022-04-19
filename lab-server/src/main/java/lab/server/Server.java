package lab.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import com.google.gson.Gson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lab.commands.SaveAndExit;
import lab.common.commands.Add;
import lab.common.commands.AddIfMax;
import lab.common.commands.Clear;
import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.commands.FilterLessThanNationality;
import lab.common.commands.GroupCountingByPassportID;
import lab.common.commands.Help;
import lab.common.commands.History;
import lab.common.commands.Info;
import lab.common.commands.MinByCoordinates;
import lab.common.commands.RemoveByID;
import lab.common.commands.RemoveGreater;
import lab.common.commands.Save;
import lab.common.commands.Show;
import lab.common.commands.Update;
import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;
import lab.common.json.DefalutGsonCreator;
import lab.common.util.ArgumentParser;
import lab.common.util.CommandManager;
import lab.common.util.CommandRunner;
import lab.common.util.DefaultCommandRunner;
import lab.io.DatagramChannelIOManager;
import lab.util.PersonCollectionServer;
import lab.util.ServerToClientCommandRunner;

public final class Server {

    private static final Logger LOGGER = LogManager.getLogger(lab.server.Server.class);

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IOManager<String, CommandResponse> io = createDefaultIOManager(scanner);
        File file = getSourceFile(args);
        int port = getServerPort(args);
        if (Objects.isNull(file) || port == -1) {
            scanner.close();
            return;
        }
        Gson gson = DefalutGsonCreator.createGson();
        Collection<Person> collection = readCollectionFromFile(file, gson);
        PersonCollectionManager manager = new PersonCollectionManager(collection);
        CommandManager<Class<? extends Command>> clientCommandManager = new CommandManager<>();
        ServerToClientCommandRunner clientCommandRunner;
        try {
            clientCommandRunner = new ServerToClientCommandRunner(clientCommandManager,
                    new ArgumentParser<>(), new DatagramChannelIOManager(port));
        } catch (IOException e) {
            LOGGER.error("Starting server failed: {}", e.getMessage());
            scanner.close();
            return;
        }
        clientCommandManager.setCommands(createClientCommandsMap(manager, clientCommandRunner));
        CommandManager<String> serverCommandManager = new CommandManager<>(
                createServerCommandsMap(manager, gson, file));
        CommandRunner<String, String> serverCommandRunner = new DefaultCommandRunner(serverCommandManager,
                new ArgumentParser<>(), io);
        LOGGER.info("Starting server at port {}", port);
        PersonCollectionServer.start(serverCommandRunner, clientCommandRunner);
        LOGGER.info("Server stopped");
        scanner.close();
    }

    @SuppressWarnings("unchecked")
    public static Collection<Person> readCollectionFromFile(File file, Gson gson) {
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                jsonBuilder.append(line);
                jsonBuilder.append("\n");
                line = bufferedReader.readLine();
            }
            String result = jsonBuilder.toString().trim();
            if (result.length() != 0) {
                return gson.fromJson(result, HashSet.class);
            }
            LOGGER.error("Starting server with new empty collection");
        } catch (IOException e) {
            LOGGER.error("Starting server failed: Can't read from file");
        }
        return new HashSet<>();
    }

    public static Map<Class<? extends Command>, Command> createClientCommandsMap(PersonCollectionManager manager,
            ServerToClientCommandRunner runner) {
        HashMap<Class<? extends Command>, Command> commands = new HashMap<>();
        commands.put(Help.class, new Help(commands.values()));
        commands.put(Info.class, new Info(manager));
        commands.put(Show.class, new Show(manager));
        commands.put(Add.class, new Add(manager));
        commands.put(Update.class, new Update(manager));
        commands.put(RemoveByID.class, new RemoveByID(manager));
        commands.put(Clear.class, new Clear(manager));
        commands.put(AddIfMax.class, new AddIfMax(manager));
        commands.put(RemoveGreater.class, new RemoveGreater(manager));
        commands.put(History.class, new History(runner));
        commands.put(MinByCoordinates.class, new MinByCoordinates(manager));
        commands.put(GroupCountingByPassportID.class, new GroupCountingByPassportID(manager));
        commands.put(FilterLessThanNationality.class, new FilterLessThanNationality(manager));
        return commands;
    }

    public static Map<String, Command> createServerCommandsMap(PersonCollectionManager manager, Gson gson, File file) {
        HashMap<String, Command> commands = new HashMap<>();
        Save saveCommand = new Save(manager, gson, file);
        commands.put("save", saveCommand);
        commands.put("exit", new SaveAndExit(saveCommand));
        return commands;
    }

    public static File getSourceFile(String[] args) {
        if (args.length == 0) {
            LOGGER.error("Starting server falied: Path to collection file must be provided");
            return null;
        }
        File file = new File(args[0]);
        if (!file.exists() || !file.isFile()) {
            LOGGER.error("Starting server failed: File not found");
            return null;
        }
        if (!file.canRead()) {
            LOGGER.error("Starting server failed: Can't read from file");
            return null;
        }
        return file;
    }

    public static int getServerPort(String[] args) {
        final int defaultPort = 1234;
        final int maxPort = 65535;
        final int minPort = 1;
        if (args.length < 2) {
            LOGGER.info("Port value set as default (1234)");
            return defaultPort;
        }
        if (args[1].matches("\\d{1,5}")) {
            int port = Integer.parseInt(args[1]);
            if (port <= maxPort && port >= minPort) {
                return port;
            }
        }
        LOGGER.error(
                "Second argument must either be valid port value or not present (if so it will be set to default value)");
        return -1;
    }

    public static IOManager<String, CommandResponse> createDefaultIOManager(Scanner scanner) {
        return new IOManager<>(
                () -> {
                    if (scanner.hasNext()) {
                        return scanner.nextLine();
                    }
                    return "";
                },
                (CommandResponse response) -> {
                    if (response.hasPrintableResult()) {
                        System.out.println(response.getMessage());
                    }
                });
    }
}
