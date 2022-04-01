package lab.parsers;

import lab.common.data.Color;
import lab.common.data.Coordinates;
import lab.common.data.Country;
import lab.common.data.Location;
import lab.common.data.Person;
import lab.io.IOManager;
import lab.util.DataReader;

public final class PersonParser {

    private PersonParser() {
    }

    public static Person parsePerson(IOManager io) {
        String name = parseName(io);
        Coordinates coordinates = parseCoordinates(io);
        int height = parseHeight(io);
        String passportID = parsePassportID(io);
        Color eyeColor = parseEyeColor(io);
        Country nationality = parseNationality(io);
        Location location = parseLocation(io);
        return new Person(name, coordinates, height, passportID, eyeColor, nationality, location);
    }

    public static String parseName(IOManager io) {
        io.write("Enter person name");
        return DataReader.readValidString(io, Person.Validator::isValidName, "Person name can't be empty");
    }

    public static Coordinates parseCoordinates(IOManager io) {
        io.write("Enter person coordinates");
        return CoordinatesParser.parseCoordinates(io);
    }

    public static int parseHeight(IOManager io) {
        io.write("Enter person height");
        return DataReader.readStringAsValidObject(io, Integer::parseInt, Person.Validator::isValidHeight,
                "Enter positive value", "Enter valid Integer", false);
    }

    public static String parsePassportID(IOManager io) {
        io.write("Enter person passportID");
        return DataReader.readValidString(io, Person.Validator::isValidPassportID,
                "passportID has min lenght (10)");
    }

    public static Color parseEyeColor(IOManager io) {
        io.write("Enter person eyeColor");
        return DataReader.readEnumValue(io, Color.class);
    }

    public static Country parseNationality(IOManager io) {
        io.write("Enter person nationality");
        return DataReader.readEnumValue(io, Country.class);
    }

    public static Location parseLocation(IOManager io) {
        io.write("Enter person location");
        return LocationParser.parseLocation(io);
    }

}
