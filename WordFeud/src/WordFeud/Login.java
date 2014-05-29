package WordFeud;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import AccountType.Account;
import GUI.AdminPanel;
import GUI.GUI;
import GUI.ModeratorPanel;
import GUI.PlayerPanel;
import Utility.DBCommunicator;

public class Login {

	private GUI gui;
	
	public Login(GUI gui) {this.gui = gui;}
	
	public String login(Account username, String password) {
		if(DBCommunicator.checkConnection() != null) {
			if(DBCommunicator.requestData("SELECT * FROM account WHERE naam = '" + username.getUsername() + "'") == null) {
				return "That username doesn't exist!";
			}
			if(DBCommunicator.requestData("SELECT * FROM account WHERE wachtwoord = '" + password + "'") == null) {
				return "Incorrect password!";
			}
			if(DBCommunicator.requestData("SELECT * FROM account WHERE naam = '" + username.getUsername() + "'").equals(username.getUsername())) {
				if(DBCommunicator.requestData("SELECT wachtwoord FROM account WHERE wachtwoord = '" + password + "' AND naam = '" + username.getUsername() + "'" ) != null) {
					gui.login(username);
					ArrayList<String> accountRolls = DBCommunicator.requestMoreData("SELECT rol_type FROM accountrol WHERE account_naam = '" + username.getUsername() + "'");
					for(String s:accountRolls) {
						if(s.equals("Player")) {
							gui.switchPanel(new PlayerPanel(gui));
							System.out.println("You have been logged in as: Player");
							return "0";
						}
					}
					for(String s:accountRolls) {
						if(s.equals("Administrator")) {
							gui.switchPanel(new AdminPanel(gui));
							System.out.println("You have been logged in as: Administrator");
							return "0";
						}
						
					}
					for(String s:accountRolls) {
						if(s.equals("Moderator")) {
							gui.switchPanel(new ModeratorPanel(gui));
							System.out.println("You have been logged in as: Moderator");
							return "0";
						}
					}
					return "You do not have a valid account_rol";
				}
				else {
					return "Wrong password!";
				}
			}
			return "ERROR404";
		}
		else {
			JOptionPane.showMessageDialog(null, "You do not have a connection, Wordfeud will now exit :(");
			System.exit(0);
			return "No connection!";
		}
	}
	
	public String register(Account username, String password, String validatePassword) {
		if(DBCommunicator.checkConnection() != null) {
			for(int i = 0; i < username.getUsername().length(); i++) {
				if(Character.isWhitespace(username.getUsername().charAt(i))) {return "No whitespaces allowed!";}
			}
			String[] safe = new String[] {"\"", "\'"};
			for(int i = 0; i < safe.length; i++) {
				if(username.getUsername().contains(safe[i]) || password.contains(safe[i])) {
					return "You can't use quotes!";
				}
			}
			
			if(DBCommunicator.requestData("SELECT naam FROM account WHERE naam = '" + username.getUsername() + "'") == null) {
				if(password.equals(validatePassword)) {
					if(!password.isEmpty() && !(password.length() < 1)) {
						DBCommunicator.writeData("INSERT INTO account(naam, wachtwoord) VALUES('" + username.getUsername() + "', '" + password + "')");
						DBCommunicator.writeData("INSERT INTO accountrol(account_naam, rol_type) VALUES('" + username.getUsername() + "', 'Player')");
						gui.login(username);
						gui.switchPanel(new PlayerPanel(gui));
						return "0";
					}
					else {
						return "That password is too short!";
					}
				}
				else {
					return "Passwords do not match!";
				}
			}
			else {
				return "That username is not available!";
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "You do not have a connection, Wordfeud will now exit :(");
			System.exit(0);
			return "No connection!";
		}
	}
	
}
