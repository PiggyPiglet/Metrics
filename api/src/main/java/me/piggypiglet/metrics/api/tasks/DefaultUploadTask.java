package me.piggypiglet.metrics.api.tasks;

import me.piggypiglet.metrics.api.json.JsonParser;
import me.piggypiglet.metrics.api.utils.HTTPUtils;

import java.util.HashMap;
import java.util.Map;

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
        } catch (Exception ignored) {
        }
    }

    @Override
    protected Object provideData() {
        Map<String, Object> data = new HashMap<>();

        data.put("java_version.", System.getProperty("java.version"));
        data.put("os_name.", System.getProperty("os.name"));
        data.put("os_arch.", System.getProperty("os.arch"));
        data.put("os_version.", System.getProperty("os.version"));
        data.put("cores.", Runtime.getRuntime().availableProcessors());

        if (location != null) {
            data.put("location." + location, 1);
        }

        return data;
    }
}