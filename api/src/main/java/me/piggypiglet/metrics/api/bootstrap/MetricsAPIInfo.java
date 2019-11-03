package me.piggypiglet.metrics.api.bootstrap;

import me.piggypiglet.metrics.api.MetricsAPI;
import me.piggypiglet.metrics.api.tasks.UploadTask;

import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MetricsAPIInfo {
    private final String name;
    private final String url;
    private final String token;
    private final UploadTask upload;
    private final ScheduledExecutorService executor;
    private final long syncInterval;

    private MetricsAPIInfo(String name, String url, String token, Object upload, Object executor, long syncInterval) {
        this.name = name;
        this.url = url;
        this.token = token;
        this.upload = (UploadTask) upload;
        this.executor = (ScheduledExecutorService) executor;
        this.syncInterval = syncInterval;
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

    public ScheduledExecutorService getExecutor() {
        return executor;
    }

    public long getSyncInterval() {
        return syncInterval;
    }

    public MetricsAPI init() {
        return new APIBootstrap(this).start();
    }

    public static class Builder {
        private String name = "d-name";
        private String url = "d-url";
        private String token = "null";
        private Object upload = "d-upload";
        private Object executor = "d-executor";
        private long syncInterval = 600000L;

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

        public Builder executor(ScheduledExecutorService executor) {
            this.executor = executor;
            return this;
        }

        public Builder syncInterval(long ms) {
            syncInterval = ms;
            return this;
        }

        public MetricsAPIInfo build() {
            String unsetVars = Stream.of(name, url, upload, executor).filter(o -> {
                try {
                    return ((String) o).startsWith("d-");
                } catch (Exception e) {
                    return false;
                }
            }).map(String::valueOf).map(s -> s.replaceFirst("d-", "")).collect(Collectors.joining(", "));

            if (!unsetVars.isEmpty()) throw new NullPointerException("These required vars weren't set in your MetricsAPI Builder: " + unsetVars);

            return new MetricsAPIInfo(name, url, token, upload, executor, syncInterval);
        }
    }
}