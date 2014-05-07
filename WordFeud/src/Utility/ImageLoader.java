package Utility;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static BufferedImage BACKGROUND;
	
	public void loadAllImages() {
		try {
			BACKGROUND = ImageIO.read(getClass().getResourceAsStream("/images/background.jpg"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
