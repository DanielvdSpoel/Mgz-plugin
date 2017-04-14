package me.robpizza.mgzopdracht.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.robpizza.mgzopdracht.SQLStats;

public class JoinEvent implements Listener, CommandExecutor {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		SQLStats.createPlayer(p.getName());
		setScoreboard(p);
		
		
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if(p.getKiller() instanceof Player) {
			SQLStats.addDeaths(p.getName(), 1);
			SQLStats.addKills(p.getKiller().getName(), 1);
		} else {
			SQLStats.addDeaths(p.getName(), 1);
		}
	}
	
	
	public void setScoreboard(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective o = board.registerNewObjective("Stats", "dummy");
		
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.setDisplayName("Stats");
		
		Score kills = o.getScore(ChatColor.GREEN + "Kills: ");
		Score deaths = o.getScore(ChatColor.GREEN + "Deaths: ");
		
		kills.setScore(SQLStats.getKills(p.getName()));
		deaths.setScore(SQLStats.getDeaths(p.getName()));
		
		p.setScoreboard(board);
	}
	
	public void removeScoreboard(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		board.resetScores(p.getName());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if(!(sender instanceof Player)) {
			sender.sendMessage("You can only run commands as player!");
			return true;
		} else {
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("stats")) {
				if(args.length == 0) {
					p.sendMessage("[]=====[Stats]=====[]");
					p.sendMessage("");
					p.sendMessage("Kills: " + SQLStats.getKills(p.getName()));
					p.sendMessage("Deaths: " + SQLStats.getDeaths(p.getName()));
					p.sendMessage("");
					p.sendMessage("[]================[]");
					return true;
				}
			}
		}
		return false;
	}
}
