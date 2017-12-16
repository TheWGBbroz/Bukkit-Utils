package nl.thewgbbroz.butils.custominventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface CustomInventoryListener {
	public void onClose(ItemStack[] contents);
	
	// If this returns TRUE, the event will be canceled
	public boolean onClick(ItemStack item, int slot, Inventory inv);
}
