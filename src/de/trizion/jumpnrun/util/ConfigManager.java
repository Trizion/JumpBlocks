package de.trizion.jumpnrun.util;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;

import de.trizion.jumpnrun.main.Main;

public class ConfigManager {
	private static FileConfiguration fc;
	private static Material blockMaterial;
	private static Material plateMaterial;
	private static Particle particle;
	private static String dropMessage;
	private static String scoreBoardTitle;
	private static Location firstBound;
	private static Location secondBound;
	private static Boolean ignoreBound = true;

	public ConfigManager() {
		fc = Main.getPlugin().getConfig();
		HashMap<String, Particle> particles = new HashMap<>();
		for(Particle p : Particle.values()) {
			particles.put(p.name(), p);
		}
		blockMaterial = Material.getMaterial(fc.getString("Plugin.Material.Block"));
		plateMaterial = Material.getMaterial(fc.getString("Plugin.Material.Pressure Plate"));
		particle = particles.containsKey(fc.getString("Plugin.Material.Particle")) ? particles.get(fc.getString("Plugin.Material.Particle")) : Particle.FLAME;
		dropMessage = fc.getString("Plugin.Language.Drop");
		scoreBoardTitle = fc.getString("Plugin.Language.Scoreboard Title");
		ignoreBound = Boolean.valueOf(fc.getString("Plugin.Bounds.Ignore"));
		if(!ignoreBound) {
			firstBound = new Location(Bukkit.getWorld(fc.getString("Plugin.Bounds.First.World")), fc.getDouble("Plugin.Bounds.First.X"), 
					fc.getDouble("Plugin.Bounds.First.Y"), fc.getDouble("Plugin.Bounds.First.Z"));
			secondBound = new Location(Bukkit.getWorld(fc.getString("Plugin.Bounds.Second.World")), fc.getDouble("Plugin.Bounds.Second.X"), 
					fc.getDouble("Plugin.Bounds.Second.Y"), fc.getDouble("Plugin.Bounds.Second.Z"));
		}
	}
		
	
	public int getSavedHighScore(UUID uuid) {
		return fc.getInt("Plugin.Scores." + uuid);
	}
	
	public void setSavedHighScore(UUID uuid, int score) {
		fc.set("Plugin.Scores." + uuid, score);
		Main.getPlugin().saveConfig();
	}
	
	public static Material getBlockMaterial() {
		return blockMaterial;
	}
	
	public static Material getPlateMaterial() {
		return plateMaterial;
	}
	
	public static Particle getParticle() {
		return particle;
	}
	
	public static String getDropMessage() {
		return dropMessage;
	}
	
	public static String getScoreBoardTitle() {
		return scoreBoardTitle;
	}
	
	public static Location getFirstBound() {
		return firstBound;
	}
	
	public static Location getSecondBound() {
		return secondBound;
	}
	
	public static Boolean getIgnoreBound() {
		return ignoreBound;
	}
	
	public static void setBound(String order, Location location) {
		fc.set("Plugin.Bounds." + order + ".World", location.getWorld().getName());
		fc.set("Plugin.Bounds." + order + ".X", location.getX());
		fc.set("Plugin.Bounds." + order + ".Y", location.getY());
		fc.set("Plugin.Bounds." + order + ".Z", location.getZ());
		fc.set("Plugin.Bounds.Ignore", "false");
		Main.getPlugin().saveConfig();
		firstBound = order.equals("First") ? location : firstBound; 
		secondBound = order.equals("Second") ? location : secondBound; 
	}
}
