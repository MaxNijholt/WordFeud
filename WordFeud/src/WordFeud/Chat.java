package WordFeud;

import Utility.DBCommunicator;
import AccountType.Account;

public class Chat {

	public Chat(){
		
	}
	
	public void sendMsg(String msg, Game game, Account player){
		String username = player.getUsername();
		int gameID = game.getID();
		System.out.println(username);
		System.out.println(gameID);
		DBCommunicator.writeData("INSERT INTO chatregel (account_naam, spel_id, tijdstip, bericht) VALUES(" + username + ", '" + gameID +"',  CURRENT_TIMESTAMP(), '" + msg + "')");
	}
	
	public String getMsg(){
		return "";
	}
	
}
