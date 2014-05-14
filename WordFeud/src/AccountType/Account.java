package AccountType;

public abstract class Account {
<<<<<<< .merge_file_a05768
	
	public Account(String account){
		
=======
	private String username;
	
	public Account(String account){
		username = account;
>>>>>>> .merge_file_a04484
	}
	
	public String getEmail(){
		return "";
	
	}

	public String getPassword(){
		return "";
		
	}
	
	public String getUsername(){
<<<<<<< .merge_file_a05768
		return null;
=======
		return username;
>>>>>>> .merge_file_a04484
		
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
