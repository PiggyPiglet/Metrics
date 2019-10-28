package me.piggypiglet.metrics.objects;

import me.piggypiglet.framework.utils.SearchUtils;

import java.util.Map;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MetricSet implements SearchUtils.Searchable {
    private final UUID id;
    private final String name;
    private final Map<String, Object> data;

    public MetricSet(UUID id, String name, Map<String, Object> data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
