package me.piggypiglet.metrics.commands;

import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.metrics.managers.MetricsManager;

import javax.inject.Inject;
import java.util.HashMap;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class AddMetric extends Command {
    @Inject private MetricsManager metricsManager;

    public AddMetric() {
        super("add");
        options.description("Add a metric").usage("<name>");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        if (args.length > 0) {
            if (metricsManager.exists(args[0])) {
                user.sendMessage("A metric already exists with the name %s", args[0]);
            } else {
                metricsManager.add(args[0], new HashMap<>());
                user.sendMessage("Created a metric by the name of %s", args[0]);
            }

            return true;
        }

        return false;
    }
}
