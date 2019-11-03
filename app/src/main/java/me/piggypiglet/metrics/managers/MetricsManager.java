package me.piggypiglet.metrics.managers;

import com.google.inject.Singleton;
import me.piggypiglet.framework.managers.objects.KeyTypeInfo;
import me.piggypiglet.framework.mysql.manager.MySQLManager;
import me.piggypiglet.metrics.objects.MetricSet;
import me.piggypiglet.metrics.tables.MetricsTable;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class MetricsManager extends MySQLManager<MetricSet> {
    private static final MetricSet DEF = new MetricSet(UUID.nameUUIDFromBytes("null".getBytes()), "null", new HashMap<>(), new HashMap<>());
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

    public void create(String name, String ip, Map<String, Object> data) {
        final Map<String, Map<String, Object>> map = ExpiringMap.builder()
                .expiration(11, TimeUnit.MINUTES)
                .expirationPolicy(ExpirationPolicy.CREATED)
                .build();
        map.put(ip, data);

        add(new MetricSet(
                UUID.nameUUIDFromBytes(FORMAT.format(LocalDateTime.now()).getBytes()),
                name,
                map,
                new HashMap<>(map)
        ));
    }

    @Override
    protected void delete(MetricSet metricSet) {
        metrics.remove(metricSet.getId());
    }
}
