package lab.common.json;

import java.time.LocalDate;
import java.util.HashSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lab.common.data.Person;

public final class DefalutGsonCreator {

    private DefalutGsonCreator() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(HashSet.class,
                        new PersonCollectionSerealizer())
                .registerTypeAdapter(HashSet.class,
                        new PersonCollectionDeserializer())
                .registerTypeAdapter(Person.class, new PersonSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(Person.class, new PersonDeserializer()).create();
    }
}
