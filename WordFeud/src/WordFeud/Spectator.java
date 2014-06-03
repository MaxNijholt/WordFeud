package WordFeud;

import java.util.ArrayList;
import java.util.HashMap;

import Utility.DBCommunicator;
import Utility.Loader;

public class Spectator {
	private Field myField;
	private int myGameID, lastTurn, turn;
	private String letters;
	@SuppressWarnings("unused")
	private ArrayList<Integer> gameStones;
	@SuppressWarnings("unused")
	private HashMap<Integer, Character> stoneChars;
	private ArrayList<GameStone> handStones;
	
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
		myField.updateField(myGameID);
		return myField;
	}
	
	
	public int getCompID(int gameID){
		int compID = DBCommunicator.requestInt("SELECT competitie_id FROM spel where id = '"+ gameID + "'");
		return compID;
	}	
	

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public ArrayList<GameStone> getHand(int turn){
		handStones = Loader.getGameStones("EN");
		handStones = DBCommunicator.getHandLetters(myGameID, turn, handStones);
		return handStones;
	}
	
	
}
