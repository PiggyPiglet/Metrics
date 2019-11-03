package me.piggypiglet.metrics.routes;

import me.piggypiglet.framework.http.responses.routes.objects.Header;
import me.piggypiglet.framework.http.responses.routes.types.json.JsonManagerRoute;
import me.piggypiglet.metrics.managers.MetricsManager;
import me.piggypiglet.metrics.objects.MetricSet;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MetricsRoute extends JsonManagerRoute<MetricSet> {
    @Inject
    public MetricsRoute(MetricsManager metricsManager) {
        super("metrics", metricsManager);
    }

    @Override
    protected Object provide(Map<String, List<String>> map, List<Header> headers) {
        return "null";
    }
}
