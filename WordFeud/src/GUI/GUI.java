package GUI;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Core.Application;
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
		this.setContentPane(new CompetitionCreatePanel(this));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
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
	
	public ArrayList<Integer> getFinishedGames(){
		ArrayList<Integer> gameInts = app.getFinishedGames();
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
}
