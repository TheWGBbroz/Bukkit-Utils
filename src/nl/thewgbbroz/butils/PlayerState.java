package nl.thewgbbroz.butils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PlayerState {
	private Player p;
	
	private double health;
	private double healthScale;
	private boolean healthScaled;
	private int foodLevel;
	private float saturation;
	private GameMode gameMode;
	private List<PotionEffect> activePotionEffects;
	private boolean allowFlight;
	private boolean canPickupItems;
	private ItemStack[] enderchestContents;
	private ItemStack[] armorContents;
	private float exhaustion;
	private float exp;
	private float fallDistance;
	private int fireTicks;
	private ItemStack[] inventoryContents;
	private int level;
	private Location location;
	private int remainingAir;
	private boolean flying;
	
	public PlayerState(Player p) {
		this.p = p;
		load();
	}
	
	// Loads the current state of the player into memory
	public void load() {
		health = ((Damageable) p).getHealth();
		healthScale = p.getHealthScale();
		healthScaled = p.isHealthScaled();
		foodLevel = p.getFoodLevel();
		saturation = p.getSaturation();
		gameMode = p.getGameMode();
		activePotionEffects = new ArrayList<>(p.getActivePotionEffects());
		allowFlight = p.getAllowFlight();
		canPickupItems = p.getCanPickupItems();
		enderchestContents = p.getEnderChest().getContents();
		armorContents = p.getEquipment().getArmorContents();
		exhaustion = p.getExhaustion();
		exp = p.getExp();
		fallDistance = p.getFallDistance();
		fireTicks = p.getFireTicks();
		inventoryContents = p.getInventory().getContents();
		level = p.getLevel();
		location = p.getLocation();
		remainingAir = p.getRemainingAir();
		flying = p.isFlying();
	}
	
	// Restores the player state from the last time load() was called
	// load() gets called in the constructor
	public void restore(boolean teleportBack) {
		p.setHealth(health);
		p.setHealthScale(healthScale);
		p.setHealthScaled(healthScaled);
		p.setFoodLevel(foodLevel);
		p.setSaturation(saturation);
		p.setGameMode(gameMode);
		p.setAllowFlight(allowFlight);
		p.setCanPickupItems(canPickupItems);
		p.getEnderChest().setContents(enderchestContents);
		p.getEquipment().setArmorContents(armorContents);
		p.setExhaustion(exhaustion);
		p.setExp(exp);
		p.setFallDistance(fallDistance);
		p.setFireTicks(fireTicks);
		p.getInventory().setContents(inventoryContents);
		p.setLevel(level);
		p.setRemainingAir(remainingAir);
		p.setFlying(flying);
		
		for(PotionEffect pe : p.getActivePotionEffects())
			p.removePotionEffect(pe.getType());
		p.addPotionEffects(activePotionEffects);
		
		if(teleportBack) {
			p.teleport(location);
		}
	}
	
	// Resets the player back to its default state
	public void reset(boolean teleportToSpawn) {
		p.setHealth(20d);
		p.setHealthScaled(false);
		p.setFoodLevel(20);
		p.setSaturation(5);
		p.setGameMode(GameMode.SURVIVAL);
		p.setAllowFlight(false);
		p.setCanPickupItems(true);
		p.getEnderChest().clear();
		p.getEquipment().clear();
		p.setExhaustion(1);
		p.setExp(0);
		p.setFallDistance(0);
		p.setFireTicks(0);
		p.getInventory().clear();
		p.setLevel(0);
		p.setRemainingAir(20);
		p.setFlying(false);
		
		for(PotionEffect pe : p.getActivePotionEffects())
			p.removePotionEffect(pe.getType());
		
		if(teleportToSpawn) {
			p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
		}
	}
	
	public Player getPlayer() {
		return p;
	}
}
