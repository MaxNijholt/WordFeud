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
		myField = new Field();
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
		int points = myPC.counterPointsTurn(myField.getNewWords());
		
		return points;
	}
	
	/**
	 * get the new words from the field
	 * let the wordchecker check if it is correct
	 * -------------------------------------------------tj vragen
	 */
	public String[] playWord(){
		myField.getNewWords();
		
		return null;
	}
	
	/**
	 * check if the game has ended
	 * -------------------------------------------------rest vragen
	 * @return
	 */
	public boolean checkEndGame(){
		return false;
	}
	
	/**
	 * pass a turn
	 * tell the DB
	 * -------------------------------------------------
	 */
	public void pass(){
		/*
		 * tell the DB it's the opponents turn
		 * tell the DB the turn is passed
		 */
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
		if(visibility.equals("openbar")){
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
