package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import Utility.SLabel;

@SuppressWarnings("serial")
public class StatisticsPanel extends JPanel {

	private JPanel centerPanel = new JPanel();

	private SLabel playerName;
	private SLabel playerNameView;
	private SLabel winLoss;
	private SLabel winLossView;
	private SLabel highestGameScore;
	private SLabel highestGameScoreView;
	private SLabel highestWordScore;
	private SLabel highestWordScoreView;
	
	private MenuPanel mp;
	private JPanel allPanel;

	private String title = "Statistics";

	public StatisticsPanel(GUI gui){

		playerName = new SLabel("Player name:", SLabel.LEFT);
		playerNameView = new SLabel(gui.getApplication().getCurrentAccount().getUsername(), SLabel.RIGHT);
		winLoss = new SLabel("Win/Loss ratio", SLabel.LEFT);
		winLossView = new SLabel("5/7", SLabel.RIGHT); //test
		highestGameScore = new SLabel("Highest score in a game", SLabel.LEFT);
		highestGameScoreView = new SLabel("800", SLabel.RIGHT);//test		
		highestWordScore = new SLabel("Highest score with one word", SLabel.LEFT);
		highestWordScoreView = new SLabel("60", SLabel.RIGHT);
		
		mp = new MenuPanel(gui, null);
		
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(94,94,94));
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		
		allPanel = new JPanel();
		allPanel.setPreferredSize(new Dimension(GUI.WIDTH,GUI.HEIGHT));
		allPanel.setBackground(new Color(94, 94, 94));	
		allPanel.setLayout(null);

		centerPanel.setLayout(new GridLayout(4,1));
		centerPanel.setBackground(Color.GRAY);
		centerPanel.setBounds(GUI.WIDTH / 2 - 100, GUI.HEIGHT / 2 + 100 - 145, 200, 200);

		centerPanel.add(playerName);
		centerPanel.add(playerNameView);
		centerPanel.add(winLoss);
		centerPanel.add(winLossView);
		centerPanel.add(highestGameScore);
		centerPanel.add(highestGameScoreView);
		centerPanel.add(highestWordScore);
		centerPanel.add(highestWordScoreView);

		allPanel.add(centerPanel);
		this.add(mp, BorderLayout.NORTH);
		this.add(allPanel, BorderLayout.CENTER);
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