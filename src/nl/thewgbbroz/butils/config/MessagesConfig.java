package nl.thewgbbroz.butils.config;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class MessagesConfig extends Config {
	private Map<String, String> cache = new HashMap<>(); // Contains messages with translated color chars
	
	public MessagesConfig(JavaPlugin plugin, String name) {
		super(plugin, name);
	}
	
	public MessagesConfig(JavaPlugin plugin) {
		this(plugin, "messages.yml");
	}
	
	public String getMessage(String path, String... replace) {
		if(!get().contains(path))
			return path;
		
		String s;
		if(cache.containsKey(path)) {
			s = cache.get(path);
		}else{
			if(get().isList(path)) {
				s = "";
				for(String str : get().getStringList(path)) {
					s += str + '\n';
				}
				s = s.substring(0, s.length() - 2);
			}else{
				s = get().getString(path);
			}
			s = ChatColor.translateAlternateColorCodes('&', s).replace("\\n", "\n");
			
			cache.put(path, s);
		}
		
		for(int i = 0; i < replace.length; i++) {
			s = s.replace("%" + (i + 1), replace[i]);
		}
		
		return s;
	}
}
