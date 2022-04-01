package lab.common.json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import lab.common.data.Person;

public final class PersonSerializer implements JsonSerializer<Person> {

    @Override
    public JsonElement serialize(Person src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("id", src.getID());
        result.addProperty("name", src.getName());
        result.add("coordinates", context.serialize(src.getCoordinates()));
        result.addProperty("creationDate", src.getCreationDate().toString());
        result.addProperty("height", src.getHeight());
        result.addProperty("passportID", src.getPassportID());
        result.addProperty("eyeColor", src.getEyeColor().toString());
        result.addProperty("nationality", src.getNationality().toString());
        result.add("location", context.serialize(src.getLocation()));

        return result;
    }

}
