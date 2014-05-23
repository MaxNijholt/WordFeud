package WordFeud;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import Utility.Loader;
import Utility.SLabel;

@SuppressWarnings("serial")
public class Tile extends SLabel {

	private String bonus;
	private boolean bonusUsed;
	private int xPos,
				yPos,
				turn;
	private GameStone gameStone;
	private boolean pickablity;

	public Tile(int x, int y) {
		super("", SLabel.CENTER, 32, 32);
		this.xPos = x;
		this.yPos = y;
		bonus = "";
		bonusUsed = false;
		pickablity = true;
	}

	public Tile(int x, int y, String bonus) {
		super("", SLabel.CENTER, 32, 32);
		this.xPos = x;
		this.yPos = y;
		this.bonus = bonus;
		bonusUsed = false;
		pickablity = true;
	}

	public Tile(int x, int y, GameStone stone){
		super("", SLabel.CENTER, 32, 32);
		this.xPos = x;
		this.yPos = y;
		bonus = "";
		bonusUsed = false;
		this.gameStone = stone;
		pickablity = true;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics image_g = image.getGraphics();
		switch(bonus) {
			case "":
				image_g.drawImage(Loader.NORMAL_TILE, 0, 0, 32, 32, null);
				break;
			case "*":
				image_g.drawImage(Loader.STAR_TILE, 0, 0, 32, 32, null);
				break;
			case "DL":
				image_g.drawImage(Loader.DL_TILE, 0, 0, 32, 32, null);
				break;
			case "DW":
				image_g.drawImage(Loader.DW_TILE, 0, 0, 32, 32, null);
				break;
			case "TL":
				image_g.drawImage(Loader.TL_TILE, 0, 0, 32, 32, null);
				break;
			case "TW":
				image_g.drawImage(Loader.TW_TILE, 0, 0, 32, 32, null);
				break;
		}
		
		g2d.drawImage(image, 0, 0, 32, 32, null);
		
		if(gameStone != null) {
			g2d.drawImage(gameStone.getImage(), 0, 0, getWidth(), getHeight(), null);
		}
	}

	// Getters	
	public String getBonus() {
		return bonus;
	}

	public GameStone getGameStone() {
		return gameStone;
	}

	public boolean getBonusUsed() {
		return bonusUsed;
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

	// Setters
	public void setBonusUsed(boolean used) {
		this.bonusUsed = used;
	}

	public void setGameStone(GameStone gamestone) {
		this.gameStone = gamestone;
		System.out.println(gamestone.getLetter() + gamestone.getValue());
		repaint();
	}
	
	public void setPickablity(boolean pick) {
		pickablity = pick;
	}
	
	public boolean getPickablity() {return pickablity;}

	public void setCoordinates(int posX, int posY) {
		this.xPos = posX;
		this.yPos = posY;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public void setBeurt(int turnid) {
		this.turn =  turnid;
	}

	public int getTurn() {
		return turn;
	}
}
