package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import javax.swing.JPanel;

import Utility.Loader;
import Utility.SButton;
import WordFeud.Game;
import WordFeud.GameStone;
import WordFeud.Tile;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements MouseListener, MouseMotionListener, Runnable {

	private SButton 	pass, swap, resign, play;
	private ChatPanel 	cp;
	private MenuPanel 	mp;
	private Game 		game;
	@SuppressWarnings("unused")
	private GUI 		gui;
	private GameStone 	currentGameStone;
	private int 		mouseX, mouseY;
	private Thread		thread;
	
	public GamePanel(GUI gui){
		this.gui = gui;
		this.game = gui.getApplication().getSelectedGame();
		gui.setLoadingCursor(true);
		this.addMouseMotionListener(this);
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(null);
		thread = new Thread(this);
		thread.start();
		this.setBackground(new Color(94, 94, 94));
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
				tile.setBounds(xPos, yPos, 32, 32);
				if(x == 1 && y == 1) {tile.setGameStone(new GameStone(1, 'A'));}
				xPos += 33;
			}
			xPos = bp.getPreferredSize().width + 20;
			yPos += 33;
		}
		xPos = bp.getPreferredSize().width + 20;
		yPos = 550;
		for(int i = 0; i < 7; i++) {
			Tile tile = new Tile(i, 0);
			tile.setGameStone(new GameStone(Integer.parseInt(Loader.TILEVALUES.get("F")), 'F'));
			tile.addMouseListener(this);
			add(tile);
			tile.setBounds(xPos, yPos, 32, 32);
			xPos += 33;
		}

	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if(currentGameStone != null) {
			g.drawImage(currentGameStone.getImage(), mouseX, mouseY, null);
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
				currentGameStone = ((Tile) e.getSource()).getGameStone();
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		
	}
	// Mouse motion
	public void mouseDragged(MouseEvent e) {
		/*if(currentGameStone != null) {
			Point locOnScreen = e.getLocationOnScreen();
			
			int x = locOnScreen.x - initLocOnScreen.x + initLoc.x - 16;
			int y = locOnScreen.y - initLocOnScreen.y + initLoc.y - 16;
			currentGameStone.setLocation(x, y);
		}*/
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX() - 16;
		mouseY = e.getY() - 16;
		repaint();
	}

	@Override
	public void run() {
		while(true) {
			repaint();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
