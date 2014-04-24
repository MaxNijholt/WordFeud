package WordFeud;

import AccountType.Player;
import Utility.DBCommunicator;

public class Competition {
	private int id;
	private String description;

	public Competition(int id, String description){
		this.id = id;
		this.description = description;
	}
	
	public String getName(){
		return "";
	}
	
	public void addPlayer(Player player){
		DBCommunicator.writeData("UPDATE deelnemer SET account_naam='" + player.getUsername() + "', competition_id='"+ this.id + "'");
	}

	public String getCompetitionOwner() {
		return DBCommunicator.requestData("SELECT ");
	}
	
	public String getCompetitionDescription() {
		return DBCommunicator.requestData("SELECT ");
	}
}
