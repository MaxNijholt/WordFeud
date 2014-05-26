package WordFeud;

import Utility.DBCommunicator;
import AccountType.Account;

public class Chat {

	public Chat(){
		
	}
	
	public void sendMsg(String msg, int gameID, String username){
		DBCommunicator.writeData("INSERT INTO chatregel (account_naam, spel_id, tijdstip, bericht) VALUES('" + username + "', " + gameID +",  CURRENT_TIMESTAMP(), '" + msg + "')");
	}
	
	public String getMsg(){
		return "";
	}
	
}
