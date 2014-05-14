package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< .merge_file_a06916
=======
import java.util.ArrayList;
>>>>>>> .merge_file_a00184

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Utility.DBCommunicator;
import Utility.SButton;
import Utility.SLabel;

public class ModeratorPanel extends JPanel {
	private GUI gui;
	private SLabel selectWord;
<<<<<<< .merge_file_a06916
	// has to become an array of the words from database
	String[] posibleWords = { "dushi", "selfie", "derp" };
	private JComboBox<String> wordList = new JComboBox(posibleWords);
=======
	ArrayList<String> posibleWords = DBCommunicator
			.requestMoreData("SELECT woord FROM woordenboek where status='Pending'");
	private JComboBox<String> wordList = new JComboBox();
>>>>>>> .merge_file_a00184
	private SButton acceptWord = new SButton("Accept word", SButton.GREY),
			rejectWord = new SButton("Reject word", SButton.GREY),
			addNewWord = new SButton("Add new word", SButton.GREY);

	public ModeratorPanel(GUI gui) {
<<<<<<< .merge_file_a06916
=======
		for (String merge : posibleWords)
		{
			wordList.addItem(merge);
		}
>>>>>>> .merge_file_a00184
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.gui = gui;

		// actionlisteners==================================================
		addNewWord.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String wordTooAdd = JOptionPane.showInputDialog(
						"What word would you like to add?", null);
				if (wordTooAdd != null)
				{
					wordTooAdd.toLowerCase();

					if (wordTooAdd.equals(""))
					{
						JOptionPane.showMessageDialog(null, "No word added",
								"error", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
<<<<<<< .merge_file_a06916
						// insert qeury vragen en overzien!
						//
						// DBCommunicator
						// .writeData("INSERT INTO woordenboek  (woord,letterset_code,status) VALUES('"
						// + wordTooAdd + "','EN','Accepted')");
						JOptionPane.showMessageDialog(null, wordTooAdd
								+ " added", "Succes",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
=======
						DBCommunicator
								.writeData("INSERT INTO woordenboek  (woord,letterset_code,status) VALUES('"
										+ wordTooAdd + "','EN','Accepted')");
						JOptionPane.showMessageDialog(null, wordTooAdd
								+ " added", "Succes",
								JOptionPane.INFORMATION_MESSAGE);
						posibleWords = DBCommunicator
								.requestMoreData("SELECT woord FROM woordenboek where status='Pending'");
					}
				}
			}
		});

>>>>>>> .merge_file_a00184
		acceptWord.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
<<<<<<< .merge_file_a06916
				// database to add word to wordlist and delete from sugestions
				// communicator*******************************************
				JOptionPane.showMessageDialog(null, "Word aproved and added",
						"succes", JOptionPane.INFORMATION_MESSAGE);

=======
				String accept = (String) wordList.getSelectedItem();
				DBCommunicator
						.writeData("UPDATE woordenboek SET status='Accepted' WHERE woord='"
								+ accept + "'");
				JOptionPane.showMessageDialog(null, "Word aproved and added",
						"succes", JOptionPane.INFORMATION_MESSAGE);
				posibleWords = DBCommunicator
						.requestMoreData("SELECT woord FROM woordenboek where status='Pending'");
>>>>>>> .merge_file_a00184
			}
		});

		rejectWord.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String remove = (String) wordList.getSelectedItem();
<<<<<<< .merge_file_a06916

				for (int t = 0; t < posibleWords.length; t++)
				{
					if (posibleWords[t].equals(remove))
					{
						posibleWords[t] = "";
						break;
					}
				}

				wordList = new JComboBox(posibleWords);
=======
				DBCommunicator
						.writeData("UPDATE woordenboek SET status='Denied' WHERE woord='"
								+ remove + "'");
>>>>>>> .merge_file_a00184

				JOptionPane.showMessageDialog(null, "Word rejected", "succes",
						JOptionPane.ERROR_MESSAGE);

			}
		});
		// ======================================================================

		this.selectWord = new SLabel("Select word: ", 0);
		// Adding components on the right locations
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 5, 50);

		this.add(this.selectWord, c);
		this.add(this.wordList, c);
		c.gridy++;
		this.add(this.acceptWord, c);
		this.add(this.rejectWord, c);
		c.gridy++;
		c.gridx = c.gridx + 3;
		this.add(this.addNewWord, c);

	}
<<<<<<< .merge_file_a06916
}
=======
}
>>>>>>> .merge_file_a00184
