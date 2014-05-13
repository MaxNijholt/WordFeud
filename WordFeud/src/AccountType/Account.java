package AccountType;

public abstract class Account {
	private String username;
	
	public Account(String account){
		username = account;
	}
	
	public String getEmail(){
		return "";
	
	}

	public String getPassword(){
		return "";
		
	}
	
	public String getUsername(){
		return username;
		
	}
	
	public void setUsername(String username){
		
	}
	
	public boolean getIsModerator(){
		return false;
	}
	
	public boolean getIsAdministrator(){
		return false;
	}
	
	public Account[] SearchPlayer(String partialname){
		return null;
	}
}
