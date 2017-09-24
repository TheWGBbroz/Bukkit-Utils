package nl.thewgbbroz.butils.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import nl.thewgbbroz.butils.BUtils;

public class VisibilityManager implements Listener {
	private List<Player> restrictedPlayerVisibility = new ArrayList<>();
	private List<Player> alwaysInvisible = new ArrayList<>();
	
	public VisibilityManager(BUtils plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		for(Player p : restrictedPlayerVisibility) {
			p.hidePlayer(e.getPlayer());
		}
		
		for(Player p : alwaysInvisible) {
			e.getPlayer().hidePlayer(p);
		}
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		restrictedPlayerVisibility.remove(e.getPlayer());
		alwaysInvisible.remove(e.getPlayer());
	}
	
	public void setInvisible(Player player, boolean invisible) {
		if(invisible) {
			alwaysInvisible.add(player);
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.hidePlayer(player);
			}
			
			// To avoid possible strange glitches
			player.showPlayer(player);
		}else{
			alwaysInvisible.remove(player);
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(!restrictedPlayerVisibility.contains(p))
					p.showPlayer(player);
			}
		}
	}
	
	public void setVisiblePlayers(Player player, Collection<Player> visible) {
		// Hide all players
		for(Player p : Bukkit.getOnlinePlayers()) {
			player.hidePlayer(p);
		}
		
		// Show needed players
		for(Player p : visible) {
			if(!alwaysInvisible.contains(p))
				player.showPlayer(p);
		}
		
		// To avoid possible strange glitches
		player.showPlayer(player);
		
		restrictedPlayerVisibility.add(player);
	}
	
	public void setVisiblePlayers(Player player, Player... visible) {
		List<Player> list = new ArrayList<>();
		for(Player p : visible)
			list.add(p);
		setVisiblePlayers(player, list);
	}
	
	public void hideEveryone(Player player) {
		setVisiblePlayers(player);
	}
	
	public void showAllPlayers(Player player) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(!alwaysInvisible.contains(p))
				player.showPlayer(p);
		}
		
		restrictedPlayerVisibility.remove(player);
	}
}
