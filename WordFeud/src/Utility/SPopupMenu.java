package Utility;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class SPopupMenu extends JPopupMenu {

	private SLabel text;
	private Color background;
	
	public SPopupMenu() {
		init();
	}
	
	public SPopupMenu(int width, int height) {
		init();
		text = new SLabel("", SLabel.CENTER, width, height);
		text.setBackground(background);
	}
	
	private void init() {
		setLayout(new GridLayout(0, 1));
		setOpaque(false);
	
		///////////	UIManager ///////////
		UIManager.put("PopupMenu.background", new Color(255, 255, 255, 0));
		UIManager.put("PopupMenu.border", BorderFactory.createEmptyBorder());
		///////////	UIManager ///////////
		
		text = new SLabel("Default message", SLabel.CENTER);
		text.setPreferredSize(new Dimension(220, 40));
		text.setBackground(background);
		add(text);
	}
	
	public void show(Component invoker, int x, int y, String message, Color background) {
		this.text = new SLabel(message, SLabel.CENTER);
		this.background = background;
		super.show(invoker, x, y);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.setFont(new Font("Arial", Font.PLAIN, 16));
		FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
		
		g2d.setColor(background);
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
		
		g2d.setColor(Color.WHITE);
		
		g2d.drawString(text.getName(), (this.getWidth() / 2) - (fm.stringWidth(text.getName()) / 2), (0 + (this.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent());
		g2d.dispose();
	}
	
}
