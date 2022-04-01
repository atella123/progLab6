package lab.common.json;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import lab.common.data.Person;

public final class PersonCollectionSerealizer implements JsonSerializer<Collection<Person>> {

    @Override
    public JsonElement serialize(Collection<Person> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray result = new JsonArray();

        for (Person i : src) {
            result.add(context.serialize(i));
        }

        return result;
    }

}
