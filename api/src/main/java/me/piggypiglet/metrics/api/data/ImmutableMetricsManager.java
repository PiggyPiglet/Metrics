package me.piggypiglet.metrics.api.data;

import com.google.gson.Gson;
import me.piggypiglet.metrics.api.bootstrap.MetricsAPIInfo;
import me.piggypiglet.metrics.api.json.JsonParser;
import me.piggypiglet.metrics.api.objects.MetricSet;
import me.piggypiglet.metrics.api.utils.HTTPUtils;

import java.util.*;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public class ImmutableMetricsManager {
    static final Gson GSON = new Gson();

    private final String name;

    private final String getUrl;
    private final String searchUrl;
    private final String existsUrl;

    public ImmutableMetricsManager(MetricsAPIInfo config) {
        final String url = config.getUrl();

        name = config.getName();
        getUrl = url + "?get=";
        searchUrl = url + "?search=";
        existsUrl = url + "?exists=";
    }

    public MetricSet get() {
        return get(name);
    }

    public MetricSet get(String name) {
        try {
            return serialise(new JsonParser(HTTPUtils.request(getUrl + name)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<MetricSet> getAll() {
        try {
            return new JsonParser("{\"results\": " + HTTPUtils.request(getUrl + "all") + "}").getJsonList("results").stream()
                    .map(this::serialise)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public MetricSet search(String query) {
        try {
            return serialise(Objects.requireNonNull(new JsonParser("{\"results\": " + HTTPUtils.request(searchUrl + query) + "}").getJsonList("results")).get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean exists() {
        return exists(name);
    }

    public boolean exists(String name) {
        try {
            return Boolean.parseBoolean(HTTPUtils.request(existsUrl + name));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private MetricSet serialise(JsonParser json) {
        return new MetricSet(
                UUID.fromString(Objects.requireNonNull(json.getString("id"))),
                json.getString("name"),
                (Map<String, Integer>) json.get("data")
        );
    }
}