package me.robpizza.mgzopdracht;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.robpizza.mgzopdracht.events.JoinEvent;

public class Main extends JavaPlugin implements Listener {
	Main instance;

	public static MySQL mysql;

	@Override
	public void onEnable() {  
		instance = this;
		ConnectMySQL();

		Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		this.getCommand("stats").setExecutor(new JoinEvent());
		this.getCommand("refresh").setExecutor(new JoinEvent());
	}

	@Override
	public void onDisable() {
		mysql.close();
	}

	
	private void ConnectMySQL() {
		mysql = new MySQL("localhost", "Mgz", "Mgz", "Password");
	}

}
