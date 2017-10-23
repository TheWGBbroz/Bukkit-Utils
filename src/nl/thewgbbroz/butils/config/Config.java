package nl.thewgbbroz.butils.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	private JavaPlugin plugin;
	private String name;
	private File file;
	private FileConfiguration config;
	
	public Config(JavaPlugin plugin, String name) {
		this.plugin = plugin;
		this.name = name;
		this.file = new File(plugin.getDataFolder(), name);
		
		saveDefault();
	}
	
	public void reload() {
		config = YamlConfiguration.loadConfiguration(file);
		
		try{
			Reader defConfigStream = new InputStreamReader(plugin.getResource(name), "UTF8");
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			config.setDefaults(defConfig);
		}catch(IOException e) {
			plugin.getLogger().severe("Could not reload config '" + name + "'!");
			e.printStackTrace();
		}
	}
	
	public FileConfiguration get() {
		if(config == null) reload();
		return config;
	}
	
	public void save() {
		if(config == null) return;
		
		try{
			config.save(file);
		}catch(IOException e) {
			plugin.getLogger().severe("Could not save config '" + name + "'!");
			e.printStackTrace();
		}
	}
	
	public void saveDefault() {
		if(!file.exists())
			plugin.saveResource(name, false);
	}
}
