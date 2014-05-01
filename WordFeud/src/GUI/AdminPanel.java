package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import Utility.DBCommunicator;
import Utility.SButton;

/**
 * @author Max Nijholt
 * 
 */

@SuppressWarnings({ "serial", "rawtypes" , "unused" })
public class AdminPanel extends Panel implements ItemListener {
	private JComboBox playerList = new JComboBox();
	private Vector<String> players;
	private JLabel selectPlayer;
	private Dimension dim = new Dimension(200, 20);
	private GUI gui;
	private SButton grantMod = new SButton("Grant Mod", Color.GRAY),
			grantAdmin = new SButton("Grant Admin", Color.GRAY),
			revokeMod = new SButton("Revoke Mod", Color.GRAY),
			revokeAdmin = new SButton("Revoke Admin", Color.GRAY);
	private ActionAdapter aa = new ActionAdapter();

	public AdminPanel() {
		this.setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
		
		this.playerList.setEditable(true);
		this.playerList.setPreferredSize(dim);
		this.playerList.addItemListener(this);
		
		this.selectPlayer 	= new JLabel("Select player: ");
		this.players 		= new Vector<String>();
		
		this.add(this.selectPlayer);
		this.add(this.playerList);
		this.add(this.grantMod);
		this.add(this.grantAdmin);
		this.add(this.revokeAdmin);
		this.add(this.revokeMod);
		
		this.grantAdmin	.addActionListener(aa);
		this.grantMod	.addActionListener(aa);
		this.revokeAdmin.addActionListener(aa);
		this.revokeMod	.addActionListener(aa);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			System.out.println(playerList.getSelectedItem());
			ArrayList<String> data = null;
			//data = DBCommunicator.requestMoreData("SELECT naam FROM account WHERE naam LIKE '" + playerList.getSelectedItem() + "%'");
			
			players.clear();
			playerList.removeAll();
			
			for (String s : data) {
				System.out.println(s);
				players.add(s);
			}
			
			for (String pl : players) {
				playerList.addItem(pl);
			}
		}
	}

	/*
	 * Method to remove rights from selected player
	 */
	private void removePrivilege(String player, String right) {
		DBCommunicator.writeData("DELETE FROM accountrol WHERE account_naam='"
				+ player + "' AND rol_type='" + right + "'");
	}

	/*
	 * Method to add rights to selected player
	 */
	private void addPrivilege(String player, String right) {
		DBCommunicator
				.writeData("INSERT INTO accountrol (account_naam, rol_type) VALUES('"
						+ player + "','" + right + "')");
	}

	/*
	 * Method for a more easy lookup player rights
	 */
	/*private ArrayList<String> getUserRights( String player) {
		ArrayList<String> data = DBCommunicator.requestMoreData("SELECT rol_type FROM accountrol WHERE account_naam='"+ player + "'");
		return data;
	}*/

	class ActionAdapter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(grantAdmin)) {
				addPrivilege(playerList.getSelectedItem().toString(),
						"Administrator");
			}
			if (e.getSource().equals(grantMod)) {
				addPrivilege(playerList.getSelectedItem().toString(),
						"Moderator");
			}
			if (e.getSource().equals(revokeMod)) {
				removePrivilege(playerList.getSelectedItem().toString(),
						"Moderator");
			}
			if (e.getSource().equals(revokeAdmin)) {
				removePrivilege(playerList.getSelectedItem().toString(),
						"Administrator");
			}
		}
	}

	
	
}
