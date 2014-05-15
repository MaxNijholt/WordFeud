package Utility;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Loader {

	public static BufferedImage 			BACKGROUND;
	public static BufferedImage 			BACKGROUNDHD;
	public static BufferedImage 			ICON;
	
	public static HashMap<String, String>	TILEVALUES;

	public void loadAllImages() {
		try {
			BACKGROUND 			= ImageIO.read(getClass().getResourceAsStream("/images/background.jpg"));
			BACKGROUNDHD		= ImageIO.read(getClass().getResourceAsStream("/images/background_hd.png"));
			ICON 				= ImageIO.read(getClass().getResourceAsStream("/images/icon.png"));
			
			System.out.println("All images have been loaded succesfully");
			
			TILEVALUES			= new HashMap<String, String>();
			for(char i = 'A'; i < 'Z'; i++) {
				TILEVALUES.put(DBCommunicator.requestData("SELECT karakter FROM lettertype WHERE letterset_code = 'EN' AND karakter = '" + i + "'"), DBCommunicator.requestData("SELECT waarde FROM lettertype WHERE letterset_code = 'EN' AND karakter = '" + i + "'"));
			}
			
			System.out.println("All Characters have been loaded succesfully");
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
