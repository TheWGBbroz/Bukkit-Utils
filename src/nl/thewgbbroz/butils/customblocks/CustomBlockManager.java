package nl.thewgbbroz.butils.customblocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import nl.thewgbbroz.butils.BUtils;

public class CustomBlockManager implements Listener {
	private static final CustomBlockClickListener DUMMY_LISTENER = new CustomBlockClickListener() {
		public void onCustomBlockClickEvent(CustomBlockManager manager, CustomBlock cb) {}
	};
	
	private Map<CustomBlock, CustomBlockClickListener> customBlocks = new HashMap<>();
	
	public CustomBlockManager(BUtils plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
		new BukkitRunnable() {
			public void run() {
				for(CustomBlock cb : customBlocks.keySet()) {
					cb.updatePlayer();
				}
			}
		}.runTaskTimer(plugin, 1, 1);
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		CustomBlock cb = null;
		
		Location loc = e.getPlayer().getEyeLocation();
		double yaw = Math.toRadians(loc.getYaw() + 90);
		double pitch = Math.toRadians(loc.getPitch() * -1);
		for(double len = 0; len < 5; len += 0.5) {
			double xzLength = Math.cos(pitch) * len;
			double dx = xzLength * Math.cos(yaw);
			double dy = len * Math.sin(pitch);
			double dz = xzLength * Math.sin(yaw);
			
			Location lineLoc = loc.clone();
			lineLoc.add(dx, dy, dz);
			
			Block b = lineLoc.getBlock();
			cb = getCustomBlock(b, e.getPlayer());
			if(cb != null)
				break;
		}
		
		if(cb != null) {
			customBlocks.get(cb).onCustomBlockClickEvent(this, cb);
		}
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		removeCustomBlocks(e.getPlayer());
	}
	
	public CustomBlock addCustomBlock(Block b, Material fakeMaterial, Player p, CustomBlockClickListener listener) {
		if(listener == null)
			listener = DUMMY_LISTENER;
		
		CustomBlock cb = new CustomBlock(p, b, fakeMaterial);
		customBlocks.put(cb, listener);
		return cb;
	}
	
	public CustomBlock getCustomBlock(Block b, Player p) {
		for(CustomBlock cb : customBlocks.keySet()) {
			if(cb.getBlock().getLocation().equals(b.getLocation()) && cb.getTargetPlayer() == p) {
				return cb;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public void removeCustomBlock(CustomBlock cb) {
		cb.getTargetPlayer().sendBlockChange(cb.getBlock().getLocation(), cb.getBlock().getType(), cb.getBlock().getData());
		customBlocks.remove(cb);
	}
	
	public void removeCustomBlocks(Player p) {
		List<CustomBlock> remove = new ArrayList<>();
		for(CustomBlock cb : customBlocks.keySet()) {
			if(cb.getTargetPlayer() == p) {
				remove.add(cb);
			}
		}
		
		for(CustomBlock cb : remove) {
			customBlocks.remove(cb);
		}
	}
	
	public List<CustomBlock> getCustomBlocks() {
		List<CustomBlock> res = new ArrayList<>();
		res.addAll(customBlocks.keySet());
		return res;
	}
}
