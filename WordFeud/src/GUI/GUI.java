package GUI;

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import AccountType.Account;
import Core.Application;
import Utility.Loader;
import Utility.MasterThread;
import WordFeud.GameStone;

@SuppressWarnings("serial")
public class GUI extends JFrame{

	public static final int 		WIDTH = 1000;
	public static final int 		HEIGHT = 600;
	public static final String 		TITLE = "Wordfeud";
	private 			Application app;
	
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
		return app.playWord();
	}
	
	public int layGameStone(GameStone gamestone, String location){
		return app.layGameStone(gamestone, location);
	}
	
	public void switchPanel(JPanel jpanel){
		this.getContentPane().removeAll();
		this.setContentPane(jpanel);
		this.revalidate();
	}
	public void switchPanel(JPanel jpanel, Observer currentPanel){
		MasterThread.getInstance().deleteObserver(currentPanel);
		this.getContentPane().removeAll();
		this.setContentPane(jpanel);
		this.revalidate();
	}
	
	public void setLoadingCursor(boolean loading){
		if(loading) this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
		else this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void pass(){ app.pass(); }
	
	public void shuffle(){
		System.out.println("the method shuffle is called but has no code. [GUI]");
	}
	
	public void swapGameStones(){
		System.out.println("the method swapGameStones is called but has no code. [GUI]");
	}
	
	public ArrayList<Integer> getFinishedGames(boolean resigned){
		return app.getFinishedGames(resigned);
	}
	
	public ArrayList<Integer> getPlayingGames(boolean myTurn){
		return app.getPlayingGames(myTurn);
	}
	
	public ArrayList<Integer> getPlayingGames(boolean myTurn, int compID){
		return app.getPlayingGames(myTurn, compID);
	}
	
	public ArrayList<Integer> getRequestedGames(boolean myRequest, boolean denied){
		return app.getRequestedGames(myRequest, denied);
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
		return app.getLastTurnType(gameID);
	}

	public int getLastTurnScore(int gameID) {
		return app.getLastTurnScore(gameID);
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
		return app.getSpectatableGames(compID);
	}

	public ArrayList<Integer> getSpectatableCompetitions() {
		return app.getSpectatableCompetitions();
	}
	
	public String getCompetitionOwner(int compID){
		return app.getCompetitionOwner(compID);
	}
	
	public String getCompetitionDescription(int compID){
		return app.getCompetitionDescription(compID);
	}
	
	public ArrayList<String> getGamePlayers(int gameID){
		return app.getGamePlayers(gameID);
	}
	
	public ArrayList<String> getCompetitionPlayers(int compID){
		return app.getCompetitionPlayers(compID);
	}
	
	public String getPlayerRanking(int compID, String player){
		String ranking = app.getPlayerRanking(compID, player);
		return ranking;
	}
	
	public boolean getHaveGameWith(String opponent, int compID){
		return app.getHaveGameWith(opponent, compID);
	}
	
	public void newGame(String player, boolean visibility){
		app.newGame(player, visibility);
	}
	
	public void seeComps(int compID, String panel){
		app.seeComps(compID, panel);
	}

	public int removeGameStone(String location) {
		return app.removeGameStone(location);
	}
	
	public void resign(){
		app.resign();
	}
}
