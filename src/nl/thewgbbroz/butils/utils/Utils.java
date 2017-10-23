package nl.thewgbbroz.butils.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import nl.thewgbbroz.butils.BUtils;

public class Utils {
	private Utils() {
	}
	
	public static final int SHORTEN = 0;
	public static final int COMMAS = 1;
	
	private static final char[] CHAT_COLOR_CHARS = "1234567890abcdef".toCharArray();
	
	public static ItemStack makeItem(Material type, int amount, short damage, String customName) {
		ItemStack is = new ItemStack(type, amount, damage);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(customName);
		is.setItemMeta(im);
		
		return is;
	}
	
	public static ItemStack makeItem(Material type, int amount, String customName) {
		return makeItem(type, amount, (short) 0, customName);
	}
	
	public static ItemStack makeItem(Material type, String customName) {
		return makeItem(type, 1, (short) 0, customName);
	}
	
	public static String formatRawString(String s) {
		s = s.toLowerCase().replace("_", " ");
		s = String.valueOf(s.charAt(0)).toUpperCase() + s.substring(1);
		return s;
	}
	
	public static void giveOrDrop(Player p, ItemStack is) {
		if(p.getInventory().firstEmpty() == -1) {
			p.getWorld().dropItem(p.getLocation(), is);
		}else{
			p.getInventory().addItem(is);
			p.updateInventory();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static Material parseMaterial(String s) {
		Material m = null;
		try{
			m = Material.getMaterial(Integer.parseInt(s));
		}catch(Exception e) {}
		
		if(m == null)
			m = Material.getMaterial(s.toUpperCase());
		
		return m;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack parseItemStack(String s) {
		// name amount damage name:___ ench:___ ench:___ lore:_,_,_
		
		if(s.equalsIgnoreCase("null"))
			return null;
		
		String[] parts;
		if(s.contains(" "))
			parts = s.split(" ");
		else
			parts = new String[] { s };
		
		Material mat = null;
		try{
			mat = Material.getMaterial(Integer.parseInt(parts[0]));
		}catch(Exception e) {}
		
		if(mat == null) {
			mat = Material.getMaterial(parts[0].toUpperCase());
		}
		
		if(mat == null)
			return null;
		
		int amount = 1;
		if(parts.length > 1) {
			// Amount
			try{
				amount = Integer.parseInt(parts[1]);
			}catch(Exception e) {}
		}
		
		short damage = 0;
		if(parts.length > 2) {
			// Damage
			try{
				damage = Short.parseShort(parts[2]);
			}catch(Exception e) {}
		}
		
		ItemStack is = new ItemStack(mat, amount, damage);
		
		if(parts.length > 3) {
			ItemMeta im = is.getItemMeta();
			
			for(int i = 3; i < parts.length; i++) {
				try{
					String part = parts[i];
					
					if(part.startsWith("name:")) {
						String name = part.substring("name:".length());
						name = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', name);
						name = name.replace("_", " ");
						im.setDisplayName(name);
					}else if(part.startsWith("ench:")) {
						String[] parts2 = part.substring("ench:".length()).split("/");
						Enchantment ench = null;
						try{
							ench = Enchantment.getById(Integer.valueOf(parts2[0]));
						}catch(Exception e) {}
						
						if(ench == null)
							ench = Enchantment.getByName(parts2[0].toUpperCase());
						
						if(ench == null) continue;
						
						int lvl = Integer.parseInt(parts2[1]);
						
						im.addEnchant(ench, lvl, true);
					}else if(part.startsWith("lore:")) {
						String p = part.substring("lore:".length());
						String[] parts2;
						if(p.contains(","))
							parts2 = p.split(",");
						else
							parts2 = new String[] { p };
						
						List<String> lore = new ArrayList<>();
						for(String l : parts2) {
							lore.add(ChatColor.translateAlternateColorCodes('&', l).replace("_", " "));
						}
						
						im.setLore(lore);
					}
				}catch(Exception e) {}
			}
			
			is.setItemMeta(im);
		}
		
		return is;
	}
	
	public static String stringifyItemStack(ItemStack is) {
		// item amount damage name:___ ench:___ ench:___ lore:_,_,_
		
		if(is == null)
			return "null";
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(is.getType().name());
		sb.append(" " + is.getAmount());
		sb.append(" " + is.getDurability());
		
		if(is.hasItemMeta() && is.getItemMeta().hasDisplayName())
			sb.append(" name:" + is.getItemMeta().getDisplayName());
		
		for(Enchantment ench : is.getEnchantments().keySet()) {
			int lvl = is.getEnchantments().get(ench);
			sb.append(" ench:" + ench.getName() + "/" + lvl);
		}
		
		if(is.hasItemMeta() && is.getItemMeta().hasLore()) {
			sb.append(" lore:");
			for(String l : is.getItemMeta().getLore())
				sb.append(l + ",");
			sb.deleteCharAt(sb.length() - 1);
		}
		
		return sb.toString();
	}
	
	public static Location parseLocation(String s) {
		if(s.equalsIgnoreCase("null"))
			return null;
		
		try{
			String[] parts = s.split(" ");
			
			World w = Bukkit.getWorld(parts[0]);
			if(w == null) {
				return null;
			}
			
			double x = Double.parseDouble(parts[1]);
			double y = Double.parseDouble(parts[2]);
			double z = Double.parseDouble(parts[3]);
			
			float yaw = 0;
			float pitch = 0;
			
			if(parts.length >= 6) {
				yaw = Float.parseFloat(parts[4]);
				pitch = Float.parseFloat(parts[5]);
			}
			
			return new Location(w, x, y, z, yaw, pitch);
		}catch(Exception e) {
		}
		
		return null;
	}
	
	public static String stringifyLocation(Location loc) {
		if(loc == null)
			return "null";
		
		return loc.getWorld().getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " " + loc.getYaw() + " " + loc.getPitch();
	}
	
	public static String formatNumber(long num, int format) {
		if(format == SHORTEN) {
			String s = String.valueOf(num);
			
			if(s.length() <= 3)
				return s;
			else if(s.length() <= 6)
				return s.substring(0, s.length() - 3) + "K";
			else if(s.length() <= 9)
				return s.substring(0, s.length() - 6) + "M";
			else
				return s.substring(0, s.length() - 9) + "B";
		}else if(format == COMMAS) {
			String s = String.valueOf(num);
			
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < s.length(); i++) {
				int numAmount = s.length() - i;
				if(numAmount % 3 == 0) {
					sb.append(',');
				}
				
				sb.append(s.charAt(i));
			}
			
			if(sb.charAt(0) == ',')
				sb.deleteCharAt(0);
			
			return sb.toString();
		}else{
			throw new IllegalArgumentException("Invalid format! Use SHORTEN or COMMAS");
		}
	}
	
	public static PotionEffect clonePotionEffect(PotionEffect pe) {
		return new PotionEffect(pe.getType(), pe.getDuration(), pe.getAmplifier());
	}
	
	public static String formatTime(int secs) {
		if(secs < 60)
			return secs + " second(s)";
		
		double mins = (double) secs / 60d;
		if(mins < 60)
			return (int) mins + " minute(s)";
		
		double hrs = mins / 60d;
		if(hrs < 24)
			return (int) hrs + " hour(s)";
		
		double days = hrs / 24d;
		return (int) days + " day(s)";
	}
	
	public static boolean isArmor(ItemStack is) {
		if(is == null)
			return false;
		
		return is.getType().name().contains("HELMET") || is.getType().name().contains("CHESTPLATE") ||
				is.getType().name().contains("LEGGINGS") || is.getType().name().contains("BOOTS");
	}
	
	public static boolean isWeapon(ItemStack is) {
		if(is == null)
			return false;
		
		return is.getType() == Material.BOW || is.getType().name().contains("SWORD");
	}
	
	public static boolean isTool(ItemStack is) {
		if(is == null)
			return false;
		
		return is.getType() == Material.FLINT_AND_STEEL || is.getType() == Material.FISHING_ROD || is.getType() == Material.CARROT_STICK ||
				is.getType() == Material.SHEARS ||
				is.getType().name().contains("SPADE") || is.getType().name().contains("AXE") || is.getType().name().contains("HOE");
	}
	
	public static boolean isRepairable(ItemStack is) {
		return isArmor(is) || isWeapon(is) || isTool(is);
	}
	
	public static int getInventorySize(int itemCount) {
		return itemCount + (9 - (itemCount % 9));
	}
	
	public static int getUnixTime() {
		return (int) (System.currentTimeMillis() / 1000L);
	}
	
	public static int parseTime(String s) {
		// 10m, 5h, 2d
		
		try{
			char timeUnit = s.charAt(s.length() - 1);
			
			double time = Double.parseDouble(s.substring(0, s.length() - 1));
			
			if(timeUnit == 's') {
				return (int) (time);
			}else if(timeUnit == 'm') {
				return (int) (time * 60d);
			}else if(timeUnit == 'h') {
				return (int) (time * 60d * 60d);
			}else if(timeUnit == 'd') {
				return (int) (time * 60d * 60d * 24d);
			}
		}catch(Exception e) {}
		
		return -1;
	}
	
	public static ChatColor getRandomChatColor() {
		return ChatColor.getByChar(CHAT_COLOR_CHARS[BUtils.RAND.nextInt(CHAT_COLOR_CHARS.length)]);
	}
	
	public static boolean isInt(String s) {
		try{
			Integer.parseInt(s);
			return true;
		}catch(Exception e) {}
		
		return false;
	}
	
	public static boolean isInt(char c) {
		return isInt(String.valueOf(c));
	}
	
	public static void sendClickableMessage(Player p, String msg, String cmd) {
		String json = "{\"text\":\"" + msg + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/" + cmd + "\"}}";
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " " + json);
	}
	
	public static String formatSecondsClock(int secs) {
		int mins = secs / 60;
		secs = secs - mins * 60;
		
		String secsStr = String.valueOf(secs);
		if(secsStr.length() < 2)
			secsStr = "0" + secsStr;
		
		return mins + ":" + secsStr;
	}
	
	public static boolean contains(Location loc, Location min, Location max) {
		if(min.getWorld() != loc.getWorld() || max.getWorld() != loc.getWorld()) {
			throw new IllegalArgumentException("All 3 locations must be in the same world!");
		}
		
		return loc.getX() > min.getX() && loc.getX() < max.getX() &&
				loc.getY() > min.getY() && loc.getY() < max.getY() &&
				loc.getZ() > min.getZ() && loc.getZ() < max.getZ();
	}
	
	public static void fillInventoryRandomly(Inventory inv, Map<ItemStack, Double> items) {
		for(ItemStack is : items.keySet()) {
			if(inv.firstEmpty() == -1)
				return;
			
			double c = items.get(is);
			if(BUtils.RAND.nextDouble() < c) {
				int slot;
				do {
					slot = BUtils.RAND.nextInt(inv.getSize());
				}while(!isNothing(inv.getItem(slot)));
				
				inv.setItem(slot, is.clone());
			}
		}
	}
	
	public static void fillInventoryRandomly(Inventory inv, List<ItemStack> items) {
		for(ItemStack is : items) {
			if(inv.firstEmpty() == -1)
				return;
			
			int slot;
			do {
				slot = BUtils.RAND.nextInt(inv.getSize());
			}while(!isNothing(inv.getItem(slot)));
			
			inv.setItem(slot, is.clone());
		}
	}
	
	public static boolean isNothing(ItemStack is) {
		return is == null || is.getType() == Material.AIR;
	}
	
	public static boolean itemStackEquals(ItemStack is1, ItemStack is2, boolean matchAmount, boolean matchDurability, boolean matchDisplayName) {
		if(is1.getType() != is2.getType())
			return false;
		
		if(matchAmount && is1.getAmount() != is2.getAmount())
			return false;
		
		if(matchDurability && is1.getDurability() != is2.getDurability())
			return false;
		
		if(matchDisplayName) {
			if(!(is1.hasItemMeta() && is2.hasItemMeta()))
				return false;
			
			if(!(is1.getItemMeta().hasDisplayName() && is2.getItemMeta().hasDisplayName()))
				return false;
			
			if(!is1.getItemMeta().getDisplayName().equals(is2.getItemMeta().getDisplayName()))
				return false;
		}
		
		return true;
	}
	
	public static void setDisplayName(ItemStack is, String displayName) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(displayName);
		is.setItemMeta(im);
	}
	
	public static ItemStack getHead(String owner) {
		ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta sm = (SkullMeta) is.getItemMeta();
		sm.setOwner(owner);
		is.setItemMeta(sm);
		
		return is;
	}
}
