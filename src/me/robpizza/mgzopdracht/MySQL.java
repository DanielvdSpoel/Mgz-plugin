package me.robpizza.mgzopdracht;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
	
	private String HOST = "";
	private String DATABASE = "";
	private String USER = "";
	private String PASSWORD = "";
	
	private Connection connection;
	
	public MySQL(String host, String database, String user, String password) {
		this.HOST = host;
		this.DATABASE = database;
		this.USER = user;
		this.PASSWORD = password;
		
		connect();
	}

	private void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?autoReconnect=true", USER, PASSWORD);
			System.out.println("[MySQL] connectie is gelukt!");
		} catch(SQLException e) {
			System.out.println("[MySQL] Connectie is niet gelukt! melding: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void close() {
		try { 
			
			if(connection != null) {
				connection.close();
				System.out.println("[MySQL] Connectie is succesvol verbroken!");
			}
		} catch (SQLException e) {
			System.out.println("[MySQL] Connectie is niet succesvol afgesloten! Melding: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void update(String qry) {
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			connect();
			System.err.println(e);
		}
	}
	
	public ResultSet query(String qry) {
		
		ResultSet rs = null;
		
		try {
			Statement st = connection.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			connect();
			System.err.println(e);
		}
		return rs;
	}
	
}
