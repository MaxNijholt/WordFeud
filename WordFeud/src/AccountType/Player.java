package AccountType;

import Utility.DBCommunicator;

public class Player{
	Account account;
	
	public Player(Account account) {
		this.account = account;
	}

	public void calculateStatistics() {
		System.out
				.println(DBCommunicator
						.requestData("SELECT spel_id, account_naam, sum(score) as totaal_score FROM mnijholt_db2.beurt as b left join mnijholt_db2.spel as s on b.spel_id = s.id where s.toestand_type = 'Finished' and s.account_naam_tegenstander = "
								+ "'"
								+ account.getUsername()
								+ "'"
								+ " or s.toestand_type = 'Finished' and s.account_naam_uitdager = "
								+ "'"
								+ account.getUsername()
								+ "'"
								+ "group by account_naam"));

	}
}