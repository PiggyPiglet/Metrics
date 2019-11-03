package me.piggypiglet.metrics.routes;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import me.piggypiglet.framework.file.framework.FileConfiguration;
import me.piggypiglet.framework.http.responses.routes.mixins.Authenticated;
import me.piggypiglet.framework.http.responses.routes.mixins.json.manager.Removable;
import me.piggypiglet.framework.http.responses.routes.objects.Header;
import me.piggypiglet.framework.http.responses.routes.types.json.JsonManagerRoute;
import me.piggypiglet.framework.utils.annotations.files.Config;
import me.piggypiglet.metrics.managers.MetricsManager;
import me.piggypiglet.metrics.objects.MetricSet;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Authenticated
@Removable
public final class MutableMetricsRoute extends JsonManagerRoute<MetricSet> {
    private static final Gson GSON = new Gson();

    private final MetricsManager manager;
    private final boolean canAdd;

    @Inject
    public MutableMetricsRoute(MetricsManager manager, @Config FileConfiguration config) {
        super("mutable-metrics", manager, MetricsRoute.GSON);
        this.manager = manager;
        canAdd = config.getBoolean("addition-via-route");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object provide(Map<String, List<String>> params, List<Header> headers) {
        if (params.containsKey("update")) {
            final String name = params.get("update").get(0);
            final Map<String, String> mapHeaders = headers.stream().collect(Collectors.toMap(Header::getKey, Header::getValue));

            if (mapHeaders.containsKey("data")) {
                final Map<String, Object> data = GSON.fromJson(mapHeaders.get("data"), LinkedTreeMap.class);

                if (!manager.exists(name)) {
                    if (canAdd) {
                        manager.create(mapHeaders.get("name"), requestIp, data);
                        return true;
                    } else {
                        return "Addition via route is not enabled.";
                    }
                } else {
                    final MetricSet result = manager.search(name).get(0);

                    if (!result.getName().equalsIgnoreCase(name)) {
                        return "Did you mean " + result.getName() + "?";
                    }

                    Stream.of(
                            result.getLiveData(),
                            result.getPersistentData()
                    ).forEach(m -> m.get(requestIp).putAll(data));

                    return true;
                }
            }
        }

        return "null";
    }
}
