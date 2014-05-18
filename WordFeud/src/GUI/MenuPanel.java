package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import Utility.SButton;
import Utility.SComboBox;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements ActionListener {
	private GUI gui;
	private SButton 	logout, stats, extraButton;
	private SComboBox	switcher;
	private SButton		player, admin, mod;

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
		ArrayList<SButton> accountRolls = new ArrayList<SButton>();
		if(gui.getApplication().getCurrentAccount().getPlayer() != null) {
			player = new SButton("Player", SButton.WHITE, 250, 30);
			player.setTextColor(new Color(100, 100, 100));
			player.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
			player.setAlignment(SButton.LEFT);
			player.addActionListener(this);
			player.setCustomRounded(false, false, false, false);
			accountRolls.add(player);
		}
		if(gui.getApplication().getCurrentAccount().getAdmin() != null) {
			admin = new SButton("Administrator", SButton.WHITE, 250, 30);
			admin.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
			admin.setTextColor(new Color(100, 100, 100));
			admin.setAlignment(SButton.LEFT);
			admin.addActionListener(this);
			admin.setCustomRounded(false, false, false, false);
			accountRolls.add(admin);
		}
		if(gui.getApplication().getCurrentAccount().getMod() != null) {
			mod = new SButton("Moderator", SButton.WHITE, 250, 30);
			mod.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
			mod.setTextColor(new Color(100, 100, 100));
			mod.setAlignment(SButton.LEFT);
			mod.addActionListener(this);
			mod.setCustomRounded(false, false, false, false);
			accountRolls.add(mod);
		}
		SButton[] rollsArray = new SButton[accountRolls.size()];
		for(int i = 0; i < accountRolls.size(); i++) {
			rollsArray[i] = accountRolls.get(i);
		}
		switcher		= new SComboBox(250, 30, rollsArray, false);
		
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
			gui.switchPanel(new StatisticsPanel(gui));
		}
		if(e.getSource().equals(extraButton)) {
			
		}
		System.out.println(e.getActionCommand());
		if(e.getSource().equals(player)) {
			gui.switchPanel(new PlayerPanel(gui));
			switcher.getPopop().setVisible(false);
		}
		if(e.getSource().equals(admin)) {
			gui.switchPanel(new AdminPanel(gui));
			switcher.getPopop().setVisible(false);
		}
		if(e.getSource().equals(mod)) {
			gui.switchPanel(new ModeratorPanel(gui));
			switcher.getPopop().setVisible(false);
		}
	}
	
}
