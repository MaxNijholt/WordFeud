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
	private String text;
	private int gameID;
	private GUI gui;
	private AScrollPane scrollPrintPanel;
	private Spectator spectate;

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
	
	public void updateText(int turn){
		String newText;
		newText = spectate.turnText(gameID, turn);
		text = newText + "\n" + text;
		printArea.setText(text);
	}

}
