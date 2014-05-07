package WordFeud;

import GUI.GUI;
import GUI.PlayerPanel;
import Utility.DBCommunicator;

public class Login {

	private GUI gui;
	
	public Login(GUI gui) {this.gui = gui;}
	
	public String login(String username, String password) {
		if(DBCommunicator.requestData("SELECT * FROM account WHERE naam = '" + username + "'") == null) {
			return "That username doesn't exist!";
		}
		if(DBCommunicator.requestData("SELECT * FROM account WHERE wachtwoord = '" + password + "'") == null) {
			return "Incorrect password!";
		}
		if(DBCommunicator.requestData("SELECT * FROM account WHERE naam = '" + username + "'").equals(username)) {
			if(DBCommunicator.requestData("SELECT wachtwoord FROM account WHERE wachtwoord = '" + password + "' AND naam = '" + username + "'" ) != null) {
				this.gui.switchPanel(new PlayerPanel(gui), true);
				return "0";
			}
			else {
				return "Wrong password!";
			}
		}
		return "ERROR404";
	}
	
	public String register(String username, String password, String validatePassword) {
		if(DBCommunicator.requestData("SELECT naam FROM account WHERE naam = '" + username + "'") == null) {
			if(password.equals(validatePassword)) {
				if(!password.isEmpty() && !(password.length() < 1)) {
					DBCommunicator.writeData("INSERT INTO account(naam, wachtwoord) VALUES('" + username + "', '" + password +"')");
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
