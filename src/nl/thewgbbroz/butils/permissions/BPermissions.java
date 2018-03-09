package nl.thewgbbroz.butils.permissions;

import org.bukkit.command.CommandSender;

public class BPermissions {
	public boolean hasPermission(CommandSender p, String... nodes) {
		if(p.isOp() || p.hasPermission("*"))
			return true;
		
		for(String node : nodes) {
			if(p.hasPermission(node))
				return true;
			
			if(node.contains(".")) {
				StringBuilder newNode = new StringBuilder();
				String[] parts = node.split("\\.");
				
				for(int i = 0; i < parts.length - 1; i++) {
					newNode.append(parts[i] + ".");
				}
				
				newNode.append("*");
				
				if(p.hasPermission(newNode.toString()))
					return true;
			}
			
			/*
			if(!node.contains(".")) {
				if(p.hasPermission(node))
					return true;
			}else{
				String[] parts = node.split("\\.");
				
				String perm = "";
				for(int i = 0; i < parts.length; i++) {
					perm += parts[i];
					
					if(p.hasPermission(perm) || p.hasPermission(perm + ".*"))
						return true;
					
					perm += ".";
				}
			}
			*/
		}
		
		return false;
	}
}
