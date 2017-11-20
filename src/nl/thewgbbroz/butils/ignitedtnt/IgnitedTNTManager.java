package nl.thewgbbroz.butils.ignitedtnt;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thewgbbroz.butils.BUtils;
import nl.thewgbbroz.butils.events.TNTIgniteEvent;

public class IgnitedTNTManager implements Listener {
	private BUtils plugin;
	
	public IgnitedTNTManager(BUtils plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		if(e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.TNT) {
			Location loc = e.getClickedBlock().getLocation();
			new BukkitRunnable() {
				public void run() {
					TNTPrimed nearest = null;
					double nearestDistSq = 100 * 100;
					
					for(Entity en : loc.getWorld().getEntities()) {
						if(en instanceof TNTPrimed) {
							double distSq = loc.distanceSquared(en.getLocation());
							if(distSq < nearestDistSq) {
								nearest = (TNTPrimed) en;
								nearestDistSq = distSq;
							}
						}
					}
					
					if(nearest != null && nearestDistSq < 1 * 1) {
						// TNT got primed!
						Bukkit.getPluginManager().callEvent(new TNTIgniteEvent(nearest));
					}
				}
			}.runTaskLater(plugin, 1);
		}
	}
}
