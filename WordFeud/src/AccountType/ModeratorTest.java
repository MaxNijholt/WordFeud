package AccountType;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Utility.DBCommunicator;

public class ModeratorTest {
	Account moderator;

	@Before
	public void setUp() throws Exception {
		DBCommunicator.getConnection();
		DBCommunicator.writeData("INSERT INTO account (naam, wachtwoord) VALUES('" + "Moderator" + "','" + "Moderator" + "')");
		DBCommunicator.writeData("INSERT INTO accountrol (account_naam, rol_type) VALUES('" + "Moderator" + "','" + "Moderator" + "')");
		moderator = new Account("Moderator");
		DBCommunicator.writeData("INSERT INTO woordenboek (woord, letterset_code, status) VALUES('testwoord', 'EN', 'Pending')");
	}

	@After
	public void tearDown() throws Exception {
		DBCommunicator.writeData("DELETE FROM accountrol WHERE account_naam='" + "Moderator" + "' AND rol_type='" + "Moderator" + "'");
		DBCommunicator.writeData("DELETE FROM account WHERE naam='" + "Moderator" + "'");
		DBCommunicator.writeData("DELETE FROM woordenboek WHERE woord='testwoordtwee'");
		DBCommunicator.writeData("DELETE FROM woordenboek WHERE woord='testwoord'");
	}

	@Test
	public void test() {
		ArrayList<String> notaproved = new ArrayList<String>();
		notaproved.add("testwoord");
		assertEquals(notaproved,moderator.getMod().getNotAprovedWords());
		moderator.getMod().aproveWord(moderator.getMod().getNotAprovedWords().get(0));
		assertNotEquals(notaproved,moderator.getMod().getNotAprovedWords());
		notaproved.remove("testwoord");
		moderator.getMod().addWord("testwoordtwee");
		assertEquals(DBCommunicator.requestData("SELECT status FROM woordenboek WHERE woord='testwoordtwee'"), "Accepted");
	}

}
