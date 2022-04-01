package lab.parsers;

import lab.common.data.Location;
import lab.io.IOManager;
import lab.util.DataReader;

public final class LocationParser {

    private LocationParser() {
    }

    public static Location parseLocation(IOManager io) {
        Float x = parseX(io);
        long y = parseY(io);
        String name = parseName(io);
        return new Location(x, y, name);
    }

    public static Float parseX(IOManager io) {
        io.write("Enter x coordinate for location");
        return DataReader.readStringAsObject(io, Float::parseFloat, "Enter valid Float", false);
    }

    public static long parseY(IOManager io) {
        io.write("Enter y coordinate for location");
        return DataReader.readStringAsObject(io, Long::valueOf, "Enter valid Long", false);
    }

    public static String parseName(IOManager io) {
        io.write("Enter location name");
        return DataReader.readValidString(io, Location.Validator::isValidName, "Enter valid location name");
    }
}
