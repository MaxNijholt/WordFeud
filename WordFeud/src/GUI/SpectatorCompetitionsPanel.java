package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

public class SpectatorCompetitionsPanel extends JPanel{

	private JScrollPane scrollPane;
	private JPanel gameContent;
	private GUI gui;
	private MenuPanel mp;

	public SpectatorCompetitionsPanel(GUI myGui) {
		
		this.gui = myGui;
		this.mp = new MenuPanel(gui, null);
		gui.setLoadingCursor(true);
		
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new BorderLayout());

		JPanel allPanel = new JPanel();
		allPanel.setLayout(new BoxLayout(allPanel, BoxLayout.PAGE_AXIS));
		allPanel.setBackground(new Color(94,94,94));
					
		//create the gameContent panel here go all the games
		gameContent 	= 	new JPanel();
		gameContent.setLayout(new BoxLayout(gameContent, BoxLayout.PAGE_AXIS));
		gameContent.setBackground(new Color(94,94,94));
		gameContent.add(Box.createRigidArea(new Dimension(500,15)));

		//create the scrollpane as container for the gameContent
		scrollPane 		= 	new JScrollPane(gameContent);
		scrollPane.setBorder(null);
		scrollPane.setPreferredSize(new Dimension(1000, 500));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		allPanel.add(scrollPane);
			
		ArrayList<Integer> compInts;
		//currentAccounts new requested games
		compInts = gui.getSpectatableCompetitions();
		if(compInts.size() != 0){
			gameContent.add(addLabel("Competitions"));
			gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			for(int e : compInts){
				gameContent.add(paintGame(e));
				gameContent.add(Box.createRigidArea(new Dimension(500,10)));
			}
		}
			
		this.add(mp, BorderLayout.NORTH);
		this.add(allPanel, BorderLayout.CENTER);
		gui.setLoadingCursor(false);
	}

	private Component paintGame(final int compID) {
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(600,90));
		panel.setPreferredSize(new Dimension(600,90));
		panel.setMaximumSize(new Dimension(600,90));
		panel.setBackground(new Color(84,84,84));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel owner		= new JPanel();
		JPanel description 	= new JPanel();
		SButton select 	= new SButton("Select", SButton.GREY, 220, 40);
		
		owner.add(new SLabel(gui.getCompetitionOwner(compID), SLabel.CENTER, new Font("Arial", Font.BOLD, 25)));
		description.add(new SLabel(gui.getCompetitionDescription(compID), SLabel.CENTER, new Font("Arial", Font.PLAIN, 25)));
		
		owner.setMinimumSize(new Dimension(250,50));
		owner.setPreferredSize(new Dimension(250,50));
		owner.setMaximumSize(new Dimension(250,50));
		description.setMinimumSize(new Dimension(250,50));
		description.setPreferredSize(new Dimension(250,50));
		description.setMaximumSize(new Dimension(250,50));
		select.setMinimumSize(select.getPreferredSize());
		
		owner.setBackground(panel.getBackground());
		description.setBackground(panel.getBackground());
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,50,0,0);
		panel.add(owner, c);
		c.gridx++;
		c.gridheight = 2;
		panel.add(select, c);
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy++;
		panel.add(description, c);
		
		select.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.spectateCompetition(compID);
			}
		});
		return panel;
	}

	private JPanel addLabel(String text) {
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(400,30));
		panel.setPreferredSize(new Dimension(400,30));
		panel.setMaximumSize(new Dimension(400,30));
		panel.setBackground(new Color(124,124,124));
		SLabel label = new SLabel(text, SLabel.CENTER, new Font("Arial", Font.BOLD, 20));
		panel.add(label);
		return panel;
	}
}
