package WordFeud;

import java.util.HashMap;

import Utility.DBCommunicator;
import Utility.Loader;

public class Field {

	private HashMap<String, GameStone> newWords;
	private HashMap<String, Tile> field;
	private String tileValue;

	public Field(int spelID) {
		newWords = new HashMap<String, GameStone>();
		field = Loader.getTiles();
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
