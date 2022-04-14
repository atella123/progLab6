package lab.common.io;

@FunctionalInterface
public interface Writter<T> {
    void write(T message);
}
