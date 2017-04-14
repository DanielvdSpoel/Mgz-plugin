package me.robpizza.mgzopdracht;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLStats {

	public static boolean playerExists(String name) {

		try {
			ResultSet rs = Main.mysql.query("SELECT * FROM players WHERE name= '" + name + "'");

			if(rs.next()) {

				return rs.getString("name") != null;
			} 
			return false;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public static void createPlayer(String name) {
		
		if(!(playerExists(name))) {
			Main.mysql.update("INSERT INTO players(name, kills, deaths) VALUES ('" + name + "', '0', '0');");
		}
	}
	
	public static Integer getKills(String name) {
		Integer i = 0;
		
		if(playerExists(name)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM players WHERE name= '" + name + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("KILLS")) == null));
				
				i = rs.getInt("KILLS");
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}  else {
			createPlayer(name);
			getKills(name);
		}
		
		return i;
	}
	
	public static Integer getDeaths(String name) {
		Integer i = 0;
		
		if(playerExists(name)) {
			try {
				ResultSet rs = Main.mysql.query("SELECT * FROM players WHERE name= '" + name + "'");
				if((!rs.next()) || (Integer.valueOf(rs.getInt("DEATHS")) == null));
				
				i = rs.getInt("DEATHS");
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}  else {
			createPlayer(name);
			getDeaths(name);
		}
		
		return i;
	}
	
	public static void setKills(String name, Integer kills) {
		if(playerExists(name)) {
			Main.mysql.update("UPDATE players SET KILLS= '" + kills + "' WHERE name='" + name + "';");
		} else {
			createPlayer(name);
			setKills(name, kills);
		}
	}
	
	public static void setDeaths(String name, Integer deaths) {
		if(playerExists(name)) {
			Main.mysql.update("UPDATE players SET DEATHS= '" + deaths + "' WHERE name='" + name + "';");
		} else {
			createPlayer(name);
			setDeaths(name, deaths);
		}
	}
	
	public static void addKills(String name, Integer kills) {
		if(playerExists(name)) {
			setKills(name, Integer.valueOf(getKills(name).intValue() + kills.intValue()));
		} else {
			createPlayer(name);
			addKills(name, kills);
		}
	}
	
	public static void addDeaths(String name, Integer deaths) {
		if(playerExists(name)) {
			setDeaths(name, Integer.valueOf(getDeaths(name).intValue() + deaths.intValue()));
		} else {
			createPlayer(name);
			addDeaths(name, deaths);
		}
	}
	
}
