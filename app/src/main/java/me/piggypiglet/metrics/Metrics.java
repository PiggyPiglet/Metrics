package me.piggypiglet.metrics;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.http.HTTPAddon;
import me.piggypiglet.framework.mysql.MySQLAddon;
import me.piggypiglet.framework.utils.annotations.files.Config;
import me.piggypiglet.framework.utils.map.KeyValueSet;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Metrics {
    private Metrics() {
        Framework.builder()
                .main(this)
                .pckg("me.piggypiglet.metrics")
                .commandPrefix("!")
                .fileDir(".")
                .file(true, "config", "/config.json", "config.json", Config.class)
                .config(HTTPAddon.class, "config", KeyValueSet.builder()
                        .key("host").value("http.host")
                        .key("port").value("http.port")
                        .key("ssl.enabled").value("http.ssl.enabled")
                        .key("ssl.path").value("http.ssl.path")
                        .key("ssl.password").value("http.ssl.password")
                        .key("basic-authentication.enabled").value("http.basic-authentication.enabled")
                        .key("basic-authentication.token").value("http.basic-authentication.token")
                        .build().getMap())
                .config(MySQLAddon.class, "config", KeyValueSet.builder()
                        .key("user").value("mysql.user")
                        .key("password").value("mysql.password")
                        .key("db").value("mysql.db")
                        .key("host").value("mysql.host")
                        .key("save-interval").value("mysql.save-interval")
                        .key("tables").value("mysql.tables")
                        .build().getMap())
                .build()
                .init();
    }

    public static void main(String[] args) {
        new Metrics();
    }
}
