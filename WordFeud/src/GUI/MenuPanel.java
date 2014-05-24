package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import Utility.SButton;
import Utility.SComboBox;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements ActionListener {
	private GUI gui;
	private SComboBox	switcher;
	private SButton		settings, stats, logout, player, admin, mod, spectator, back;
	private JPanel 		backPanel;

	public MenuPanel(GUI gui, JPanel backPanel) {
		this.setPreferredSize(new Dimension( GUI.WIDTH, 30));
		this.setLayout(new BorderLayout());
		this.setBackground(SButton.GREY);
		this.gui = gui;
		this.backPanel = backPanel;
		
		back = new SButton("Back", SButton.GREY, 250, 30);
		back.addActionListener(this);
		back.setCustomRounded(false, false, false, false);
		
		ArrayList<SButton> accountRolls = new ArrayList<SButton>();
		
		settings = new SButton("Your Settings", SButton.WHITE, 250, 40);
		settings.setTextColor(new Color(100, 100, 100));
		settings.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
		settings.setAlignment(SButton.LEFT);
		settings.addActionListener(this);
		settings.setCustomRounded(false, false, false, false);
		accountRolls.add(settings);
		
		stats = new SButton("User Stats", SButton.WHITE, 250, 40);
		stats.setTextColor(new Color(100, 100, 100));
		stats.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
		stats.setAlignment(SButton.LEFT);
		stats.addActionListener(this);
		stats.setCustomRounded(false, false, false, false);
		accountRolls.add(stats);
		
		if(gui.getApplication().getCurrentAccount().getPlayer() != null) {
			player = new SButton("> Player", SButton.WHITE, 250, 40);
			player.setTextColor(new Color(100, 100, 100));
			player.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
			player.setAlignment(SButton.LEFT);
			player.addActionListener(this);
			player.setCustomRounded(false, false, false, false);
			accountRolls.add(player);
		}
		if(gui.getApplication().getCurrentAccount().getAdmin() != null) {
			admin = new SButton("> Administrator", SButton.WHITE, 250, 40);
			admin.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
			admin.setTextColor(new Color(100, 100, 100));
			admin.setAlignment(SButton.LEFT);
			admin.addActionListener(this);
			admin.setCustomRounded(false, false, false, false);
			accountRolls.add(admin);
		}
		if(gui.getApplication().getCurrentAccount().getMod() != null) {
			mod = new SButton("> Moderator", SButton.WHITE, 250, 40);
			mod.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
			mod.setTextColor(new Color(100, 100, 100));
			mod.setAlignment(SButton.LEFT);
			mod.addActionListener(this);
			mod.setCustomRounded(false, false, false, false);
			accountRolls.add(mod);
		}
		
		spectator = new SButton("> Spectator", SButton.WHITE, 250, 40);
		spectator.setTextColor(new Color(100, 100, 100));
		spectator.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
		spectator.setAlignment(SButton.LEFT);
		spectator.addActionListener(this);
		spectator.setCustomRounded(false, false, false, false);
		accountRolls.add(spectator);
		
		logout = new SButton("Log Out", SButton.WHITE, 250, 40);
		logout.setTextColor(new Color(100, 100, 100));
		logout.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
		logout.setAlignment(SButton.LEFT);
		logout.addActionListener(this);
		logout.setCustomRounded(false, false, false, false);
		accountRolls.add(logout);
		
		SButton[] rollsArray = new SButton[accountRolls.size()];
		for(int i = 0; i < accountRolls.size(); i++) {
			rollsArray[i] = accountRolls.get(i);
		}
		switcher		= new SComboBox(250, 30, rollsArray, false);
		switcher.setPlaceholder("Menu");
	
		add(switcher, BorderLayout.EAST);
		if(backPanel != null) {add(back, BorderLayout.WEST);}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(logout)) {
			gui.logout();
		}
		if(e.getSource().equals(settings)) {
			gui.switchPanel(new SettingsPanel(gui, gui.getApplication().getCurrentAccount()));
		}
		if(e.getSource().equals(stats)) {
			gui.switchPanel(new StatisticsPanel(gui));
		}
		if(e.getSource().equals(player)) {
			gui.switchPanel(new PlayerPanel(gui));
		}
		if(e.getSource().equals(admin)) {
			gui.switchPanel(new AdminPanel(gui));
		}
		if(e.getSource().equals(mod)) {
			gui.switchPanel(new ModeratorPanel(gui));
		}
		if(e.getSource().equals(spectator)) {
			gui.switchPanel(new SpectatorCompetitionsPanel(gui));
		}
		if(e.getSource().equals(back)) {
			gui.switchPanel(backPanel);
		}
		switcher.getPopop().setVisible(false);
	}
	
	public SButton getBackButton() {return back;}
	
}
