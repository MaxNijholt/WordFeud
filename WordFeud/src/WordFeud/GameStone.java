package WordFeud;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import Utility.Loader;

public class GameStone {

	// Instance variables
	private int 	value, id;
	private int 	width, height;
	private char 	letter;
	private boolean	hand;
	private String 	letterSet;
	private Font 	large, small;
	
	/**
	 * Constructor parameters: value, letter<br>
	 * This constructor creates a Object that hold information about a GameStone<br>
	 * You can get its GUI aspect by calling the .getImage() method
	 */
	public GameStone(int value, char letter){
		init(value, letter);
	}	
	
	/**
	 * Constructor parameters: value, letter, set<br>
	 * This constructor creates a Object that hold information about a GameStone<br>
	 * You can get its GUI aspect by calling the .getImage() method<br>
	 * This constructor gives the letterSet a value
	 */
	public GameStone(int value, char letter, String set){
		init(value, letter);
		letterSet = set;
	}
	
	/**
	 * This is a private initialize method for the constructor
	 */
	private void init(int v, char l) {
		id		= -1;
		value 	= v;
		letter 	= l;
		width	= 32;
		height	= 32;
		hand	= false;
		large 	= new Font("Arial", Font.BOLD, 20);
		small	= new Font("Arial", Font.PLAIN, 8);
	}
	
	// Getters
	public void setDimension(int w, int h) 				{width = w; height = h;}
	public void setFonts(Font l, Font s)				{large = l; small = s;}
	public void setID(int i) 							{id = i;}
	public boolean getHand()							{return hand;}
	
	// Setters
	public int getID() 									{return id;}
	public int getValue()								{return value;}
	public char getLetter()								{return letter;}
	public Font getSmallFont() 							{return small;}
	public Font getLargeFont() 							{return large;}
	public String getLetterSet() 						{return letterSet;}
	public void setHand(boolean h)						{hand = h;}
	
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)image.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.setColor(new Color(255, 255, 255, 180));
		g2d.drawImage(Loader.GAMESTONE, 0, 0, width, height, null);
		g2d.setColor(Color.BLACK);
		FontMetrics fmLarge = g2d.getFontMetrics(large);
		FontMetrics fmSmall = g2d.getFontMetrics(small);
		g2d.setFont(large);
		g2d.drawString(String.valueOf(letter), (width / 2) - (fmLarge.stringWidth(String.valueOf(letter)) / 2), (0 + (height+1-0) / 2) - ((fmLarge.getAscent() + fmLarge.getDescent()) / 2) + fmLarge.getAscent());
		String temp = String.valueOf(value);
		g2d.setColor(Color.BLACK);
		g2d.setFont(small);
		g2d.drawString(temp, width - fmSmall.stringWidth(String.valueOf(value)) - (fmSmall.stringWidth(String.valueOf(value)) / 2), (fmSmall.getHeight()) - (fmSmall.getHeight() / 4));
		return image;
	}
	
}
