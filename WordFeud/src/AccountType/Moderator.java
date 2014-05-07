package AccountType;

import Utility.DBCommunicator;

/**
 * Moderator Class
 * 
 */

public class Moderator extends Account {
	private String username;

	private String pending;

	public Moderator(String username) {
		super(username);
		this.username = username;

	}

	/**
	 * Gets unapproved words out of a list from the Database
	 * 
	 * @return a list of pending words
	 */
	public String getNotAprovedWords() {

		pending = DBCommunicator
				.requestData("SELECT * FROM woordenboek where status = 'Pending'");

		return pending;

	}

	/**
	 * adds a word to the Database
	 * 
	 * @param word
	 */
	public void addWord(String word) {
		DBCommunicator.writeData(word);
	}

	/**
	 * Aproves a word that was pending in the Database
	 * 
	 * @param word
	 */
	public void aproveWord(String word) {
		DBCommunicator
				.writeData("UPDATE woordenboek set status='Accepted' where woord = "
						+ "'" + word + "'");

	}

	/**
	 * Denies a word that was pending in the database
	 * 
	 * @param word
	 */
	public void denyWord(String word) {
		DBCommunicator
				.writeData("UPDATE woordenboek set status='Denied' where woord = "
						+ "'" + word + "'");

	}
}
