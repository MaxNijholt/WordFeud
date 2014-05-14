package WordFeud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import Utility.SLabel;

@SuppressWarnings("serial")
public class Tile extends SLabel {

	private String bonus;
	private boolean bonusUsed;
	private int xPos;
	private int yPos;
	private GameStone gameStone;
	
	public Tile(int x, int y){
		super("", SLabel.CENTER, 30, 30);
		this.xPos = x;
		this.yPos = y;
		bonus = "";
		bonusUsed = false;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.setColor(new Color(90, 90, 90));
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
	}
	
	// Getters
	public String getBonus(){return bonus;}
	public GameStone getGameStone(){return gameStone;}
	public boolean getBonusUsed(){return bonusUsed;}
	public int getXPos(){return xPos;}
	public int getYPos(){return yPos;}
	
	// Setters
	public void setBonusUsed(boolean used){this.bonusUsed = used;}
	public void setGameStone(GameStone gamestone){
		this.gameStone = gamestone;
		repaint();
	}
	public void setCoordinates(int posX, int posY){this.xPos = posX; this.yPos = posY;}
	public void setBonus(String bonus){this.bonus = bonus;}
}
