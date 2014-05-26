package GUI;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import AccountType.Account;
import Core.Application;
import Utility.Loader;
import WordFeud.GameStone;

@SuppressWarnings("serial")
public class GUI extends JFrame{

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Wordfeud";
	private Application app;
	
	public GUI(Application app){
		this.app = app;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle(TITLE);
		this.setContentPane(new LoginPanel(this));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setIconImage(Loader.ICON);
	}
	
	public ArrayList<String> playWord(){
		ArrayList<String> words = app.playWord();
		return words;
	}
	
	public int layGameStone(GameStone gamestone, String location){
		int points = app.layGameStone(gamestone, location);
		return points;	
	}
	
	public void switchPanel(JPanel panel){
		this.getContentPane().removeAll();
		this.setContentPane(panel);
		this.revalidate();
	}
	
	public void setLoadingCursor(boolean loading){
		if(loading){
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}
		else{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	public void pass(){
		
	}
	
	public void shuffle(){
		
	}
	
	public void swapGameStones(){
		
	}
	
	public ArrayList<Integer> getFinishedGames(boolean resigned){
		ArrayList<Integer> gameInts = app.getFinishedGames(resigned);
		return gameInts;
	}
	
	public ArrayList<Integer> getPlayingGames(boolean myTurn){
		ArrayList<Integer> gameInts = app.getPlayingGames(myTurn);
		return gameInts;
	}
	
	public ArrayList<Integer> getPlayingGames(boolean myTurn, int compID){
		ArrayList<Integer> gameInts = app.getPlayingGames(myTurn, compID);
		return gameInts;
	}
	
	public ArrayList<Integer> getRequestedGames(boolean myRequest, boolean denied){
		ArrayList<Integer> gameInts = app.getRequestedGames(myRequest, denied);
		return gameInts;
	}
	
	public String getOpponentName(int gameID){
		
		return app.getOpponentName(gameID);
	}
	
	public void acceptGame(int gameID) {
		app.acceptGame(gameID);
	}
	
	public void acceptGame(int gameID, int compID) {
		app.acceptGame(gameID, compID);
	}
	
	public void denyGame(int gameID) {
		app.denyGame(gameID);
	}

	public void denyGame(int gameID, int compID) {
		app.denyGame(gameID, compID);
	}
	
	public String getLastTurntype(int gameID) {
		String turnType = app.getLastTurnType(gameID);
		return turnType;
	}

	public int getLastTurnScore(int gameID) {
		int turnScore = app.getLastTurnScore(gameID);
		return turnScore;
	}

	public void logout() {
		app.logout();
		
	}
	
	public void selectGame(int gameID) {
		app.selectGame(gameID);
	}

	public void spectateCompetition(int compID) {
		app.spectateCompetition(compID);
	}
	
	public void spectateGame(int gameID) {
		app.spectateGame(gameID);
	}

	public Application getApplication(){
		return app;
	}

	public void login(Account username) {
		app.login(username);
		
	}

	public ArrayList<Integer> getSpectatableGames(int compID) {
		ArrayList<Integer> gameInts = app.getSpectatableGames(compID);
		return gameInts;
	}

	public ArrayList<Integer> getSpectatableCompetitions() {
		ArrayList<Integer> compInts = app.getSpectatableCompetitions();
		return compInts;
	}
	
	public String getCompetitionOwner(int compID){
		String owner =app.getCompetitionOwner(compID);
		return owner;
	}
	
	public String getCompetitionDescription(int compID){
		String description = app.getCompetitionDescription(compID);
		return description;
	}
	
	public ArrayList<String> getGamePlayers(int gameID){
		ArrayList<String> players = app.getGamePlayers(gameID);
		return players;
	}
	
	public ArrayList<String> getCompetitionPlayers(int compID){
		ArrayList<String> players = app.getCompetitionPlayers(compID);
		return players;
	}
	
	public String getPlayerRanking(int compID, String player){
		String ranking = app.getPlayerRanking(compID, player);
		return ranking;
	}
	
	public boolean getHaveGameWith(String opponent, int compID){
		boolean doHave = app.getHaveGameWith(opponent, compID);
		return doHave;
	}
	
	public void newGame(String player, boolean visibility){
		app.newGame(player, visibility);
	}
	
	public void seeComps(int compID, JPanel panel){
		app.seeComps(compID, panel);
	}
}
