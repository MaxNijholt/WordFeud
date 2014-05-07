package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatisticsPanel extends JPanel {
	
	private JPanel centerPanel = new JPanel();
	
	private JLabel playerName;
	private JLabel winLoss;
	private JLabel highestGameScore;
	private JLabel highestWordScore;
	
	private String title = "Statistics";

	
	
	
	public StatisticsPanel(){
				
		playerName = new JLabel("Player name");
		winLoss = new JLabel("Win/Loss ratio");
		highestGameScore = new JLabel("Highest score in a game");
		highestWordScore = new JLabel("Highest score with one word");
		
		this.setPreferredSize(new Dimension(GUI.WIDTH,GUI.HEIGHT));
		this.setBackground(Color.GRAY);	
		this.setLayout(null);
		
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBackground(Color.GRAY);
		centerPanel.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 + 100 - 145, 200, 200);
		
		playerName.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		winLoss.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		highestGameScore.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		highestWordScore.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		playerName.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		winLoss.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		highestGameScore.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		highestWordScore.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		
		centerPanel.add(playerName);
		centerPanel.add(winLoss);
		centerPanel.add(highestGameScore);
		centerPanel.add(highestWordScore);
		
		this.add(centerPanel);
	
				
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 100));
		g2d.drawString(title, (int) ((GUI.WIDTH / 2) - (g.getFontMetrics().getStringBounds(GUI.TITLE, g).getWidth() / 2)), 150);
	}
}

