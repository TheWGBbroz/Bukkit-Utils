package nl.thewgbbroz.butils.multiticktask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MultiTickTask<T> {
	private Consumer<T> consumer;
	
	private boolean running = false;
	
	public MultiTickTask(Consumer<T> consumer) {
		this.consumer = consumer;
	}
	
	public void runTask(JavaPlugin plugin, Collection<T> collection, int operationsPerTick, int tickPeriod) {
		if(running)
			throw new IllegalStateException("This task is already running!");
		
		running = true;
		
		final List<T> copy = new ArrayList<>(collection);
		
		new BukkitRunnable() {
			int index = 0;
			
			public void run() {
				if(!running) {
					cancel();
					return;
				}
				
				for(int i = 0; i < operationsPerTick; i++) {
					consumer.accept(copy.get(index));
					
					index++;
					if(index >= copy.size()) {
						running = false;
						cancel();
						break;
					}
				}
			}
		}.runTaskTimer(plugin, 0, tickPeriod);
	}
	
	public void cancel() {
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
}
