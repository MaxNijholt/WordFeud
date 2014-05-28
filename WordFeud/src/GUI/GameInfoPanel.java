package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class GameInfoPanel extends JPanel {

	public GameInfoPanel() {
		this.setLayout(new GridLayout(5, 1, 0, 10));
		this.setPreferredSize(new Dimension(180, 215));
		this.setBackground(new Color(33, 36, 40));
	}

}
