package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import Utility.Loader;
import Utility.SButton;
import WordFeud.Game;
import WordFeud.GameStone;
import WordFeud.Tile;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements MouseListener {

	private SButton 	pass, swap, resign, play;
	private ChatPanel 	cp;
	private MenuPanel 	mp;
	private Game 		game;
	@SuppressWarnings("unused")
	private GUI 		gui;
	private GameStone 	currentGameStone;
	
	public GamePanel(GUI gui){
		this.gui = gui;
		this.game = gui.getApplication().getSelectedGame();
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(null);
		this.setBackground(new Color(23, 26, 30));
		this.requestFocus();
		mp		= new MenuPanel(gui, new PlayerPanel(gui));
		mp.setPreferredSize(new Dimension(GUI.WIDTH, 30));
		cp	 	= new ChatPanel(gui, game);
		cp.setPreferredSize(new Dimension(250, GUI.HEIGHT));
		pass 	= new SButton("Pass", SButton.CYAN, 150, 40);
		swap 	= new SButton("Swap",  SButton.YELLOW, 150, 40);
		resign 	= new SButton("Resign",  SButton.RED, 150, 40);
		play	= new SButton("Play",  SButton.GREEN, 150, 40);
		
		// The buttons
		JPanel bp = new JPanel();
		bp.setLayout(new GridLayout(4, 1, 0, 10));
		bp.setOpaque(false);
		bp.add(pass);
		bp.add(swap);
		bp.add(resign);
		bp.add(play);		
		
		add(mp);
		mp.setBounds(0, 0, mp.getPreferredSize().width, mp.getPreferredSize().height);
		add(bp);
		bp.setBounds(10, 50, bp.getPreferredSize().width, bp.getPreferredSize().height);
		add(cp);
		cp.setBounds(GUI.WIDTH - cp.getPreferredSize().width - 10, 10, cp.getPreferredSize().width, cp.getPreferredSize().height);
		
		
		HashMap<String, Tile> tiles = game.getMyField().getTiles();
		
		int xPos = bp.getPreferredSize().width + 20;
		int yPos = 50;
		for(int y = 1; y < 16; y++) {
			for(int x = 1; x < 16; x++) {
				Tile tile = tiles.get(x + "," + y);
				add(tile);
				tile.addMouseListener(this);
				tile.setPickablity(false);
				tile.setBounds(xPos, yPos, 32, 32);
				if(x == 1 && y == 1) {tile.setGameStone(new GameStone(5, 'W'));}
				xPos += 33;
			}
			xPos = bp.getPreferredSize().width + 20;
			yPos += 33;
		}
		
		String letters 				= game.getGameStoneLetters();
		ArrayList<Character> chars 	= new ArrayList<Character>();
		System.out.println(letters);
		for(int i = 0; i < letters.length(); i++) {
			if(letters.charAt(i) == ',') {}
			else {
				chars.add(letters.charAt(i));
				System.out.println(letters.charAt(i));
			}
		}
		
		xPos = bp.getPreferredSize().width + 20;
		yPos = 550;
		for(int i = 0; i < 7; i++) {
			Tile tile = new Tile(i, 0);
			tile.setGameStone(new GameStone(Integer.parseInt(Loader.TILEVALUES.get(Character.toString(chars.get(i).charValue()))), chars.get(i).charValue()));
			tile.addMouseListener(this);
			add(tile);
			tile.setBounds(xPos, yPos, 32, 32);
			xPos += 33;
		}

	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		if(currentGameStone == null) {
			if(((Tile) e.getSource()).getGameStone() != null) {
				if(((Tile) e.getSource()).getPickablity()) {
					currentGameStone = ((Tile) e.getSource()).getGameStone();
					((Tile) e.getSource()).setGameStone(null);
				}
			}
		}
		else {
			if(((Tile) e.getSource()).getGameStone() == null) {
				((Tile) e.getSource()).setGameStone(currentGameStone);
				((Tile) e.getSource()).setPickablity(true);
				currentGameStone = null;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		
	}
	
}
