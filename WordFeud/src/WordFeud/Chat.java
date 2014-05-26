package WordFeud;

import GUI.ChatPanel;
import Utility.DBCommunicator;

public class Chat implements Runnable {
	
	private final boolean chat = true;
	private int gameID;
	private ChatPanel chatPanel;
	private Thread t;

	public Chat(int gameID, ChatPanel cp){
		this.gameID = gameID;
		this.chatPanel = cp;
		t = new Thread();
		t.start();
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
			
			try{
				chatPanel.setChatText(DBCommunicator.getChat(gameID));
				
				t.sleep(5000);
			}
			catch(InterruptedException e){
				
			}			
		}		
	}
	
}
