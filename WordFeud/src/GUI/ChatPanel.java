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
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import Utility.AScrollPane;
import Utility.DBCommunicator;
import Utility.SButton;
import WordFeud.Chat;
import WordFeud.Game;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel implements ActionListener, KeyListener {

	private JTextArea 	typeArea, printArea;
	private SButton		send;
	private Game		game;
	private GUI			gui;
	private AScrollPane	scrollTypePanel,
						scrollPrintPanel;
	private Chat 		chat;
	
	public ChatPanel(GUI gui, Game game){
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		this.game = game;
		this.gui = gui;
			
		printArea = new JTextArea();	
		printArea.setEditable(false);
		printArea.setWrapStyleWord(true);
		printArea.setLineWrap(true);
		
		typeArea = new JTextArea();
		typeArea.addKeyListener(this);
		typeArea.setWrapStyleWord(true);
		typeArea.setLineWrap(true);
		
		scrollPrintPanel = new AScrollPane(220, 350, printArea, false, true);
		
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
		this.add(scrollPrintPanel, c);
		c.gridy++;
		c.gridwidth = 1;
		this.add(scrollTypePanel, c);
		c.gridx++;
		this.add(send, c);
		
		chat = new Chat(game.getID(), this);
	}

	public void actionPerformed(ActionEvent e) {
		if(!typeArea.getText().trim().isEmpty()){
			DBCommunicator.sendMsg(typeArea.getText().trim(), game.getID(), gui.getApplication().getCurrentAccount().getUsername());
			typeArea.setText("");
		}
		else System.out.println("[ChatPanel]: No text");
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(!typeArea.getText().trim().isEmpty()){
				e.consume();
				DBCommunicator.sendMsg(typeArea.getText().trim(), game.getID(), gui.getApplication().getCurrentAccount().getUsername());
				typeArea.setText("");
			}
			else System.out.println("[ChatPanel]: No text");
		 }
	}

	public void keyReleased(KeyEvent e){}

	public void keyTyped(KeyEvent e){}
	
	public void setChatText(ArrayList<String> chat){
		printArea.setText("");
		for(String e : chat){
			printArea.setText(printArea.getText() + e);
		}
	}
	
	public Chat getChat() {return chat;}
}
