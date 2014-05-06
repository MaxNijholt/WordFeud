package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Utility.SButton;
import Utility.SLabel;
import Utility.STextField;

@SuppressWarnings("serial")
public class PlayerPanel extends JPanel implements ActionListener{

	private STextField searchText;
	private SButton searchButton;
	private JPanel searchPanel;
	private JScrollPane scrollPane;
	private JPanel gameContent;
	private GUI gui;
	
	
	public PlayerPanel(GUI gui){
		this.gui = gui;
		gui.setLoadingCursor(true);
		
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//create the search textfield and button might be erased
		searchText 		= 	new STextField("search");
		searchButton 	= 	new SButton("search", SButton.GREY, 220, 40);
		searchPanel 	= 	new JPanel();
		searchPanel.setMaximumSize(new Dimension(500,65));
		searchPanel.setBackground(new Color(94, 94, 94));
		searchPanel.setLayout(new FlowLayout());
		searchPanel.add(searchText);
		searchPanel.add(searchButton);
		this.add(searchPanel);
		
		//create the gameContent panel here go all the games
		gameContent 	= 	new JPanel();
		gameContent.setLayout(new BoxLayout(gameContent, BoxLayout.PAGE_AXIS));
		gameContent.setBackground(new Color(94,94,94));
		
		//create the scrollpane as container for the gameContent
		scrollPane 		= 	new JScrollPane(gameContent);
		scrollPane.setBorder(null);
		scrollPane.setPreferredSize(new Dimension(1000, 500));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane);
		
		ArrayList<Integer> gameInts;
		
		//currentAccounts new requested games
		gameInts = gui.getRequestedGames(false, false);
		if(gameInts.size() != 0){
			gameContent.add(addLabel("new request", 1));
			gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			for(int e : gameInts){
				gameContent.add(paintGame(e, "NewRequest"));
				gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			}
		}
		
		gameContent.add(addLabel("Playing",0));
		gameContent.add(Box.createRigidArea(new Dimension(500,10)));
				
		//games that are still playing
		gameInts = gui.getPlayingGames(true);
		if(gameInts.size() != 0){
			gameContent.add(addLabel("your Turn",1));
			gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			for(int e : gameInts){
				gameContent.add(paintGame(e, "Playing"));
				gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			}
		}
			
		gameInts = gui.getPlayingGames(false);
		if(gameInts.size() != 0){
			gameContent.add(addLabel("opponents turn",1));
			gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			for(int e : gameInts){
				gameContent.add(paintGame(e, "Playing"));
				gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			}
		}
		
		gameContent.add(addLabel("Finished games",0));
		gameContent.add(Box.createRigidArea(new Dimension(500,10)));
		//games that are finished
		
		gameInts = gui.getFinishedGames(false);
		if(gameInts.size() != 0){
			gameContent.add(addLabel("finished",1));
			gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			for(int e : gameInts){
				gameContent.add(paintGame(e, "Finished"));
				gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			}
		}
			
		gameInts = gui.getFinishedGames(true);
		if(gameInts.size() != 0){
			gameContent.add(addLabel("Resigned",1));
			gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			for(int e : gameInts){
				gameContent.add(paintGame(e, "Finished"));
				gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			}
		}
	
		gameContent.add(addLabel("Requested",0));
		gameContent.add(Box.createRigidArea(new Dimension(500,10)));
		//games that currentPlayer requested	
		
		gameInts = gui.getRequestedGames(true, false);
		if(gameInts.size() != 0){
			gameContent.add(addLabel("waiting", 1));
			gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			for(int e : gameInts){
				gameContent.add(paintGame(e, "Request"));
				gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			}
		}
			
		gameInts = gui.getRequestedGames(true, true);
		if(gameInts.size() != 0){
			gameContent.add(addLabel("denied", 1));
			gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			for(int e : gameInts){
				gameContent.add(paintGame(e, "Request"));
				gameContent.add(Box.createRigidArea(new Dimension(500,10)));
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
			panel.setMinimumSize(new Dimension(500,35));
			panel.setPreferredSize(new Dimension(500,35));
			panel.setMaximumSize(new Dimension(500,35));
			panel.setBackground(new Color(124,124,124));
			SLabel label = new SLabel(labelText, SLabel.CENTER, new Font("Arial", Font.BOLD, 25));
			panel.add(label);
			
		}
		if (type == 1){
			panel.setMinimumSize(new Dimension(400,30));
			panel.setPreferredSize(new Dimension(400,30));
			panel.setMaximumSize(new Dimension(400,30));
			panel.setBackground(new Color(124,124,124));
			SLabel label = new SLabel(labelText, SLabel.CENTER, new Font("Arial", Font.BOLD, 20));
			panel.add(label);
			
		}
		
		return panel;
	}
	
	/**
	 * create a view for a game and return it in a JPanel to be added
	 * @param gameID
	 * @return
	 */
	private JPanel paintGame(final int gameID, String gameType){
		System.out.println("paint game: " + gameID);
		
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(600,90));
		panel.setPreferredSize(new Dimension(600,90));
		panel.setMaximumSize(new Dimension(600,90));
		panel.setBackground(new Color(84,84,84));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//for a game newly requested. option to accept or reject
		if(gameType.equals("NewRequest")){
			SButton accept 	= new SButton("accept", SButton.GREY, 220, 40);
			SButton deny 	= new SButton("deny", SButton.GREY, 220, 40);
			JPanel opponent = new JPanel();
			JPanel play 	= new JPanel();
			
			opponent.add(new SLabel(gui.getOpponentName(gameID), SLabel.CENTER, new Font("Arial", Font.BOLD, 25)));
			play.add(new SLabel("wants to play a game with you", SLabel.CENTER, new Font("Arial", Font.PLAIN, 20)));
			
			opponent.setMinimumSize(new Dimension(200,30));
			play.setMinimumSize(new Dimension(300, 30));
			accept.setMinimumSize(accept.getPreferredSize());
			deny.setMinimumSize(deny.getPreferredSize());
			
			opponent.setBackground(panel.getBackground());
			play.setBackground(panel.getBackground());
			
			c.gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(5,15,0,0);
			panel.add(opponent, c);
			c.gridy++;
			panel.add(play,c);
			c.gridx++;
			c.gridy--;
			panel.add(accept, c);
			c.gridy++;
			panel.add(deny, c);
			
			accept.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					gui.acceptGame(gameID);
				}
			});
			
			deny.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					gui.denyGame(gameID);
				}
			});
		}
		
		//for a game that is playing. option to select
		else if(gameType.equals("Playing")){
			
		}
		
		//for a game that has finished. option to watch/spectate
		else if(gameType.equals("Finished")){
			
		}
		
		//for a game that has been requested. no options
		else if(gameType.equals("Request")){
			
		}
		
		return panel;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
