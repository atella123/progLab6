package lab.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.gson.Gson;

import lab.common.commands.Command;
import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;
import lab.common.json.DefalutGsonCreator;
import lab.common.util.CommandRunner;
import lab.common.io.IOManager;
import lab.common.commands.Info;
import lab.common.commands.MinByCoordinates;
import lab.common.commands.RemoveByID;
import lab.common.commands.RemoveGreater;
import lab.common.commands.Save;
import lab.common.commands.Show;
import lab.common.commands.Update;
import lab.common.commands.Add;
import lab.common.commands.AddIfMax;
import lab.common.commands.Clear;
import lab.common.commands.Exit;
import lab.common.commands.FilterLessThanNationality;
import lab.common.commands.GroupCountingByPassportID;
import lab.common.commands.Help;
import lab.common.commands.History;

public final class Server {

    private static final Integer PORT = 1234;

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        PersonCollectionServer personCollectionServer = new PersonCollectionServer(PORT,
                DefalutGsonCreator.createGson());
        personCollectionServer.run();
    }

    @SuppressWarnings("unchecked")
    public static Collection<Person> readCollectionFromFile(File file, Gson gson, IOManager io) {
        StringBuilder jsonBuilder = new StringBuilder();
        if (file.exists() && file.isFile()) {
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
                io.write("File is empty");
            } catch (IOException e) {
                io.write("Can't read collection from file");
            }
        } else {
            io.write("Can't find file");
        }
        return new HashSet<>();
    }

    public static Map<String, Command> createCommandsMap(PersonCollectionManager manager, Gson gson,
            CommandRunner runner, File file) {
        HashMap<String, Command> commands = new HashMap<>();
        commands.put("help", new Help(commands.values()));
        commands.put("info", new Info(manager));
        commands.put("show", new Show(manager));
        commands.put("add", new Add(manager));
        commands.put("update", new Update(manager));
        commands.put("remove_by_id", new RemoveByID(manager));
        commands.put("clear", new Clear(manager));
        commands.put("save", new Save(manager, gson, file));
        commands.put("exit", new Exit());
        commands.put("add_if_max", new AddIfMax(manager));
        commands.put("remove_greater", new RemoveGreater(manager));
        commands.put("history", new History(runner));
        commands.put("min_by_coordinates", new MinByCoordinates(manager));
        commands.put("group_counting_by_passport_id", new GroupCountingByPassportID(manager));
        commands.put("filter_less_than_nationality", new FilterLessThanNationality(manager));
        return commands;
    }
}
