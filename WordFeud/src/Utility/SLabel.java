package Utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class SLabel extends JLabel {
	
	// Instance variables
	private String 	name;
	private Font	font;
	private int		alignment;
	
	// Constants
	public static final int LEFT		=	0;
	public static final int RIGHT		=	1;	
	public static final int CENTER		=	2;
	public static final int TOPCENTER	= 	3;
	
	public SLabel(String name, int alignment) {
		this.name 		= name;
		this.font 		= new Font("Arial", Font.PLAIN, 16);
		this.alignment 	= alignment;
		FontMetrics fm 	= this.getFontMetrics(font);
		this.setPreferredSize(new Dimension(fm.stringWidth(name), fm.getHeight()));
	}
	
	public SLabel(String name, int alignment, int width, int height) {
		this.name 		= name;
		this.font 		= new Font("Arial", Font.PLAIN, 16);
		this.alignment 	= alignment;
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public SLabel(String name, int alignment, Font font) {
		this.name 		= name;
		this.font 		= font;
		this.alignment 	= alignment;
		FontMetrics fm 	= this.getFontMetrics(font);
		this.setPreferredSize(new Dimension(fm.stringWidth(name), fm.getHeight()));
	}
	
	public SLabel(String name, int alignment, Font font, int width, int height) {
		this.name 		= name;
		this.font 		= font;
		this.alignment 	= alignment;
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setFont(font);
		FontMetrics fm = this.getFontMetrics(font);
		int xalign = 0;
		int yalign = (0 + (this.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent();
		switch(alignment) {
			case 0:
				xalign = 0;
				break;
			case 1:
				xalign = getWidth() - fm.stringWidth(name);
				break;
			case 2:
				xalign = (getWidth() / 2) - (fm.stringWidth(name) / 2);
				break;
			case 3:
				xalign = (getWidth() / 2) - (fm.stringWidth(name) / 2);
				yalign = ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent();
				break;
		}
		g2d.drawString(name, xalign, yalign);
		g2d.dispose();
	}
	
}
