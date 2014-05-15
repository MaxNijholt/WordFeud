package Utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 * @author Stan van Heumen
 */
@SuppressWarnings("serial")
public class STextField extends JTextField {
	
	// Instance variables
	private int 	arc, alignment;
	private String 	placeholder;
	private boolean topLeftRounded, topRightRounded, bottomLeftRounded, bottomRightRounded;
	private Font	font;
	private Color	background, foreground;
	
	// Alignment constants
	public static final int LEFT	= 0;
	public static final int	CENTER	= 1;
	public static final int	RIGHT	= 2;
	
	/**
	 * STextfield constructor parameters: String text<br>
	 * This constructor creates a textField that is the size of the placeholder
	 */
	public STextField(String text) {
		init(text);
	}
	
	/**
	 * STextfield constructor parameters: String text, int width, int height<br>
	 * This constructor creates a textField that is the size of the width and height parameters
	 */
	public STextField(String text, int width, int height) {
		init(text);
		setPreferredSize(new Dimension(width, height));
	}
	
	/**
	 * Private initialize method for the default stuff<br>
	 * - Instance variables<br>
	 * - TextField values
	 */
	private void init(String text) {
		// Instance variables
		placeholder			= text;									// The placeholder that will be displayed inside the STextArea
		arc 				= 10;									// The arc of the rounding
		alignment			= STextField.LEFT;						// The alignment of the placeholder, default is LEFT
		topLeftRounded 		= true;									// topLeft arc is drawn
		topRightRounded 	= true;									// topRight arc is drawn
		bottomLeftRounded	= true;									// bottomLeft arc is drawn
		bottomRightRounded	= true;									// bottomRight arc is drawn
		background			= new Color(255, 255, 255);				// Default color for the background
		foreground			= new Color(100, 100, 100);				// Default color for the foreground
		font				= new Font("Arial", Font.PLAIN, 16);	// Default font for the STextField
		
		// TextField variables
		setPreferredSize(new Dimension(getFontMetrics(font).stringWidth(placeholder) + 10, getFontMetrics(font).getHeight() + 10));
		setBorder(BorderFactory.createLineBorder(background, 5));
		setOpaque(false);
		setFont(font);
	}
	
	/**
	 * Overridden paintComponent(Graphics g) method from JComponent used to draw better graphics for textfield<br>
	 * This textField has different shapes including: rounded, square, square-rounded<br>
	 * These are all customizable
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setColor(background);
		
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
		
		FontMetrics fm = g2d.getFontMetrics(font);
		g2d.setFont(font);
		if(this.getText() == null || (this.getText().length() < 1)) {
			g2d.setColor(foreground);
			int x = 0;
			switch(alignment) {
			case SButton.LEFT:
				x = 5;
				break;
			case SButton.CENTER:
				x = (this.getWidth() / 2) - (fm.stringWidth(placeholder) / 2);
				break;
			case SButton.RIGHT:
				x = (this.getWidth() - fm.stringWidth(placeholder) - 5);
				break;
			}
			g2d.drawString(placeholder, x, (0 + (this.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent());
		}
		super.paintComponent(g);
		g2d.dispose();
	}
	
	// Rounding setters
	public void setArc(int arc)							{this.arc = arc;}
	public void setTopLeftRounded(boolean rounded) 		{this.topLeftRounded = rounded;}
	public void setTopRightRounded(boolean rounded) 	{this.topRightRounded = rounded;}
	public void setBottomLeftRounded(boolean rounded) 	{this.bottomLeftRounded = rounded;}
	public void setBottomRightRounded(boolean rounded) 	{this.bottomRightRounded = rounded;}
	public void setRounded(boolean rounded) {this.topLeftRounded = rounded; this.topRightRounded = rounded; this.bottomLeftRounded = rounded; this.bottomRightRounded = rounded;}
	public void setCustomRounded(boolean topLeft, boolean topRight, boolean bottomLeft, boolean bottomRight) {this.topLeftRounded = topLeft; this.topRightRounded = topRight; this.bottomLeftRounded = bottomLeft; this.bottomRightRounded = bottomRight;}
	
	// Background setters
	public void setBackground(Color color) 				{this.background = color;}
	public void setForeground(Color color)				{this.foreground = color;}
	
	// Text setters
	public void setPlaceholder(String placeholder) 		{this.placeholder = placeholder;}
	public void setTextFont(Font font) 					{this.font = font; this.setFont(font);}
	public void setAlignment(int alignment)				{this.alignment = alignment;}
	
	// Placeholder getter
	public String getPlaceholder() 						{return placeholder;}
	
}
