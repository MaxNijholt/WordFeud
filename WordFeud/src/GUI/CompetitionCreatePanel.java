package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Utility.SButton;
import Utility.SComboBox;
import Utility.SLabel;
import Utility.STextField;

@SuppressWarnings("serial")
public class CompetitionCreatePanel extends JPanel implements ActionListener {

	private STextField name;
	private SLabel title, nameLabel, compLabel, layoutLabel, typeLabel, inviteLabel;
	private SButton create, back;
	private SComboBox comp, layout, type, invite;
	private GUI gui;
	
	public CompetitionCreatePanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(new GridBagLayout());
		this.setBackground(new Color(94, 94, 94));
		GridBagConstraints c = new GridBagConstraints();
		this.gui = gui;
		
		name = new STextField("Competition name");
		
		title 		= new SLabel("Create a competition", SLabel.LEFT, new Font("Arial", Font.BOLD, 50));
		
		nameLabel	= new SLabel("Competition name", SLabel.RIGHT, 220, 40);
		compLabel 	= new SLabel("Private Competition", SLabel.RIGHT, 220, 40);
		layoutLabel = new SLabel("Board layout", SLabel.RIGHT, 220, 40);
		typeLabel 	= new SLabel("Competition type", SLabel.RIGHT, 220, 40);
		inviteLabel	= new SLabel("Invite Players", SLabel.RIGHT, 220, 40);
		
		create 		= new SButton("Create", SButton.GREY, 220, 40);
		back 		= new SButton("Back", SButton.GREY, 220, 40);
		comp 		= new SComboBox(220, 40, new String[] {"Open", "Private"});
		layout 		= new SComboBox(220, 40, new String[] {"Random", "Static"});
		type 		= new SComboBox(220, 40, new String[] {"Single", "Double", "Triple"});
		invite		= new SComboBox(220, 40, new String[] {"/Test/Player/1", "/Test/Player/2", "/Test/Player/3", "/Test/Player/4"});
		
		back.addActionListener(this);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(0, 0, 50, 0);
		c.fill = GridBagConstraints.BOTH;
		this.add(title, c);
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 5, 50);
		c.gridy++;
		this.add(nameLabel, c);
		c.gridx++;
		this.add(name, c);
		c.gridx++;
		this.add(inviteLabel, c);
		c.gridy++;
		this.add(invite, c);
		c.gridx = 0;
		this.add(compLabel, c);
		c.gridx++;
		this.add(comp, c);
		c.gridx = 0;
		c.gridy++;
		this.add(layoutLabel, c);
		c.gridx++;
		this.add(layout, c);
		c.gridx = 0;
		c.gridy++;
		this.add(typeLabel, c);
		c.gridx++;
		this.add(type, c);
		c.gridx = 1;
		c.gridy++;
		this.add(create, c);
		c.gridy++;
		this.add(back, c);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)) {
			gui.switchPanel(new LoginPanel(gui));
		}
	}
	
}
