package GUI;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
	
	public void playWord(){
		
	}
	
	public boolean layGameStone(GameStone gamestone, String location){
		return false;
		
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
	
	public void login(String username) {
		app.login(username);
	}
	
	public void selectGame(int gameID){
		app.selectGame(gameID);
	}
	
	public ArrayList<Integer> getFinishedGames(boolean resigned){
		ArrayList<Integer> gameInts = app.getFinishedGames(resigned);
		return gameInts;
	}
	
	public ArrayList<Integer> getPlayingGames(boolean myTurn){
		ArrayList<Integer> gameInts = app.getPlayingGames(myTurn);
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
	
	public void denyGame(int gameID) {
		app.denyGame(gameID);
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
	
	public void spectateGame() {
		app.spectateGame();
	}

	public Application getApplication() {
		return app;
	}

	public void selectGame(int gameID) {
		app.selectGame(gameID);
	}

	public void spectateGame() {
		app.spectateGame();
	}

	public Application getApplication()
	{
		// TODO Auto-generated method stub
		return app;
	}
}
