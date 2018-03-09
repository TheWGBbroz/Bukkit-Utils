package nl.thewgbbroz.butils;

import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import nl.thewgbbroz.butils.config.Config;
import nl.thewgbbroz.butils.config.MessagesConfig;
import nl.thewgbbroz.butils.customblocks.CustomBlockManager;
import nl.thewgbbroz.butils.custominventory.CustomInventoryManager;
import nl.thewgbbroz.butils.managers.CombatManager;
import nl.thewgbbroz.butils.managers.PlayerEnderPearlThrowEventManager;
import nl.thewgbbroz.butils.managers.PlayerPotionEffectEventManager;
import nl.thewgbbroz.butils.managers.TNTIgniteEventManager;
import nl.thewgbbroz.butils.managers.VisibilityManager;
import nl.thewgbbroz.butils.mysql.DatabaseConnection;
import nl.thewgbbroz.butils.permissions.BPermissions;

public class BUtils extends JavaPlugin {
	public static final Random RAND = new Random();
	
	private static BUtils instance;
	
	private Config config;
	private MessagesConfig globalMessages;
	
	// Managers
	private VisibilityManager visibilityManager;
	private CustomInventoryManager customInventoryManager;
	private CombatManager combatManager;
	private CustomBlockManager customBlockManager;
	private TNTIgniteEventManager tntIgniteEventManager;
	private PlayerPotionEffectEventManager playerPotionEffectEventManager;
	private PlayerEnderPearlThrowEventManager playerEnderPearlThrowEventManager;
	//
	
	private BPermissions bPermissions;
	private DatabaseConnection globalDB;
	
	@Override
	public void onEnable() {
		instance = this;
		
		config = new Config(this, "config.yml");
		globalMessages = new MessagesConfig(this, "global-messages.yml");
		
		// Managers
		visibilityManager = new VisibilityManager(this);
		customInventoryManager = new CustomInventoryManager(this);
		combatManager = new CombatManager(this);
		customBlockManager = new CustomBlockManager(this);
		tntIgniteEventManager = new TNTIgniteEventManager(this);
		playerPotionEffectEventManager = new PlayerPotionEffectEventManager(this);
		playerEnderPearlThrowEventManager = new PlayerEnderPearlThrowEventManager(this);
		//
		
		bPermissions = new BPermissions();
		
		globalDB = new DatabaseConnection(getConfig().getString("global-mysql.host"), getConfig().getString("global-mysql.user"), getConfig().getString("global-mysql.password"), getConfig().getString("global-mysql.database"), true);
		
		// TESTS:
		//new Tests(this).testMultiTickTask();
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
	
	public MessagesConfig getGlobalMessages() {
		return globalMessages;
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
	
	public TNTIgniteEventManager getTntIgniteEventManager() {
		return tntIgniteEventManager;
	}
	
	public PlayerPotionEffectEventManager getPlayerPotionEffectEventManager() {
		return playerPotionEffectEventManager;
	}
	
	public PlayerEnderPearlThrowEventManager getPlayerEnderPearlThrowEventManager() {
		return playerEnderPearlThrowEventManager;
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
