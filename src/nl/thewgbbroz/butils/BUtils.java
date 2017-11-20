package nl.thewgbbroz.butils;

import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import nl.thewgbbroz.butils.config.Config;
import nl.thewgbbroz.butils.customblocks.CustomBlockManager;
import nl.thewgbbroz.butils.custominventory.CustomInventoryManager;
import nl.thewgbbroz.butils.ignitedtnt.IgnitedTNTManager;
import nl.thewgbbroz.butils.managers.CombatManager;
import nl.thewgbbroz.butils.managers.VisibilityManager;
import nl.thewgbbroz.butils.mysql.DatabaseConnection;
import nl.thewgbbroz.butils.permissions.BPermissions;

public class BUtils extends JavaPlugin {
	public static final Random RAND = new Random();
	
	private static BUtils instance;
	
	private Config config;
	
	// Managers
	private VisibilityManager visibilityManager;
	private CustomInventoryManager customInventoryManager;
	private CombatManager combatManager;
	private CustomBlockManager customBlockManager;
	private IgnitedTNTManager ignitedTNTManager;
	//
	
	private BPermissions bPermissions;
	private DatabaseConnection globalDB;
	
	@Override
	public void onEnable() {
		instance = this;
		
		config = new Config(this, "config.yml");
		
		// Managers
		visibilityManager = new VisibilityManager(this);
		customInventoryManager = new CustomInventoryManager(this);
		combatManager = new CombatManager(this);
		customBlockManager = new CustomBlockManager(this);
		ignitedTNTManager = new IgnitedTNTManager(this);
		//
		
		bPermissions = new BPermissions();
		
		globalDB = new DatabaseConnection(getConfig().getString("global-mysql.host"), getConfig().getString("global-mysql.user"), getConfig().getString("global-mysql.password"), getConfig().getString("global-mysql.database"), true);
	}
	
	@Override
	public void onDisable() {
	}
	
	@Override
	public FileConfiguration getConfig() {
		return config.get();
	}
	
	@Override
	public void reloadConfig() {
		config.reload();
	}
	
	@Override
	public void saveConfig() {
		config.save();
	}
	
	@Override
	public void saveDefaultConfig() {
		config.saveDefault();
	}
	
	public VisibilityManager getVisibilityManager() {
		return visibilityManager;
	}
	
	public CustomInventoryManager getCustomInventoryManager() {
		return customInventoryManager;
	}
	
	public CombatManager getCombatManager() {
		return combatManager;
	}
	
	public CustomBlockManager getCustomBlockManager() {
		return customBlockManager;
	}
	
	public IgnitedTNTManager getIgnitedTNTManager() {
		return ignitedTNTManager;
	}
	
	public BPermissions getBPermissions() {
		return bPermissions;
	}
	
	public DatabaseConnection getGlobalDataBase() {
		return globalDB;
	}
	
	public static BUtils getInstance() {
		return instance;
	}
}
