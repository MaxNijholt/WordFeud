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

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Utility.SButton;
import Utility.SComboBox;
import Utility.SLabel;

@SuppressWarnings("serial")
public class GameCreatePanel extends JPanel implements ActionListener {

	private SLabel 		title, game, layout, challenge;
	private SComboBox 	gameBox, layoutBox, challengeBox;
	private SButton		create, cancel, random;
	private GUI 		gui;
	
	public GameCreatePanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.gui = gui;
		
		title			= new SLabel("Create a new game", SLabel.LEFT, new Font("Arial", Font.BOLD, 30));
		game 			= new SLabel("Private game", SLabel.RIGHT, 220, 40);
		layout 			= new SLabel("Board Layout", SLabel.RIGHT, 220, 40);
		challenge 		= new SLabel("Challenge player", SLabel.RIGHT, 220, 40);
		
		gameBox 		= new SComboBox(220, 40, new String[] {"ON", "OFF"});
		layoutBox 		= new SComboBox(220, 40, new String[] {"Random", "Static"});
		challengeBox 	= new SComboBox(220, 40, new String[] {"/Test/Player/1", "/Test/Player/2", "/Test/Player/3", "/Test/Player/4"});
	
		create			= new SButton("Create", SButton.GREY, 220, 40);
		cancel			= new SButton("Cancel", SButton.GREY, 220, 40);
		random			= new SButton("Random", SButton.ORANGE, 80, 40);
		
		create.addActionListener(this);
		cancel.addActionListener(this);
		random.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.setBackground(getBackground());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 15, 5, 0);
		c.gridx = 0;
		c.gridy = 0;
		buttonPanel.add(game, c);
		c.gridx++;
		buttonPanel.add(gameBox, c);
		c.gridy++;
		c.gridx = 0;
		buttonPanel.add(layout, c);
		c.gridx++;
		buttonPanel.add(layoutBox, c);
		c.gridy++;
		c.gridx = 0;
		buttonPanel.add(challenge, c);
		c.gridx++;
		buttonPanel.add(challengeBox, c);
		c.gridx++;
		buttonPanel.add(random, c);
		c.gridy++;
		c.gridx = 0;
		buttonPanel.add(create, c);
		c.gridx++;
		buttonPanel.add(cancel, c);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(getBackground());
		titlePanel.setLayout(new BorderLayout());
		titlePanel.add(title, BorderLayout.WEST);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 20, 0));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(getBackground());
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		
		this.add(mainPanel);
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
