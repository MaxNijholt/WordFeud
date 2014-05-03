package AccountType;

import Utility.DBCommunicator;

public class Player extends Account {
	private String username;

	private double winLoss;
	private int highScore;

	public Player(String username) {
		super(username);
		this.username = username;

	}

	public void calculateStatistics() {

		System.out
				.println(DBCommunicator
						.requestData("SELECT spel_id, account_naam, sum(score) as totaal_score FROM mnijholt_db2.beurt as b left join mnijholt_db2.spel as s on b.spel_id = s.id where s.toestand_type = 'Finished' and s.account_naam_tegenstander = "
								+ "'"
								+ username
								+ "'"
								+ " or s.toestand_type = 'Finished' and s.account_naam_uitdager = "
								+ "'"
								+ username
								+ "'"
								+ "group by account_naam"));

	}
}
