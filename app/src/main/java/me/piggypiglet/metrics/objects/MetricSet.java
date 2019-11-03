package me.piggypiglet.metrics.objects;

import com.google.gson.annotations.Expose;
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
    @Expose(serialize = false) private final Map<String, Map<String, Object>> liveData;
    @Expose(serialize = false) private final Map<String, Map<String, Object>> persistentData;

    public MetricSet(UUID id, String name, Map<String, Map<String, Object>> liveData, Map<String, Map<String, Object>> persistentData) {
        this.id = id;
        this.name = name;
        this.liveData = liveData;
        this.persistentData = persistentData;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public Map<String, Map<String, Object>> getLiveData() {
        return liveData;
    }

    public Map<String, Map<String, Object>> getPersistentData() {
        return persistentData;
    }
}
