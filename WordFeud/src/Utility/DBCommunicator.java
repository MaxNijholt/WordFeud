package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import WordFeud.GameStone;

public class DBCommunicator {

	/**
	 * @author Stan van Heumen
	 */
	
	/* CONSTANTS */
	private static final String CLASS_NAME	=	"com.mysql.jdbc.Driver";
	private static final String DB_URL		=	"jdbc:mysql://databases.aii.avans.nl:3306/mnijholt_db2";
	private static final String DB_USERNAME	=	"mnijholt";
	private static final String DB_PASSWORD	=	"42IN04SOi";

	private static Connection con;

	public static void getConnection() {
		try {
			Class.forName(CLASS_NAME);
			con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			System.out.println("A connection has been established with " + DB_URL);
		} 
		catch (SQLException e) {
			System.out.println("SQLException: It was not possible to create a connection with " + DB_URL);
		} 
		catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + CLASS_NAME + "was not found");
		}
	}
	
	public static Connection checkConnection() {return con;}

	/**
	 * This method allows you to request data from the Database.</br>
	 * It uses a query and a column, so you can get all the records from a certain column.</br>
	 * MUST fill in both variables!</br>
	 * Example: "select name from account;"</br>
	 * This will return the first record in the Database.
	 */
	public static String requestData(String query) {
		PreparedStatement	stm;
		ResultSet 			res;
		String				result = null;
		try {
			stm = con.prepareStatement(query);
			res = stm.executeQuery(query + " limit 1;");

			while(res.next()) {
				result = res.getString(1);
			}	
			res.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Max
	 * This method allows you to request a ArrayList of data from the Database.</br>
	 * It uses a query and a column, so you can get all the records from a certain column.</br>
	 * MUST fill in both variables!</br>
	 * Example: "select name from account;"</br>
	 * This will return all records in the Database.
	 */
	public static ArrayList<String> requestMoreData(String query) {
		Statement	stm;
		ResultSet 	res;
		ArrayList<String>		result = new ArrayList<String>();
		try {
			stm = con.createStatement();
			res = stm.executeQuery(query);

			while(res.next()) {
				result.add(res.getString(1));
			}	
			res.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @author Max
	 * @param letterSetCode is either EN or NL
	 * @return hashmap within a hashmap
	 * For an example how this works see Utility/Loader.java
	 */
	public static HashMap<Character , HashMap<Integer, Integer>> requestLetters(String letterSetCode){
		Statement	stm;
		ResultSet 	res;
		HashMap<Character , HashMap<Integer, Integer>> result = new HashMap<Character , HashMap<Integer, Integer>>();
		try {
			stm = con.createStatement();
			res = stm.executeQuery("SELECT waarde, karakter, aantal FROM lettertype WHERE letterset_code='"+ letterSetCode.toUpperCase() +"'");
			while(res.next()) {
				HashMap<Integer, Integer> valueamount = new HashMap<Integer, Integer>();
				valueamount.put(res.getInt(1), res.getInt(3));
				result.put(res.getString(2).toString().charAt(0), valueamount);	
			}
			res.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @author Max
	 * @param "Standard"
	 * @return HashMap<String (Location), String (Bonus)> including the right bonuses and empty locations.
	 */
	public static HashMap<String, String> requestTilesMap(String boardType){
		Statement	stm;
		ResultSet 	res;
		HashMap<String, String> result = new HashMap<String, String>();
		try {
			stm = con.createStatement();
			res = stm.executeQuery("SELECT tegeltype_soort, x, y FROM tegel WHERE bord_naam='"+ boardType +"'");
			while(res.next()) {
				if(		res.getString(1).equals("TW") || 
						res.getString(1).equals("DW") ||
						res.getString(1).equals("TL") || 
						res.getString(1).equals("DL") ||
						res.getString(1).equals("*") 
					){
					String s = "";
					result.put(s = s + res.getInt(2) +"," + res.getInt(3) , res.getString(1));
				}	else{
					String s = "";
					result.put(s = s + res.getInt(2) +"," + res.getInt(3), "");
				}
			}
			res.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @author Max
	 * @param "int gameID, ArrayList<GameStone>"
	 * @return HashMap<String (Location), String (Bonus)> including the right bonuses and empty locations.
	 */
	public static void generateStoneIDs(int gameID, ArrayList<GameStone> gameStones){
		PreparedStatement	stm;
		try {
			for(GameStone gs : gameStones){
				stm = con.prepareStatement("INSERT INTO letter (spel_id, lettertype_letterset_code, lettertype_karakter) VALUES('"
						+ gameID + "','" + gs.getLetterSet() + "', '" + gs.getLetter() + "')");
				stm.executeUpdate();
				stm.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Max
	 * @param "int gameID, ArrayList<GameStone>"
	 * @return HashMap<String (Location), String (Bonus)> including the right bonuses and empty locations.
	 */
	public static ArrayList<GameStone> getGeneratedStoneIDs(int gameID, ArrayList<GameStone> gameStones){
		Statement	stm;
		ResultSet 	res;
//		gameStones = Loader.getGameStones(gameStones.get(0).getLetterSet().toUpperCase());
		try {
				stm = con.createStatement();
				res = stm.executeQuery("SELECT id, lettertype_karakter FROM letter WHERE spel_id='" + gameID + "' AND lettertype_letterset_code='" + "EN" + "'");
				while(res.next()) {
					for(GameStone gs : gameStones){
						if(res.getString(2).equals(gs.getLetter()) && gs.getID() == -1){
							gs.setID(res.getInt(1));
							System.out.println(gs.getID());
						}
					}
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return gameStones;
	}

	/**
	 * This method allows you to write data to the Database.</br>
	 * It uses a query as variable.</br>
	 * Example: "INSERT INTO account(naam, wachtwoord) VALUES('test', '123')"</br>
	 * This method adds the query to the Database.
	 */
	public static void writeData(String query) {
		PreparedStatement	stm;

		try {
			stm = con.prepareStatement(query);
			stm.executeUpdate();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeNamePassword(String name, String password) {
		PreparedStatement	stm;

		try {
			stm = con.prepareStatement("INSERT INTO account(naam, wachtwoord) VALUES(" + "'" + name + "', '" + password + "')");
			stm.executeUpdate();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static int requestInt(String query) {
		Statement	stm;
		ResultSet 	res;
		int		result = 0;
		try {
			stm = con.createStatement();
			res = stm.executeQuery(query + " limit 1;");

			while(res.next()) {
				result = res.getInt(1);
			}	
			res.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}