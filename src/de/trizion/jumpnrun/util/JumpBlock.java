package de.trizion.jumpnrun.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import de.trizion.jumpnrun.listener.GroundClickListener;

public class JumpBlock {
  private static Location blockLoc;
  private static HashMap<UUID, Location> nextLoc = new HashMap<UUID, Location>();
  private static HashMap<UUID, Location> beforeLoc = new HashMap<UUID, Location>();

  public JumpBlock(UUID uuid, Block clickedBlock, boolean startBool) {
  	ArrayList<Integer> offset = generateOffset(startBool, ConfigManager.getIgnoreBound(), 
  			clickedBlock.getLocation(), ConfigManager.getFirstBound(), ConfigManager.getSecondBound());
    blockLoc = clickedBlock.getLocation().clone().add(offset.get(0), offset.get(1), offset.get(2));
    if(beforeLoc.containsKey(uuid)) {
      beforeLoc.get(uuid).clone().add(0, 1, 0).getBlock().setType(Material.AIR);
      beforeLoc.get(uuid).getBlock().setType(Material.AIR);
    }
    if(nextLoc.containsKey(uuid)) {
      if(beforeLoc.containsKey(uuid)) {
        beforeLoc.replace(uuid, nextLoc.get(uuid));
      }else {
        beforeLoc.put(uuid, nextLoc.get(uuid));
      }
      nextLoc.replace(uuid, blockLoc);
      GroundClickListener.getScoreManager(uuid).incScore();
      if(GroundClickListener.getScoreManager(uuid).getScore() > GroundClickListener.getScoreManager(uuid).getHighScore()) {
        GroundClickListener.getScoreManager(uuid).incHighScore();
      }
    }else {
      nextLoc.put(uuid, blockLoc);
    }
  }
  
  private ArrayList<Integer> generateOffset(boolean start, Boolean ignoreBound, Location current, Location firstBound, Location secondBound){
  	ArrayList<Integer> offset = new ArrayList<Integer>();
    Random rand = new Random();
  	int randX = - rand.nextInt(7) + 3;
    int randY = start ? 1 : rand.nextInt(2);
    int randZ = - rand.nextInt(7) + 3;
    if(Math.abs(randX) <= 1 && Math.abs(randZ) <= 1) {
      randX = Math.abs(randX) + 2;
    }
    if(ignoreBound) {
      offset.add(randX);
      offset.add(randY);
      offset.add(randZ);
    	return offset;
    }
    int minX = Math.min(firstBound.getBlockX(), secondBound.getBlockX());
  	int minY = Math.min(firstBound.getBlockY(), secondBound.getBlockY());
  	int minZ = Math.min(firstBound.getBlockZ(), secondBound.getBlockZ());
  	int boundX = Math.abs(firstBound.getBlockX() - secondBound.getBlockX());
  	int boundY = Math.abs(firstBound.getBlockY() - secondBound.getBlockY());
  	int boundZ = Math.abs(firstBound.getBlockZ() - secondBound.getBlockZ());
  	
  	if(current.getBlockX() + randX < minX || current.getBlockX() + randX > minX + boundX) randX *= -1;
  	if(current.getBlockY() + randY + 1 > minY + boundY) randY *= -1;
  	if(current.getBlockZ() + randZ < minZ || current.getBlockZ() + randZ > minZ + boundZ) randZ *= -1;
  	
    offset.add(randX);
    offset.add(randY);
    offset.add(randZ);
  	return offset;
  }
  
  public void place(UUID uuid) {
    blockLoc.getBlock().setType(ConfigManager.getBlockMaterial());
    blockLoc.clone().add(0, 1, 0).getBlock().setType(ConfigManager.getPlateMaterial());
    Player player = Bukkit.getPlayer(uuid);
    if(player != null) player.spawnParticle(ConfigManager.getParticle(), blockLoc.clone().add(0.5, 1, 0.5), 20, 0, 0, 0, .02);
  }
  
  public static Location getLastLoc(UUID uuid) {
    return beforeLoc.containsKey(uuid) ?  beforeLoc.get(uuid) : null;
  }
  
  public static Location getNextLoc(UUID uuid) {
    return nextLoc.containsKey(uuid) ?  nextLoc.get(uuid) : null;
  }
  
  public static void terminate(UUID uuid) {
    if(nextLoc.containsKey(uuid)) {
      nextLoc.get(uuid).clone().add(0, 1, 0).getBlock().setType(Material.AIR);
      nextLoc.get(uuid).getBlock().setType(Material.AIR);
      nextLoc.remove(uuid);
    }
    if(beforeLoc.containsKey(uuid)) {
      beforeLoc.get(uuid).clone().add(0, 1, 0).getBlock().setType(Material.AIR);
      beforeLoc.get(uuid).getBlock().setType(Material.AIR);
      beforeLoc.remove(uuid);
    }
    GroundClickListener.getScoreManager(uuid).resetScore();
    Bukkit.getPlayer(uuid).setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
  }
}
