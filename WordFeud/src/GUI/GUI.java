package GUI;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Core.Application;
import WordFeud.GameStone;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Wordfeud";
	private Application app;
	private JPanel currpanel = new LoginPanel(this);

	public GUI(Application app) {
		this.app = app;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle(TITLE);
		this.setContentPane(new CompetitionCreatePanel(this));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void playWord() {

	}

	public boolean layGameStone(GameStone gamestone, String location) {
		return false;

	}

	public void switchPanel(JPanel panel) {
		if (currpanel != null) {
			this.remove(currpanel);
		}
		this.currpanel = panel;
		this.add(panel);
		this.revalidate();
	}

	public void switchPanel(JPanel panel, boolean menu) {
		if (currpanel != null) {
			this.remove(currpanel);
		}
		if (menu)
			this.add(new MenuPanel(this, app.getCurrentAccount().toString()));
		this.currpanel = panel;
		this.add(panel);
		this.revalidate();
	}

	public void setLoadingCursor(boolean loading) {
		if (loading) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		} else {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public void pass() {

	}

	public void shuffle() {

	}

	public void swapGameStones() {

	}

	public ArrayList<Integer> getFinishedGames(boolean resigned) {
		ArrayList<Integer> gameInts = app.getFinishedGames(resigned);
		return gameInts;
	}

	public ArrayList<Integer> getPlayingGames(boolean myTurn) {
		ArrayList<Integer> gameInts = app.getPlayingGames(myTurn);
		return gameInts;
	}

	public ArrayList<Integer> getRequestedGames(boolean myRequest,
			boolean denied) {
		ArrayList<Integer> gameInts = app.getRequestedGames(myRequest, denied);
		return gameInts;
	}

	public String getOpponentName(int gameID) {

		return app.getOpponentName(gameID);
	}
}
