package lab.util;

@FunctionalInterface
public interface StringConverter<T> {
    T convert(String s);
}
