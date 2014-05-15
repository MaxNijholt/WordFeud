package WordFeud;

import AccountType.Account;
import GUI.*;
import Utility.DBCommunicator;

public class Login {

	private GUI gui;
	
	public Login(GUI gui) {this.gui = gui;}
	
	public String login(Account username, String password) {
		if(DBCommunicator.requestData("SELECT * FROM account WHERE naam = '" + username.getUsername() + "'") == null) {
			return "That username doesn't exist!";
		}
		if(DBCommunicator.requestData("SELECT * FROM account WHERE wachtwoord = '" + password + "'") == null) {
			return "Incorrect password!";
		}
		if(DBCommunicator.requestData("SELECT * FROM account WHERE naam = '" + username.getUsername() + "'").equals(username.getUsername())) {
			if(DBCommunicator.requestData("SELECT wachtwoord FROM account WHERE wachtwoord = '" + password + "' AND naam = '" + username.getUsername() + "'" ) != null) {
				gui.login(username);
				gui.switchPanel(new PlayerPanel(gui));
				return "0";
			}
			else {
				return "Wrong password!";
			}
		}
		return "ERROR404";
	}
	
	public String register(Account username, String password, String validatePassword) {
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
	
}
