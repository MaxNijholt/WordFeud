package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

import Utility.AScrollPane;
import Utility.SButton;
import Utility.SLabel;

@SuppressWarnings("serial")
public class SpectatorPanel extends JPanel {

	private AScrollPane scrollPane;
	private AScrollPane playerScrollPane;
	private JPanel playerContent;
	private JPanel gameContent;
	private GUI gui;
	private MenuPanel mp;
	private int compID;
	private SButton back;

	public SpectatorPanel(GUI myGui, int compID){
		this.gui = myGui;
		if(gui.getApplication().getCurrentAccount() == null){
			back = new SButton("Back", SButton.GREY, 220, 40);
		}
		else{
			this.mp = new MenuPanel(gui, new SpectatorCompetitionsPanel(gui));
		}
		
		gui.setLoadingCursor(true);
		this.compID = compID;
		
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new BorderLayout());

		JPanel allPanel = new JPanel();
		allPanel.setLayout(new FlowLayout());
		allPanel.setBackground(new Color(94,94,94));
		
		//create the playercontent panel, here go all the players
		playerContent	=	new JPanel();
		playerContent.setLayout(new BoxLayout(playerContent, BoxLayout.PAGE_AXIS));
		playerContent.setBackground(new Color(94,94,94));
		playerContent.add(Box.createRigidArea(new Dimension(400,15)));
		
		//create the scrollpane as container for the playercontent
		playerScrollPane 		= 	new AScrollPane(400, 550, playerContent, false, true);
//		playerScrollPane.setBorder(null);
//		playerScrollPane.setPreferredSize(new Dimension(400, 550));
//		playerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		playerScrollPane.getVerticalScrollBar().setUnitIncrement(16);
//		playerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		allPanel.add(playerScrollPane);
		
		//create the gameContent panel here go all the games
		gameContent 	= 	new JPanel();
		gameContent.setLayout(new BoxLayout(gameContent, BoxLayout.PAGE_AXIS));
		gameContent.setBackground(new Color(94,94,94));
		gameContent.add(Box.createRigidArea(new Dimension(500,15)));

		//create the scrollpane as container for the gameContent
		scrollPane 		= 	new AScrollPane(500, 550, gameContent, false, true);
//		scrollPane.setBorder(null);
//		scrollPane.setPreferredSize(new Dimension(500, 550));
//		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
//		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		allPanel.add(scrollPane);
		
		//get all the players in the competition
		ArrayList<String> playerNames = gui.getCompetitionPlayers(compID);
		playerContent.add(addLabel("Players", 300));
		if(playerNames.size() !=0){
			playerContent.add(Box.createRigidArea(new Dimension(400,10)));
			playerContent.add(paintPlayer(null, 0));
			for(int e = 0; e < playerNames.size(); e++){
				playerContent.add(paintPlayer(playerNames.get(e), e));
			}
		}
		
		//get all the games in the competition
		ArrayList<Integer> gameInts = gui.getSpectatableGames(compID);
		gameContent.add(addLabel("Games", 400));
		if(gameInts.size() != 0){
			gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			for(int e : gameInts){
				gameContent.add(paintGame(e));
				gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			}
		}
		
		if(back != null){
			back.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					gui.switchPanel(new SpectatorCompetitionsPanel(gui));
				}
			});
			this.add(back, BorderLayout.NORTH);
		}
		else{
			this.add(mp, BorderLayout.NORTH);
		}
		this.add(allPanel, BorderLayout.CENTER);
		gui.setLoadingCursor(false);
	}

	private JPanel paintPlayer(String name, int nr) {
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(350,40));
		panel.setPreferredSize(new Dimension(350,40));
		panel.setMaximumSize(new Dimension(350,40));
		panel.setBackground(new Color(84,84,84));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel nrPanel	=	new JPanel();
		JPanel nameP 	= 	new JPanel();
		JPanel score	=	new JPanel();
		
		if(name == null){
			nrPanel.add(new SLabel("nr", SLabel.LEFT, new Font("Arial", Font.BOLD, 20)));
			nameP.add(new SLabel("Name", SLabel.LEFT, new Font("Arial", Font.BOLD, 20)));
			score.add(new SLabel("Score", SLabel.LEFT, new Font("Arial", Font.BOLD, 20)));
		}
		else{
			nrPanel.add(new SLabel("" + nr, SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
			nameP.add(new SLabel(name, SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
			String ranking = gui.getPlayerRanking(compID, name);
			if(ranking != null){
				ranking = ranking.substring(0, 7);
				score.add(new SLabel(ranking, SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
			}
			else{
				score.add(new SLabel("0", SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
			}
		}
		
		nrPanel.setMinimumSize(new Dimension(20,40));
		nrPanel.setPreferredSize(new Dimension(20,40));
		nrPanel.setMaximumSize(new Dimension(20,40));
		nameP.setMinimumSize(new Dimension(200,40));
		nameP.setPreferredSize(new Dimension(200,40));
		nameP.setMaximumSize(new Dimension(200,40));
		score.setMinimumSize(new Dimension(120,40));
		score.setPreferredSize(new Dimension(120,40));
		score.setMaximumSize(new Dimension(120,40));
		
		nrPanel.setBackground(panel.getBackground());
		nameP.setBackground(panel.getBackground());
		score.setBackground(panel.getBackground());
		
		c.gridx = 0;
		c.gridy = 0;
		panel.add(nrPanel, c);
		c.gridx++;
		panel.add(nameP, c);
		c.gridx++;
		panel.add(score, c);
		
		return panel;
	}

	private JPanel paintGame(final int gameID) {
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(450,90));
		panel.setPreferredSize(new Dimension(450,90));
		panel.setMaximumSize(new Dimension(450,90));
		panel.setBackground(new Color(84,84,84));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel owner		= new JPanel();
		JPanel versus		= new JPanel();
		JPanel opponent 	= new JPanel();
		SButton spectate	= new SButton("Spectate", SButton.GREY, 220, 40);
		
		ArrayList<String> players = gui.getGamePlayers(gameID);
		
		owner.add(new SLabel(players.get(0), SLabel.CENTER, new Font("Arial", Font.BOLD, 20)));
		versus.add(new SLabel("versus", SLabel.CENTER, new Font("Arial", Font.PLAIN, 20)));
		opponent.add(new SLabel(players.get(1), SLabel.CENTER, new Font("Arial", Font.BOLD, 20)));
		
		owner.setMinimumSize(new Dimension(150,30));
		owner.setPreferredSize(new Dimension(150,30));
		owner.setMaximumSize(new Dimension(150,30));
		versus.setMinimumSize(new Dimension(150,25));
		versus.setPreferredSize(new Dimension(150,25));
		versus.setMaximumSize(new Dimension(150,25));
		opponent.setMinimumSize(new Dimension(150,35));
		opponent.setPreferredSize(new Dimension(150,35));
		opponent.setMaximumSize(new Dimension(150,35));
		spectate.setMinimumSize(spectate.getPreferredSize());
		
		owner.setBackground(panel.getBackground());
		versus.setBackground(panel.getBackground());
		opponent.setBackground(panel.getBackground());
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,30,0,0);
		panel.add(owner, c);
		c.gridy++;;
		panel.add(versus, c);
		c.gridheight = 2;
		c.gridx = 1;
		panel.add(spectate, c);
		c.gridx--;
		c.gridy++;
		panel.add(opponent,c);
		
		
		spectate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.spectateGame(gameID);
				System.out.println(gameID);
			}
		});
		return panel;
	}

	private Component addLabel(String text, int width) {
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(width,30));
		panel.setPreferredSize(new Dimension(width,30));
		panel.setMaximumSize(new Dimension(width,30));
		panel.setBackground(new Color(124,124,124));
		SLabel label = new SLabel(text, SLabel.CENTER, new Font("Arial", Font.BOLD, 20));
		panel.add(label);
		return panel;
	}
	
	public void setMenuPanel(JPanel panel){
		this.mp = new MenuPanel(gui, panel);
		this.add(mp, BorderLayout.NORTH);
	}
	
}
