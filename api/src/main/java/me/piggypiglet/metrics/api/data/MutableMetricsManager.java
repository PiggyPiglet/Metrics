package me.piggypiglet.metrics.api.data;

import me.piggypiglet.metrics.api.bootstrap.MetricsAPIInfo;
import me.piggypiglet.metrics.api.utils.HTTPUtils;

import java.util.HashMap;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MutableMetricsManager extends ImmutableMetricsManager {
    private final String token;
    private final String saveUrl;

    public MutableMetricsManager(MetricsAPIInfo config) {
        super(config);
        token = config.getToken();
        saveUrl = config.getUrl() + "?update=" + config.getName();
    }

    public void update(Object data) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("auth", token);
        headers.put("data", GSON.toJson(data));

        try {
            HTTPUtils.request(saveUrl, headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
