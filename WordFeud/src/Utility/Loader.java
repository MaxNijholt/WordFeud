package Utility;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import WordFeud.GameStone;
import WordFeud.Tile;

public class Loader {

	public static BufferedImage 			BACKGROUND;
	public static BufferedImage 			BACKGROUNDHD;
	public static BufferedImage 			ICON;
	public static BufferedImage				NORMAL_TILE;
	public static BufferedImage				STAR_TILE;
	public static BufferedImage				TL_TILE;
	public static BufferedImage				TW_TILE;
	public static BufferedImage				DL_TILE;
	public static BufferedImage				DW_TILE;
	public static BufferedImage 			GAMESTONE;
	
	
	public static HashMap<String, String>	TILEVALUES;

	public void loadAllImages() {
		try {
			BACKGROUND 			= ImageIO.read(getClass().getResourceAsStream("/images/background.jpg"));
			BACKGROUNDHD		= ImageIO.read(getClass().getResourceAsStream("/images/background_hd.png"));
			ICON 				= ImageIO.read(getClass().getResourceAsStream("/images/icon.png"));
			
			NORMAL_TILE 		= ImageIO.read(getClass().getResourceAsStream("/images/normal_tile.png"));
			STAR_TILE			= ImageIO.read(getClass().getResourceAsStream("/images/star_tile.png"));
			TL_TILE 			= ImageIO.read(getClass().getResourceAsStream("/images/tl_tile.png"));
			TW_TILE 			= ImageIO.read(getClass().getResourceAsStream("/images/tw_tile.png"));
			DL_TILE				= ImageIO.read(getClass().getResourceAsStream("/images/dl_tile.png"));
			DW_TILE 			= ImageIO.read(getClass().getResourceAsStream("/images/dw_tile.png"));
			GAMESTONE 			= ImageIO.read(getClass().getResourceAsStream("/images/tile.png"));
			
			System.out.println("All images have been loaded succesfully");
			
			TILEVALUES			= new HashMap<String, String>();
			for(char i = 'A'; i < 'Z'; i++) {
				TILEVALUES.put(
						DBCommunicator.requestData("SELECT karakter FROM lettertype WHERE letterset_code = 'EN' AND karakter = '" + i + "'"), 
						DBCommunicator.requestData("SELECT waarde FROM lettertype WHERE letterset_code = 'EN' AND karakter = '" + i + "'"));
			}
			
			System.out.println("All Characters have been loaded succesfully");
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static ArrayList<GameStone> getGameStones(String language){
		ArrayList<GameStone> gamestones = new ArrayList<GameStone>();
		HashMap<Character, HashMap<Integer, Integer>> letterset = DBCommunicator.requestLetters("EN");
		for(char ch = 'A'; ch < 'Z'; ch++ ){
		 	for(int i = 1; i < Integer.parseInt(letterset.get(ch).values().toString().substring(letterset.get(ch).values().toString().indexOf('[')+1,letterset.get(ch).values().toString().indexOf(']')))+1; i++){
		 		int value = Integer.parseInt(letterset.get(ch).keySet().toString().substring(letterset.get(ch).keySet().toString().indexOf('[')+1,letterset.get(ch).keySet().toString().indexOf(']')));
		 		new GameStone(value ,ch);
		 		System.out.println(value + " " + ch);
		 	}
		}
		return gamestones;
	}
	
	public static HashMap<String, Tile> getTiles(){
		HashMap<String, String> tiles = DBCommunicator.requestTilesMap("Standard");
		HashMap<String, Tile> tilemap = new HashMap<String, Tile>();
		ArrayList<String> loc = new ArrayList<String>();
		loc.addAll(tiles.keySet());
		for(String s : loc){
			String x = "" + s.substring(0, s.indexOf(","));
			String y = "" + s.substring(s.indexOf(",")+1);
			Tile ile = new Tile(Integer.parseInt(x), Integer.parseInt(y) ,tiles.get(s));
			tilemap.put(s, ile);
		}
		return tilemap;
	}

}
