package nl.thewgbbroz.butils.events;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerEnderPearlThrowEvent extends PlayerEvent implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();
	
	private EnderPearl enderPearl;
	private int cooldown;
	
	private boolean cancelled = false;
	
	public PlayerEnderPearlThrowEvent(Player player, EnderPearl enderPearl, int cooldown) {
		super(player);
		
		this.enderPearl = enderPearl;
		this.cooldown = cooldown;
	}
	
	public EnderPearl getEnderPearl() {
		return enderPearl;
	}
	
	public int getCooldown() {
		return cooldown;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
