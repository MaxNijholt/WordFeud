package GUI;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class AddPlayersPanel extends JPanel{

	private GUI gui;
	private MenuPanel mp;
	
	public AddPlayersPanel(GUI gui){
		this.gui = gui;
		this.setLayout(new BorderLayout());
		
		mp = new MenuPanel(gui, "CompetitionCreatePanel");
		this.add(mp, BorderLayout.NORTH);
	}
	
}
