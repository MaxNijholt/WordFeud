package WordFeud;

import Core.Application;
import Utility.DBCommunicator;
import Utility.PointCounter;
import Utility.WordChecker;

public class Game {

	private Field myField;
	private PointCounter myPC;
	private WordChecker myWC;
	private Application app;
	private int id;
	private String opponent;
	
	/**
	 * cronstruct the game
	 * -------------------------------------------------
	 */
	public Game(int gameID, Application app){
		this.app = app;
		this.id = gameID;
		myField = new Field(id);
		myPC = new PointCounter(myField);
		myWC = new WordChecker();
	}
	
	/**
	 * tell field to lay a gamestone
	 * let pointcounter calculate the points
	 * -------------------------------------------------tj vragen
	 * @param gamestone
	 * @param location
	 * @return
	 */
	public int layGameStone(GameStone gamestone, String location){
		myField.layGameStone(gamestone, location);
		int points = 0;//myPC.counterPointsTurn(myField.getNewWords());
		
		return points;
	}
	
	/**
	 * get the new words from the field
	 * let the wordchecker check if it is correct
	 * -------------------------------------------------tj vragen
	 */
	public String[] playWord(){
		myField.getNewWords();
		//myWC.checkWords(myField.getNewWords(), playedMove, playBoard)
		
		return null;
	}
	
	/**
	 * check if the game has ended
	 * -------------------------------------------------rest vragen
	 * @return
	 */
	public void endGame(){
		
	}
	
	/**
	 * pass a turn
	 * tell the DB
	 */
	public void pass(){
		boolean thirdPass = false;
		int lastTurnID = DBCommunicator.requestInt("SELECT id FROM beurt WHERE spel_id = " + id + " AND account_naam = '" + app.getCurrentAccount().getUsername() + "' ORDER BY id DESC");
		String lastTurn = DBCommunicator.requestData("SELECT aktie_type FROM beurt WHERE spel_id = " + id + " AND id = " + lastTurnID);
		if(lastTurn != null){
			if(lastTurn.equals("Pass")){
				int secondLastTurnID = lastTurnID - 2;
				String secondLastTurn = DBCommunicator.requestData("SELECT aktie_type FROM beurt WHERE spel_id = " + id + " AND id = "+ secondLastTurnID);
				if(secondLastTurn.equals("Pass")){
					endGame();
					thirdPass = true;
				}
			}
		}
		
		if(!thirdPass){
			int newTurn = lastTurnID + 2;
			DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type) VALUES (" + newTurn + ", " + id + ", '" + app.getCurrentAccount().getUsername() + "', 0, 'Pass')");
		}
	}
	
	/**
	 * tell the DB to swap
	 * get new gamestones
	 * -------------------------------------------------
	 */
	public void swapGameStones(){
		/*
		 * tell the DB it's the opponents turn
		 * tell the DB the swapped gamestones
		 * get new gamestones
		 * tell the db the new gamestones
		 */
	}
	
	/**
	 * shuffle your gamestones around
	 * does not end turn
	 * -------------------------------------------------
	 */
	public void shuffle(){
		/*
		 * show the gamestones in a different order
		 */
	}
	
	/**
	 * get the visibility from the db and return boolean
	 * @return
	 */
	public boolean getVisibility(){
		String visibility = DBCommunicator.requestData( "SELECT zichtbaarheid_type FROM spel WHERE id = " + id );
		if(visibility.equals("openbaar")){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * getter and setter
	 * @return
	 */
	public Field getMyField() {
		return myField;
	}

	public void setMyField(Field myField) {
		this.myField = myField;
	}

	public int getID() {
		return id;
	}
	
	public String getOpponent(){
		return opponent;
	}
}
