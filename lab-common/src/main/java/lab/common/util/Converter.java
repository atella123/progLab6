package lab.common.util;

@FunctionalInterface
public interface Converter<F, T> {
    T convert(F f);
}
