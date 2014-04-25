package GUI;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import WordFeud.GameStone;

@SuppressWarnings("serial")
public class GUI extends JFrame{

	public GUI(){
		// EDIT HIER JE PANEL!
		StatisticsPanel PN = new StatisticsPanel();
		this.setPreferredSize(new Dimension(1280,800));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//UNCOMMENT DIT EN JE PANNEL IN DE PANE!
		this.setContentPane(PN);
		
		
		this.pack();
		this.setVisible(true);
	}
	
	public void playWord(){
		
	}
	
	public boolean layGameStone(GameStone gamestone, String location){
		return false;
	}
	
	public void switchPanel(JPanel pane){
		
	}
	
	public void pass(){
		
	}
	
	public void shuffle(){
		
	}
	
	public void swapGameStones(){
		
	}
	
}
