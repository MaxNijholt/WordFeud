package WordFeud;

import Utility.DBCommunicator;
import Utility.PointCounter;
import Utility.WordChecker;

public class Game {

	private Field myField;
	private int myID;
	private PointCounter myPC;
	private WordChecker myWC;
	
	/**
	 * cronstruct the game
	 * -------------------------------------------------
	 */
	public Game(int id){
		myID = id;
		myField = new Field();
		myPC = new PointCounter();
		myWC = new WordChecker();
		
	}
	
	/**
	 * tell field to lay a gamestone
	 * let pointcounter calculate the points
	 * -------------------------------------------------
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
	 * -------------------------------------------------
	 */
	public void playWord(){
		
	}
	
	/**
	 * check if the game has ended
	 * -------------------------------------------------
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
	 * set the visibility of a game
	 * tell the db
	 * @param bool
	 */
	public void setVisibility(Boolean visibility){
		String visible;
		if(visibility){
			visible = "openbaar";
		}
		else{
			visible = "priv�";
		}
		
		DBCommunicator.writeData("UPDATE spel SET zichtbaarheid_type='" + visible + "' WHERE id="+ myID + ";");
	}
	
	/**
	 * get the visibility from the db and return boolean
	 * -------------------------------------------------
	 * @return
	 */
	public boolean getVisibility(){
		/*
		 * get the visibility from the DB
		 */
		return false;
	}

	/**
	 * getter and setter
	 * @return
	 */
	public Field getMyField() {
		return myField;
	}
}
