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
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Utility.SButton;
import Utility.SLabel;
import Utility.SPopupMenu;
import Utility.STextField;

@SuppressWarnings("serial")
public class CompetitionCreatePanel extends JPanel implements ActionListener {

	private STextField 			name, player, year, day, month;;
	private SLabel 				nameLabel, playerLabel, endDateLabel;
	private SButton 			create;
	private GUI 				gui;
	private MenuPanel mp;
	private GridBagConstraints c;
	
	public CompetitionCreatePanel(GUI gui) {
		this.gui = gui;
		gui.setLoadingCursor(true);
		
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(new Color(94, 94, 94));
		
		
		nameLabel		= new SLabel("Competition name", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 210, 20);
		playerLabel		= new SLabel("Maximum players", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 210, 20);
		endDateLabel	= new SLabel("End date", SLabel.LEFT, new Font("Arial", Font.PLAIN, 15), 210, 20);
		
		create 			= new SButton("Create", SButton.GREY, 210, 40);
		
		name 			= new STextField("Competition name", 210, 40);
		player 			= new STextField("Maximum players (up to 24)", 210, 40);
		year  			= new STextField("YYYY", 70, 40);
		month  			= new STextField("MM", 70, 40);
		day  			= new STextField("DD", 70, 40);
		
		mp				= new MenuPanel(gui, "CompetitionPanel");
		
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
		c.gridy = 0;
		c.gridx++;
		buttonPanel.add(playerLabel, c);
		c.gridy++;
		buttonPanel.add(player, c);
		c.gridx = 0;
		c.gridy++;
		buttonPanel.add(create, c);
		
		JPanel date = new JPanel();
		date.add(year);
		date.add(month);
		date.add(day);
		
		c.gridy = 0;
		c.gridx = 2;
		buttonPanel.add(endDateLabel, c);
		c.gridy++;
		buttonPanel.add(date, c);
		date.setBackground(new Color(94, 94, 94));
		
		
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
		error.show(create, (create.getWidth() + 15), 0, 210, 40, "Fill in all fields correctly", Color.red);
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
					showError();
				}
			}
			else{
				showError();
			}	
			
		}
	}
	
}
