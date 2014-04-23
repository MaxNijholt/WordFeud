package AccountType;

import Utility.DBCommunicator;


public class Moderator extends Account {
	
	
	private String pending;
	

	public Moderator(){
		
	}
	
	
	/**
	 * Gets unapproved words out of a list from the Database
	 * 
	 * @return a list of pending words
	 */
	public String getNotAprovedWords(){
		
		String query = "";
		
		pending = DBCommunicator.requestData(query);
		
		return pending;
		
	}
	
	
	/**
	 * adds a word to the Database
	 * 
	 * @param word
	 */
	public void addWord(String word){
		DBCommunicator.writeData(word);
	}
	
	/**
	 * Aproves a word that was pending in the Database
	 * @param word
	 */
	public void aproveWord(String word){
		DBCommunicator.writeData(word);
		
	}
	
	/**
	 * Denies a word that was pending in the database
	 * @param word
	 */
	public void denyWord(String word){
		DBCommunicator.writeData(word);
		
	}
}
