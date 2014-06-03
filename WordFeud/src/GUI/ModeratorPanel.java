package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Utility.MComboBox;
import Utility.SButton;
import Utility.SLabel;

@SuppressWarnings("serial")
public class ModeratorPanel extends JPanel {
	private GUI 				mygui;
	private SLabel 				selectWord, searchWord;
	private ArrayList<String> 	posibleWords 	= new ArrayList<String>();
	private MenuPanel 			mp;
	private JPanel 				allPanel;
	private JComboBox<String> 	wordList 		= new JComboBox<String>();
	private SButton 			acceptWord 		= new SButton("Accept word", SButton.GREY),
								rejectWord 		= new SButton("Reject word", SButton.GREY),
								addNewWord 		= new SButton("Add new word", SButton.GREY),
								denyWord 		= new SButton("Remove word", SButton.GREY);
	private MComboBox			removeWord		= new MComboBox(150, 25, null, this);

	public ModeratorPanel(final GUI gui) {
		mygui = gui;
		mygui.setLoadingCursor(true);
		this.mp = new MenuPanel(gui, "LoginPanel");
		allPanel = new JPanel();
		allPanel.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		allPanel.setBackground(new Color(94, 94, 94));

		allPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(94,94,94));
		posibleWords = mygui.getApplication().getCurrentAccount().getMod().getNotAprovedWords();
		wordList = new JComboBox<String>();
		for (String merge : posibleWords) {
			wordList.addItem(merge);
		}
		denyWord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gui.getApplication().getCurrentAccount().getMod().denyWord(removeWord.getSelectedItem());
			}
		});
		addNewWord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String wordTooAdd = JOptionPane.showInputDialog(
						"What word would you like to add?", null);
				if (wordTooAdd != null) {
					wordTooAdd.toLowerCase();
					if (wordTooAdd.equals("")) {
						JOptionPane.showMessageDialog(null, "No word added", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						if (mygui.getApplication().getCurrentAccount().getMod().tryAddWord(wordTooAdd) == true) {
							mygui.getApplication().getCurrentAccount().getMod().addWord(wordTooAdd);
							JOptionPane.showMessageDialog(null, wordTooAdd	+ " added", "Succes", JOptionPane.INFORMATION_MESSAGE);
							posibleWords = mygui.getApplication() .getCurrentAccount().getMod() .getNotAprovedWords();
						} else {
							JOptionPane.showMessageDialog(null, wordTooAdd + " already in Database", "Error", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});

		acceptWord.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String accept = (String) wordList.getSelectedItem();
				mygui.getApplication().getCurrentAccount().getMod() .aproveWord(accept);
				JOptionPane.showMessageDialog(null, "Word aproved and added", "Succes", JOptionPane.INFORMATION_MESSAGE);
				posibleWords = mygui.getApplication().getCurrentAccount() .getMod().getNotAprovedWords();
				wordList.removeAllItems();
				for (String merge : posibleWords) {
					wordList.addItem(merge);
				}
			}
		});

		rejectWord.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String remove = (String) wordList.getSelectedItem();
				mygui.getApplication().getCurrentAccount().getMod() .denyWord(remove);
				JOptionPane.showMessageDialog(null, "Word rejected", "Succes", JOptionPane.ERROR_MESSAGE);
				posibleWords = mygui.getApplication().getCurrentAccount() .getMod().getNotAprovedWords();
				wordList.removeAllItems();
				for (String merge : posibleWords) {
					wordList.addItem(merge);
				}
			}
		});

		this.selectWord = new SLabel("Select word: ", 0);
		this.searchWord = new SLabel("Search word: ", 0);
		// Adding components on the right locations
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 5, 50);

		allPanel.add(this.selectWord, c);
		allPanel.add(this.wordList, c);
		c.gridy++;
		allPanel.add(this.acceptWord, c);
		allPanel.add(this.rejectWord, c);
		c.gridy++;
		c.gridx = c.gridx + 3;
		allPanel.add(this.addNewWord, c);
		c.gridy++;
		c.gridx = c.gridx - 3;
		allPanel.add(this.searchWord,c);
		allPanel.add(this.removeWord,c);
		allPanel.add(this.denyWord,c);

		mygui.setLoadingCursor(false);
		this.add(mp, BorderLayout.NORTH);
		this.add(allPanel, BorderLayout.CENTER);
	}
}
