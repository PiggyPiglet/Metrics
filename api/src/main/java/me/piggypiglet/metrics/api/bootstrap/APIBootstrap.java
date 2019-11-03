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
        MutableMetricsManager mutableMetricsManager = null;

        if (!config.getToken().equals("null")) {
            mutableMetricsManager = new MutableMetricsManager(config);

            final long ms = 600000L;
            final UploadTask task = config.getUpload();

            task.populate(mutableMetricsManager);
            config.getScheduler().scheduleAtFixedRate(task, ms, ms, TimeUnit.MILLISECONDS);
        }

        return new MetricsAPI(config, immutableMetricsManager, mutableMetricsManager);
    }
}
