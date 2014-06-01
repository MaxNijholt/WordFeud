package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Utility.AScrollPane;
import Utility.DBCommunicator;
import Utility.MComboBox;
import Utility.SButton;
import Utility.SComboBox;
import Utility.SLabel;
import Utility.STextField;

@SuppressWarnings("serial")
public class CompetitionCreatePanel extends JPanel implements ActionListener {

	private STextField 			name, player, endDate, year, day, month;;
	private SComboBox 			addPlayers;
	private SLabel 				nameLabel, playerLabel, endDateLabel, addedLabel;
	private AScrollPane			scroller;
	private ArrayList<SLabel>	addedPlayers;
	private SButton 			create, back, add;
	private JPanel				addPanel, addedPanel;
	private GUI 				gui;
	private JPanel scrollPane;
	private MenuPanel mp;
	private Calendar calendar;
	
	public CompetitionCreatePanel(GUI gui) {
		this.gui = gui;
		gui.setLoadingCursor(true);
		
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(new Color(94, 94, 94));
		
		addedPlayers	= new ArrayList<SLabel>();
		
//		title 			= new SLabel("New competition", SLabel.LEFT, new Font("Arial", Font.BOLD, 50));
		nameLabel		= new SLabel("Competition name", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 220, 20);
		playerLabel		= new SLabel("Maximum players", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 220, 20);
		endDateLabel	= new SLabel("End date", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 220, 20);
		addedLabel		= new SLabel("Added players", SLabel.LEFT, 220, 40);
		
		create 			= new SButton("Create", SButton.GREY, 220, 40);
		back 			= new SButton("Back", SButton.GREY, 220, 40);
		add 			= new SButton("Add players", SButton.GREY, 220, 40);
		
		name 			= new STextField("Competition name", 220, 40);
		player 			= new STextField("Maximum players (2 up to 24)", 220, 40);
		endDate 		= new STextField("YYYY-MM-DD", 220, 40);
		year  			= new STextField("YYYY", 70, 40);
		month  			= new STextField("MM", 70, 40);
		day  			= new STextField("DD", 70, 40);
		
		mp				= new MenuPanel(gui, "CompetitionPanel");
		
		// To fill the challenger box
				ArrayList<String> allPlayers = DBCommunicator.requestMoreData("SELECT naam FROM account ORDER BY naam ASC");
				String[] players = new String[allPlayers.size()];
				for(int i = 0; i < allPlayers.size(); i++) {players[i] = allPlayers.get(i);}
				// Creating the challenger box
		addPlayers		= new SComboBox(220, 40, players);
		addPlayers.setPlaceholder("Playername");
		
		scrollPane = new JPanel();
		scrollPane.setLayout(new GridLayout(0, 1));
		scrollPane.setBackground(getBackground());
		
		scroller 		= new AScrollPane(100, 100, scrollPane, false, true);
		for(int i = 0; i < 20; i++) {
			SButton l = new SButton("Sla" + i, SButton.WHITE);
			l.setCustomRounded(false, false, false, false);
			l.setTextColor(Color.BLACK);
			l.addActionListener(this);
			scrollPane.add(l);
		}
		
		
		back.addActionListener(this);
		add.addActionListener(this);
		create.addActionListener(this);
		
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
		buttonPanel.add(playerLabel, c);
		c.gridx++;
		buttonPanel.add(endDateLabel, c);
		c.gridx--;
		c.gridy++;
		buttonPanel.add(player, c);
		c.gridx++;
		buttonPanel.add(year, c);
		c.gridx++;
		buttonPanel.add(month, c);
		c.gridx++;
		buttonPanel.add(day, c);
		c.gridx = 0;
		c.gridy++;
//		buttonPanel.add(playerLabel, c);
		c.gridy++;
//		buttonPanel.add(player, c);
		c.gridy++;
		buttonPanel.add(create, c);
//		buttonPanel.add(back, c);
		
//		JPanel titlePanel = new JPanel();
//		titlePanel.setBackground(new Color(0, 0, 0, 100));
//		titlePanel.setLayout(new BorderLayout());
//		titlePanel.add(title, BorderLayout.WEST);
//		titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		addPanel = new JPanel();
		addPanel.setBackground(getBackground());
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.PAGE_AXIS));
		for(int i = 0; i < addedPlayers.size(); i++) {
			addPanel.add(addedPlayers.get(i));
		}
		
//		addedPanel = new JPanel();
//		addedPanel.setBackground(getBackground());
//		addedPanel.setLayout(new BorderLayout());
//		addedPanel.add(addedLabel, BorderLayout.NORTH);
//		addedPanel.add(scroller, BorderLayout.CENTER);
//		addedPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(getBackground());
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(mp, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.WEST);
//		mainPanel.add(addedPanel, BorderLayout.EAST);
		
		this.add(mainPanel);
		gui.setLoadingCursor(false);
	}
	
	public int getInt(String text){
		Integer i;
		if(text.matches("[0-9]+")){
			i = Integer.parseInt(text);
			return i;		
		}
		else{
			return 0;
		}


	}
	
	public int getDaysOfMonth(int month){
		if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
			return 31;
		}
		
		if(month == 4 || month == 6 || month == 9 || month == 11){
			return 30;
		}
		
		if(month == 2){
			return 28;
		}
		
		else{
			return 0;
		}
		

	}
	
	
	public boolean getMonth(int year, int month, int day){
		Calendar calendar = Calendar.getInstance();
		if(year > calendar.get(1)){
			return true;
		}
		
		if(year == calendar.get(1) && month > (calendar.get(2) + 1)){
			return true;
		}
		
		if(year == calendar.get(1) && month == (calendar.get(2) + 1) && day > calendar.get(5)){
			return true;
		}
		
		return false;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)) {
			gui.switchPanel(new CompetitionPanel(gui));
		}
		
		if(e.getSource().equals(create)){
			
			if(!name.getText().isEmpty() && !player.getText().isEmpty() && getInt(player.getText()) > 1 && !year.getText().isEmpty() 
					&& !month.getText().isEmpty() && !day.getText().isEmpty() && getInt(player.getText()) < 24
					&& getMonth(getInt(year.getText()), getInt(month.getText()), getInt(day.getText())) == true){
				
				if(getInt(day.getText()) <= getDaysOfMonth(getInt(month.getText())) && getInt(day.getText()) > 0){
					String endDate = year.getText() + "-" + month.getText() + "-" + day.getText();
				gui.getApplication().addCompetition((endDate + " 00:00:00"), name.getText(), 1, getInt(player.getText()));
				gui.getApplication().getSelectedCompetition().addPlayer(gui.getApplication().getCurrentAccount().getUsername());	
				gui.switchPanel(new CompetitionPanel(gui));
				}			
				else{
					System.out.println("Please check if you filled in all fields correctly");
				}
			}
			else{
				System.out.println("Please check if you filled in all fields correctly");
			}	
			
		}
		
		if(e.getSource().equals(add)) {
			boolean alreadyThere = false;
			for(int x = 0; x < scrollPane.getComponents().length; x++) {
				SButton s = (SButton)scrollPane.getComponent(x);
				if(e.getActionCommand().equals(s.getActionCommand())) {
					alreadyThere = true;
					return;
				}
			}
			if(!alreadyThere) {
				SButton l = new SButton(addPlayers.getSelectedItem(), SButton.WHITE);
				l.setCustomRounded(false, false, false, false);
				l.setTextColor(Color.BLACK);
				l.addActionListener(this);
				scrollPane.add(l);
				scrollPane.revalidate();
			}
		}
		for(int i = 0; i < scrollPane.getComponents().length; i ++) {
			if(scrollPane.getComponent(i) instanceof SButton) {
				SButton s = (SButton)scrollPane.getComponent(i);
				if(s.getActionCommand().equals(e.getActionCommand())) {
					scrollPane.remove(scrollPane.getComponent(i));
					scrollPane.revalidate();
					return;
				}
			}
		}
	}
	
}
