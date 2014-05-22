package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class GamePanel extends JPanel implements MouseListener, ActionListener {

	private SButton 	pass, swap, resign, play, shuffle;
	private ChatPanel 	cp;
	private MenuPanel 	mp;
	private Game 		game;
	@SuppressWarnings("unused")
	private GUI 		gui;
	private GameStone 	currentGameStone;
	private ArrayList<Tile> hand = new ArrayList<Tile>();
	
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
		swap 	= new SButton("Swap", SButton.YELLOW, 150, 40);
		resign 	= new SButton("Resign", SButton.RED, 150, 40);
		play	= new SButton("Play", SButton.GREEN, 150, 40);
		shuffle	= new SButton("Shuffle", SButton.PURPLE, 150, 40);
		
		pass.addActionListener(this);
		swap.addActionListener(this);
		resign.addActionListener(this);
		play.addActionListener(this);
		shuffle.addActionListener(this);
		
		// The buttons
		JPanel bp = new JPanel();
		bp.setLayout(new GridLayout(5, 1, 0, 10));
		bp.setOpaque(false);
		bp.add(pass);
		bp.add(swap);
		bp.add(resign);
		bp.add(play);
		bp.add(shuffle);
		
		add(mp);
		mp.setBounds(0, 0, mp.getPreferredSize().width, mp.getPreferredSize().height);
		add(bp);
		bp.setBounds(10, 50, bp.getPreferredSize().width, bp.getPreferredSize().height);
		add(cp);
		cp.setBounds(GUI.WIDTH - cp.getPreferredSize().width - 10, 10, cp.getPreferredSize().width, cp.getPreferredSize().height);
		
		
		HashMap<String, Tile> tiles = game.getMyField().getTiles();
		System.out.println(tiles);
		
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
		
		ArrayList<Integer> gameStones 		= game.getGameStones();
		HashMap<Integer, Character> chars 	= game.getStoneLetters();
		
		ArrayList<GameStone> currentGameStones = new ArrayList<GameStone>();
		
		for(int i = 0; i < gameStones.size(); i++) {
			currentGameStones.add(new GameStone(Integer.parseInt(Loader.TILEVALUES.get(chars.get(gameStones.get(i)).toString())), chars.get(gameStones.get(i)).charValue()));
		}
		
		xPos = bp.getPreferredSize().width + 20;
		yPos = 550;
		for(int i = 0; i < 7; i++) {
			Tile tile = new Tile(i, 0);
			tile.setGameStone(currentGameStones.get(i));
			tile.addMouseListener(this);
			hand.add(tile);
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

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(shuffle)) {
			game.shuffle();
			
			ArrayList<Integer> gameStones 		= game.getGameStones();
			HashMap<Integer, Character> chars 	= game.getStoneLetters();
			
			for(int i = 0; i < gameStones.size(); i++) {
				GameStone s = new GameStone(Integer.parseInt(Loader.TILEVALUES.get(chars.get(gameStones.get(i)).toString())), chars.get(gameStones.get(i)).charValue());
				hand.get(i).setGameStone(s);
			}
			currentGameStone = null;
			repaint();
		}
	}
	
}
