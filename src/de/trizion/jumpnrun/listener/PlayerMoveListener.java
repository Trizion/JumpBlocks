package de.trizion.jumpnrun.listener;

import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.trizion.jumpnrun.util.ConfigManager;
import de.trizion.jumpnrun.util.JumpBlock;

public class PlayerMoveListener implements Listener{
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();
    if(GroundClickListener.getInitialBlock() != null && 
        JumpBlock.getLastLoc(uuid) != null &&
        JumpBlock.getLastLoc(uuid).getBlockY() - 1 >= 
        player.getLocation().getY()) {
      
      player.sendTitle(ConfigManager.getDropMessage(), "§b§lScore: " + GroundClickListener.getScoreManager(uuid).getScore(), 1, 40, 5);
      player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
      player.playSound(GroundClickListener.getInitialLoc(), Sound.ITEM_SHIELD_BREAK, 1, 1);
      player.teleport(GroundClickListener.getInitialLoc());     
      endRun(uuid);
    } 
  }
  
  public void endRun(UUID uuid) {
  	JumpBlock.terminate(uuid);
    GroundClickListener.getScoreManager(uuid).saveHighScore(uuid);
  }
}
