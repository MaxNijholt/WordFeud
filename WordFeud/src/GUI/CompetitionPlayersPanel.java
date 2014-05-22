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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Utility.AScrollPane;
import Utility.Loader;
import Utility.SButton;
import Utility.SLabel;

@SuppressWarnings("serial")
public class CompetitionPlayersPanel extends JPanel implements ActionListener {

	private int compID;
	private AScrollPane scrollPane;
	private JPanel gameContent;
	private JPanel playerContent;
	private AScrollPane playerScrollPane;
	private GUI gui;
	private MenuPanel mp;

	public CompetitionPlayersPanel(GUI gui, int compID) {
		this.gui = gui;
		this.mp = new MenuPanel(gui, null);
		this.compID = compID;

		gui.setLoadingCursor(true);

		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new BorderLayout());

		JPanel allPanel = new JPanel();
		allPanel.setLayout(new FlowLayout());
		allPanel.setBackground(new Color(94, 94, 94));

		// create the playercontent panel, here go all the players
		playerContent = new JPanel();
		playerContent.setLayout(new BoxLayout(playerContent,BoxLayout.PAGE_AXIS));
		playerContent.setBackground(new Color(94, 94, 94));
		playerContent.add(Box.createRigidArea(new Dimension(400, 15)));

		// create the scrollpane as container for the playercontent
		playerScrollPane = new AScrollPane(500, 550, playerContent, false, true);
//		playerScrollPane.setBorder(null);
//		playerScrollPane.setPreferredSize(new Dimension(500, 550));
//		playerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		playerScrollPane.getVerticalScrollBar().setUnitIncrement(16);
//		playerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		allPanel.add(playerScrollPane);
		
		// create the gameContent panel here go all the games
		gameContent = new JPanel();
		gameContent.setLayout(new BoxLayout(gameContent, BoxLayout.PAGE_AXIS));
		gameContent.setBackground(new Color(94, 94, 94));
		gameContent.add(Box.createRigidArea(new Dimension(500, 15)));

		// create the scrollpane as container for the gameContent
		scrollPane = new AScrollPane(475, 500, gameContent, false, true);
//		scrollPane.setBorder(null);
//		scrollPane.setPreferredSize(new Dimension(475, 550));
//		scrollPane
//				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
//		scrollPane
//				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		allPanel.add(scrollPane);
		
		//get all the players in the competition
				ArrayList<String> playerNames = gui.getCompetitionPlayers(compID);
				playerContent.add(addLabel("Players", 1));
				if(playerNames.size() !=0){
					playerContent.add(Box.createRigidArea(new Dimension(400,10)));
					playerContent.add(paintPlayer(null, 0));
					for(int e = 0; e < playerNames.size(); e++){
						playerContent.add(paintPlayer(playerNames.get(e), e));
					}
				}

		ArrayList<Integer> gameInts;
		// currentAccounts new requested games
		gameInts = gui.getRequestedGames(false, false);
		if (gameInts.size() != 0) {
			gameContent.add(addLabel("New request", 1));
			gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			for (int e : gameInts) {
				gameContent.add(paintGame(e, "NewRequest"));
				gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			}
		}

		gameContent.add(addLabel("Playing", 0));
		gameContent.add(Box.createRigidArea(new Dimension(500, 10)));

		// games that are still playing
		gameInts = gui.getPlayingGames(true);
		if (gameInts.size() != 0) {
			gameContent.add(addLabel("Your Turn", 1));
			gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			for (int e : gameInts) {
				gameContent.add(paintGame(e, "Playing"));
				gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			}
		}

		gameInts = gui.getPlayingGames(false);
		if (gameInts.size() != 0) {
			gameContent.add(addLabel("Opponents turn", 1));
			gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			for (int e : gameInts) {
				gameContent.add(paintGame(e, "Playing"));
				gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			}
		}

		gameContent.add(addLabel("Finished games", 0));
		gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
		// games that are finished

		gameInts = gui.getFinishedGames(false);
		if (gameInts.size() != 0) {
			gameContent.add(addLabel("Finished", 1));
			gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			for (int e : gameInts) {
				gameContent.add(paintGame(e, "Finished"));
				gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			}
		}

		gameInts = gui.getFinishedGames(true);
		if (gameInts.size() != 0) {
			gameContent.add(addLabel("Resigned", 1));
			gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			for (int e : gameInts) {
				gameContent.add(paintGame(e, "Finished"));
				gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			}
		}

		gameContent.add(addLabel("Requested", 0));
		gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
		// games that currentPlayer requested

		gameInts = gui.getRequestedGames(true, false);
		if (gameInts.size() != 0) {
			gameContent.add(addLabel("Waiting", 1));
			gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			for (int e : gameInts) {
				gameContent.add(paintGame(e, "Waiting"));
				gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			}
		}

		gameInts = gui.getRequestedGames(true, true);
		if (gameInts.size() != 0) {
			gameContent.add(addLabel("Denied", 1));
			gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			for (int e : gameInts) {
				gameContent.add(paintGame(e, "Denied"));
				gameContent.add(Box.createRigidArea(new Dimension(500, 10)));
			}
		}

		this.add(mp, BorderLayout.NORTH);
		this.add(allPanel, BorderLayout.CENTER);

		gui.setLoadingCursor(false);
	}

	/**
	 * creates a label and returns it in a JPanel to be added
	 * 
	 * @param labelText
	 * @param type
	 * @return
	 */
	private JPanel addLabel(String labelText, int type) {
		JPanel panel = new JPanel();
		if (type == 0) {
			panel.setMinimumSize(new Dimension(400, 35));
			panel.setPreferredSize(new Dimension(400, 35));
			panel.setMaximumSize(new Dimension(400, 35));
			panel.setBackground(new Color(124, 124, 124));
			SLabel label = new SLabel(labelText, SLabel.CENTER, new Font("Arial", Font.BOLD, 25));
			panel.add(label);
		}
		if (type == 1) {
			panel.setMinimumSize(new Dimension(300, 30));
			panel.setPreferredSize(new Dimension(300, 30));
			panel.setMaximumSize(new Dimension(300, 30));
			panel.setBackground(new Color(124, 124, 124));
			SLabel label = new SLabel(labelText, SLabel.CENTER, new Font("Arial", Font.BOLD, 20));
			panel.add(label);
		}
		return panel;
	}

	
	private JPanel paintPlayer(final String name, int nr) {
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(450,40));
		panel.setPreferredSize(new Dimension(450,40));
		panel.setMaximumSize(new Dimension(450,40));
		panel.setBackground(new Color(84,84,84));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel nrPanel	=	new JPanel();
		JPanel nameP 	= 	new JPanel();
		JPanel score	=	new JPanel();
		SButton challenge = new SButton("challenge", SButton.GREY, 100, 35);
		Component rigidArea = null;
		
		if(name == null){
			nrPanel.add(new SLabel("nr", SLabel.LEFT, new Font("Arial", Font.BOLD, 20)));
			nameP.add(new SLabel("Name", SLabel.LEFT, new Font("Arial", Font.BOLD, 20)));
			score.add(new SLabel("Score", SLabel.LEFT, new Font("Arial", Font.BOLD, 20)));
			rigidArea = Box.createRigidArea(new Dimension(100,35));
		}
		else{
			nrPanel.add(new SLabel("" + (nr + 1), SLabel.LEFT, new Font("Arial", Font.PLAIN, 20)));
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
		c.gridx++;
		if(rigidArea == null){
			panel.add(challenge,c);
		}
		else{
			panel.add(rigidArea, c);
		}
		
		challenge.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean doHaveGame = gui.getHaveGameWith(name, compID);
				if(doHaveGame){
					JOptionPane.showMessageDialog(null, "You already have a game with " + name, "Error", JOptionPane.ERROR_MESSAGE);
				}
				else{
					final JFrame newGameFrame = new JFrame();
					JPanel newGamePanel = new JPanel();
					
					newGameFrame.setResizable(false);
					newGameFrame.setTitle("Challenge");
					newGameFrame.setUndecorated(true);
					newGameFrame.setAlwaysOnTop(true);
					
					SButton open 	= new SButton("Open", SButton.GREY, 100, 35);
					SButton closed 	= new SButton("Private", SButton.GREY, 100, 35);
					newGamePanel.add(open);
					newGamePanel.add(closed);
					
					open.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							gui.newGame(name, true);
							gui.switchPanel(new CompetitionPlayersPanel(gui, compID));
							newGameFrame.dispose();
						}
					});
					
					closed.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							gui.newGame(name, false);
							gui.switchPanel(new CompetitionPlayersPanel(gui, compID));
							newGameFrame.dispose();
						}
					});
					
					newGameFrame.setContentPane(newGamePanel);
					newGameFrame.pack();
					newGameFrame.setLocationRelativeTo(null);
					newGameFrame.setVisible(true);
					newGameFrame.setIconImage(Loader.ICON);
				}
			}
			
		});
		
		return panel;
	}
	
	/**
	 * create a view for a game and return it in a JPanel to be added
	 * 
	 * @param gameID
	 * @return
	 */
	private JPanel paintGame(final int gameID, String gameType) {
		System.out.println("paint game: " + gameID);

		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(400, 100));
		panel.setPreferredSize(new Dimension(400, 100));
		panel.setMaximumSize(new Dimension(400, 100));
		panel.setBackground(new Color(84, 84, 84));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// for a game newly requested. option to accept or reject
		if (gameType.equals("NewRequest")) {
			SButton accept = new SButton("accept", SButton.GREY, 220, 40);
			SButton deny = new SButton("deny", SButton.GREY, 220, 40);
			JPanel opponent = new JPanel();
			JPanel play = new JPanel();

			opponent.add(new SLabel(gui.getOpponentName(gameID), SLabel.CENTER,
					new Font("Arial", Font.BOLD, 25)));
			play.add(new SLabel("wants to play with you", SLabel.CENTER,
					new Font("Arial", Font.PLAIN, 20)));

			opponent.setMinimumSize(new Dimension(150, 40));
			opponent.setPreferredSize(new Dimension(150, 40));
			opponent.setMaximumSize(new Dimension(150, 40));
			play.setMinimumSize(new Dimension(250, 40));
			play.setPreferredSize(new Dimension(250, 40));
			play.setMaximumSize(new Dimension(250, 40));
			accept.setMinimumSize(new Dimension(100,40));
			accept.setPreferredSize(new Dimension(100,40));
			accept.setMaximumSize(new Dimension(100,40));
			deny.setMinimumSize(new Dimension(100,40));
			deny.setPreferredSize(new Dimension(100,40));
			deny.setMaximumSize(new Dimension(100,40));

			opponent.setBackground(panel.getBackground());
			play.setBackground(panel.getBackground());

			c.gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(5, 5, 0, 0);
			panel.add(opponent, c);
			c.gridy++;
			panel.add(play, c);
			c.gridx++;
			c.gridy--;
			panel.add(accept, c);
			c.gridy++;
			panel.add(deny, c);

			accept.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					gui.acceptGame(gameID, compID);
				}
			});
			deny.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					gui.denyGame(gameID, compID);
				}
			});
		}
		// for a game that is playing. option to select
		else if (gameType.equals("Playing")) {
			JPanel opponent = new JPanel();
			JPanel lastTurn = new JPanel();
			SButton select = new SButton("Select", SButton.GREY, 220, 40);

			opponent.add(new SLabel(gui.getOpponentName(gameID), SLabel.CENTER,
					new Font("Arial", Font.BOLD, 25)));
			lastTurn.add(new SLabel(gui.getLastTurntype(gameID) + " "
					+ gui.getLastTurnScore(gameID), SLabel.CENTER, new Font(
					"Arial", Font.PLAIN, 25)));

			opponent.setMinimumSize(new Dimension(200, 40));
			opponent.setPreferredSize(new Dimension(200, 40));
			opponent.setMaximumSize(new Dimension(200, 40));
			lastTurn.setMinimumSize(new Dimension(200, 40));
			lastTurn.setPreferredSize(new Dimension(200, 40));
			lastTurn.setMaximumSize(new Dimension(200, 40));
			select.setMinimumSize(new Dimension(100,40));
			select.setPreferredSize(new Dimension(100,40));
			select.setMaximumSize(new Dimension(100,40));

			opponent.setBackground(panel.getBackground());
			lastTurn.setBackground(panel.getBackground());

			c.gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(0,25, 0, 0);
			panel.add(opponent, c);
			c.gridx++;
			c.gridheight = 2;
			panel.add(select, c);
			c.gridheight = 1;
			c.gridx = 0;
			c.gridy++;
			panel.add(lastTurn, c);

			select.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					gui.selectGame(gameID);
				}
			});
		}
		// for a game that has finished. option to watch/spectate
		else if (gameType.equals("Finished")) {
			JPanel opponent = new JPanel();
			JPanel lastTurn = new JPanel();
			SButton spectate = new SButton("Spectate", SButton.GREY, 220, 40);

			opponent.add(new SLabel(gui.getOpponentName(gameID), SLabel.CENTER,
					new Font("Arial", Font.BOLD, 25)));
			lastTurn.add(new SLabel(gui.getLastTurntype(gameID) + " "
					+ gui.getLastTurnScore(gameID), SLabel.CENTER, new Font(
					"Arial", Font.PLAIN, 25)));

			opponent.setMinimumSize(new Dimension(200, 30));
			lastTurn.setMinimumSize(new Dimension(200, 30));
			spectate.setMinimumSize(spectate.getPreferredSize());

			opponent.setBackground(panel.getBackground());
			lastTurn.setBackground(panel.getBackground());

			c.gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(0, 50, 0, 0);
			panel.add(opponent, c);
			c.gridx++;
			c.gridheight = 2;
			panel.add(spectate, c);
			c.gridheight = 1;
			c.gridx = 0;
			c.gridy++;
			panel.add(lastTurn, c);

			spectate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					gui.spectateGame(gameID);
				}
			});
		}
		// for a game that has been requested. no options
		else if (gameType.equals("Denied") || gameType.equals("Waiting")) {
			JPanel opponent = new JPanel();
			JPanel type = new JPanel();

			opponent.add(new SLabel(gui.getOpponentName(gameID), SLabel.CENTER,
					new Font("Arial", Font.BOLD, 25)));
			type.add(new SLabel(gameType, SLabel.CENTER, new Font("Arial",
					Font.PLAIN, 25)));

			opponent.setMinimumSize(new Dimension(200, 30));
			type.setMinimumSize(new Dimension(200, 30));

			opponent.setBackground(panel.getBackground());
			type.setBackground(panel.getBackground());

			c.gridx = 0;
			c.gridy = 0;
			panel.add(opponent, c);
			c.gridy++;
			panel.add(type, c);
		}
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
