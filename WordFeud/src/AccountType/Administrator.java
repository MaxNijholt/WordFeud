package AccountType;

import java.util.ArrayList;

import Utility.DBCommunicator;


public class Administrator{

	public void addPrivilege(String player, String right){
		DBCommunicator.writeData("INSERT INTO accountrol (account_naam, rol_type) VALUES('"
							+ player + "','" + right + "')");
		
	}
	
	public void removePrivilege(String player, String right){
		DBCommunicator.writeData("DELETE FROM accountrol WHERE account_naam='"
				+ player + "' AND rol_type='" + right + "'");
	}
	
	public ArrayList<String> getUserRights(String player) {
		ArrayList<String> data = DBCommunicator
				.requestMoreData("SELECT rol_type FROM accountrol WHERE account_naam='"
						+ player + "'");
		return data;
	}
	
	public void newPlayer(){
		
	}
	
}
