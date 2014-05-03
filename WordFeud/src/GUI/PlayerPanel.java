package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Utility.SButton;
import Utility.SLabel;
import Utility.STextField;
import WordFeud.Login;

public class PlayerPanel extends JPanel {

	STextField searchText;
	SButton searchButton;
	JPanel searchPanel;
	
	
	public PlayerPanel(GUI gui){
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setBackground(new Color(94, 94, 94));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		searchText = new STextField("search");
		searchButton = new SButton("search", SButton.GREY, 220, 40);
		searchPanel = new JPanel();
		searchPanel.setBackground(new Color(94, 94, 94));
		searchPanel.setLayout(new FlowLayout());
		searchPanel.add(searchText);
		searchPanel.add(searchButton);
		
		this.add(searchPanel);
		this.add(new SLabel("your turn", 5));
		
	}

	
	private void paintGame(){
		
	}
}
