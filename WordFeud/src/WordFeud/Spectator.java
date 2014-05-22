package WordFeud;

import Utility.DBCommunicator;

public class Spectator {
	private Field myField;
	private int myGameID, lastTurn, turnCount, turn;
	private String letters;
	
	public Spectator(int gameID){
		myGameID = gameID;
		myField = new Field(myGameID);
		
		
		
	}

	public String getLastTurnLetters() {
		lastTurn = DBCommunicator.requestInt("SELECT id from beurt WHERE spel_id = " + myGameID + " ORDER BY id DESC");
		letters = DBCommunicator.requestData("SELECT inhoud FROM plankje WHERE spel_id = " + myGameID + " AND beurt_id = " + lastTurn);
		return letters;
	}
	
	public String getTurnLetters(int turn){
		if(turn == DBCommunicator.requestInt("SELECT id from beurt WHERE spel_id = " + myGameID + " AND id = " + turn + " ORDER BY id DESC")){
			letters = DBCommunicator.requestData("SELECT inhoud FROM plankje WHERE spel_id = " + myGameID + " AND beurt_id = " + turn);
			return letters;
		}
		else{
			System.out.println("invalid turn");
			return null;
		}
		
	}
	

	public Field getMyField() {
		return myField;
	}
	
	public Field nextTurn(){
		
		
		return myField;
	}
	
	public Field previousTurn(){
		
		
		return myField;
	}
	
	
	
	
}
