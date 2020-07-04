package de.trizion.jumpnrun.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import de.trizion.jumpnrun.main.Main;

public class ScoreBoardManager {
  private int score = 0;
  private int highScore = 0;
  private Scoreboard sb;
  private Objective objective;
  
  public ScoreBoardManager(UUID uuid) {
    this.sb = Bukkit.getScoreboardManager().getNewScoreboard();
    this.objective = this.sb.registerNewObjective("abcd", "abcd", "abcd");
    this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    this.objective.setDisplayName(ConfigManager.getScoreBoardTitle());
    this.highScore = Main.getConfigManager().getSavedHighScore(uuid);
    this.objective.getScore("§6Score: ").setScore(this.score);
    this.objective.getScore("§6Highscore: ").setScore(this.highScore);
  }
  
  public int getScore() {
    return this.score;
  }
  
  public void incScore() {
    this.objective.getScore("§6Score: ").setScore(this.score + 1);
    this.score ++;
  }
  
  public int getHighScore() {
    return this.highScore;
  }
  
  public void incHighScore() {
    this.objective.getScore("§6Highscore: ").setScore(this.highScore + 1);
    this.highScore ++;
  }
  
  public void resetScore() {
    this.score = 0;
    this.objective.getScore("§6Score: ").setScore(0);
  }
  
  public Scoreboard getScoreBoard() {
    return this.sb;
  }
  
  public void saveHighScore(UUID uuid) {
  	if(this.highScore > Main.getConfigManager().getSavedHighScore(uuid)) {
  		Main.getConfigManager().setSavedHighScore(uuid, this.highScore);
  	}
  }
}
