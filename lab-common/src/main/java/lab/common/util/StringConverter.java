package lab.common.util;

@FunctionalInterface
public interface StringConverter<T> {
    T convert(String s);
}
