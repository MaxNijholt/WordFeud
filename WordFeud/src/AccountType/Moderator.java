package AccountType;

import java.util.ArrayList;

import Utility.DBCommunicator;

/**
 * Moderator Class
 * 
 */

public class Moderator{
	private ArrayList<String> pendingWords;

	/**
	 * Gets unapproved words out of a list from the Database
	 * 
	 * @return a list of pending words
	 */
	public ArrayList<String> getNotAprovedWords() {

		pendingWords = DBCommunicator
				.requestMoreData("SELECT * FROM woordenboek where status = 'Pending'");

		return pendingWords;

	}

	/**
	 * adds a word to the Database
	 * 
	 * @param word
	 */
	public void addWord(String word) {
		DBCommunicator
				.writeData("INSERT INTO `woordenboek`(`woord`,`letterset_code`,`status`) VALUES("
						+ "'" + word + "'" + ",'EN','Accepted')");
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