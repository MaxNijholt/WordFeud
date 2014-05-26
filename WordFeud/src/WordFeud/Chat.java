package WordFeud;

import GUI.ChatPanel;
import Utility.DBCommunicator;

public class Chat implements Runnable {
	
	private boolean chat = true;
	private int gameID;
	private ChatPanel chatPanel;

	public Chat(int gameID, ChatPanel cp){
		this.gameID = gameID;
		this.chatPanel = cp;
	}
	
	public void sendMsg(String msg, int gameID, String username){
		DBCommunicator.writeData("INSERT INTO chatregel (account_naam, spel_id, tijdstip, bericht) VALUES('" + username + "', " + gameID +",  CURRENT_TIMESTAMP(), '" + msg + "')");
	}
	
	public String getMsg(){
		return "";
	}

	@Override
	public void run()
	{
		while(chat){
			chatPanel.setChatText(DBCommunicator.getChat(gameID));
			chat = false;
		}
		
		
	}
	
}
