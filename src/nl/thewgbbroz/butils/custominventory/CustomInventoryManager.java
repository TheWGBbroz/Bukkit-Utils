package nl.thewgbbroz.butils.custominventory;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import nl.thewgbbroz.butils.BUtils;

public class CustomInventoryManager implements Listener {
	private Map<Player, CustomInventoryListener> inInv = new HashMap<>();
	
	public CustomInventoryManager(BUtils plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		if(inInv.containsKey(e.getPlayer())) {
			e.getPlayer().closeInventory();
			inInv.remove(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent e) {
		if(inInv.containsKey(e.getPlayer())) {
			inInv.get(e.getPlayer()).onClose(e.getInventory().getContents());
			inInv.remove(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		if(inInv.containsKey(e.getWhoClicked())) {
			CustomInventoryListener listener = inInv.get(e.getWhoClicked());
			
			if(listener.onClick(e.getCurrentItem(), e.getSlot(), e.getClickedInventory()))
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryDragEvent(InventoryDragEvent e) {
		if(inInv.containsKey(e.getWhoClicked())) {
			CustomInventoryListener listener = inInv.get(e.getWhoClicked());
			
			boolean cancel = false;
			for(Integer slot : e.getNewItems().keySet()) {
				ItemStack is = e.getNewItems().get(slot);
				
				if(listener.onClick(is, slot, e.getInventory())) {
					cancel = true;
					// Don't break, the listener might want to know about the other clicks as well!
				}
			}
			
			if(cancel)
				e.setCancelled(true);
		}
	}
	
	public Inventory openInventory(Player p, ItemStack[] contents, String title, CustomInventoryListener cil) {
		if(contents.length % 9 != 0)
			throw new IllegalArgumentException("contents.length must be divisible by 9!");
		
		Inventory inv = Bukkit.createInventory(null, contents.length, title);
		inv.setContents(contents);
		
		inInv.put(p, cil);
		
		p.openInventory(inv);
		
		return inv;
	}
}
