package com.kosame.pewd.services;

import com.kosame.pewd.services.plugin.KosameStarter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Service extends KosameStarter {

    private static Service service;
    public static final List<String> warnings = new ArrayList<>();
    public static HashMap<Player, Player> reply = new HashMap<>();

    @Override
    public void start() {
        service = this;
    }

    @Override
    public void load() {

    }

    @Override
    public void enable() {
        saveDefaultConfig();

        // remove the spawn-protection-size
        if (Bukkit.getSpawnRadius() != 0) {
            Bukkit.setSpawnRadius(0);
        }

        // remove the command /reload, /pl
        try {
            SimpleCommandMap simpleCommandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
            Field field = simpleCommandMap.getClass().getDeclaredField("knownCommands");
            field.setAccessible(true);
            Map<String, Command> knownCommands = (Map<String, Command>) field.get(simpleCommandMap);
            knownCommands.remove("rl");
            knownCommands.remove("reload");
            knownCommands.remove("bukkit:rl");
            knownCommands.remove("bukkit:reload");
        } catch (ReflectiveOperationException ex) {
            getLogger().log(Level.SEVERE, "Cannot remove reload command: ", ex);
        }
    }

    @Override
    public void disable() {
        getLogger().log(Level.SEVERE, "O plugin KosameService foi desligado.");
    }

    public static Service getInstance() {
        return service;
    }
}
