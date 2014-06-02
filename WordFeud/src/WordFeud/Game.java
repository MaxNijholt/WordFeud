package WordFeud;

import java.sql.SQLException;
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
	 */
	public Game(int gameID, Application app){
		this.app = app;
		this.id = gameID;
		myField = new Field(id);
		myPC = new PointCounter();
		myWC = new WordChecker();
		gameStones = new ArrayList<Integer>();
		stoneLetters = new ArrayList<Character>();
		stoneChars = new HashMap<Integer, Character>();
		
		opponent = DBCommunicator.requestData("SELECT account_naam_uitdager FROM spel WHERE id = " + gameID);
		if(opponent.equals(app.getCurrentAccount().getUsername()))
			opponent = DBCommunicator.requestData("SELECT account_naam_tegenstander FROM spel WHERE id = " + gameID);
		
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
		System.out.println(gamestone.getID());
		int points = myPC.count(myPC.createWords(myField.getTiles(), myField.getNewWords()), myField.getTiles(), myField.getNewWords().size());
		
		return points;
	}
	
	public int removeGameStone(String location, boolean check){
		myField.removeGameStone(location);
		if(check){
			return myPC.count(myPC.createWords(myField.getTiles(), myField.getNewWords()), myField.getTiles(), myField.getNewWords().size());
		}
		else{
			return 0;
		}
	}
	
	/**
	 * get the new words from the field
	 * let the wordchecker check if it is correct
	 */
	public ArrayList<String> playWord(){
		System.out.println(myField.getNewWords());
		ArrayList<String> words;
		if(myField.getNewWords().size() != 0){
			 words =  myWC.checkWords(myPC.createWords(myField.getTiles(), myField.getNewWords()), myField.getNewWords(), myField.getTiles(), getfirstWord());
		}
		else{
			words = new ArrayList<String>();
			words.add("You have not placed any stones");
			return words;
		}
		System.out.println(words);
		
		if(words.size() == 0){
			
			playDB();
			return null;
			
		}
		else{
			myField.updateField(id);
			return words;
		}
		
	}
	
	/**
	 * write the word to the db
	 */
	public void playDB(){
		boolean emptyPot = false;
		int points = myPC.count(myPC.createWords(myField.getTiles(), myField.getNewWords()), myField.getTiles(), myField.getNewWords().size());
		int turn = DBCommunicator.requestInt("SELECT id FROM beurt WHERE spel_id = " + id + " AND account_naam = '" + app.getCurrentAccount().getUsername() + "' ORDER BY id DESC");
		turn += 2;
		
		DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type) VALUES (" +  turn + ", " + id + ", '" + app.getCurrentAccount().getUsername() + "', " + points + ", 'Word')");
		
		HashMap<String, GameStone> newWords = myField.getNewWords();
		System.out.println(newWords);
		ArrayList<Integer> addedInts = new ArrayList<Integer>();
		
		for(Entry<String, GameStone> word : newWords.entrySet()){
			System.out.println(word.getKey());
			String key = word.getKey();
			int x = 0;
			int y = 0;
			
			String[] cords = key.split(",");
			x = Integer.parseInt(cords[0]);
			y = Integer.parseInt(cords[1]);
			
			
			int stoneID = word.getValue().getID();
			System.out.println(stoneID);
			String character = DBCommunicator.requestData("SELECT letterType_karakter FROM letter WHERE id = " + stoneID + " AND spel_id = " + id);
			System.out.println(character);
			if(!character.equals("?")){
				DBCommunicator.writeData("INSERT INTO gelegdeletter (letter_id, spel_id, beurt_id, tegel_x, tegel_y, tegel_bord_naam, blancoletterkarakter)"
						+ " VALUES(" + stoneID + ", " + id + ", " + turn + ", " + x + ", " + y + ", 'Standard', NULL)");
			}
			else{
				DBCommunicator.writeData("INSERT INTO gelegdeletter (letter_id, spel_id, beurt_id, tegel_x, tegel_y, tegel_bord_naam, blancoletterkarakter)"
						+ " VALUES(" + stoneID + ", " + id + ", " + turn + ", " + x + ", " + y + ", 'Standard', '" + word.getValue().getLetter() + "')");
			}
			
			for(int e = 0; e < gameStones.size(); e++){
				if(gameStones.get(e) == stoneID){
					
					int potSize = DBCommunicator.requestInt("SELECT COUNT(letter_id) FROM pot WHERE spel_id = " + id);
					if(potSize != 0){
						boolean added = false;
						while(!added){
							int letterID = (int) (Math.random() * 105);
							System.out.println(letterID);
							String randCharacter = DBCommunicator.requestData("SELECT karakter FROM pot WHERE spel_id = " + id + " AND letter_id = " + letterID);
							if(randCharacter != null){
								boolean inStones = false;
								for(int a = 0; a < gameStones.size(); a++){
									System.out.println(gameStones.get(a));
									if(gameStones.get(a) == letterID){
										inStones = true;
									}
								}
								if(!inStones){
									gameStones.set(e,letterID);
									System.out.println("PLACE "+ id +" "+ letterID +" "+ turn);
									DBCommunicator.writeData("INSERT INTO letterbakjeletter (spel_id, letter_id, beurt_id) VALUES (" + id + ", " + letterID + ", " + turn + ")");
									addedInts.add(letterID);
									added = true;
								}
							}
						}
					}
					else{
						emptyPot = true;
						gameStones.remove(e);
						break;
					}
				}
			}
		}
		
		ArrayList<Integer> nonAddedInts = new ArrayList<Integer>();
		for(int e : gameStones){
			nonAddedInts.add(e);
		}
		
		for(int a : addedInts){
			for(int e = 0; e < nonAddedInts.size(); e++){
				if(a == nonAddedInts.get(e)){
					nonAddedInts.remove(e);
					break;
				}
			}
		}
		
		for(int e : nonAddedInts){
			System.out.println("PLACE "+ id +" "+ e +" "+ turn);
			DBCommunicator.writeData("INSERT INTO letterbakjeletter (spel_id, letter_id, beurt_id) VALUES (" + id + ", " + e + ", " + turn + ")");
		}
				
		if(emptyPot && (gameStones.size() == 0)){
			DBCommunicator.writeData("UPDATE spel SET toestand_type = 'Finished' WHERE id = " + id );
			endGame(true);
		}
		else{
			this.setStoneLetters();
			this.fillStoneChars();
		}
	}
	
	/**
	 * tell the db to end the game
	 * @return
	 */
	public void endGame(boolean emptyEnd){
		
		if(emptyEnd){
			ArrayList<Integer> opponentStones = new ArrayList<Integer>();
			boolean done = false;
			int turnID = DBCommunicator.requestInt("SELECT id from beurt WHERE spel_id = " + id + " AND account_naam = '" + opponent + "' ORDER BY id DESC");
			String restQuery = "";
			String query = "SELECT letter_id FROM letterbakjeletter WHERE spel_id = " + id + " AND beurt_id = " + turnID + " " + restQuery + " ORDER BY beurt_id DESC";
			while(!done){
				int newCharachter = DBCommunicator.requestInt(query);
				if(!(newCharachter == 0)){
					opponentStones.add(newCharachter);
					restQuery += " AND letter_id <> " + newCharachter;
					query = "SELECT letter_id FROM letterbakjeletter WHERE spel_id = " + id + " AND beurt_id = " + turnID + "  " + restQuery + " ORDER BY beurt_id DESC";
				}
				else{
					done = true;
				}
			}
			
			int totalValue = 0;
			for(int e : opponentStones){
				String letterChar = DBCommunicator.requestData("SELECT letterType_karakter FROM letter WHERE id = " + e + " AND spel_id = " + id + " AND lettertype_letterset_code = 'EN'");
				int letterValue = DBCommunicator.requestInt("SELECT waarde FROM lettertype WHERE letterset_code = 'EN' AND karakter = '" + letterChar + "'");
				totalValue += letterValue;
			}
			
			int opTurn = turnID + 2;
			int myTurn = opTurn + 1;
			int opValue = totalValue * -1;
			DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type) VALUES "
					+ "(" + opTurn + ", " + id + ", '" + opponent + "', " + opValue + ", 'End'),"
					+ "(" + myTurn + ", " + id + ", '" + app.getCurrentAccount().getUsername() + "', " + totalValue + ", 'End')");
		}
		else{
			int turnID = DBCommunicator.requestInt("SELECT id from beurt WHERE spel_id = " + id + " AND account_naam = '" + app.getCurrentAccount().getUsername() + "' ORDER BY id DESC");
	
			int totalValue = 0;
			for(int e : gameStones){
				String letterChar = DBCommunicator.requestData("SELECT letterType_karakter FROM letter WHERE id = " + e + " AND spel_id = " + id + " AND lettertype_letterset_code = 'EN'");
				int letterValue = DBCommunicator.requestInt("SELECT waarde FROM lettertype WHERE letterset_code = 'EN' AND karakter = '" + letterChar + "'");
				totalValue += letterValue;
			}
			
			int opTurn = turnID + 1;
			int myTurn = opTurn + 1;
			int myValue = totalValue * -1;
			DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type) VALUES "
					+ "(" + opTurn + ", " + id + ", '" + opponent + "', " + totalValue + ", 'End'),"
					+ "(" + myTurn + ", " + id + ", '" + app.getCurrentAccount().getUsername() + "', " + myValue + ", 'End')");
		}
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
					DBCommunicator.writeData("UPDATE spel SET toestand_type = 'Finished' WHERE id = " + id );
					int newTurn = lastTurnID + 2;
					DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type) VALUES (" + newTurn + ", " + id + ", '" + app.getCurrentAccount().getUsername() + "', 0, 'Pass')");
					for(int e : gameStones){
						DBCommunicator.writeData("INSERT INTO letterbakjeletter (spel_id, letter_id, beurt_id) VALUES (" + id + ", " + e + ", " + newTurn + ")");
					}
					endGame(false);
					thirdPass = true;
				}
			}
		}
		
		if(!thirdPass){
			int newTurn = lastTurnID + 2;
			DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type) VALUES (" + newTurn + ", " + id + ", '" + app.getCurrentAccount().getUsername() + "', 0, 'Pass')");
			for(int e : gameStones){
				DBCommunicator.writeData("INSERT INTO letterbakjeletter (spel_id, letter_id, beurt_id) VALUES (" + id + ", " + e + ", " + newTurn + ")");
			}
		}
	}
	
	/**
	 * tell the db to resign, ending the game
	 */
	public void resign(){
		int turn = DBCommunicator.requestInt("SELECT id FROM beurt WHERE spel_id = " + id + " AND account_naam = '" + app.getCurrentAccount().getUsername() + "' ORDER BY id DESC");
		turn += 2;
		int totalScore = DBCommunicator.requestInt("SELECT totaalscore FROM score WHERE spel_id = " + id + " AND account_naam = '" + app.getCurrentAccount().getUsername() + "'");
		totalScore = totalScore * -1;
		DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type) VALUES(" + turn + ", " + id + ", '" + app.getCurrentAccount().getUsername() + "', " + totalScore +", 'Resign')");
		for(int e : gameStones){
			DBCommunicator.writeData("INSERT INTO letterbakjeletter (spel_id, letter_id, beurt_id) VALUES (" + id + ", " + e + ", " + turn + ")");
		}
		DBCommunicator.writeData("UPDATE spel SET toestand_type = 'Resigned' WHERE id = " + id );
		
		int opTurn = turn + 1;
		int myTurn = opTurn + 1;
		DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type) VALUES "
				+ "(" + opTurn + ", " + id + ", '" + opponent + "', 0, 'End'),"
				+ "(" + myTurn + ", " + id + ", '" + app.getCurrentAccount().getUsername() + "', 0, 'End')");
	}
	
	/**
	 * tell the DB to swap
	 * get new gamestones
	 */
	public void swapGameStones(ArrayList<Integer> stoneIDs){
		int turn = DBCommunicator.requestInt("SELECT id FROM beurt WHERE spel_id = " + id + " ORDER BY id DESC");
		turn ++;
		
		DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type) Values(" + turn + ", " + id + ", '" + app.getCurrentAccount().getUsername() + "',0 ,  'Swap')");
		
		for(int stoneID : stoneIDs){
			boolean swapped = false;
			while(!swapped){
				int letterID = (int) (Math.random() * 105);
				
				String character = DBCommunicator.requestData("SELECT karakter FROM pot WHERE spel_id = " + id + " AND letter_id = " + letterID);
				if(character != null){
					boolean inStones = false;
					for(int e = 0; e < gameStones.size(); e++){
						if(gameStones.get(e) == letterID){
							inStones = true;
						}
					}
					if(!inStones){
						for(int e = 0; e < gameStones.size(); e++){
							if(gameStones.get(e) == stoneID){
								swapped = true;
								gameStones.set(e, letterID);
								break;
							}
						}
					}
				}
			}
		}
		for(int stoneID : gameStones){
			if(stoneID != 0){
				System.out.println("PLACE " + id + " " + stoneID + " " + turn);
				DBCommunicator.writeData("INSERT INTO letterbakjeletter (spel_id, letter_id, beurt_id) VALUES (" + id + ", " + stoneID + ", " + turn + ")");
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
				int randNumber = (int) (Math.random() * 7);
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
	
	public boolean getfirstWord(){
		int turn = DBCommunicator.requestInt("SELECT id FROM beurt WHERE spel_id = " + id + " AND aktie_type = 'Word' ORDER BY id ASC");
		if(turn == 0) return true;
		else return false;
		
	}
}
