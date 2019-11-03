package me.piggypiglet.metrics.api.bootstrap;

import me.piggypiglet.metrics.api.MetricsAPI;
import me.piggypiglet.metrics.api.data.ImmutableMetricsManager;
import me.piggypiglet.metrics.api.data.MutableMetricsManager;
import me.piggypiglet.metrics.api.tasks.UploadTask;

import java.util.concurrent.TimeUnit;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class APIBootstrap {
    private final MetricsAPIInfo config;

    public APIBootstrap(MetricsAPIInfo config) {
        this.config = config;
    }

    public MetricsAPI start() {
        final ImmutableMetricsManager immutableMetricsManager = new ImmutableMetricsManager(config);
        final MutableMetricsManager mutableMetricsManager = new MutableMetricsManager(config);

        if (!config.getToken().equals("null")) {
            final long ms = config.getSyncInterval();
            final UploadTask task = config.getUpload();

            task.populate(mutableMetricsManager);
            config.getExecutor().scheduleAtFixedRate(task, ms, ms, TimeUnit.MILLISECONDS);
        }

        return new MetricsAPI(config, immutableMetricsManager, mutableMetricsManager);
    }
}
