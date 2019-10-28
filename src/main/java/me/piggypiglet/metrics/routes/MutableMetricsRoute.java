package me.piggypiglet.metrics.routes;

import me.piggypiglet.framework.http.responses.routes.mixins.Authenticated;
import me.piggypiglet.framework.http.responses.routes.mixins.json.manager.Removable;
import me.piggypiglet.framework.http.responses.routes.objects.Header;
import me.piggypiglet.framework.http.responses.routes.types.json.JsonManagerRoute;
import me.piggypiglet.metrics.managers.MetricsManager;
import me.piggypiglet.metrics.objects.MetricSet;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Authenticated
@Removable
public final class MutableMetricsRoute extends JsonManagerRoute<MetricSet> {
    private final MetricsManager manager;

    @Inject
    public MutableMetricsRoute(MetricsManager manager) {
        super("mutable-metrics", manager);
        this.manager = manager;
    }

    @Override
    protected Object provide(Map<String, List<String>> params, List<Header> headers) {
        if (params.containsKey("add")) {
            final String name = params.get("add").get(0);
            final Map<String, String> mapHeaders = headers.stream().collect(Collectors.toMap(Header::getKey, Header::getValue));

            if (mapHeaders.containsKey("data")) {
                if (!manager.exists(name)) {
                    final Random random = ThreadLocalRandom.current();


//                   manager.add(new MetricSet());
                }
            }
        }

        return "null";
    }
}
