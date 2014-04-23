package AccountType;

import Utility.DBCommunicator;


public class Moderator extends Account {
	

	public Moderator(){
		
	}
	
	
	/**
	 * Gets unapproved words out of a list from the Database
	 * 
	 * @return
	 */
	public String[] getNotAprovedWords(){
		
		String query = "";
		
		DBCommunicator.requestData(query);
		
		return null;
		
	}
	
	public void addWord(String word){
		
	}
	
	public void aproveWord(String word){
		
	}
	
	public void denyWord(){
		
	}
}
