package AccountType;

import java.util.ArrayList;

import Utility.DBCommunicator;

public class Account {
	protected String username;
	private Moderator mod = null;
	private Player player = null;
	private Administrator admin = null;
	private ArrayList<String> rights;

	public Account(String username) {
		this.username = username;
		rights = this.getUserRights(username);
		if (this.isAdministrator()) {
			admin = new Administrator();
		}
		if (this.isPlayer()) {
			player = new Player(this);
		}
		if (this.isModerator()) {
			mod = new Moderator();
		}
	}

	public void update() {
		this.player = null;
		this.admin = null;
		this.mod = null;
		rights = this.getUserRights(username);
		if (this.isAdministrator()) {
			admin = new Administrator();
		}
		if (this.isPlayer()) {
			player = new Player(this);
		}
		if (this.isModerator()) {
			mod = new Moderator();
		}
	}

	public String getUsername() {
		return username;

	}

	public Moderator getMod() {
		return mod;
	}

	public Player getPlayer() {
		return player;
	}

	public Administrator getAdmin() {
		return admin;
	}

	public ArrayList<String> getUserRights(String player) {
		ArrayList<String> data = DBCommunicator
				.requestMoreData("SELECT rol_type FROM accountrol WHERE account_naam='"
						+ player + "'");
		return data;
	}

	public boolean isModerator() {
		Boolean bool = false;
		for (String right : rights) {
			if (right.equals("Moderator")) {
				bool = true;
			}
		}
		return bool;
	}

	public boolean isAdministrator() {
		Boolean bool = false;
		for (String right : rights) {
			if (right.equals("Administrator")) {
				bool = true;
			}
		}
		return bool;
	}

	public boolean isPlayer() {
		Boolean bool = false;
		for (String right : rights) {
			if (right.equals("Player")) {
				bool = true;
			}
		}
		return bool;
	}

	public void changeUsername(String newName) {
		DBCommunicator.writeData("UPDATE account SET naam = '" + newName +"'");
		this.username = newName;
	}

	public void changePassword(String newPass) {
		DBCommunicator.writeData("UPDATE account SET wachtwoord = '" + newPass +"'");
	}

}