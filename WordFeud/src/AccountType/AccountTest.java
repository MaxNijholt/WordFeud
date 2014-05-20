package AccountType;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Utility.DBCommunicator;

public class AccountTest {
	private Account moderator, administrator, player;

	@Before
	public void setUp() throws Exception {
		DBCommunicator.writeData("INSERT INTO account (naam, wachtwoord) VALUES('"
				+ "Moderator" + "','" + "Moderator" + "')");
		DBCommunicator.writeData("INSERT INTO accountrol (account_naam, rol_type) VALUES('"
				+ "Moderator" + "','" + "Moderator" + "')");
		moderator = new Account("Moderator");

		DBCommunicator.writeData("INSERT INTO account (naam, wachtwoord) VALUES('"
				+ "Administrator" + "','" + "Administrator" + "')");
		DBCommunicator.writeData("INSERT INTO accountrol (account_naam, rol_type) VALUES('"
				+ "Administrator" + "','" + "Administrator" + "')");
		administrator = new Account("Administrator");
		
		DBCommunicator.writeData("INSERT INTO account (naam, wachtwoord) VALUES('"
				+ "Player" + "','" + "Player" + "')");
		DBCommunicator.writeData("INSERT INTO accountrol (account_naam, rol_type) VALUES('"
				+ "Player" + "','" + "Player" + "')");
		player = new Account("Player");
	}

	@After
	public void tearDown() throws Exception {
		DBCommunicator.writeData("DELETE FROM accountrol WHERE account_naam='"
				+ "Administrator" + "' AND rol_type='" + "Administrator" + "'");
		DBCommunicator.writeData("DELETE FROM accountrol WHERE account_naam='"
				+ "Moderator" + "' AND rol_type='" + "Moderator" + "'");
		DBCommunicator.writeData("DELETE FROM accountrol WHERE account_naam='"
				+ "Player" + "' AND rol_type='" + "Player" + "'");
		DBCommunicator.writeData("DELETE FROM account WHERE naam='"
				+ "Player" + "'");
		DBCommunicator.writeData("DELETE FROM account WHERE naam='"
				+ "Moderator" + "'");
		DBCommunicator.writeData("DELETE FROM account WHERE naam='"
				+ "Administrator" + "'");
	}

	@Test
	public void test() {
		assertEquals(administrator.getUsername(), "Administrator");
		assertEquals(moderator.getUsername(), "Moderator");
		assertEquals(player.getUsername(), "Player");
		assertEquals(false, moderator.isAdministrator());
		assertEquals(false, player.isAdministrator());
		assertEquals(false, administrator.isModerator());
		assertEquals(true, moderator.isModerator());
		assertEquals(false, player.isModerator());
		assertEquals(false, administrator.isPlayer());
		assertEquals(false, moderator.isPlayer());
		assertEquals(true, player.isPlayer());
		assertEquals(true, administrator.isAdministrator());
		
	}

}
