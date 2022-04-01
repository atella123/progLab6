package lab.common.json;

import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import lab.common.data.Color;
import lab.common.data.Coordinates;
import lab.common.data.Country;
import lab.common.data.Location;
import lab.common.data.Person;

public final class PersonDeserializer implements JsonDeserializer<Person> {

    @Override
    public Person deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Person p = new Person(
                jsonObject.get("name").getAsString(),
                context.deserialize(jsonObject.get("coordinates"), Coordinates.class),
                jsonObject.get("height").getAsInt(),
                jsonObject.get("passportID").getAsString(),
                Color.valueOf(jsonObject.get("eyeColor").getAsString()),
                Country.valueOf(jsonObject.get("nationality").getAsString()),
                context.deserialize(jsonObject.get("location"), Location.class));
        p.setID(jsonObject.get("id").getAsInt());
        p.setCreationDate(context.deserialize(jsonObject.get("creationDate"), LocalDate.class));
        return p;
    }

}
