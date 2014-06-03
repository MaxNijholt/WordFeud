package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import Utility.DBCommunicator;
import Utility.SButton;
import Utility.SLabel;

@SuppressWarnings("serial")
public class GameInfoPanel extends JPanel {
	private SLabel score = new SLabel("", SLabel.CENTER, 200, 40);
	private SLabel vs = new SLabel("", SLabel.CENTER, 200, 40);
	private GamePanel gameP;
	private int turnScore = 0;
	private int yourScore = 0;
	private int opponentScore = 0;
	private int potSize = 0;

	public GameInfoPanel(GamePanel gamepanel) {
		gameP = gamepanel;
		this.setPreferredSize(new Dimension(195, 315));
		this.setBackground(new Color(23, 26, 30));
		this.updateInfo(0, gamepanel.getGameScores());
		score.setName("Pot : "+gameP.getApp().getPotSize(gameP.getApp().getSelectedGame().getID())+" Turn score : " + turnScore);
		score.setOpaque(true);
		score.drawBackground(true);
		score.changeTextColor(SButton.WHITE, SButton.GREEN);
		score.setFont(new Font("Arial", Font.BOLD, 10));
		score.setSize(new Dimension(100, 30));
		score.setCustomRounded(true, true, true, true);
		vs.setName("you : "+yourScore+"  opponent : "+opponentScore+"");
		vs.setOpaque(true);
		vs.drawBackground(true);
		vs.changeTextColor(SButton.WHITE, SButton.CYAN);
		vs.setFont(new Font("Arial", Font.BOLD, 10));
		vs.setSize(new Dimension(100, 30));
		vs.setCustomRounded(true, true, true, true);
		
		this.add(score);
		score.setBounds(0, 0, 180, 40);
		this.add(vs);
		score.setBounds(0, 40, 180, 40);

	}


	public void updateInfo(int scoreTurn,int[] playerScores)
	{
		this.turnScore = scoreTurn;
		this.yourScore=playerScores[0];
		this.opponentScore=playerScores[1];
		score.setName("Pot : "+gameP.getApp().getPotSize(gameP.getApp().getSelectedGame().getID())+" Turn score : " + turnScore);
		vs.setName("you : "+yourScore+" opponent : "+opponentScore+"");
	}

	public void deniedReqeust(ArrayList<String> deniedWords)
	{
		int x = 0;
		int y = 50;

		if (deniedWords != null && deniedWords.size() != 0)
		{
			for (String deleter : deniedWords)
			{
				if (deleter.equals("Error"))
				{
					SLabel error = new SLabel("Incorect move", SLabel.CENTER,
							180, 40);
					error.setOpaque(true);
					error.changeTextColor(Color.WHITE, SButton.RED);
					error.drawBackground(true);
					error.setCustomRounded(true, true, true, true);
					this.add(error);
					error.setBounds(0, 100, 180, 40);
				}
				else
				{
					String name = "Request";
					y = y + 50;

					SLabel addDeniedWord = new SLabel(deleter, SLabel.CENTER,
							90, 40);
					final SButton request = new SButton(name, SButton.GREEN,
							90, 40);

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
								request.setEnabled(false);
							}

						});
					}
					if (DBCommunicator
							.requestData("SELECT woord FROM woordenboek where status='Pending' and woord='"
									+ deleter + "'") != null)
					{
						request.setName("Requested");
						request.setColor(SButton.RED);
						request.setEnabled(false);
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

		}
		else
		{

		}

	}

	public void emptyPanel()
	{
		this.removeAll();
		this.add(score);
		this.add(vs);
	}

}
