package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import Utility.SButton;
import WordFeud.GameStone;
import WordFeud.Tile;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private GUI gui;
	private SButton pass, swap, resign, play;
	private ChatPanel chat;
	
	public GamePanel(GUI gui){
		this.gui = gui;
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0));
		this.setBackground(new Color(29, 29, 29));
		chat = new ChatPanel();
		
		pass 	= new SButton("Pass", SButton.CYAN, 60, 60);
		swap 	= new SButton("Swap",  SButton.YELLOW, 60, 60);
		resign 	= new SButton("Resign",  SButton.RED, 60, 60);
		play	= new SButton("Play",  SButton.GREEN, 60, 60);
		pass.setArc(60);
		swap.setArc(60);
		resign.setArc(60);
		play.setArc(60);
		
		JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainPanel.setOpaque(false);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1, 0, 10));
		buttonPanel.setOpaque(false);
		
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(15, 15, 3, 3));
		gamePanel.setOpaque(false);
		
		for(int y = 0; y < 15; y++) {
			for(int x = 0; x < 15; x++) {
				Tile tile = new Tile(x, y);
				if(y == 0 && x == 0) {tile.setGameStone(new GameStone(1, 'E'));}
				if(y == 4 && x == 6) {tile.setGameStone(new GameStone(5, 'W'));}
				gamePanel.add(tile);
			}
		}
		
		buttonPanel.add(pass);
		buttonPanel.add(swap);
		buttonPanel.add(resign);
		buttonPanel.add(play);		
		
		mainPanel.add(buttonPanel);
		mainPanel.add(gamePanel);
		mainPanel.add(chat);
		
		add(mainPanel);
		
	}
	
}
