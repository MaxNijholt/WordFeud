package AccountType;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Utility.DBCommunicator;

public class AdministratorTest {
	Account adam;

	@Before
	public void setUp() throws Exception {
		DBCommunicator.getConnection();
		DBCommunicator.writeData("INSERT INTO account (naam, wachtwoord) VALUES('"
				+ "Administrator" + "','" + "Administrator" + "')");
		DBCommunicator.writeData("INSERT INTO accountrol (account_naam, rol_type) VALUES('"
				+ "Administrator" + "','" + "Administrator" + "')");
		adam = new Account("Administrator");
	}

	@After
	public void tearDown() throws Exception {
		DBCommunicator.writeData("DELETE FROM accountrol WHERE account_naam='"
				+ "Administrator" + "' AND rol_type='" + "Administrator" + "'");
		DBCommunicator.writeData("DELETE FROM accountrol WHERE account_naam='"
				+ "Administrator" + "' AND rol_type='" + "Moderator" + "'");
		DBCommunicator.writeData("DELETE FROM account WHERE naam='"
				+ "Administrator" + "'");
	}

	@Test
	public void test() {
		ArrayList<String> rights =  new ArrayList<String>();
		rights.add("Administrator");
		assertEquals(rights,adam.getAdmin().getUserRights("Administrator"));
		adam.getAdmin().addPrivilege("Administrator", "Moderator");
		adam.update();
		rights.add("Moderator");
		assertEquals(rights,adam.getAdmin().getUserRights("Administrator"));
		adam.getAdmin().removePrivilege("Administrator", "Moderator");
		adam.update();
		assertNotEquals(rights,adam.getAdmin().getUserRights("Administrator"));
		rights.remove("Moderator");
		assertEquals(rights,adam.getAdmin().getUserRights("Administrator"));
	}

}
