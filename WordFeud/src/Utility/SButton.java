package Utility;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

/**
 * @author Stan van Heumen
 */
@SuppressWarnings("serial")
public class SButton extends JButton implements MouseListener {

	// Instance Variables
	private 			int 	state, alignment, arc;
	private 			boolean topLeftRounded, topRightRounded, bottomLeftRounded, bottomRightRounded;
	private				Font	font;
	private 			Color 	standardColor, hoverColor, clickColor, textColor;
	
	// Color constants
	public static final	Color 	ORANGE 	= new Color(201, 80, 46);
	public static final	Color 	GREEN 	= new Color(5, 142, 5);
	public static final	Color 	PINK 	= new Color(161, 27, 60);
	public static final	Color 	CYAN	= new Color(5, 142, 158);
	public static final	Color 	YELLOW 	= new Color(230, 156, 27);
	public static final	Color 	BLUE	= new Color(45, 126, 219);
	public static final	Color 	PURPLE 	= new Color(86, 56, 168);
	public static final	Color 	GREY	= new Color(68, 68, 68);
	public static final Color 	BLACK	= new Color(0, 0, 0);
	public static final Color	WHITE	= new Color(255, 255, 255);
	
	// Alignment constants
	public static final int 	LEFT	= 0;
	public static final int		CENTER	= 1;
	public static final int		RIGHT	= 2;
	
	//Constructor @param name, color
	public SButton(String name, Color color) {
		// Default stuff
		init(name);
		calculateColors(color);
	}
	
	// Constructor @param name, color, width, height
	public SButton(String name, Color color, int width, int height) {
		// Default stuff
		init(name);
		calculateColors(color);
		this.setPreferredSize(new Dimension(width, height));
	}
	
	// Private initialize method
	private void init(String name) {
		font 				= new Font("Arial", Font.PLAIN, 16);
		state 				= 0;
		alignment			= SButton.CENTER;
		arc					= 10;
		topLeftRounded 		= true;
		topRightRounded 	= true;
		bottomRightRounded	= true;	
		bottomLeftRounded	= true;
		textColor			= Color.WHITE;
		FontMetrics fm = getFontMetrics(font);
		this.setName(name);
		this.setFont(font);
		this.setPreferredSize(new Dimension(fm.stringWidth(name) + 10, fm.getHeight() + 10));
		this.setOpaque(false);
		this.setBorderPainted(false);
		this.addMouseListener(this);
	}
	
	// Private method to calculate the colors
	private void calculateColors(Color color) {
		// Get the RGB value of Color color
		int r	= color.getRed();
		int g 	= color.getGreen();
		int b 	= color.getBlue();
		// Set color to that color
		this.standardColor = color;
		// Calculate the hover color
		if(r + 10 <= 255) {r += 10;} else {r = 255;}
		if(g + 10 <= 255) {g += 10;} else {g = 255;}
		if(b + 10 <= 255) {b += 10;} else {b = 255;}
		// Set the hover color to that calculated color
		this.hoverColor = new Color(r, g, b);
		// Reset the RGB values
		r	= color.getRed();
		g 	= color.getGreen();
		b 	= color.getBlue();
		// Calculate the click color
		if(r - 10 >= 0) {r -= 10;} else {r = 0;}
		if(g - 10 >= 0) {g -= 10;} else {g = 0;}
		if(b - 10 >= 0) {b -= 10;} else {b = 0;}
		// Set the click color to that calculated color
		this.clickColor = new Color(r, g, b);
	}
	
	// The overridden paintComponent, used to draw the button
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		// Switch the different states
		switch(state) {
			case 0:
				g2d.setColor(this.standardColor);
				break;
			case 1:
				g2d.setColor(this.hoverColor);
				break;
			case 2: 
				g2d.setColor(this.clickColor);
				break;
		}
		// Important for smooth letters and corners
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
		
		if(!topLeftRounded) 	{g2d.fillRect(0, 0, arc, arc);}
		if(!topRightRounded) 	{g2d.fillRect(getWidth() - arc, 0, arc, arc);}
		if(!bottomLeftRounded)	{g2d.fillRect(0, getHeight() - arc, arc, arc);}
		if(!bottomRightRounded)	{g2d.fillRect(getWidth() - arc, getHeight() - arc, arc, arc);}
		
		g2d.setFont(new Font("Arial", Font.PLAIN, 16));
		FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
		g2d.setColor(textColor);
		int x = 0;
		switch(alignment) {
			case SButton.LEFT:
				x = 5;
				break;
			case SButton.CENTER:
				x = (this.getWidth() / 2) - (fm.stringWidth(getName()) / 2);
				break;
			case SButton.RIGHT:
				x = (this.getWidth() - fm.stringWidth(getName()) - 5);
				break;
		}
		g2d.drawString(getName(), x, (0 + (this.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent());
		g2d.dispose();
	}

	// Mouse Events
	public void mouseClicked(MouseEvent e) 	{state = 1; repaint();}
	public void mouseEntered(MouseEvent e) 	{state = 1; repaint();}
	public void mouseExited(MouseEvent e) 	{state = 0; repaint();}
	public void mousePressed(MouseEvent e) 	{state = 2; repaint();}
	public void mouseReleased(MouseEvent e) {state = 0; repaint();}
	
	// Setters
	public void setColor(Color color){calculateColors(color);}
	public void setColors(Color standardColor, Color hoverColor, Color clickColor) {
		this.standardColor 	= standardColor;
		this.hoverColor 	= hoverColor;
		this.clickColor 	= clickColor;
	}
	public void setRounded(boolean rounded) {
		this.topLeftRounded 	= rounded;
		this.topRightRounded 	= rounded;
		this.bottomLeftRounded 	= rounded;
		this.bottomRightRounded = rounded;
	}
	public void setTopLeftRounded(boolean rounded) 		{this.topLeftRounded = rounded;}
	public void setTopRightRounded(boolean rounded) 	{this.topRightRounded = rounded;}
	public void setBottomLeftRounded(boolean rounded) 	{this.bottomLeftRounded = rounded;}
	public void setBottomRightRounded(boolean rounded) 	{this.bottomRightRounded = rounded;}
	public void setCustomRounded(boolean topLeft, boolean topRight, boolean bottomLeft, boolean bottomRight) {
		this.topLeftRounded 	= topLeft;
		this.topRightRounded 	= topRight;
		this.bottomLeftRounded 	= bottomLeft;
		this.bottomRightRounded = bottomRight;
	}
	public void setTextColor(Color color) 				{this.textColor = color;}
	public void setAlignment(int alignment)				{this.alignment = alignment;}
	public void setFont(Font font)						{this.font = font;}	
	public void setArc(int arc) 						{this.arc = arc;}
	
}
