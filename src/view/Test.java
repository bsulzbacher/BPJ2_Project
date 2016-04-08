package view;

import java.io.IOException;
import java.sql.SQLException;

import utils.ConnectionHelper;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		ConnectionHelper con = new ConnectionHelper();
		String type = con.getTableType(0);
		System.out.println("Table type: " + type);
	}
}
