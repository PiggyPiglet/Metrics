package me.piggypiglet.metrics.routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.piggypiglet.framework.http.responses.routes.objects.Header;
import me.piggypiglet.framework.http.responses.routes.types.json.JsonManagerRoute;
import me.piggypiglet.metrics.managers.MetricsManager;
import me.piggypiglet.metrics.objects.MetricSet;
import me.piggypiglet.metrics.objects.MetricSetSerialiser;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MetricsRoute extends JsonManagerRoute<MetricSet> {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(MetricSet.class, new MetricSetSerialiser())
            .create();

    @Inject
    public MetricsRoute(MetricsManager metricsManager) {
        super("metrics", metricsManager, GSON);
    }

    @Override
    protected Object provide(Map<String, List<String>> map, List<Header> headers) {
        return "null";
    }
}
