package GUI;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ContentPane extends JPanel {

	public ContentPane(JPanel panel) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.add(panel);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
}
