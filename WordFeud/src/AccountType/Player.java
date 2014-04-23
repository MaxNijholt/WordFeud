package AccountType;

import Utility.DBCommunicator;

public class Player extends Account {
	private String username;

	public Player() {

	}

	public void createCompetition() {

	}

	public void createGame() {

	}

	public void calculateStatistics() {

		DBCommunicator
				.requestData("SELECT spel_id, account_naam, sum(score) as totaal_score FROM mnijholt_db2.beurt as bleft join mnijholt_db2.spel as s on b.spel_id = s.idwhere s.toestand_type = 'Finished' and s.account_naam_tegenstander = "
						+ username
						+ " or s.toestand_type = 'Finished' and s.account_naam_uitdager = "
						+ username + "group by account_naam");
		

	}
}
