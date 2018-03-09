package nl.thewgbbroz.butils.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thewgbbroz.butils.BUtils;
import nl.thewgbbroz.butils.events.PlayerPotionEffectEvent;

public class PlayerPotionEffectEventManager implements Listener {
	private Map<Player, List<PotionEffect>> previousPotions = new HashMap<>();
	
	public PlayerPotionEffectEventManager(BUtils plugin) {
		new BukkitRunnable() {
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(previousPotions.containsKey(p)) {
						List<PotionEffect> prevPotionEffects = previousPotions.get(p);
						
						List<PotionEffect> current = new ArrayList<>(p.getActivePotionEffects());
						for(PotionEffect pe : current) {
							if(!contains(prevPotionEffects, pe, 1)) {
								// This potion effect is new
								PlayerPotionEffectEvent event = new PlayerPotionEffectEvent(p, pe);
								Bukkit.getPluginManager().callEvent(event);
								
								if(event.isCancelled()) {
									p.removePotionEffect(pe.getType());
								}else{
									p.addPotionEffect(event.toPotionEffect(), true);
								}
							}
						}
					}
					
					previousPotions.put(p, new ArrayList<>(p.getActivePotionEffects()));
				}
			}
		}.runTaskTimer(plugin, 1, 1);
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		previousPotions.remove(e.getPlayer());
	}
	
	private static boolean contains(List<PotionEffect> pes, PotionEffect target, int durationOffset) {
		for(PotionEffect pe : pes) {
			if(pe.getType() == target.getType() && pe.getDuration() == target.getDuration() + durationOffset &&
					pe.getAmplifier() == target.getAmplifier() && pe.isAmbient() == target.isAmbient() &&
					pe.hasParticles() == target.hasParticles() && pe.getColor() == target.getColor())
				return true;
		}
		
		return false;
	}
}
