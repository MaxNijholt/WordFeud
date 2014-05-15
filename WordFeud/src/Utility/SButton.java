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
	
	// Default color constants
	public static final	Color 	ORANGE 	= new Color(201, 80, 46);
	public static final	Color 	GREEN 	= new Color(5, 142, 5);
	public static final	Color 	PINK 	= new Color(161, 27, 60);
	public static final	Color 	CYAN	= new Color(5, 142, 158);
	public static final	Color 	RED		= new Color(237, 67, 33);
	public static final	Color 	YELLOW 	= new Color(230, 156, 27);
	public static final	Color 	BLUE	= new Color(45, 126, 219);
	public static final	Color 	PURPLE 	= new Color(86, 56, 168);
	public static final	Color 	GREY	= new Color(68, 68, 68);
	public static final Color 	BLACK	= new Color(0, 0, 0);
	public static final Color	WHITE	= new Color(255, 255, 255);
	
	// Transparent color constants
	public static final	Color 	T_GREY	= new Color(68, 68, 68, 100);
	public static final Color 	T_BLACK	= new Color(0, 0, 0, 100);
	public static final Color	T_WHITE	= new Color(255, 255, 255, 100);
	
	// Alignment constants
	public static final int 	LEFT	= 0;
	public static final int		CENTER	= 1;
	public static final int		RIGHT	= 2;
	
	/**
	 * Constructor parameters: name, color<br>
	 * This constructor creates a button that has a preferredSize that is equal to the name of the button, plus some padding<br>
	 * The SButton has custom colors like for example: 'SButton.RED'
	 */
	public SButton(String name, Color color) {
		init(name);
		calculateColors(color);
	}
	
	/**
	 * Constructor parameters: name, color, width, height<br>
	 * This constructor creates a button that has a preferredSize that is equal to the width and height parameters<br>
	 * The SButton has custom colors like for example: 'SButton.RED'
	 */
	public SButton(String name, Color color, int width, int height) {
		init(name);
		calculateColors(color);
		setPreferredSize(new Dimension(width, height));
	}
	
	/**
	 * Private initialize method for default stuff, like:<br>
	 * - Instance variables<br>
	 * - Button values
	 */
	private void init(String name) {
		// Instance variables
		state 				= 0;									// Not hovered, not clicked
		arc					= 10;									// The arc of the rounding
		topLeftRounded 		= true;									// topLeft arc is drawn
		topRightRounded 	= true;									// topRight arc is drawn
		bottomLeftRounded	= true;									// bottomLeft arc is drawn
		bottomRightRounded	= true;									// bottomRight arc is drawn
		font 				= new Font("Arial", Font.PLAIN, 16);	// Default font for the SButton
		alignment			= SButton.CENTER;						// SButton default alignment
		textColor			= SButton.WHITE;						// SButton default color
		
		// Button values
		setName(name);
		setFont(font);
		setPreferredSize(new Dimension(getFontMetrics(font).stringWidth(name) + 10, getFontMetrics(font).getHeight() + 10));
		setOpaque(false);
		setBorderPainted(false);
		addMouseListener(this);
	}
	
	/**
	 * Private method to calculate the colors:<br>
	 * - Default color	: the default color for the button<br>
	 * - Hover color	: the color that the button becomes when you hover it<br>
	 * - Click color	: the color that the button becomes when you click it
	 */
	private void calculateColors(Color color) {
		// Get the RGB value of Color color
		int r	= color.getRed();
		int g 	= color.getGreen();
		int b 	= color.getBlue();
		// Set color to that color
		standardColor = color;
		// Calculate the hover color
		if(r + 10 <= 255) {r += 10;} else {r = 255;}
		if(g + 10 <= 255) {g += 10;} else {g = 255;}
		if(b + 10 <= 255) {b += 10;} else {b = 255;}
		// Set the hover color to that calculated color
		hoverColor = new Color(r, g, b);
		// Reset the RGB values
		r	= color.getRed();
		g 	= color.getGreen();
		b 	= color.getBlue();
		// Calculate the click color
		if(r - 10 >= 0) {r -= 10;} else {r = 0;}
		if(g - 10 >= 0) {g -= 10;} else {g = 0;}
		if(b - 10 >= 0) {b -= 10;} else {b = 0;}
		// Set the click color to that calculated color
		clickColor = new Color(r, g, b);
	}
	
	/**
	 * The overridden paintComponent(Graphics g) from JComponent, used to draw the button<br>
	 * This button has different shapes including: rounded, square, square-rounded<br>
	 * These are all customizable
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
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
		
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
		
		if(!topLeftRounded) {
			g2d.clearRect(0, 0, arc, arc);
			g2d.fillRect(0, 0, arc, arc);
		}
		if(!topRightRounded) {
			g2d.clearRect(getWidth() - arc, 0, arc, arc);
			g2d.fillRect(getWidth() - arc, 0, arc, arc);
		}
		if(!bottomLeftRounded) {
			g2d.clearRect(0, getHeight() - arc, arc, arc);
			g2d.fillRect(0, getHeight() - arc, arc, arc);
		}
		if(!bottomRightRounded)	{
			g2d.clearRect(getWidth() - arc, getHeight() - arc, arc, arc);
			g2d.fillRect(getWidth() - arc, getHeight() - arc, arc, arc);
		}
		
		g2d.setFont(font);
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
	
	// Color setters
	public void setColor(Color color) 					{calculateColors(color);}
	public void setColors(Color standardColor, Color hoverColor, Color clickColor) {this.standardColor = standardColor; this.hoverColor = hoverColor; this.clickColor = clickColor;}
	public void setTextColor(Color color) 				{this.textColor = color;}
	
	// Rounding setters
	public void setArc(int arc) 						{this.arc = arc;}
	public void setTopLeftRounded(boolean rounded) 		{this.topLeftRounded = rounded;}
	public void setTopRightRounded(boolean rounded) 	{this.topRightRounded = rounded;}
	public void setBottomLeftRounded(boolean rounded) 	{this.bottomLeftRounded = rounded;}
	public void setBottomRightRounded(boolean rounded) 	{this.bottomRightRounded = rounded;}
	public void setRounded(boolean rounded) {this.topLeftRounded = rounded; this.topRightRounded = rounded; this.bottomLeftRounded = rounded; this.bottomRightRounded = rounded;}
	public void setCustomRounded(boolean topLeft, boolean topRight, boolean bottomLeft, boolean bottomRight) {this.topLeftRounded = topLeft; this.topRightRounded = topRight; this.bottomLeftRounded = bottomLeft; this.bottomRightRounded = bottomRight;}
	
	// Text setters
	public void setAlignment(int alignment)				{this.alignment = alignment;}
	public void setFont(Font font)						{this.font = font;}
	
}
