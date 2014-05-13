package Utility;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static BufferedImage BACKGROUND;
	public static BufferedImage ICON;
	
	public void loadAllImages() {
		try {
			BACKGROUND 			= ImageIO.read(getClass().getResourceAsStream("/images/background.jpg"));
			ICON 				= ImageIO.read(getClass().getResourceAsStream("/images/icon.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
