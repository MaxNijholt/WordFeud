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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import Utility.AScrollPane;
import Utility.DBCommunicator;
import Utility.SButton;
import Utility.STextArea;
import WordFeud.Chat;
import WordFeud.Game;
import WordFeud.GameStone;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel implements ActionListener, KeyListener {

	private STextArea 	printArea;
	private JTextArea 	typeArea;
	private SButton		send;
	private Game		game;
	private GUI			gui;
	private AScrollPane	scrollTypePanel;
	private Chat 		chat;
	
	public ChatPanel(GUI gui, Game game){
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		this.game = game;
		this.gui = gui;
			
		printArea = new STextArea(220, 350);	
		printArea.setEditable(false);
		
		typeArea= new JTextArea();
		typeArea.addKeyListener(this);
		typeArea.setWrapStyleWord(true);
		typeArea.setLineWrap(true);
		
		
		scrollTypePanel = new AScrollPane(160, 90, typeArea, false, true);
		scrollTypePanel.setPreferredSize(new Dimension(160,90));
		scrollTypePanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollTypePanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
				
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
		
		chat = new Chat(game.getID(), this);
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

	public void keyReleased(KeyEvent e){}

	public void keyTyped(KeyEvent e){}
	
	public void setChatText(ArrayList<String> chat){
		printArea.setText("");
		for(String e : chat){
			printArea.setText(printArea.getText() + e);
			//System.out.println(e);
		}
	}
}
