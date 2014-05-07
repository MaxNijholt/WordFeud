package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private GUI gui;
	private JButton passButton, swapButton, resignButton, playButton;
	private ChatPanel chatPanel;
	private JPanel northPanel, eastPanel, southPanel, westPanel;
	
	public GamePanel(GUI gui){
		this.gui = gui;
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		this.setLayout(new BorderLayout());
		
		//northPanel: The menu
		northPanel.setPreferredSize(new Dimension(1000, 100));
		northPanel.setMaximumSize(northPanel.getPreferredSize());
		northPanel.setMinimumSize(northPanel.getPreferredSize());
		
		this.add(northPanel, BorderLayout.NORTH);
		
		//eastPanel: chat
		eastPanel.setPreferredSize(new Dimension(250, 400));
		eastPanel.setMaximumSize(eastPanel.getPreferredSize());
		eastPanel.setMinimumSize(eastPanel.getPreferredSize());
		
		chatPanel = new ChatPanel();
		eastPanel.add(chatPanel);
		
		this.add(eastPanel, BorderLayout.EAST);
		
		//southPanel: The stones you hold
		southPanel.setPreferredSize(new Dimension(1000, 100));
		southPanel.setMaximumSize(southPanel.getPreferredSize());
		southPanel.setMinimumSize(southPanel.getPreferredSize());
		
		this.add(southPanel, BorderLayout.SOUTH);
		
		//westPanel: This turns options
		westPanel.setPreferredSize(new Dimension(250, 400));
		westPanel.setMaximumSize(westPanel.getPreferredSize());
		westPanel.setMinimumSize(westPanel.getPreferredSize());
		westPanel.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		passButton = new JButton("Pass");
		swapButton = new JButton("Swap");
		resignButton = new JButton("Resign");
		playButton = new JButton("Play");
		
		westPanel.add(passButton);
		westPanel.add(swapButton);
		westPanel.add(resignButton);
		westPanel.add(playButton);
		
		this.add(westPanel, BorderLayout.WEST);
		
		
	}
	
	
}
