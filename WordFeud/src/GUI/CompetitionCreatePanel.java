package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import Utility.STextField;

@SuppressWarnings("serial")
public class CompetitionCreatePanel extends JPanel implements ActionListener {

	private STextField 			name, player;
	private SComboBox 			addPlayers;
	private SLabel 				title, nameLabel, playerLabel, addLabel, addedLabel;
	private ArrayList<SLabel>	addedPlayers;
	private SButton 			create, back, add;
	private JPanel				addPanel, addedPanel;
	private GUI 				gui;
	
	public CompetitionCreatePanel(GUI gui) {
		this.gui = gui;
		gui.setLoadingCursor(true);
		
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(new Color(94, 94, 94));
		
		addedPlayers	= new ArrayList<SLabel>();
		
		title 			= new SLabel("New competition", SLabel.LEFT, new Font("Arial", Font.BOLD, 50));
		nameLabel		= new SLabel("Competition name", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 220, 20);
		playerLabel		= new SLabel("Maximum players", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 220, 20);
		addLabel		= new SLabel("Add player", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 220, 20);
		addedLabel		= new SLabel("Added players", SLabel.LEFT, 220, 40);
		
		create 			= new SButton("Create", SButton.GREY, 220, 40);
		back 			= new SButton("Back", SButton.GREY, 220, 40);
		add 			= new SButton("Add", SButton.GREY, 110, 40);
		
		name 			= new STextField("Competition name", 220, 40);
		player 			= new STextField("Maximum players (2 up to 24)", 220, 40);
		// To fill the challenger box
				ArrayList<String> allPlayers = DBCommunicator.requestMoreData("SELECT naam FROM account ORDER BY naam ASC");
				String[] players = new String[allPlayers.size()];
				for(int i = 0; i < allPlayers.size(); i++) {players[i] = allPlayers.get(i);}
				// Creating the challenger box
		addPlayers		= new SComboBox(220, 40, players);
		addPlayers.setPlaceholder("Playername");
		
		back.addActionListener(this);
		add.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.setBackground(getBackground());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 15, 5, 0);
		buttonPanel.add(nameLabel, c);
		c.gridy++;
		buttonPanel.add(name, c);
		c.gridx++;
		c.gridy = 0;
		buttonPanel.add(addLabel, c);
		c.gridy++;
		buttonPanel.add(addPlayers, c);
		c.gridx++;
		buttonPanel.add(add, c);
		c.gridx = 0;
		c.gridy++;
		buttonPanel.add(playerLabel, c);
		c.gridy++;
		buttonPanel.add(player, c);
		c.gridy++;
		buttonPanel.add(create, c);
		c.gridy++;
		buttonPanel.add(back, c);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(new Color(0, 0, 0, 100));
		titlePanel.setLayout(new BorderLayout());
		titlePanel.add(title, BorderLayout.WEST);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		addPanel = new JPanel();
		addPanel.setBackground(getBackground());
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.PAGE_AXIS));
		for(int i = 0; i < addedPlayers.size(); i++) {
			addPanel.add(addedPlayers.get(i));
		}
		
		addedPanel = new JPanel();
		addedPanel.setBackground(getBackground());
		addedPanel.setLayout(new BorderLayout());
		addedPanel.add(addedLabel, BorderLayout.NORTH);
		addedPanel.add(addPanel, BorderLayout.CENTER);
		addedPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(getBackground());
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.WEST);
		mainPanel.add(addedPanel, BorderLayout.EAST);
		
		this.add(mainPanel);
		gui.setLoadingCursor(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)) {
			gui.switchPanel(new CompetitionPanel(gui));
		}
		if(e.getSource().equals(add)) {
			addedPlayers.add(new SLabel(addPlayers.getSelectedItem(), SLabel.LEFT, 220, 40));
			System.out.println("Players:");
			for(int i = 0; i < addedPlayers.size(); i++) {
				System.out.println(addedPlayers.get(i));
			}
		}
	}
	
}
