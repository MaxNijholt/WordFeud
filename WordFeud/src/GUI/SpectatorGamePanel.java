package GUI;

import java.awt.Color;
import java.awt.Dimension;
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

	private SButton 	next, previous, back;
	private Spectator 	spectate;
	private GUI 		gui;
	private	int 		gameID, turn;
	private ArrayList<Tile> hand 	= new ArrayList<Tile>();
	private ArrayList<Tile> field	= new ArrayList<Tile>();
	
	public SpectatorGamePanel(GUI gui, int gameID){
		this.gui = gui;
		this.gameID = gameID;
		spectate = new Spectator(gameID);
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(null);
		this.setBackground(new Color(23, 26, 30));
		this.requestFocus();
		next 	= new SButton("Next turn", SButton.CYAN, 150, 40);
		previous 	= new SButton("Previous turn", SButton.YELLOW, 150, 40);
		back 	= new SButton("Back", SButton.RED, 150, 40);
		
		next.addActionListener(this);
		previous.addActionListener(this);
		back.addActionListener(this);
		
		// The buttons
		JPanel bp = new JPanel();
		bp.setLayout(new GridLayout(5, 1, 0, 10));
		bp.setOpaque(false);
		bp.add(next);
		bp.add(previous);
		bp.add(back);
		
		
		HashMap<String, Tile> tiles = spectate.getMyField().getTiles();
		turn = spectate.getLastTurn();
		
		int xPos = bp.getPreferredSize().width + 20;
		int yPos = 50;
		for(int y = 1; y < 16; y++) {
			for(int x = 1; x < 16; x++) {
//				if(tile.getTurn == turn){
					Tile tile = tiles.get(x + "," + y);
					field.add(tile);
					tile.setPickablity(false);
					xPos += 33;
//				}
			}
			xPos = bp.getPreferredSize().width + 20;
			yPos += 33;
		}
		
		String letters 				= spectate.getLastTurnLetters();
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
			field.add(tile);
			tile.setPickablity(false);
			xPos += 33;
		}

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(next)) {
			if(turn != spectate.getLastTurn()){
				turn++;
			}
		}
		if(e.getSource().equals(previous)) {
			if(turn != 2){
				turn--;
			}
			
		}
		if(e.getSource().equals(back)) {
//			gui.switchPanel(new SpectatorPanel(gui));
		}

	}
	
}
