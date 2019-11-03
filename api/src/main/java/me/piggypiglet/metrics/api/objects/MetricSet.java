package me.piggypiglet.metrics.api.objects;

import java.util.Map;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MetricSet {
    private final UUID uuid;
    private final String name;
    private final Map<String, Object> data;

    public MetricSet(UUID uuid, String name, Map<String, Object> data) {
        this.uuid = uuid;
        this.name = name;
        this.data = data;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
