package lab.common.data;

import java.time.LocalDate;
import java.util.Objects;

import lab.common.exceptions.IllegalFieldValueException;

public class Person implements Comparable<Person> {

    private Integer id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого
                        // поля должно быть уникальным, Значение этого поля должно генерироваться
                        // автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private java.time.LocalDate creationDate; // Поле не может быть null, Значение этого поля должно
                                              // генерироваться
                                              // автоматически
    private int height; // Значение поля должно быть больше 0
    private String passportID; // Длина строки должна быть не меньше 10, Поле может быть null
    private Color eyeColor; // Поле может быть null
    private Country nationality; // Поле не может быть null
    private Location location; // Поле не может быть null

    public Person(String name, Coordinates coordinates, int height, String passportID, Color eyeColor,
            Country nationality, Location location) {
        setID(0);
        setName(name);
        setCoordinates(coordinates);
        setCreationDate();
        setHeight(height);
        setPassportID(passportID);
        setEyeColor(eyeColor);
        setNationality(nationality);
        setLocation(location);
    }

    public Integer getID() {
        return id;
    }

    public void setID(Integer newID) {
        this.id = newID;
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    private void setCoordinates(Coordinates coordinates) {
        if (!Validator.isValidCoordinates(coordinates)) {
            throw new IllegalFieldValueException();
        }
        this.coordinates = coordinates;
    }

    public java.time.LocalDate getCreationDate() {
        return creationDate;
    }

    private void setCreationDate() {
        creationDate = LocalDate.now();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getHeight() {
        return height;
    }

    private void setHeight(int height) {
        if (!Validator.isValidHeight(height)) {
            throw new IllegalFieldValueException();
        }
        this.height = height;
    }

    public String getPassportID() {
        return passportID;
    }

    private void setPassportID(String passportID) {
        if (!Validator.isValidPassportID(passportID)) {
            throw new IllegalFieldValueException();
        }
        this.passportID = passportID;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    private void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Country getNationality() {
        return nationality;
    }

    private void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    private void setLocation(Location location) {
        if (!Validator.isValidLocation(location)) {
            throw new IllegalFieldValueException();
        }
        this.location = location;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coordinates == null) ? 0 : coordinates.hashCode());
        result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
        result = prime * result + ((eyeColor == null) ? 0 : eyeColor.hashCode());
        result = prime * result + height;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((nationality == null) ? 0 : nationality.hashCode());
        result = prime * result + ((passportID == null) ? 0 : passportID.hashCode());
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
        Person other = (Person) obj;
        if (!coordinates.equals(other.coordinates)) {
            return false;
        }
        if (!creationDate.equals(other.creationDate)) {
            return false;
        }
        if (eyeColor != other.eyeColor) {
            return false;
        }
        if (height != other.height) {
            return false;
        }
        if (!id.equals(other.id)) {
            return false;
        }
        if (!location.equals(other.location)) {
            return false;
        }
        if (!name.equals(other.name)) {
            return false;
        }
        if (nationality != other.nationality) {
            return false;
        }
        return !passportID.equals(other.passportID);
    }

    @Override
    public String toString() {
        return "Person [coordinates=" + coordinates + ", creationDate=" + creationDate + ", eyeColor=" + eyeColor
                + ", height=" + height + ", id=" + id + ", location=" + location + ", name=" + name + ", nationality="
                + nationality + ", passportID=" + passportID + "]";
    }

    @Override
    public int compareTo(Person person) {
        return this.name.length() - person.name.length();
    }

    public static final class Validator {
        private static final int MIN_H = 10;

        private Validator() {
        }

        public static boolean isValidName(String name) {
            if (Objects.nonNull(name)) {
                return !name.isEmpty();
            }
            return false;
        }

        public static boolean isValidCoordinates(Coordinates coordinates) {
            return Objects.nonNull(coordinates);
        }

        public static boolean isValidHeight(int height) {
            return height > 0;
        }

        public static boolean isValidPassportID(String passportID) {
            if (Objects.nonNull(passportID)) {
                return passportID.length() >= MIN_H;
            }
            return false;
        }

        public static boolean isValidLocation(Location location) {
            return Objects.nonNull(location);
        }
    }
}
