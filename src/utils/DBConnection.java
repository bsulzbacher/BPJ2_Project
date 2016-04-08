package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBConnection {

	static final String DRIVER = "com.mysql.jdbc.Driver";  
	static final String URL = "jdbc:mysql://c02-dev.space/Sanierung";
	static final String USER = "c02user";
	static final String PASSWORD = "njjoic902pmDAFckja8";
	   
	private static Connection connection = null;  
	
	public static Connection getConnection() throws IOException, SQLException, ClassNotFoundException {
		if(connection != null) {
			return connection;
		}
		else {
			//Create Database Connection
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			return connection;
		}
	}
}
