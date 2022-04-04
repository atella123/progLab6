package lab.client;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lab.commands.ExecuteScript;
import lab.common.data.Color;
import lab.common.data.Coordinates;
import lab.common.data.Location;
import lab.common.data.Country;
import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;
import lab.common.json.LocalDateDeserializer;
import lab.common.json.PersonCollectionDeserializer;
import lab.common.json.PersonCollectionSerealizer;
import lab.common.json.PersonDeserializer;
import lab.common.json.PersonSerializer;
import lab.common.parsers.CoordinatesParser;
import lab.common.parsers.LocationParser;
import lab.common.parsers.PersonParser;
import lab.common.util.ArgumentParser;
import lab.common.util.CommandManager;
import lab.common.util.CommandRunner;
import lab.common.util.DataReader;
import lab.common.util.StringConverter;

public final class Client {

    private static final Integer PORT = 1234;
    private static final Integer BUFFER_SIZE = 1024;

    private Client() {
    }

    public static void main(String[] args) {

        // try (DatagramSocket socket = new DatagramSocket()) {
        // String message = "help";
        // DatagramPacket packet = new DatagramPacket(message.getBytes(),
        // message.getBytes().length,
        // InetAddress.getLocalHost(), PORT);
        // socket.send(packet);
        // DatagramPacket packet2 = new DatagramPacket(new byte[BUFFER_SIZE],
        // BUFFER_SIZE);
        // socket.receive(packet2);
        // System.out.println(new String(packet2.getData()));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        IOManager io = new IOManager();
        Gson gson = createGson();
        CommandManager commandManager = new CommandManager();
        CommandRunner runner = new CommandRunner(commandManager, createArgumentParser(io));
        io.setReader(() -> {
            System.out.print("% ");
            return io.defaultConsoleReader().readLine();
        });
        runner.setIO(io);
        runner.run();
    }

    public static Gson createGson() {
        return new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(HashSet.class,
                        new PersonCollectionSerealizer())
                .registerTypeAdapter(HashSet.class,
                        new PersonCollectionDeserializer())
                .registerTypeAdapter(Person.class, new PersonSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(Person.class, new PersonDeserializer()).create();
    }

    public static ArgumentParser createArgumentParser(IOManager io) {
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.add(Integer.class, Integer::valueOf);
        argumentParser.add(String.class, x -> x);
        argumentParser.add(Person.class, x -> PersonParser.parsePerson(io));
        argumentParser.add(Coordinates.class, x -> CoordinatesParser.parseCoordinates(io));
        argumentParser.add(Location.class, x -> LocationParser.parseLocation(io));
        argumentParser.add(Country.class, x -> DataReader.readEnumValue(io, Country.class));
        argumentParser.add(Color.class, x -> DataReader.readEnumValue(io, Color.class));
        return argumentParser;
    }
}
