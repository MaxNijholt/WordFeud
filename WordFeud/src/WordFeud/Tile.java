package WordFeud;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import Utility.Loader;

public class Tile {
	
	// Instance variables
	private int 		xPos, 
						yPos, 
						width, 
						height, 
						turn;
	private boolean 	bonusUsed, 
						pickablity;
	private String 		bonus;
	private GameStone 	gameStone;

	/**
	 * Constructor parameters: x, y<br>
	 * This constructor creates a Object that hold information about a Tile<br>
	 * You can get its GUI aspect by calling the .getImage() method
	 */
	public Tile(int x, int y) {
		init(x, y);
	}

	/**
	 * Constructor parameters: x, y, bonus<br>
	 * This constructor creates a Object that hold information about a Tile<br>
	 * You can get its GUI aspect by calling the .getImage() method<br>
	 * This constructor gives the tile a bonus
	 */
	public Tile(int x, int y, String b) {
		init(x, y);
		bonus = b;
	}

	/**
	 * Constructor parameters: x, y, gameStone<br>
	 * This constructor creates a Object that hold information about a Tile<br>
	 * You can get its GUI aspect by calling the .getImage() method<br>
	 * This constructor gives the tile a GameStone
	 */
	public Tile(int x, int y, GameStone stone){
		init(x, y);
		gameStone = stone;
		if(stone!=null)
		this.setBonusUsed(true);
	}
	
	/**
	 * This is a private initialize method for the constructor
	 */
	private void init(int x, int y) {
		xPos 		= x;
		yPos 		= y;
		width		= 32;
		height		= 32;
		bonus 		= "";
		bonusUsed 	= false;
		pickablity 	= true;
		gameStone	= null;
	}

	// Getters	
	public int getXPos() 			{return xPos;}
	public int getYPos() 			{return yPos;}
	public boolean isBonusUsed() 	{return bonusUsed;}
	public boolean getPickablity() 	{return pickablity;}
	public String getBonus() 		{return bonus;}
	public GameStone getGameStone() {return gameStone;}
	public int getTurn()			{return turn;}
	
	// Setters
	public void setBonusUsed(boolean used) 			{bonusUsed = used;}
	public void setGameStone(GameStone stone) 		{gameStone = stone;}
	public void setPickablity(boolean pick) 		{pickablity = pick;}
	public void setCoordinates(int posX, int posY) 	{xPos = posX; yPos = posY;}
	public void setBonus(String b) 					{bonus = b;}
	public void setTurn(int i) 						{turn = i;}

	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)image.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		if(gameStone == null) {
			switch(bonus) {
			case "":
				g2d.drawImage(Loader.NORMAL_TILE, 0, 0, width, height, null);
				break;
			case "*":
				g2d.drawImage(Loader.STAR_TILE, 0, 0, width, height, null);
				break;
			case "DL":
				g2d.drawImage(Loader.DL_TILE, 0, 0, width, height, null);
				break;
			case "DW":
				g2d.drawImage(Loader.DW_TILE, 0, 0, width, height, null);
				break;
			case "TL":
				g2d.drawImage(Loader.TL_TILE, 0, 0, width, height, null);
				break;
			case "TW":
				g2d.drawImage(Loader.TW_TILE, 0, 0, width, height, null);
				break;
			}
		}
		else {
			g2d.drawImage(gameStone.getImage(), 0, 0, null);
		}
		return image;
	}
	
}
