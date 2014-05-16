package WordFeud;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import Utility.SLabel;

@SuppressWarnings("serial")
public class GameStone extends SLabel {

	private int 	value;
	private char 	letter;
	private Font 	large, small;
	
	public GameStone(int value, char letter){
		super(String.valueOf(letter), SLabel.CENTER, new Font("Arial", Font.BOLD, 25), 30, 30);
		this.value 	= value;
		this.letter = letter;
		this.large 	= new Font("Arial", Font.BOLD, 20);
		this.small	= new Font("Arial", Font.PLAIN, 8);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.setColor(Color.WHITE);
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
		g2d.setColor(Color.BLACK);
		FontMetrics fmLarge = g2d.getFontMetrics(large);
		FontMetrics fmSmall = g2d.getFontMetrics(small);
		g2d.setFont(large);
		g.drawString(String.valueOf(letter), (getWidth() / 2) - (fmLarge.stringWidth(String.valueOf(letter)) / 2) - (fmSmall.stringWidth("0") / 2), (0 + (this.getHeight()+1-0) / 2) - ((fmLarge.getAscent() + fmLarge.getDescent()) / 2) + fmLarge.getAscent());
		String temp = String.valueOf(value);
		g2d.setColor(Color.BLACK);
		g2d.setFont(small);
		g.drawString(temp, getWidth() - fmSmall.stringWidth(String.valueOf(value)) - 2, (fmSmall.getHeight() / 2) + 5);
	}
	
	public int getValue(){return value;}
	public char getLetter(){return letter;}
	public Font getLargeFont() {return large;}
	public Font getSmallFont() {return small;}
	public void setDimension(int width, int height) 	{this.setPreferredSize(new Dimension(width, height));}
	public void setFonts(Font large, Font small)		{this.large = large; this.small = small;}
	
}
