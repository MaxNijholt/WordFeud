package WordFeud;

import java.util.HashMap;

public class Field {

	private HashMap<String, GameStone> newWords;
	private HashMap<String, Tile> field;

	public Field() {
		newWords = new HashMap<String, GameStone>();
		field = new HashMap<String, Tile>();

		for (int i = 1; i < 16; i++) {
			for (int j = 1; j < 16; j++) {
				field.put(i + "," + j, new Tile(i, j));
			}
		}
	}

	public void layGameStone(GameStone gamestone, String location) {
		for (int i = 1; i < 16; i++) {
			for (int j = 1; j < 16; j++) {
				if (location.equals(i + "," + j)) {
					if (field.get(location).getGameStone() == null) {
						field.get(location).setGameStone(gamestone);
						newWords.put(location, gamestone);
						break;
					}
				}
			}
		}

	}

	public void removeGameStone(String location) {
		for (int i = 1; i < 16; i++) {
			for (int j = 1; j < 16; j++) {
				if (field.get(location).getGameStone() != null) {
					field.get(location).setGameStone(null);
					newWords.remove(location);

					break;
				}
			}
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
