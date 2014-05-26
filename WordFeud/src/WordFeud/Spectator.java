package WordFeud;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import Utility.DBCommunicator;

public class Spectator {
	private Field myField;
	private int myGameID, lastTurn, turnCount, turn;
	private String letters;
	private ArrayList<Integer> gameStones;
	private HashMap<Integer, Character> stoneChars;
	
	public Spectator(int gameID){
		myGameID = gameID;
		myField = new Field(myGameID);
		gameStones = new ArrayList<Integer>();
		stoneChars = new HashMap<Integer, Character>();
		
		
	}
	
	public int getLastTurn(){
		lastTurn = DBCommunicator.requestInt("SELECT id from beurt WHERE spel_id = " + myGameID + " AND aktie_type != 'End' ORDER BY id DESC");
		return lastTurn;
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
	
	public Field getTurn(int turn){
		
		return myField;
	}
	

	public ArrayList<Integer> getGameStones(){
		return gameStones;
	}
	
	public HashMap<Integer, Character> getStoneChars(){
		return stoneChars;
	}
	
	public int getCompID(int gameID){
		int compID = DBCommunicator.requestInt("SELECT competitie_id FROM spel where id = '"+ gameID + "'");
		return compID;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	
}
