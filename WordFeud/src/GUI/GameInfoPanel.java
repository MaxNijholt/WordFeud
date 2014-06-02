package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Utility.AScrollPane;

public class GameInfoPanel extends JPanel {
	private JLabel score = new JLabel();
	private int turnScore = 0;

	public GameInfoPanel() {
		this.setLayout(new GridLayout(5, 1, 0, 10));
		this.setPreferredSize(new Dimension(180, 215));
		this.setBackground(new Color(33, 36, 40));
		score.setText("Your turn score will be: " + turnScore);
		score.setOpaque(true);
		score.setBackground(Color.GREEN);
		score.setFont(new Font("Arial", Font.BOLD, 14));
		this.add(score);

		AScrollPane scoreBar = new AScrollPane(this.getPreferredSize().width,
				this.getPreferredSize().height, this, false, true);
		add(scoreBar);
		scoreBar.setBounds(10, 320, this.getPreferredSize().width,
				this.getPreferredSize().height);

	}

	public void updateInfo(ArrayList<String> denied, int scoreTurn)
	{
		this.turnScore = scoreTurn;
		score.setText("Your turn score will be: " + turnScore);
		if (denied.size() != 0)
		{
			for (String deleter : denied)
			{
				JLabel addDeniedWord = new JLabel();
				addDeniedWord.setOpaque(true);
				addDeniedWord.setBackground(Color.RED);
				addDeniedWord.setFont(new Font("Arial", Font.BOLD, 14));
				addDeniedWord.setText(deleter);
				this.add(addDeniedWord);
			}

		}

	}

}
