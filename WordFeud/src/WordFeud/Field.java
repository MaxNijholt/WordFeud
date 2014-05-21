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

//		for (int i = 1; i < 16; i++) {
//			for (int j = 1; j < 16; j++) {
//				tileValue = DBCommunicator
//						.requestData("SELECT lettertype_karakter FROM gelegdeletter left join letter on gelegdeletter.letter_id = letter.id and gelegdeletter.spel_id = letter.spel_id where gelegdeletter.spel_id = "
//								+ spelID
//								+ " and tegel_x = "
//								+ i
//								+ " and tegel_y = " + j);
//				if (tileValue != null) {
//				
//					if (tileValue.equals("?")) {
//						tileValue = DBCommunicator
//								.requestData("SELECT blancoletterkarakter FROM gelegdeletter left join letter on gelegdeletter.letter_id = letter.id and gelegdeletter.spel_id = letter.spel_id where gelegdeletter.spel_id = "
//										+ spelID
//										+ " and tegel_x = "
//										+ i
//										+ " and tegel_y = " + j);
//
//						field.put(i + "," + j, new Tile(i, j, tileValue));
//					
//					}
//					else{
//						field.put(i + "," + j, new Tile(i, j, tileValue));
//					}
//				}
//				else {
//
//					field.put(i + "," + j, new Tile(i, j));
//				} 
//			}
//		}
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
