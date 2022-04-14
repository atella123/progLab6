package lab.common.util;

import java.util.HashMap;

import lab.common.exceptions.NoParserAvailableException;

public class ArgumentParser<T> {

    private HashMap<Class<?>, Converter<T, ?>> classParsers;

    public ArgumentParser() {
        classParsers = new HashMap<>();
    }

    public <A> void add(Class<A> clazz, Converter<T, A> converter) {
        classParsers.put(clazz, converter);
    }

    public void remove(Class<?> clazz) {
        classParsers.remove(clazz);
    }

    public Object convert(Class<?> clazz, T argument) {
        if (clazz.isInstance(argument)) {
            return argument;
        }
        if (!classParsers.containsKey(clazz)) {
            throw new NoParserAvailableException("For " + clazz);
        }
        return classParsers.get(clazz).convert(argument);
    }

    public boolean canParse(Class<?> clazz) {
        return classParsers.containsKey(clazz);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((classParsers == null) ? 0 : classParsers.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ArgumentParser<?> other = (ArgumentParser<?>) obj;
        if (classParsers == null) {
            return other.classParsers == null;
        }
        return classParsers.equals(other.classParsers);
    }

}
