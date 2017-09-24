package nl.thewgbbroz.butils.custominventory;

import org.bukkit.inventory.ItemStack;

public interface CustomInventoryListener {
	public void onClose(ItemStack[] contents);
	public boolean onClick(ItemStack item);
}
