package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Utility.SButton;
import Utility.STextField;

@SuppressWarnings("serial")
public class CompetitionCreatePanel extends JPanel implements ActionListener {

	private STextField name;
	private SButton back;
	private GUI gui;
	
	public CompetitionCreatePanel(GUI gui) {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(null);
		this.gui = gui;
		
		name = new STextField("Competition name");
		
		back = new SButton("Back", SButton.GREY);
		
		back.addActionListener(this);
		
		this.add(name);
		this.add(back);
		
		name.setBounds(100, 100, 220, 40);
		back.setBounds(100, 150, 220, 40);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(94, 94, 94));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.PLAIN, 40));
		g2d.drawString("Create a competition", 100, 75);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)) {
			gui.switchPanel(new LoginPanel(gui));
		}
	}
	
}
