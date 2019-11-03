package me.piggypiglet.metrics.api.bootstrap;

import me.piggypiglet.metrics.api.MetricsAPI;
import me.piggypiglet.metrics.api.tasks.DefaultUploadTask;
import me.piggypiglet.metrics.api.tasks.UploadTask;
import me.piggypiglet.metrics.api.utils.BuilderUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MetricsAPIInfo {
    private final String name;
    private final String url;
    private final String token;
    private final UploadTask upload;
    private final ScheduledExecutorService scheduler;

    private MetricsAPIInfo(String name, String url, String token, UploadTask upload, ScheduledExecutorService scheduler) {
        this.name = name;
        this.url = url;
        this.token = token;
        this.upload = upload;
        this.scheduler = scheduler;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }

    public UploadTask getUpload() {
        return upload;
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public MetricsAPI init() {
        return new APIBootstrap(this).start();
    }

    public static class Builder {
        private String name = "d-name";
        private String url = "d-url";
        private String token = "null";
        private UploadTask upload = new DefaultUploadTask();
        private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        private Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder url(String url) {
            url = url.toLowerCase();
            url = url.endsWith("/metrics") ? url : url + "/metrics";

            this.url = url;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder upload(UploadTask task) {
            this.upload = task;
            return this;
        }

        public Builder scheduler(ScheduledExecutorService scheduler) {
            this.scheduler = scheduler;
            return this;
        }

        public MetricsAPIInfo build() {
            BuilderUtils.checkVars("MetricsAPI", name, url);

            return new MetricsAPIInfo(name, url, token, upload, scheduler);
        }
    }
}