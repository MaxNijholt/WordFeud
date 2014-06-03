package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Utility.AScrollPane;
import Utility.DBCommunicator;
import Utility.Loader;
import Utility.SButton;
import Utility.SLabel;
import WordFeud.Game;
import WordFeud.GameStone;
import WordFeud.Tile;

/**
 * @author Stan van Heumen
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, MouseListener, MouseMotionListener, ActionListener, Observer {

	// Instance Variables
	private SButton 						pass, swap, resign, play, shuffle;
	private ChatPanel 						cp;
	private MenuPanel 						mp;
	private JPanel							bp;
	private Game 							game;
	private GUI 							gui;
	private GameStone 						currentGameStone;
	private boolean 						running;
	private Thread 							gameThread;
	private ArrayList<Tile> 				hand, field;
	private ArrayList<GameStone> 			stones;
	private int 							mouseX, mouseY;
	private JFrame 							questionFrame, swapFrame;
	private SLabel 							turn;
	private GameInfoPanel					gip=new GameInfoPanel();
//	private MasterThread mt;
	
	/**
	 * Constructor parameters: Gui gui<br>
	 * This constructor creates a GamePanel that allows you to play games in WordFeud<br>
	 * The GamePanel has got 2 grids (GameGrid and HandGrid)
	 */
	public GamePanel(GUI gui) {
//		mt = mt.getInstance();
//		mt.addObserver(this);
		init(gui);

			
		turn = new SLabel("", SLabel.CENTER);
		if(gui.getApplication().getMyTurn(gui.getApplication().getSelectedGame().getID())) {
			turn.setName("It's your turn");
		}
		else {
			turn.setName("It is your opponents turn");
		}
		//infopanel
		AScrollPane scoreBar = new AScrollPane(gip.getPreferredSize().width,
				gip.getPreferredSize().height, gip, false, true);
		
		add(mp);
		mp.setBounds(0, 0, mp.getPreferredSize().width, mp.getPreferredSize().height);
		mp.add(turn);
		add(bp);
		bp.setBounds(10, 50, bp.getPreferredSize().width, bp.getPreferredSize().height);
		add(scoreBar);
		scoreBar.setBounds(10, 320, 195, 315);
		add(cp);
		cp.setBounds(GUI.WIDTH - cp.getPreferredSize().width - 10, 10, cp.getPreferredSize().width, cp.getPreferredSize().height);
	}
	
	/**
	 * Private initialize method for the constructor of GamePanel
	 */
	private void init(GUI g) {
		// Default stuff
		setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		setLayout(null);
		setBackground(new Color(23, 26, 30));
		addMouseListener(this);
		addMouseMotionListener(this);
		
		// Initialize objects/primitives
		running 		= true;
		mouseX			= 0;
		mouseY			= 0;
		gui 			= g;
		game 			= gui.getApplication().getSelectedGame();
		gameThread 		= new Thread(this);
		hand 			= new ArrayList<Tile>();
		field 			= new ArrayList<Tile>();
		stones 			= new ArrayList<GameStone>();
		mp				= new MenuPanel(gui, "PlayerPanel");
		cp 				= new ChatPanel(gui, game);
		
		mp.setPreferredSize(new Dimension(GUI.WIDTH, 30));
		cp.setPreferredSize(new Dimension(250, GUI.HEIGHT));
		
		pass 			= new SButton("Pass", SButton.CYAN, 150, 40);
		swap 			= new SButton("Swap", SButton.YELLOW, 150, 40);
		resign 			= new SButton("Resign", SButton.RED, 150, 40);
		play 			= new SButton("Play", SButton.GREEN, 150, 40);
		shuffle 		= new SButton("Shuffle", SButton.PURPLE, 150, 40);
		
		pass.addActionListener(this);
		swap.addActionListener(this);
		resign.addActionListener(this);
		play.addActionListener(this);
		shuffle.addActionListener(this);
		
		ArrayList<SButton> buttons = mp.getAllButtons();
		for(SButton s:buttons) {s.addActionListener(this);}
		
		// Initialize ButtonPanel
		bp = new JPanel();
		bp.setLayout(new GridLayout(5, 1, 0, 10));
		bp.setOpaque(false);
		
		bp.add(pass);
		bp.add(swap);
		bp.add(resign);
		bp.add(play);
		bp.add(shuffle);
		
		//Initialize Game
		HashMap<String, Tile> tiles = game.getMyField().getTiles();
		for(int y = 1; y < 16; y++) {
			for(int x = 1; x < 16; x++) {
				Tile tile = tiles.get(x + "," + y);
				tile.setPickablity(false);
				field.add(tile);
			}
		}
		
		// Initialize Hand
		ArrayList<GameStone> currentGameStones = DBCommunicator.getHandLetters(game.getID(), DBCommunicator.requestInt("SELECT id from beurt WHERE spel_id = " + game.getID() + " AND account_naam = '" + gui.getApplication().getCurrentAccount().getUsername() + "' ORDER BY id DESC"), Loader.getGameStones("EN"));

		for(int i = 0; i < 7; i++) {
			Tile tile = new Tile(i + 1, -1);
			if(i < currentGameStones.size()) {
				if(currentGameStones.get(i) != null) {
					GameStone gs = currentGameStones.get(i);
					gs.setHand(true);
					tile.setGameStone(gs);
					stones.add(gs);
				}
			}
			hand.add(tile);
		}
		
		// Start the gameThread;
		gameThread.start();
	}

	/**
	 * Overridden run method from the Runnable interface, which allows this panel to update 60 frames per second
	 */
	public void run() {
		System.out.println("[GAMEPANEL] Game_Thread has started");
		while(running) {
			repaint();
			try {Thread.sleep(1000 / 60);}
			catch(InterruptedException e) {e.printStackTrace();}
		}
		System.out.println("[GAMEPANEL] Game_Thread has stoppped");
	}

	/**
	 * Overridden paintComponent method from JComponent
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		for(Tile t:field) {
			g2d.drawImage(t.getImage(), (t.getXPos() * 33) + 180, (t.getYPos() * 33) + 10, null);
		}
		for(Tile t:hand) {
			g2d.drawImage(t.getImage(), (t.getXPos() * 33) + 180, (t.getYPos() * 33) + 580, null);
		}
		if(currentGameStone != null) {
			g2d.drawImage(currentGameStone.getImage(), mouseX - (currentGameStone.getImage().getWidth() / 2), mouseY - (currentGameStone.getImage().getHeight() / 2), null);
		}
		g2d.dispose();
	}
	
	// Mouse Event
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			// Check for board grid
			for(Tile t:field) {
				if((e.getX() >= (t.getXPos() * 33) + 180) && (e.getX() <= (t.getXPos() * 33) + 180 + 32) && (e.getY() >= (t.getYPos() * 33) + 10) && (e.getY() <= (t.getYPos() * 33) + 10 + 32)) {
					if (currentGameStone == null) {
						if(t.getPickablity()) {
							currentGameStone = t.getGameStone();
							t.setPickablity(false);
							gip.emptyPanel();
							gip.updateInfo(gui.removeGameStone(t.getXPos() + "," + t.getYPos(), true));
							t.setGameStone(null);
						}
					}
					else {
						if(t.getGameStone() == null) {
							if(currentGameStone.getLetter() == '?') {
								// Check for the question mark (?)
								questionFrame 	= new JFrame();
								JPanel swapPanel	= new JPanel();
								swapPanel.setLayout(new GridLayout(6, 5));
								questionFrame.setResizable(false);
								questionFrame.setTitle(GUI.TITLE);
								questionFrame.setContentPane(swapPanel);

								for(char i = 'A'; i <= 'Z'; i++) {
									SButton s = new SButton(Character.toString(i), SButton.WHITE, 50, 50);
									s.addActionListener(this);
									s.setColors(Color.WHITE, new Color(230, 230, 230), new Color(200, 200, 200));
									s.setTextColor(Color.BLACK);
									swapPanel.add(s);
								}
								questionFrame.addWindowListener(new WindowAdapter() {
						            //
						            // Invoked when a window is de-activated.
						            //
						            public void windowDeactivated(WindowEvent e) {
						                questionFrame.dispose();
						            }
						 
						        });
								questionFrame.pack();
								questionFrame.setLocationRelativeTo(null);
								questionFrame.setVisible(true);
								
								t.setGameStone(currentGameStone);
								t.setPickablity(true);
							}
							else {
								t.setGameStone(currentGameStone);
								t.setPickablity(true);
							}
							gip.emptyPanel();
							gip.updateInfo( gui.layGameStone(currentGameStone, (t.getXPos() + "," + t.getYPos())));
							
							currentGameStone = null;
						}
					}
				}
			}

			// Check for hand grid
			for(Tile t : hand) {
				if((e.getX() >= (t.getXPos() * 33) + 180) && (e.getX() <= (t.getXPos() * 33) + 180 + 32) && (e.getY() >= (t.getYPos() * 33) + 580) && (e.getY() <= (t.getYPos() * 33) + 580 + 32)) {
					if(currentGameStone == null) {
						if (t.getPickablity()) {

							currentGameStone = t.getGameStone();
							t.setGameStone(null);
						}
					}
					else {
						if(t.getGameStone() == null) {
							if(currentGameStone.getValue() == 0) {
								currentGameStone.setLetter("?");
							}
							t.setGameStone(currentGameStone);
							currentGameStone = null;
						}
					}
				}
			}
		}
	}

	// Action event
	public void actionPerformed(ActionEvent e) {
		for(char i = 'A'; i <= 'Z'; i++) {
			if(e.getActionCommand().equals(Character.toString(i))) {
				for(Tile t:field) {
					if(t.getGameStone() != null) {
						if(t.getGameStone().getLetter() == '?') {
							t.getGameStone().setLetter(Character.toString(i));
							questionFrame.dispose();
						}
					}
				}
			}
		}
		
		// Fix for thread staying on
		if(e.getActionCommand().equals("Your settings") || e.getActionCommand().equals("User stats") || e.getActionCommand().equals("> Player") || e.getActionCommand().equals("> Administrator") || e.getActionCommand().equals("> Moderator") || e.getActionCommand().equals("> Spectator") || e.getActionCommand().equals("Log Out") || e.getActionCommand().equals("Back")) {
			turnOffThreads();
		}
		
		// Shuffle
		if(e.getSource().equals(shuffle)) {
			for (int i = 0; i < field.size(); i++) {
				if (field.get(i).getGameStone() != null) {
					if (field.get(i).getGameStone().getHand()) {
						game.removeGameStone(field.get(i).getXPos() + "," + field.get(i).getYPos(), true);
						field.get(i).setGameStone(null);
					}
				}
			}
			Collections.shuffle(stones);
			for(int i = 0; i < hand.size(); i++) {
				try{
					hand.get(i).setGameStone(stones.get(i));
				}
				catch(IndexOutOfBoundsException a){
					
				}
			}
			currentGameStone = null;
		}
		
		// Swap
		if(e.getSource().equals(swap)) {
			if(gui.getApplication().getMyTurn(gui.getApplication().getSelectedGame().getID())){
				for(int i = 0; i < field.size(); i++) {
					if(field.get(i).getGameStone() != null) {
						if(field.get(i).getGameStone().getHand()) {
							game.removeGameStone(field.get(i).getXPos() + "," + field.get(i).getYPos(), true);
							field.get(i).setGameStone(null);
						}
					}
				}
				
				for(int i = 0; i < hand.size(); i++) {
					try{hand.get(i).setGameStone(stones.get(i));}
					catch(IndexOutOfBoundsException a){}
				}
				final ArrayList<Integer> swapStones = new ArrayList<Integer>();
				swapFrame 			= new JFrame();
				JPanel swapPanel	= new JPanel();
				swapFrame.addWindowListener(new WindowAdapter() {
		            //
		            // Invoked when a window is de-activated.
		            //
		            public void windowDeactivated(WindowEvent e) {
		            	swapFrame.dispose();
		            }
		 
		        });
				swapPanel.setLayout(null);
				swapPanel.setPreferredSize(new Dimension(20 + (stones.size() * stones.get(0).getImage().getWidth()), 60 + stones.get(0).getImage().getHeight()));
				swapFrame.setResizable(false);
				swapFrame.setTitle(GUI.TITLE);
				swapFrame.setContentPane(swapPanel);
				
				SButton swap 	= new SButton("Swap", SButton.GREY, (swapPanel.getPreferredSize().width / 2) - 15, 30);
				swap.setBounds(10, stones.get(0).getImage().getHeight() + 20, swap.getPreferredSize().width, swap.getPreferredSize().height);
				swap.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Swap
						if(swapStones.size() >= 1) {
							boolean swapped = gui.getApplication().swapGameStones(swapStones);
							if(swapped){
								swapFrame.dispose();
								turnOffThreads();
								gui.getApplication().selectGame(gui.getApplication().getSelectedGame().getID());
							}
							else{
								swapFrame.dispose();
								JOptionPane.showMessageDialog(null, "There are not enough stones left", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
				
				SButton cancel 	= new SButton("Cancel", SButton.GREY, (swapPanel.getPreferredSize().width / 2) - 15, 30);
				cancel.setBounds(swapPanel.getPreferredSize().width - cancel.getPreferredSize().width - 10, stones.get(0).getImage().getHeight() + 20, swap.getPreferredSize().width, swap.getPreferredSize().height);
				cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						swapFrame.dispose();
					}
				});
			
				swapPanel.setBackground(getBackground());
				swapFrame.setIconImage(Loader.ICON);
			
				for(int i = 0; i < stones.size(); i++) {
					final SLabel s = new SLabel(Character.toString(stones.get(i).getLetter()), SLabel.CENTER, 32, 32);
					s.setBounds(10 + (i * 32), 10, 32, 32);
					s.setName(Character.toString(stones.get(i).getLetter()));
					s.drawBackground(true);
					s.changeTextColor(Color.BLACK, Color.WHITE);
					s.addMouseListener(new MouseListener() {
						public void mouseClicked(MouseEvent e) 	{
							if(s.getBackgroundColor().equals(Color.WHITE)) {
								s.changeTextColor(Color.BLACK, Color.YELLOW);
								for(GameStone gs:stones) {
									if(Character.toString(gs.getLetter()).equals(s.getName())) {
										if(!swapStones.contains(gs.getID())) {
											swapStones.add(gs.getID());
											break;
										}
									}
								}
							}
							else {
								s.changeTextColor(Color.BLACK, Color.WHITE);
								ArrayList<Integer> copyStones = new ArrayList<Integer>();
								for(Integer i:swapStones) {
									copyStones.add(i);
								}
								boolean gameStoneFound = false;
								for(GameStone gs:stones) {
									if(Character.toString(gs.getLetter()).equals(s.getName()) && !gameStoneFound) {
										for(Integer i:copyStones) {
											if(i == gs.getID()) {
												swapStones.remove(i);
												gameStoneFound = true;
												System.out.println("You removed a gameStone");
												break;
											}
										}
									}
								}
							}
							String total = "";
							for(int i = 0; i < swapStones.size(); i++) {
								total += swapStones.get(i) + ":";
							}
							System.out.println(total);
							s.repaint();
						}
						public void mouseEntered(MouseEvent e) 	{}
						public void mouseExited(MouseEvent e) 	{}
						public void mousePressed(MouseEvent e) 	{}
						public void mouseReleased(MouseEvent e) {}
					});
					swapPanel.add(s);
				}
				
				
				swapPanel.add(swap);
				swapPanel.add(cancel);
			
				swapFrame.pack();
				swapFrame.setLocationRelativeTo(null);
				swapFrame.setVisible(true);
			}
			else{
				JOptionPane.showMessageDialog(null, "It is not your turn!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// Play
		if(e.getSource().equals(play)) {
			if(gui.getApplication().getMyTurn(gui.getApplication().getSelectedGame().getID())){
				System.out.println(turn.getName());
				System.out.println("pleh");
				if(!turn.getName().equals("It is your opponents turn")){
					ArrayList<String> word = gui.playWord();
					gip.deniedReqeust(word);
					for(int i = 0; i < field.size(); i++) {
						if(field.get(i).getGameStone() != null) {
							if(field.get(i).getGameStone().getHand()) {
								game.removeGameStone(field.get(i).getXPos() + "," + field.get(i).getYPos(), false);
								field.get(i).setGameStone(null);
							}
						}
					}
					for(int i = 0; i < hand.size(); i++) {
						try{
							hand.get(i).setGameStone(stones.get(i));
						}
						catch(IndexOutOfBoundsException a){
							
						}
					}
					currentGameStone = null;
					
					if(word == null) {
						turnOffThreads();
						if(gui.getApplication().getEnd(gui.getApplication().getSelectedGame().getID())){
							gui.getApplication().spectateGame(gui.getApplication().getSelectedGame().getID());
						}
						else{
							gui.getApplication().selectGame(gui.getApplication().getSelectedGame().getID());
						}
					}
				}
				else{
					turnOffThreads();
					gui.getApplication().selectGame(gui.getApplication().getSelectedGame().getID());
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "It is not your turn!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// Resign
		if(e.getSource().equals(resign)) {
			if(gui.getApplication().getMyTurn(gui.getApplication().getSelectedGame().getID())){
				turnOffThreads();
				game.resign();
				gui.getApplication().spectateGame(gui.getApplication().getSelectedGame().getID());
			}
			else{
				JOptionPane.showMessageDialog(null, "It is not your turn!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		// Pass
		if(e.getSource().equals(pass)) {
			if(gui.getApplication().getMyTurn(gui.getApplication().getSelectedGame().getID())){
				turnOffThreads();
				gui.pass();
				if(gui.getApplication().getEnd(gui.getApplication().getSelectedGame().getID())){
					gui.getApplication().spectateGame(gui.getApplication().getSelectedGame().getID());
				}
				else{
					gui.getApplication().selectGame(gui.getApplication().getSelectedGame().getID());
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "It is not your turn!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	// Mouse moved event
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	// Unused Mouse Events
	public void mouseClicked(MouseEvent e) 	{}
	public void mouseEntered(MouseEvent e) 	{}
	public void mouseExited(MouseEvent e)	{}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e)	{}

	// Setters
	public void turnOffThreads() 		{cp.getChat().closeThread(); running = false;}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("[GamePanel] update revalidate");
//		revalidate();
	}
}
