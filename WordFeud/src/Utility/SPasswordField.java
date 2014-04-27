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

private String placeholder;
	
	public SPasswordField(String placeholder) {
		this.placeholder = placeholder;
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		this.setFont(new Font("Arial", Font.PLAIN, 16));
		this.setPreferredSize(new Dimension(220, 40));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(String.valueOf(this.getPassword()) == null || (String.valueOf(this.getPassword()).length() < 1)) {
			Graphics2D g2d = (Graphics2D)g.create();
			g2d.setColor(new Color(100, 100, 100));
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			FontMetrics fm = g2d.getFontMetrics();
			g2d.drawString(placeholder, 5, (0 + (this.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent());
			g2d.dispose();
		}
	}
	
}
