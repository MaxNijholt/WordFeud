package AccountType;

import java.util.ArrayList;

import Utility.DBCommunicator;


public class Administrator{

	public void addPrivilege(String player, String right){
		if(DBCommunicator.requestData("SELECT rol_type FROM accountrol WHERE account_naam='"+ player +"' AND rol_type='"+ right +"'")==null){
			DBCommunicator.writeData("INSERT INTO accountrol (account_naam, rol_type) VALUES('" + player + "','" + right + "')");
		}
	}
	
	public void removePrivilege(String player, String right){
		if(this.getUserRights(player).size()>1)
		DBCommunicator.writeData("DELETE FROM accountrol WHERE account_naam='" + player + "' AND rol_type='" + right + "'");
	}
	
	public ArrayList<String> getUserRights(String player) {
		return DBCommunicator.requestMoreData("SELECT rol_type FROM accountrol WHERE account_naam='" + player + "'");
	}
	
	public void changeUsername(Account account, String newName) {
		if(!DBCommunicator.requestData("SELECT naam FROM account WHERE naam='"+newName+"'").equals(newName) || DBCommunicator.requestData("SELECT naam FROM account WHERE naam='"+newName+"'") == null){
			DBCommunicator.writeData("UPDATE account SET naam = '" + newName +"' WHERE naam='" + account.getUsername() + "'");
		}
	}

	public void changePassword(Account account, String newPass) {
		DBCommunicator.writeData("UPDATE account SET wachtwoord = '" + newPass +"' WHERE naam='" + account.getUsername() + "'");
	}
	
}
