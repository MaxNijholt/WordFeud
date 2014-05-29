package Utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

/**
 * @author Stan van Heumen
 */
public class SplashText implements Runnable {

	// Instance Variables
	private BufferedImage 		image;
	private Thread 				thread;
	private String[] 			texts;
	private JPanel				panel;
	private int					x, y, width, height;
	private boolean				running;
	private Color[] 			colors;
	
	// Color constants : TEXT
	public static final Color	PRE1_T 		= new Color(255, 255, 0);		// Yellow
	public static final Color	PRE2_T		= new Color(237, 67, 33);		// Red
	public static final Color	PRE3_T		= new Color(255, 255, 255);		// White
	
	// Color constants : SHADOW
	public static final Color	PRE1_S		= new Color(63, 63, 0);			// Dark yellow
	public static final Color	PRE2_S		= new Color(33, 33, 33);		// Grayish
	public static final Color	PRE3_S		= new Color(0, 0, 0);			// Black
	
	/**
	 * Constructor parameters: textColor, shadowColor, xPos, yPos, p<br>
	 * This constructor creates a SplashText that you can show off.<br>
	 * The SplashText has custom prefixes like for example: 'PRE1_T' for text and 'PRE1_S' for shadow.
	 */
	public SplashText(Color textColor, Color shadowColor, int xPos, int yPos, JPanel p) {
		panel		= p;
		x			= xPos;
		y			= yPos;
		thread 		= new Thread(this);
		colors 		= new Color[11];
		texts		= new String[] 
		{	
			"Heumeuuuu","This is awesome", "So cool", "Really nice!", "51% bug free!", "Awesome", "LOL!",
			"It's a game!", "Wordfeud", "ITSMA so cool!", "UMadBrah?", ".party();", "Check it out!",
			"Deja vu!", "Deja vu!", "Finger-licking!", "GOTY!", "Mmmph, mmph!", "Pretty!", "Fancy!",
			"Woah!", "Wow!", "Yaay!", "Water proof!", "Superfragilisticexpialidocious!",
			"This message will be to small for you to see, am i right?", "Try it!", "this.addBeer(new Beer());",
			"Random Splashtexts ftw", "if(this.beer.isEmpty()){newBeer();}", "Colormatic!", "$W@G", "OverPowered",
			"Might contain peanuts", "max.getBeer();", "NullCounter!", "WC", "Software Engineering != cool",
			"a2 + b2 = c2", "Threaderdethread", "Why do Java programmers wear glasses, because they don't C#"
		};
		
		String text	 	= texts[new Random().nextInt(texts.length)];
		Font font 		= new Font("Aharoni", Font.BOLD, 200);
		FontMetrics fm 	= panel.getFontMetrics(font);
		
		image  			= new BufferedImage(fm.stringWidth(text) + 5, fm.stringWidth(text) + 5, BufferedImage.TYPE_INT_ARGB);
		width 			= image.getWidth();
		height 			= image.getHeight();
		
		createImage(text, textColor, shadowColor, font);
		
		running	= true;
		thread.start();
		
	}
	
	/**
	 * Constructor parameters: g<br>
	 * This method allows you to draw the SplashText over your panel, remember to use paint instead of paintComponent,<br>
	 * because paint paints all over the components!
	 */
	public void drawSplash(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.drawImage(image, x - (width / 2), y - (height / 2), width, height, null);
	}
	
	/**
	 * Constructor parameters: text, textColor, shadowColor, font<br>
	 * This private method creates the image that will be displayed as the SplashText.
	 */
	private void createImage(String text, Color textColor, Color shadowColor, Font font) {
		Graphics2D imageGraphics 	= (Graphics2D)image.getGraphics();
		FontMetrics fm	 			= imageGraphics.getFontMetrics(font);
		
		colors[0] 	= Color.blue;
		colors[1] 	= Color.green;
		colors[2] 	= Color.cyan;
		colors[3] 	= Color.red;
		colors[4] 	= Color.pink;
		colors[5] 	= Color.yellow;
		colors[6] 	= Color.gray;
		colors[7] 	= Color.darkGray;
		colors[8] 	= Color.MAGENTA;
		colors[9] 	= Color.blue;
		colors[10] = Color.green;
		
		imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		imageGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		imageGraphics.setFont(font);
		imageGraphics.rotate(Math.toRadians(-20), image.getWidth() / 2, image.getHeight() / 2);
		imageGraphics.setColor(shadowColor);
		imageGraphics.drawString(text, 5, ((image.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent() + 5);
		if(text.equals("Colormatic!")){
			String s 	= "Colormatic!";
			int counter	= 0;
			for(int i = 0; i < s.length(); i++) {
				imageGraphics.setColor(colors[i]);
				String letter = s.substring(i, i + 1);
				imageGraphics.drawString(letter, counter, ((image.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent());
				counter += fm.stringWidth(letter);
			}
		}else {
			imageGraphics.setColor(textColor);
			imageGraphics.drawString(text, 0, ((image.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent());
		}
	}
	
	/**
	 * This method is a required method when you implement Runnable, this method starts whenever the thread<br>
	 * gets started, it makes the image bigger and smaller at a framerate close to 60.
	 */
	public void run() {
		final int startSize = 240;
		final int minSize 	= 210;
		final int maxSize 	= 270;
		int size			= startSize;
		int increaser		= 2;
		while(running) {
			if(size >= maxSize) {increaser = -2;}
			if(size <= minSize) {increaser = 2;}
			size += increaser;
			width = size;
			height = size;
			panel.repaint();
			try {Thread.sleep(1000/60);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	// Setters
	public void setRunning(boolean run) {running = run;}

}
