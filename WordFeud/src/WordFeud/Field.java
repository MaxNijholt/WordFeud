package WordFeud;

import java.util.HashMap;

public class Field {

	private HashMap<String, GameStone> newWords;
	private HashMap<String, Tile> field;
<<<<<<< .merge_file_a06472
	
	public Field(){
		newWords = new HashMap<String, GameStone>();
		field = new HashMap<String, Tile>();
		
		//initialiseer field
		for(int i = 1; i < 16; i++){
			for(int j = 1; j < 16; j++){
=======

	public Field() {
		newWords = new HashMap<String, GameStone>();
		field = new HashMap<String, Tile>();

		for (int i = 1; i < 16; i++) {
			for (int j = 1; j < 16; j++) {
>>>>>>> .merge_file_a06888
				field.put(i + "," + j, new Tile(i, j));
			}
		}
	}
<<<<<<< .merge_file_a06472
	
	public void layGameStone(GameStone gamestone, String location){
		for(int i = 1; i < 16; i++){
			for(int j = 1; j < 16; j++){
				if(location.equals(i + "," + j)){
					//controle of de tile leeg is
					if(field.get(location).getGameStone() == null ){
						//leg gamestone op de tile
						field.get(location).setGameStone(gamestone);
						//Zet gamestone in lijst met nieuw gelegde gamestones
						newWords.put(location, gamestone);
						break;
					}else{
						//return GameStone naar de hand van de speler
						
					}
				}
			}
		}
			
	}
	
	public void removeGameStone(String location){
		for(int i = 1; i < 16; i++){
			for(int j = 1; j < 16; j++){
				if(field.get(location).getGameStone() != null ){
					//leg gamestone op de tile
					field.get(location).setGameStone(null);
					//Zet gamestone in lijst met nieuw gelegde gamestones
					newWords.remove(location);
					//return GameStone naar de hand van de speler
					
					break;
				}else{
					//Doe niks of geef melding
					
				}
			}
		}
	}
	
	public HashMap<String, GameStone> getNewWords(){
		
		return newWords;
		
	}
	
	public HashMap<String, Tile> getTiles(){
		
		return field;
		
	}
	
	
=======

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

>>>>>>> .merge_file_a06888
}
