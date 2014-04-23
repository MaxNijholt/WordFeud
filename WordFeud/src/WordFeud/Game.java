package WordFeud;
/*
 * last update: 23/04
 */
import Utility.PointCounter;

import Utility.WordChecker;

public class Game {

	Field myField;
	private PointCounter myPC;
	private WordChecker myWC;
	
	/**
	 * cronstruct the game
	 */
	public Game(){
		myField = new Field();
	}
	
	/**
	 * tell field to lay a gamestone
	 * let pointcounter calculate the points
	 * @param gamestone
	 * @param location
	 * @return
	 */
	public boolean layGameStone(GameStone gamestone, String location){
		return false;
		
	}
	
	/**
	 * get the new words from the field
	 * let the wordchecker check if it is correct
	 */
	public void playWord(){
		
	}
	
	/**
	 * check if the game has ended
	 * @return
	 */
	public boolean checkEndGame(){
		return false;
	}
	
	/**
	 * pass a turn
	 * tell the DB
	 */
	public void pass(){
		
	}
	
	/**
	 * tell the DB to swap
	 * get new gamestones
	 */
	public void swapGameStones(){
		
	}
	
	/**
	 * shuffle your gamestones around
	 * does not end turn
	 */
	public void shuffle(){
		
	}
	
	/**
	 * set the visibility of a game
	 * tell the db
	 * @param bool
	 */
	public void setVisibility(Boolean bool){
		
	}
	
	/**
	 * get the visibility from the db and return
	 * @return
	 */
	public boolean getVisibility(){
		return false;
	}
}
