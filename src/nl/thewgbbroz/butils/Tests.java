package nl.thewgbbroz.butils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import nl.thewgbbroz.butils.multiticktask.MultiTickTask;

public class Tests {
	private BUtils plugin;
	
	protected Tests(BUtils plugin) {
		this.plugin = plugin;
	}
	
	protected void testMultiTickTask() {
		World world = Bukkit.getWorlds().get(0);
		
		List<Chunk> list = new ArrayList<>();
		for(int c = 0; c < 10; c++) {
			for(int r = 0; r < 10; r++) {
				list.add(world.getChunkAt(r, c));
			}
		}
		
		new MultiTickTask<Chunk>(c -> {
			c.load(true);
			System.out.println("Loaded chunk (" + c.getX() + ", " + c.getZ() + ")");
		}).runTask(plugin, list, 2, 5);
	}
}
