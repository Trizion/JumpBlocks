package de.trizion.jumpnrun.listener;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import de.trizion.jumpnrun.util.JumpBlock;

public class LandOnBlockListener implements Listener{
  
  @EventHandler
  public void onOnBlockLand(PlayerInteractEvent event) {
    if(event.hasBlock()) {
      Location blockLoc = event.getClickedBlock().getLocation().add(0, -1, 0);
      Location goldLoc = JumpBlock.getNextLoc(event.getPlayer().getUniqueId());
      if(goldLoc != null && blockLoc.getBlockX() == goldLoc.getBlockX() && blockLoc.getBlockY() == goldLoc.getBlockY()
          && blockLoc.getBlockZ() == goldLoc.getBlockZ()) {
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
        new JumpBlock(player.getUniqueId(), blockLoc.getBlock(), false).place(player.getUniqueId());
      }
    }  
  }
}
