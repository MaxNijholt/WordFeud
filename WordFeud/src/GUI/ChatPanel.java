package GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Utility.DBCommunicator;
import Utility.SButton;
import Utility.STextArea;
import WordFeud.Chat;
import WordFeud.Game;
import WordFeud.GameStone;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel implements ActionListener, KeyListener {

	private STextArea 	printArea;
	private STextArea 	typeArea;
	private SButton		send;
	private Game		game;
	private GUI			gui;
	private JScrollPane	scrollTypePanel;
	private Chat 		chat;
	
	public ChatPanel(GUI gui, Game game){
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		this.game = game;
		this.gui = gui;
		
		chat = new Chat(game.getID(), this);
		
		printArea = new STextArea(220, 350);	
		printArea.setEditable(false);
		
		typeArea= new STextArea(160, 90);
		typeArea.setCustomRounded(true, false, true, false);
		typeArea.addKeyListener(this);
		
		scrollTypePanel = new JScrollPane(typeArea);
		scrollTypePanel.setPreferredSize(new Dimension(160,90));		
		
		
		
		
		send = new SButton("Send", SButton.GREEN, 60, 90);
		send.setCustomRounded(false, true, false, true);
		send.addActionListener(this);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(5, 0, 5, 0);
		this.add(printArea, c);
		c.gridy++;
		c.gridwidth = 1;
		this.add(scrollTypePanel, c);
		c.gridx++;
		this.add(send, c);
	}

	public void actionPerformed(ActionEvent e) {
		DBCommunicator.sendMsg(typeArea.getText(), game.getID(), gui.getApplication().getCurrentAccount().getUsername());
		typeArea.setText("");
	}

	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			e.consume();
			DBCommunicator.sendMsg(typeArea.getText(), game.getID(), gui.getApplication().getCurrentAccount().getUsername());
			typeArea.setText("");
		 }
	}

	
	public void keyReleased(KeyEvent e)
	{
		
	}


	public void keyTyped(KeyEvent e)
	{
		
		
	}
	
	public void setChatText(ArrayList<String> chat){
		
		for(String e : chat){
			printArea.addText(e);
			System.out.println(e);
		}
		
	}
	
	
}
