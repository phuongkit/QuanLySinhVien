package jdbc;

import java.util.*;
import java.sql.*;

public class JDBCConnection {
	public static Connection initializeDatabase() throws SQLException{
		// Initialize all the information regarding 
		// Database Connection
		
		String dbDriver = "com.mysql.cj.jdbc.Driver";
		String dbURL = "jdbc:mysql://localhost:3306"; 
		// Database name to access 
		String dbName = "QuanLyDonHang"; 
		String dbUsername = "root";
		String dbPassword = "12345"; 
		String connectionURL = dbURL + "/" + dbName;
		Connection conn = null;
		try { 
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(connectionURL, dbUsername, dbPassword);
			System.out.println("connect successfully!");
		}catch (Exception ex) {
			System.out.println("connect failure!" ) ; 
			ex.printStackTrace();
		}
		return conn; 
	}
}