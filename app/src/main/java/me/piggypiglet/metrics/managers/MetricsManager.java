package me.piggypiglet.metrics.managers;

import com.google.inject.Singleton;
import me.piggypiglet.framework.managers.objects.KeyTypeInfo;
import me.piggypiglet.framework.mysql.manager.MySQLManager;
import me.piggypiglet.metrics.objects.MetricSet;
import me.piggypiglet.metrics.tables.MetricsTable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class MetricsManager extends MySQLManager<MetricSet> {
    private static final MetricSet DEF = new MetricSet(UUID.nameUUIDFromBytes("null".getBytes()), "null", new HashMap<>());
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSS");

    private final Map<UUID, MetricSet> metrics = new ConcurrentHashMap<>();

    public MetricsManager() {
        super(new MetricsTable());
    }

    @Override
    protected KeyTypeInfo configure(KeyTypeInfo.Builder builder) {
        return builder
                .key(UUID.class)
                    .map(metrics, DEF)
                .key(String.class)
                    .list(items, (s, m) -> m.getName().equalsIgnoreCase(s), DEF)
                .build();
    }

    @Override
    protected void insert(MetricSet metricSet) {
        metrics.put(metricSet.getId(), metricSet);
    }

    public void add(String name, Map<String, Object> data) {
        add(new MetricSet(
                UUID.nameUUIDFromBytes(FORMAT.format(LocalDateTime.now()).getBytes()),
                name,
                data
        ));
    }

    @Override
    protected void delete(MetricSet metricSet) {
        metrics.remove(metricSet.getId());
    }
}
