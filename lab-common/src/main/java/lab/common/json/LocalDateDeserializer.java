package lab.common.json;

import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String[] jsonString = json.getAsString().split("-");
        return LocalDate.of(Integer.parseInt(jsonString[0]), Integer.parseInt(jsonString[1]),
                Integer.parseInt(jsonString[2]));
    }

}
