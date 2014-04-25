package GUI;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import Utility.DBCommunicator;

@SuppressWarnings("serial")
public class AdminPanel extends Panel implements ItemListener{
	private JComboBox playerList = new JComboBox();
	private Vector<String> players;
	@SuppressWarnings("unused")
	private JLabel selectPlayer;
	
	
	public AdminPanel(){
		this.setPreferredSize(new Dimension(GUI.WIDTH,GUI.HEIGHT));
		this.playerList.setEditable(true);
		this.playerList.addItemListener(this);
		selectPlayer = new JLabel("Select player: ");
		players = new Vector<String>();
		this.add(playerList);
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
	          System.out.println(playerList.getSelectedItem());
	          ArrayList<String> data = DBCommunicator.requestMoreData("SELECT naam FROM account WHERE naam LIKE '"  + playerList.getSelectedItem() + "%'" );
	          System.out.println("SELECT naam FROM account WHERE naam LIKE '"  + playerList.getSelectedItem() + "%'" );
	          players.clear();
	          playerList.removeAll();
	          for (String s : data){
	        	  System.out.println(s);
	        	  players.add(s);
	          }
	          for(String pl : players){
		          playerList.addItem(pl);
	          }
	       }
	}
	
	
	
}
