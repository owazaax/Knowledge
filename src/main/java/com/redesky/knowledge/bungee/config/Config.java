package com.redesky.knowledge.bungee.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {

    private Plugin plugin;
    private String name;
    private File file;
    private Configuration config;

    public Config(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.file = new File(plugin.getDataFolder(), name);
        if (existsConfig()) {
            reloadConfig();
        }
    }

    public Plugin getPlugin() {
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

    public Configuration getConfig() {
        return config;
    }

    public void reloadConfig() {
        file = new File(plugin.getDataFolder(), name);

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        reloadConfig();
    }

    public void saveDefaultConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream(name)) {
                Files.copy(in, file.toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        reloadConfig();
    }

    public void deleteConfig() {
        file.delete();
    }

    public boolean contains(String path) {
        return (config.get(path) != null);
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