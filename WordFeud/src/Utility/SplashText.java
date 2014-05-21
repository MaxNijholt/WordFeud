package Utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JPanel;

public class SplashText implements Runnable {

	private String 			text;
	private Color 			color;
	private int				x, y;
	private Font			font;
	private Thread 			thread;
	private String[] 		texts;
	private JPanel			panel;
	private boolean			running;
	
	public SplashText(Color c, int xPos, int yPos, JPanel p) {
		color		= c;
		panel		= p;
		x			= xPos;
		y			= yPos;
		font		= new Font("Arial", Font.PLAIN, 10);
		thread 		= new Thread(this);
		texts		= new String[] 
				{	
//					"This is awesome", "So cool", "Really nice!", "35% bug free!", "Awesome", "LOL!",
//					"It's a game!", "Wordfeud", "ITSMA so cool!", "UMadBrah?", ".party();", "Check it out!",
//					"Deja� vu!", "Deja� vu!", "Finger-licking!", "GOTY!", "Mmmph, mmph!", "Pretty!", "Fancy!",
//					"Woah!", "Wow!", "Yaay!", "Water proof!", "Superfragilisticexpialidocious!",
//					"This message will never appear on the splash screen, isn't that weird?", "Try it!"
					"Administrator Adam = new Administrator();"
				};
		text		= texts[new Random().nextInt(texts.length)];
		running		= true;
		thread.start();
	}
	
	public void drawSplash(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setFont(font);
		FontRenderContext frc = g2d.getFontRenderContext();
		TextLayout l = new TextLayout(text, font, frc);
		Rectangle2D bounds = l.getBounds();
		g2d.rotate(Math.toRadians(-20), x - (Math.round(bounds.getWidth()) / 2), y);
		g2d.setColor(Color.BLACK);
		g2d.drawString(text, x - (Math.round(bounds.getWidth()) / 2) + 3, y + 3);
		g2d.setColor(color);
		g2d.drawString(text, x - (Math.round(bounds.getWidth()) / 2), y);
	}
	
	public void run() {
		int i = 20;
		int j = 1;
		while(running) {
			font = new Font("Aharoni", Font.BOLD, i);
			if(i == 25) {j = -1;}
			if(i == 10) {j = 1;}
			i += j;
			panel.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setRunning(boolean run) {
		this.running = run;
	}

}
