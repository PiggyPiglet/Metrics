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
    private final Map<String, Integer> liveData;
    private final Map<String, Integer> persistentData;

    public MetricSet(UUID uuid, String name, Map<String, Integer> liveData, Map<String, Integer> persistentData) {
        this.uuid = uuid;
        this.name = name;
        this.liveData = liveData;
        this.persistentData = persistentData;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getLiveData() {
        return liveData;
    }

    public Map<String, Integer> getPersistentData() {
        return persistentData;
    }
}
