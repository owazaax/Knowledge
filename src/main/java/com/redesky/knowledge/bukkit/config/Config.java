package com.redesky.knowledge.bukkit.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {

    private JavaPlugin plugin;
    private String name;
    private File file;
    private YamlConfiguration config;

    public Config(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        reloadConfig();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public boolean existsConfig() {
        return file.exists();
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void reloadConfig() {
        file = new File(plugin.getDataFolder(), name);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefault() {
        config.options().copyDefaults(true);
    }

    public void saveDefaultConfig() {
        plugin.saveResource(name, false);
    }

    public void deleteConfig() {
        file.delete();
    }

    public boolean contains(String path) {
        return config.contains(path);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public double getDouble(String path) {
        return config.getDouble(path);
    }

    public List<?> getList(String path) {
        return config.getList(path);
    }

    public void set(String path, Object value) {
        config.set(path, value);
    }

}