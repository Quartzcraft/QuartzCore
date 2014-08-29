package uk.co.quartzcraft.core.systems.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class QCConfig {
    public Plugin plugin;
    public FileConfiguration config;

    public QCConfig(Plugin pl) {
        this.plugin = pl;
        this.config = this.plugin.getConfig();
    }

    public Object get(String path) {
        return this.config.get(path);
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public int getInt(String path) {
        return this.config.getInt(path);
    }
}
