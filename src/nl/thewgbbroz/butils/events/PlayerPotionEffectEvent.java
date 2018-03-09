package nl.thewgbbroz.butils.events;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerPotionEffectEvent extends PlayerEvent implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();
	
	private PotionEffectType type;
	private int duration;
	private int amplifier;
	private boolean ambient;
	private boolean particles;
	private Color color;
	
	private boolean cancelled = false;
	
	public PlayerPotionEffectEvent(Player player, PotionEffectType type, int duration, int amplifier, boolean ambient,
			boolean particles, Color color) {
		super(player);
		
		this.type = type;
		this.duration = duration;
		this.amplifier = amplifier;
		this.ambient = ambient;
		this.particles = particles;
		this.color = color;
	}
	
	public PlayerPotionEffectEvent(Player p, PotionEffect pe) {
		this(p, pe.getType(), pe.getDuration(), pe.getAmplifier(), pe.isAmbient(), pe.hasParticles(), pe.getColor());
	}
	
	public PotionEffectType getType() {
		return type;
	}
	
	public void setType(PotionEffectType type) {
		this.type = type;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getAmplifier() {
		return amplifier;
	}
	
	public void setAmplifier(int amplifier) {
		this.amplifier = amplifier;
	}
	
	public boolean isAmbient() {
		return ambient;
	}
	
	public void setAmbient(boolean ambient) {
		this.ambient = ambient;
	}
	
	public boolean hasParticles() {
		return particles;
	}
	
	public void setParticles(boolean particles) {
		this.particles = particles;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public PotionEffect toPotionEffect() {
		return new PotionEffect(type, duration, amplifier, ambient, particles, color);
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
