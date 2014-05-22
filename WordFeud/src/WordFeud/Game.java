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
	private int[] gameStones;
	
	/**
	 * construct the game
	 * -------------------------------------------------
	 */
	public Game(int gameID, Application app){
		this.app = app;
		this.id = gameID;
		myField = new Field(id);
		myPC = new PointCounter(myField);
		myWC = new WordChecker();
		
		opponent = DBCommunicator.requestData("SELECT account_naam_uitdager FROM spel WHERE id = " + gameID);
		if(opponent.equals(app.getCurrentAccount().getUsername())){
			opponent = DBCommunicator.requestData("SELECT account_naam_tegenstander FROM spel WHERE id = " + gameID);
		}
		
		this.setGameStones();
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
		int points = 0;
		
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
	 * tell the db to end the game
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
	 */
	public void swapGameStones(int[] stoneIDs){
		int beurt = DBCommunicator.requestInt("SELECT id FROM beurt WHERE spel_id = " + id + " ORDER BY id DESC");
		beurt ++;
		
		DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type) Values(" + beurt + ", " + id + ", '" + app.getCurrentAccount().getUsername() + "',0 ,  'Swap')");
		
		
		for(int stoneID : stoneIDs){
			boolean swapped = false;
			while(!swapped){
				int letterID = (int) (Math.random() * 105);
				
				String character = DBCommunicator.requestData("SELECT karakter FROM pot WHERE spel_id = " + id + " AND letter_id = " + letterID);
				if(character != null){
					swapped = true;
					
					for(int e = 0; e < gameStones.length; e++){
						if(gameStones[e] == stoneID){
							gameStones[e] = letterID;
							break;
						}
					}
				}
			}
		}
		for(int stoneID : gameStones){
			if(stoneID != 0){
				DBCommunicator.writeData("INSERT INTO letterbakjeletter (spel_id, letter_id, beurt_id) VALUES (" + id + ", " + stoneID + ", " + beurt + ")");
			}
		}
	}
	
	/**
	 * shuffle your gamestones around
	 * does not end turn
	 */
	public void shuffle(){
		
		boolean[] used = {false,false,false,false,false,false,false};
		int[] copyStones = new int[7];
		for(int e = 0; e < gameStones.length; e++){
			copyStones[e] = gameStones[e];
		}
		for(int e : copyStones){
			boolean placed = false;		
			while(!placed){
				int randNumber = (int) (Math.random() * 7);
				if(!used[randNumber]){
					gameStones[randNumber] = e;
					used[randNumber] = true;
					placed = true;
				}
			}
		}
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
	
	private void setGameStones(){
		gameStones = new int[7];
		int counter = 0;
		boolean done = false;
		int turnID = DBCommunicator.requestInt("SELECT id from beurt WHERE spel_id = " + id + " AND account_naam = '" + app.getCurrentAccount().getUsername() + "' ORDER BY id DESC");
		String restQuery = "";
		String query = "SELECT letter_id FROM letterbakjeletter WHERE spel_id = " + id + " AND beurt_id = " + turnID + " " + restQuery + " ORDER BY beurt_id DESC";
		while(!done){
			int newCharachter = DBCommunicator.requestInt(query);
			if(!(newCharachter == 0)){
				gameStones[counter] = newCharachter;
				restQuery += " AND letter_id <> " + newCharachter;
				query = "SELECT letter_id FROM letterbakjeletter WHERE spel_id = " + id + " AND beurt_id = " + turnID + "  " + restQuery + " ORDER BY beurt_id DESC";
				counter++;
			}
			else{
				done = true;
			}
		}
	}
	
	public int[] getGameStones(){
		return gameStones;
	}
	
	public String getGameStoneLetters(){
		int turnID = DBCommunicator.requestInt("SELECT id from beurt WHERE spel_id = " + id + " AND account_naam = '" + app.getCurrentAccount().getUsername() + "' ORDER BY id DESC");
		String letters = DBCommunicator.requestData("SELECT inhoud FROM plankje WHERE spel_id = " + id + " AND beurt_id = " + turnID);
		return letters;
	}
}
