package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import Utility.Loader;
import Utility.SButton;
import WordFeud.GameStone;
import WordFeud.Spectator;
import WordFeud.Tile;

@SuppressWarnings("serial")
public class SpectatorGamePanel extends JPanel implements ActionListener {

	private SButton next, previous, back;
	private Spectator spectate;
	private MenuPanel mp;
	private JPanel bp;
	private GUI gui;
	private int gameID, turn, xPos, yPos;
	private ArrayList<Tile> hand = new ArrayList<Tile>();
	private ArrayList<Tile> opponentHand = new ArrayList<Tile>();
	private ArrayList<Tile> field = new ArrayList<Tile>();
	private HashMap<String, Tile> tiles;

	public SpectatorGamePanel(GUI gui, int gameID) {
		this.gui = gui;
		this.gameID = gameID;
		spectate = new Spectator(gameID);
		if (gui.getApplication().getCurrentAccount() == null) {
			back = new SButton("Back", SButton.GREY, 220, 40);
			back.setPreferredSize(new Dimension(GUI.WIDTH, 30));
		} else {
			this.mp = new MenuPanel(gui, new SpectatorCompetitionsPanel(gui));
			mp.setPreferredSize(new Dimension(GUI.WIDTH, 30));
		}
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(null);
		this.setBackground(new Color(23, 26, 30));
		this.requestFocus();
		next = new SButton("Next turn", SButton.CYAN, 150, 40);
		previous = new SButton("Previous turn", SButton.RED, 150, 40);

		next.addActionListener(this);
		previous.addActionListener(this);

		// The buttons
		bp = new JPanel();
		bp.setLayout(new GridLayout(5, 1, 0, 10));
		bp.setOpaque(false);
		bp.add(next);
		bp.add(previous);

		add(bp);
		bp.setBounds(10, 50, bp.getPreferredSize().width,
				bp.getPreferredSize().height);

		if (back != null) {
			back.addActionListener(this);
			add(back);
			back.setBounds(0, 0, back.getPreferredSize().width,
					back.getPreferredSize().height);
		} else {
			add(mp);
			mp.setBounds(0, 0, mp.getPreferredSize().width,
					mp.getPreferredSize().height);
		}

		tiles = spectate.getMyField().getTiles();
		turn = spectate.getLastTurn();

		xPos = bp.getPreferredSize().width + 20;
		yPos = 50;
		for (int y = 1; y < 16; y++) {
			for (int x = 1; x < 16; x++) {

				if (tiles.get(x + "," + y).getGameStone() != null) {
					if (tiles.get(x + "," + y).getGameStone().getTurn() <= turn) {
						Tile tile = tiles.get(x + "," + y);
						field.add(tile);
						tile.setPickablity(false);
						xPos += 33;
					} else {
						tiles.get(x + "," + y).setGameStone(null);
						Tile tile = tiles.get(x + "," + y);
						field.add(tile);
						tile.setPickablity(false);
						xPos += 33;
					}
				} else {
					Tile tile = tiles.get(x + "," + y);
					field.add(tile);
					tile.setPickablity(false);
					xPos += 33;
				}

			}
			xPos = bp.getPreferredSize().width + 20;
			yPos += 33;
		}

		ArrayList<GameStone> currentGameStones = spectate.getHand(turn);

		for (int i = 0; i < 7; i++) {
			Tile tile = new Tile(i + 1, -1);
			if (i < currentGameStones.size()) {
				if (currentGameStones.get(i) != null) {
					tile.setGameStone(currentGameStones.get(i));
				}
			}
			hand.add(tile);
		}
		
		ArrayList<GameStone> opponentGameStones = spectate.getHand(turn);

		for (int i = 0; i < 7; i++) {
			Tile tile = new Tile(i + 1, -1);
			if (i < opponentGameStones.size()) {
				if (opponentGameStones.get(i) != null) {
					tile.setGameStone(opponentGameStones.get(i));
				}
			}
			opponentHand.add(tile);
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		for (Tile t : field) {
			g2d.drawImage(t.getImage(), (t.getXPos() * 33) + 180,
					(t.getYPos() * 33) + 10, null);
		}
		for (Tile t : hand) {
			g2d.drawImage(t.getImage(), (t.getXPos() * 33) + 180,
					(t.getYPos() * 33) + 580, null);
		}
		for (Tile t : opponentHand) {
			g2d.drawImage(t.getImage(), (t.getXPos() * 33) + (gui.getWidth() - 180),
					(t.getYPos() * 33) + 580, null);
		}
		g2d.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(next)) {
			if (turn != spectate.getLastTurn()) {
				turn++;
				spectate.setTurn(turn);
				tiles = spectate.getMyField().getTiles();

				xPos = bp.getPreferredSize().width + 20;
				yPos = 50;
				for (int y = 1; y < 16; y++) {
					for (int x = 1; x < 16; x++) {
						if (tiles.get(x + "," + y).getGameStone() != null) {
							if (tiles.get(x + "," + y).getGameStone().getTurn() <= turn) {
								Tile tile = tiles.get(x + "," + y);
								field.add(tile);
								tile.setPickablity(false);
								xPos += 33;
							} else {
								tiles.get(x + "," + y).setGameStone(null);
								Tile tile = tiles.get(x + "," + y);
								field.add(tile);
								tile.setPickablity(false);
								xPos += 33;
							}
						} else {
							Tile tile = tiles.get(x + "," + y);
							field.add(tile);
							tile.setPickablity(false);
							xPos += 33;
						}
					}
					xPos = bp.getPreferredSize().width + 20;
					yPos += 33;
				}

				ArrayList<GameStone> currentGameStones = spectate.getHand(turn);

				for (int i = 0; i < 7; i++) {
					Tile tile = new Tile(i + 1, -1);
					if (i < currentGameStones.size()) {
						if (currentGameStones.get(i) != null) {
							tile.setGameStone(currentGameStones.get(i));
						}
					}
					hand.add(tile);
				}

				repaint();
			}
		}
		if (e.getSource().equals(previous)) {
			if (turn >= 3) {
				turn--;
				spectate.setTurn(turn);
				tiles = spectate.getMyField().getTiles();

				xPos = bp.getPreferredSize().width + 20;
				yPos = 50;
				for (int y = 1; y < 16; y++) {
					for (int x = 1; x < 16; x++) {
						if (tiles.get(x + "," + y).getGameStone() != null) {
							if (tiles.get(x + "," + y).getGameStone().getTurn() <= turn) {
								Tile tile = tiles.get(x + "," + y);
								field.add(tile);
								tile.setPickablity(false);
								xPos += 33;
							} else {
								tiles.get(x + "," + y).setGameStone(null);
								Tile tile = tiles.get(x + "," + y);
								field.add(tile);
								tile.setPickablity(false);
								xPos += 33;
							}
						} else {
							Tile tile = tiles.get(x + "," + y);
							field.add(tile);
							tile.setPickablity(false);
							xPos += 33;
						}
					}
					xPos = bp.getPreferredSize().width + 20;
					yPos += 33;
				}

				ArrayList<GameStone> currentGameStones = spectate.getHand(turn);

				for (int i = 0; i < 7; i++) {
					Tile tile = new Tile(i + 1, -1);
					if (i < currentGameStones.size()) {
						if (currentGameStones.get(i) != null) {
							tile.setGameStone(currentGameStones.get(i));
						}
					}
					hand.add(tile);
				}

				repaint();
			}

		}
		if (e.getSource().equals(back)) {
			gui.switchPanel(new SpectatorPanel(gui, spectate.getCompID(gameID)));
		}

	}

}
