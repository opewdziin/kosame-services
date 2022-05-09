package com.kosame.pewd.services.plugin;

import com.kosame.pewd.services.plugin.config.FileUtils;
import com.kosame.pewd.services.plugin.config.KosameConfig;
import com.kosame.pewd.services.plugin.config.KosameWriter;
import com.kosame.pewd.services.plugin.logger.KosameLog;
import com.kosame.pewd.services.reflection.Accessors;
import com.kosame.pewd.services.reflection.acessors.FieldAccessor;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public abstract class KosameStarter extends JavaPlugin {

    private static final FieldAccessor<PluginLogger> LOGGER_FIELD_ACCESSOR = Accessors.getField(JavaPlugin.class, "logger", PluginLogger.class);
    private FileUtils fileUtils;

    public KosameStarter() {
        this.fileUtils = new FileUtils(this);
        LOGGER_FIELD_ACCESSOR.set(this, new KosameLog(this));
        this.start();
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

    public KosameConfig getConfig(String name) {
        return this.getConfig("", name);
    }

    public KosameConfig getConfig(String path, String name) {
        return KosameConfig.getConfig(this, "plugins/" + this.getName() + "/" + path, name);
    }

    public KosameWriter getWriter(File file) {
        return getWriter(file, "");
    }

    public KosameWriter getWriter(File file, String header) {
        return new KosameWriter((KosameLog) this.getLogger(), file, header);
    }

    public FileUtils getFileUtils() {
        return this.fileUtils;
    }
}
