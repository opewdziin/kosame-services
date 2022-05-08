package com.kosame.pewd.services.plugin.logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class KosameLog extends PluginLogger {

    private final Plugin plugin;
    private final String prefix;
    private final CommandSender sender;

    public KosameLog(Plugin plugin) {
        super(plugin);
        this.plugin = plugin;
        this.prefix = "[" + plugin.getName() + "] ";
        this.sender = Bukkit.getConsoleSender();
    }

    public KosameLog(KosameLog parent, String prefix) {
        super(parent.plugin);
        this.plugin = parent.plugin;
        this.prefix = parent.prefix + prefix;
        this.sender = Bukkit.getConsoleSender();
    }

    public void run(Level level, String method, Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception ex) {
            this.log(level, method.replace("${n}", plugin.getName()).replace("${v}", plugin.getDescription().getVersion()), ex);
        }
    }

    @Override
    public void log(LogRecord logRecord) {
        KosameLevel level = KosameLevel.fromName(logRecord.getLevel().getName());
        if (level == null) {
            return;
        }

        String message = logRecord.getMessage();
        if (message.equals("Default system encoding may have misread config.yml from plugin jar")) {
            return;
        }
        StringBuilder result = new StringBuilder(this.prefix + message);
        if (logRecord.getThrown() != null) {
            result.append("\n").append(logRecord.getThrown().getLocalizedMessage());
            for (StackTraceElement ste : logRecord.getThrown().getStackTrace()) {
                result.append("\n").append(ste.toString());
            }
        }

        this.sender.sendMessage(level.format(result.toString()));
    }

    public KosameLog getModule(String module) {
        return new KosameLog(this, module + ": ");
    }

    private enum KosameLevel {
        INFO("§a"),
        WARNING("§e"),
        SEVERE("§c");

        private final String color;

        KosameLevel(String color) {
            this.color = color;
        }

        public static KosameLevel fromName(String name) {
            for (KosameLevel level : values()) {
                if (level.name().equalsIgnoreCase(name)) {
                    return level;
                }
            }

            return null;
        }

        public String format(String message) {
            return this.color + message;
        }
    }
}
