package WordFeud;

import java.util.ArrayList;

import Utility.DBCommunicator;
import Utility.Loader;

public class Spectator {
	private Field myField;
	private int myGameID, lastTurn, turn;
	private String letters, turnText;
	private ArrayList<GameStone> handStones;

	public Spectator(int gameID) {
		myGameID = gameID;
		myField = new Field(myGameID);

	}

	public int getLastTurn() {
		lastTurn = DBCommunicator
				.requestInt("SELECT id from beurt WHERE spel_id = " + myGameID
						+ " AND aktie_type != 'End' ORDER BY id DESC");
		return lastTurn;
	}

	public String getTurnLetters(int turn) {
		if (turn == DBCommunicator
				.requestInt("SELECT id from beurt WHERE spel_id = " + myGameID
						+ " AND id = " + turn + " ORDER BY id DESC")) {
			letters = DBCommunicator
					.requestData("SELECT inhoud FROM plankje WHERE spel_id = "
							+ myGameID + " AND beurt_id = " + turn);
			return letters;
		} else {
			System.out.println("invalid turn");
			return null;
		}

	}

	public Field getMyField() {
		myField.updateField(myGameID);
		return myField;
	}

	public int getCompID(int gameID) {
		int compID = DBCommunicator
				.requestInt("SELECT competitie_id FROM spel where id = '"
						+ gameID + "'");
		return compID;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public ArrayList<GameStone> getHand(int turn) {
		handStones = Loader.getGameStones("EN");
		handStones = DBCommunicator.getHandLetters(myGameID, turn, handStones);
		return handStones;
	}

	public String turnText(int gameID, int turn) {

		String playerName = DBCommunicator
				.requestData("SELECT account_naam FROM beurt where spel_id = '"
						+ gameID + "' and id = '" + turn + "'");

		if (DBCommunicator.requestData(
				"SELECT aktie_type FROM beurt where spel_id = '" + gameID
						+ "' and id = '" + turn + "'").equals("Swap")) {

			turnText = playerName + " has swapped";
		} else if (DBCommunicator.requestData(
				"SELECT aktie_type FROM beurt where spel_id = '" + gameID
						+ "' and id = '" + turn + "'").equals("Pass")) {

			turnText = playerName + " has passed";
		} else if (turn == 2) {
			turnText = "First turn";
		} else {
			String playedWord = DBCommunicator
					.requestData("SELECT woorddeel FROM gelegd where spel_id = '"
							+ gameID + "' and beurt_id = '" + turn + "'");
			int points = DBCommunicator
					.requestInt("SELECT score FROM beurt where spel_id = '"
							+ gameID + "' and id = '" + turn + "'");

			playedWord = playedWord.replaceAll(",", "");

			turnText = playerName + " played: " + playedWord + " for: "
					+ points + " points";
		}
		System.out.println(turnText);
		return turnText;
	}

	public String[] players(int gameID, int turn) {
		String[] players = new String[2];
		players[0] = DBCommunicator
				.requestData("SELECT account_naam FROM beurt where spel_id = '"
						+ gameID + "' and id = '" + turn + "'");
		players[1] = DBCommunicator
				.requestData("SELECT account_naam FROM beurt where spel_id = '"
						+ gameID + "' and id = '" + (turn - 1) + "'");
		return players;
	}
}
