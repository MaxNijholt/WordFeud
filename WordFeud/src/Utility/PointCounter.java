package Utility;

import java.util.HashMap;

import WordFeud.Field;
import WordFeud.GameStone;

public class PointCounter {
	
	private Field field;

	public PointCounter(Field field){
		this.field = field;
		
	}
	
	public boolean jokerCheck(){
		return false;
	
	}
	
	public int counterPointsTurn(HashMap<String, GameStone> createdWords){
		int points = 0;
		int wordBonus = 0;
		String[] locations = (String[]) createdWords.keySet().toArray();
		
		for(int i = 0; i < locations.length; i++){
			if(!checkBonus(locations[i]).equals("")){
				if(checkBonus(locations[i]).equals("dw")){
					wordBonus += 2;
				}else if(checkBonus(locations[i]).equals("tw")){
					wordBonus += 3;
				}else if(checkBonus(locations[i]).equals("dl")){
					points += createdWords.get(locations[i]).getValue() * 2;
				}else if(checkBonus(locations[i]).equals("tl")){
					points += createdWords.get(locations[i]).getValue() * 3;
				}else{
					System.out.println("ERROR!");
				}
			}else{
				points += createdWords.get(locations[i]).getValue();
			}
		}
		
		
		return points;
		
	}
	
	private String checkBonus(String location){
		if(!field.getTiles().get(location).getBonus().equals("") ||
			!field.getTiles().get(location).getBonusUsed()){
				return field.getTiles().get(location).getBonus();
		}else{
			return "";
		}
	}
}
