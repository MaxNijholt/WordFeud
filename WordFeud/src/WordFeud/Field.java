package WordFeud;

import java.util.HashMap;

import Utility.DBCommunicator;
import Utility.Loader;

public class Field {

	private HashMap<String, GameStone> newWords;
	private HashMap<String, Tile> field;

	public Field(int gameID) {
		newWords = new HashMap<String, GameStone>();
		String language = DBCommunicator.requestData("SELECT letterset_naam FROM mnijholt_db2.spel WHERE id='" + gameID +"';");
		field = Loader.updateTiles(gameID,Loader.getGameStones(language),Loader.getTiles());
	}

	public void layGameStone(GameStone gamestone, String location) {
		if (field.get(location).getGameStone() == null) {
			field.get(location).setGameStone(gamestone);
			newWords.put(location, gamestone);
		}

	}

	public void removeGameStone(String location) {
		if (field.get(location).getGameStone() != null) {
			field.get(location).setGameStone(null);
			newWords.remove(location);

		}
	}

	public HashMap<String, GameStone> getNewWords() {

		return newWords;

	}

	public HashMap<String, Tile> getTiles() {

		return field;

	}

	public void clearNewWords() {
		newWords.clear();
	}
}
