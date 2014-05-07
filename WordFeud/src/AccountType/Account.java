package AccountType;

public abstract class Account {
	private  String username;

	public Account(String username) {

	}


	public String getUsername() {
		return username;

	}

	public boolean getIsModerator() {
		return false;
	}

	public boolean getIsAdministrator() {
		return false;
	}

	public boolean getIsPlayer() {
		return false;
	}

}
