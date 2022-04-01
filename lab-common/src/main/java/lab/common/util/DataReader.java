package lab.common.util;

import java.util.Objects;
import java.util.function.Predicate;

import lab.common.exceptions.StringIsNullException;
import lab.common.exceptions.UnableToConvertStringException;
import lab.common.io.IOManager;

public final class DataReader {

    private DataReader() {

    }

    public static String readString(IOManager io) {
        String s = io.readLine();
        if (Objects.isNull(s)) {
            throw new StringIsNullException();
        }
        if ("".equals(s)) {
            return null;
        }
        return s;
    }

    public static String readValidString(IOManager io, Predicate<String> predicate,
            String illegalValueMessage) {
        String s = readString(io);
        while (!predicate.test(s)) {
            io.write(illegalValueMessage);
            s = readString(io);
        }
        return s;
    }

    public static <T> T readStringAsObject(IOManager io, StringConverter<T> converter, String illegalValueMessage,
            boolean canBeNull) {
        T t;
        String s;
        while (true) {
            s = readString(io);
            try {
                t = convertString(s, converter, canBeNull);
                return t;
            } catch (UnableToConvertStringException e) {
                io.write(illegalValueMessage);
            }
        }
    }

    private static <T> T convertString(String s, StringConverter<T> converter, boolean canBeNull) {
        if (!Objects.isNull(s) || canBeNull) {
            try {
                return converter.convert(s);
            } catch (NumberFormatException e) {
                throw new UnableToConvertStringException();
            }
        }
        throw new UnableToConvertStringException();
    }

    public static <T> T readStringAsValidObject(IOManager io, StringConverter<T> converter, Predicate<T> predicate,
            String illegalValueMessage, String convertMessage, boolean canBeNull) {
        T t = readStringAsObject(io, converter, illegalValueMessage, canBeNull);
        while (!predicate.test(t)) {
            io.write(illegalValueMessage);
            t = readStringAsObject(io, converter, convertMessage, canBeNull);
        }
        return t;
    }

    public static <E extends Enum<E>> E readEnumValue(IOManager io, Class<E> enumClass) {
        String s;
        do {
            EnumUtil.printEnumValues(io, enumClass);
            s = readString(io);
            if (Objects.nonNull(s)) {
                s = s.toUpperCase();
            } else {
                io.write("Can't get value from empty string");
            }
        } while (!EnumUtil.isEnumValue(s, enumClass));
        return Enum.valueOf(enumClass, s);
    }
}
