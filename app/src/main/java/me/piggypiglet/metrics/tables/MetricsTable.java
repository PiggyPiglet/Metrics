package me.piggypiglet.metrics.tables;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import me.piggypiglet.framework.mysql.table.Table;
import me.piggypiglet.framework.utils.map.KeyValueSet;
import me.piggypiglet.metrics.objects.MetricSet;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MetricsTable extends Table<MetricSet> {
    private static final Gson GSON = new Gson();

    public MetricsTable() {
        super("data");
    }

    @SuppressWarnings("unchecked")
    @Override
    public MetricSet dataToType(KeyValueSet keyValueSet) {
        final Map<String, Object> map = keyValueSet.getMap();

        return new MetricSet(
                UUID.fromString((String) map.get("uuid")),
                (String) map.get("name"),
                ExpiringMap.builder()
                        .expirationPolicy(ExpirationPolicy.CREATED)
                        .expiration(11, TimeUnit.MINUTES)
                        .build(),
                GSON.fromJson((String) map.get("data"), LinkedTreeMap.class)
        );
    }

    @Override
    public KeyValueSet typeToData(MetricSet metricSet) {
        return KeyValueSet.builder()
                .key("uuid").value(metricSet.getId().toString())
                .key("name").value(metricSet.getName())
                .key("data").value(GSON.toJson(metricSet.getPersistentData()))
                .build();
    }

    @Override
    protected KeyValueSet saveLocation(MetricSet metricSet) {
        return KeyValueSet.builder()
                .key("uuid").value(metricSet.getId().toString())
                .build();
    }
}
