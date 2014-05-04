package Utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class STextArea extends JTextArea {
	
	private boolean rounded;
	
	public STextArea(int width, int height, boolean editable) {
		this.setRows(0);
		this.setPreferredSize(new Dimension(width, height));
		this.setEditable(editable);
		this.rounded = true;
		this.setWrapStyleWord(true);
		this.setLineWrap(true);
		this.setOpaque(false);
		this.setFont(new Font("Arial", Font.PLAIN, 16));
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setColor(Color.WHITE);
		if(!rounded) {
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}
		else {
			g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
		}
		super.paintComponent(g);
		g2d.dispose();
	}
	
	public void setRounded(boolean rounded) {this.rounded = rounded;}
	public void addText(String text) {
		if(!this.getText().isEmpty()) {this.setText(this.getText() + "\n" + text);}
		else {this.setText(text);}
		this.setRows(getRows() + 1);
	}
	
}
