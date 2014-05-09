package AccountType;

import Utility.DBCommunicator;

public abstract class Account {
	private String username;

	public Account(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;

	}

	public boolean getIsModerator() {
		if (DBCommunicator.requestData(
				"SELECT rol_type FROM accountrol where account_naam = " + "'"
						+ username + "'" + " and rol_type = 'Moderator'")
				.equals("Moderator")) {

			return true;
		} else if (DBCommunicator.requestData("SELECT rol_type FROM accountrol where account_naam = " + "'"
				+ username + "'" + " and rol_type = 'Moderator'") == null) {
			return false;
		}
		return false;
	}

	public boolean getIsAdministrator() {
		if (DBCommunicator.requestData("SELECT rol_type FROM accountrol where account_naam = " + "'"
				+ username + "'" + " and rol_type = 'Aministrator'").equals("Administrator")) {

			return true;
		} else if (DBCommunicator.requestData("SELECT rol_type FROM accountrol where account_naam = " + "'"
				+ username + "'" + " and rol_type = 'Aministrator'") == null) {
			return false;
		}
		return false;
	}

	public boolean getIsPlayer() {

		if (DBCommunicator.requestData("SELECT rol_type FROM accountrol where account_naam = " + "'"
				+ username + "'" + " and rol_type = 'Player'").equals("Player")) {

			return true;
		} else if (DBCommunicator.requestData("SELECT rol_type FROM accountrol where account_naam = " + "'"
				+ username + "'" + " and rol_type = 'Player'") == null) {
			return false;
		}
		return false;
	}

}
