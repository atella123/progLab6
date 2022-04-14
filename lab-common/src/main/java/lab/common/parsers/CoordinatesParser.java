package lab.common.parsers;

import lab.common.data.Coordinates;
import lab.common.util.DataReader;
import lab.common.io.IOManager;

public final class CoordinatesParser {

    private CoordinatesParser() {
    }

    public static Coordinates parseCoordinates(IOManager<String, String> io) {
        Float x = parseX(io);
        Integer y = parseY(io);
        return new Coordinates(x, y);
    }

    public static Float parseX(IOManager<String, String> io) {
        io.write("Enter x coordinate");
        return DataReader.readStringAsObject(io, Float::parseFloat, "Enter valid Float", false);
    }

    public static Integer parseY(IOManager<String, String> io) {
        io.write("Enter y coordinate");
        return DataReader.readStringAsValidObject(io, Integer::parseInt, Coordinates.Validator::isValidY,
                "Enter valid Integer", "y must be bigger than -322", false);
    }
}
