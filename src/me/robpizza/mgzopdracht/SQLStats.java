package me.robpizza.mgzopdracht;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLStats {

	public static void createPlayer(String name) {
		Main.mysql.update("INSERT INTO players(name, kills, deaths) VALUES ('" + name + "', '0', '0') ON DUPLICATE KEY UPDATE name=name;");
	}


	public static Integer getKills(String name) {
		Integer i = 0;
		try {
			ResultSet rs = Main.mysql.query("SELECT * FROM players WHERE name= '" + name + "'");
			if((!rs.next()) || (Integer.valueOf(rs.getInt("KILLS")) == null)) {
				createPlayer(name);
				getKills(name);
			}

			i = rs.getInt("KILLS");

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public static Integer getDeaths(String name) {
		Integer i = 0;
		try {
			ResultSet rs = Main.mysql.query("SELECT * FROM players WHERE name= '" + name + "'");
			if((!rs.next()) || (Integer.valueOf(rs.getInt("DEATHS")) == null)) {
				createPlayer(name);
				getDeaths(name);
			}

			i = rs.getInt("DEATHS");

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public static void setKills(String name, Integer kills) {
		Main.mysql.update("UPDATE players SET KILLS= '" + kills + "' WHERE name='" + name + "';");
	}

	public static void setDeaths(String name, Integer deaths) {
		Main.mysql.update("UPDATE players SET DEATHS= '" + deaths + "' WHERE name='" + name + "';");
	}

	public static void addKills(String name, Integer kills) {
		setKills(name, Integer.valueOf(getKills(name).intValue() + kills.intValue()));
	}

	public static void addDeaths(String name, Integer deaths) {
		setDeaths(name, Integer.valueOf(getDeaths(name).intValue() + deaths.intValue()));
	}
}
