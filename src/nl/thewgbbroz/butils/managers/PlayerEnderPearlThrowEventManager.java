package nl.thewgbbroz.butils.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thewgbbroz.butils.BUtils;
import nl.thewgbbroz.butils.events.PlayerEnderPearlThrowEvent;

public class PlayerEnderPearlThrowEventManager implements Listener {
	private static final int DEFAULT_COOLDOWN = 20;
	
	private BUtils plugin;
	
	public PlayerEnderPearlThrowEventManager(BUtils plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onProjectileLaunchEvent(ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof EnderPearl && e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof Player) {
			EnderPearl ep = (EnderPearl) e.getEntity();
			Player shooter = (Player) ep.getShooter();
			
			PlayerEnderPearlThrowEvent event = new PlayerEnderPearlThrowEvent(shooter, ep, DEFAULT_COOLDOWN);
			Bukkit.getPluginManager().callEvent(event);
			
			int cooldown;
			if(event.isCancelled()) {
				e.setCancelled(true);
				cooldown = 0;
			}else{
				cooldown = event.getCooldown();
			}
			
			new BukkitRunnable() {
				public void run() {
					shooter.setCooldown(Material.ENDER_PEARL, cooldown);
				}
			}.runTaskLater(plugin, 1);
		}
	}
}
