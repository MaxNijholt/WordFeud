package WordFeud;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import Utility.Loader;
import Utility.SLabel;

@SuppressWarnings("serial")
public class GameStone extends SLabel {

	private int 	value;
	private char 	letter;
	private Font 	large, small;
	
	public GameStone(int value, char letter){
		super(String.valueOf(letter), SLabel.CENTER, 30, 30);
		this.value 	= value;
		this.letter = letter;
		this.large 	= new Font("Arial", Font.BOLD, 20);
		this.small	= new Font("Arial", Font.PLAIN, 8);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.drawImage(getImage(), 0, 0, null);
	}
	
	public int getValue(){return value;}
	public char getLetter(){return letter;}
	public Font getLargeFont() {return large;}
	public Font getSmallFont() {return small;}
	public void setDimension(int width, int height) 	{this.setPreferredSize(new Dimension(width, height));}
	public void setFonts(Font large, Font small)		{this.large = large; this.small = small;}
	
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(this.getPreferredSize().width, this.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)image.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.setColor(new Color(255, 255, 255, 180));
		g2d.drawImage(Loader.GAMESTONE, 0, 0, this.getPreferredSize().width, this.getPreferredSize().height, null);
		g2d.setColor(Color.BLACK);
		FontMetrics fmLarge = g2d.getFontMetrics(large);
		FontMetrics fmSmall = g2d.getFontMetrics(small);
		g2d.setFont(large);
		g2d.drawString(String.valueOf(letter), (this.getPreferredSize().width / 2) - (fmLarge.stringWidth(String.valueOf(letter)) / 2), (0 + (this.getPreferredSize().height+1-0) / 2) - ((fmLarge.getAscent() + fmLarge.getDescent()) / 2) + fmLarge.getAscent());
		String temp = String.valueOf(value);
		g2d.setColor(Color.BLACK);
		g2d.setFont(small);
		g2d.drawString(temp, this.getPreferredSize().width - fmSmall.stringWidth(String.valueOf(value)) - (fmSmall.stringWidth(String.valueOf(value)) / 2), (fmSmall.getHeight()) - (fmSmall.getHeight() / 4));
		return image;
	}
	
}
