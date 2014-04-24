package GUI;

import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class AdminPanel extends Panel {
	private JComboBox playerList;
	private Vector<String> players;
	private JLabel selectPlayer;
	
	
	public AdminPanel(){
		selectPlayer = new JLabel("Select player: ");
		players = new Vector<String>();
		this.playerList = new JComboBox(players);
		this.add(playerList);
	}
	
	
	
}
