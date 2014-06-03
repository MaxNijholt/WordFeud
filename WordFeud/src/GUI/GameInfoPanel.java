package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Utility.AScrollPane;
import Utility.DBCommunicator;
import Utility.SButton;
import Utility.SLabel;

@SuppressWarnings("serial")
public class GameInfoPanel extends JPanel {
	private SLabel score = new SLabel("", SLabel.CENTER, 200, 40);
	private int turnScore = 0;

	public GameInfoPanel() {
		this.setPreferredSize(new Dimension(195, 315));
		this.setBackground(new Color(23, 26, 30));
		score.setName("Turn score : " + turnScore);
		score.setOpaque(true);
		score.drawBackground(true);
		score.changeTextColor(SButton.WHITE, SButton.GREEN);
		score.setFont(new Font("Arial", Font.BOLD, 10));
		score.setSize(new Dimension(100, 30));
		this.add(score);
		score.setBounds(0, 0, 180, 40);

	}

	public void updateInfo(int scoreTurn)
	{
		this.turnScore = scoreTurn;
		score.setName("Turn score : " + turnScore);

	}

	public void deniedReqeust(ArrayList<String> deniedWords)
	{
		int x = 0;
		int y = 0;

		if (deniedWords!=null &&deniedWords.size() != 0)
		{
			for (String deleter : deniedWords)
			{
				String name = "Request for aproval";
				y = y + 50;

				SLabel addDeniedWord = new SLabel(deleter, SLabel.CENTER, 90,
						40);
				final SButton request = new SButton(name, SButton.GREEN, 90, 40);

				final String word = deleter;
				if (!request.getName().equals("Requested"))
				{

					request.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0)
						{
							if (DBCommunicator
									.requestData("SELECT * FROM woordenboek where woord = '"
											+ word + "'") == null)
							{
								DBCommunicator
										.writeData("INSERT  INTO `woordenboek`(`woord`,`letterset_code`,`status`) VALUES("
												+ "'"
												+ word
												+ "'"
												+ ",'EN','Pending')");

							}
							else
							{
								DBCommunicator
										.writeData("INSERT  INTO `woordenboek`(`woord`,`letterset_code`,`status`) VALUES("
												+ "'"
												+ word
												+ "'"
												+ ",'EN','Pending')");

							}
							request.setName("Requested");
						}

					});
				}
				if (DBCommunicator
						.requestData("SELECT woord FROM woordenboek where status='Pending' and woord='"
								+ deleter + "'") != null)
				{
					request.setName("Requested");
					request.setColor(SButton.RED);
				}
				addDeniedWord.setOpaque(true);
				addDeniedWord.changeTextColor(Color.WHITE, SButton.RED);
				addDeniedWord.drawBackground(true);
				addDeniedWord.setCustomRounded(true, false, true, false);
				request.setCustomRounded(false, true, false, true);

				this.add(addDeniedWord);
				addDeniedWord.setBounds(x, y, 90, 40);
				x = 80;
				this.add(request);
				request.setBounds(x, y, 90, 40);

				request.setFont(new Font("Arial", Font.BOLD, 8));

				x = 0;
			}

		}
		else
		{

		}

	}

	public void emptyPanel()
	{
		this.removeAll();
		this.add(score);
	}

}
