package AccountType;

public abstract class Account {
	
	public Account(String account){
		
	}
	
	public String getEmail(){
		return "";
	
	}

	public String getPassword(){
		return "";
		
	}
	
	public String getUsername(){
		return null;
		
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
