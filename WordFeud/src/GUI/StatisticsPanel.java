package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatisticsPanel extends JPanel {
	
	private JLabel playerName = new JLabel("Player name");
	private JLabel winLoss = new JLabel("Win/Loss ratio");
	
	public StatisticsPanel(){
		
		this.setPreferredSize(new Dimension(1280,800));
		this.setBackground(Color.GRAY);	
//		this.setLayout();

		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		//g.drawString("Player name", (int) ( 640-g.getFontMetrics().getStringBounds("Player name", g).getWidth()/2 ), 350);
	}
}
