package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import WordFeud.GameStone;

@SuppressWarnings("serial")
public class GUI extends JFrame{

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Wordfeud";
	
	public GUI(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle(TITLE);
		this.setContentPane(new LoginPanel(this));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void playWord(){
		
	}
	
	public boolean layGameStone(GameStone gamestone, String location){
		return false;
		
	}
	
	public void switchPanel(JPanel panel){
		this.getContentPane().removeAll();
		this.setContentPane(panel);
		this.revalidate();
	}
	
	public void pass(){
		
	}
	
	public void shuffle(){
		
	}
	
	public void swapGameStones(){
		
	}
	
}
