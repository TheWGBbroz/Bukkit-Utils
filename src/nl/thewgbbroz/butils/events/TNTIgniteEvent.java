package nl.thewgbbroz.butils.events;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

public class TNTIgniteEvent extends EntityEvent {
	private static final HandlerList HANDLERS = new HandlerList();
	
	public TNTIgniteEvent(TNTPrimed what) {
		super(what);
	}
	
	public TNTPrimed getTNT() {
		return (TNTPrimed) entity;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
