package GUI;

import java.awt.BorderLayout;
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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Utility.DBCommunicator;
import Utility.SButton;
import Utility.SComboBox;
import Utility.SLabel;

/**
 * @author Stan van Heumen
 */
@SuppressWarnings("serial")
public class GameCreatePanel extends JPanel implements ActionListener {

	private SLabel 		title, game, layout, challenge, newGame;
	private SComboBox 	gameBox, layoutBox, challengeBox;
	private SButton		create, cancel, random;
	private GUI 		gui;
	private MenuPanel	mp;
	
	public GameCreatePanel(GUI gui) {
		this.gui = gui;
		gui.setLoadingCursor(true);
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		this.mp = new MenuPanel(gui);
		
		title			= new SLabel("New game", SLabel.LEFT, new Font("Arial", Font.PLAIN, 50));
		game 			= new SLabel("Private game", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 340, 20);
		layout 			= new SLabel("Board", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 340, 20);
		challenge 		= new SLabel("Challenge player", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 340, 20);
		newGame			= new SLabel("New game with", SLabel.LEFT, new Font("Arial", Font.PLAIN, 30), 340, 40);
		
		gameBox 		= new SComboBox(340, 40, new String[] {"On", "Off"});
		layoutBox 		= new SComboBox(340, 40, new String[] {"Normal", "Random"});
		
		// To fill the challenger box
		ArrayList<String> allPlayers = DBCommunicator.requestMoreData("SELECT naam FROM account ORDER BY naam ASC");
		String[] players = new String[allPlayers.size()];
		for(int i = 0; i < allPlayers.size(); i++) {players[i] = allPlayers.get(i);}
		// Creating the challenger box
		challengeBox 	= new SComboBox(340, 40, players);
	
		create			= new SButton("Create", SButton.GREY, 168, 40);
		cancel			= new SButton("Cancel", SButton.GREY, 168, 40);
		random			= new SButton("Random", SButton.ORANGE, 340, 40);
		
		create.addActionListener(this);
		cancel.addActionListener(this);
		random.addActionListener(this);
		
		JPanel sideBySidePanel = new JPanel();
		sideBySidePanel.setBackground(getBackground());
		sideBySidePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));
		sideBySidePanel.add(create);
		sideBySidePanel.add(cancel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.setBackground(getBackground());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 15, 5, 0);
		c.gridx = 0;
		c.gridy = 0;
		buttonPanel.add(game, c);
		c.gridy++;
		buttonPanel.add(gameBox, c);
		c.gridy++;
		buttonPanel.add(layout, c);
		c.gridy++;
		buttonPanel.add(layoutBox, c);
		c.gridy++;
		buttonPanel.add(newGame, c);
		c.gridy++;
		buttonPanel.add(challenge, c);
		c.gridy++;
		buttonPanel.add(challengeBox, c);
		c.gridy++;
		buttonPanel.add(random, c);
		c.gridy++;
		buttonPanel.add(sideBySidePanel, c);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(getBackground());
		titlePanel.setLayout(new BorderLayout());
		titlePanel.add(title, BorderLayout.WEST);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(getBackground());
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		
		this.add(mp);
		this.add(mainPanel);
		gui.setLoadingCursor(false);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(create)) {
			System.out.println("Create a game in the database!");
		}
		if(e.getSource().equals(cancel)) {
			gui.switchPanel(new LoginPanel(gui));
		}
		if(e.getSource().equals(random)) {
			System.out.println("Random!!!!!!");
		}
	}
	
}
