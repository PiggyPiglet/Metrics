package me.piggypiglet.metrics.routes;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import me.piggypiglet.framework.http.responses.routes.mixins.Authenticated;
import me.piggypiglet.framework.http.responses.routes.mixins.json.manager.Removable;
import me.piggypiglet.framework.http.responses.routes.objects.Header;
import me.piggypiglet.framework.http.responses.routes.types.json.JsonManagerRoute;
import me.piggypiglet.metrics.managers.MetricsManager;
import me.piggypiglet.metrics.objects.MetricSet;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Authenticated
@Removable
public final class MutableMetricsRoute extends JsonManagerRoute<MetricSet> {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSS");
    private static final Gson GSON = new Gson();

    private final MetricsManager manager;

    @Inject
    public MutableMetricsRoute(MetricsManager manager) {
        super("mutable-metrics", manager);
        this.manager = manager;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object provide(Map<String, List<String>> params, List<Header> headers) {
        if (params.containsKey("add")) {
            final String name = params.get("add").get(0);
            final Map<String, String> mapHeaders = headers.stream().collect(Collectors.toMap(Header::getKey, Header::getValue));

            if (mapHeaders.containsKey("data")) {
                if (!manager.exists(name)) {
                    manager.add(new MetricSet(
                            UUID.nameUUIDFromBytes(FORMAT.format(LocalDateTime.now()).getBytes()),
                            mapHeaders.get("name"),
                            GSON.fromJson(mapHeaders.get("data"), LinkedTreeMap.class))
                    );

                    return true;
                }
            }
        }

        return "null";
    }
}
