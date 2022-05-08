package com.kosame.pewd.services.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class KosameStarter extends JavaPlugin {

    public KosameStarter() {
    }

    public abstract void start();

    public abstract void load();

    public abstract void enable();

    public abstract void disable();

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

}
