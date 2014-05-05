package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Utility.SButton;
import Utility.SLabel;
import Utility.STextField;

@SuppressWarnings("serial")
public class PlayerPanel extends JPanel {

	private STextField searchText;
	private SButton searchButton;
	private JPanel searchPanel;
	
	
	public PlayerPanel(GUI gui){
		gui.setLoadingCursor(true);
		
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		searchText = new STextField("search");
		searchButton = new SButton("search", SButton.GREY, 220, 40);
		searchPanel = new JPanel();
		searchPanel.setMaximumSize(new Dimension(500,65));
		searchPanel.setBackground(new Color(94, 94, 94));
		searchPanel.setLayout(new FlowLayout());
		searchPanel.add(searchText);
		searchPanel.add(searchButton);
		this.add(searchPanel);
		
		ArrayList<Integer> gameInts;
		
		//currentAccounts new requested games
		gameInts = gui.getRequestedGames(false, false);
		if(gameInts.size() != 0){
			this.add(addLabel("new request", 1));
			for(int e : gameInts){
				this.add(paintGame(e, "NewRequest"));
			}
		}
		
		this.add(addLabel("Playing",0));
				
		//games that are still playing
		gameInts = gui.getPlayingGames(true);
		if(gameInts.size() != 0){
			this.add(addLabel("your Turn",1));
			for(int e : gameInts){
				this.add(paintGame(e, "Playing"));
			}
		}
			
		gameInts = gui.getPlayingGames(false);
		if(gameInts.size() != 0){
			this.add(addLabel("opponents turn",1));
			for(int e : gameInts){
				this.add(paintGame(e, "Playing"));
			}
		}
		
		this.add(addLabel("Finished games",0));
		//games that are finished
		
		gameInts = gui.getFinishedGames(false);
		if(gameInts.size() != 0){
			this.add(addLabel("finished",1));
			for(int e : gameInts){
				this.add(paintGame(e, "Finished"));
			}
		}
			
		gameInts = gui.getFinishedGames(true);
		if(gameInts.size() != 0){
			this.add(addLabel("Resigned",1));
			for(int e : gameInts){
				this.add(paintGame(e, "Finished"));
			}
		}
	
		this.add(addLabel("Requested",0));
		//games that currentPlayer requested	
		
		gameInts = gui.getRequestedGames(true, false);
		if(gameInts.size() != 0){
			this.add(addLabel("waiting", 1));
			for(int e : gameInts){
				this.add(paintGame(e, "Request"));
			}
		}
			
		gameInts = gui.getRequestedGames(true, true);
		if(gameInts.size() != 0){
			this.add(addLabel("denied", 1));
			for(int e : gameInts){
				this.add(paintGame(e, "Request"));
			}
		}
		gui.setLoadingCursor(false);
	}
	

	/**
	 * creates a label and returns it in a JPanel to be added
	 * @param labelText
	 * @param type
	 * @return
	 */
	private JPanel addLabel(String labelText, int type){
		JPanel panel = new JPanel();
		
		if (type == 0){
			panel.setMaximumSize(new Dimension(500,35));
			panel.setBackground(new Color(124,124,124));
			SLabel label = new SLabel(labelText, SLabel.CENTER, new Font("Arial", Font.BOLD, 25));
			panel.add(label);
			this.add(Box.createRigidArea(new Dimension(500,10)));
		}
		if (type == 1){
			panel.setMaximumSize(new Dimension(400,30));
			panel.setBackground(new Color(124,124,124));
			SLabel label = new SLabel(labelText, SLabel.CENTER, new Font("Arial", Font.BOLD, 20));
			panel.add(label);
			this.add(Box.createRigidArea(new Dimension(500,10)));
		}
		
		return panel;
	}
	
	/**
	 * create a view for a game and return it in a JPanel to be added
	 * @param gameID
	 * @return
	 */
	private JPanel paintGame(int gameID, String gameType){
		System.out.println("paint game: " + gameID);
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(600,50));
		return panel;
	}
}
