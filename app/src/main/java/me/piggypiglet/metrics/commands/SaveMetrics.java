package me.piggypiglet.metrics.commands;

import me.piggypiglet.framework.commands.Command;
import me.piggypiglet.framework.mysql.manager.MySQLManagers;
import me.piggypiglet.framework.user.User;

import javax.inject.Inject;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class SaveMetrics extends Command {
    @Inject private MySQLManagers mySQLManagers;

    public SaveMetrics() {
        super("save");
        options.description("Save all metrics in memory to database.").usage("");
    }

    @Override
    protected boolean execute(User user, String[] args) {
        mySQLManagers.saveAll();
        user.sendMessage("Saved to db.");
        return true;
    }
}
