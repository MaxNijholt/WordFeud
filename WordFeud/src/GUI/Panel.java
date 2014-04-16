package GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class Panel extends JPanel{
	
	public Panel(){
		this.revalidate();
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(this.getWidth()/2-50, this.getHeight()/2-50, 100, 100);
		g.setColor(Color.WHITE);
		g.drawString(this.getClass().getSimpleName(), this.getWidth()/2-20, this.getHeight()/2-20);
	}
}
