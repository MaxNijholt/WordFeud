package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import WordFeud.GameStone;

@SuppressWarnings("serial")
public class GUI extends JFrame{

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public GUI(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setContentPane(new ContentPane(new LoginPanel()));
		this.pack();
		this.setLocationRelativeTo(null);
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
