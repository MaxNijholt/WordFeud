package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import Utility.SButton;
import WordFeud.Game;
import WordFeud.Tile;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private SButton 	pass, swap, resign, play;
	private ChatPanel 	chat;
	private MenuPanel 	mp;
	private Game 		game;
	private GUI 		gui;
	
	public GamePanel(GUI gui){
		this.gui = gui;
		gui.setLoadingCursor(true);
		
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(94, 94, 94));
		mp		= new MenuPanel(gui, new PlayerPanel(gui));
		chat 	= new ChatPanel(gui, game);
		
		pass 	= new SButton("Pass", SButton.CYAN, 120, 40);
		swap 	= new SButton("Swap",  SButton.YELLOW, 120, 40);
		resign 	= new SButton("Resign",  SButton.RED, 120, 40);
		play	= new SButton("Play",  SButton.GREEN, 120, 40);
		
		JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainPanel.setBackground(new Color(50, 50, 50));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1, 0, 10));
		buttonPanel.setOpaque(false);
		/*		
		JPanel handPanel = new JPanel();
		handPanel.setLayout(new GridLayout(1, 7, 3, 3));
		for(int i = 0; i < 7; i++) {
			Tile tile = new Tile(i, 0);
			handPanel.add(tile);
		}
		*/
		
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(15, 15, 3, 3));
		gamePanel.setOpaque(false);
		
		for(int y = 0; y < 15; y++) {
			for(int x = 0; x < 15; x++) {
				Tile tile = new Tile(x, y);
				gamePanel.add(tile);
			}
		}
		
		buttonPanel.add(pass);
		buttonPanel.add(swap);
		buttonPanel.add(resign);
		buttonPanel.add(play);		
		
		mainPanel.add(buttonPanel);
		mainPanel.add(gamePanel);
		
		add(mp, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		add(chat, BorderLayout.EAST);
		gui.setLoadingCursor(false);
		
	}
	
}
