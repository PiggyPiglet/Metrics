package me.piggypiglet.metrics.api;

import me.piggypiglet.metrics.api.bootstrap.MetricsAPIInfo;
import me.piggypiglet.metrics.api.data.ImmutableMetricsManager;
import me.piggypiglet.metrics.api.data.MutableMetricsManager;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MetricsAPI {
    private final MetricsAPIInfo config;
    private final ImmutableMetricsManager immutableMetricsManager;
    private final MutableMetricsManager mutableMetricsManager;

    public MetricsAPI(MetricsAPIInfo config, ImmutableMetricsManager immutableMetricsManager, MutableMetricsManager mutableMetricsManager) {
        this.config = config;
        this.immutableMetricsManager = immutableMetricsManager;
        this.mutableMetricsManager = mutableMetricsManager;
    }

    public static MetricsAPIInfo.Builder builder() {
        return MetricsAPIInfo.builder();
    }

    public MetricsAPIInfo getConfig() {
        return config;
    }

    public ImmutableMetricsManager getImmutableMetricsManager() {
        return immutableMetricsManager;
    }

    public MutableMetricsManager getMutableMetricsManager() {
        if (mutableMetricsManager == null) {
            throw new UnsupportedOperationException("A mutable metrics manager isn't present due to an authentication token not being provided in the API builder.");
        }

        return mutableMetricsManager;
    }
}
