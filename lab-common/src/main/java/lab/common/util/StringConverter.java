package lab.common.util;

@FunctionalInterface
public interface StringConverter<T> extends Converter<String, T> {
    T convert(String f);
}
