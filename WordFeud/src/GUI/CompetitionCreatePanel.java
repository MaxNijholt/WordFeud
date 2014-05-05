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
import Utility.SLabel;
import Utility.STextArea;
import Utility.STextField;

@SuppressWarnings("serial")
public class CompetitionCreatePanel extends JPanel implements ActionListener {

	private STextField 	name, player, addPlayers;
	private SLabel 		title, nameLabel, playerLabel, addLabel, addedLabel;
	private STextArea	addedPlayers;
	private SButton 	create, back, add;
	private GUI 		gui;
	
	public CompetitionCreatePanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(new Color(94, 94, 94));
		this.gui = gui;
		
		addedPlayers	= new STextArea(300, 300, false);
		addedPlayers.setHighlighter(null);
		
		title 			= new SLabel("Create a competition", SLabel.LEFT, new Font("Arial", Font.BOLD, 30));
		nameLabel		= new SLabel("Competition name", SLabel.RIGHT, 130, 40);
		playerLabel		= new SLabel("Maximum players", SLabel.RIGHT, 130, 40);
		addLabel		= new SLabel("Add player", SLabel.RIGHT, 130, 40);
		addedLabel		= new SLabel("Added players", SLabel.LEFT, 130, 40);
		
		create 			= new SButton("Create", SButton.GREY, 220, 40);
		back 			= new SButton("Back", SButton.GREY, 220, 40);
		add				= new SButton("Add", SButton.GREY, 100, 40);
		
		name 			= new STextField("Competition name");
		player 			= new STextField("Maximum players (2 up to 24)");
		addPlayers		= new STextField("Name");
		
		back.addActionListener(this);
		add.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.setBackground(getBackground());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		buttonPanel.add(nameLabel, c);
		c.gridx++;
		c.insets = new Insets(0, 15, 5, 0);
		buttonPanel.add(name, c);
		c.gridx = 0;
		c.gridy++;
		buttonPanel.add(playerLabel, c);
		c.gridx++;
		buttonPanel.add(player, c);
		c.gridx = 0;
		c.gridy++;
		buttonPanel.add(addLabel, c);
		c.gridx++;
		buttonPanel.add(addPlayers, c);
		c.gridx++;
		buttonPanel.add(add, c);
		c.gridx = 0;
		c.gridy++;
		buttonPanel.add(create, c);
		c.gridx++;
		buttonPanel.add(back, c);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(getBackground());
		titlePanel.setLayout(new BorderLayout());
		titlePanel.add(title, BorderLayout.WEST);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
		
		JPanel addedPanel = new JPanel();
		addedPanel.setBackground(getBackground());
		addedPanel.setLayout(new BorderLayout());
		addedPanel.add(addedLabel, BorderLayout.NORTH);
		addedPanel.add(addedPlayers, BorderLayout.CENTER);
		addedPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(getBackground());
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(addedPanel, BorderLayout.EAST);
		
		this.add(mainPanel);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)) {
			gui.switchPanel(new LoginPanel(gui));
		}
		if(e.getSource().equals(add)) {
			if(!(addPlayers.getText().length() < 1)) {
				addedPlayers.addText(addPlayers.getText());
				addPlayers.setText("");
			}
		}
	}
	
}
