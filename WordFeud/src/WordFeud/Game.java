package WordFeud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
	private ArrayList<Integer> gameStones;
	private HashMap<Integer, Character> stoneChars;
	private ArrayList<Character> stoneLetters;
	
	/**
	 * construct the game
	 * -------------------------------------------------
	 */
	public Game(int gameID, Application app){
		this.app		= app;
		this.id			= gameID;
		myField			= new Field(id);
		myPC			= new PointCounter();
		myWC			= new WordChecker();
		gameStones		= new ArrayList<Integer>();
		stoneLetters	= new ArrayList<Character>();
		stoneChars		= new HashMap<Integer, Character>();
		
		opponent = DBCommunicator.requestData("SELECT account_naam_uitdager FROM spel WHERE id = " + gameID);
		if(opponent.equals(app.getCurrentAccount().getUsername())){
			opponent = DBCommunicator.requestData("SELECT account_naam_tegenstander FROM spel WHERE id = " + gameID);
		}
		
		this.setGameStones();
		this.setStoneLetters();
		this.fillStoneChars();
	}
	
	/**
	 * tell field to lay a gamestone
	 * let pointcounter calculate the points
	 * @param gamestone
	 * @param location
	 * @return
	 */
	public int layGameStone(GameStone gamestone, String location){
		myField.layGameStone(gamestone, location);
	
		int points = myPC.count(myPC.createWords(myField.getTiles(), myField.getNewWords()), myField.getTiles());
		return points;
	}
	
	/**
	 * get the new words from the field
	 * let the wordchecker check if it is correct
	 */
	public ArrayList<String> playWord(){
		myField.getNewWords();
		ArrayList<String> words =  myWC.checkWords(myPC.createWords(myField.getTiles(), myField.getNewWords()), myField.getNewWords(), myField.getTiles());
		
		if(words.size() == 0){
			playDB();
			return null;
		}
		else{
			return words;
		}
		
	}
	
	/**
	 * write the word to the db
	 */
	public void playDB(){
		int points = myPC.count(myPC.createWords(myField.getTiles(), myField.getNewWords()), myField.getTiles());
		int turn = DBCommunicator.requestInt("SELECT id FROM beurt WHERE spel_id = " + id + " AND account_naam = '" + app.getCurrentAccount().getUsername() + "' ORDER BY id DESC");
		turn += 2;
		
		DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type) VALUES (" +  turn + ", " + id + ", '" + app.getCurrentAccount().getUsername() + "', " + points + ", 'Word')");
		
		HashMap<String, GameStone> newWords = myField.getNewWords();
		System.out.println(newWords);
		
		for(Entry<String, GameStone> word : newWords.entrySet()){
			String key = word.getKey();
			char[] charKey = key.toCharArray();
			boolean doneX = false;
			int x = 0;
			int y = 0;
			
			for(char letter : charKey){
				if(letter != ','){
					if(!doneX){
						if(x == 0){
							x = (int) letter;
						}
						else{
							x = ((int) letter) + 10;
						}
					}
					else{
						if(y == 0){
							y = (int) letter;
						}
						else{
							y = ((int) letter) + 10;
						}
					}
				}
				else{
					doneX = true;
				}
			}
			
			int stoneID = word.getValue().getID();
			String character = DBCommunicator.requestData("SELECT letterType_karakter FROM letter WHERE id = " + stoneID + " AND spel_id = " + id);
			if(!character.equals("?")){
				DBCommunicator.writeData("INSERT INTO gelegdeletter (letter_id, spel_id, beurt_id, tegel_x, tegel_y, tegel_bord_naam, blancoletterkarakter)"
						+ " VALUES(" + stoneID + ", " + id + ", " + turn + ", " + x + ", " + y + ", 'Standard', NULL");
			}
			else{
				DBCommunicator.writeData("INSERT INTO gelegdeletter (letter_id, spel_id, beurt_id, tegel_x, tegel_y, tegel_bord_naam, blancoletterkarakter)"
						+ " VALUES(" + stoneID + ", " + id + ", " + turn + ", " + x + ", " + y + ", 'Standard', " + word.getValue().getLetter());
			}
		}
	}
	
	/**
	 * tell the db to end the game
	 * only when end by finish
	 * -------------------------------------------------
	 * @return
	 */
	public void endGame(){
		
	}
	
	/**
	 * tell the db to end this game
	 * only when end by 3rd pass or resign
	 */
	public void endGameByForce(){
		
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
					endGameByForce();
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
	public void swapGameStones(ArrayList<Integer> stoneIDs){
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
					
					for(int e = 0; e < gameStones.size(); e++){
						if(gameStones.get(e) == stoneID){
							gameStones.set(e, letterID);
							break;
						}
					}
				}
			}
		}
		for(int stoneID : gameStones){
			System.out.println(stoneID);
			if(stoneID != 0){
				DBCommunicator.writeData("INSERT INTO letterbakjeletter (spel_id, letter_id, beurt_id) VALUES (" + id + ", " + stoneID + ", " + beurt + ")");
			}
		}
		this.setStoneLetters();
		this.fillStoneChars();
	}
	
	/**
	 * shuffle your gamestones around
	 * does not end turn
	 */
	public void shuffle(){
		
		ArrayList<Boolean> used = new ArrayList<Boolean>();
		ArrayList<Integer> copyStones = new ArrayList<Integer>();
		for(int e = 0; e < gameStones.size(); e++){
			copyStones.add(gameStones.get(e));
			used.add(false);
		}
		for(int e : copyStones){
			char l = stoneChars.get(e);
			boolean placed = false;		
			while(!placed){
				int randNumber = (int) (Math.random() * copyStones.size());
				System.out.println(randNumber);
				if(!used.get(randNumber)){
					gameStones.set(randNumber, e);
					stoneLetters.set(randNumber, l);
					used.set(randNumber, true);
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
		gameStones = new ArrayList<Integer>();
		boolean done = false;
		int turnID = DBCommunicator.requestInt("SELECT id from beurt WHERE spel_id = " + id + " AND account_naam = '" + app.getCurrentAccount().getUsername() + "' ORDER BY id DESC");
		String restQuery = "";
		String query = "SELECT letter_id FROM letterbakjeletter WHERE spel_id = " + id + " AND beurt_id = " + turnID + " " + restQuery + " ORDER BY beurt_id DESC";
		while(!done){
			int newCharachter = DBCommunicator.requestInt(query);
			if(!(newCharachter == 0)){
				gameStones.add(newCharachter);
				restQuery += " AND letter_id <> " + newCharachter;
				query = "SELECT letter_id FROM letterbakjeletter WHERE spel_id = " + id + " AND beurt_id = " + turnID + "  " + restQuery + " ORDER BY beurt_id DESC";
			}
			else{
				done = true;
			}
		}
	}
	
	public ArrayList<Integer> getGameStones(){
		return gameStones;
	}
	
	public HashMap<Integer, Character> getStoneChars(){
		return stoneChars;
	}
	
	public void setStoneLetters(){
		stoneLetters = new ArrayList<Character>();
		int turnID = DBCommunicator.requestInt("SELECT id from beurt WHERE spel_id = " + id + " AND account_naam = '" + app.getCurrentAccount().getUsername() + "' ORDER BY id DESC");
		String letterString = DBCommunicator.requestData("SELECT inhoud FROM plankje WHERE spel_id = " + id + " AND beurt_id = " + turnID);
		char[] fullChar = letterString.toCharArray();
		for(char a : fullChar){
			if(a != ','){
				stoneLetters.add(a);
			}
		}		
	}

	public void fillStoneChars(){
		stoneChars = new HashMap<Integer, Character>();
		for(int e = 0; e < gameStones.size(); e++){
			stoneChars.put(gameStones.get(e), stoneLetters.get(e));
		}
	}
}
