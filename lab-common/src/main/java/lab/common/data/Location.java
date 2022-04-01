package lab.common.data;

import java.util.Objects;

import lab.common.exceptions.IllegalFieldValueException;

public class Location {
    private Float x; // Поле не может быть null
    private long y;
    private String name; // Поле не может быть null

    public Location(Float x, long y, String name) {
        setX(x);
        setY(y);
        setName(name);
    }

    public Float getX() {
        return x;
    }

    private void setX(Float x) {
        if (!Validator.isValidX(x)) {
            throw new IllegalFieldValueException();
        }
        this.x = x;
    }

    public long getY() {
        return y;
    }

    private void setY(long y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (!Validator.isValidName(name)) {
            throw new IllegalFieldValueException();
        }
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + (int) (y ^ (y >>> prime));
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
        Location other = (Location) obj;
        if (!name.equals(other.name)) {
            return false;
        }
        if (!x.equals(other.x)) {
            return false;
        }
        return y != other.y;
    }

    @Override
    public String toString() {
        return "Location [name=" + name + ", x=" + x + ", y=" + y + "]";
    }

    public static final class Validator {
        private Validator() {
        }

        public static boolean isValidX(Float x) {
            return Objects.nonNull(x);
        }

        public static boolean isValidName(String s) {
            return Objects.nonNull(s);
        }
    }
}
