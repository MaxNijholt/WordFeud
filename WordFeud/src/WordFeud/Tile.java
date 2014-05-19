package WordFeud;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Utility.SLabel;

@SuppressWarnings("serial")
public class Tile extends SLabel implements MouseListener, MouseMotionListener {

	private String bonus;
	private boolean bonusUsed;
	private int xPos;
	private int yPos;
	private GameStone gameStone;
	private Point initLoc, initLocOnScreen;

	public Tile(int x, int y) {
		super("", SLabel.CENTER, 32, 32);
		this.xPos = x;
		this.yPos = y;
		bonus = "";
		bonusUsed = false;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public Tile(int x, int y, String value) {
		super("", SLabel.CENTER, 32, 32);
		this.xPos = x;
		this.yPos = y;
		bonus = "";
		bonusUsed = false;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setColor(new Color(90, 90, 90));
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
		
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
		repaint();
	}

	public void setCoordinates(int posX, int posY) {
		this.xPos = posX;
		this.yPos = posY;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		Component comp = (Component)e.getSource();
		initLoc = comp.getLocation();
		initLocOnScreen = comp.getLocationOnScreen();
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		Component comp = (Component)e.getSource();
		Point locOnScreen = e.getLocationOnScreen();
		
		int x = locOnScreen.x - initLocOnScreen.x + initLoc.x - 16;
		int y = locOnScreen.y - initLocOnScreen.y + initLoc.y - 16;
		comp.setLocation(x, y);
	}

	public void mouseMoved(MouseEvent e) {}
}
