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

@SuppressWarnings("serial")
public class SButton extends JButton implements MouseListener {

	private int state;
	private Color standardColor, hoverColor, clickColor;
	
	public static Color ORANGE 	= new Color(201, 80, 46);
	public static Color GREEN 	= new Color(5, 142, 5);
	public static Color PINK 	= new Color(161, 27, 60);
	public static Color CYAN	= new Color(5, 142, 158);
	public static Color YELLOW 	= new Color(230, 156, 27);
	public static Color BLUE	= new Color(45, 126, 219);
	public static Color PURPLE 	= new Color(86, 56, 168);
	public static Color GREY	= new Color(68, 68, 68);
	
	public SButton(String name, Color color) {
		this.setName(name);
		this.standardColor = color;
		int red 	= color.getRed() + 10;
		int green 	= color.getGreen() + 10;
		int blue 	= color.getBlue() + 10;
		this.hoverColor = new Color(red, green, blue);
		this.clickColor = Color.BLACK;
		this.setPreferredSize(new Dimension(100, 30));
		this.state = 0;
		this.setBorderPainted(false);
		this.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
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
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.PLAIN, 16));
		FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.drawString(this.getName(), (this.getWidth() / 2) - (fm.stringWidth(this.getName()) / 2), (0 + (this.getHeight()+1-0) / 2) - ((fm.getAscent() + fm.getDescent()) / 2) + fm.getAscent());
		g2d.dispose();
	}
<<<<<<< .merge_file_a00696
=======
	
	public void setDefaultColor(Color color){
		this.standardColor = color;
	}
	
>>>>>>> .merge_file_a02964

	/*
	 * Mouse Events
	 */
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {state = 1; repaint();}
	public void mouseExited(MouseEvent e) {state = 0; repaint();}
	public void mousePressed(MouseEvent e) {state = 2; repaint();}
	public void mouseReleased(MouseEvent e) {state = 0; repaint();}
	
	
	
}
