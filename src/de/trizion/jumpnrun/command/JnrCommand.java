package de.trizion.jumpnrun.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.trizion.jumpnrun.listener.LocationSelectListener;
import de.trizion.jumpnrun.util.ConfigManager;

public class JnrCommand implements TabExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && ((Player) sender).hasPermission("jnr.customize")) {
			if(args.length == 1 && args[0].equalsIgnoreCase("bounds")){
				Player player = (Player) sender;
				if(player.getInventory().firstEmpty() != -1) {
					ItemStack boundStick = new ItemStack(Material.RABBIT_FOOT);
					ItemMeta stickMeta = boundStick.getItemMeta();
					stickMeta.addEnchant(Enchantment.DURABILITY, 1, false);
					stickMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					stickMeta.setDisplayName("§7[§3§lJNR§7] §bBound Selector");
					stickMeta.setLore(Arrays.asList("§7Left Click: First Bound", "§7Right Click: Second Bound"));
					boundStick.setItemMeta(stickMeta);
					player.getInventory().addItem(boundStick);
					
				}else {	
					sender.sendMessage("§7[§3§lJNR§7]: §cInventory space for JNR-Tool required!");
				}
			}else if (args.length == 1 && args[0].equalsIgnoreCase("confirm")){
				Location loc1 = LocationSelectListener.getFirstLocation();
				Location loc2 = LocationSelectListener.getSecondLocation();
				if(Math.abs(loc1.getX() - loc2.getX()) > 8 && Math.abs(loc1.getY() - loc2.getY()) > 8 && Math.abs(loc1.getZ() - loc2.getZ()) > 8) {
					ConfigManager.setBound("First", loc1);
					ConfigManager.setBound("Second", loc2);
					sender.sendMessage("§7[§3§lJNR§7]: §bBounds have been created!");
				}else {
					sender.sendMessage("§7[§3§lJNR§7]: §cSelection must at least be 9x9x9!");
				}
			}else {
				sender.sendMessage("§7[§3§lJNR§7]: §cUse /jnr <keyword>");
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length != 1) return new ArrayList<String>();
		return Arrays.asList("bounds", "confirm");
	}

}
