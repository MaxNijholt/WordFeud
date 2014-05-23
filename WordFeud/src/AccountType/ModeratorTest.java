package AccountType;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Utility.DBCommunicator;

public class ModeratorTest {
	Account moderator;

	@Before
	public void setUp() throws Exception {
		DBCommunicator.getConnection();
		DBCommunicator.writeData("INSERT INTO account (naam, wachtwoord) VALUES('"
				+ "Moderator" + "','" + "Moderator" + "')");
		DBCommunicator.writeData("INSERT INTO accountrol (account_naam, rol_type) VALUES('"
				+ "Moderator" + "','" + "Moderator" + "')");
		moderator = new Account("Moderator");
	}

	@After
	public void tearDown() throws Exception {
		DBCommunicator.writeData("DELETE FROM accountrol WHERE account_naam='"
				+ "Moderator" + "' AND rol_type='" + "Moderator" + "'");
		DBCommunicator.writeData("DELETE FROM account WHERE naam='"
				+ "Administrator" + "'");
	}

	@Test
	public void test() {
		
	}

}
