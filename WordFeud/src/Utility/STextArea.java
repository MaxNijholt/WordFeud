package Utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

/**
 * @author Stan van Heumen
 */
@SuppressWarnings("serial")
public class STextArea extends JTextArea {
	
	// Instance variables
	private int		arc, alignment;
	private String 	placeholder;
	private boolean topLeftRounded, topRightRounded, bottomLeftRounded, bottomRightRounded;
	private Font	font;
	private Color	background, foreground;
	
	// Alignment constants
	public static final int LEFT	= 0;
	public static final int	CENTER	= 1;
	public static final int	RIGHT	= 2;
		
	/**
	 * STextArea constructor parameters: none<br>
	 * This constructor creates a textField that is 100*100
	 */
	public STextArea() {
		init();
	}
	
	/**
	 * STextArea constructor parameters: String text<br>
	 * This constructor creates a textField that is the size of the placeholder
	 */
	public STextArea(String text) {
		init();
		placeholder = text;
		setPreferredSize(new Dimension(getFontMetrics(font).stringWidth(placeholder) + 10, getFontMetrics(font).getHeight() + 10));
	}
	
	/**
	 * STextArea constructor parameters: int width, int height<br>
	 * This constructor creates a textField that is the size of the width and height parameters
	 */
	public STextArea(int width, int height) {
		init();
		setPreferredSize(new Dimension(width, height));
	}
	
	/**
	 * STextArea constructor parameters: boolean editable<br>
	 * This constructor creates a textField that is 100*100 and can be non-editable or editable
	 */
	public STextArea(boolean editable) {
		init();
		setEditable(editable);
	}
	
	/**
	 * STextArea constructor parameters: String text, int width, int height, boolean editable<br>
	 * This constructor creates a textField that is the size of the width and height parameters,<br>
	 * And can be editable or non-editable and contains a String
	 */
	public STextArea(String text, int width, int height, boolean editable) {
		init();
		placeholder = text;
		setPreferredSize(new Dimension(width, height));
		setEditable(editable);
	}
	
	/**
	 * Private initialize method for the default stuff<br>
	 * - Instance variables<br>
	 * - TextArea values
	 */
	private void init() {
		// Instance variables
		placeholder 		= "";
		arc					= 10;
		alignment			= STextArea.CENTER;
		topLeftRounded 		= true;
		topRightRounded 	= true;
		bottomRightRounded	= true;	
		bottomLeftRounded	= true;
		background			= new Color(255, 255, 255);
		foreground			= new Color(100, 100, 100);
		font				= new Font("Arial", Font.PLAIN, 16);
		
		// TextArea variables
		setRows(0);
		setEditable(true);
		setWrapStyleWord(true);
		setLineWrap(true);
		setOpaque(false);
		setFont(font);
		setPreferredSize(new Dimension(100, 100));
		setBorder(BorderFactory.createLineBorder(background, 5));
	}
	
	/**
	 * Overridden paintComponent(Graphics g) method from JComponent used to draw better graphics for STextArea<br>
	 * This STextArea has different shapes including: rounded, square, square-rounded<br>
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
			if(placeholder != null) {
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

	// Text setters
	public void setBackground(Color color) 				{this.background = color;}
	public void setForeground(Color color)				{this.foreground = color;}
	public void setTextFont(Font font) 					{this.font = font; this.setFont(font);}
	public void setAlignment(int alignment)				{this.alignment = alignment;}
	
	// Add text
	public void addText(String text) {
		if(!this.getText().isEmpty()) {this.setText(this.getText() + "\n" + text);}
		else {this.setText(text);}
		setRows(getRows() + 1);
	}
	
}
