package de.trizion.jumpnrun.listener;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import de.trizion.jumpnrun.util.JumpBlock;
import de.trizion.jumpnrun.util.ScoreBoardManager;

public class GroundClickListener implements Listener{
  private static JumpBlock initialBlock;
  private static Location initialLoc;
  private static HashMap<UUID, ScoreBoardManager> scoreManagers = new HashMap<UUID, ScoreBoardManager>();  
  
  @EventHandler
  public void onGroundClick(PlayerInteractEvent event) {
    if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_BOOTS
        && event.getAction() == Action.LEFT_CLICK_BLOCK) {
    	UUID uuid = event.getPlayer().getUniqueId();
    	if(scoreManagers.containsKey(uuid)) {
    		scoreManagers.replace(uuid, new ScoreBoardManager(uuid));
    	}else {
    		scoreManagers.put(uuid, new ScoreBoardManager(uuid));
    	}
      JumpBlock.terminate(uuid);
      initialBlock = new JumpBlock(uuid, event.getClickedBlock(), true);
      initialLoc = event.getClickedBlock().getLocation().add(0, 1, 0);
      initialBlock.place(uuid);
      event.getPlayer().setScoreboard(scoreManagers.get(uuid).getScoreBoard());
      event.setCancelled(true);
    }
  }
  
  public static JumpBlock getInitialBlock() {
    return initialBlock;
  }
  
  public static Location getInitialLoc() {
    return initialLoc;
  }
  
  public static ScoreBoardManager getScoreManager(UUID uuid) {
    return scoreManagers.get(uuid);   
  }
}
