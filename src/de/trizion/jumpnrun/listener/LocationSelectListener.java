package de.trizion.jumpnrun.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class LocationSelectListener implements Listener{
	public static Location firstLocation;
	public static Location secondLocation;
  
  @EventHandler
  public void onBuildActivation(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if(player.getInventory().getItemInMainHand().getType() == Material.RABBIT_FOOT && event.getHand() == EquipmentSlot.HAND) {     
      if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
      	firstLocation = event.getClickedBlock().getLocation();
        player.sendMessage("§7[§3§lJNR§7]: First position set to (" + firstLocation.getBlockX() + ", " + firstLocation.getBlockY() + ", " + firstLocation.getBlockZ() + ")");
      }else if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      	secondLocation = event.getClickedBlock().getLocation();
        player.sendMessage("§7[§3§lJNR§7]: Second position set to (" + secondLocation.getBlockX() + ", " + secondLocation.getBlockY() + ", " + secondLocation.getBlockZ() + ")");
      }     
      event.setCancelled(true);
      if(firstLocation != null && secondLocation != null) {
      	player.sendMessage("§7[§3§lJNR§7]: §bUse §9/jnr confirm §bto confirm selection");
      }
    }
  }

	public static Location getFirstLocation() {
		return firstLocation;
	}

	public static Location getSecondLocation() {
		return secondLocation;
	}
}

