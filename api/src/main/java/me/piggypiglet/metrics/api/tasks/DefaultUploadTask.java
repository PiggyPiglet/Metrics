package me.piggypiglet.metrics.api.tasks;

import me.piggypiglet.metrics.api.json.JsonParser;
import me.piggypiglet.metrics.api.utils.HTTPUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class DefaultUploadTask extends UploadTask {
    private String location = null;

    @Override
    public void populate() {
        try {
            location = new JsonParser(HTTPUtils.request("http://ip-api.com/json/")).getString("country");
        } catch (Exception ignored) {}
    }

    @Override
    protected Object provideData() {
        Map<String, Integer> data = new HashMap<>();

        Stream.of(
                "java_version." + System.getProperty("java.version"),
                "os_name." + System.getProperty("os.name"),
                "os_arch." + System.getProperty("os.arch"),
                "os_version." + System.getProperty("os.version"),
                "cores." + Runtime.getRuntime().availableProcessors()
        ).forEach(s -> data.put(s, 1));

        if (location != null) {
            data.put("location." + location, 1);
        }

        return data;
    }
}