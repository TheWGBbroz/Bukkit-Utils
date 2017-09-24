package nl.thewgbbroz.butils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import nl.thewgbbroz.butils.BUtils;

public abstract class BCommandExecutor implements CommandExecutor {
	public static final String DEFAULT_NO_PERMISSIONS = ChatColor.RED + "You don't have permissions to do this!";
	public static final String DEFAULT_NEED_PLAYER = ChatColor.RED + "You need to be a player to execute this command!";
	
	private String noPermsMsg = null;
	private String needPlayerMsg = null;
	
	private String permission;
	private boolean needPlayer;
	
	public BCommandExecutor(String permission, boolean needPlayer) {
		this.permission = permission;
		this.needPlayer = needPlayer;
	}
	
	public BCommandExecutor(String permission) {
		this(permission, false);
	}
	
	public BCommandExecutor() {
		this(null, false);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(permission != null && !BUtils.getInstance().getBPermissions().hasPermission(sender, permission)) {
			sender.sendMessage(getNoPermissionsMessage());
			return true;
		}
		
		boolean isPlayer = sender instanceof Player;
		
		if(needPlayer && !isPlayer) {
			sender.sendMessage(getNeedPlayerMessage());
			return true;
		}
		
		execute(sender, args, isPlayer);
		
		return true;
	}
	
	public abstract void execute(CommandSender sender, String[] args, boolean isPlayer);
	
	public void register(JavaPlugin plugin, String cmd) {
		plugin.getCommand(cmd).setExecutor(this);
	}
	
	protected void setNoPermissionsMessage(String noPerms) {
		this.noPermsMsg = noPerms;
	}
	
	public void setNeedPlayerMessage(String needPlayer) {
		this.needPlayerMsg = needPlayer;
	}
	
	public String getNoPermissionsMessage() {
		return noPermsMsg == null ? DEFAULT_NO_PERMISSIONS : noPermsMsg;
	}
	
	public String getNeedPlayerMessage() {
		return needPlayerMsg == null ? DEFAULT_NEED_PLAYER : needPlayerMsg;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public boolean hasPermission() {
		return permission != null;
	}
	
	public boolean isNeedPlayer() {
		return needPlayer;
	}
}
