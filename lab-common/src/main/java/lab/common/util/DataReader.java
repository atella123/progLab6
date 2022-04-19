package lab.common.util;

import java.util.Objects;
import java.util.function.Predicate;

import lab.common.exceptions.StringIsNullException;
import lab.common.exceptions.UnableToConvertValueException;
import lab.common.io.IOManager;

public final class DataReader {

    private DataReader() {
        throw new UnsupportedOperationException();
    }

    public static String readString(IOManager<String, String> io) {
        String s = io.read();
        if (Objects.isNull(s)) {
            throw new StringIsNullException();
        }
        if ("".equals(s)) {
            return null;
        }
        return s;
    }

    public static String readValidString(IOManager<String, String> io, Predicate<String> predicate,
            String illegalValueMessage) {
        String s = readString(io);
        while (!predicate.test(s)) {
            io.write(illegalValueMessage);
            s = readString(io);
        }
        return s;
    }

    public static <T> T readStringAsObject(IOManager<String, String> io, Converter<String, T> converter,
            String illegalValueMessage,
            boolean canBeNull) {
        T t;
        String s;
        while (true) {
            s = readString(io);
            try {
                t = convertString(s, converter, canBeNull);
                return t;
            } catch (UnableToConvertValueException e) {
                io.write(illegalValueMessage);
            }
        }
    }

    private static <T> T convertString(String s, Converter<String, T> converter, boolean canBeNull) {
        if (!Objects.isNull(s) || canBeNull) {
            try {
                return converter.convert(s);
            } catch (NumberFormatException e) {
                throw new UnableToConvertValueException();
            }
        }
        throw new UnableToConvertValueException();
    }

    public static <T> T readStringAsValidObject(IOManager<String, String> io, Converter<String, T> converter,
            Predicate<T> predicate,
            String illegalValueMessage, String convertMessage, boolean canBeNull) {
        T t = readStringAsObject(io, converter, illegalValueMessage, canBeNull);
        while (!predicate.test(t)) {
            io.write(illegalValueMessage);
            t = readStringAsObject(io, converter, convertMessage, canBeNull);
        }
        return t;
    }

    public static <E extends Enum<E>> E readEnumValue(IOManager<String, String> io, Class<E> enumClass) {
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

    public static <E extends Enum<E>> E readEnumValue(String s, Class<E> enumClass) {
        if (Objects.nonNull(s) && EnumUtil.isEnumValue(s.toUpperCase(), enumClass)) {
            return Enum.valueOf(enumClass, s.toUpperCase());
        }
        return null;
    }
}
