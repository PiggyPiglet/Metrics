package me.piggypiglet.metrics.objects;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MetricSetSerialiser implements JsonSerializer<MetricSet> {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public JsonElement serialize(MetricSet src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject metricSet = new JsonObject();

        metricSet.addProperty("id", src.getId().toString());
        metricSet.addProperty("name", src.getName());
        metricSet.addProperty("live_data", GSON.toJson(flatten(src.getLiveData())));
        metricSet.addProperty("persistent_data", GSON.toJson(flatten(src.getPersistentData())));

        return metricSet;
    }

    private Map<String, Integer> flatten(Map<String, Map<String, Object>> map) {
        final Map<String, Integer> data = new HashMap<>();

        map.forEach((ip, m) -> m.forEach((k, v) -> {
            String key = k + "." + v;

            if (data.containsKey(key)) {
                data.put(key, data.get(key) + 1);
            } else {
                data.put(key, 1);
            }
        }));

        return data;
    }
}
