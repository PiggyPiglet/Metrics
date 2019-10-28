package me.piggypiglet.metrics.tables;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import me.piggypiglet.framework.mysql.table.Table;
import me.piggypiglet.framework.utils.map.KeyValueSet;
import me.piggypiglet.metrics.objects.MetricSet;

import java.util.Map;
import java.util.UUID;

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
                UUID.nameUUIDFromBytes(((String) map.get("uuid")).getBytes()),
                (String) map.get("name"),
                GSON.fromJson((String) map.get("data"), LinkedTreeMap.class)
        );
    }

    @Override
    public KeyValueSet typeToData(MetricSet metricSet) {
        return KeyValueSet.builder()
                .key("uuid").value(metricSet.getId().toString())
                .key("name").value(metricSet.getName())
                .key("data").value(GSON.toJson(metricSet.getData()))
                .build();
    }

    @Override
    protected KeyValueSet saveLocation(MetricSet metricSet) {
        return KeyValueSet.builder()
                .key("uuid").value(metricSet.getId().toString())
                .build();
    }
}
