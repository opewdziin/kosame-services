package com.kosame.pewd.services.plugin.config;

import com.kosame.pewd.services.plugin.KosameStarter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class KosameConfig {

    private static final Map<String, KosameConfig> cache = new HashMap<>();
    private final KosameStarter starter;
    private final File file;
    private YamlConfiguration config;

    private KosameConfig(KosameStarter starter, String path, String name) {
        this.starter = starter;
        this.file = new File(path + "/" + name + ".yml");
        if (!file.exists()) {
            this.file.getParentFile().mkdirs();
            InputStream in = starter.getResource(name + ".yml");
            if (in != null) {
                starter.getFileUtils().copyFile(in, file);
            } else {
                try {
                    this.file.createNewFile();
                } catch (IOException ex) {
                    starter.getLogger().log(Level.SEVERE, "Um erro inesperado ocorreu ao criar o arquivo \"" + file.getName() + "\": ", ex);
                }
            }
        }

        try {
            this.config = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        } catch (IOException ex) {
            starter.getLogger().log(Level.SEVERE, "Um erro inesperado ocorreu ao criar a config \"" + file.getName() + "\": ", ex);
        }
    }

    public static KosameConfig getConfig(KosameStarter starter, String path, String name) {
        if (!cache.containsKey(path + "/" + name)) {
            cache.put(path + "/" + name, new KosameConfig(starter, path, name));
        }

        return cache.get(path + "/" + name);
    }

    public boolean createSection(String path) {
        this.config.createSection(path);
        return save();
    }

    public boolean set(String path, Object obj) {
        this.config.set(path, obj);
        return save();
    }

    public boolean contains(String path) {
        return this.config.contains(path);
    }

    public Object get(String path) {
        return this.config.get(path);
    }

    public int getInt(String path) {
        return this.config.getInt(path);
    }

    public int getInt(String path, int def) {
        return this.config.getInt(path, def);
    }

    public double getDouble(String path) {
        return this.config.getDouble(path);
    }

    public double getDouble(String path, double def) {
        return this.config.getDouble(path, def);
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def) {
        return this.config.getBoolean(path, def);
    }

    public List<String> getStringList(String path) {
        return this.config.getStringList(path);
    }

    public List<Integer> getIntegerList(String path) {
        return this.config.getIntegerList(path);
    }

    public Set<String> getKeys(boolean flag) {
        return this.config.getKeys(flag);
    }

    public ConfigurationSection getSection(String path) {
        return this.config.getConfigurationSection(path);
    }

    public void reload() {
        try {
            this.config = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        } catch (IOException ex) {
            starter.getLogger().log(Level.SEVERE, "Um erro inesperado ocorreu ao recarregar a config \"" + file.getName() + "\": ", ex);
        }
    }

    public boolean save() {
        try {
            this.config.save(this.file);
            return true;
        } catch (IOException ex) {
            starter.getLogger().log(Level.SEVERE, "Um erro inesperado ocorreu ao salvar a config \"" + file.getName() + "\": ", ex);
            return false;
        }
    }

    public File getFile() {
        return this.file;
    }

    public YamlConfiguration getRawConfig() {
        return this.config;
    }

}
