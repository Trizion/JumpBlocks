package de.trizion.jumpnrun.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.trizion.jumpnrun.command.JnrCommand;
import de.trizion.jumpnrun.listener.GroundClickListener;
import de.trizion.jumpnrun.listener.LandOnBlockListener;
import de.trizion.jumpnrun.listener.LocationSelectListener;
import de.trizion.jumpnrun.listener.PlayerMoveListener;
import de.trizion.jumpnrun.util.ConfigManager;

public class Main extends JavaPlugin{
	private static Main plugin;
	private static ConfigManager configManager;
  
  public void onEnable() {
  	plugin = this;
  	configManager = new ConfigManager();
    PluginManager pm = Bukkit.getPluginManager();
    pm.registerEvents(new GroundClickListener(), this);
    pm.registerEvents(new LandOnBlockListener(), this);
    pm.registerEvents(new PlayerMoveListener(), this);
    pm.registerEvents(new LocationSelectListener(), this);
    
    getCommand("jnr").setExecutor(new JnrCommand());
  }
  
  public static Main getPlugin() {
  	return plugin;
  }
  
  public static ConfigManager getConfigManager() {
  	return configManager;
  }
}
