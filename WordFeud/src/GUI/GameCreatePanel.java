package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import Utility.SButton;
import Utility.SComboBox;
import Utility.SLabel;

@SuppressWarnings("serial")
public class GameCreatePanel extends JPanel {

	private SLabel 		title, game, layout, challenge;
	private SComboBox 	gameBox, layoutBox, challengeBox;
	private SButton		create, cancel/*, random*/;
//	private GUI 		gui;
	
	public GameCreatePanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		//this.gui = gui;
		
		title			= new SLabel("Game", SLabel.LEFT, new Font("Arial", Font.BOLD, 40), 220, 40);
		game 			= new SLabel("Private game", SLabel.RIGHT, 220, 40);
		layout 			= new SLabel("Board Layout", SLabel.RIGHT, 220, 40);
		challenge 		= new SLabel("Challenge player", SLabel.RIGHT, 220, 40);
		
		gameBox 		= new SComboBox(220, 40, new String[] {"ON", "OFF"});
		layoutBox 		= new SComboBox(220, 40, new String[] {"Random", "Static"});
		challengeBox 	= new SComboBox(220, 40, new String[] {"/Test/Player/1", "/Test/Player/2", "/Test/Player/3", "/Test/Player/4"});
	
		create			= new SButton("Create", SButton.GREY, 220, 40);
		cancel			= new SButton("Cancel", SButton.GREY, 220, 40);
		//random			= new SButton("Random", SButton.GREY, 220, 40);
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 50, 0);
		c.fill = GridBagConstraints.BOTH;
		this.add(title);
		c.insets = new Insets(0, 15, 5, 0);
		c.gridwidth = 1;
		c.gridy++;
		this.add(game, c);
		c.gridx++;
		this.add(gameBox, c);
		c.gridy++;
		c.gridx = 0;
		this.add(layout, c);
		c.gridx++;
		this.add(layoutBox, c);
		c.gridy++;
		c.gridx = 0;
		this.add(challenge, c);
		c.gridx++;
		this.add(challengeBox, c);
		c.gridy++;
		c.gridx = 0;
		this.add(create, c);
		c.gridx++;
		this.add(cancel, c);
	}
	
}
