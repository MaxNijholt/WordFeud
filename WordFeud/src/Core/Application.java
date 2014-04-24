package Core;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Utility.DBCommunicator;
import WordFeud.Competition;
import WordFeud.Game;
import WordFeud.GameStone;
import AccountType.Account;
import AccountType.Administrator;
import AccountType.Moderator;
import AccountType.Player;
import GUI.GUI;
import GUI.Panel;

/**
 * last update: 23/04
 */
public class Application {
	
	Game selectedGame;
	Competition selectedCompetition;
	Account currentAccount;
	GUI myGui;
	
	/**
	 * start up the application
	 * create the Gui
	 */
	public Application(){
		myGui = new GUI(this);
		
		try {
			addCompetition("test", "2014/04/25", "normal");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * create a new competition and write it to the db
	 * @throws ParseException 
	 */
	public void addCompetition(String compName, String endDate, String boardLayout) throws ParseException{
		Competition newComp = new Competition(compName, endDate, boardLayout);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date parsed = format.parse(endDate);
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
		
        System.out.println(DBCommunicator.requestData("SELECT id FROM competitie ORDER BY id DESC"));
		int last = DBCommunicator.requestInt("SELECT id FROM competitie ORDER BY id DESC");
		System.out.println(last);
		
		selectedCompetition = newComp;
		
		
	}
	
	
	/**
	 * write player to the db
	 * call the login method
	 */
	public void newPlayer(String email, String username, String password){
		/*
		 * WRITE TO DB
		 */
		
		this.login(username, password);
	}
	
	/**
	 * write game to the db
	 * call the playgame method
	 */
	public void newGame(Player player1, Player player2){
		/*
		 * WRITE TO DB
		 */
		
		/*
		 * GET GAMEID FROM DB
		 */
		this.playGame("");
	}
	
	/**
	 * create a new game for a competition and write it to the db
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
	public boolean login(String username, String Password){
		return false;
		
	}
	
	/**
	 * get game from db
	 * create the new game
	 * switch to the gamePanel
	 */
	public void playGame(String gameID){
		/*
		 * GET GAME FROM DB
		 */
		Game newGame = new Game();
		selectedGame = newGame;
		myGui.switchPanel(null);
	}
	
	/**
	 * tell game to lay a gamestone
	 * return the boolean from game
	 */
	public boolean layGameStone(GameStone gamestone, String location){
		selectedGame.layGameStone(gamestone, location);
		return false;
	}
	
	/*
	 * get word approval
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
	 */
	public void switchRoll(String accountType){
		if(accountType.equals("player")){
			currentAccount = new Player();
			myGui.switchPanel(null);
		}
		else if(accountType.equals("moderator")){
			currentAccount = new Moderator();
			myGui.switchPanel(null);
		}
		else if(accountType.equals("administrator")){
			currentAccount = new Administrator();
			myGui.switchPanel(null);
		}
	}
	
	/**
	 * get all account names form the db
	 * return all accounts the have a name LIKE the given string
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
	
}
