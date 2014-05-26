package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import Utility.AScrollPane;
import Utility.DBCommunicator;
import Utility.SButton;
import Utility.SLabel;
import Utility.STextArea;
import WordFeud.Chat;
import WordFeud.Game;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel implements ActionListener, KeyListener {

	private STextArea 			typeArea;
	private SButton				send;
	private ArrayList<SLabel> 	messages;
	private AScrollPane			scroller;
	private JPanel				scrollPanel;
	private Chat 				chat;
	private Game				game;
	private GUI					gui;
	
	public ChatPanel(GUI gui, Game game){
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		this.game = game;
		this.gui = gui;
		chat = new Chat();
		
		messages = new ArrayList<SLabel>();
		scrollPanel = new JPanel();
		scroller = new AScrollPane(getWidth(), getHeight() - 100, scrollPanel, false, true);
		
		typeArea= new STextArea(getWidth() - 60, 100);
		typeArea.setCustomRounded(true, false, true, false);
		typeArea.addKeyListener(this);
		
		send = new SButton("Send", SButton.GREEN, 60, 100);
		send.setCustomRounded(false, true, false, true);
		send.addActionListener(this);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(5, 0, 5, 0);
		this.add(scroller, c);
		c.gridy++;
		c.gridwidth = 1;
		this.add(typeArea, c);
		c.gridx++;
		this.add(send, c);
	}

	public void actionPerformed(ActionEvent e) {
		DBCommunicator.sendMsg(typeArea.getText(), game.getID(), gui.getApplication().getCurrentAccount().getUsername());
		//printArea.setText(printArea.getText() + "\n" + typeArea.getText());
		typeArea.setText("");
	}

	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			e.consume();
			System.out.println(typeArea.getText());
			System.out.println(game.getID());
			System.out.println(gui.getApplication().getCurrentAccount().getUsername());
			//chat.sendMsg(typeArea.getText(), new Game(1), gui.getApplication().getCurrentAccount());
			DBCommunicator.sendMsg(typeArea.getText(), game.getID(), gui.getApplication().getCurrentAccount().getUsername());
			//printArea.setText(printArea.getText() + "\n" + typeArea.getText());
			typeArea.setText("");
		 }
	}

	
	public void keyReleased(KeyEvent e)
	{
		
	}


	public void keyTyped(KeyEvent e)
	{
		
		
	}
	
	
}
