package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import Utility.AScrollPane;
import WordFeud.Spectator;

@SuppressWarnings("serial")
public class SpectatorInfoPanel extends JPanel {

	private JTextArea printArea;
	private String text, scoreText;
	private int gameID;
	private GUI gui;
	private AScrollPane scrollPrintPanel;
	private Spectator spectate;
	private int[] scores;
	private String[] players;

	public SpectatorInfoPanel(GUI gui, int gameID, Spectator spectate) {
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		this.gameID = gameID;
		this.gui = gui;
		this.spectate = spectate;
		text = "";

		printArea = new JTextArea();
		printArea.setEditable(false);
		printArea.setWrapStyleWord(true);
		printArea.setLineWrap(true);

		scrollPrintPanel = new AScrollPane(220, 350, printArea, false, true);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(5, 0, 5, 0);
		this.add(scrollPrintPanel, c);
	}

	public void updateText(int turn) {
		String newText;
		newText = spectate.turnText(gameID, turn);
		players = spectate.players(gameID, turn);
		scores = gui.getApplication().getSpectateScores(gameID, players[0],
				turn);
		scoreText = players[0] + " has: " + scores[0] + " points \n"
				+ players[1] + " has: " + scores[1] + " points \n";

		text = newText + "\n" + scoreText + "\n" + text;
		printArea.setText(text);
	}

}
