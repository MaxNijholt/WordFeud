package Core;

import java.util.ArrayList;

import AccountType.Account;
import AccountType.Administrator;
import AccountType.Moderator;
import AccountType.Player;
import GUI.GUI;
import GUI.LoginPanel;
import GUI.GamePanel;
import GUI.PlayerPanel;
import GUI.SpectatorPanel;
import Utility.DBCommunicator;
import Utility.ImageLoader;
import WordFeud.Competition;
import WordFeud.Game;
import WordFeud.GameStone;


public class Application {
	
	private Game selectedGame;
	private Competition selectedCompetition;
	private Account currentAccount;
	private GUI myGui;
	private ImageLoader loader;


	/**
	 * start up the application
	 * create the Gui
	 */
	public Application(){
		DBCommunicator.getConnection();
		loader = new ImageLoader();
		loader.loadAllImages();
		myGui = new GUI(this);		
	}
	
	
	/**
	 * create a new competition and write it to the db
	 * input endDate format yyyymmdd
	 * 
	 */
	public void addCompetition(String compName, String endDate, String description){
		
		int lastID = DBCommunicator.requestInt("SELECT id FROM competitie ORDER BY id DESC");
		int newID = lastID + 1;
		
		
		DBCommunicator.writeData("INSERT INTO competitie (id, account_naam_eigenaar, start, einde, omschrijving) VALUES(" + newID + ", '" + currentAccount.getUsername() +"',  CURRENT_TIMESTAMP(), '" + endDate + "' , '" + description + "');");

		Competition newComp = new Competition(newID);
		selectedCompetition = newComp;	
		
	}
	
	
	/**
	 * write player to the db
	 * call the login method
	 */
	public boolean newPlayer(String username, String password){
		
		String getName = DBCommunicator.requestData("SELECT naam FROM account WHERE naam = '"+ username + "'");
		if(getName == null){
			DBCommunicator.writeData("INSERT INTO account (naam, wachtwoord) VALUES('" + username + "', '" + password + "')");
			this.login(username);
			return true;
		}
		else{
			System.err.println("username already exists");
			return false;
		}
	}
	
	/**
	 * write game to the db
	 * call the playgame method
	 * -------------------------------------------------
	 */
	public void newGame(Player player2, boolean visibility){
		/*
		 * WRITE TO DB
		 */
		
		/*
		 * GET GAMEID FROM DB
		 */
		this.selectGame(0);
	}
	
	/**
	 * create a new game for a competition and write it to the db
	 * -------------------------------------------------
	 */
	public void newCompetitionGame(Player player1, Player player2, Competition compo){
		/*
		 * WRITE TO DB
		 */
		Game newGame = new Game();
		selectedGame = newGame;
		
		myGui.switchPanel(null);
	}
	
	/**
	 * get a account from the db
	 * check if it exists and the password is correct
	 * create the new player and switch to the Playerpanel
	 */
	public void login(String username){
		currentAccount = new Player(username);
	}
	
	/**
	 * get game from db
	 * create the new game
	 * switch to the gamePanel
	 * -------------------------------------------------
	 */
	public void selectGame(int gameID){
		Game newGame = new Game(gameID);
		selectedGame = newGame;
		myGui.switchPanel(new GamePanel(myGui));
	}
	
	public void spectateGame() {
		myGui.switchPanel(new SpectatorPanel());
	}
	
	
	/**
	 * tell game to lay a gamestone
	 * return the boolean from game
	 */
	public int layGameStone(GameStone gamestone, String location){
		int retrievedPoints = selectedGame.layGameStone(gamestone, location);
		return retrievedPoints;
	}
	
	/**
	 * get word approval
	 * -------------------------------------------------
	 */
	public boolean getWordApproval(){
		/*
		 * CREATEEE
		 */
		return false;
	}
	
	/**
	 * create a new account of that type
	 * switch to his panel
	 * -------------------------------------------------
	 */
	public void switchRoll(String accountType){
		if(accountType.equals("player")){
			currentAccount = new Player("henk"); //henk is for testing
			myGui.switchPanel(null);
		}
		else if(accountType.equals("moderator")){
			currentAccount = new Moderator(null);
			myGui.switchPanel(null);
		}
		else if(accountType.equals("administrator")){
			currentAccount = new Administrator(null);
			myGui.switchPanel(null);
		}
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
	
	/**
	 * accept or deny a game in the db
	 * @param gameID
	 */
	public void acceptGame(int gameID){
		DBCommunicator.writeData("UPDATE spel SET reaktie_type = 'Accepted', toestand_type = 'Playing' WHERE id = " + gameID);
		String opponent = this.getOpponentName(gameID);
		DBCommunicator.writeData("INSERT INTO beurt (id, spel_id, account_naam, score, aktie_type)"
				+ "VALUES (1, " + gameID + ", '" + opponent + "', 0, 'Begin'), (2, " + gameID + ", '"+ currentAccount.getUsername() +"', 0, 'Begin')");
		myGui.switchPanel(new PlayerPanel(myGui));
	}
	
	public void denyGame(int gameID){
		DBCommunicator.writeData("UPDATE spel SET reaktie_type = 'Rejected' WHERE id = " + gameID);
		myGui.switchPanel(new PlayerPanel(myGui));
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
	public void swapGameStones(){
		selectedGame.swapGameStones();
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
