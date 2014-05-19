package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utility.Loader;
import Utility.SButton;
import WordFeud.Game;
import WordFeud.GameStone;
import WordFeud.Tile;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private SButton 	pass, swap, resign, play;
	private ChatPanel 	chat;
	private MenuPanel 	mp;
	private Game 		game;
	@SuppressWarnings("unused")
	private GUI 		gui;
	
	public GamePanel(GUI gui){
		this.gui = gui;
		this.game = gui.getApplication().getSelectedGame();
		gui.setLoadingCursor(true);
		
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(94, 94, 94));
		mp		= new MenuPanel(gui, new PlayerPanel(gui));
		mp.setPreferredSize(new Dimension(GUI.WIDTH, 30));
		mp.setMinimumSize(new Dimension(GUI.WIDTH, 30));
		mp.setMaximumSize(new Dimension(GUI.WIDTH, 30));
		chat 	= new ChatPanel(gui, game);
		chat.setPreferredSize(new Dimension(250, GUI.HEIGHT));
		chat.setMinimumSize(new Dimension(250, GUI.HEIGHT));
		chat.setMaximumSize(new Dimension(250, GUI.HEIGHT));
		pass 	= new SButton("Pass", SButton.CYAN, 150, 40);
		swap 	= new SButton("Swap",  SButton.YELLOW, 150, 40);
		resign 	= new SButton("Resign",  SButton.RED, 150, 40);
		play	= new SButton("Play",  SButton.GREEN, 150, 40);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1, 0, 10));
		buttonPanel.setOpaque(false);
		
		JPanel wrapButton = new JPanel();
		wrapButton.setLayout(new GridBagLayout());
		wrapButton.setPreferredSize(new Dimension(200, GUI.HEIGHT));
		wrapButton.setMinimumSize(new Dimension(200, GUI.HEIGHT));
		wrapButton.setMaximumSize(new Dimension(200, GUI.HEIGHT));
		wrapButton.setOpaque(false);
		wrapButton.add(buttonPanel);

		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(null);
		gamePanel.setBorder(new EmptyBorder(10, 0, 10, 0));
		gamePanel.setOpaque(false);
		gamePanel.setPreferredSize(new Dimension(550, 550));
		
		HashMap<String, Tile> tiles = game.getMyField().getTiles();
		int xPos = 10;
		int yPos = 10;
		for(int y = 1; y < 16; y++) {
			for(int x = 1; x < 16; x++) {
				Tile tile = tiles.get(x + "," + y);
				gamePanel.add(tile);
				tile.setBounds(xPos, yPos, 32, 32);
				if(x == 1 && y == 1) {tile.setGameStone(new GameStone(1, 'A'));}
				xPos += 33;
			}
			xPos = 10;
			yPos += 33;
		}
		yPos += 20;
		for(int i = 0; i < 7; i++) {
			Tile tile = new Tile(i, 0);
			tile.setGameStone(new GameStone(Integer.parseInt(Loader.TILEVALUES.get("F")), 'F'));
			gamePanel.add(tile);
			tile.setBounds(xPos, yPos, 32, 32);
			xPos += 33;
		}
		
		buttonPanel.add(pass);
		buttonPanel.add(swap);
		buttonPanel.add(resign);
		buttonPanel.add(play);		
		
		add(mp, BorderLayout.NORTH);
		add(wrapButton, BorderLayout.WEST);
		add(gamePanel, BorderLayout.CENTER);
		add(chat, BorderLayout.EAST);
		gui.setLoadingCursor(false);
		
	}
	
}
