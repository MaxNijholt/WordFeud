package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DBCommunicator {

	/* CONSTANTS */
	private static final String CLASS_NAME = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://databases.aii.avans.nl:3306/mnijholt_db2";
	private static final String DB_USERNAME = "mnijholt";
	private static final String DB_PASSWORD = "42IN04SOi";

	/**
	 * This method allows you to request data from the Database.</br> It uses a
	 * query and a column, so you can get all the records from a certain
	 * column.</br> MUST fill in both variables!</br> Example:
	 * "select name from account;"</br> This will return the first record in the
	 * Database.
	 */
	public static String requestData(String query) {
		Connection con;
		Statement stm;
		ResultSet res;
		String result = null;
		try {
			Class.forName(CLASS_NAME);
			con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			stm = con.createStatement();
			res = stm.executeQuery(query + " limit 1;");

			while (res.next()) {
				result = res.getString(1);
			}
			res.close();
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static ArrayList<String> requestMoreData(String query) {
		Connection con;
		Statement stm;
		ResultSet res;
		ArrayList<String> result = new ArrayList<String>();
		try {
			Class.forName(CLASS_NAME);
			con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			stm = con.createStatement();
			res = stm.executeQuery(query);

			while(res.next()){
				result.add(res.getString(1));
			}
			res.close();
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This method allows you to write data to the Database.</br> It uses a
	 * query as variable.</br> Example:
	 * "INSERT INTO account(naam, wachtwoord) VALUES('test', '123')"</br> This
	 * method adds the query to the Database.
	 */
	public static void writeData(String query) {
		Connection con;
		PreparedStatement stm;

		try {
			Class.forName(CLASS_NAME);
			con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			stm = con.prepareStatement(query);
			stm.executeUpdate();
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeNamePassword(String name, String password) {
		Connection con;
		PreparedStatement stm;

		try {
			Class.forName(CLASS_NAME);
			con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			stm = con
					.prepareStatement("INSERT INTO account(naam, wachtwoord) VALUES("
							+ "'" + name + "', '" + password + "')");
			stm.executeUpdate();
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
