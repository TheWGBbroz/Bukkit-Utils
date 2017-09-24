package nl.thewgbbroz.butils.customblocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class CustomBlock {
	private Player targetPlayer;
	private Block block;
	private Material fakeMaterial;
	
	protected CustomBlock(Player targetPlayer, Block block, Material fakeMaterial) {
		this.targetPlayer = targetPlayer;
		this.block = block;
		this.fakeMaterial = fakeMaterial;
	}
	
	@SuppressWarnings("deprecation")
	public void updatePlayer() {
		targetPlayer.sendBlockChange(block.getLocation(), fakeMaterial, (byte) 0);
	}
	
	public Player getTargetPlayer() {
		return targetPlayer;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public Material getFakeMaterial() {
		return fakeMaterial;
	}
}
