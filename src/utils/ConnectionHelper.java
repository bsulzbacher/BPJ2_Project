package utils;

import java.io.IOException;
import java.sql.*;

public class ConnectionHelper {

	private Connection connection;
	
	public ConnectionHelper() throws ClassNotFoundException, IOException, SQLException{
		connection = DBConnection.getConnection();
	}
	
	public String getTableType(int tablename) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT Strasse from Adresse where idAdresse = ?");
		stmt.setInt(1, tablename);
		ResultSet rs= stmt.executeQuery();
		String type = "no table type";
		while(rs.next()) {
			type = rs.getString("Strasse");
		}
		return type;
	}
	
}
