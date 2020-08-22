package com.minilook.minilook.data.model.base;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class BooleanDeserializer implements JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        int code = json.getAsInt();
        if (code == 0) {
            return false;
        } else if (code == 1) {
            return true;
        } else {
            return null;
        }
    }
}
