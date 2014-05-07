package Utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

/**
 * @author Stan van Heumen
 */
@SuppressWarnings("serial")
public class SLabel extends JLabel {
	
	// Instance variables
	private String 	text;
	private int 	arc;
	private boolean topLeftRounded, topRightRounded, bottomLeftRounded, bottomRightRounded;
	private Font	font;
	private int		alignment;
	private Color	foreground, background;
	private boolean drawBackground;
	
	// Constants
	public static final int LEFT		=	0;
	public static final int RIGHT		=	1;	
	public static final int CENTER		=	2;
	public static final int TOPCENTER	= 	3;
	public static final int PADDINGLEFT	=	4;
	
	public SLabel(String name, int align) {
		init(name, align);
	}
	
	public SLabel(String name, int align, int width, int height) {
		init(name, align);
		setPreferredSize(new Dimension(width, height));
	}
	
	public SLabel(String name, int align, Font f) {
		init(name, align);
		font = f;
		FontMetrics fm 	= getFontMetrics(font);
		setPreferredSize(new Dimension(fm.stringWidth(name), fm.getHeight()));
	}
	
	public SLabel(String name, int align, Font f, int width, int height) {
		init(name, align);
		font = f;
		setPreferredSize(new Dimension(width, height));
	}
	
	private void init(String name, int align) {
		text 				= name;
		font 				= new Font("Arial", Font.PLAIN, 16);
		alignment 			= align;
		foreground			= new Color(255, 255, 255);
		background			= new Color(0, 0, 0);
		topLeftRounded 		= true;
		topRightRounded 	= true;
		bottomRightRounded	= true;	
		bottomLeftRounded	= true;
		arc 				= 10;
		FontMetrics fm 	= getFontMetrics(font);
		setPreferredSize(new Dimension(fm.stringWidth(text) + 10, fm.getHeight() + 10));
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setFont(font);
		g2d.setColor(background);
		if(drawBackground) {
			g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
			
			if(!topLeftRounded) 	{g2d.fillRect(0, 0, arc, arc);}
			if(!topRightRounded) 	{g2d.fillRect(getWidth() - arc, 0, arc, arc);}
			if(!bottomLeftRounded)	{g2d.fillRect(0, getHeight() - arc, arc, arc);}
			if(!bottomRightRounded)	{g2d.fillRect(getWidth() - arc, getHeight() - arc, arc, arc);}
			
		}
		
		g2d.setColor(foreground);
		FontMetrics fm = g2d.getFontMetrics(font);
		g2d.setFont(font);
		int xalign = 0;
		int yalign = (0 + (this.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent();
		switch(alignment) {
			case 0:
				xalign = 0;
				break;
			case 1:
				xalign = getWidth() - fm.stringWidth(text);
				break;
			case 2:
				xalign = (getWidth() / 2) - (fm.stringWidth(text) / 2);
				break;
			case 3:
				xalign = (getWidth() / 2) - (fm.stringWidth(text) / 2);
				yalign = ((fm.getAscent() + fm.getDescent()) / 2);
				break;
			case 4:
				xalign = 5;
				break;
		}
		g2d.drawString(text, xalign, yalign);
		g2d.dispose();
	}
	
	public void setTopLeftRounded(boolean rounded) 		{this.topLeftRounded = rounded;}
	public void setTopRightRounded(boolean rounded) 	{this.topRightRounded = rounded;}
	public void setBottomLeftRounded(boolean rounded) 	{this.bottomLeftRounded = rounded;}
	public void setBottomRightRounded(boolean rounded) 	{this.bottomRightRounded = rounded;}
	public void setArc(int arc)							{this.arc = arc;}
	
	public void setTextFont(Font font) {
		this.font = font;
		this.setFont(font);
	}
	
	public void setCustomRounded(boolean topLeft, boolean topRight, boolean bottomLeft, boolean bottomRight) {
		this.topLeftRounded 	= topLeft;
		this.topRightRounded 	= topRight;
		this.bottomLeftRounded 	= bottomLeft;
		this.bottomRightRounded = bottomRight;
	}
	
	public void changeTextColor(Color foreground, Color background) {this.foreground = foreground; this.background = background;}
	public void setName(String text) {this.text = text;}
	public void drawBackground(boolean a) {this.drawBackground = a;}
	
	public String getName() {return text;}
	
}
