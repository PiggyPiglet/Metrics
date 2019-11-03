package me.piggypiglet.metrics.commands;

import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.user.User;
import me.piggypiglet.metrics.managers.MetricsManager;

import javax.inject.Inject;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class RemoveMetric extends Command {
    @Inject private MetricsManager metricsManager;

    public RemoveMetric() {
        super("remove");
        options.usage("<name>").description("Remove a metric.");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        if (args.length > 0) {
            if (metricsManager.exists(args[0])) {
                metricsManager.remove(metricsManager.get(args[0]));
                user.sendMessage("Removed %s.", args[0]);
            } else {
                user.sendMessage("No metric called %s exists.", args[0]);
            }

            return true;
        }

        return false;
    }
}
