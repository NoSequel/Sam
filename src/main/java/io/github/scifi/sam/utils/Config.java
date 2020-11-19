package io.github.scifi.sam.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@Getter @Setter
public class Config extends YamlConfiguration {

    private String name, directory;

    private File file;

    public Config(String name, JavaPlugin plugin, String directory) {
        this.name = name;
        this.directory = directory;
        this.file = new File(directory, name + ".yml");

        if (!this.file.exists()) {
            plugin.saveResource(name + ".yml", true);
        }

        load();
        save();


    }

    public void load() {
        try {
            load(this.file);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public void save() {
        try {
            save(this.file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
