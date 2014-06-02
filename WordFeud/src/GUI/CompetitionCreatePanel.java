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

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Utility.DBCommunicator;
import Utility.SButton;
import Utility.SComboBox;
import Utility.SLabel;
import Utility.SPopupMenu;
import Utility.STextField;

@SuppressWarnings("serial")
public class CompetitionCreatePanel extends JPanel implements ActionListener {

	private STextField 			name, player, year, day, month;;
	private SComboBox 			addPlayers;
	private SLabel 				nameLabel, playerLabel, endDateLabel;
	private SButton 			create;
	private GUI 				gui;
	private JPanel scrollPane;
	private MenuPanel mp;
	private JPanel buttonPanel;
	private GridBagConstraints c;
	
	public CompetitionCreatePanel(GUI gui) {
		this.gui = gui;
		gui.setLoadingCursor(true);
		
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(new Color(94, 94, 94));
		
		
		nameLabel		= new SLabel("Competition name", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 220, 20);
		playerLabel		= new SLabel("Maximum players", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 220, 20);
		endDateLabel	= new SLabel("End date", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 220, 20);
		
		create 			= new SButton("Create", SButton.GREY, 220, 40);
		
		name 			= new STextField("Competition name", 220, 40);
		player 			= new STextField("Maximum players (up to 24)", 220, 40);
		year  			= new STextField("YYYY", 70, 40);
		month  			= new STextField("MM", 70, 40);
		day  			= new STextField("DD", 70, 40);
		
		mp				= new MenuPanel(gui, "CompetitionPanel");
		buttonPanel		= new JPanel();
		
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

		for(int i = 0; i < 20; i++) {
			SButton l = new SButton("Sla" + i, SButton.WHITE);
			l.setCustomRounded(false, false, false, false);
			l.setTextColor(Color.BLACK);
			l.addActionListener(this);
			scrollPane.add(l);
		}
		
		
		create.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.setBackground(getBackground());
		c = new GridBagConstraints();
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
		c.gridy++;
		c.gridy++;
		buttonPanel.add(create, c);
		
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(getBackground());
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(mp, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.WEST);
		
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
	
	public void showError(){
		SPopupMenu error = new SPopupMenu();
		error.show(create, 230, 0, 500, 40, "Please fill in all fields correctly", Color.red);
	}
	
	public void actionPerformed(ActionEvent e) {	
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
					System.out.println("nope");
					showError();
				}
			}
			else{
				System.out.println("nope");
				showError();
			}	
			
		}
	}
	
}
