package lab.common.util;

import java.util.HashMap;

public final class ArgumentParser {

    private HashMap<Class<?>, StringConverter<?>> classParsers;

    public <T> void add(Class<T> clazz, StringConverter<T> stringConverter) {
        classParsers.put(clazz, stringConverter);
    }

    public void remove(Class<?> clazz) {
        classParsers.remove(clazz);
    }

    public Object convert(Class<?> clazz, String argument) {
        return classParsers.get(clazz).convert(argument);
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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ArgumentParser other = (ArgumentParser) obj;
        if (classParsers == null) {
            if (other.classParsers != null)
                return false;
        } else if (!classParsers.equals(other.classParsers))
            return false;
        return true;
    }

}
