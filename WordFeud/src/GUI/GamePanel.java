package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Utility.DBCommunicator;
import Utility.Loader;
import Utility.SButton;
import WordFeud.Game;
import WordFeud.GameStone;
import WordFeud.Tile;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, MouseListener,
		MouseMotionListener, ActionListener {

	private SButton pass, swap, resign, play, shuffle;
	private ChatPanel cp;
	private MenuPanel mp;
	private JLabel score=new JLabel();
	private Game game;
	private GUI gui;
	private GameStone currentGameStone;
	private boolean running = true;
	private Thread thread = new Thread(this);
	private ArrayList<Tile> hand = new ArrayList<Tile>();
	private ArrayList<Tile> field = new ArrayList<Tile>();
	private int mouseX, mouseY, turnScore=0;

	public GamePanel(GUI gui) {
		this.gui = gui;
		this.game = gui.getApplication().getSelectedGame();
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(null);
		this.setBackground(new Color(23, 26, 30));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		mp = new MenuPanel(gui, new PlayerPanel(gui));
		mp.setPreferredSize(new Dimension(GUI.WIDTH, 30));
		cp = new ChatPanel(gui, game);
		cp.setPreferredSize(new Dimension(250, GUI.HEIGHT));
		pass = new SButton("Pass", SButton.CYAN, 150, 40);
		swap = new SButton("Swap", SButton.YELLOW, 150, 40);
		resign = new SButton("Resign", SButton.RED, 150, 40);
		play = new SButton("Play", SButton.GREEN, 150, 40);
		shuffle = new SButton("Shuffle", SButton.PURPLE, 150, 40);

		pass.addActionListener(this);
		swap.addActionListener(this);
		resign.addActionListener(this);
		play.addActionListener(this);
		shuffle.addActionListener(this);

		// MenuPanel thread not stopping glitch fix:
		mp.getBackButton().addActionListener(this);

		// The buttons
		JPanel bp = new JPanel();
		bp.setLayout(new GridLayout(5, 1, 0, 10));
		bp.setOpaque(false);
		bp.add(pass);
		bp.add(swap);
		bp.add(resign);
		bp.add(play);
		bp.add(shuffle);

		// infopanel

		score.setText("Your turn score will be: 0");
		score.setOpaque(true);
		score.setBackground(Color.GREEN);
		score.setFont(new Font("Arial", Font.BOLD, 14));
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(5, 1, 0, 10));
		infoPanel.setPreferredSize(new Dimension(180, 215));
		infoPanel.setBackground(new Color(33, 36, 40));
		infoPanel.add(score);

		add(mp);
		mp.setBounds(0, 0, mp.getPreferredSize().width,
				mp.getPreferredSize().height);
		add(bp);
		bp.setBounds(10, 50, bp.getPreferredSize().width,
				bp.getPreferredSize().height);
		add(infoPanel);
		infoPanel.setBounds(10, 320, infoPanel.getPreferredSize().width, infoPanel.getPreferredSize().height);
		add(cp);
		cp.setBounds(GUI.WIDTH - cp.getPreferredSize().width - 10, 10,
				cp.getPreferredSize().width, cp.getPreferredSize().height);

		HashMap<String, Tile> tiles = game.getMyField().getTiles();

		for (int y = 1; y < 16; y++)
		{
			for (int x = 1; x < 16; x++)
			{
				Tile tile = tiles.get(x + "," + y);
				field.add(tile);
				tile.setPickablity(false);
			}
		}

		ArrayList<GameStone> currentGameStones = DBCommunicator.getHandLetters(
				game.getID(), DBCommunicator
						.requestInt("SELECT id from beurt WHERE spel_id = "
								+ game.getID()
								+ " AND account_naam = '"
								+ gui.getApplication().getCurrentAccount()
										.getUsername() + "' ORDER BY id DESC"),
				Loader.getGameStones("EN"));

		for (int i = 0; i < 7; i++)
		{
			Tile tile = new Tile(i + 1, -1);
			if (i < currentGameStones.size())
			{
				if (currentGameStones.get(i) != null)
				{
					System.out.println(currentGameStones.get(i).getID());
					tile.setGameStone(currentGameStones.get(i));
					tile.getGameStone().setHand(true);

				}
			}
			hand.add(tile);
		}

		thread.start();
	}

	public void run()
	{
		while (running)
		{
			repaint();
			try
			{
				Thread.sleep(1000 / 60);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		for (Tile t : field)
		{
			g2d.drawImage(t.getImage(), (t.getXPos() * 33) + 180,
					(t.getYPos() * 33) + 10, null);
		}
		for (Tile t : hand)
		{
			g2d.drawImage(t.getImage(), (t.getXPos() * 33) + 180,
					(t.getYPos() * 33) + 580, null);
		}
		if (currentGameStone != null)
		{
			g2d.drawImage(currentGameStone.getImage(), mouseX
					- (currentGameStone.getImage().getWidth() / 2), mouseY
					- (currentGameStone.getImage().getHeight() / 2), null);
		}
		g2d.dispose();
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			for (Tile t : field)
			{
				if ((e.getX() >= (t.getXPos() * 33) + 180)
						&& (e.getX() <= (t.getXPos() * 33) + 180 + 32)
						&& (e.getY() >= (t.getYPos() * 33) + 10)
						&& (e.getY() <= (t.getYPos() * 33) + 10 + 32))
				{
					if (currentGameStone == null)
					{
						if (t.getPickablity())
						{
							currentGameStone = t.getGameStone();
							t.setPickablity(false);
							score.setText("Your turn score would be: "
									+ gui.layGameStone(currentGameStone,
											(t.getXPos() + "," + t.getYPos())));

							t.setGameStone(null);
						}
					}
					else
					{
						if (t.getGameStone() == null)
						{
							t.setGameStone(currentGameStone);
							score.setText("Your turn score would be: "
									+ gui.layGameStone(currentGameStone,
											(t.getXPos() + "," + t.getYPos())));
							t.setPickablity(true);
							currentGameStone = null;
						}
					}
				}
			}

			for (Tile t : hand)
			{
				if ((e.getX() >= (t.getXPos() * 33) + 180)
						&& (e.getX() <= (t.getXPos() * 33) + 180 + 32)
						&& (e.getY() >= (t.getYPos() * 33) + 580)
						&& (e.getY() <= (t.getYPos() * 33) + 580 + 32))
				{
					if (currentGameStone == null)
					{
						if (t.getPickablity())
						{
							currentGameStone = t.getGameStone();
							t.setGameStone(null);
						}
					}
					else
					{
						if (t.getGameStone() == null)
						{
							t.setGameStone(currentGameStone);
							currentGameStone = null;
						}
					}
				}
			}
		}
		if (e.getButton() == MouseEvent.BUTTON2)
		{

		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(mp.getBackButton()))
		{
			running = false;
		}
		if (e.getSource().equals(shuffle))
		{
			for (int i = 0; i < field.size(); i++)
			{
				if (field.get(i).getGameStone() != null)
				{
					if (field.get(i).getGameStone().getHand())
					{
						field.get(i).setGameStone(null);
					}
				}
			}
			ArrayList<GameStone> stones = new ArrayList<GameStone>();
			for(Tile t:hand) {
				if(t.getGameStone() != null) {
					stones.add(t.getGameStone());
				}
				t.setGameStone(null);
			}
			Collections.shuffle(stones);
			for(int i = 0; i < hand.size(); i++) {
				if(stones.get(i) != null) {
					hand.get(i).setGameStone(stones.get(i));
				}
			}
			currentGameStone = null;
		}
		if (e.getSource().equals(swap))
		{

			gui.switchPanel(new GamePanel(gui));
		}
		if (e.getSource().equals(play))
		{
			System.out.println(gui.playWord());
			gui.switchPanel(new GamePanel(gui));
		}
		if (e.getSource().equals(resign))
		{
			game.resign();
			gui.switchPanel(new GamePanel(gui));
		}
		if (e.getSource().equals(pass))
		{
			System.out.println("pass");
			gui.pass();
			gui.switchPanel(new GamePanel(gui));
		}
	}

	public void mouseDragged(MouseEvent w)
	{
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public int getTurnScore()
	{
		return turnScore;
	}

	public void setTurnScore(int turnScore)
	{
		this.turnScore = turnScore;
	}
}
