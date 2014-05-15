package Utility;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Loader {
	
	public static BufferedImage BACKGROUND;
	public static BufferedImage BACKGROUNDHD;
	public static BufferedImage ICON;

	public void loadAllImages() {
		try {
			BACKGROUND 			= ImageIO.read(getClass().getResourceAsStream("/images/background.jpg"));
			BACKGROUNDHD		= ImageIO.read(getClass().getResourceAsStream("/images/background_hd.png"));
			ICON 				= ImageIO.read(getClass().getResourceAsStream("/images/icon.png"));
			
			System.out.println("All images have been loaded succesfully");
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
