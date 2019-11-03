package me.piggypiglet.metrics.api.tasks;

import me.piggypiglet.metrics.api.data.MutableMetricsManager;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class UploadTask implements Runnable {
    private MutableMetricsManager mutableMetricsManager;

    protected void populate() {}

    public final void populate(MutableMetricsManager manager) {
        mutableMetricsManager = manager;
        populate();
    }

    protected abstract Object provideData();

    @Override
    public final void run() {
        mutableMetricsManager.update(provideData());
    }
}
