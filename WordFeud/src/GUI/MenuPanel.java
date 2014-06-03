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

/**
 * @author Stan van Heumen
 */
@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements ActionListener {
	
	// Instance variables
	private GUI 						gui;
	private SComboBox					switcher;
	private SButton						settings, 
										stats, 
										player, 
										admin, 
										mod, 
										spectator, 
										logout, 
										back;
	private ArrayList<SButton>			allButtons;
	private String 						backPanel;
	/**
	 * Constructor parameters: Gui g, JPanel bp<br>
	 * This constructor creates a MenuPanel that you can use for switching between panels<br>
	 * The MenuPanel has a BackPanel that returns you to the last panel (bp)
	 */
	public MenuPanel(GUI g, String bp) {
		init(g, bp);
		
		initButtons();
		
		SButton[] rollsArray = new SButton[allButtons.size()];
		for(int i = 0; i < allButtons.size(); i++) {
			rollsArray[i] = allButtons.get(i);
		}
		
		switcher = new SComboBox(250, 30, rollsArray, false);
		switcher.setPlaceholder("Menu");
	
		add(switcher, BorderLayout.EAST);
		if(backPanel != null) {
			back = new SButton("Back", SButton.GREY, 250, 30);
			back.addActionListener(this);
			back.setCustomRounded(false, false, false, false);
			add(back, BorderLayout.WEST);
			allButtons.add(back);
		}
	}
	
	/**
	 * Private initialize method for the constructor to set the default stuff.
	 */
	private void init(GUI g, String bp) {
		setPreferredSize(new Dimension( GUI.WIDTH, 30));
		setLayout(new BorderLayout());
		setBackground(SButton.GREY);
		gui 		= g;
		backPanel 	= bp;
		allButtons 	= new ArrayList<SButton>();
	}
	
	/**
	 * Private initialize method for the buttons in the MenuPanel.
	 */
	private void initButtons() {
		settings = new SButton("Your Settings", SButton.WHITE, 250, 40);
		settings.setTextColor(new Color(100, 100, 100));
		settings.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
		settings.setAlignment(SButton.LEFT);
		settings.addActionListener(this);
		settings.setCustomRounded(false, false, false, false);
		allButtons.add(settings);
		
		stats = new SButton("User Stats", SButton.WHITE, 250, 40);
		stats.setTextColor(new Color(100, 100, 100));
		stats.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
		stats.setAlignment(SButton.LEFT);
		stats.addActionListener(this);
		stats.setCustomRounded(false, false, false, false);
		allButtons.add(stats);
		
		if(gui.getApplication().getCurrentAccount().getPlayer() != null) {
			player = new SButton("> Player", SButton.WHITE, 250, 40);
			player.setTextColor(new Color(100, 100, 100));
			player.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
			player.setAlignment(SButton.LEFT);
			player.addActionListener(this);
			player.setCustomRounded(false, false, false, false);
			allButtons.add(player);
		}
		if(gui.getApplication().getCurrentAccount().getAdmin() != null) {
			admin = new SButton("> Administrator", SButton.WHITE, 250, 40);
			admin.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
			admin.setTextColor(new Color(100, 100, 100));
			admin.setAlignment(SButton.LEFT);
			admin.addActionListener(this);
			admin.setCustomRounded(false, false, false, false);
			allButtons.add(admin);
		}
		if(gui.getApplication().getCurrentAccount().getMod() != null) {
			mod = new SButton("> Moderator", SButton.WHITE, 250, 40);
			mod.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
			mod.setTextColor(new Color(100, 100, 100));
			mod.setAlignment(SButton.LEFT);
			mod.addActionListener(this);
			mod.setCustomRounded(false, false, false, false);
			allButtons.add(mod);
		}
		
		spectator = new SButton("> Spectator", SButton.WHITE, 250, 40);
		spectator.setTextColor(new Color(100, 100, 100));
		spectator.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
		spectator.setAlignment(SButton.LEFT);
		spectator.addActionListener(this);
		spectator.setCustomRounded(false, false, false, false);
		allButtons.add(spectator);
		
		logout = new SButton("Log Out", SButton.WHITE, 250, 40);
		logout.setTextColor(new Color(100, 100, 100));
		logout.setColors(new Color(255, 255, 255), new Color(245, 245, 245), new Color(235, 235, 235));
		logout.setAlignment(SButton.LEFT);
		logout.addActionListener(this);
		logout.setCustomRounded(false, false, false, false);
		allButtons.add(logout);
	}

	/**
	 * Overridden ActionAdapter method that allows this panel to receive input by using SButtons.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(logout)) 	{gui.logout();}
		if(e.getSource().equals(settings)) 	{gui.switchPanel(new SettingsPanel(gui, gui.getApplication().getCurrentAccount()));}
		if(e.getSource().equals(stats)) 	{gui.switchPanel(new StatisticsPanel(gui));}
		if(e.getSource().equals(player)) 	{gui.switchPanel(new PlayerPanel(gui));}
		if(e.getSource().equals(admin)) 	{gui.switchPanel(new AdminPanel(gui));}
		if(e.getSource().equals(mod)) 		{gui.switchPanel(new ModeratorPanel(gui));}
		if(e.getSource().equals(spectator)) {gui.switchPanel(new SpectatorCompetitionsPanel(gui));}
		if(e.getSource().equals(back)) 		{
			String name = backPanel;
			if(name.equals("AdminPanel")) 					{gui.switchPanel(new AdminPanel(gui));}
			if(name.equals("CompetitionCreatePanel")) 		{gui.switchPanel(new CompetitionCreatePanel(gui));}
			if(name.equals("CompetitionPanel")) 			{gui.switchPanel(new CompetitionPanel(gui));}
			if(name.equals("GamePanel")) 					{gui.switchPanel(new GamePanel(gui));}
			if(name.equals("LoginPanel")) 					{gui.switchPanel(new LoginPanel(gui));}
			if(name.equals("ModeratorPanel")) 				{gui.switchPanel(new ModeratorPanel(gui));}
			if(name.equals("PlayerPanel")) 					{gui.switchPanel(new PlayerPanel(gui));}
			if(name.equals("RegisterPanel")) 				{gui.switchPanel(new RegisterPanel(gui));}
			if(name.equals("SettingsPanel")) 				{gui.switchPanel(new SettingsPanel(gui, gui.getApplication().getCurrentAccount()));}
			if(name.equals("SpectatorCompetitionsPanel")) 	{gui.switchPanel(new SpectatorCompetitionsPanel(gui));}
			if(name.equals("SpectatorPanel")) 				{gui.switchPanel(new SpectatorPanel(gui, gui.getApplication().getSelectedCompetition().getID()));}
			if(name.equals("StatisticsPanel"))				{gui.switchPanel(new StatisticsPanel(gui));}
			
		}
		switcher.getPopop().setVisible(false);
	}
	
	// Getters
	public ArrayList<SButton> getAllButtons() 	{return allButtons;}
	public SButton getBackButton() 				{return back;}
	
}
