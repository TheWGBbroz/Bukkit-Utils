package nl.thewgbbroz.butils.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import nl.thewgbbroz.butils.BUtils;

public class CombatManager implements Listener {
	private static final int PVP_COOLDOWN = 10 * 1000;
	
	// Last time a player got attacked or attacked someone
	private Map<Player, Long> lastPvp = new HashMap<>();
	
	public CombatManager(BUtils plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	public boolean isInCombat(Player p, int pvpCooldown) {
		if(!lastPvp.containsKey(p))
			return false;
		
		return (System.currentTimeMillis() - lastPvp.get(p)) < pvpCooldown;
	}
	
	public boolean isInCombat(Player p) {
		return isInCombat(p, PVP_COOLDOWN);
	}
	
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player && e.getDamager() instanceof Player))
			return;
		
		Player p = (Player) e.getEntity();
		Player damager = (Player) e.getDamager();
		
		long now = System.currentTimeMillis();
		lastPvp.put(p, now);
		lastPvp.put(damager, now);
	}
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e) {
		lastPvp.remove(e.getEntity());
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		lastPvp.remove(e.getPlayer());
	}
}
