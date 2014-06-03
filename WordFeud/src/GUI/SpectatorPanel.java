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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Utility.AScrollPane;
import Utility.Loader;
import Utility.SButton;
import Utility.SLabel;

@SuppressWarnings("serial")
public class SpectatorPanel extends JPanel {

	private AScrollPane 	scrollPane,
							playerScrollPane;
	private JPanel 			playerContent;
	private JPanel 			gameContent;
	private GUI 			gui;
	private MenuPanel 		mp;
	private int 			compID;
	private SButton 		back;

	public SpectatorPanel(GUI myGui, int compID){
		this.gui = myGui;
		if(gui.getApplication().getCurrentAccount() == null)
			back = new SButton("Back", SButton.GREY, 220, 40);
		else this.mp = new MenuPanel(gui, "SpectatorCompetitionsPanel");
		
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
		allPanel.add(playerScrollPane);
		
		//create the gameContent panel here go all the games
		gameContent 	= 	new JPanel();
		gameContent.setLayout(new BoxLayout(gameContent, BoxLayout.PAGE_AXIS));
		gameContent.setBackground(new Color(94,94,94));
		gameContent.add(Box.createRigidArea(new Dimension(500,15)));

		//create the scrollpane as container for the gameContent
		scrollPane 		= 	new AScrollPane(500, 550, gameContent, false, true);
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
		else this.add(mp, BorderLayout.NORTH);
		this.add(allPanel, BorderLayout.CENTER);
		gui.setLoadingCursor(false);
	}

	private JPanel paintPlayer(final String name, int nr) {
		final JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(350,40));
		panel.setPreferredSize(new Dimension(350,40));
		panel.setMaximumSize(new Dimension(350,40));
		panel.setBackground(new Color(84,84,84));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel nrPanel	=	new JPanel();
		SButton nameP;
		JPanel score	=	new JPanel();
		
		if(name == null){
			nrPanel.add(new SLabel("nr", SLabel.LEFT, new Font("Arial", Font.BOLD, 20)));
			nameP = new SButton("Name", new Color(84,84,84));
			nameP.setFont(new Font("Arial", Font.BOLD, 20));
			score.add(new SLabel("Score", SLabel.LEFT, new Font("Arial", Font.BOLD, 20)));
		}
		else{
			nrPanel.add(new SLabel("" + nr, SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
			nameP = new SButton(name, new Color(84,84,84));
			nameP.setFont(new Font("Arial", Font.ITALIC, 20));
			String ranking = gui.getPlayerRanking(compID, name);
			
			nameP.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					final JFrame playerFrame = new JFrame();
					playerFrame.addWindowListener(new WindowAdapter() {
			            //
			            // Invoked when a window is de-activated.
			            //
			            public void windowDeactivated(WindowEvent e) {
			            	playerFrame.dispose();
			            }
			 
			        });
					JPanel playerPanel = new JPanel();
					
					playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
					
					playerFrame.setResizable(false);
					playerFrame.setTitle(name);
					playerFrame.setAlwaysOnTop(true);
					playerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					
					JPanel competition	= new JPanel();
					JPanel numberGames	= new JPanel();
					JPanel pointAmount	= new JPanel();
					JPanel averagePoints = new JPanel();
					JPanel gamesWon		= new JPanel();
					JPanel gamesLost	= new JPanel();
					JPanel kD			= new JPanel();
					JPanel bayesian		= new JPanel();
					
					competition.add(new SLabel("Competition : " + gui.getApplication().getCompetitionDescription(compID),SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
					numberGames.add(new SLabel("Games		:	" + gui.getApplication().getAmountGames(name, compID),SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
					pointAmount.add(new SLabel("Total points		:	" + gui.getApplication().getTotalPoints(name, compID),SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
					averagePoints.add(new SLabel("Average points per game :	" + gui.getApplication().getAveragePoints(name, compID),SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
					gamesWon.add(new SLabel("Games won		:	" + gui.getApplication().getGamesWon(name, compID),SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
					gamesLost.add(new SLabel("Games	lost	:	" + gui.getApplication().getGamesLost(name, compID),SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
					kD.add(new SLabel("W/L		:	" + gui.getApplication().getWinLose(name, compID),SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
					
					String ranking = gui.getPlayerRanking(compID, name);
					if(ranking != null){
						ranking = ranking.substring(0, 4);
					}
					else{
						ranking = 0 + "";
					}
					bayesian.add(new SLabel("Bayesian score	:	" + ranking ,SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
					
					competition.setBackground(panel.getBackground());
					numberGames.setBackground(panel.getBackground());
					pointAmount.setBackground(panel.getBackground());	
					averagePoints.setBackground(panel.getBackground());
					gamesWon.setBackground(panel.getBackground());		
					gamesLost.setBackground(panel.getBackground());	
					kD.setBackground(panel.getBackground());		
					bayesian.setBackground(panel.getBackground());	
					playerPanel.setBackground(panel.getBackground());
					
					playerPanel.add(competition);
					playerPanel.add(numberGames);
					playerPanel.add(pointAmount);
					playerPanel.add(averagePoints);
					playerPanel.add(gamesWon);
					playerPanel.add(gamesLost);
					playerPanel.add(kD);
					playerPanel.add(bayesian);
					
					SButton oK = new SButton("OK", SButton.GREY);
					oK.setMinimumSize(new Dimension(100,30));
					oK.setPreferredSize(new Dimension(100,30));
					oK.setMaximumSize(new Dimension(100,30));
					
					playerPanel.add(oK);
					oK.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							playerFrame.dispose();
						}
					});
					
					playerFrame.setContentPane(playerPanel);
					playerFrame.pack();
					playerFrame.setLocationRelativeTo(null);
					playerFrame.setVisible(true);
					playerFrame.setIconImage(Loader.ICON);
				}
			});
			
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
	
	public void setMenuPanel(String panel) {
		this.mp = new MenuPanel(gui, panel);
		this.add(mp, BorderLayout.NORTH);
	}
	
}
