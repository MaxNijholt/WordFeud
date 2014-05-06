package Utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class SPasswordField extends JPasswordField {

	private String 	placeholder;
	private boolean	rounded;
	private boolean focus;
	
	public SPasswordField(String placeholder) {
		this.placeholder 	= placeholder;
		this.rounded 		= true;
		this.focus 			= false;
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		this.setBackground(new Color(0, 0, 0, 0));
		this.setOpaque(false);
		this.setFont(new Font("Arial", Font.PLAIN, 16));
		this.setPreferredSize(new Dimension(220, 40));
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		if(!focus) {g2d.setColor(Color.WHITE);} else {g2d.setColor(new Color(220, 220, 220));}
		if(!rounded) {
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}
		else {
			g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
		}
		FontMetrics fm = g2d.getFontMetrics();
		if(String.valueOf(getPassword()) == null || (String.valueOf(getPassword()).length() < 1)) {
			g2d.setColor(new Color(100, 100, 100));
			g2d.drawString(placeholder, 5, (0 + (this.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent());
		}
		super.paintComponent(g);
		g2d.dispose();
	}
	
}
