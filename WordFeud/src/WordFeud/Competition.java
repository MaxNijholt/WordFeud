package WordFeud;

import AccountType.Account;
import Utility.DBCommunicator;


@SuppressWarnings("unused")
public class Competition {
	private int id, min, max;
	private String description, owner, startDate, endDate;

	public Competition(int id){
		this.id = id;
		this.min = this.getMinimum();
		this.max = this.getMaximum();
		this.description = this.getCompetitionDescription();
		this.owner = this.getCompetitionOwner();
		this.startDate = this.getStartDate();
		this.endDate = this.getEndDate();
	}
	
	/*
	 * Alternative method to add a competition. 
	 */

	public Competition(String endDate, String description, int mini, int maxi, String competitionOwner){
		int lastID = DBCommunicator.requestInt("SELECT id FROM competitie ORDER BY id DESC");
		int id = lastID + 1;
		this.description = description;
		this.min = mini;
		this.max = maxi;
		this.owner = competitionOwner;
		DBCommunicator.writeData("INSERT INTO competitie (id, account_naam_eigenaar, start, einde, omschrijving, minimum_aantal_deelnemers, maximum_aantal_deelnemers) VALUES(" + this.id + ", '" + competitionOwner +"',  CURRENT_TIMESTAMP(), '" + endDate + "' , '" + description + "' , " + mini + "," + maxi + ");");
	}
	
	/*
	 * Getter to get the date when the compo ends.
	 */
	public String getEndDate(){
		return DBCommunicator.requestData("SELECT einde FROM competitie WHERE id='" + this.id + "'");
	}
	/*
	 * Getter to get the date the compo began.
	 */
	public String getStartDate(){
		return DBCommunicator.requestData("SELECT einde FROM competitie WHERE id='" + this.id + "'");
	}
	
	/**
	 * Method to add a player (String) to the competition
	 */
	public void addPlayer(String player){
		DBCommunicator.writeData("INSERT INTO deelnemer SET account_naam='" + player + "', competitie_id='"+ this.id + "'");
	}
	
	/**
	 * Method to add a player (String) to the competition
	 */
	public void addPlayer(Account player){
		DBCommunicator.writeData("INSERT INTO deelnemer SET account_naam='" + player.getUsername() + "', competition_id='"+ this.id + "'");
	}

	/**
	 * Method to get the owner of the competition
	 */
	public String getCompetitionOwner() {
		return DBCommunicator.requestData("SELECT account_naam_eigenaar FROM competitie WHERE id='" + this.id + "'");
	}
	
	/*
	 *	Getter for the description
	 */
	public String getCompetitionDescription() {
		return DBCommunicator.requestData("SELECT omschrijving FROM competitie WHERE id='" + this.id + "'");
	}
	
	/*
	 * Getter for the minimum amount of players
	 */
	public int getMinimum(){
		return DBCommunicator.requestInt("SELECT minimum_aantal_deelnemers FROM competitie WHERE id='" + this.id + "'");
	}
	
	/*
	 * Getter for the maximum amount of players
	 */
	private int getMaximum() {
		return DBCommunicator.requestInt("SELECT maximum_aantal_deelnemers FROM competitie WHERE id='" + this.id + "'");
	}

	public int getID() {
		return id;
	}
}
