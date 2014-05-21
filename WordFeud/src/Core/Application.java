package Core;

import java.util.ArrayList;

import AccountType.Account;
import GUI.CompetitionPanel;
import GUI.GUI;
import GUI.GamePanel;
import GUI.LoginPanel;
import GUI.PlayerPanel;
import GUI.SpectatorGamePanel;
import GUI.SpectatorPanel;
import Utility.DBCommunicator;
import Utility.Loader;
import WordFeud.Competition;
import WordFeud.Game;
import WordFeud.GameStone;


public class Application {
	
	private Game selectedGame;
	private Competition selectedCompetition;	
	private Account currentAccount;
	private GUI myGui;
	private Loader loader;


	/**
	 * start up the application
	 * create the Gui
	 */
	public Application(){
		DBCommunicator.getConnection();
		loader = new Loader();
		loader.loadAllImages();
		myGui = new GUI(this);
	}
	
	
	/**create a new competition*/
	public void addCompetition(String endDate, String description, int mini, int maxi){
		selectedCompetition = new Competition(endDate, description, mini, maxi, currentAccount.getUsername());
		myGui.switchPanel(new CompetitionPanel(myGui));
	}	
	
	/**
	 * let Game create a new game
	 * call the playgame method
	 */
	public void newGame(String player2, boolean visibility){
		String visible;
		if(visibility){
			visible = "openbaar";
		}
		else{
			visible = "priv���";
		}
			
		int lastID = DBCommunicator.requestInt("SELECT id FROM spel ORDER BY id DESC");
		int newID = lastID + 1;
			
		DBCommunicator.writeData("INSERT INTO spel (id, competitie_id, toestand_type, account_naam_uitdager, account_naam_tegenstander, moment_uitdaging, reaktie_type, zichtbaarheid_type, bord_naam, letterset_naam)"
								+ " VALUES(" + newID + ", " + selectedCompetition.getID() + ", 'Request', '" + currentAccount.getUsername() + "', '" + player2 + "', CURRENT_TIMESTAMP(), 'Unknown', '" + visible + "' , 'Standard', 'EN');");
	}
	

	/**
	 * create the new competition
	 * switch to the competitionpanel
	 */
	public void selectCompetition(int compID){
		selectedCompetition = new Competition(compID);
		myGui.switchPanel(new CompetitionPanel(myGui));
	}

	/**
	 * create the new account
	 */
	public void login(Account username){
		currentAccount = username;
	}
	
	public void selectGame(int gameID) {
		Game newGame = new Game(gameID, this);
		selectedGame = newGame;
		myGui.switchPanel(new GamePanel(myGui));
	}
	
	public void spectateCompetition(int compID) {
		myGui.switchPanel(new SpectatorPanel(myGui, compID));
	}
	
	public void spectateGame(int gameID) {
		myGui.switchPanel(new SpectatorGamePanel(myGui, gameID));
	}
	
	/**
	 * tell game to lay a gamestone
	 * return the int from game
	 */
	public int layGameStone(GameStone gamestone, String location){
		int retrievedPoints = selectedGame.layGameStone(gamestone, location);
		return retrievedPoints;
	}

	/**
	 * accept or deny a game in the db
	 * @param gameID
	 */
	public void acceptGame(int gameID){
		DBCommunicator.writeData("UPDATE spel SET reaktie_type = 'Accepted', moment_reaktie = CURRENT_TIMESTAMP(), toestand_type = 'Playing' WHERE id = " + gameID);
		String opponent = this.getOpponentName(gameID);
		DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type)"
				+ "VALUES (1, " + gameID + ", '" + opponent + "', 0, 'Begin'), (2, " + gameID + ", '"+ currentAccount.getUsername() +"', 0, 'Begin')");
		
		this.createGameLetters(gameID);
		this.createGameHands(gameID);
		myGui.switchPanel(new PlayerPanel(myGui));
	}
	
	/**
	 * add all the letters for the beginning of a game to the DB
	 * @param gameID
	 */
	private void createGameLetters(int gameID){
		int letterID = 1;
		for(char letter = 'A'; letter <= 'Z'; letter++){
			int amount = DBCommunicator.requestInt("SELECT aantal FROM lettertype WHERE letterset_code = 'EN' AND karakter = '" + letter + "'");
			
			for(int i = 0; i < amount; i++){
				DBCommunicator.writeData("INSERT INTO letter (id, spel_id, lettertype_letterset_code, lettertype_karakter) VALUES (" + letterID + ", " + gameID + ", 'EN', '" + letter + "')" );
				letterID++;
			}
		}

		DBCommunicator.writeData("INSERT INTO letter (id, spel_id, lettertype_letterset_code, lettertype_karakter) VALUES (" + letterID + ", " + gameID + ", 'EN', '?')");
		letterID++;
		DBCommunicator.writeData("INSERT INTO letter (id, spel_id, lettertype_letterset_code, lettertype_karakter) VALUES (" + letterID + ", " + gameID + ", 'EN', '?')");
	}
	
	/**
	 * add 7 random letters to the hands of each player
	 * @param gameID
	 */
	private void createGameHands(int gameID){
		for(int beurt = 1; beurt <= 2; beurt++){
			int e = 0;
			while(e < 7){
				int letterID = (int) (Math.random() * 105);
				String character = DBCommunicator.requestData("SELECT karakter FROM pot WHERE spel_id = " + gameID + " AND letter_id = " + letterID);
				if(character != null){
					DBCommunicator.writeData("INSERT INTO letterbakjeletter (spel_id, letter_id, beurt_id) VALUES(" + gameID + ", " + letterID + ", " + beurt + ")");
					e++;
				}
			}
		}
	}
	
	public void denyGame(int gameID){
		DBCommunicator.writeData("UPDATE spel SET reaktie_type = 'Rejected', moment_reaktie = CURRENT_TIMESTAMP() WHERE id = " + gameID);
		myGui.switchPanel(new PlayerPanel(myGui));
	}
	
	/**
	 * get all the games that have finished (finished or resigned) and return their integers
	 * @param activeType
	 */
	public ArrayList<Integer> getFinishedGames(boolean resigned) {
		ArrayList<Integer> gameInts = new ArrayList<Integer>();
		String player = currentAccount.getUsername();
		String resign = "Finished";
		if(resigned){
			resign = "Resigned";
		}
		String query = "SELECT id FROM spel WHERE (account_naam_uitdager = '"+ player + "' OR account_naam_tegenstander = '"+ player + "') AND toestand_type = '" + resign + "'";
		Boolean searching = true;
		
		while(searching){
			int gameID = DBCommunicator.requestInt(query);
			if(gameID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + gameID;
				gameInts.add(gameID);
			}
		}
		return gameInts;
	}
	
	/**
	 * get all games that are still playing (my turn or opponents turn) and return their integers
	 * @param activeType
	 * @param myTurn
	 * @return
	 */
	public ArrayList<Integer> getPlayingGames(Boolean myTurn) {
		ArrayList<Integer> gameInts = new ArrayList<Integer>();
		ArrayList<Integer> turnInts = new ArrayList<Integer>();
		String player = currentAccount.getUsername();
		String query = "SELECT id FROM spel WHERE (account_naam_uitdager = '"+ player + "' OR account_naam_tegenstander = '"+ player + "') AND toestand_type = 'Playing'";
		Boolean searching = true;
		
		while(searching){
			int gameID = DBCommunicator.requestInt(query);

			if(gameID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + gameID;
				gameInts.add(gameID);
			}
		}
		
		for(int e : gameInts){
			String name = DBCommunicator.requestData("SELECT account_naam FROM beurt WHERE spel_id = " + e + " ORDER BY id DESC");
			if((name.equals(currentAccount.getUsername()) && (!myTurn))){
				turnInts.add(e);
			}
			else if((!name.equals(currentAccount.getUsername()) && (myTurn))){
				turnInts.add(e);
			}
		}
		
		return turnInts;
	}
	
	/**
	 * get all requested games (currentAccount or opponents request) (denied or unknown) and return their integers
	 * @param myRequest
	 * @param denied
	 * @return
	 */
	public ArrayList<Integer> getRequestedGames(boolean myRequest, boolean denied) {
		ArrayList<Integer> gameInts = new ArrayList<Integer>();
		String player = currentAccount.getUsername();
		String query = "";
		if(denied){
			query = "SELECT id FROM spel WHERE (account_naam_uitdager = '"+ player + "' OR account_naam_tegenstander = '"+ player + "') AND toestand_type = 'Request' AND reaktie_type = 'Rejected'";
		}
		else{
			if(myRequest){
				query = "SELECT id FROM spel WHERE account_naam_uitdager = '"+ player + "' AND toestand_type = 'Request' AND reaktie_type = 'Unknown'";
			}
			else{
				query = "SELECT id FROM spel WHERE account_naam_tegenstander = '"+ player + "' AND toestand_type = 'Request' AND reaktie_type = 'unknown'";
			}
		}
		Boolean searching = true;
		
		while(searching){
			int gameID = DBCommunicator.requestInt(query);
			if(gameID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + gameID;
				gameInts.add(gameID);
			}
		}
		return gameInts;
	}
	
	public ArrayList<Integer> getSpectatableGames(int compID) {
		ArrayList<Integer> gameInts = new ArrayList<Integer>();
		
		String query = "SELECT id FROM spel WHERE competitie_id = " + compID + " AND zichtbaarheid_type = 'openbaar'";
		Boolean searching = true;
		
		while(searching){
			int gameID = DBCommunicator.requestInt(query);

			if(gameID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + gameID;
				gameInts.add(gameID);
			}
		}
		return gameInts;
	}
	
	public ArrayList<Integer> getSpectatableCompetitions() {
		ArrayList<Integer> compInts = new ArrayList<Integer>();
		
		String endQuery = "";
		String query = "SELECT id FROM competitie WHERE id <> 0 " + endQuery;
		Boolean searching = true;
		
		while(searching){
			int compID = DBCommunicator.requestInt(query);

			if(compID == 0){
				searching = false;
			}
			else{
				query += " AND id <> " + compID;
				compInts.add(compID);
			}
		}
		return compInts;
	}
	
	/**
	 * get a the opponents name from the db
	 */
	public String getOpponentName(int gameID){
		
		String name = DBCommunicator.requestData("SELECT account_naam_uitdager FROM spel WHERE id = " + gameID);
		if(name.equals(currentAccount.getUsername())){
			name = DBCommunicator.requestData("SELECT account_naam_tegenstander FROM spel WHERE id = " + gameID);
		}
		
		return name;
	}
	
	public String getCompetitionOwner(int compID){
		String ownerName = DBCommunicator.requestData("SELECT account_naam_eigenaar FROM competitie WHERE id = " + compID);
		return ownerName;
	}
	
	public String getCompetitionDescription(int compID){
		String description = DBCommunicator.requestData("SELECT omschrijving FROM competitie WHERE id = " + compID);
		return description;
	}
	
	public ArrayList<String> getGamePlayers(int gameID){
		ArrayList<String> players = new ArrayList<String>();
		players.add(DBCommunicator.requestData("SELECT account_naam_uitdager FROM spel WHERE id = " + gameID));
		players.add(DBCommunicator.requestData("SELECT account_naam_tegenstander FROM spel WHERE id = " + gameID));
		return players;
	}
	
	public ArrayList<String> getCompetitionPlayers(int compID){
		ArrayList<String> players = new ArrayList<String>();
		String rankingQuery = "SELECT account_naam FROM ranking WHERE competitie_id = " + compID;
		String restQuery = "SELECT account_naam FROM deelnemer WHERE competitie_id = " + compID;
		boolean done = false;
		
		while(!done){
			String name = DBCommunicator.requestData(rankingQuery);
			if(name == null){
				done = true;
			}
			else{
				players.add(name);
				rankingQuery += " AND account_naam <> '" + name + "'";
				restQuery += " AND account_naam <> '" + name + "'";
			}
		}
		
		done = false;
		while(!done){
			String name = DBCommunicator.requestData(restQuery);
			if(name == null){
				done = true;
			}
			else{
				players.add(name);
				restQuery += " AND account_naam <> '" + name + "'";
			}
		}
		
		return players;
	}
	
	public String getPlayerRanking(int compID, String player){
		String ranking = DBCommunicator.requestData("SELECT bayesian_rating FROM ranking WHERE competitie_id = " + compID + " AND account_naam = '" + player + "'");
		return ranking;
	}
	
	/**
	 * get the last turns information
	 */
	public String getLastTurnType(int gameID){
		String turnType = DBCommunicator.requestData("SELECT aktie_type FROM beurt WHERE spel_id = " + gameID + " ORDER BY id desc");
		return turnType;
	}
	
	public int getLastTurnScore(int gameID){
		int turnScore = DBCommunicator.requestInt("SELECT score FROM beurt WHERE spel_id = " + gameID + " ORDER BY id desc");
		return turnScore;
	}
	
	/**
	 * get all account names form the db
	 * return all accounts the have a name LIKE the given string
	 * -------------------------------------------------
	 */
	public Account[] searchPlayer(String partialname){
		
		return null;
	}
	
	/**
	 * call the game to play a word
	 */
	public void playWord(){
		selectedGame.playWord();
	}
	
	/**
	 * tell game to pass a turn
	 */
	public void pass(){
		selectedGame.pass();
	}
	
	/**
	 *tell game to swapGameStones
	 */
	public void swapGameStones(int[] stoneIDs){
		selectedGame.swapGameStones(stoneIDs);
	}
	
	/**
	 * tell the game to shuffle
	 */
	public void shuffle(){
		selectedGame.shuffle();
	}
	
	
	/**
	 * getters and setters
	 * @return
	 */
	public Game getSelectedGame() {
		return selectedGame;
	}


	public void setSelectedGame(Game selectedGame) {
		this.selectedGame = selectedGame;
	}


	public Competition getSelectedCompetition() {
		return selectedCompetition;
	}


	public void setSelectedCompetition(Competition selectedCompetition) {
		this.selectedCompetition = selectedCompetition;
	}


	public Account getCurrentAccount() {
		return currentAccount;
	}


	public void setCurrentAccount(Account currentAccount) {
		this.currentAccount = currentAccount;
	}


	public void logout() {
		this.setCurrentAccount(null);
		myGui.switchPanel(new LoginPanel(myGui));
	}
}
