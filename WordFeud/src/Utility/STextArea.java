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
	private int		arc;
	private String 	placeholder;
	private boolean topLeftRounded, topRightRounded, bottomLeftRounded, bottomRightRounded;
	private Font	font;
	private Color	background, foreground;
	
	/**
	 * STextArea constructor parameters: none
	 */
	public STextArea() {
		init();
	}
	
	/**
	 * STextArea constructor parameters: String text
	 */
	public STextArea(String text) {
		init();
		placeholder = text;
	}
	
	/**
	 * STextArea constructor parameters: int width, int height
	 */
	public STextArea(int width, int height) {
		init();
		setPreferredSize(new Dimension(width, height));
	}
	
	/**
	 * STextArea constructor parameters: boolean editable
	 */
	public STextArea(boolean editable) {
		init();
		setEditable(editable);
	}
	
	/**
	 * STextArea constructor parameters: String text, int width, int height, boolean editable
	 */
	public STextArea(String text, int width, int height, boolean editable) {
		init();
		placeholder = text;
		setPreferredSize(new Dimension(width, height));
		setEditable(editable);
	}
	
	/**
	 * Private init method for the default stuff
	 */
	private void init() {
		placeholder 		= "";
		topLeftRounded 		= true;
		topRightRounded 	= true;
		bottomRightRounded	= true;	
		bottomLeftRounded	= true;
		arc					= 10;
		background			= new Color(255, 255, 255);
		foreground			= new Color(100, 100, 100);
		font				= new Font("Arial", Font.PLAIN, 16);
		setRows(0);
		setPreferredSize(new Dimension(200, 200));
		setEditable(true);
		setWrapStyleWord(true);
		setLineWrap(true);
		setOpaque(false);
		setFont(new Font("Arial", Font.PLAIN, 16));
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
	}
	
	/**
	 * Overridden paintComponent(Graphics g) method from JComponent used to draw better graphics for STextArea
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setColor(background);
		
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
		
		if(!topLeftRounded) 	{g2d.fillRect(0, 0, arc, arc);}
		if(!topRightRounded) 	{g2d.fillRect(getWidth() - arc, 0, arc, arc);}
		if(!bottomLeftRounded)	{g2d.fillRect(0, getHeight() - arc, arc, arc);}
		if(!bottomRightRounded)	{g2d.fillRect(getWidth() - arc, getHeight() - arc, arc, arc);}
		
		FontMetrics fm = g2d.getFontMetrics(font);
		g2d.setFont(font);
		
		if(getText() == null || getText().length() < 1) {
			g2d.setColor(foreground);
			if(placeholder != null) {
				g2d.drawString(placeholder, 5, (0 + (this.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent());
			}
		}
		super.paintComponent(g);
		g2d.dispose();
	}
	
	// Setters
	public void setTopLeftRounded(boolean rounded) 		{this.topLeftRounded = rounded;}
	public void setTopRightRounded(boolean rounded) 	{this.topRightRounded = rounded;}
	public void setBottomLeftRounded(boolean rounded) 	{this.bottomLeftRounded = rounded;}
	public void setBottomRightRounded(boolean rounded) 	{this.bottomRightRounded = rounded;}
	
	public void setBackground(Color color) 	{this.background = color;}
	public void setForeground(Color color)	{this.foreground = color;}
	public void setArc(int arc)				{this.arc = arc;}
	
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
	
	public void addText(String text) {
		if(!this.getText().isEmpty()) {this.setText(this.getText() + "\n" + text);}
		else {this.setText(text);}
		setRows(getRows() + 1);
	}
	
}
