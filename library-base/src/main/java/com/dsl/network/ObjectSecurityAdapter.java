package com.dsl.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 由于更深一层的object无法自动解析，所以更深一层的错误处理需要使用该适配器
 *
 * @author dsl-abben
 * on 2020/09/02.
 */
public class ObjectSecurityAdapter<T> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        T temp = null;
        if (json.isJsonObject()) {
            Gson mGson = new GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .registerTypeAdapter(Long.class, new LongSecurityAdapter())
                    .registerTypeAdapter(Integer.class, new IntSecurityAdapter())
                    .registerTypeAdapter(Float.class, new FloatSecurityAdapter())
                    .registerTypeAdapter(List.class, new ArraySecurityAdapter())
                    .create();
            temp = mGson.fromJson(json, typeOfT);
            return temp;
        }
        return temp;
    }

}
