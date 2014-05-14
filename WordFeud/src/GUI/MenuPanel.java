package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Utility.SButton;
import Utility.SComboBox;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements ActionListener {
	private GUI gui;
	private SButton 	logout, stats, extraButton;
	private SComboBox	switcher;

	public MenuPanel(GUI gui) {
		this.setPreferredSize(new Dimension( GUI.WIDTH, 30));
		this.setLayout(new GridLayout(1, 4));
		this.setBackground(new Color(0, 0, 0));
		this.gui = gui;
		logout 			= new SButton("Logout", SButton.GREY, 250, 30);
		logout.setCustomRounded(false, false, false, false);
		stats			= new SButton("Stats", SButton.GREY, 250, 30);
		stats.setCustomRounded(false, false, false, false);
		extraButton 	= new SButton("Extra", SButton.GREY, 250, 30);
		extraButton.setCustomRounded(false, false, false, false);
		switcher		= new SComboBox(250, 30, new String[] {"Administrator", "Player"}, false);
		
		logout.addActionListener(this);
		stats.addActionListener(this);
		extraButton.addActionListener(this);
		
		add(logout);
		add(stats);
		add(extraButton);
		add(switcher);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(logout)) {
			gui.logout();
		}
		if(e.getSource().equals(stats)) {
			gui.switchPanel(new StatisticsPanel());
			// TODO: Add gui parameter
		}
		if(e.getSource().equals(extraButton)) {
			
		}
	}
	
}
